珠峰正式课React源码学习

屏幕刷新率
绝大多数设备屏幕的刷新率是60次/秒

每一帧的预算时间是16.66ms （1 / 60）

每一帧（16.6ms）内，要处理很多事情：
1、处理事件
2、处理定时器
3、Begin Frame 开始帧
4、requestAnimationFrame
5、Layout
6、Paint
7、idle 空闲期

requestIdleCallback是向浏览器请求空闲时间执行回调
浏览器在一帧内，会先执行渲染、布局、绘制、资源加载、事件响应等高优先级的任务，这些高优任务执行完后如果这一帧还有空闲时间，就会分配空余的时间片，此时就会执行requestIdleCallback，所以requestIdleCallback的回调参数有一个属性，timeRemaining()，通过该函数，浏览器告诉用户当前帧还剩多少空闲时间

假如在一帧（16ms）内，上述高优任务花费了5ms，用户自己的任务花费2ms，之后就把剩余的时间片（16 - 5 - 2）归还给浏览器，让其执行更高优的任务

假如浏览器的高优任务执行耗时已经超过16ms，那requestIdleCallback就不再执行

告诉浏览器，现在就执行callback，但它的优先级比较低，可以空闲的时候再执行
但如果过了1000ms了还没执行，就要立即执行，1000ms其实相当于过期时间，或者说最长等待时间
window.requestIdleCallback( callback, { timeout: 1000 } )
Callback回调的参数是一个对象：
{
	timeRemaining(): 当前帧还剩多少空闲时间
	didTimeout: 传给requestIdleCallback的callback是否超时（即超过了1000ms还没执行）
}

class Update {
	constructor (payload, nextUpdate) {
		this.payload = payload
		this.nextUpdate = nextUpdate
	}
}
class UpdateQueue {
	constructor () {
		this.baseState = null
		this.firstUpdate = null
		this.lastUpdate = null
	}
	enqueueUpdate (update) {
		this.lastUpdate.nextUpdate = update
		this.lastUpdate = update
	}
	forceUpdate () {
		let currentState = this.baseState
		let currentUpdate = this.firstUpdate
		while (currentUpdate) {
			let nextState = typeof currentState.payload === ‘function’ ?
				currentUpdate.payload(currentState) : currentUpdate.payload
			currentState = { …currentState, …nextState }
			currentUpdate = currentUpdate.nextUpdate
		}
		this.firstUpdate = this.lastUpdate = null
		this.baseState = currentState
		return currentState
	}
}

React递归对比VirtualDOM树，找到变动的节点然后更新它们的过程叫reconcilation
在reconcilation期间，react一直占用浏览器的资源，一来会导致用户的事件得不到响应，二来会导致浏览器掉帧，感觉卡顿

React的每次更新都是从根节点开始，深度很深，而普通递归必须一气呵成，是无法中止递归过程的，所以需要Fiber
Fiber是一个执行单元，每次执行完一个执行单元，React将检查还剩多少时间片，如果没有时间就将控制权让出去

Fiber同时还是一种数据结构，React目前的做法是使用链表，每个VirtualDOM节点内部表示为一个Fiber

type Fiber = {
	return: // 注意不是parent，子节点和兄弟节点遍历完后接下来并不是回到父节点，而是回到叔叔节点
	child:
	sibling:
}

Fiber两个阶段——协调和提交
reconcilation（或者叫render）：协调渲染
commit：提交

let nextUnitOfWork = null
function workLoop () {
	while (nextUnitOfWork) {
		nextUnitOfWork = performUnitOfWork(nextUnitOfWork)
  }
  if (!nextUnitOfWork) {
    console.log('render结束')
  }
}

function performUnitOfWork (fiber) {
  beginWork(fiber)
  if (fiber.child) {
    return fiber.child
  }
  // 没有child了，证明此fiber节点已经完成了
  while(fiber) {
    completeUnitOfWork(fiber)
    if (fiber.sibling) {
      return fiber.sibling
    }
    fiber = fiber.return
  }
}

function completeUnitOfWork (fiber) {
  console.log('结束', fiber.key)
}

function beginWork (fiber) {
  console.log('开始', fiber.key)
}
nextUnitOfWork = rootFiber
requestIdleCallback(workLoop, { timeout: 1000 })



// ==============================================================================================

// createElement的实现
function createElement (type, config, ...children) {
  delete config.__self;
  delete config.__source; // 表示该元素在哪行哪列哪个文件生成的
  return {
    type,
    props: {
      config,
      children: children.map(child => {
        return typeof child === 'object' ? child : {
          type: ELEMENT_TEXT,
          props: { text: child, children: [] }
        }
      })
    }
  }
}
// 类组件的实现
class Component {
  constructor (props) {
    this.props = props
    this.updateQueue = new UpdateQueue()
  }
  setState (payload) {
    let update = new Update(payload)
    // 在源码中，updateQueue是放在此类组件对应的fiber节点的internalFiber
    // this.internalFiber.updateQueue.enqueueUpdate(update)
    this.updateQueue.enqueueUpdate(update)
    // 从根节点开始调度
    scheduleRoot()
  }
}
// 类组件标识：isReactComponent
Component.prototype.isReactComponent = {}
const React = {
  createElement,
  Component
}
export default React

export class Update {
  constructor (payload) {
    this.payload = payload
  }
}
// 数据结构是一个单链表
export class UpdateQueue {
  constructor () {
    this.firstUpdate = null
    this.lastUpdate = null
  }
  enqueueUpdate (update) {
    if (this.lastUpdate === null) {
      this.firstUpdate = this.lastUpdate = update
    } else {
      this.lastUpdate.nextUpdate = update
      this.lastUpdate = update
    }
  }
  forceUpdate (state) {
    let currentUpdate = this.firstUpdate
    while (currentUpdate) {
      let nextState = typeof currentUpdate.payload === 'function' ?
        currentUpdate.payload(state) :
        currentUpdate.payload
      state = { ...state, ...nextState }
      currentUpdate = currentUpdate.nextUpdate
    }
    this.firstUpdate = this.lastUpdate = null
    return state
  }
}

// fiber思想类似于generator，可中断

// render方法的实现：
function render (element, container) {
  let rootFiber = {
    tag: TAG_ROOT,
    stateNode: container, // 一般情况下如果这个元素是一个原生DOM节点的话，stateNode就指向真实DOM元素
    props: {
      // element是虚拟DOM
      children: [element]
    }
  }
  scheduleRoot(rootFiber)
}
const ReactDOM = {
  render
}
export default ReactDOM

// 从根节点开始渲染和调度
// 两个阶段：
// 1、diff阶段：对比新旧的虚拟DOM，进行增量更新或创建（即render）这个阶段可能比较花时间，我们可以对任务进行拆分，拆分的力度是虚拟DOM
// diff和render阶段的成果是effect list知道哪些节点更新，哪些删除了，哪些增加了
// render阶段两个任务：1、根据虚拟DOM生成fiber 2、收集effect list
// 2、commit阶段：进行DOM更新创建，此阶段不能暂停，必须一气呵成
let nextUnitOfWork = null
// rootFiber
let workInProgressRoot = null
// 渲染成功之后当前根rootFiber
let currentRoot = null
// 删除的节点并不放在effect list中，需要单独记录并执行
let deletions = []
function scheduleRoot (rootFiber) {
  // 第3次及之后渲染，复用上上一次树结构
  if (currentRoot && currentRoot.alternate) {
    workInProgressRoot = currentRoot.alternate
    workInProgressRoot.alternate = currentRoot
    if (rootFiber) {
      workInProgressRoot.props = rootFiber.props
    }
  }
  // 至少已经渲染过一次
  else if (currentRoot) {
    if (rootFiber) {
      rootFiber.alternate = currentRoot
      workInProgressRoot = rootFiber
    } else {
      workInProgressRoot = {
        ...currentRoot,
        alternate: currentRoot
      }
    }
  } else { // 第一次渲染
    nextUnitOfWork = workInProgressRoot
  }
  workInProgressRoot.firstEffect =
  workInProgressRoot.nextEffect =
  workInProgressRoot.lastEffect = null
}

function performUnitOfWork (currentFiber) {
  beginWork(currentFiber)
  if (currentFiber.child) {
    return currentFiber.child
  }
  while (currentFiber) {
    completeUnitOfWork(currentFiber)
    if (currentFiber.sibling) {
      return currentFiber.sibling
    }
    currentFiber = currentFiber.return
  }
}

// 在完成的时候要收集有副作用的fiber，然后组成effect list

// 每个fiber有两个属性，firstEffect指向第一个有副作用的子fiber
// lastEffect指向最后一个有副作用的子fiber
// 中间的用nextEffect依次指向下一个
function completeUnitOfWork (currentFiber) {
  let returnFiber = currentFiber.return
  if (returnFiber) {
    ////////////////////////////把自己儿子的effect的链挂在父亲上去////////////////////////////
    if (!returnFiber.firstEffect) {
      returnFiber.firstEffect = currentFiber.firstEffect
    }
    if (currentFiber.lastEffect) {
      if (returnFiber.lastEffect) {
        returnFiber.lastEffect.nextEffect = currentFiber.firstEffect
      }
      returnFiber.lastEffect = currentFiber.lastEffect
    }
    ////////////////////////////////////////////////////////
    const effectTag = currentFiber.effectTag
    if (effectTag) {
      if (returnFiber.lastEffect) {
        returnFiber.lastEffect.nextEffect = currentFiber
      } else {
        returnFiber.firstEffect = currentFiber
      }
      returnFiber.lastEffect = currentFiber
    }
  }
}

// beginWork和completeUnitOfWork相对分别代表开始和结束
// 1、创建真实DOM元素
// 2、创建子fiber
function beginWork (currentFiber) {
  if (currentFiber.tag === TAG_ROOT) {
    updateHostRoot(currentFiber)
  } else if (currentFiber.tag === TAG_TEXT) {
    updateHostText(currentFiber)
  } else if (currentFiber.tag === TAG_HOST) {
    updateHost(currentFiber)
  } else if (currentFiber.tag === TAG_CLASS) {
    updateClassComponent(currentFiber)
  } else if (currentFiber.tag === TAG_FUNCTION_COMPONENT) {
    updateFunctionComponent(currentFiber)
  }
}

function updateFunctionComponent (currentFiber) {
  // hook相关
  workInProgressFiber = currentFiber
  hookIndex = 0
  workInProgressFiber.hooks = []

  const newChildren = currentFiber.type(currentFiber.props)
  reconcileChildren(currentFiber, newChildren)
}

function updateClassComponent (currentFiber) {
  if (!currentFiber.stateNode) {
    // 类组件实例和fiber双向互有指针指向
    currentFiber.stateNode = new currentFiber.type(currentFiber.props)
    currentFiber.stateNode.internalFiber = currentFiber
    currentFiber.updateQueue = new UpdateQueue()
  }
  currentFiber.stateNode.state = currentFiber.updateQueue.forceUpdate(currentFiber.stateNode.state)
  let newElement = currentFiber.stateNode.render()
  const newChildren = [newElement]
  reconcileChildren(currentFiber, newChildren)
}

function createDOM (currentFiber) {
  if (currentFiber.tag === TAG_TEXT) {
    return document.createTextNode(currentFiber.props.text)
  } else if (currentFiber.tag === TAG_HOST) {
    let stateNode = document.createElement(currentFiber.type)
    updateDOM(stateNode, {}, currentFiber.props)
    return stateNode
  }
}

function updateDOM (stateNode, oldProps, newProps) {
  if (stateNode && stateNode.setAttribute) {
    setProps(stateNode, oldProps, newProps)
  }
}

function setProps (dom, oldProps, newProps) {
  for (let key in oldProps) {}
  for (let key in newProps) {
    if (key !== 'children') {
      setProp(dom, key, newProps[key])
    }
  }
}

function setProp (dom, key, value) {
  if (/^on/.test(key)) {
    // 没有用合成事件
    dom[key.toLowerCase()] = value
  } else if (key === 'style') {
    if (value) {
      for (let styleName in value) {
        dom.style[styleName] = value[styleName]
      }
    }
  } else {
    dom.setAttribute(key, value)
  }
}

function updateHostText (currentFiber) {
  if (!currentFiber.stateNode) {
    currentFiber.stateNode = createDOM(currentFiber)
  }
}

function updateHostRoot (currentFiber) {
  // 先处理自己，如果是原生DOM节点，创建真实DOM
  // 2、创建子fiber
  let newChildren = currentFiber.props.children
  reconcileChildren(currentFiber, newChildren)
}

function updateHost (currentFiber) {
  if (!currentFiber.stateNode) {
    currentFiber.stateNode = createDOM(currentFiber)
  }
  const newChildren = currentFiber.props.children
  reconcileChildren(currentFiber, newChildren)
}

function reconcileChildren (currentFiber, newChildren) {
  let newChildIndex = 0
  let oldFiber = currentFiber.alternate && currentFiber.alternate.child
  if (oldFiber) {
    oldFiber.firstEffect = oldFiber.lastEffect = oldFiber.nextEffect = null
  }
  let prevSibling
  while (newChildIndex < newChildren.length || oldFiber) {
    let newChild = newChildren[newChildIndex]
    let newFiber
    const sameType = oldFiber && newChild && oldFiber.type === newChild.type
    let tag
    if (newChild && typeof newChild.type === 'function' && newChild.type.prototype.isReactComponent) {
      tag = TAG_CLASS
    } else if (newChild && typeof newChild.type === 'function') {
      tag = TAG_FUNCTION_COMPONENT
    } else if (newChild && newChild.type === ELEMENT_TEXT) {
      tag = TAG_TEXT // 文本节点
    } else if (typeof newChild.type === 'string') {
      tag = TAG_HOST // 原生DOM节点
    }
    if (sameType) {
      if (oldFiber.alternate) {
        newFiber = oldFiber.alternate
        newFiber.props = newFiber.props
        newFiber.alternate = oldFiber
        newFiber.effectTag = UPDATE
        newFiber.updateQueue = oldFiber.updateQueue || new UpdateQueue()
        newFiber.nextEffect = null
      } else {
        newFiber = {
          tag: oldFiber.tag,
          type: oldFiber.type,
          props: newChild.props,
          stateNode: oldFiber.stateNode,
          return: currentFiber,
          alternate: oldFiber,
          effectTag: UPDATE,
          updateQueue: oldFiber.updateQueue || new UpdateQueue(),
          nextEffect: null,
        }
      }
    } else {
      if (newChild) {
        newFiber = {
          tag,
          type: newChild.type, //div
          props: newChild.props,
          stateNode: null, // div还没有创建DOM元素
          return: currentFiber,
          effectTag: PLACEMENT, // 副作用标识 PLACEMENT代表增加节点
          updateQueue: new UpdateQueue(),
          nextEffect: null, // effect list也是一个单链表，effect list顺序和完成顺序是一样的，但只放有副作用的节点
        }
      }
      if (oldFiber) {
        oldFiber.effectTag = DELETION
        deletions.push(oldFiber)
      }
    }
    if (oldFiber) {
      oldFiber = oldFiber.sibling
    }
    if (newFiber) {
      if (newChildIndex === 0) {
        currentFiber.child = newFiber
      } else {
        prevSibling.sibling = newFiber
      }
      prevSibling = newFiber
    }
    newChildIndex++
  }
}

function workLoop (deadline) {
  let shouldYield = false // 是否要让出时间片或执行控制权
  while (nextUnitOfWork && !shouldYield) {
    nextUnitOfWork = performUnitOfWork(nextUnitOfWork)
    shouldYield = deadline.timeRemaining() < 1
  }
  if (!nextUnitOfWork && workInProgressRoot) {
    console.log('render阶段结束')
    commitRoot()
  }
  requestIdleCallback(workLoop, { timeout: 500 })
}

function commitRoot () {
  // 执行effect list之前，把该删的元素都删掉
  deletions.forEach(commitWork)
  let currentFiber = workInProgressRoot.firstEffect
  while (currentFiber) {
    commitWork(currentFiber)
    currentFiber = currentFiber.nextEffect
  }
  deletions.length = 0
  currentRoot = workInProgressRoot
  workInProgressRoot = null
}

function commitWork (currentFiber) {
  if (!currentFiber) return
  let returnFiber = currentFiber.return
  while (returnFiber.tag !== TAG_HOST && returnFiber.tag !== TAG_ROOT && returnFiber.tag !== TAG_TEXT) {
    returnFiber = returnFiber.return
  }
  let domReturn = returnFiber.stateNode
  if (currentFiber.effectTag === PLACEMENT) {
    let nextFiber = currentFiber
    // 这个if判断加或不加都可以appendChild如果是append已经存在于父节点的DOM对象的话，会做移动操作
    if (nextFiber.tag === TAG_CLASS) {
      return
    }
    // 如果要挂载的节点不是DOM节点，比如是类组件的Fiber，一直找第一个儿子，直到找到真实DOM节点为止
    while (nextFiber.tag !== TAG_HOST && nextFiber.tag !== TAG_TEXT) {
      nextFiber = nextFiber.child
    }
    domReturn.appendChild(currentFiber.stateNode)
  } else if (currentFiber.effectTag === DELETION) {
    return commitDeletion(currentFiber, domReturn)
    // domReturn.removeChild(currentFiber.stateNode)
  } else if (currentFiber.effectTag === UPDATE) {
    if (currentFiber.type === ELEMENT_TEXT) {
      if (currentFiber.alternate.props.text !== currentFiber.props.text) {
        currentFiber.stateNode.textContent = currentFiber.props.text
      }
    } else {
      if (currentFiber.tag === TAG_CLASS) {
        return currentFiber.effectTag = null
      }
      updateDOM(currentFiber.stateNode, currentFiber.alternate.props, currentFiber.props)
    }
  }
  currentFiber.effectTag = null
}

function commitDeletion (currentFiber, domReturn) {
  if (currentFiber.tag === TAG_HOST || currentFiber.tag === TAG_TEXT) {
    domReturn.removeChild(currentFiber)
  } else {
    commitDeletion(currentFiber.child, domReturn)
  }
}

requestIdleCallback(workLoop, { timeout: 5000 })

// 总结：
// 1、在beginWork创建fiber
// 2、在completeUnitOfWork的时候收集effect






function reducer (state, action) {
  switch (action.type) {
    case 'ADD':
      return { count: state.count + 1 }
    default:
      return state
  }
}

// hook的使用
function FunctionCounter () {
  const [countState, dispatch] = React.useReducer(reducer, { count: 0 })
  return (
    <div>
      <span>{countState.count}</span>
      <button onClick={() => dispatch({ type: 'ADD' })}>加1</button>
    </div>
  )
}
ReactDOM.render(
  <FunctionCounter name="计数器"></FunctionCounter>,
  document.getElementById('root')
)

// ================================================================================
// useReducer的实现：
let workInProgressFiber = null
let hookIndex = 0

function useReducer (reducer, initialValue) {
  let newHook = workInProgressFiber.alternate &&
    workInProgressFiber.alternate.hooks &&
    workInProgressFiber.alternate.hooks[hookIndex]
  if (newHook) {
    // 第2次渲染
    newHook.state = newHook.updateQueue.forceUpdate(newHook.state)
  } else {
    newHook = {
      state: initialValue,
      updateQueue: new UpdateQueue()
    }
  }
  const dispatch = action => {
    let payload = reducer ? reducer(newHook.state, action) : action
    newHook.updateQueue.enqueueUpdate(
      new Update(payload)
    )
    scheduleRoot()
  }
  workInProgressFiber.hooks[hookIndex++] = newHook
  return [
    newHook.state, dispatch
  ]
}

function useState (initialValue) {
  return useReducer(null, initialValue)
}

// 为什么要引入fiber架构：
// js是单线程，如果深度递归虚拟dom树创建dom节点，执行流程会很长，阻塞UI进程的渲染


// 对fiber的操作分为遍历和完成两个阶段
// 遍历对应willMount hook
// 完成对应didMount hook
// fiber的遍历是深度优先
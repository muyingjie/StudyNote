// ref的3种用法：
// 不推荐使用
<p ref="stringRef">span1</p>

<p ref={ele => (this.methodRef = ele)}>span2</p>

this.objRef = React.createRef()
<p ref={this.objRef}>span3</p>

// render方法：
ReactDOM.render(element, container, callback)

class ReactDOM {
  render (element, container, callback) {
    return legacyRenderSubtreeIntoContainer(
      null,
      element,
      container,
      false,
      callback
    )
  }
}

function legacyRenderSubtreeIntoContainer (
  parentComponent, children, container, forceHydrate, callback
) {}

render(element, container, callback) ->
legacyRenderSubtreeIntoContainer(parentComponent, children, container, forceHydrate, callback) ->
  root = container._reactRootContainer = legacyCreateRootFromDomContainer(container, forceHydrate) ->
    return new ReactRoot(container, isConcurrent, shouldHydrate) ->
      this._internalRoot = createContainer(container, isConcurrent, hydrate) ->
        return createFiberRoot(containerInfo, isConcurrent, hydrate) ->
          var uninitializedFiber = createHostRootFiber(isConcurrent); uninitializedFiber.stateNode = root; return root; ->
            return createFiber(HostRoot, null, null, mode) ->
              return new FiberNode(tag, pendingProps, key, mode)
  unbatchedUpdates -> 
    if (parentComponent != null) root.legacy_renderSubtreeIntoContainer(parentComponent, children, callback) ->
      // work非核心流程，非重点
      work = new ReactWork()
      work.then(callback)
        if (this._didCommit) onCommit() return
        var callbacks = this._callbacks
        callbacks.push(onCommit)
      updateContainer(children, root, parentComponent, work._onCommit)
        currentTime = requestCurrentTime()
        expirationTime = computeExpirationForFiber(currentTime, container.current)
        return updateContainerAtExpirationTime(element, container, parentComponent, expirationTime, callback)
          // 大部分情况下context拿到的都是空对象
          context = getContextForSubtree(parentComponent)
            if (!parentComponent) return emptyContextObject;
            var fiber = get(parentComponent);
            var parentContext = findCurrentUnmaskedContext(fiber);
            if (fiber.tag === ClassComponent) {
              var Component = fiber.type;
              if (isContextProvider(Component)) {
                return processChildContext(fiber, Component, parentContext);
              }
            }
            return parentContext;
          if (container.context === null) container.context = context
          else container.pendingContext = context;
          return scheduleRootUpdate(current$$1, element, expirationTime, callback)
            update = createUpdate(expirationTime)
              return {
                expirationTime: expirationTime,
                tag: UpdateState,
                payload: null,
                callback: null,
                next: null,
                nextEffect: null
              };
            update.payload = { element: element }
            update.callback = callback
            flushPassiveEffects()
              scheduler.unstable_cancelCallback(passiveEffectCallbackHandle)
              passiveEffectCallback()
            enqueueUpdate(current$$1, update)
              if (alternate === null)
                queue1 = fiber.updateQueue;
                queue2 = null;
                if (queue1 === null)
                  queue1 = fiber.updateQueue = createUpdateQueue(fiber.memoizedState)
              else
                queue1 = fiber.updateQueue;
                queue2 = alternate.updateQueue;
                if (queue1 === null)
                  if (queue2 === null)
                    queue1 = fiber.updateQueue = createUpdateQueue(fiber.memoizedState);
                    queue2 = alternate.updateQueue = createUpdateQueue(alternate.memoizedState);
                  else
                    queue1 = fiber.updateQueue = cloneUpdateQueue(queue2);
                else
                  if (queue2 === null)
                    queue2 = alternate.updateQueue = cloneUpdateQueue(queue1);
                  else
                    // no operation
              if (queue2 === null || queue1 === queue2)
                appendUpdateToQueue(queue1, update)
              else
                if (queue1.lastUpdate === null || queue2.lastUpdate === null)
                  appendUpdateToQueue(queue1, update);
                  appendUpdateToQueue(queue2, update);
                else
                  appendUpdateToQueue(queue1, update);
                  queue2.lastUpdate = update;
            scheduleWork(current$$1, expirationTime)
            return expirationTime
    else root.render(children, callback)
      var root = this._internalRoot;
      var work = new ReactWork();
      work.then(callback);
      // 和上面的if分支逻辑一样
      updateContainer(children, root, null, work._onCommit);
return getPublicRootInstance(root._internalRoot)


// 在类组件初始化的时候，可以拿到一个名为ClassComponentUpdater的类，当我们的组件调用setState的时候，就会走到这个类的enqueueSetState方法
enqueueSetState
  var fiber = get(inst);
  var currentTime = requestCurrentTime();
  var expirationTime = computeExpirationForFiber(currentTime, fiber);

  var update = createUpdate(expirationTime);
  update.payload = payload;
  update.callback = callback;

  flushPassiveEffects();
  enqueueUpdate(fiber, update);
  scheduleWork(fiber, expirationTime);


// 第4章 Fiber Schedule
// 绝大多数代码都在react-reconciler/ReactFiberScheduler.js中
addRootToScheduler // 每个应用可能会有多个root，该方法可以维护多个root之间的关系

// 根据expirationTime是同步的，则调用performSyncWork立即进行调度，否则调用scheduleCallbackWithExpirationTime放入异步队列中进行调度
scheduleCallbackWithExpirationTime
scheduleDeferredCallback
add to callbackList
requestWork 最早的callback
animationTick记录浏览器帧时间
idleTick调度任务
当前时间和过期时间对比是否过期
如果没有过期，执行首个任务 deadline.didTimeout = false
如果过期了，执行任务 deadline.didTimeout = true
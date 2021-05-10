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
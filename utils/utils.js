// 为每个vue实例对应的dom对象添加属性，指向vue实例对象
function vmMapToDom(vm) {
  if(vm.$el.setAttribute) {
      var name = '$' + (vm.$options.name + vm._uid + '').replace(/-/g, '_');
      vm.$el.setAttribute('vm' + vm._uid, name)
      window[name] = vm
  }
  var l = vm.$children.length;
  var i = 0;
  while (i < l) {
      vmMapToDom(vm.$children[i]);
      i++;
  }
}

vmMapToDom(App)
class SyncHook {
  constructor (args) {
    this.tasks = []
  }
  tap (name, task) {
    this.tasks.push(task)
  }
  call (...args) {
    this.tasks.forEach(task => task(...args))
  }
}

let hook = new SyncHook(['name'])
hook.tap('react', function () {
  console.log('react', name)
})
hook.tap('node', function () {
  console.log('node', name)
})
hook.call()


// AsyncSeriesWaterfallHook
class AsyncSeriesWaterfallHook {
  constructor () {
    this.tasks = []
  }
  tapAsync () {
    this.tasks.push(task)
  }
  callAsync (...args) {
    let finalCallback = args.pop()
    let index = 0
    let next = (err, data) => {
      let task = this.tasks[index]
      if (!task) return finalCallback()
      index === 0 ? task(...args) : task(data, next)
      index++
    }
    next()
  }
}








// bin/zf-pack.js
let path = require('path')
let config = require(path.resolve('webpack.config.js'))
let Compiler = require('../lib/Compiler')
let compiler = new Compiler(config)
compiler.run()

// Compiler类
// lib/Compiler
let fs = require('fs')
let path = require('path')
let babylon = require('babylon')
let traverse = require('@babel/traverse').default
let t = require('@babel/types')
let generator = require('@babel/generator').default

// babylon 将源码转为ast
// @babel/traverse
// @babel/types
// @babel/generator

class Compiler {
  constructor (config) {
    this.config = config
    this.entryId
    this.modules = {}
    this.entry = config.entry
    // 工作路径
    this.root = process.cwd()
  }
  getSource () {
    let content = fs.readFileSync(modulePath, 'utf8')
    return content
  }
  parse (source, parentPath) {
    let ast = babylon.parse(source)
    let dependencies = []
    traverse(ast, {
      CallExpression (p) {
        let node = p.node
        if (node.callee.name === 'require') {
          node.callee.name = '__webpack_require__'
          let moduleName = node.arguments[0].value
          moduleName = moduleName + (path.extname(moduleName) ? '' : '.js')
          moduleName = './' + path.join(parentPath, moduleName)
          dependencies.push(moduleName)
          node.arguments = [t.stringLiteral(moduleName)]
        }
      }
    })
    let sourceCode = generator(ast).code
    return {
      sourceCode, dependencies
    }
  }
  buildModule (modulePath, isEntry) {
    let source = this.getSource(modulePath)
    // modulePath在this.root下的相对路径
    let moduleName = './' + path.relative(this.root, modulePath)
    if (isEntry) {
      this.entryId = moduleName
    }
    let {sourceCode, dependencies} = this.parse(source, path.dirname(moduleName))
    this.modules[moduleName] = sourceCode
    dependencies.forEach(dep => {
      this.buildModule(path.join(this.root, dep), false)
    })
  }
  emitFile () {}
  run () {
    this.buildModule(path.resolve(this.root, this.entry), true)
    this.emitFile()
  }
}
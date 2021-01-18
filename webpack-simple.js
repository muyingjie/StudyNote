let fs = require('fs')
let path = require('path')

let config = require(path.resolve('webpack.config.js'))

class Compiler {
  constructor (config) {
    this.config = config
    this.entryId
    this.modules = {}
    this.entry = config.entry
    // 工作路径
    this.root = process.cwd() 
  }
  buildModule (modulePath, isEntry) {
    let source = this.getSource(modulePath)
    let moduleName = './' + path.relative(this.root, modulePath)
    if (isEntry) {
      this.entryId = moduleName
    }
    let {sourceCode, dependencies} = this.parse(source, path.dirname())
    this.modules[moduleName] = sourceCode
  }
  parse (source, parentPath) {
    
  }
  getSource (modulePath) {
    let content = fs.readFileSync(modulePath, 'utf8')
    return content
  }
  emitFile () {}
  run () {
    this.buildModule(path.resolve(this.root, this.entry), true)
    this.emitFile()
  }
}

let compiler = new Compiler(config)

compiler.run()
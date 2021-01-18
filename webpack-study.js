// AsyncSeriesHook
// 异步串行队列，发布时这样执行：
function callAsync (...args) {
  // 等队列中所有的函数都执行完时调用finalCallback
  let finalCallback = args.pop()
  let index = 0
  let next = _ => {
    if (index === this.length) return finalCallback()
    let task = tasks[index++]
    task(...args, next)
  }
  next()
}

// express中app.use的原理和上述逻辑类似

// promise队列按顺序调用
function callPromise (...args) {
  let [first, ...others] = args
  others.reduce((p, n) => {
    return p.then(_ => n(...args))
  }, first(...args))
}

// redux的原理和上述逻辑类似

// Hook钩子主要分布于

// 查找
// grep "entryOption" -rn ./node_modules/webpack

JSON.stringify(this.hooks.thisCompilation.taps.map(o => o.name))
["JsonpTemplatePlugin","FetchCompileWasmTemplatePlugin","SplitChunksPlugin","CachePlugin"]

JSON.stringify(this.hooks.compilation.taps.map(o => o.name))
[
  "HotModuleReplacementPlugin",
  "HtmlWebpackPluginHooks",
  "FunctionModulePlugin",
  "NodeSourcePlugin",
  "LoaderTargetPlugin",
  "SourceMapDevToolPlugin",
  "JavascriptModulesPlugin",
  "JsonModulesPlugin",
  "WebAssemblyModulesPlugin",
  "SingleEntryPlugin",
  "CompatibilityPlugin",
  "HarmonyModulesPlugin",
  "AMDPlugin",
  "RequireJsStuffPlugin",
  "CommonJsPlugin",
  "LoaderPlugin",
  "LoaderPlugin",
  "NodeStuffPlugin",
  "APIPlugin",
  "ConstPlugin",
  "UseStrictPlugin",
  "RequireIncludePlugin",
  "RequireEnsurePlugin",
  "RequireContextPlugin",
  "ImportPlugin",
  "SystemPlugin",
  "EnsureChunkConditionsPlugin",
  "RemoveParentModulesPlugin",
  "RemoveEmptyChunksPlugin",
  "MergeDuplicateChunksPlugin",
  "FlagDependencyExportsPlugin",
  "NamedModulesPlugin",
  "OccurrenceOrderChunkIdsPlugin",
  "NamedChunksPlugin",
  "DefinePlugin",
  "TemplatedPathPlugin",
  "RecordIdsPlugin",
  "WarnCaseSensitiveModulesPlugin"
]

[
  "AMDPlugin",
  "APIPlugin",
  "CommonJsPlugin",
  "CompatibilityPlugin",
  "ConstPlugin",
  "DefinePlugin",
  "EnsureChunkConditionsPlugin",
  "FlagDependencyExportsPlugin",
  "FunctionModulePlugin",
  "HarmonyModulesPlugin",
  "HotModuleReplacementPlugin",
  "HtmlWebpackPluginHooks",
  "ImportPlugin",
  "JavascriptModulesPlugin",
  "JsonModulesPlugin",
  "LoaderPlugin",
  "LoaderPlugin",
  "LoaderTargetPlugin",
  "MergeDuplicateChunksPlugin",
  "NamedChunksPlugin",
  "NamedModulesPlugin",
  "NodeSourcePlugin",
  "NodeStuffPlugin",
  "OccurrenceOrderChunkIdsPlugin",
  "RecordIdsPlugin",
  "RemoveEmptyChunksPlugin",
  "RemoveParentModulesPlugin",
  "RequireContextPlugin",
  "RequireEnsurePlugin",
  "RequireIncludePlugin",
  "RequireJsStuffPlugin",
  "SingleEntryPlugin",
  "SourceMapDevToolPlugin",
  "SystemPlugin",
  "TemplatedPathPlugin",
  "UseStrictPlugin",
  "WarnCaseSensitiveModulesPlugin",
  "WebAssemblyModulesPlugin"
]

JSON.stringify(this.hooks.make.taps.map(o => o.name))
["HtmlWebpackPlugin","SingleEntryPlugin"]

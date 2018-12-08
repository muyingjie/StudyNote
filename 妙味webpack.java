npm install react --save-dev //保存到package.json文件中

webpack是一款模块管理兼打包工具

把各种资源（js css less sass）等都当做模块

webpack app/index.js build/build.js //参数分别代表源和目标js

可以通过require的方式引用模块
var str=require("./app.js");
document.body.innerHTML = "111";

也可以通过define和回调的方式引用模块
define(["./app.js"], function(str){
	document.body.innerHTML = str;
});

如果要用webpack处理css，需要安装css-loader style-loader
npm install css-loader style-loader --save-dev

//处理css模块的方式：
require("style!css!./css/reset.css");
require("style!css!./css/style.css");

css文件中如果需要依赖其他css模块，可以通过@import导入
@import "./reset.css";
div{
	border: 2px solid #000;
}

// 当拥有webpack.config.js文件之后
// webpack.config.js
module.exports = {
	//入口
	entry: "./app/index.js", //如果想要打包生成两个文件这里应写成对象的形式，output里面的filename也需要修改为"[name].js"，这里面的name是一个变量，值entry里面每一项的key的值，也就是说该key值将来会成为文件名
	//输出
	output: {
		path: "./build/",
		filename: "build.js"
	},
	module: {
		loaders: [
			{
				test: /.css$/,
				loader: ["style","css"], //在此配置了之后在index.js入口文件中的require不必再写成style!css!这样的形式
				exclude: "/node_modules/" //排除的部分
			}
		]
	},
	//在此配置了之后必须在package.json中也写成--hot --inline
	devServer: {
		hot: true,
		inline: true
	},
	//添加resolve之后入口文件中require里面就不必写扩展名了
	resolve: {
		extensions: ["", ".js", ".css", ".jsx"]
	},
	plugins: [
		new htmlWebpackPlugin({
			title: "my first react app",
			chunks: ["build"], //当entry为对象类型时chunks里面放此处生成html所依赖的js文件
			filename: "class.html" //构建后生成的html文件名，不写的话默认为index.html
		})
	]
};

修改文件源码后，自动刷新页面把修改同步到页面上：
安装webpack-dev-server模块：
npm install webpack-dev-server
安装后在命令行中使用webpack-dev-server命令
webpack-dev-server --hot --inline

webpack-dev-server --port 10000 端口冲突时可以改变端口号

webpack-dev-server --port 10000 --hot --inline这条指令可以配置在package.json的scripts builds中

测试时需要把所有打包好的资源放在一个index.html文件中，为了避免每次需要新建一个index.html文件，可以安装html-webpack-plugin插件
npm install html-webpack-plugin --save-dev

使用时可以在webpack.config.js中引入
var htmlWebpackPlugin = require("html-webpack-plugin");
并在plugins中配置

这样一来只需要npm run build即可完成所有工作

package.json中scripts里面还可以配置webpack的配置文件：
{
	build: "webpack.config.js",
	start_html: "webpack.html.config.js",
	
}
构建的时候如果执行npm run start_html，则运行webpack.html.config.js配置文件
构建的时候如果执行npm run build，则运行webpack.config.js配置文件


===============================================
webpack实用性基础教程
加载css时不仅需要使用css-loader，还需要加上style-loader，style-loader会将css以style标签的形式插入html结构中
例如webpack做了如下配置之后
module: {
	rules: [
		{
			test: /\.css$/,
			use: ['style-loader', 'css-loader']
		}
	]
}
代码中
import './main.css'; 就会将main.css插入到html中

css-loader会处理css文件中出现的url，自动引入里面要引入的模块
file-loader：1、把资源移动到输出目录 2、返回最终引入资源的url




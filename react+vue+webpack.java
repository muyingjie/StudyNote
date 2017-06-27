react
用到3个js
react.js
react-dom.js
browser.js //解析JSX语法

<script type="text/babel">
	var StudentApp = React.createClass({
		render: function(){
			// 注意一个组件里面只能有一个顶级标签，此外像input这样的标签需要闭合
			// class需要改成className
			return (
				<div>
					<h1 className="list">学生信息表</h1>
					<input type="text" value="aaa" />
				</div>
			);
		}
	});
	// 将StudentApp组件插入到id为studentInfo的元素中
	ReactDOM.render(<StudentApp />, document.getElementById("studentInfo"));
</script>



































url分析：xxx.php?mod=xxx&action=xxx&params=xxx

mod相当于Controller，action相当于Controller中的方法，params相当于参数

新建模板：
1、template下建立自定义目录
2、复制默认模板xml配置文件，重命名为discuz_style_xxx.xml
3、修改以下4个参数
	<item id="name"> 后台管理系统中显示的模板的名字
	<item id="templateid"> 模板id
	<item id="tplname"> 对模板的描述
	<item id="directory">
4、在自定义目录下新建common目录
5、在common中新建自定义的header.html footer.html以及css

建css文件时以extend_开头，无需手动引入
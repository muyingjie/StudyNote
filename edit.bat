::https://wenku.baidu.com/view/371957c06137ee06eff91819.html
::bat命令执行完之后不仅打印出执行结果，默认还会把执行的命令显示一遍
::@符号的作用就是不显示执行的命令
::echo off相当于在每条命令前加一个@符号，实际上echo后面跟空格加字符串就相当于输出该字符串，而echo后面加off代表关闭输出
::@echo off意思是不显示echo off本身
::pause 为防止批处理执行完后立即退出，添加pause命令
::和::一样，rem也是注释命令

::goto :xxx相当于给批处理记了一个标号，可以通过goto xxx来跳到该编号处的指令执行
@echo aaa
echo aaa
@pause
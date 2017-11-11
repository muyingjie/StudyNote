::https://wenku.baidu.com/view/371957c06137ee06eff91819.html
::http://blog.csdn.net/lanbing510/article/details/7461073
::bat命令执行完之后不仅打印出执行结果，默认还会把执行的命令显示一遍
::@符号的作用就是不显示执行的命令
::echo off相当于在每条命令前加一个@符号，实际上echo后面跟空格加字符串就相当于输出该字符串，而echo后面加off代表关闭输出
::@echo off意思是不显示echo off本身
::echo. 意思是输出一个空行，命令行中的“．”要紧跟在ECHO后面中间不能有空格，否则“．”将被当作提示信息输出到屏幕。另外“．”可以用，：；”／[\]＋等任一符号替代。
::pause 为防止批处理执行完后立即退出，添加pause命令，执行pause指令之后会显示Press any key to continue...
::和::一样，rem也是注释命令，但是::不论是否用echo on打开命令行回显状态，始终不会回显，而rem在echo on打开时是可以回显的

::goto 指令中:xxx相当于给批处理记了一个标号，可以通过goto xxx来跳到该编号处的指令执行
::call 2.bat执行2.bat批处理文件，执行完之后再往下走
::IF判断格式：IF [NOT] ERRORLEVEL number
::IF ERRORLEVEL这个句子必须放在某个命令的后面，执行命令后由IF ERRORLEVEL来判断命令的返回值
::例如:

echo off
dir z:
rem 如果执行dir z:返回1则跳转到标号为1处执行
IF ERRORLEVEL 1 goto 1
IF ERRORLEVEL 0 goto 0
echo 命令执行成功
goto exit
// :后面跟一个字母开头，字母数字组成的标号，该标号才可被goto识别，而且冒号所在行的标号后面的内容都会被忽略
:1
echo 命令执行失败
goto exit
:exit

::errorlevel不仅代表错误码，还代表指令执行的返回码
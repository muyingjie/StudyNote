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

::errorlevel不仅代表错误码，还代表指令执行的返回码，默认值为0，一般命令执行出错会设 errorlevel 为1

ECHO @ECHO OFF>AUTOEXEC.BAT 建立自动批处理文件
ECHO C:\CPAV\BOOTSAFE>>AUTOEXEC.BAT 向自动批处理文件中追加内容

PAUSE：停止系统命令的执行并显示请按任意键继续. . .

find 在文件中搜索字符串
FIND [/V] [/C] [/N] [/I] [/OFF[LINE]] "string" [[drive:][path]filename[ ...]]
  /V        显示所有未包含指定字符串的行。
  /C        仅显示包含字符串的行数。
  /N        显示行号。
  /I        搜索字符串时忽略大小写。
  /OFF[LINE] 不要跳过具有脱机属性集的文件。
  "string"  指定要搜索的文字串，
  [drive:][path]filename 指定要搜索的文件。

Find常和type命令结合使用
Type [drive:][path]filename | find "string" [>tmpfile] #挑选包含string的行
Type [drive:][path]filename | find /v "string"   #剔除文件中包含string的行
Type [drive:][path]filename | find /c   #显示文件行数

例：type test.txt|find "111" 



start 调用外部程序并任其在新窗口自行运行
例：start explorer d:\


assoc 和 ftype
assoc 获取或设置'文件扩展名'关联，关联到'文件类型'
ftype 获取或设置'文件类型'关联，关联到'执行程序和参数'

当你双击一个.txt文件时，windows并不是根据.txt直接判断用 notepad.exe 打开
而是先调用assoc .txt找到其文件类型为txtfile
再调用 txtfile 关联的命令行 txtfile=%SystemRoot%\system32\NOTEPAD.EXE %1
可以在"文件夹选项"→"文件类型"里修改这2种关联

ftype           #显示所有'文件类型'关联
ftype exefile   #显示exefile类型关联的命令行，结果显示 exefile="%1" %*


assoc .txt=Word.Document.8 设置.txt为word类型的文档，可以看到.txt文件的图标都变了
assoc .txt=txtfile 恢复.txt的正确关联

CALL命令可以在批处理执行过程中调用另一个批处理，当另一个批处理执行完后，再继续执行原来的批处理

CALL [drive:][path]filename [batch-parameters]
调用的其它批处理程序。filename 参数必须具有 .bat 或 .cmd 扩展名。

CALL :label arguments
调用本文件内命令段，相当于子程序。被调用的命令段以标签:label开头
以命令goto :eof结尾。






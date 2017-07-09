Linux
Ctrl+P、Ctrl+N作用相当于↑↓方向键的作用
Prev	Next
Ctrl+B、Ctrl+F作用相当于←→方向键的作用
Back	Forward
Ctrl+A、Ctrl+E作用是将光标移动到指令最开始、最末尾
		End

光标盖住的字符是光标后的字符
Ctrl+H删除光标前字符
Ctrl+D删除光标后字符

Ctrl+U删除光标之前所有字符

Linux系统目录结构
/
/bin: 经常使用的命令
/dev: 设备文件
/etc: 存放所有的系统管理所需要的配置文件
/home: 该目录下每个用户一个目录
/lib: 动态链接库 动态库又称为共享库 类似于Windows下的dll
/media: Linux系统会自动识别一些设备，例如U盘、光驱等，当识别后，Linux会把识别的设备挂载到这个目录下
/mnt: 系统提供该目录是为了让用户临时挂载别的文件系统的，只有把U盘或光盘挂载到Linux的时候/mnt才会有东西
/root: 超管的目录
/usr: 用户的很多应用程序和文件都放在这个目录下，类似Windows下的program files
/boot: 启动Linux时使用的一些核心文件
/lost+found: 文件碎片，意外关机时一些配置文件会存放在这里面
/sbin: 管理员使用的命令


用户目录：
绝对路径
相对路径

切换到root用户之后可以通过exit退到普通用户

cd不跟任何参数是进入当前用户的家目录

tree 1Day
将1Day目录下所有的文件以树状的方式展示 tree命令需要通过apt-get联网安装
展示出来的结构中
白色	普通文件		stdio.h
蓝色	目录
绿色	可执行文件
红色	压缩包
青色	链接文件
黄色	设备文件
				block	块
				char	字符	键盘
				fifo	管道
灰色	其他文件

ls -a才能看到所有文件

回到家目录：
	cd /home/aaa
	cd ~
	cd

mkdir dir/dir1/dir2 -p 递归创建目录必须加参数-p

rmdir lover //删除空目录 不实用，用的很少

rm aa -r //-r参数代表递归删除

rm aa -ri //-i参数会在删除aa目录下的每个文件时都会提示是否要删除

rm即使删除空目录也要加参数-r

touch Luffy //创建文件

touch一个存在的文件会修改文件的最后修改时间

rm Luffy //不需要参数，可以直接删

rm Luffy -i//会提示是否删除该文件

cp hello.c temp//将hello.c中的内容拷贝到temp文件中，如果temp文件没有，则会创建，如果temp文件存在，将会把里面的内容覆盖掉

cp mytest/ newDir -r //递归的将mytest目录里面所有的内容拷贝到newDir中，newDir不存在时会新建这样一个目录，目录下新建的内容和mytest目录下的内容是一样的，newDir存在时会在newDir下新建一个mytest目录，这个mytest目录中将会把原来的内容都拷贝进来

查看文件内容的5中方式 了解为主
cat changhenge //但是如果文件比较长，可能显示不全

more changhenge //先显示一部分，可以按回车键向后翻一行 空格键可以翻一屏 但是只能往后翻 不能往前翻 按q键或者Ctrl+c可退出

less changhenge //可以按回车键向后翻一行 空格键可以翻一屏 Ctrl+B往前翻页  Ctrl+F往后翻页 Ctrl+N往后翻一行 Ctrl+P往前翻一行

head changhenge -5 //查看文件中前5行

tail changhenge -20 //查看文件中后20行

mv laowang laowang2 //如果laowang2不存在，则将文件laowang改名为laowang2

mv laowang2 test //如果test存在 则将laowang2移动到test目录中

ln -s hello.c hello.soft //创建hello.c的快捷方式hello.soft文件 ln是软链接 即快捷方式

此时如果将hello.c移动到别的目录，则cat hello.soft会提示没有那个文件或目录

ln -s ~/1Day/hello.c hello.soft //创建了一个hello.soft软链接文件，不论hello.c移动到哪里，cat hello.soft就可以看到hello.c的内容了

给目录创建软链接也是可以的

ln hello.c hello.hard //创建硬链接文件hello.hard，ls查看文件列表可以发现hello.c和hello.hard所占空间大小一样，而且文件名颜色也一样，表面上看起来好像是将hello.c复制了一份出来，但实际上不是

硬链接相当于给原来的文件做了一个备份

-rw-rw-r-- 2 itcast itcast 129 5月 28 11:30 hello.c
-rw-rw-r-- 2 itcast itcast 129 5月 28 11:30 hello.hard

第二列的2就代表硬链接数，因为已经创建了一个hello.hard硬链接了，再加上hello.c，所以硬链接数就是2

Linux文件系统的存储单位是块
Linux在磁盘上寻找文件的时候通过iNode节点来查找
每个文件都对应一个iNode节点

文件所有硬链接都对应一个iNode节点

可以为硬链接创建软链接

目录不可以创建硬链接

文件或目录属性 非重点
wc aaa //获取文本文件的信息 行数 单词个数(以空格为准) 字节数 文件名
od -tx bbb //获取二进制文件的信息 -t用来指定数据的显示格式 x是-t的参数，代表用16进制的形式展示
du -h ccc //查看当前目录的大小
df //磁盘使用情况

which ls //得到/bin/ls 代表shell命令解析器在/bin/ls下找到了该命令

which查不到内建命令，只能查到外部命令，例如which cd就不会返回任何结果

whoami //当前登录用户是谁

修改文件权限
文字设定法
	chmod [who] [+|-|=] [mode]
		who 有四种 文件所有者u 文件所属组g 其他人o 所有的人a 默认为a
		+ 增加权限
		- 减少权限
		= 覆盖原来的权限
		mode 有三种 读r 写w 执行x

	chmod o+w temp //为temp文件增加写权限
数字设定法
	- 没有权限
	r 4
	w 2
	x 1
	765
		7 rwx 文件所有者
		6 rw- 文件所属组
		5 r-x 其他人
	chmod 755 temp //将temp目录/文件所有者的权限改为7(所有者 rwx) 6(所属组 rw-) 5(其他人 r-x)

	chmod -001 temp //将temp目录/文件其他人的执行权限(1)去掉
	
修改所有者和所属组
文件所有者可能并不是该文件所属组里面的成员
sudo chown zhangsan temp //将temp所有者改为zhangsan
sudo chown Luffy:lisi temp //将temp所有者改为Luffy，所属组改为lisi
sudo chgrp itcast temp //将temp所属组改为itcast

目录必须有执行权限，否则目录都不能进去

查找和检索
按文件属性查找
	文件名 find 查找目录 -name "文件的名字" 
		sudo find /home/itcast -name "itcast" //在/home/itcast目录下查找itcast文件
		sudo find /home/itcast -name "hel*" //以hel开头的文件 *代表多个字符 ?代表一个字符
	文件大小
		sudo find /home/itcast -size +10k //大小大于10k的 注意单位k是小写 但是单位兆是大写的M
		sudo find /home/itcast -size +10M -size -100M //大小在10-100M之间的文件
	文件类型
		sudo find /home/itcast -type f //搜索普通文件类型

按文件内容查找
	grep -r "查找的内容" 查找的路径
	grep -r "stdio.h" / 查找根目录下文件中带有stdio.h的文件

	
软件安装与卸载
方法1：在线安装==========================
Ubuntu下在线安装需要用apt-get	sudo apt-get install tree
centos下在线安装需要用yum		sudo yum install tree
用该指令会下载到一个指定的目录中：/var/cache/apt/archives目录下
移除：sudo apt-get remove tree
更新软件列表：sudo apt-get update //注意是更新软件列表，而不是软件，软件列表中存储着软件和它对应的下载地址
清理所有软件安装包：sudo apt-get clean //实际清理的是/var/cache/apt/archives目录下的.deb文件 Ubuntu下的安装包后缀名就是deb

aptitude也是下载工具 apt-get和aptitude都是Ubuntu系统自带的两个包管理工具
sudo aptitude install tree
sudo aptitude reinstall tree
sudo aptitude update
sudo aptitude remove tree
sudo aptitude show tree

方法2：deb包安装=====================
sudo dpkg -i sublime-amd64.deb //在Ubuntu中安装sublime软件
sudo dpkg -r sublime-amd64.deb //在Ubuntu中卸载sublime软件

方法3：源码安装=======================
1、解压缩源代码包
2、进入到安装目录：cd dir
3、检测文件是否缺失，创建Makefile，检测编译环境：./configure
4、编译源码，生成库和可执行程序：执行make命令，执行编译过程
5、把库和可执行程序安装到系统目录下：sudo make install
6、删除和卸载软件：sudo make distclean
7、上述安装步骤并不是绝对的，应该先查看附带的README文件

例如现在假如有一个安装包解压缩到了json-c-0.12文件夹，该文件夹里面会有一个configure可执行文件
命令行中执行该configure文件：./configure 执行的过程中会做一些配置，有时在安装文档中会提示在配置的过程中带一些参数

磁盘管理
itcast@itcast:/media$ ls
floppy floppy0 itcast

floppy是一个快捷方式，即软链接文件，指向floppy0目录

虚拟机全屏之后才可以把U盘挂载到虚拟机上

umount /media/itcast/ESD-USB //卸载掉挂载到Linux上的ESD-USB U盘

将U盘挂载到mnt目录下
sudo fdisk -l //查看设备信息 会找到/dev/sdb1 这就是U盘
sudo mount /dev/sdb1 /mnt //将U盘挂载到/mnt下

挂载完了之后就可以复制U盘上的东西了

sudo umount /mnt //卸载U盘，会报设备忙，需要在非mnt目录下执行该命令就可以避免该错误

如果不挂载到mnt目录下而是挂载到家目录里面的话，例如
sudo mount /dev/sdb1 /home/itcast/sort
sort里面的内容会被临时覆盖掉
当把U盘卸载掉之后里面的内容就又有了

因此可以找一个空目录去挂载

压缩包管理
gzip .gz格式的压缩包
gzip *.txt //将后缀为txt的文件压缩，但是并没有把所有的*.txt打包到一个文件里面，而且原来的文件都会被替换
gunzip *.gz会解压所有的gz文件
bzip2 .bz2格式的压缩包
bzip2 *.txt //缺点和gzip是一样的
bunzip *.bz2会解压所有的bz2文件

bzip2和gzip都不可以压缩目录

bzip2 -k *.txt //加了-k参数之后可以保留原来的文件

bzip2和gzip不能满足我们的要求

tar
	c 创建 压缩
	x 释放 解压
	v 显示提示信息 可以省略
	f 指定压缩文件的名字
	z 使用gzip的方式压缩文件，后缀将成为.gz
	j 使用bzip2的方式压缩文件，后缀将成为.bz2
	c和x不能同时使用
	z和j不能同时使用
	不使用z/j参数的话只能对文件或目录打包

tar zcvf xxx.tar.gz xxx //用gzip的方式把xxx压缩成xxx.tar.gz
tar jcvf xxx.tar.bz2 xxx //用bzip2的方式把xxx压缩成xxx.tar.bz2
//带上gz和bz2后缀的原因是为了在解压的时候可以知道用什么指令解压

tar jcvf animal.tar.bz2 animal/ *.txt //将animal下所有内容和当前目录下的txt文件都压缩到animal.tar.bz2压缩文件中

tar可以压缩目录 tar内部使用的还是bzip2和gzip

解压缩
tar jxvf xxx.tar.bz2 //将xxx.tar.bz2解压到当前目录

tar jxvf xxx.tar.bz2 -C test //将xxx.tar.bz2解压缩到test目录中 注意参数是大写的C

rar -- 需要用户手动安装
参数：
	压缩：a
	解压：x
压缩：
	rar a 生成的压缩文件的名字(不必手动写rar) 压缩的文件或目录
解压：
	rar x 压缩的文件名 解压缩的目录(非必写)

zip -- 
压缩：
	zip 生成的压缩包的名字(无需指定后缀) 压缩的文件或目录
	zip如果不加参数，压缩目录时不会自动递归压缩，需要加上参数-r才可以
解压：
	unzip 压缩包的名字
	如果要指定解压目录的话，需要加上参数-d，unzip all.zip -d ~

总结：
相同之处：
	语法相似：
		压缩：tar/rar/zip 参数 生成的压缩文件的名字 要压缩的文件或目录
		解压：tar/rar/zip 参数 压缩包的名字 参数(rar没有参数) 解压缩目录

		
进程、用户、网络管理
一个应用程序启动后就叫进程

who查看当前在线用户的状况
itcast	:0		2016-05-29 01:57 (:0)
itcast	pts/0	2016-05-29 08:46 (:0)
itcast	pts/14	2016-05-29 08:46 (192.168.40.115)
itcast	pts/26	2016-05-29 08:46 (192.168.40.132)

第一行的:0在不同的版本下显示的内容可能不一样，12.04和16.04版本显示为tty7，只有14.04版本显示为:0

Linux下有6个文字终端，1个图形界面终端

tty7的意思是Linux操作系统中的桌面终端

tty1-tty6是Linux的文字终端

ctrl+alt+F1-F7

pts/0 pts/14 pts/26都是设备终端，Linux中我们每打开一个终端就对应一个pts pts/后面的数字代表终端编号

//查看进程的命令
ps a //查看当前操作系统下所有用户
ps au //在a的基础上显示一些用户的信息，其中PID列代表进程id，tty一列代表终端
ps aux //查看没有终端的应用程序 tty一列的值为？的代表没有终端 没有终端就不需要终端也就不需要与用户交互

由于ps aux得到很多信息，因此需要通过管道来过滤，管道对应的类型时是类型
指令1|指令2 将指令1的输出作为指令2的输入
ps aux|grep bash //查找ps aux结果中带有bash的结果，注意利用grep查找时最后一项是grep指令占用的进程，因此如果执行ps aux|grep bash如果得到一条记录，则证明没有找到结果

kill -SIGKILL 5179 //杀死进程

env //查看当前进程的环境变量
Linux下环境变量的格式：key=value value可以有多个，每个之间通过冒号:分割
因此我们如果自己要给环境变量添加值，要注意用逗号分割

top //基本没用 相当于Windows中的任务管理器

网络相关命令
ifconfig

ping 192.168.40.115 -c 4 //发4个包，count
参数还可以用-i来表示多少秒

nslookup //查看域名对应的ip

用户管理
用户添加
adduser //用法简单，实际上是个脚本，不是命令
sudo adduser zhangsan
除了添加用户zhangsan之外，还添加了一个用户组，名字默认也叫zhangsan

sudo adduser ZhangSan //报错，用户名不能包含大写字母

如果需要包含大写，需要用useradd指令

sudo useradd -s /bin/bash -g Robin -d /home/Robin -m Robin
//-s 代表使用什么类型的命令解析器，通常值为/bin/bash
//-g 创建用户所属的组
//-d 指定用户家目录
//-m 如果家目录下没有Robin目录，就创建一个

如果仅执行sudo useradd -s /bin/bash -g Robin -d /home/Robin -m Robin这条指令，会提示Robin组不存在，因此我们还需要创建一个用户组
sudo group Robin

创建了用户之后需要给该用户指定密码
sudo passwd Robin

修改当前用户的密码
passwd

sudo passwd //不加参数代表修改root用户的密码

切换用户：su zhangsan

sudo deluser zhangsan //删除用户 但是zhangsan对应的家目录还在

sudo userdel -r zhangsan //删除用户 并且删除其家目录

查看当前Linux下有哪些用户：
vi /etc/passwd

ftp软件在安装的时候既可以当做服务器端，又可以当做客户端
ftp用户的家目录为 /srv/ftp
ftp服务器搭建(负责文件的上传和下载)：vsftpd
	服务器端
		修改配置文件：配置用户能够执行哪些操作，哪些可以上传，哪些可以下载
		//配置文件在 /etc/vsftpd.conf
			anonymous_enable=NO
			local_enable=YES //是否允许本地用户登录
			write_enable=YES //实名登录用户拥有写权限(上传数据)
			local_umask=022 //设置本地掩码为022
			anon_upload_enable=YES //匿名用户可以向ftp服务器上传数据
			anon_mkdir_write_enable=YES //匿名用户可以在ftp上创建目录
			
			//注意：配置文件中key和value之间的等号两边一定不能加空格
			
		重启服务：
			//配置文件改完之后需要重启服务器:sudo service vsftpd restart
	客户端
		实名用户登录
			ftp + IP(server) 例如：ftp 192.168.40.119 //登录之后是ftp服务器所处的家目录
			输入用户名
			输入密码
			文件的上传和下载：
				上传：put file
				下载：get file
				ftp服务器不允许操作目录，只允许操作文件，想操作目录的话只能打包
		匿名用户登录
			ftp + serverIP 
			用户名：anonymous 固定
			密码：直接回车 不用输入
			不允许匿名用户在任意目录之间切换，只能在一个指定的目录范围内工作，需要在ftp服务器上创建一个匿名用户的目录——匿名用户的根目录
			//在/etc/vsftpd.conf添加下面这行配置
			anon_root=/home/zhangsan/MyFtp/
			匿名用户将只能访问到/home/zhangsan/MyFtp/
			退出 quit bye exit
		lftp客户端访问ftp服务器
			lftd就是一个ftp客户端工具，可以上传和下载目录
			软件安装：sudo apt-get install lftp
			登录服务器：
				匿名：lftp 服务器IP
					login
				实名：lftp username@127.0.0.1
					输入服务器密码
					
			lpwd还可以查看匿名用户目录所处的服务器的目录
				
			lcd /home/itcast/1Day 将本地目录变成/home/itcast/1Day
			
			mput 可以同时上传多个文件
			mirror -R Lucy/ 上传Lucy目录
			mirror laowang 下载laowang目录
			mget 下载多个文件

nfs服务器的使用(作用是共享文件夹)
	net file system->网络文件系统，允许网络中的计算机之间通过TCP/IP网络共享资源

	安装：sudo apt-get install nfs-kernel-server

	创建一个欲共享出去的目录，如：/home/xxx/xxx

	打开配置文件：sudo vi /etc/exports

	服务器端：
		创建共享目录：
		修改配置文件：/etc/exports
			新增一行 /home/Robin/NfsShare *(rw, sync) 
			//*其实代表一个IP网段，正规的做法应该是/home/Robin/NfsShare 192.168.40.* 但是有些版本的Linux会出问题，因此我们通常写成*
			//*号后面的括号是权限列表 sync是把实时将内存中的数据更新到磁盘上
		重启服务：sudo service nfs-kernel-server restart

	客户端：
		挂载服务器共享目录
		mount 服务器IP:共享目录
		mount 192.168.1.40:/home/Robin/NfsShare /mnt

ssh服务器
	服务器端：
		安装ssh sudo apt-get install openssh-server
		查看ssh是否安装：sudo aptitude show openssh-server
	客户端：
		远程登录：
			ssh 用户名@IP 确认连接的时候一定要写yes/no 不能写y/n
			例如：服务器管理员通过ssh远程登录外地主机，进行维护
		退出登录
			logout

scp命令(cp只能在当前主机各个目录上拷贝，scp可以在各个主机之间拷贝)
	该命令需要安装openssh-server
	scp -r 目标用户名@目标主机IP地址:/目标文件绝对路径 /保存到本机的绝对(相对)路径

终端翻页
	shift + pageup -> 上翻页
	shift + pagedown -> 下翻页
	
清屏
	clear
	Ctrl + l
	
创建终端
	Ctrl + alt + T (Ubuntu)
	Ctrl + shift + T (添加新标签页)
	
查看帮助文档 man man
	1、可执行程序或shell命令
	2、系统调用 内核提供的函数
	3、库调用 程序库中的函数
	4、特殊文件 通常位于/dev
	5、文件格式和规范，如/etc/passwd
	6、游戏
	7、杂项 包括宏包和规范
	8、系统管理命令
	9、内核例程
	
alias //查看别名 查看命令是否被封装了
	alias ls
	//返回结果为alias ls='ls --color=auto'
	//证明我们执行的ls指令被封装成了ls --color=auto
	//当我们直接执行ls时显示出的结果是有颜色的
	//当我们进入到bin目录下，通过./ls执行的时候是没有颜色的
	
	//自定义一个指令：alias pag='ps aux | grep'
	//执行之后pag命令就代表ps aux | grep
	//这种方法得到的自定义指令是临时的，当把终端关掉时指令失效
	//如果需要长久有效需要设置.bashrc文件

在显示器上显示字符串
	echo "hello linux"
	echo $PATH //PATH代表环境变量的key值 $代表从环境变量中取值
	
关机重启
	poweroff
	reboot
	shutdown

vim是从vi发展过来的
vi不可以用鼠标 没有菜单 只有命令
vi的三种工作模式
命令模式
编辑模式
末行模式：在末行模式下可以输入一些指令
打开vi默认进入命令模式

通过输入iaos进入文本模式
iaos和IAOS是不一样的

文本模式不可以直接切换到末行模式，需要从文本模式摁ESC退出到命令模式
命令模式输入冒号:进入末行模式
末行模式摁两下ESC键回到命令模式

末行模式下
:w 再按回车 将执行写入操作，即将当前编辑的文本保存，完了之后进入到命令模式

命令模式下
移动光标
H J K L
前 下 上 后
当前行头部 0
当前行尾部 $
文件开始位置 gg
文件结束位置 G
移动到500行 500G

删除
删除一个字符：
删除光标所在字符：x
撤销：u
删除光标前字符：X
删除一个单词：dw delete word 使用该指令要注意将光标移动到第一个字符前
删除当前行的光标前：d0
删除当前行的光标后：d$或D
删除光标所在行：dd
删除当前行后面4行：4dd
反撤销：Ctrl+r

vi中的删除的本质是剪切，因此在剪切板中存储了删除的这些内容

p键粘贴，粘贴起始点是光标位置的下一行
P键粘贴，粘贴起始点是光标位置所在行

复制当前行：yy
复制当前行后面4行：4yy

按v键切换到可视模式，通过h j k l可以切换方向
可视模式下切换方向时会将对应内容选中
此时如果想复制就按y，如果想删除就是d
可视模式下粘贴就会把内容粘贴到光标所在位置后面

查找：输入正斜杠/，后面跟要查找的内容，回车，就会把要找的内容黄色高亮显示
向后切换每个高亮可以用n键
向前切换每个高亮可以用N键

?后面跟要查找的内容也可以查找

正斜杠/查找时是从光标所在位置向后查找
问号?是向上查找

移动到某个单词上，按#键，光标当前所在单词就会变成黄色高亮，即成为查找对象

可视模式下按r，再输入其他字符，将替换光标所处字符，r只能做单个字符替换，如果想要将s替换成aa，是不可以的，因为aa是两个字符

向右缩进 >>
向左缩进 <<

希望在man文档中查看库函数定义时可以将光标移动到函数名上按shift+K 默认跳到第一章，在第一章中搜索有没有printf的命令，第一章正好有这样一个指令，因此就停到了第一章，如果第一章没有printf这样一个指令的话有可能直接跳到第三章对应的位置
因此为了保证跳转的正确性，我们需要手动指定跳转到第几章：3shift+k就跳到了第3章
退出man文档按q即可

命令模式下可以通过aios进入编辑模式

命令模式下输入a在底行可以看到"插入"两个字，此时可以在光标后面插入内容
输入A也可以切换到编辑模式，此时光标会切换到当前行行尾，因此输入时是在当前行行尾进行插入

命令模式下输入i进入编辑模式时，输入的字符插入到光标前
命令模式下输入I进入编辑模式时，此时光标移动到当前行行首

命令模式下输入o进入编辑模式时，在光标所在位置的下方新创建了一行，光标也移动到了下面这行
命令模式下输入O进入编辑模式时，在光标所在位置的上方新创建了一行，光标也移动到了上面这行

命令模式下输入s进入编辑模式时，输入字符时会删除光标后的字符
命令模式下输入S进入编辑模式时，输入字符时当前行会被删除

命令模式下切换到末行模式可以输入冒号:
:300 //跳转到第300行
:s/tom/jack //执行之后光标所在行的第一个tom将被替换成jack
:s/tom/jack/g //光标所在行的所有tom都被替换成jack
:%s/tom/jack //每行的第一个tom会被替换成jack
:%s/tom/jack/g //整个文档中所有tom会被替换成jack
:27,30s/tom/jack/g //27-30行的tom会被替换成jack
:/tom //查找文档中所有的tom并高亮显示

:!ls //会执行ls命令

摁两次esc就可以回到末行模式

:w //保存
:q //退出
:q! //退出不保存
:wq //保存并退出
:x //保存并退出

命令模式下摁两个大写的Z也可以保存并退出

屏幕分屏可以在末行模式下输入
:sp 水平分屏
:vsp 垂直分屏
:vsp mainwindow.cpp 将会在另外一屏中显示mainwindow.cpp这个文件

ctrl w w //就可以在两个屏幕之间切换

:wqall //将分的所有的屏全都关闭

vim打造IDE
	//系统级配置文件目录：/etc/vim/vimrc //修改系统级配置文件将会使所有的Linux用户受益
	//用户级配置文件目录：~/vim/vimrc //修改当前用户家目录里面的配置，只对当前用户有效

gcc编译的4个阶段
								
hello.c
	------------预处理器(cpp) gcc -E hello.c -o hello.i(头文件展开，宏替换，注释去掉)----------->
hello.i
	------------编译器(gcc) gcc -S hello.i -o hello.s(C文件编译成汇编文件)------------->
hello.s
	------------汇编器(as) gcc -c hello.s -o hello.o(汇编文件变成二进制文件)-------------->
hello.o
	------------链接器(ld) gcc hello.o -o hello(将函数库中相应的代码组合到目标文件中)----------------->
a.out

第二步编译最浪费时间

预处理期间是gcc调用了cpp来完成的，as和ld也是gcc调用了它们

//直接生成最终的可执行文件，中间文件都没有
gcc aaa.c -o aaa

gcc sum.c -I ./include -o app //指定头文件的目录为./include
//此时如果在源文件中写#include "head.h"的话，引入的是#include "./include/head.h"

gcc sum.c -o app -D DEBUG //编译时指定DEBUG宏
//此时如果程序中有
#ifdef DEBUG
	printf("val");
#endif
DEBUG 这个宏不必在最开始定义，这样用的就是编译时的宏，由于我们在编译的时候可能会涉及很多文件都需要用到DEBUG宏，因此这样一来就不需要每个文件中定义或者引用别的文件的宏了

gcc sum.c -o app -D DEBUG -O3 //-O表示将程序优化到3级，优化的等级有0 1 2 3

gcc sum.c -o app -D DEBUG -O3 -Wall //编译时输出警告信息

gcc sum.c -o app -D DEBUG -O3 -Wall -g //增加参数-g之后会在应用程序中增加调试信息，具体调试信息是什么等介绍了gdb之后再说

静态库
静态库的制作
	命名规则：
		lib + 库的名字 + .a
		libmytest.a
	制作步骤：
		生成对应的.o文件 .c-->.o
		将生成的.o文件打包 ar rcs libmytest.a + 生成的所有的.o
	发布和使用静态库
		发布静态库
		头文件
	使用静态库方法一
		gcc main.c -I include lib/libMyCalc.a -o sum

使用静态库方法二
gcc main.c -I include -L lib -l MyCalc -o myapp
-L 指定静态库的目录
-l 指定静态库的名字 起名字的时候我们起成了libMyCalc.a 在-l参数后面写的时候需要掐头去尾写成MyCalc

nm libMyCalc.a //查看libMyCalc.a里面的.o文件
nm还可以查看可执行文件app:nm app 第二列中有的行是T，T代表在代码区

静态库的优点：
发布程序的时候，不需要提供对应的库
库的加载速度比较快

静态库的缺点：
库被打包到应用程序中，导致库的体积很大
库发生改变，应用程序需要重新编译

动态库(共享库) 对应Windows中的dll文件
共享库的制作：
	命名规则：lib + 名字 + .so
	制作步骤：
		生成与位置无关的代码(生成与位置无关的.o)
			gcc -fPIC -c *.c -I ../include
		将.o打包成共享库(动态库)
			gcc -shared -o libMyCalc.so *.o -I include
	发布和使用动态库

gdb调试
gdb app 调试app可执行程序
(gdb) l 查看带main函数的源文件
(gdb) l select_sort.c:20 查看select_sort.c的第20行
(gdb) l select_sort.c:selectSort 查看select_sort.c的selectSort函数位置 如果想往后看可以输入l，如果输入l之后还想往后看可以摁enter，直接摁enter代表执行上一次的操作

进入某个文件后
(gdb) break 22 就在22行打了断点
可以简写成
(gdb) b 22
(gdb) b 15 if i==15 条件断点
(gdb) info break 查看断点信息 可以简写为(gdb) i b，和chrome中断点信息基本一致
(gdb) start 让gdb开始跑起来，start只让程序执行一步就停住，输入n进入下一步，这叫单步调试，输入c继续执行进入下一个断点
(gdb) s 遇到函数执行时输入s进入函数内部，类似于chrome调试中的F11进入函数内部
调试中如果想查看变量a的值，可以通过(gdb) p a，查看变量a的类型，可以通过(gdb) ptype a
(gdb) display i 追踪i变量的值
(gdb) info display 获得断点的编号
(gdb) undisplay 1 不追踪编号为1的断点
(gdb) u 跳出单次循环，相当于循环程序中的continue
(gdb) finish 跳出当前函数
(gdb) d 4 删除编号为4的断点
(gdb) set var i=10 设置变量i的值为10
(gdb) quit 退出gdb

makefile
为了避免书写很长的gcc命令，因此出现了makefile
命名：Makefile makefile
规则三要素：目标 依赖 命令
	目标：依赖条件
		命令


app:main.c add.c sub.c mul.c
	gcc main.c add.c sub.c mul.c -o app

写好makefile之后输入make就可以查看makefile里面的内容并生成app可执行文件

I/O操作
fopen可以打开文件，返回值为FILE* fp
文件描述符：可以通过文件描述符来找到文件的位置
I/O缓冲区：标准C库为每个I/O函数提供了一个I/O缓冲区，缓冲区是为了减少对硬盘的操作

缓存区中的数据写到硬盘上的时机
1、手动刷新缓冲区 fflush
2、缓冲区已满
3、正常关闭文件
	1>fclose
	2>return(main函数)
	3>exit(main函数)

fflush和fclose较为常见

注：Linux系统函数没有I/O缓冲区


在内存中的内核区有一块叫做PCB的东西，这个PCB叫进程控制块，PCB中存储着文件描述符表，该表实际上是一个数组，共有1024项，每一项都代表可以打开一个文件，每打开一个文件就在文件描述符表中占用一个位置，其中第0 1 2三项默认处于被打开的状态，这三项分别是STDIN_FILENO STDOUT_FILENO STDERR_FILENO，分别代表标准输入、标准输出、标准错误三个库文件

内存用户区：
环境变量env
命令行参数
栈空间
共享库
堆空间
.bss(未初始化全局变量)
.data(已初始化全局变量)
text(代码段，二进制机器指令)
受保护的地址(0-4K)

Linux下可执行文件格式ELF

CPU为什么要用虚拟地址空间与物理地址空间映射？
1、方便编译器和操作系统安排程序的地址分析
	程序可以使用一系列相邻的虚拟地址来访问物理内存中不相邻的大内存缓冲区
2、方便进程之间的隔离
	不同进程使用的虚拟地址彼此隔离，一个进程中的代码无法更改正在由另一进程使用的物理内存
3、方便OS使用你那可怜的内存
	程序可以使用一系列虚拟地址来访问大于可用物理内存的内存缓冲区。当物理内存的供应量变小时，内存管理器会将物理内存页(通常大小为4K)保存到磁盘文件。数据或代码页会根据需要在物理内存与磁盘之间移动

C标准函数和系统函数的关系(见图)
printf具体过程
printf函数->标准输出(stdout)：FILE*--------FD(文件描述符) FP_POS(文件指针) BUFFER(缓冲区)
->Linux操作系统应用层：调用write函数将文件描述符传递过去 write(FD, "hello", 5)
->Linux操作系统系统调用层：sys_write() 用户空间->内核空间
->Linux操作系统内核层：设备驱动函数
->硬件层 显示器显示

Linux下常用的系统I/O函数
open
	man 2 open //从第二章开始找open函数 第二章主要介绍系统函数
	#include <sys/types.h>
	#include <sys/stat.h>
	#include <fcntl.h>
	
	//pathname是文件打开的路径 flags是打开的方式(值可以为O_RDONLY O_WRONLY O_RDWR) 这个open重载只是适合文件存在的情况，如果文件不存在，就需要用下面的三个参数的open重载
	int open(const char *pathname, int flags);
	//mode参数指定为O_CREAT可以用来创建文件
	//文件权限：本地有一个掩码，文件的实际权限：给定的权限和本地掩码取反进行按位与操作得出来的才是实际权限，umask可以查看掩码
	int open(const char *pathname, int flags, mode_t mode);
	int creat(const char * pathname, mode_t mode);
	
	open creat返回值为新的文件描述符 或者 -1
	
	void perror(char* c); //接收参数作为一个标记，注意并不是把open返回的-1传进去，作用类似于PHP中的die里面传的函数
	
	//open函数用法
	#include <sys/types.h>
	#include <sys/stat.h>
	#include <fcntl.h>
	#include <unistd.h> //close函数需要
	#include <stdlib.h> //exit函数需要
	#include <stdio.h> //perror函数需要
	
	int main(){
		int fd;
		//打开已有文件
		fd = open("hello", O_RDWR);
		
		if(fd == -1){
			perror("open file");
			exit(1);
		}
		
		//创建新文件
		//fd = open("myhello", O_RDWR | O_CREATE, 0777); //777代表Linux文件访问权限
		fd = open("myhello", O_RDWR | O_CREATE, 0775); //注：本地有一个掩码，文件的实际权限：给定的权限和本地掩码取反进行按位与操作得出来的才是实际权限
		if(fd == -1){
			perror("open file");
			exit(1);
		}
		
		//判断文件是否存在
		fd = open("myhello", O_RDWR | O_CREAT | O_EXEC, 0777);
		if(fd == -1){
			perror("open file");
			exit(1);
		}
		
		//文件截断为0 即清空文件内容
		fd = open("myhello", O_RDWR | O_TRUNC, 0777);
		if(fd == -1){
			perror("open file");
			exit(1);
		}
		
		int ret = close(fd);
		printf("%d", ret);
	}
	
	
read
#include <unistd.h>

//ssize_t的意思是signed size_t
ssize_t read(int fd, void* buf, size_t count);
返回值：
-1 读文件失败
0  文件读完了
>0 读取的字节数

write lseek close



系统编程===============================================================================

程序：是编译好的二进制文件，保存在磁盘上，不占用系统资源（所谓系统资源是指CPU 内存 打开的文件 设备 锁...，注意磁盘不是系统资源）

进程：是一个抽象的概念，与操作系统原理联系紧密。进程是活跃的程序，占用系统资源，在内存中执行。（程序运行起来，产生一个进程）

同一个程序可以加载为不同的进程

同时开两个终端各自都有一个bash但彼此id不同

当CPU一个时刻只能处理一个进程时，这种设计模式叫单道程序设计模式
当CPU一个时刻可以处理多个进程时，这种设计模式叫多道程序设计模式
多道程序设计中CPU使用权会依次分配给每个进程，过一段时间后CPU收回该使用权，收回使用权的过程叫时钟中断

单道程序设计模式目前基本淘汰

寄存器->cache->内存->硬盘->网络
从左到右存取速度依次降低，容量依次增大

CPU先通过预取器从内存中取出指令，然后交给译码器分析该指令的作用再将操作数放入寄存器，再通过ALU计算将结果写回到寄存器中，再交给缓存、内存，这样就完成了一条指令的执行

寄存器通常会放在一起，叫寄存器堆

CPU中还有一个部件叫MMU，用来完成虚拟内存和物理内存的对应，并在CPU访问内存时设置和修改CPU访问内存的级别，内核态和用户态使用CPU时对内存的访问级别肯定不一样，内核态的访问级别明显较高，即能访问范围更大的内存范围

当物理内存只有512MB时，也可以虚拟出来4G，虚拟地址是指可用的地址空间有4G

程序中使用的地址是虚拟地址，不会涉及物理地址

当调用printf时是用户态，printf会进一步调用底层的write方法，调用write方法时就要从用户态转变为内核态，MMU会将CPU的访问级别从3级（最低）转换为0级（最高），Intel给CPU提供了4个等级，0 1 2 3，Linux操作系统只用到了2个等级。

磁盘上的1个程序分别开启两次时，会产生两个不同的进程，并且会为每个进程分配各自的物理内存（text(程序可执行代码段)、rodata(存放C中的字符串和#define定义的常量)、data(已初始化全局变量)、bss(未初始化全局变量)、堆空间、共享库、栈空间、命令行参数、环境变量），各自的物理内存会通过MMU分别对应一套虚拟内存
例如启动两次QQ，肯定要分配两次物理内存和虚拟内存，这样才可以保证登录的两个账号互不受影响

而内核空间（3G-4G）只需要一份即可，因为各个进程共用的是一块内核空间，但问题是内核空间中会有PCB进程描述符，由于各个进程调用的系统库函数不一定一样，因此各个进程的PCB进程描述符也不一定一样，但它们又是共用了一套物理内存，具体MMU该如何映射，如何管理，留给读者自己思考

Linux内核中，进程控制块是task_struct结构体
在/usr/src/linux-headers-3.16.0/include/linux/sched.h文件中可以查看该结构体的定义，内部成员有很多，只需要掌握下面几项：
进程id：进程唯一标识，在C语言中用pid_t类型表示，其实就是一个非负整数
进程状态：有就绪、运行、挂起、停止等状态，挂起状态是在等待除CPU以外的其他资源，是主动放弃CPU使用权
进程切换时需要保存和恢复的一些CPU寄存器
描述虚拟地址空间的信息
描述控制终端的信息
当前工作目录（Current Working Directory）
umask掩码
文件描述符表，包含很多指向file结构体的指针
和信号相关的信息
用户id和组id
会话（Session）和进程组
进程可以使用的资源上限













//网络编程======================================================================
C/S 
	优点：
		1、协议选用灵活，可以自定义协议
		2、数据可以提前缓存
	缺点：
		1、对用户安全构成威胁
		2、需要开发客户端和服务器端，工作量很大，沟通调试成本高

B/S的优缺点和C/S是相对的

IP地址：在网络环境中唯一标识一台主机
端口号：在主机中唯一标识一个进程
IP+端口号：在网络环境中唯一标识一个进程(socket)
socket必须包含IP和端口号

socket是Linux文件的一种类型，socket实际上就是Linux中的一个文件 而且是伪文件


server实现

#include <stdio.h>
#include <unistd.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <ctype.h> //toupper

#define SERV_IP "192.168.1.234"
#define SERV_PORT 6666

int main(){
	int lfd;
	int cfd;
	struct socketaddr_in serv_addr;
	struct socketaddr_in clie_addr;
	socklen_t clie_addr_len;
	char buf[BUFSIZ];
	int n;
	int i;
	
	lfd = socket(AF_INET, SOCK_STREAM, 0);
	
	//定义协议族
	serv_addr.sin_family = AF_INET;
	//定义端口号
	serv_addr.sin_port = htons(SERV_PORT);
	//定义IP
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	
	//绑定IP和端口号
	bind(lfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr));
	
	//指定建立连接上限数 注意不是保持连接的上限
	listen(lfd, 32);
	
	clie_addr_len = sizeof(clie_addr);
	//等待客户端连接
	cfd = accept(lfd, (struct sockaddr *)&clie_addr, &clie_addr_len);
	
	n = read(cfd, buf, sizeof(buf));
	for(i = 0; i < n; i++){
		buf[i] = toupper(buf[i]);
	}
	write(cfd, buf, n);
	
	close(lfd);
	close(cfd);
	
	return 0;
}

client实现



















































































































































































































































































































































































































































































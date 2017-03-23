PHP基础篇

Redis
Redis有客户端和服务端 通常说的Redis是指服务端
Redis是存在于内存中的非关系型数据库，因为是在内存中，因此很快

应用：
缓存
队列
数据库：基于Redis拥有的完整的硬盘持久化的机制

安装环境：
Linux/CentOS release 6.8

下载Redis源码 2.8.13

Redis需要gcc编译器和tcl（Redis的测试组件是基于tcl这门语言来写的）

服务端安装：
tar xf redis-2.8.13.tar.gz
cd redis-2.8.13
make
sudo make install
安装到了/usr/local/bin/redis-server下

redis通常和一个配置文件配合使用，配置文件的基本格式在源码路径下的redis.conf文件中
先将该文件复制到家目录中，打开它更改相关配置
daemonize yes //默认值为no，代表前台启动，改为yes变成后台启动

port 7200 //默认是6379，由于多实例和安全性的问题，通常改为7200

启动redis服务端的命令：redis-server /home/vagrant/config/redis/redis.conf 参数即为redis server启动时所依赖的配置文件

有时权限不够时需要使用sudo来执行，需要加上redis-server的路径：
sudo /usr/local/bin/redis-server /home/vagrant/config/redis/redis.conf

确定一下redis-server是否启动使用指令：ps aux|grep redis-server

此时实际上redis客户端也已经安装到Linux中了，可以使用which redis-cli查看安装的位置

直接执行redis-cli指令来登录redis-server，会发现登录失败，因为redis-cli默认登录本机的6379端口，需要跟上参数：redis-cli -h 127.0.0.1 -p 7200

登录成功后执行info命令可以显示redis的相关信息

在命令行中就可以查看Redis有哪些数据类型了：String、List、Set、Hash Sort、Set
String：可以存储字符串、整数、浮点，统称为元素，读写能力：对字符串操作、对整数类型加减
List：存储一个序列集合且每个节点都包好了一个元素，读写能力：序列两端推入、或弹出元素、修剪或查找或移除元素
Set：存储各不相同的元素，读写能力：从集合中插入或删除元素
Hash：存储key-value的散列组，其中key是字符串，value是元素，读写能力：按照key进行增加删除
Sort Set：带分数的score-value有序集合，其中score为浮点，value为元素，读写能力：集合插入、按照分数范围查找

String类型：
key	value(string/int/float)
set string1 yejianfeng
get string1 //yejianfeng

set string2 4
get string2 //4

incr string2
get string2 //5

decrby string2 2
get string2 //3


List类型
lpush list1 12 //lpush代表从左边入队
lpush list1 13
rpop list1 12  //rpop代表从右边出队

llen list1 //返回list1的长度

Set类型
sadd set1 12
scard set1 //查看有几个元素
sadd set1 13
sadd set1 13
scard set1 //2 13重复插入只有一个

sismember set1 13 //1 说明13是set1中的成员

srem set1 13 // 从set1中删除13

Hash类型
hset hash1 key1 12 //键为key1 值为12
hget hash1 key1 //12

hlen hash1 //1 长度为1

hmget hash1 key1 key2 //一次性获取hash1中key1 key2的值

SortSet类型 类似hash类型
zadd zset1 10.1 val1 //增加一个分数为10.1，值为val1的zset1
zadd zset1 11.2 val2 
zadd zset1 9.2 val3
zcard zset1 //长度为3
zrange zset1 0 2 withscores //按照分数将第0-2个元素排名 得到9.2 10.1 11.2
zrank zset1 val2 //2 val2排在第2名
zadd zset1 12.2 val3 //将val3变为12.2
zadd zset1 12.2 val2 //val2和val3的score一样，则按照key值排列，val2比val3小，因此val2排在val3前面





































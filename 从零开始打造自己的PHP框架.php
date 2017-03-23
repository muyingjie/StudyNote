// 从零开始打造自己的PHP框架
// 入口文件->定义常量->引入函数库->自动加载类->启动框架
//												|
// 返回结果->加载控制器->路由解析----------------	|


<?php

define('IMOOC', realpath('/'));
define('CORE', IMOOC.'/core');
define('APP', IMOOC.'/app');
define('DEBUG', true);

if(DEBUG){
	ini_set('display_error', 'On');
}else{
	ini_set('display_error', 'Off');
}


































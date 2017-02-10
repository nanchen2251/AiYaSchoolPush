<?php
require_once '../db.php';
require_once '../table.php';
require_once '../format.php';

// 包含ezSQL的核心文件
include_once 'shared/ez_sql_core.php';

// 包含ezSQL具体的数据库文件，这里以mysql为例
include_once 'mysql/ez_sql_mysql.php';

// 初始化数据库对象并建立数据库连接
$db = new ezSQL_mysql ( DB_USER, DB_PWD, DB_NAME, DB_HOST,"utf8");

// 取数组
// $users = $db->get_results("select * from aiya_user");
// foreach ($users as $user){
// echo $user->username;
// echo '<br/>';
// echo $user->password;
// echo '<br/>';
// echo '<br/>';
// }

// 取对象
// $user = $db->get_row ( "select * from aiya_user where username = '15680802251'" );
// if ($user != null){
// 	echo $user->username;
// 	echo '<br/>';
// 	echo $user->password;
// 	echo '<br/>';
// }else{
// 	echo "没有值";
// }

// 取数值
// $var = $db->get_var("select password from aiya_user where username = '15680802251'");
// echo $var;

//插入值到数据库
// $db->query("insert into aiya_user (username,password,nickname) values ('123456','123456','测试者账号')");
// echo $result;

//更新数据信息
// $db->query("update aiya_user set password = '123' where username = '123456'");

// 用ezSQL的打印方式
$result = $db->get_results("select * from aiya_user");
$db->vardump($result);
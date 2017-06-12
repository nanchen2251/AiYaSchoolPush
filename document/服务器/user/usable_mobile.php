<?php

require_once '../db.php';
require_once '../format.php';
require_once '../table.php';


@$mobile = $_GET['mobile'] ? $_GET['mobile']:null;

if ($mobile == null){
	Response::json_response(-1,"传入参数错误！",null);
	exit();
}

// 确定参数传入不为null，开始数据库连接
$link = DataBaseUtil::getInstance()->connect();

if ($link->errno != 0){ // 数据库连接失败
	Response::json_response(-1,"数据库连接失败!",null);
	exit();
}

// sql语句  select * from aiya_user where username = $mobile
$query = "select * from ".TABLE_USER." where username = '".$mobile."'";
// echo $query;
// echo '<br/>';

// 开始执行查询
mysqli_query($link, $query);

$num = $link->affected_rows;

if ($num){// 说明查询出了结果  只要不为0 就是真
	Response::json_response(-1,"当前账号已经被注册,请直接登录!",null);
}else{
	Response::json_response(0,"恭喜你，该账号可以注册！",null);
}

mysqli_close($link);



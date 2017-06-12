<?php

require_once '../format.php';
require_once '../db.php';
require_once '../table.php';

$link = DataBaseUtil::getInstance()->connect();
mysqli_set_charset($link, "utf8"); // 设置编码为utf8

if ($link->errno != 0) { // 数据库连接失败
	Response::json_response ( - 1, "数据库连接失败!", null );
	exit ();
}

@$username = $_GET['username'];

$query = "select * from ".TABLE_USER." where username = '".$username."'";

$result = mysqli_query($link, $query);

$row = mysqli_fetch_array($result);

if ($row){
	$arr = array(
			'username' => $row['username'],
			'nickname' => $row['nickname'],
			'avatar' => $row['avatar']
	);
	Response::json_response(0,"获取用户信息成功",$arr);
}else{
	Response::json_response(-1,"获取用户信息失败",null);
}
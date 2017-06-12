<?php

require_once '../db.php';
require_once '../format.php';
require_once '../table.php';

file_get_contents("php://input");

$link = DataBaseUtil::getInstance()->connect();

if ($link->errno != 0){ // 数据库连接失败
	Response::json_response(-1,"数据库连接失败!",null);
	exit();
}

mysqli_set_charset($link, "utf8");

@$avatar = $_GET['iconUrl'];
@$type = $_GET['type'];
@$username = $_GET['username'];

switch ($type){
	case 0:// 设置自己的头像
		$query = "update ".TABLE_USER." set avatar = '".$avatar."' where username = '".$username."'";
		mysqli_query($link, $query);
		$num = mysqli_affected_rows($link);
// 		echo $query.'<br/>'.$num;
		if ($num == 1){
			Response::json_response(0,"头像替换成功",null);
		}else{
			Response::json_response(-1,"头像替换失败！",null);
		}
		break;
	case 1:// 设置孩子的头像
		$query = "update ".TABLE_USER." set child_avatar = '".$avatar."' where username = '".$username."'";
		mysqli_query($link, $query);
		
		$num = mysqli_affected_rows($link);
		if ($num == 1){
			Response::json_response(0,"孩子头像替换成功",null);
		}else{
			Response::json_response(-1,"孩子头像替换失败！",null);
		}
		break;
}

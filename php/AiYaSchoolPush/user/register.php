<?php
require_once '../db.php';
require_once '../format.php';
require_once '../table.php';
require_once '../emchat-server-php/easemobtest.php';
file_get_contents("php://input");

$link = DataBaseUtil::getInstance()->connect();

if ($link->errno != 0){ // 数据库连接失败
	Response::json_response(-1,"数据库连接失败!",null);
	exit();
}

mysqli_set_charset($link, "utf8");

@$username = $_POST['username'] ? $_POST['username']:null;
@$password = $_POST['password'] ? $_POST['password']:null;
@$nickname = $_POST['nickname'] ? $_POST['nickname']:null;
@$birthday = $_POST['birthday'] ? $_POST['birthday']:null;
@$avatar = $_POST['avatar'] ? $_POST['avatar']:null;

// $username = '1201';
// $password = '123';
// $nickname = '哈哈';
// $birthday = time();


$result = createUser($username, $password);

// 如果注册出现了错误
if (array_key_exists('error', $result)){
	Response::json_response(-1,"注册IM失败".$result['error'],$result);
	exit();
}

// 注册环信成功

$sql = "insert into ".TABLE_USER." 
		(username,password,nickname,birthday,avatar) values ('"
		.$username."','".$password."','".$nickname."','".$birthday."','".$avatar."')";

// echo $sql;

mysqli_query($link, $sql);

// echo $data;

// echo '<br/>';

$num = $link->affected_rows;

// echo $num;


if ($num == 1){
	Response::json_response(0,"注册成功！",null);	
}else{
	Response::json_response(-1,"注册失败"+mysqli_error($link),null);
}

mysqli_close($link);
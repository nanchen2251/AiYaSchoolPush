<?php
require_once '../db.php';
require_once '../format.php';
require_once '../table.php';
require_once '../emchat-server-php/easemobtest.php';

file_get_contents ( "php://input" );

$link = DataBaseUtil::getInstance ()->connect ();

if ($link->errno != 0) { // 数据库连接失败
	Response::json_response ( - 1, "数据库连接失败!", null );
	exit ();
}

@$username = $_POST['username'];
@$password = $_POST['password'];

// $username = '15680802251';
// $password = '123456';

$result = resetPassword ($username, $password );
// 如果重置密码出现了错误
if (array_key_exists('error', $result)){
	Response::json_response(-1,"重置IM密码失败".$result['error'],$result);
	exit();
}


// 首先判断和原密码是否一致
$query = "select * from ".TABLE_USER." where username = '".$username."' and password = '".$password."'";
$result = mysqli_query($link, $query);
$row = mysqli_fetch_array($result);
if ($row != 0){// 密码和原来一致 ，无需更新
	Response::json_response(-1,"新密码不能和原密码一致！",null);
	mysqli_close($link);
	exit();
}

$sql = "update " . TABLE_USER . " set password = '" . $password . "' where username = '" . $username."'";
$data = mysqli_query ( $link, $sql );
if ($data){
	$num = mysqli_affected_rows($link);
	if ($num == 1) { // 影响了一行，说明更新数据库成功
		Response::json_response ( 0, "重置密码成功!", null );
	} else { // 没有影响一行，两种情况，一种是密码和原密码一致
		Response::json_response ( - 1, "重置密码失败！手机号未注册！", null );
	}
}else{
	Response::json_response(-1,mysqli_error($link),null);
}
mysqli_close ( $link );


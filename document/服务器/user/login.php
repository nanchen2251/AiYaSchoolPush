<?php
require_once '../db.php';
require_once '../format.php';
require_once '../table.php';

file_get_contents ( "php://input" );

$link = DataBaseUtil::getInstance ()->connect ();

if ($link->errno != 0) { // 数据库连接失败
	Response::json_response ( - 1, "数据库连接失败!", null );
	exit ();
}
mysqli_set_charset($link, "utf8");

@$username = $_POST ['username'];
@$password = $_POST ['password'];


$sql = "select * from " . TABLE_USER . " where username = '" . $username . "' and password = '" . $password."'";

$result = mysqli_query ( $link, $sql );

$num = mysqli_affected_rows ( $link );

if ($num == 1) {
	// 登录成功后返回用户的信息
	$row = mysqli_fetch_array ( $result );
	$data = array (
			'username' => $row ['username'],
			'password' => $row ['password'],
			'birthday' => $row ['birthday'],
			'nickname' => $row ['nickname'],
			'type' => $row ['type'],
			'classid' => $row ['classid'],
			'address' => $row ['address'],
			'avatar' => $row ['avatar'],
			'child_name' => $row ['child_name'],
			'child_avatar' => $row ['child_avatar'] 
	);
	Response::json_response ( 0, "登录成功", $data );
} else {
	Response::json_response ( - 1, "登录失败，用户名或者密码不正确!", null );
}

mysqli_close ( $link );

?>

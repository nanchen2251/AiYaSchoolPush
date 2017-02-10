<?php

require_once '../format.php';
require_once '../db.php';
require_once '../table.php';

file_get_contents("php://input");
$link = DataBaseUtil::getInstance()->connect();
mysqli_set_charset($link, "utf8"); // 设置编码为utf8

if ($link->errno != 0) { // 数据库连接失败
	Response::json_response ( - 1, "数据库连接失败!", null );
	exit ();
}

$time = time();
@$mainid = $_POST['mainId'];
@$username = $_POST['username'];
@$content = $_POST['content'];
@$reply = $_POST['reply'];

// @$mainid = 101;
// @$username = 15680802251;
// @$content = '草拟吗的';
// @$reply = 18384988447;

$query = "insert into ".TABLE_COMMENT." (mainid,username,
		time,content,reply) values('".
		$mainid."','".$username."','".
		$time."','".$content."','".$reply."')";

$result = mysqli_query($link, $query);

$num = mysqli_affected_rows($link);

if($num == 1){
	Response::json_response(0,"插入数据成功！",null);
}else{
	Response::json_response(-1,"插入数据失败",null);
}
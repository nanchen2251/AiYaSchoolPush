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
@$praiseId = $_GET['infoId'];
@$username = $_GET['username'];


$query = "delete from ".TABLE_COMMENT." where infoid = ".$praiseId." and username = '".$username."'";
		$result = mysqli_query($link, $query);
		$num = mysqli_affected_rows($link);
		if($num == 1){
			Response::json_response(0,"删除数据成功！",null);
		}else{
			Response::json_response(-1,"删除数据失败",null);
		}
		
?>
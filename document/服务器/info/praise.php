<?php
require_once '../format.php';
require_once '../db.php';
require_once '../table.php';
require_once 'util.php';

file_get_contents ( "php://input" );
$link = DataBaseUtil::getInstance ()->connect ();
mysqli_set_charset ( $link, "utf8" ); // 设置编码为utf8

if ($link->errno != 0) { // 数据库连接失败
	Response::json_response ( - 1, "数据库连接失败!", null );
	exit ();
}

$time = time ();
@$mainId = $_GET ['mainId'];
@$username = $_GET ['username'];

$query = "select * from " . TABLE_PRAISE . " where username = '".$username."' and mainid = " . $mainId;
$result = mysqli_query ( $link, $query );
$num = mysqli_affected_rows ( $link );

if ($num == 0) {
	$query = "insert into " . TABLE_PRAISE . " (mainid,username) values('" . $mainId . "','" . $username . "')";
	$isInsert = true;
} else {
	$query = "delete from " . TABLE_PRAISE . " where mainid = " . $mainId . " and username = '".$username."'";
	$isInsert = false;
}

$result = mysqli_query ( $link, $query );
$num = mysqli_affected_rows ( $link );
$arr = array(
		'isInsert' => $isInsert,
		'praiseCount'=>Util::getInfoCount($mainId, TABLE_PRAISE)
);
if ($num == 1) {
	Response::json_response ( 0, "更新赞的信息成功！", $arr);
} else {
	Response::json_response ( - 1, "更新赞的信息失败", null );
}



?>
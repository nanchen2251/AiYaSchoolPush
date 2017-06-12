<?php

/**
 * 负责更新一系列信息
 */

require_once '../table.php';
require_once '../format.php';
require_once '../db.php';
require_once '../emchat-server-php/easemobtest.php';

$link = DataBaseUtil::getInstance()->connect();
mysqli_set_charset($link, "utf8");

@$action = $_GET['action'];
@$value = $_GET['value'];
@$username = $_GET['username'];



$query = "update ".TABLE_USER." set ".$action." = '".$value."' where username = '".$username."'";
$result = mysqli_query($link, $query);
$num = mysqli_affected_rows($link);

// echo $query;
// echo '<br/>';
// echo $num;
if ($num == 1){
	if ($action == 'nickname'){
		editNickname($username,$value);
	}
	Response::json_response(0,"更新信息成功！",null);
}else{
	Response::json_response(-1,"更新用户信息失败!",null);
}


?>
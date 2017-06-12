<?php

require_once '../format.php';

$target_path = "./avatar";// 接收文件目录

@$filename = $_FILES['avatar']['name']; // 显示客户端文件的原名称
@$error =  $_FILES['avatar']['error'];
$target_path = $target_path."/".$filename;
// $target_path = $filename;

if (is_uploaded_file(@$_FILES['avatar']['tmp_name'])){
	$flag = move_uploaded_file(@$_FILES['avatar']['tmp_name'],$target_path);
	if ($flag){
		Response::json_response(0,"头像设置成功！",null);
	}else{
		Response::json_response(-1,"头像设置失败:".$error,null);
	}
}else{
	Response::json_response(-1,"头像上传过程中出现了未知错误".$error,null);
}




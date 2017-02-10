<?php
/**
 * --------------------------------------------------
 * 环信PHP REST示例代码
 * --------------------------------------------------
 * Copyright(c) 2015 环信即时通信云 www.easemob.com
 * --------------------------------------------------
 * Author: 神之爱 <fengpei@easemob.com>
 * --------------------------------------------------
 */
// 设置
$client_id = 'YXA6QK2SkJtDEeaLo_UHPAg0VQ';
$client_secret = 'YXA6jG6Pd2yPrIteNqfU8BGk23BFKwU';
$base_url = "https://a1.easemob.com/15680802251/aiyaschoolpush/";

$i = 11;
// test ( $i );
function test($i) {
	switch ($i) {
		case 10 : // 获取token
			var_dump ( getToken () );
			break;
		case 11 : // 创建单个用户
			$result = createUser ( "11011", "123456" );
			// var_dump($result);
			echo "<pre>";
			print_r ( $result );
			echo "<pre>";
			echo "<br/>";
			echo "下面是自己的" . '<br/>';
			if (array_key_exists ( 'error', $result )) {
				echo $result ['error'];
			}
			break;
		case 12 : // 创建批量用户
			var_dump ( createUsers ( array (
					array (
							"username" => "zhangsan",
							"password" => "123456" 
					),
					array (
							"username" => "lisi",
							"password" => "123456" 
					) 
			) ) );
			break;
		case 13 : // 重置用户密码
			var_dump ( resetPassword ( "zhangsan", "123456" ) );
			break;
		case 14 : // 获取单个用户
			var_dump ( getUser ( "zhangsan" ) );
			break;
		case 15 : // 获取批量用户---不分页(默认返回10个)
			var_dump ( getUsers () );
			break;
		case 16 : // 获取批量用户----分页
			$cursor = readCursor ( "userfile.txt" );
			var_dump ( getUsersForPage ( 10, $cursor ) );
			break;
		case 17 : // 删除单个用户
			var_dump ( deleteUser ( "zhangsan" ) );
			break;
		case 18 : // 删除批量用户
			var_dump ( deleteUsers ( 2 ) );
			break;
		case 19 : // 修改昵称
			var_dump ( editNickname ( "zhangsan", "小A" ) );
			break;
		case 20 : // 添加好友----
			var_dump ( addFriend ( "zhangsan", "lisi" ) );
			break;
		case 21 : // 删除好友
			var_dump ( deleteFriend ( "zhangsan", "lisi" ) );
			break;
		case 22 : // 查看好友
			var_dump ( showFriends ( "zhangsan" ) );
			break;
		case 23 : // 查看黑名单
			var_dump ( getBlacklist ( "zhangsan" ) );
			break;
		case 24 : // 往黑名单中加人
			$usernames = array (
					"usernames" => array (
							"zhangsan",
							"lisi" 
					) 
			);
			var_dump ( addUserForBlacklist ( "wangwu", $usernames ) );
			break;
		case 25 : // 从黑名单中减人
			var_dump ( deleteUserFromBlacklist ( "zhangsan", "lisi" ) );
			break;
		case 26 : // 查看用户是否在线
			var_dump ( isOnline ( "zhangsan" ) );
			break;
		case 27 : // 查看用户离线消息数
			var_dump ( getOfflineMessages ( "zhangsan" ) );
			break;
		case 28 : // 查看某条消息的离线状态
			var_dump ( getOfflineMessageStatus ( "zhangsan", "77225969013752296_pd7J8-20-c3104" ) );
			break;
		case 29 : // 禁用用户账号-----
			var_dump ( deactiveUser ( "zhangsan" ) );
			break;
		case 30 : // 解禁用户账号-----
			var_dump ( activeUser ( "zhangsan" ) );
			break;
		case 31 : // 强制用户下线
			var_dump ( disconnectUser ( "zhangsan" ) );
			break;
		case 32 : // 上传图片或文件
			var_dump ( uploadFile ( "./resource/up/pujing.jpg" ) );
			// var_dump(uploadFile("./resource/up/mangai.mp3"));
			// var_dump(uploadFile("./resource/up/sunny.mp4"));
			break;
		case 33 : // 下载图片或文件
			var_dump ( downloadFile ( '01adb440-7be0-11e5-8b3f-e7e11cda33bb', 'Aa20SnvgEeWul_Mq8KN-Ck-613IMXvJN8i6U9kBKzYo13RL5' ) );
			break;
		case 34 : // 下载图片缩略图
			var_dump ( downloadThumbnail ( '01adb440-7be0-11e5-8b3f-e7e11cda33bb', 'Aa20SnvgEeWul_Mq8KN-Ck-613IMXvJN8i6U9kBKzYo13RL5' ) );
			break;
		case 35 : // 发送文本消息
			$from = 'admin';
			$target_type = "users";
			// $target_type="chatgroups";
			$target = array (
					"zhangsan",
					"lisi",
					"wangwu" 
			);
			// $target=array("122633509780062768");
			$content = "Hello HuanXin!";
			$ext ['a'] = "a";
			$ext ['b'] = "b";
			var_dump ( sendText ( $from, $target_type, $target, $content, $ext ) );
			break;
		case 36 : // 发送透传消息
			$from = 'admin';
			$target_type = "users";
			// $target_type="chatgroups";
			$target = array (
					"zhangsan",
					"lisi",
					"wangwu" 
			);
			// $target=array("122633509780062768");
			$action = "Hello HuanXin!";
			$ext ['a'] = "a";
			$ext ['b'] = "b";
			var_dump ( sendCmd ( $from, $target_type, $target, $action, $ext ) );
			break;
		case 37 : // 发送图片消息
			$filePath = "./resource/up/pujing.jpg";
			$from = 'admin';
			$target_type = "users";
			$target = array (
					"zhangsan",
					"lisi" 
			);
			$filename = "pujing.jpg";
			$ext ['a'] = "a";
			$ext ['b'] = "b";
			var_dump ( sendImage ( $filePath, $from, $target_type, $target, $filename, $ext ) );
			break;
		case 38 : // 发送语音消息
			$filePath = "./resource/up/mangai.mp3";
			$from = 'admin';
			$target_type = "users";
			$target = array (
					"zhangsan",
					"lisi" 
			);
			$filename = "mangai.mp3";
			$length = 10;
			$ext ['a'] = "a";
			$ext ['b'] = "b";
			var_dump ( sendAudio ( $filePath, $from = "admin", $target_type, $target, $filename, $length, $ext ) );
			break;
		case 39 : // 发送视频消息
			$filePath = "./resource/up/sunny.mp4";
			$from = 'admin';
			$target_type = "users";
			$target = array (
					"zhangsan",
					"lisi" 
			);
			$filename = "sunny.mp4";
			$length = 10; // 时长
			$thumb = 'https://a1.easemob.com/easemob-demo/chatdemoui/chatfiles/c06588c0-7df4-11e5-932c-9f90699e6d72';
			$thumb_secret = 'wGWIyn30EeW9AD1fA7wz23zI8-dl3PJI0yKyI3Iqk08NBqCJ';
			$ext ['a'] = "a";
			$ext ['b'] = "b";
			var_dump ( sendVedio ( $filePath, $from = "admin", $target_type, $target, $filename, $length, $thumb, $thumb_secret, $ext ) );
			break;
		case 40 : // 发文件消息
			$filePath = "./resource/up/a.rar";
			$from = 'admin';
			$target_type = "users";
			$target = array (
					"zhangsan",
					"lisi" 
			);
			$filename = "a.rar";
			$length = 10; // 时长
			$ext ['a'] = "a";
			$ext ['b'] = "b";
			var_dump ( sendFile ( $filePath, $from = "admin", $target_type, $target, $filename, $length, $ext ) );
			break;
		case 41 : // 获取app中的所有群组-----不分页（默认返回10个）
			var_dump ( getGroups () );
			break;
		case 42 : // //获取app中的所有群组--------分页
			$cursor = readCursor ( "groupfile.txt" );
			var_dump ( $cursor );
			var_dump ( getGroupsForPage ( 2, $cursor ) );
			break;
		case 43 : // 获取一个或多个群组的详情
			$group_ids = array (
					"1445830526109",
					"1445833238210" 
			);
			var_dump ( getGroupDetail ( $group_ids ) );
			break;
		case 44 : // 创建一个群组
			$options ['groupname'] = "group001";
			$options ['desc'] = "this is a love group";
			$options ['public'] = true;
			$options ['owner'] = "zhangsan";
			$options ['members'] = Array (
					"wangwu",
					"lisi" 
			);
			var_dump ( createGroup ( $options ) );
			break;
		case 45 : // 修改群组信息
			$group_id = "124113058216804760";
			$options ['groupname'] = "group002";
			$options ['description'] = "修改群描述";
			$options ['maxusers'] = 300;
			var_dump ( modifyGroupInfo ( $group_id, $options ) );
			break;
		case 46 : // 删除群组
			$group_id = "124113058216804760";
			var_dump ( deleteGroup ( $group_id ) );
			break;
		case 47 : // 获取群组中的成员
			$group_id = "122633509780062768";
			var_dump ( getGroupUsers ( $group_id ) );
			break;
		case 48 : // 群组单个加人-----------
			$group_id = "122633509780062768";
			$username = "lisi";
			var_dump ( addGroupMember ( $group_id, $username ) );
			break;
		case 49 : // 群组批量加人
			$group_id = "122633509780062768";
			$usernames ['usernames'] = array (
					"lisi",
					"wangwu" 
			);
			var_dump ( addGroupMembers ( $group_id, $usernames ) );
			break;
		case 50 : // 群组单个减人
			$group_id = "122633509780062768";
			$username = "lisi";
			var_dump ( deleteGroupMember ( $group_id, $username ) );
			break;
		case 51 : // 群组批量减人-------
			$group_id = "122633509780062768";
			// $usernames['usernames']=array("lisi","wangwu");
			$usernames = 'lisi,wangwu';
			var_dump ( deleteGroupMembers ( $group_id, $usernames ) );
			break;
		case 52 : // 获取一个用户参与的所有群组
			var_dump ( getGroupsForUser ( "zhangsan" ) );
			break;
		case 53 : // 群组转让
			$group_id = "122633509780062768";
			$options ['newowner'] = "lisi";
			var_dump ( changeGroupOwner ( $group_id, $options ) );
			break;
		case 54 : // 查询一个群组黑名单用户名列表
			$group_id = "122633509780062768";
			var_dump ( getGroupBlackList ( $group_id ) );
			break;
		case 55 : // 群组黑名单单个加人-----
			$group_id = "122633509780062768";
			$username = "lisi";
			var_dump ( addGroupBlackMember ( $group_id, $username ) );
			break;
		case 56 : // 群组黑名单批量加人
			$group_id = "122633509780062768";
			$usernames ['usernames'] = array (
					"lisi",
					"wangwu" 
			);
			var_dump ( addGroupBlackMembers ( $group_id, $usernames ) );
			break;
		case 57 : // 群组黑名单单个减人
			$group_id = "122633509780062768";
			$username = "lisi";
			var_dump ( deleteGroupBlackMember ( $group_id, $username ) );
			break;
		case 58 : // 群组黑名单批量减人
			$group_id = "122633509780062768";
			$usernames ['usernames'] = array (
					"lisi",
					"wangwu" 
			);
			var_dump ( deleteGroupBlackMembers ( $group_id, $usernames ) );
			break;
		case 59 : // 创建聊天室
			$options ['name'] = "chatroom001";
			$options ['description'] = "this is a love chatroom";
			$options ['maxusers'] = 300;
			$options ['owner'] = "zhangsan";
			$options ['members'] = Array (
					"wangwu",
					"lisi" 
			);
			var_dump ( createChatRoom ( $options ) );
			break;
		case 60 : // 修改聊天室信息
			$chatroom_id = "124121390293975664";
			$options ['name'] = "chatroom002";
			$options ['description'] = "修改聊天室描述";
			$options ['maxusers'] = 300;
			var_dump ( modifyChatRoom ( $chatroom_id, $options ) );
			break;
		case 61 : // 删除聊天室
			$chatroom_id = "124121390293975664";
			var_dump ( deleteChatRoom ( $chatroom_id ) );
			break;
		case 62 : // 获取app中所有的聊天室
			var_dump ( getChatRooms () );
			break;
		case 63 : // 获取一个聊天室的详情
			$chatroom_id = "124121939693277716";
			var_dump ( getChatRoomDetail ( $chatroom_id ) );
			break;
		case 64 : // 获取一个用户加入的所有聊天室
			var_dump ( getChatRoomJoined ( "zhangsan" ) );
			break;
		case 65 : // 聊天室单个成员添加-----
			$chatroom_id = "124121939693277716";
			$username = "zhangsan";
			var_dump ( addChatRoomMember ( $chatroom_id, $username ) );
			break;
		case 66 : // 聊天室批量成员添加
			$chatroom_id = "124121939693277716";
			$usernames ['usernames'] = array (
					'wangwu',
					'lisi' 
			);
			var_dump ( addChatRoomMembers ( $chatroom_id, $usernames ) );
			break;
		case 67 : // 聊天室单个成员删除
			$chatroom_id = "124121939693277716";
			$username = "zhangsan";
			var_dump ( deleteChatRoomMember ( $chatroom_id, $username ) );
			break;
		case 68 : // 聊天室批量成员删除---
			$chatroom_id = "124121939693277716";
			// $usernames['usernames']=array('zhangsan','lisi');
			$usernames = 'zhangsan,lisi';
			var_dump ( deleteChatRoomMembers ( $chatroom_id, $usernames ) );
			break;
		case 69 : // 导出聊天记录-------不分页
			$ql = "select+*+where+timestamp>1435536480000";
			var_dump ( getChatRecord ( $ql ) );
			break;
		case 70 : // 导出聊天记录-------分页
			$ql = "select+*+where+timestamp>1435536480000";
			$cursor = readCursor ( "chatfile.txt" );
			// var_dump($cursor);
			var_dump ( getChatRecordForPage ( $ql, 10, $cursor ) );
			break;
	}
}
// ------------------------------------------------------用户体系
/**
 * *获取token
 */
function getToken() {
	// 将外面的变量变成全局
	global $client_id, $client_secret;
	
	$options = array (
			"grant_type" => "client_credentials",
			"client_id" => $client_id,
			"client_secret" => $client_secret 
	);
	// json_encode()函数，可将PHP数组或对象转成json字符串，使用json_decode()函数，可以将json字符串转换为PHP数组或对象
	$body = json_encode ( $options );
	// 使用 $GLOBALS 替代 global
	$url = $GLOBALS ['base_url'] . 'token';
	// $url=$base_url.'token';
	
	$header = array (
			"Content-Type" => "application/json" 
	);
	
	$tokenResult = postCurl ( $url, $body, $header );
	// var_dump($tokenResult['expires_in']);
	// return $tokenResult;
	return "Authorization:Bearer " . $tokenResult ["access_token"];
	
	// return "Authorization:Bearer YWMtG_u2OH1tEeWK7IWc3Nx2ygAAAVHjWllhTpavYYyhaI_WzIcHIQ9uitTvsmw";
}
/**
 * 授权注册
 */
function createUser($username, $password) {
	$url = $GLOBALS ['base_url'] . 'users';
	$options = array (
			"username" => $username,
			"password" => $password 
	);
	$body = json_encode ( $options );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $body, $header );
	return $result;
}
/*
 * 批量注册用户
 */
function createUsers($options) {
	$url = $GLOBALS ['base_url'] . 'users';
	
	$body = json_encode ( $options );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $body, $header );
	return $result;
}
/*
 * 重置用户密码
 */
function resetPassword($username, $newpassword) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/password';
	$options = array (
			"newpassword" => $newpassword 
	);
	$body = json_encode ( $options );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $body, $header, "PUT" );
	return $result;
}

/*
 * 获取单个用户
 */
function getUser($username) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, "GET" );
	return $result;
}
/*
 * 获取批量用户----不分页
 */
function getUsers($limit = 0) {
	if (! empty ( $limit )) {
		$url = $GLOBALS ['base_url'] . 'users?limit=' . $limit;
	} else {
		$url = $GLOBALS ['base_url'] . 'users';
	}
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, "GET" );
	return $result;
}
/*
 * 获取批量用户---分页
 */
function getUsersForPage($limit = 0, $cursor = '') {
	$url = $GLOBALS ['base_url'] . 'users?limit=' . $limit . '&cursor=' . $cursor;
	
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, "GET" );
	if (! empty ( $result ["cursor"] )) {
		$cursor = $result ["cursor"];
		writeCursor ( "userfile.txt", $cursor );
	}
	// var_dump($GLOBALS['cursor'].'00000000000000');
	return $result;
}

// 创建文件夹
function mkdirs($dir, $mode = 0777) {
	if (is_dir ( $dir ) || @mkdir ( $dir, $mode ))
		return TRUE;
	if (! mkdirs ( dirname ( $dir ), $mode ))
		return FALSE;
	return @mkdir ( $dir, $mode );
}
// 写入cursor
function writeCursor($filename, $content) {
	// 判断文件夹是否存在，不存在的话创建
	if (! file_exists ( "resource/txtfile" )) {
		mkdirs ( "resource/txtfile" );
	}
	$myfile = @fopen ( "resource/txtfile/" . $filename, "w+" ) or die ( "Unable to open file!" );
	@fwrite ( $myfile, $content );
	fclose ( $myfile );
}
// 读取cursor
function readCursor($filename) {
	// 判断文件夹是否存在，不存在的话创建
	if (! file_exists ( "resource/txtfile" )) {
		mkdirs ( "resource/txtfile" );
	}
	$file = "resource/txtfile/" . $filename;
	$fp = fopen ( $file, "a+" ); // 这里这设置成a+
	if ($fp) {
		while ( ! feof ( $fp ) ) {
			// 第二个参数为读取的长度
			$data = fread ( $fp, 1000 );
		}
		fclose ( $fp );
	}
	return $data;
}
/*
 * 删除单个用户
 */
function deleteUser($username) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'DELETE' );
	return $result;
}
/*
 * 删除批量用户
 * limit:建议在100-500之间，、
 * 注：具体删除哪些并没有指定, 可以在返回值中查看。
 */
function deleteUsers($limit) {
	$url = $GLOBALS ['base_url'] . 'users?limit=' . $limit;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'DELETE' );
	return $result;
}
/*
 * 修改用户昵称
 */
function editNickname($username, $nickname) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username;
	$options = array (
			"nickname" => $nickname 
	);
	$body = json_encode ( $options );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $body, $header, 'PUT' );
	return $result;
}
/*
 * 添加好友
 */
function addFriend($username, $friend_name) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/contacts/users/' . $friend_name;
	$header = array (
			getToken (),
			'Content-Type:application/json' 
	);
	$result = postCurl ( $url, '', $header, 'POST' );
	return $result;
}

/*
 * 删除好友
 */
function deleteFriend($username, $friend_name) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/contacts/users/' . $friend_name;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'DELETE' );
	return $result;
}
/*
 * 查看好友
 */
function showFriends($username) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/contacts/users';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
/*
 * 查看用户黑名单
 */
function getBlacklist($username) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/blocks/users';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
/*
 * 往黑名单中加人
 */
function addUserForBlacklist($username, $usernames) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/blocks/users';
	$body = json_encode ( $usernames );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $body, $header, 'POST' );
	return $result;
}
/*
 * 从黑名单中减人
 */
function deleteUserFromBlacklist($username, $blocked_name) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/blocks/users/' . $blocked_name;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'DELETE' );
	return $result;
}
/*
 * 查看用户是否在线
 */
function isOnline($username) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/status';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
/*
 * 查看用户离线消息数
 */
function getOfflineMessages($username) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/offline_msg_count';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
/*
 * 查看某条消息的离线状态
 * ----deliverd 表示此用户的该条离线消息已经收到
 */
function getOfflineMessageStatus($username, $msg_id) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/offline_msg_status/' . $msg_id;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
/*
 * 禁用用户账号
 */
function deactiveUser($username) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/deactivate';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header );
	return $result;
}
/*
 * 解禁用户账号
 */
function activeUser($username) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/activate';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header );
	return $result;
}

/*
 * 强制用户下线
 */
function disconnectUser($username) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/disconnect';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
// --------------------------------------------------------上传下载
/*
 * 上传图片或文件
 */
function uploadFile($filePath) {
	$url = $GLOBALS ['base_url'] . 'chatfiles';
	$file = file_get_contents ( $filePath );
	$body ['file'] = $file;
	$header = array (
			'enctype:multipart/form-data',
			getToken (),
			"restrict-access:true" 
	);
	$result = postCurl ( $url, $body, $header, 'XXX' );
	return $result;
}
/*
 * 下载文件或图片
 */
function downloadFile($uuid, $shareSecret) {
	$url = $GLOBALS ['base_url'] . 'chatfiles/' . $uuid;
	$header = array (
			"share-secret:" . $shareSecret,
			"Accept:application/octet-stream",
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	$filename = md5 ( time () . mt_rand ( 10, 99 ) ) . ".png"; // 新图片名称
	if (! file_exists ( "resource/down" )) {
		// mkdir("../image/down");
		mkdirs ( "resource/down/" );
	}
	
	$file = @fopen ( "resource/down/" . $filename, "w+" ); // 打开文件准备写入
	@fwrite ( $file, $result ); // 写入
	fclose ( $file ); // 关闭
	return $filename;
}
/*
 * 下载图片缩略图
 */
function downloadThumbnail($uuid, $shareSecret) {
	$url = $GLOBALS ['base_url'] . 'chatfiles/' . $uuid;
	$header = array (
			"share-secret:" . $shareSecret,
			"Accept:application/octet-stream",
			getToken (),
			"thumbnail:true" 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	$filename = md5 ( time () . mt_rand ( 10, 99 ) ) . "th.png"; // 新图片名称
	if (! file_exists ( "resource/down" )) {
		// mkdir("../image/down");
		mkdirs ( "resource/down/" );
	}
	
	$file = @fopen ( "resource/down/" . $filename, "w+" ); // 打开文件准备写入
	@fwrite ( $file, $result ); // 写入
	fclose ( $file ); // 关闭
	return $filename;
}

// --------------------------------------------------------发送消息
/*
 * 发送文本消息
 */
function sendText($from = "admin", $target_type, $target, $content, $ext) {
	$url = $GLOBALS ['base_url'] . 'messages';
	$body ['target_type'] = $target_type;
	$body ['target'] = $target;
	$options ['type'] = "txt";
	$options ['msg'] = $content;
	$body ['msg'] = $options;
	$body ['from'] = $from;
	$body ['ext'] = $ext;
	$b = json_encode ( $body );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $b, $header );
	return $result;
}
/*
 * 发送透传消息
 */
function sendCmd($from = "admin", $target_type, $target, $action, $ext) {
	$url = $GLOBALS ['base_url'] . 'messages';
	$body ['target_type'] = $target_type;
	$body ['target'] = $target;
	$options ['type'] = "cmd";
	$options ['action'] = $action;
	$body ['msg'] = $options;
	$body ['from'] = $from;
	$body ['ext'] = $ext;
	$b = json_encode ( $body );
	$header = array (
			getToken () 
	);
	// $b=json_encode($body,true);
	$result = postCurl ( $url, $b, $header );
	return $result;
}
/*
 * 发图片消息
 */
function sendImage($filePath, $from = "admin", $target_type, $target, $filename, $ext) {
	$result = uploadFile ( $filePath );
	$uri = $result ['uri'];
	$uuid = $result ['entities'] [0] ['uuid'];
	$shareSecret = $result ['entities'] [0] ['share-secret'];
	$url = $GLOBALS ['base_url'] . 'messages';
	$body ['target_type'] = $target_type;
	$body ['target'] = $target;
	$options ['type'] = "img";
	$options ['url'] = $uri . '/' . $uuid;
	$options ['filename'] = $filename;
	$options ['secret'] = $shareSecret;
	$options ['size'] = array (
			"width" => 480,
			"height" => 720 
	);
	$body ['msg'] = $options;
	$body ['from'] = $from;
	$body ['ext'] = $ext;
	$b = json_encode ( $body );
	$header = array (
			getToken () 
	);
	// $b=json_encode($body,true);
	$result = postCurl ( $url, $b, $header );
	return $result;
}
/*
 * 发语音消息
 */
function sendAudio($filePath, $from = "admin", $target_type, $target, $filename, $length, $ext) {
	$result = uploadFile ( $filePath );
	$uri = $result ['uri'];
	$uuid = $result ['entities'] [0] ['uuid'];
	$shareSecret = $result ['entities'] [0] ['share-secret'];
	$url = $GLOBALS ['base_url'] . 'messages';
	$body ['target_type'] = $target_type;
	$body ['target'] = $target;
	$options ['type'] = "audio";
	$options ['url'] = $uri . '/' . $uuid;
	$options ['filename'] = $filename;
	$options ['length'] = $length;
	$options ['secret'] = $shareSecret;
	$body ['msg'] = $options;
	$body ['from'] = $from;
	$body ['ext'] = $ext;
	$b = json_encode ( $body );
	$header = array (
			getToken () 
	);
	// $b=json_encode($body,true);
	$result = postCurl ( $url, $b, $header );
	return $result;
}
/*
 * 发视频消息
 */
function sendVedio($filePath, $from = "admin", $target_type, $target, $filename, $length, $thumb, $thumb_secret, $ext) {
	$result = uploadFile ( $filePath );
	$uri = $result ['uri'];
	$uuid = $result ['entities'] [0] ['uuid'];
	$shareSecret = $result ['entities'] [0] ['share-secret'];
	$url = $GLOBALS ['base_url'] . 'messages';
	$body ['target_type'] = $target_type;
	$body ['target'] = $target;
	$options ['type'] = "video";
	$options ['url'] = $uri . '/' . $uuid;
	$options ['filename'] = $filename;
	$options ['thumb'] = $thumb;
	$options ['length'] = $length;
	$options ['secret'] = $shareSecret;
	$options ['thumb_secret'] = $thumb_secret;
	$body ['msg'] = $options;
	$body ['from'] = $from;
	$body ['ext'] = $ext;
	$b = json_encode ( $body );
	$header = array (
			getToken () 
	);
	// $b=json_encode($body,true);
	$result = postCurl ( $url, $b, $header );
	return $result;
}
/*
 * 发文件消息
 */
function sendFile($filePath, $from = "admin", $target_type, $target, $filename, $length, $ext) {
	$result = uploadFile ( $filePath );
	$uri = $result ['uri'];
	$uuid = $result ['entities'] [0] ['uuid'];
	$shareSecret = $result ['entities'] [0] ['share-secret'];
	$url = $GLOBALS ['base_url'] . 'messages';
	$body ['target_type'] = $target_type;
	$body ['target'] = $target;
	$options ['type'] = "file";
	$options ['url'] = $uri . '/' . $uuid;
	$options ['filename'] = $filename;
	$options ['length'] = $length;
	$options ['secret'] = $shareSecret;
	$body ['msg'] = $options;
	$body ['from'] = $from;
	$body ['ext'] = $ext;
	$b = json_encode ( $body );
	$header = array (
			getToken () 
	);
	// $b=json_encode($body,true);
	$result = postCurl ( $url, $b, $header );
	return $result;
}
// -------------------------------------------------------------群组操作

/*
 * 获取app中的所有群组----不分页
 */
function getGroups($limit = 0) {
	if (! empty ( $limit )) {
		$url = $GLOBALS ['base_url'] . 'chatgroups?limit=' . $limit;
	} else {
		$url = $GLOBALS ['base_url'] . 'chatgroups';
	}
	
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, "GET" );
	return $result;
}
/*
 * 获取app中的所有群组---分页
 */
function getGroupsForPage($limit = 0, $cursor = '') {
	$url = $GLOBALS ['base_url'] . 'chatgroups?limit=' . $limit . '&cursor=' . $cursor;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, "GET" );
	
	if (! empty ( $result ["cursor"] )) {
		$cursor = $result ["cursor"];
		writeCursor ( "groupfile.txt", $cursor );
	}
	// var_dump($GLOBALS['cursor'].'00000000000000');
	return $result;
}
/*
 * 获取一个或多个群组的详情
 */
function getGroupDetail($group_ids) {
	$g_ids = implode ( ',', $group_ids );
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $g_ids;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
/*
 * 创建一个群组
 */
function createGroup($options) {
	$url = $GLOBALS ['base_url'] . 'chatgroups';
	$header = array (
			getToken () 
	);
	$body = json_encode ( $options );
	$result = postCurl ( $url, $body, $header );
	return $result;
}
/*
 * 修改群组信息
 */
function modifyGroupInfo($group_id, $options) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id;
	$body = json_encode ( $options );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $body, $header, 'PUT' );
	return $result;
}
/*
 * 删除群组
 */
function deleteGroup($group_id) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'DELETE' );
	return $result;
}
/*
 * 获取群组中的成员
 */
function getGroupUsers($group_id) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id . '/users';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
/*
 * 群组单个加人
 */
function addGroupMember($group_id, $username) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id . '/users/' . $username;
	$header = array (
			getToken (),
			'Content-Type:application/json' 
	);
	$result = postCurl ( $url, '', $header );
	return $result;
}
/*
 * 群组批量加人
 */
function addGroupMembers($group_id, $usernames) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id . '/users';
	$body = json_encode ( $usernames );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $body, $header );
	return $result;
}
/*
 * 群组单个减人
 */
function deleteGroupMember($group_id, $username) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id . '/users/' . $username;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'DELETE' );
	return $result;
}
/*
 * 群组批量减人
 */
function deleteGroupMembers($group_id, $usernames) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id . '/users/' . $usernames;
	// $body=json_encode($usernames);
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'DELETE' );
	return $result;
}
/*
 * 获取一个用户参与的所有群组
 */
function getGroupsForUser($username) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/joined_chatgroups';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
/*
 * 群组转让
 */
function changeGroupOwner($group_id, $options) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id;
	$body = json_encode ( $options );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $body, $header, 'PUT' );
	return $result;
}
/*
 * 查询一个群组黑名单用户名列表
 */
function getGroupBlackList($group_id) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id . '/blocks/users';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
/*
 * 群组黑名单单个加人
 */
function addGroupBlackMember($group_id, $username) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id . '/blocks/users/' . $username;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header );
	return $result;
}
/*
 * 群组黑名单批量加人
 */
function addGroupBlackMembers($group_id, $usernames) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id . '/blocks/users';
	$body = json_encode ( $usernames );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $body, $header );
	return $result;
}
/*
 * 群组黑名单单个减人
 */
function deleteGroupBlackMember($group_id, $username) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id . '/blocks/users/' . $username;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'DELETE' );
	return $result;
}
/*
 * 群组黑名单批量减人
 */
function deleteGroupBlackMembers($group_id, $usernames) {
	$url = $GLOBALS ['base_url'] . 'chatgroups/' . $group_id . '/blocks/users';
	$body = json_encode ( $usernames );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $body, $header, 'DELETE' );
	return $result;
}
// -------------------------------------------------------------聊天室操作
/*
 * 创建聊天室
 */
function createChatRoom($options) {
	$url = $GLOBALS ['base_url'] . 'chatrooms';
	$header = array (
			getToken () 
	);
	$body = json_encode ( $options );
	$result = postCurl ( $url, $body, $header );
	return $result;
}
/*
 * 修改聊天室信息
 */
function modifyChatRoom($chatroom_id, $options) {
	$url = $GLOBALS ['base_url'] . 'chatrooms/' . $chatroom_id;
	$body = json_encode ( $options );
	$result = postCurl ( $url, $body, $header, 'PUT' );
	return $result;
}
/*
 * 删除聊天室
 */
function deleteChatRoom($chatroom_id) {
	$url = $GLOBALS ['base_url'] . 'chatrooms/' . $chatroom_id;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'DELETE' );
	return $result;
}
/*
 * 获取app中所有的聊天室
 */
function getChatRooms() {
	$url = $GLOBALS ['base_url'] . 'chatrooms';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, "GET" );
	return $result;
}

/*
 * 获取一个聊天室的详情
 */
function getChatRoomDetail($chatroom_id) {
	$url = $GLOBALS ['base_url'] . 'chatrooms/' . $chatroom_id;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
/*
 * 获取一个用户加入的所有聊天室
 */
function getChatRoomJoined($username) {
	$url = $GLOBALS ['base_url'] . 'users/' . $username . '/joined_chatrooms';
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'GET' );
	return $result;
}
/*
 * 聊天室单个成员添加
 */
function addChatRoomMember($chatroom_id, $username) {
	$url = $GLOBALS ['base_url'] . 'chatrooms/' . $chatroom_id . '/users/' . $username;
	$header = array (
			getToken (),
			'Content-Type:application/json' 
	);
	$result = postCurl ( $url, '', $header );
	return $result;
}
/*
 * 聊天室批量成员添加
 */
function addChatRoomMembers($chatroom_id, $usernames) {
	$url = $GLOBALS ['base_url'] . 'chatrooms/' . $chatroom_id . '/users';
	$body = json_encode ( $usernames );
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, $body, $header );
	return $result;
}
/*
 * 聊天室单个成员删除
 */
function deleteChatRoomMember($chatroom_id, $username) {
	$url = $GLOBALS ['base_url'] . 'chatrooms/' . $chatroom_id . '/users/' . $username;
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'DELETE' );
	return $result;
}
/*
 * 聊天室批量成员删除
 */
function deleteChatRoomMembers($chatroom_id, $usernames) {
	$url = $GLOBALS ['base_url'] . 'chatrooms/' . $chatroom_id . '/users/' . $usernames;
	// $body=json_encode($usernames);
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, 'DELETE' );
	return $result;
}
// -------------------------------------------------------------聊天记录

/*
 * 导出聊天记录----不分页
 */
function getChatRecord($ql) {
	if (! empty ( $ql )) {
		$url = $GLOBALS ['base_url'] . 'chatmessages?ql=' . $ql;
	} else {
		$url = $GLOBALS ['base_url'] . 'chatmessages';
	}
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, "GET" );
	return $result;
}
/*
 * 导出聊天记录---分页
 */
function getChatRecordForPage($ql, $limit = 0, $cursor) {
	if (! empty ( $ql )) {
		$url = $GLOBALS ['base_url'] . 'chatmessages?ql=' . $ql . '&limit=' . $limit . '&cursor=' . $cursor;
	}
	$header = array (
			getToken () 
	);
	$result = postCurl ( $url, '', $header, "GET" );
	$cursor = $result ["cursor"];
	writeCursor ( "chatfile.txt", $cursor );
	// var_dump($GLOBALS['cursor'].'00000000000000');
	return $result;
}

/**
 * postCurl方法
 */
function postCurl($url, $body, $header, $type = "POST") {
	// 1.创建一个curl资源
	$ch = curl_init ();
	// 2.设置URL和相应的选项
	curl_setopt ( $ch, CURLOPT_URL, $url ); // 设置url
	                                        // 1)设置请求头
	                                        // array_push($header, 'Accept:application/json');
	                                        // array_push($header,'Content-Type:application/json');
	                                        // array_push($header, 'http:multipart/form-data');
	                                        // 设置为false,只会获得响应的正文(true的话会连响应头一并获取到)
	curl_setopt ( $ch, CURLOPT_HEADER, 0 );
	curl_setopt ( $ch, CURLOPT_TIMEOUT, 5 ); // 设置超时限制防止死循环
	                                         // 设置发起连接前的等待时间，如果设置为0，则无限等待。
	curl_setopt ( $ch, CURLOPT_CONNECTTIMEOUT, 5 );
	// 将curl_exec()获取的信息以文件流的形式返回，而不是直接输出。
	curl_setopt ( $ch, CURLOPT_RETURNTRANSFER, true );
	// 2)设备请求体
	if (count ( $body ) > 0) {
		// $b=json_encode($body,true);
		curl_setopt ( $ch, CURLOPT_POSTFIELDS, $body ); // 全部数据使用HTTP协议中的"POST"操作来发送。
	}
	// 设置请求头
	if (count ( $header ) > 0) {
		curl_setopt ( $ch, CURLOPT_HTTPHEADER, $header );
	}
	// 上传文件相关设置
	curl_setopt ( $ch, CURLOPT_FOLLOWLOCATION, true );
	curl_setopt ( $ch, CURLOPT_MAXREDIRS, 3 );
	curl_setopt ( $ch, CURLOPT_SSL_VERIFYPEER, false ); // 对认证证书来源的检查
	curl_setopt ( $ch, CURLOPT_SSL_VERIFYHOST, 0 ); // 从证书中检查SSL加密算
	                                                
	// 3)设置提交方式
	switch ($type) {
		case "GET" :
			curl_setopt ( $ch, CURLOPT_HTTPGET, true );
			break;
		case "POST" :
			curl_setopt ( $ch, CURLOPT_POST, true );
			break;
		case "PUT" : // 使用一个自定义的请求信息来代替"GET"或"HEAD"作为HTTP请 求。这对于执行"DELETE" 或者其他更隐蔽的HTT
			curl_setopt ( $ch, CURLOPT_CUSTOMREQUEST, "PUT" );
			break;
		case "DELETE" :
			curl_setopt ( $ch, CURLOPT_CUSTOMREQUEST, "DELETE" );
			break;
	}
	
	// 4)在HTTP请求中包含一个"User-Agent: "头的字符串。-----必设
	
	curl_setopt ( $ch, CURLOPT_USERAGENT, 'SSTS Browser/1.0' );
	curl_setopt ( $ch, CURLOPT_ENCODING, 'gzip' );
	
	curl_setopt ( $ch, CURLOPT_USERAGENT, 'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)' ); // 模拟用户使用的浏览器
	                                                                                                           // 5)
	                                                                                                           
	// 3.抓取URL并把它传递给浏览器
	$res = curl_exec ( $ch );
	
	$result = json_decode ( $res, true );
	// 4.关闭curl资源，并且释放系统资源
	curl_close ( $ch );
	if (empty ( $result ))
		return $res;
	else
		return $result;
}

?>

<?php

require_once '../format.php';
require_once '../db.php';
require_once '../table.php';
require_once 'util.php';


@$username = $_GET['username'];// 用户名，用于返回是否赞了主贴
@$classid = $_GET['classId'];//班级id，用于哪些人可见
@$infotype = $_GET['infoType'];//信息类型，用于区分版块
@$lastId = $_GET['lastId']; //  获取最后一次获取数据的id
@$start = $_GET['start'];//从第几条开始取
@$count = $_GET['count'];//需要取多少条
if ($start != 0){
	$start = $count;
}

$link = DataBaseUtil::getInstance()->connect();
mysqli_set_charset($link, "utf8"); // 设置编码为utf8

if ($link->errno != 0) { // 数据库连接失败
	Response::json_response ( - 1, "数据库连接失败!", null );
	exit ();
}

// 取出数据，并做逆序处理
$query = "select * from ".TABLE_MAIN." where classid = "
		.$classid." and infotype = "
				.$infotype." and mainid <= ".$lastId." order by time desc limit ".$start.",".$count;

$result = mysqli_query($link, $query);
$num = mysqli_affected_rows($link);
$arr = array();
$i = 0;
while (@$row = mysqli_fetch_array($result)){
	$arr[$i]['mainid'] = $row['mainid']; 	
	$arr[$i]['classid'] = $row['classid']; 	
	$arr[$i]['username'] = $row['username']; 	
	$arr[$i]['time'] = $row['time']; 	
	$arr[$i]['infotype'] = $row['infotype']; 	
	$arr[$i]['content'] = $row['content']; 
	$arr[$i]['user'] = getUser($row['username']);
	$arr[$i]['isIPraised'] = getPraiseInfo($username, $row['mainid']);
	$arr[$i]['commentCount'] = getInfoCount($row['mainid'],TABLE_COMMENT);//获取评论数
	$arr[$i]['praiseCount'] = getInfoCount($row['mainid'],TABLE_PRAISE);//获取赞数
	$arr[$i]['commentInfo'] = getCommentInfo($row['mainid']);
// 	$arr[$i]['lastid'] = getLastMainId();  
// 	$arr[$i]['num'] = $num;
	$arr[$i]['picUrls'] = Util::getPicInfo($row['mainid']);
	$arr[$i]['videoUrl'] = Util::getVideoInfo($row['mainid']);
	$i++;
}
if ($i == 0){
	Response::json_response(-1,"没有更多信息",null);
}else{
	Response::json_response(0,"获取成功！",$arr);
}


/**
 * 获取用户是否点赞
 * @param unknown $username	用户名
 * @param unknown $mainid	主贴id
 * @return boolean
 */
function getPraiseInfo($username,$mainId){
	$link = DataBaseUtil::getInstance()->connect();
	$query = "select * from " . TABLE_PRAISE . " where username = '".$username. "' and mainid = " . $mainId;
	mysqli_query($link, $query);
	$num = mysqli_affected_rows($link);
	if ($num == 0){
		return false;
	}
	return true;
}

/**
 * 获取主贴评论信息
 * @param string $mainid	主贴id
 * @return NULL|unknown[]|NULL
 */
function getCommentInfo($mainid = ''){
	$link = DataBaseUtil::getInstance()->connect();
	mysqli_set_charset($link, "utf8"); // 设置编码为utf8
	$query = "select * from ".TABLE_COMMENT." where mainid = ".$mainid;
	$result = mysqli_query($link, $query);
	$arr = array();
	$i = 0;
	while (@$row = mysqli_fetch_array($result)){
		$arr[$i]['infoid'] = $row['infoid']; //信息id 
		$arr[$i]['mainid'] = $row['mainid']; //条目id
		$arr[$i]['username'] = $row['username'];// 用户名
		$arr[$i]['time'] = $row['time'];
		$arr[$i]['reply'] = $row['reply']; // 回复人id
		$arr[$i]['content'] = $row['content']; // 评论内容
		$arr[$i]['commentUser'] = getUser($row['username']); // 评论人信息
		$arr[$i]['replyUser'] = getUser($row['reply']); // 回复给的人信息
		$i++;
	}
	if ($i == 0){
		return null;
	}
	return $arr;
}


/**
 * 获取发帖用户信息
 * @param string $username	用户名
 * @return unknown[]|NULL	用户信息数组
 */
function getUser($username = ''){
	$link = DataBaseUtil::getInstance()->connect();
	mysqli_set_charset($link, "utf8"); // 设置编码为utf8
	$query = "select * from ".TABLE_USER." where username = '".$username."'";
	$result = mysqli_query($link, $query);
	@$row = mysqli_fetch_array($result);
	if ($row){
		$arr = array(
				'username' => $row['username'],
				'nickname' => $row['nickname'],
				'avatar' => $row['avatar']
		);
		return $arr;
	}else{
		return null;
	}
}

/**
 * 获取赞或者评论数
 * @param string $mainid	主贴id
 * @return unknown			返回数目
 */
function getInfoCount($mainid,$tableName){
	$link = DataBaseUtil::getInstance()->connect();
	$query = "select * from ".$tableName." where mainid = ".$mainid;
	$result = mysqli_query($link, $query);
	$row = mysqli_affected_rows($link);
	return $row;
}

/**
 * 获取第一行的MainId
 * @param unknown $limit
 * @return unknown
 */
function getLastMainId(){
	$link = DataBaseUtil::getInstance()->connect();
	$query = "select mainid from ".TABLE_MAIN." order by time desc limit 0,1";
	$result = mysqli_query($link, $query);
	@$row = mysqli_fetch_array($result);
	$mainid = $row['mainid'];
	return $mainid;
}

?>


<?php
require_once '../table.php';
require_once '../db.php';
require_once '../format.php';
class Util {
	/**
	 * 获取用户是否点赞
	 * 
	 * @param unknown $username
	 *        	用户名
	 * @param unknown $mainid
	 *        	主贴id
	 * @return boolean
	 */
	public static function getPraiseInfo($username, $mainId) {
		$link = DataBaseUtil::getInstance ()->connect ();
		$query = "select * from " . TABLE_PRAISE . " where username = '".$username."' and mainid = " . $mainId;
		mysqli_query ( $link, $query );
		$num = mysqli_affected_rows ( $link );
		if ($num == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取主贴评论信息
	 * 
	 * @param string $mainid
	 *        	主贴id
	 * @return NULL|unknown[]|NULL
	 */
	public static function getCommentInfo($mainid = '') {
		$link = DataBaseUtil::getInstance ()->connect ();
		mysqli_set_charset ( $link, "utf8" ); // 设置编码为utf8
		$query = "select * from " . TABLE_COMMENT . " where mainid = " . $mainid;
		$result = mysqli_query ( $link, $query );
		$arr = array ();
		$i = 0;
		while ( @$row = mysqli_fetch_array ( $result ) ) {
			$arr [$i] ['infoid'] = $row ['infoid']; // 信息id
			$arr [$i] ['mainid'] = $row ['mainid']; // 条目id
			$arr [$i] ['username'] = $row ['username']; // 用户名
			$arr [$i] ['time'] = $row ['time'];
			$arr [$i] ['reply'] = $row ['reply']; // 回复人id
			$arr [$i] ['content'] = $row ['content']; // 评论内容
			$arr [$i] ['commentUser'] = getUser ( $row ['username'] ); // 评论人信息
			$arr [$i] ['replyUser'] = getUser ( $row ['reply'] ); // 回复给的人信息
			$i ++;
		}
		if ($i == 0) {
			return null;
		}
		return $arr;
	}
	
	/**
	 * 获取发帖用户信息
	 * 
	 * @param string $username
	 *        	用户名
	 * @return unknown[]|NULL 用户信息数组
	 */
	public static function getUser($username = '') {
		$link = DataBaseUtil::getInstance ()->connect ();
		mysqli_set_charset ( $link, "utf8" ); // 设置编码为utf8
		$query = "select * from " . TABLE_USER . " where username = '".$username."'";
		$result = mysqli_query ( $link, $query );
		@$row = mysqli_fetch_array ( $result );
		if ($row) {
			$arr = array (
					'username' => $row ['username'],
					'nickname' => $row ['nickname'],
					'avatar' => $row ['avatar'] 
			);
			return $arr;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取赞或者评论数
	 * 
	 * @param string $mainid
	 *        	主贴id
	 * @return unknown 返回数目
	 */
	public static function getInfoCount($mainid, $tableName) {
		$link = DataBaseUtil::getInstance ()->connect ();
		$query = "select * from " . $tableName . " where mainid = " . $mainid;
		$result = mysqli_query ( $link, $query );
		$row = mysqli_affected_rows ( $link );
		return $row;
	}
	
	/**
	 * 获取社区图片信息
	 * 
	 * @param unknown $mainid        	
	 * @return unknown
	 */
	public static function getPicInfo($mainid) {
		$link = DataBaseUtil::getInstance ()->connect ();
		mysqli_set_charset ( $link, "utf8" );
		$query = "select * from " . TABLE_PIC . " where mainid = " . $mainid;
		$result = mysqli_query ( $link, $query );
		$arr = array ();
		$i = 0;
		while ( @$row = mysqli_fetch_array ( $result ) ) {
			$arr [$i] ['picid'] = $row ['picid'];
			$arr [$i] ['imageUrl'] = $row ['url'];
			$i ++;
		}
		return $arr;
	}
	
	public static function getVideoInfo($mainid) {
		$link = DataBaseUtil::getInstance ()->connect ();
		mysqli_set_charset ( $link, "utf8" );
		$query = "select * from " . TABLE_VIDEO . " where mainid = " . $mainid;
		$result = mysqli_query ( $link, $query );
		$arr = array ();
		$i = 0;
		while ( @$row = mysqli_fetch_array ( $result ) ) {
			$arr [$i] ['videoid'] = $row ['videoid'];
			$arr [$i] ['picid'] = $row ['picid'];
			$arr [$i] ['videoUrl'] = $row ['url'];
			$i ++;
		}
		return $arr;
	}
	
	/**
	 * 获取mainId
	 * @param unknown $username	用户名
	 * @param unknown $time		发布时间
	 * @param unknown $infotype	信息类型
	 * @return unknown			返回mainId主键
	 */
	public static function getMainId($username,$time,$infotype){
		$link = DataBaseUtil::getInstance()->connect();
		$query = "select mainid from ".TABLE_MAIN." where username = "
				.$username." and time = ".$time." and infotype = ".$infotype;
		$result = mysqli_query($link, $query);
		@$row = mysqli_fetch_array($result);
		return $row['mainid'];
	}
	
	/**
	 * 插入图片的url
	 * @param unknown $mainid
	 * @param unknown $url
	 * @return boolean
	 */
	public static function insertPicUrls($mainid,$url){
		$link = DataBaseUtil::getInstance()->connect();
		$query = "insert into ".TABLE_PIC." (mainid,url) values ('".$mainid."','".$url."')";
		mysqli_query($link, $query);
		$num = mysqli_affected_rows($link);
		if($num == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 插入视频url到视频表
	 */
	public static function insertVideoUrls($mainid,$picid,$url){
		$link = DataBaseUtil::getInstance()->connect();
		$query = "insert into ".TABLE_VIDEO." (mainid,picid,url) values ('".$mainid."','".$picid."','".$url."')";
		mysqli_query($link, $query);
		$num = mysqli_affected_rows($link);
		if($num == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取picid用于插入视频表
	 * @param unknown $mainid
	 * @param unknown $picUrl
	 * @return unknown
	 */
	public static function getPicId($mainid,$picUrl){
		$link = DataBaseUtil::getInstance()->connect();
		$query = "select picid from ".TABLE_PIC." where mainid = '".$mainid."' and url = '".$picUrl."'";
		$result = mysqli_query($link, $query);
		@$row = mysqli_fetch_array($result);
		return $row['picid'];
	}
}
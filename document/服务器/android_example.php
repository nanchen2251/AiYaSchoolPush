<?php
use xmpush\Builder;
use xmpush\HttpBase;
use xmpush\Sender;
use xmpush\Constants;
use xmpush\Stats;
use xmpush\Tracer;
use xmpush\Feedback;
use xmpush\DevTools;
use xmpush\Subscription;
use xmpush\TargetedMessage;
include_once(dirname(__FILE__).'/autoload.php');
require_once 'format.php';


@$classId = $_GET['classId'];
@$type = $_GET['infoType'];

XmPushUtil::pushMainInfo($classId,$type);


/**
 * 小米推送相关服务类
 * @author Administrator
 *
 */
class XmPushUtil{
	
// 	private static $_instance;
// 	private $secret = '/eC85bejfRVHaQPN7akfHQ==';
// 	private $package = 'com.example.nanchen.aiyaschoolpush';
// 	private function _construct(){
// 		// 常量设置必须在new Sender()方法之前调用
// 		Constants::setPackage($package);
// 		Constants::setSecret($secret);
// 	}
	
// 	static public function getInstance(){
// 		if (!(self::$_instance instanceof self)){
// 			self::$_instance = new self();
// 		}
// 		return self::$_instance;
// 	}
	
	public static function pushMainInfo($classId,$type){
		$secret = '/eC85bejfRVHaQPN7akfHQ==';
		$package = 'com.example.nanchen.aiyaschoolpush';
		// 常量设置必须在new Sender()方法之前调用
		Constants::setPackage($package);
		Constants::setSecret($secret);
		$description = '您收到一条新消息';
		switch ($type){
			case 1:
				$description = '您收到一条新的公告信息！';
				break;
			case 2:
				$description = '您收到一条新的作业信息！';
				break;
		}
		$aliasList = array('alias1', 'alias2');
		$title = '爱吖校推';
		$payload = '{"test":1,"ok":"It\'s a string"}';
		$sender = new Sender();	
		
		// 推送消息给相应的classid的用户
		$message = new Builder();
		$message->title($title);
		$message->description($description);
		$message->passThrough(0);
		//对于预定义点击行为，payload会通过点击进入的界面的intent中的extra字段获取，而不会调用到onReceiveMessage方法
		$message->payload($payload);
		$message->extra(Builder::notifyEffect, 1);//此处预定义点击行为，1为打开app
		$message->extra(Builder::notifyForeground, 1);
		$message->notifyId(0);
		$message->build();	
		
		$sender->broadcast($message, $classId,3);  // 发送订阅主题，这里将设置为班级classId设置为topic
		
		
// 		$targetMessage2 = new TargetedMessage();
// 		$targetMessage2->setTarget($classId, TargetedMessage::TARGET_TYPE_ALIAS);  // 采用Alias标签
// 		$targetMessage2->setMessage($message);
// 		$targetMessageList = array($targetMessage2);
// 		$sender->multiSend($targetMessageList, TargetedMessage::TARGET_TYPE_ALIAS);
	}
}







// message1 演示自定义的点击行为
// $message1 = new Builder();
// $message1->title($title);  // 通知栏的title
// $message1->description($desc); // 通知栏的descption
// $message1->passThrough(0);  // 这是一条通知栏消息，如果需要透传，把这个参数设置成1,同时去掉title和descption两个参数
// $message1->payload($payload); // 携带的数据，点击后将会通过客户端的receiver中的onReceiveMessage方法传入。
// $message1->extra(Builder::notifyForeground, 1); // 应用在前台是否展示通知，如果不希望应用在前台时候弹出通知，则设置这个参数为0
// $message1->notifyId(2); // 通知类型。最多支持0-4 5个取值范围，同样的类型的通知会互相覆盖，不同类型可以在通知栏并存
// $message1->build();
// $targetMessage = new TargetedMessage();
// $targetMessage->setTarget('alias1', TargetedMessage::TARGET_TYPE_ALIAS); // 设置发送目标。可通过regID,alias和topic三种方式发送
// $targetMessage->setMessage($message1);

// // message2 演示预定义点击行为中的点击直接打开app行为
// $message2 = new Builder();
// $message2->title($title);
// $message2->description($desc);
// $message2->passThrough(0);
// $message2->payload($payload); // 对于预定义点击行为，payload会通过点击进入的界面的intent中的extra字段获取，而不会调用到onReceiveMessage方法。
// $message2->extra(Builder::notifyEffect, 1); // 此处设置预定义点击行为，1为打开app
// $message2->extra(Builder::notifyForeground, 1);
// $message2->notifyId(0);
// $message2->build();
// $targetMessage2 = new TargetedMessage();
// $targetMessage2->setTarget('alias2', TargetedMessage::TARGET_TYPE_ALIAS);
// $targetMessage2->setMessage($message2);

// $targetMessageList = array($targetMessage, $targetMessage2);
// //print_r($sender->multiSend($targetMessageList,TargetedMessage::TARGET_TYPE_ALIAS)->getRaw());

// print_r($sender->sendToAliases($message1,$aliasList)->getRaw());
// //$stats = new Stats();
// //$startDate = '20140301';
// //$endDate = '20140312';
// //print_r($stats->getStats($startDate,$endDate)->getData());
// //$tracer = new Tracer();
// //print_r($tracer->getMessageStatusById('t1000270409640393266xW')->getRaw());

?>

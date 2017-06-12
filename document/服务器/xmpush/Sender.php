<?php
/**
 * MiPush消息发送类.
 * @author wangkuiwei
 * @name Sender
 * @desc MiPush消息发送
 *
 */
namespace xmpush;

class Sender extends HttpBase {
	
	public function __construct(){
		parent::__construct();
	}
	
	//指定regId单发消息
	public function send(Message $message,$regId,$retries=1){
		$fields = $message->getFields();
		$fields['registration_id'] = $regId;
		$url = Constants::$domain.Constants::reg_url;
		return $this->postResult($url, $fields,$retries);
	}
	
	//指定regId列表群发
	public function sendToIds(Message $message,$regIdList,$retries=1){
		$fields = $message->getFields();
		$jointRegIds = '';
		foreach($regIdList as $regId){
			if(isset($regId)){
				$jointRegIds .= $regId.Constants::$comma;
			}
		}
		$fields['registration_id'] = $jointRegIds;
		$url = Constants::$domain.Constants::reg_url;
		return $this->postResult($url, $fields,$retries);
	}
	
	//多条发送
	public function multiSend($targetMessages,$type,$retries=1){
		if($type == TargetedMessage::TARGET_TYPE_ALIAS){
			$url = Constants::$domain.Constants::multi_messages_aliases_url;
		}else if($type == TargetedMessage::TARGET_TYPE_USER_ACCOUNT){
			$url = Constants::$domain.Constants::multi_messages_user_accounts_url;
		}else{
			$url = Constants::$domain.Constants::multi_messages_regids_url;
		}
		$data = array();
		foreach($targetMessages as $targetMsg){
			array_push($data,$targetMsg->getFields());
		}
		$fields = array('messages' => json_encode($data));
		return $this->postResult($url, $fields,$retries);
	}
	
	//多条发送
	public function multiSendAtTime($targetMessages,$type,$timeToSend,$retries=1){
		if($type == TargetedMessage::TARGET_TYPE_ALIAS){
			$url = Constants::$domain.Constants::multi_messages_aliases_url;
		}else if($type == TargetedMessage::TARGET_TYPE_USER_ACCOUNT){
			$url = Constants::$domain.Constants::multi_messages_user_accounts_url;
		}else{
			$url = Constants::$domain.Constants::multi_messages_regids_url;
		}
		$data = array();
		foreach($targetMessages as $targetMsg){
			array_push($data,$targetMsg->getFields());
		}
		$fields = array('messages' => json_encode($data),'time_to_send' => $timeToSend);
		return $this->postResult($url, $fields,$retries);
	}

	//指定别名单发
	public function sendToAlias(Message $message,$alias,$retries=1){
		$fields = $message->getFields();
		$fields['alias'] = $alias;
		$url = Constants::$domain.Constants::alias_url;
		return $this->postResult($url, $fields, $retries);
	}
	
	//指定别名列表群发
	public function sendToAliases(Message $message,$aliasList,$retries=1){
		$fields = $message->getFields();
		$jointAliases = '';
		foreach($aliasList as $alias){
			if(strlen($jointAliases)>0){
				$jointAliases = $jointAliases.Constants::$comma;
			}
			$jointAliases = $jointAliases.$alias;
		}
		$fields['alias'] = $jointAliases;
		$url = Constants::$domain.Constants::alias_url;
		return $this->postResult($url, $fields, $retries);
	}

	//指定userAccount群发
	public function sendToUserAccount(Message $message,$userAccount,$retries=1){
		$fields = $message->getFields();
		$fields['user_account'] = $userAccount;
		$url = Constants::$domain.Constants::user_account_url;
		return $this->postResult($url, $fields, $retries);
	}

	//指定userAccount列表群发
	public function sendToUserAccounts(Message $message,$userAccountList,$retries=1){
		$fields = $message->getFields();
		$jointUserAccounts = '';
		foreach($userAccountList as $userAccount){
			if(strlen($jointUserAccounts)>0){
				$jointUserAccounts = $jointUserAccounts.Constants::$comma;
			}
			$jointUserAccounts = $jointUserAccounts.$userAccount;
		}
		$fields['user_account'] = $jointUserAccounts;
		$url = Constants::$domain.Constants::user_account_url;
		return $this->postResult($url, $fields, $retries);
	}
	
	//指定topic群发
	public function broadcast(Message $message,$topic,$retries=1){
		$fields = $message->getFields();
		$fields['topic'] = $topic;
		$url = Constants::$domain.Constants::topic_url;
		return $this->postResult($url, $fields, $retries);
	}
	
	//向所有设备发送消息
	public function broadcastAll(Message $message,$retries=1){
		$fields = $message->getFields();
		$url = Constants::$domain.Constants::all_url;
		return $this->postResult($url, $fields, $retries);
	}

	//广播消息，多个topic，支持topic间的交集、并集或差集
	public function multiTopicBroadcast(Message $message,$topicList,$topicOp,$retries=1){
		if(count($topicList)==1){
			return $this->broadcast($message,$topicList[0],$retries);
		}
		$fields = $message->getFields();
		$jointTopics = '';
		foreach($topicList as $topic){
			if(strlen($jointTopics)>0){
				$jointTopics = $jointTopics.Constants::$multi_topic_split;
			}
			$jointTopics = $jointTopics.$topic;
		}
		$fields['topics'] = $jointTopics;
		$fields['topic_op'] = $topicOp;
		$url = Constants::$domain.Constants::multi_topic_url;
		return $this->postResult($url, $fields, $retries);
	}

	// 检测定时任务是否存在
	public function checkScheduleJobExist($msgId,$retries=1){
		$fields = array('job_id' => $msgId);
		$url = Constants::$domain.Constants::check_schedule_job_exist;
		return $this->postResult($url, $fields, $retries);
	}

	// 删除定时任务
	public function deleteScheduleJob($msgId){
		$fields = array('job_id' => $msgId);
		$url = Constants::$domain.Constants::delete_schedule_job;
		return $this->postResult($url, $fields, $retries);
	}
}

?>

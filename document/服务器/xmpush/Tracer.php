<?php
/**
 * 消息状态追踪API.
 * @author wangkuiwei
 * @name Tracer
 * @desc 消息状态追踪API。
 *
 */
namespace xmpush;

class Tracer extends HttpBase {
	
	public function __construct(){
		parent::__construct();
	}
	
	public function getMessageStatusById($msgId,$retries=1){
		$fields = array(
					'msg_id' => $msgId
			);
		$url = Constants::$domain.Constants::message_trace_url;
		$result = $this->getResult($url, $fields, $retries);
		return $result;
	}

        public function getMessageStatusByJobKey($jobKey,$retries=1){
		$fields = array(
					'job_key' => $jobKey
			);
		$url = Constants::$domain.Constants::message_trace_url;
		$result = $this->getResult($url, $fields, $retries);
		return $result;
	}

	public function getMessagesStatusByTimeArea($beginTime,$endTime,$retries=1){
		$fields = array(
					'begin_time' => $beginTime,
					'end_time' => $endTime
			);
		$url = Constants::$domain.Constants::messages_trace_url;
		$result = $this->getResult($url, $fields, $retries);
		return $result;
	}
	
}

?>

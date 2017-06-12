<?php
/**
 * 服务器返回的结果.
 * @author wangkuiwei
 * @name Result
 * @desc Sender发送消息后，服务器返回的结果。
 *
 */
namespace xmpush;

class Result {
	private $errorCode;
	private $raw;
	
	public function __construct($jsonStr){
		$data = json_decode($jsonStr,true);
		$this->raw = $data;
		$this->errorCode = $data['code'];
	}
	
	public function getErrorCode(){
		return $this->errorCode;
	}
	
	public function getRaw(){
		return $this->raw;
	}
}

?>

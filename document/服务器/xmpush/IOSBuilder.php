<?php

/**
 * IOS设备的消息体.
 * @author wangkuiwei
 * @name IOSBuilder
 * @desc 构建发送给IOS设备的Message对象。
 *
 */
namespace xmpush;

class IOSBuilder extends Message {
	const soundUrl = 'sound_url'; 
	const badge = 'badge';
	public function __construct(){
		parent::__construct();
	}
	
	public function description($description){
		$this->description = $description;
	}
	
	public function timeToLive($ttl){
		$this->time_to_live = $ttl;
	}
	
	public function timeToSend($timeToSend){
		$this->time_to_send = $timeToSend;
	}
	
	public function soundUrl($url){
		$this->extra(IOSBuilder::soundUrl, $url);
	}

	public function badge($badge){
		$this->extra(IOSBuilder::badge, $badge);
	}

	public function extra($key,$value){
		$this->extra[$key] = $value;
	}
	
	public function build(){
		$keys = array(
				'description','time_to_live','time_to_send'		
		);
		foreach($keys as $key){
			if(isset($this->$key)){
				$this->fields[$key] = $this->$key;
				$this->json_infos[$key] = $this->$key;
			}
		}
		
		//单独处理extra
		$JsonExtra = array();
		if(count($this->extra) > 0){
			foreach($this->extra as $extraKey=>$extraValue){
				$this->fields[Message::EXTRA_PREFIX.$extraKey] = $extraValue;
				$JsonExtra[$extraKey] = $extraValue;
			}
		}
		$this->json_infos['extra'] = $JsonExtra;
	}
}

?>

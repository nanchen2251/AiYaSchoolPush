<?php

/**
 * 消息体.
 * @author wangkuiwei
 * @name Message
 * @desc 构建要发送的消息内容。
 *
 */
namespace xmpush;

class Message {
	const EXTRA_PREFIX = 'extra.';
	protected $payload;				//消息内容
	protected $restricted_package_name;	        //支持多包名
	protected $pass_through;			//是否透传给app(1 透传 0 通知栏信息)
	protected $notify_type;				//通知类型 可组合 (-1 Default_all,1 Default_sound,2 Default_vibrate(震动),4 Default_lights)
	protected $notify_id;				//0-4同一个notifyId在通知栏只会保留一条
	protected $extra;					//可选项，额外定义一些key value（字符不能超过1024，key不能超过10个）
	protected $description;				//在通知栏的描述，长度小于128
	protected $title;					//在通知栏的标题，长度小于16
	protected $time_to_live;			//可选项，当用户离线是，消息保留时间，默认两周，单位ms
	protected $time_to_send;			//可选项，定时发送消息，用自1970年1月1日以来00:00:00.0 UTC时间表示（以毫秒为单位的时间）。
	
	protected $fields;				//含有本条消息所有属性的数组
	protected $json_infos;
	
	/* IOS 使用 */
	protected $sound_url;			//可选，消息铃声
	protected $badge;				//可选，自定义通知数字角标
	

	public function __construct(){
	    $this->extra = array();
	    $this->fields = array();
	}
	
	public function getFields(){
		return $this->fields;
	}

	public function getJSONInfos(){
		return $this->json_infos;
	}
	
}

?>

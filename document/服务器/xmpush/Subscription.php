<?php
/**
 * 订阅/取消订阅标签.
 * @author wangkuiwei
 * @name Subscription
 *
 */
namespace xmpush;

class Subscription extends HttpBase {
	
	public function __construct(){
		parent::__construct();
	}
	
        public function subscribe($regId, $topic,$retries=1){
		$fields = array('registration_id' => $regId,'topic' => $topic);
		$url = Constants::$domain.Constants::subscribe_url;
		return $this->postResult($url, $fields,$retries);
	}

	public function subscribeForRegids($regIdList, $topic,$retries=1){
		$jointRegIds = '';
		foreach($regIdList as $regId){
			if(isset($regId)){
				$jointRegIds .= $regId.Constants::$comma;
			}
		}
		$fields = array('registration_id' => $jointRegIds,'topic' => $topic);
		$url = Constants::$domain.Constants::subscribe_url;
		return $this->postResult($url, $fields,$retries);
	}	

	public function unsubscribe($regId, $topic,$retries=1){
		$fields = array('registration_id' => $regId,'topic' => $topic);
		$url = Constants::$domain.Constants::unsubscribe_url;
		return $this->postResult($url, $fields,$retries);
	}

	public function unsubscribeForRegids($regIdList, $topic,$retries=1){
		$jointRegIds = '';
		foreach($regIdList as $regId){
			if(isset($regId)){
				$jointRegIds .= $regId.Constants::$comma;
			}
		}
		$fields = array('registration_id' => $jointRegIds,'topic' => $topic);
		$url = Constants::$domain.Constants::unsubscribe_url;
		return $this->postResult($url, $fields,$retries);
	}

	public function subscribeTopicByAlias($aliasList, $topic,$retries=1){
		$jointAliases = '';
		foreach($aliasList as $alias){
			if(isset($alias)){
				$jointAliases .= $alias.Constants::$comma;
			}
		}
		$fields = array('aliases' => $jointAliases,'topic' => $topic);
		$url = Constants::$domain.Constants::subscribe_alias_url;
		return $this->postResult($url, $fields,$retries);
	}

	public function unsubscribeTopicByAlias($aliasList, $topic,$retries=1){
		$jointAliases = '';
		foreach($aliasList as $alias){
			if(isset($alias)){
				$jointAliases .= $alias.Constants::$comma;
			}
		}
		$fields = array('aliases' => $jointAliases,'topic' => $topic);
		$url = Constants::$domain.Constants::unsubscribe_alias_url;
		return $this->postResult($url, $fields,$retries);
	}

       public function subscribeByPackageName($regId, $topic, $packageName, $retries=1){
		$fields = array('registration_id' => $regId,'topic' => $topic,'restricted_package_name' => $packageName);
		$url = Constants::$domain.Constants::subscribe_url;
		return $this->postResult($url, $fields,$retries);
	}

	public function subscribeForRegidsByPackageName($regIdList, $topic, $packageName,$retries=1){
		$jointRegIds = '';
		foreach($regIdList as $regId){
			if(isset($regId)){
				$jointRegIds .= $regId.Constants::$comma;
			}
		}
		$fields = array('registration_id' => $jointRegIds,'topic' => $topic,'restricted_package_name' => $packageName);
		$url = Constants::$domain.Constants::subscribe_url;
		return $this->postResult($url, $fields,$retries);
	}

	public function unsubscribeByPackageName($regId, $topic, $packageName,$retries=1){
		$fields = array('registration_id' => $regId,'topic' => $topic,'restricted_package_name' => $packageName);
		$url = Constants::$domain.Constants::unsubscribe_url;
		return $this->postResult($url, $fields,$retries);
	}

	public function unsubscribeForRegidsByPackageName($regIdList, $topic, $packageName,$retries=1){
		$jointRegIds = '';
		foreach($regIdList as $regId){
			if(isset($regId)){
				$jointRegIds .= $regId.Constants::$comma;
			}
		}
		$fields = array('registration_id' => $jointRegIds,'topic' => $topic,'restricted_package_name' => $packageName);
		$url = Constants::$domain.Constants::unsubscribe_url;
		return $this->postResult($url, $fields,$retries);
	}

	public function subscribeTopicByPackageNameAlias($aliasList, $topic, $packageName,$retries=1){
		$jointAliases = '';
		foreach($aliasList as $alias){
			if(isset($alias)){
				$jointAliases .= $alias.Constants::$comma;
			}
		}
		$fields = array('aliases' => $jointAliases,'topic' => $topic,'restricted_package_name' => $packageName);
		$url = Constants::$domain.Constants::subscribe_alias_url;
		return $this->postResult($url, $fields,$retries);
	}

	public function unsubscribeTopicByPackageNameAlias($aliasList, $topic, $packageName,$retries=1){
		$jointAliases = '';
		foreach($aliasList as $alias){
			if(isset($alias)){
				$jointAliases .= $alias.Constants::$comma;
			}
		}
		$fields = array('aliases' => $jointAliases,'topic' => $topic,'restricted_package_name' => $packageName);
		$url = Constants::$domain.Constants::unsubscribe_alias_url;
		return $this->postResult($url, $fields,$retries);
	}
}

?>

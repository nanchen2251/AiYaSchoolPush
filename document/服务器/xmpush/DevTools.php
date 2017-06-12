<?php
/**
 * 设备查询工具集.
 * @author wangkuiwei
 * @name DevTools
 *
 */
namespace xmpush;

class DevTools extends HttpBase {
	
	public function __construct(){
		parent::__construct();
	}
	
        public function getAliasesOf($packageName, $regId, $retries=1){
		$url = Constants::$domain.Constants::get_all_aliases;
                $fields = array('registration_id' => $regId,'restricted_pa​ckage_name' => $packageName);
		$result = $this->getResult($url, $fields, $retries);
		return $result;
	}

        public function getTopicsOf($packageName, $regId, $retries=1){
		$url = Constants::$domain.Constants::get_all_topics;
                $fields = array('registration_id' => $regId,'restricted_pa​ckage_name' => $packageName);
		$result = $this->getResult($url, $fields, $retries);
		return $result;
	}
	
}

?>

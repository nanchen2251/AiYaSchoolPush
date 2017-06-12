<?php
require_once 'connect_config.php';
class DataBaseUtil{
	
	static $_instance;
	
	private function _construct(){
		
	}
	
	static public function getInstance(){
		if (!(self::$_instance instanceof self)){
			self::$_instance = new self();
		}
		return self::$_instance;
	}

	public function connect(){
		return mysqli_connect(DB_HOST,DB_USER,DB_PWD,DB_NAME,DB_PORT);
	}
}
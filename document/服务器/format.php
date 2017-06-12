<?php

class Response{
	
	/**
	 * 封装数据为Json数据类型
	 * @param unknown $code	状态码
	 * @param string $msg	状态消息
	 * @param array $data	数据
	 */
	public static function json_response($code,$msg = '',$data = array()){
		$result = self::grant_array($code,$msg,$data);
		echo json_encode($result,JSON_UNESCAPED_UNICODE);
		exit();
	}
	
	/**
	 * 根据接口格式生成原数据数组
	 * @param unknown $code	状态码
	 * @param string $msg	状态信息
	 * @param array $data	数据
	 * @return string|unknown[]|string[]
	 */
	private function grant_array($code,$msg = '',$data = array()){
		if (!is_numeric($code)){
			return '';
		}
		
		$result = array(
				'code' => $code,
				'msg' => $msg,
				'data' => $data
		);
		return $result;
	}
}
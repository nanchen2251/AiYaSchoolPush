<?php
/**
 * @author wangkuiwei
 * @name HttpBase
 *
 */
namespace xmpush;

class HttpBase {
	private $appSecret;
	
	public function __construct(){
		$this->appSecret = Constants::$secret;
	}
	
	//发送请求，获取result，带重试
	public function getResult($url,$fields,$retries){
	    $result = new Result($this->getReq($url, $fields));
	    if($result->getErrorCode() == ErrorCode::Success){
	        return $result;
	    }
	    //重试
	    for($i=0;$i<$retries;$i++){
	        $result = new Result($this->getReq($url, $fields));
	        if($result->getErrorCode() == ErrorCode::Success){
	            break;
	        }
	    }
	    return $result;
	}
	
	//get方式发送请求
	public function getReq($url,$fields,$timeout=3){
	    $headers = array('Authorization: key=' . $this->appSecret, 'Content-Type: application/x-www-form-urlencoded');
	    // Open connection
	    $ch = curl_init();
	
	    curl_setopt($ch, CURLOPT_URL, $url.'?'.http_build_query($fields));
	    curl_setopt($ch, CURLOPT_POST, false);
	    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
	    curl_setopt ( $ch, CURLOPT_CONNECTTIMEOUT, $timeout );
	    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	
	    // Execute post
	    $result = curl_exec($ch);
	
	    // Close connection
	    curl_close($ch);
	    return $result;
	}
	
	//发送请求，获取result，带重试
	public function postResult($url,$fields,$retries){
		$result = new Result($this->postReq($url, $fields));
		if($result->getErrorCode() == ErrorCode::Success){
		    return $result;
		}
		//重试
		for($i=0;$i<$retries;$i++){
		    $result = new Result($this->postReq($url, $fields));
		    if($result->getErrorCode() == ErrorCode::Success){
		        break;
		    }
		}
		return $result;
	}
	
	//post方式发送请求
	public function postReq($url,$fields,$timeout=10){
	    $headers = array('Authorization: key=' . $this->appSecret, 'Content-Type: application/x-www-form-urlencoded');
	    // Open connection
	    $ch = curl_init();
	
	    // Set the url, number of POST vars, POST data
	    curl_setopt($ch, CURLOPT_URL, $url);
	    curl_setopt($ch, CURLOPT_POST, true);
	    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
	    curl_setopt ( $ch, CURLOPT_CONNECTTIMEOUT, $timeout );
	    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	    curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($fields));
	    print_r('HTTP Params <br> '.urldecode(http_build_query($fields)));
	    echo'<br>';
	    // Execute post
	    $result = curl_exec($ch);
	    	
	    // Close connection
	    curl_close($ch);
	    return $result;
	}
	
}

?>

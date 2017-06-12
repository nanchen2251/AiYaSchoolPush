<?php
/**
 * 加水印类
 */

class AddWater{

	private $imgPath; // 图片路径
	 
	public function __construct($imgPath="./"){
		$this->imgPath = rtrim($imgPath,"/")."/";
	}

	// 写水印动作
	public function waterInfo($ground,$water,$pos=0,$prefix="lee_",$tm=50){
		$allPathGround  = $this->imgPath.$ground;
		$allPathWater   = $this->imgPath.$water;
		$groundInfo = $this->imgInfo($allPathGround);
		$waterInfo  = $this->imgInfo($allPathWater);

		//判断水印图片是否比原图大
		if(!$newPos=$this->imgPos($groundInfo,$waterInfo,$pos)){
			echo "您的水印图片比原图大哦";
			return false;
		}
		 
		//打开资源
		$groundRes=$this->imgRes($allPathGround,$groundInfo['mime']);
		$waterRes=$this->imgRes($allPathWater,$waterInfo['mime']);

		//整合资源
		$newGround=$this->imgCopy($groundRes,$waterRes,$newPos,$waterInfo,$tm);

		//保存资源
		$this->saveImg($newGround,$ground,$groundInfo['mime'],$prefix);

	}

	private function saveImg($img,$ground,$info,$prefix){
		$path=$this->imgPath.$prefix.$ground;
		switch($info){
			case "image/jpg":
			case "image/jpeg":
			case "image/pjpeg":
				imagejpeg($img,$path);
				break;
			case "image/gif":
				imagegif($img,$path);
				break;
			case "image/png":
				imagepng($img,$path);
				break;
			default:
				imagegd2($img,$path);
		}
	}

	private function imgCopy($ground,$water,$pos,$waterInfo,$tm){
		imagecopymerge($ground,$water,$pos[0],$pos[1],0,0,$waterInfo[0],$waterInfo[1],$tm);
		return $ground;
	}

	private function imgRes($img,$imgType){
		switch($imgType){
			case "image/jpg":
			case "image/jpeg":
			case "image/pjpeg":
				$res=imagecreatefromjpeg($img);
				break;
			case "image/gif":
				$res=imagecreatefromgif($img);
				break;
			case "image/png":
				$res=imagecreatefrompng($img);
				break;
			case "image/wbmp":
				$res=imagecreatefromwbmp($img);
				break;
			default:
				$res=imagecreatefromgd2($img);
		}
		return $res;
	}

	// 位置为
	// 1 左上 2中上 3右上
	// 4 左中 5中中 6右中
	// 7 左下 8中下 9右下
	// 0 随机位置
	private function imgPos($ground,$water,$pos){
		if($ground[0]<$water[0] || $ground[1]<$water[1])  //判断水印与原图比较 如果水印的高或者宽比原图小 将返回假
			return false;
			switch($pos){
				case 1:
					$x=0;
					$y=0;
					break;
				case 2:
					$x=ceil(($ground[0]-$water[0])/2);
					$y=0;
					break;
				case 3:
					$x=$ground[0]-$water[0];
					$y=0;
					break;
				case 4:
					$x=0;
					$y=ceil(($ground[1]-$water[1])/2);
					break;
				case 5:
					$x=ceil(($ground[0]-$water[0])/2);
					$y=ceil(($ground[1]-$water[1])/2);
					break;
				case 6:
					$x=$ground[0]-$water[0];
					$y=ceil(($ground[1]-$water[1])/2);
					break;
				case 7:
					$x=0;
					$y=$ground[1]-$water[1];
					break;
				case 8:
					$x=ceil($ground[0]-$water[0]/2);
					$y=$ground[1]-$water[1];
					break;
				case 9:
					$x=$ground[0]-$water[0];
					$y=$ground[1]-$water[1];
					break;
				case 0:
				default:
					$x=rand(0,$ground[0]-$water[0]);
					$y=rand(0,$ground[1]-$water[1]);
			}
			$xy[]=$x;
			$xy[]=$y;
			return $xy;
	}

	// 获取图片信息的函数
	private function imgInfo($img){
		return getimagesize($img);
	}
		
}
?>
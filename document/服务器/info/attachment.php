<?php
require_once '../format.php';
require_once 'add_water.php';

$target_path_head = "./pic"; // 接收文件目录 默认

file_get_contents ( "php://input" );

@$size = $_POST ['size'];
$hasVideo = false;
$videoName = '';

$flag = false;
for($i = 0; $i < $size; $i ++) {
	@$filename = $_FILES ['file'.$i] ['name']; // 显示客户端文件的原名称
	@$error = $_FILES ['file'.$i] ['error'];
	@$fileType = getFileType($filename);
	switch ($fileType){
		case 'mp4':
			$target_path_head = "./video"; // mp4文件放在video文件夹
			$hasVideo = true;
			break;
		default:
			$target_path_head = "./pic"; // 由于当前只有mp4和图片，所以其余都放在pic文件夹
			if ($hasVideo){
				$videoName = $target_path_head."/".$filename;
			}
			break;
	}
	$target_path = $target_path_head."/".$filename;
	// $target_path = $filename;
	if (is_uploaded_file ( @$_FILES ['file'.$i] ['tmp_name'] )) {
		$flag = move_uploaded_file ( @$_FILES ['file'.$i] ['tmp_name'], $target_path );
		@$error = @$error.":".@$filename."上传错误";
	} else {
		$flag = false;
	}
}

if ($hasVideo){
	// 可以传进一个添加水印后保存的路径，路径相对于类脚本
	// 如果为空则默认是脚本当前路径
	$water=new AddWater();
	
	// 参数：
	// 1.  源图
	// 2. 水印图 即 LOGO
	// 3.  位置
	// 位置为
	// 1 左上 2中上 3右上
	// 4 左中 5中中 6右中
	// 7 左下 8中下 9右下
	// 0 随机位置
	// 4. 保存添加水印图片的文件名前缀
	//  5. 透明度
	$water->waterInfo($videoName,"play_big.png",5,"",50);
}

if ($flag) {
	Response::json_response ( 0, "附件上传成功！", null );
} else {
	Response::json_response ( - 1, "附件上传失败！:" . @$error, null );
}

/**
 * 获取文件类型
 * @param string $filename 文件名称
 * @return string 文件类型
 */
function getFileType($filename) {
	return substr($filename, strrpos($filename, '.') + 1);
}

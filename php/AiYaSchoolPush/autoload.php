<?php
define ( 'MiPush_Root', dirname ( __FILE__ ) . '/' );
function mipushAutoload($classname) {
	$parts = explode ( '\\', $classname );
	$path = MiPush_Root . implode ( '/', $parts ) . '.php';
	if (file_exists ( $path )) {
		include ($path);
	}
}

spl_autoload_register ( 'mipushAutoload' );

<?php
/**
 * 常量定义.
 * @author wangkuiwei
 * @name Constants
 * @desc 常量定义
 *
 */
namespace xmpush;

class Constants {
	public static $domain = 'https://api.xmpush.xiaomi.com';
	public static $comma = ',';
	public static $multi_topic_split = ';$;';
	public static $packageName = '';
	public static $bundle_id = '';
	public static $secret = '';
	
	const reg_url = '/v3/message/regid';
	const alias_url = '/v3/message/alias';
	const user_account_url = '/v2/message/user_account';
	const topic_url = '/v3/message/topic';
	const multi_topic_url = '/v3/message/multi_topic';
        const all_url = '/v3/message/all';
	const multi_messages_regids_url = '/v2/multi_messages/regids';
	const multi_messages_aliases_url = '/v2/multi_messages/aliases';
        const multi_messages_user_accounts_url = '/v2/multi_messages/user_accounts';
	const stats_url = '/v1/stats/message/counters';
	const message_trace_url = '/v1/trace/message/status';
	const messages_trace_url = '/v1/trace/messages/status';
	const validation_regids_url = '/v1/validation/regids';
	const subscribe_url = '/v2/topic/subscribe';
	const unsubscribe_url = '/v2/topic/unsubscribe';
	const subscribe_alias_url = '/v2/topic/subscribe/alias';
	const unsubscribe_alias_url = '/v2/topic/unsubscribe/alias';
	const fetch_invalid_regids_url = 'https://feedback.xmpush.xiaomi.com/v1/feedback/fetch_invalid_regids';
	const delete_schedule_job = '/v2/schedule_job/delete';
	const check_schedule_job_exist = '/v2/schedule_job/exist';
        const get_all_aliases = '/v1/alias/all';
        const get_all_topics = '/v1/topic/all';

	const UNION = 'UNION';
	const INTERSECTION = 'INTERSECTION';
	const EXCEPT = 'EXCEPT';

	public static function setPackage($package){
		self::$packageName = $package;
	}
	
	public static function setSecret($secret){
		self::$secret = $secret;
	}

	public static function setBundleId($bundleId){
		self::$bundle_id = $bundleId;
	}
	
	public static function useOfficial(){
		self::$domain = 'https://api.xmpush.xiaomi.com';
	}
	
	public static function useSandbox(){
		self::$domain = 'https://sandbox.xmpush.xiaomi.com';
	}
}

?>

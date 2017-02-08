package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.AppService;
import com.example.nanchen.aiyaschoolpush.model.User;
import com.example.nanchen.aiyaschoolpush.net.okgo.JsonCallback;
import com.example.nanchen.aiyaschoolpush.net.okgo.LslResponse;
import com.example.nanchen.aiyaschoolpush.utils.TextUtil;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;
import com.example.nanchen.aiyaschoolpush.ui.view.IcomoonTextView;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 重置密码页面
 */
public class ResetPwdActivity extends ActivityBase implements OnClickListener{

    private static final String TAG = "ResetPwdActivity";

    private TitleView mTitleBar;
    private IcomoonTextView mTitleCommit;
    private EditText mEditPhone;
    private EditText mEditVercode;
    private Button mBtnGetCode;
    private EditText mEditPwd1;
    private EditText mEditPwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);

        // 短信验证需要先注册相关信息   注意必须在合适的位置取消注册 否则会造成内存泄漏
        SMSSDK.registerEventHandler(eh);

        bindView();


    }

    private void bindView() {
        mTitleBar = (TitleView) findViewById(R.id.reset_pwd_titleBar);
        mTitleCommit = (IcomoonTextView) findViewById(R.id.reset_pwd_title_commit);
        mEditPhone = (EditText) findViewById(R.id.reset_pwd_phone);
        mEditVercode = (EditText) findViewById(R.id.reset_pwd_code);
        mBtnGetCode = (Button) findViewById(R.id.reset_pwd_get_code);
        mEditPwd1 = (EditText) findViewById(R.id.reset_pwd_pwd1);
        mEditPwd2 = (EditText) findViewById(R.id.reset_pwd_pwd2);

        String phone = getIntent().getStringExtra("phone");
        if (!TextUtils.isEmpty(phone)){
            mEditPhone.setText(phone);
        }
        mTitleBar.setTitle("重置密码");
        mTitleBar.setLeftButtonAsFinish(this);

        mTitleCommit.setOnClickListener(this);
        mBtnGetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_pwd_title_commit:
//                Crouton.makeText(this,"你点击了提交按钮", Style.ALERT,R.id.reset_pwd_pwd2_layout).show();
                getCode = true;
                submit();
                break;
            case R.id.reset_pwd_get_code:
//                Crouton.makeText(this,"你点击了获取验证码", Style.ALERT).show();
                getVerCode();
                break;
        }
    }

    private String phone;
    private String pwd1;


    private void submit() {
        phone = mEditPhone.getText().toString().trim();
        final String code = mEditVercode.getText().toString().trim();
        pwd1 = mEditPwd1.getText().toString().trim();
        String pwd2 = mEditPwd2.getText().toString().trim();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code) || TextUtils.isEmpty(pwd1)
                || TextUtils.isEmpty(pwd2)){
            showCrouton("请填写必要信息！");
            return;
        }
        if (!TextUtil.isMobile(phone)){
            showCrouton("手机号格式不正确！");
            return;
        }
        if (!pwd1.equals(pwd2)){
            showCrouton("两次输入的密码不一致！");
            return;
        }
        if (pwd1.length() < 6){
            showCrouton("密码长度不能小于6!");
            return;
        }

        showLoading(this);

        AppService.getInstance().resetPwdAsync(phone, pwd1, new JsonCallback<LslResponse<User>>() {
            @Override
            public void onSuccess(LslResponse<User> userLslResponse, Call call, Response response) {
                if (userLslResponse.code == LslResponse.RESPONSE_ERROR){
                    UIUtil.showToast(userLslResponse.msg);
                    stopLoading();
                }else{
                    SMSSDK.submitVerificationCode(COUNTRY_CODE,phone,code);//验证验证码  引发回调
                }
            }
        });



    }

    private void showCrouton(String desc){
        Crouton.makeText(this,desc,Style.ALERT,R.id.reset_location).show();
    }

    /**
     * 获取验证码
     */
    private void getVerCode() {
        String phone = mEditPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            showCrouton("手机号不能为空！");
            return;
        }
        if (!TextUtil.isMobile(phone)){
            showCrouton("手机号格式不正确！");
            return;
        }

        SMSSDK.getVerificationCode(COUNTRY_CODE,phone);// 请求获取验证码
        timeCount = null;
        timeCount = new TimeCount(120 * 1000,1000,this);
        timeCount.start();
    }


    private static final int GET_VERCODE_SUC = 0x1;
    private static final int GET_VERCODE_FAILED = 0x2;
    private static final int VERIFY_SUC = 0x3;
    private static final int VERIFY_FAILED = 0x4;
    private static final String COUNTRY_CODE = "86";
    private boolean getCode = false;
    private TimeCount timeCount;

    /**
     * 短信验证的回调监听处理
     */
    private EventHandler eh = new EventHandler(){

        /**
         * 当操作结束会触发的回调方法
         * @param event     要操作的类型
         * @param result    操作结果，为SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.RESULT_ERROR表示操作失败
         * @param data      事件操作的结果，其具体取值根据参数result而定
         */
        @Override
        public void afterEvent(int event, int result, Object data) {
            super.afterEvent(event, result, data);
            if (result == SMSSDK.RESULT_COMPLETE){ // 回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    Log.e(TAG,"提交验证码成功");
                    handler.sendEmptyMessageDelayed(VERIFY_SUC,3000);
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//获取验证码成功
                    Log.e(TAG,"获取验证码成功");
                    getCode = true;
                    handler.sendEmptyMessage(GET_VERCODE_SUC);
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
                    Log.e(TAG,"返回支持发送验证码的国家列表成功");
                }
            }else { // 回调失败
                try {
                    Log.e(TAG,"回调操作异常");
                    if (getCode){
                        handler.sendEmptyMessageDelayed(VERIFY_FAILED,3000);
                    }else {
                        handler.sendEmptyMessage(GET_VERCODE_FAILED);
                        getCode = false;
                    }
                } catch (Exception e) {
                    //do something
                }
            }
        }
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case VERIFY_SUC:
                    // 验证成功
//                    Crouton.makeText(ResetPwdActivity.this,"验证成功！",Style.ALERT).show();
                    startResetPwd(); // 开始重置密码
                    break;
                case VERIFY_FAILED:
                    UIUtil.showToast(ResetPwdActivity.this,"你输入的验证码不正确！");
                    stopLoading();
                    break;
                case GET_VERCODE_SUC:
                    UIUtil.showToast(ResetPwdActivity.this,"获取验证码成功！");
                    break;
                case GET_VERCODE_FAILED:
                    UIUtil.showToast(ResetPwdActivity.this,"获取验证码失败！");
                    stopLoading();
                    break;
            }
        }
    };

    /**
     * 开始注册
     */
    private void startResetPwd() {
        stopLoading();
//                    showCrouton("密码重置成功！");
        UIUtil.showToast("密码重置成功！");
        ResetPwdActivity.this.finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);

        handler.removeCallbacksAndMessages(null);//销毁的时候移除所有的handler线程操作 参数为null时候会移除all
        timeCount = null;
        Log.e(TAG,"Activity成功销毁");
    }

    /**
     * 专用于倒计时处理的类
     */
    private static class TimeCount extends CountDownTimer {
        private ResetPwdActivity activity;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval,ResetPwdActivity activity) {
            super(millisInFuture, countDownInterval);
            this.activity = activity;
        }

        /**
         * 计时过程显示
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            // 设置为灰色
            activity.mBtnGetCode.setBackgroundResource(R.drawable.button_border_radius_no);
            activity.mBtnGetCode.setClickable(false);
            activity.mBtnGetCode.setText(millisUntilFinished/1000 + "秒后可重试");
        }

        /**
         * 计时结束调用
         */
        @Override
        public void onFinish() {
            activity.mBtnGetCode.setClickable(true);
            activity.mBtnGetCode.setBackgroundResource(R.drawable.reset_pwd_btn_bg);
            activity.mBtnGetCode.setText("获取验证码");
        }
    }

}

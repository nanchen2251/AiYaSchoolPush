package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.AppService;
import com.example.nanchen.aiyaschoolpush.model.User;
import com.example.nanchen.aiyaschoolpush.net.okgo.JsonCallback;
import com.example.nanchen.aiyaschoolpush.net.okgo.LslResponse;
import com.example.nanchen.aiyaschoolpush.utils.TextUtil;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends ActivityBase implements OnClickListener {

    private static final String TAG = "RegisterActivity";
    private static final String COUNTRY_CODE = "86";
    private static final int TAG_USERNAME = 1;
    private static final int TAG_PWD1 = 2;
    private static final int TAG_PWD2 = 3;
    private static final int TAG_VERCODE = 4;


    private TitleView mTitleBar;
    private EditText mEditUsername;
    private EditText mEditPwd1;
    private EditText mEditPwd2;
    private EditText mEditVercode;
    private Button mBtnVerify;
    private Button mBtnGetVercode;
    private Timer timer;
    private TimeCount timeCount;//用于倒计时任务
    private String phone;
    private String verCode;
    private String pwd1;
    private String pwd2;

    private static final int GET_VERCODE_SUC = 0x1;
    private static final int GET_VERCODE_FAILED = 0x2;
    private static final int VERIFY_SUC = 0x3;
    private static final int VERIFY_FAILED = 0x4;

    private TextInputLayout mInputUsername;
    private TextInputLayout mInputPwd1;
    private TextInputLayout mInputPwd2;
    private TextInputLayout mInputCode;


    private boolean getCode = false;

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
                    startRegister();
                    break;
                case VERIFY_FAILED:
                    UIUtil.showToast(RegisterActivity.this,"你输入的验证码不正确！");
                    stopLoading();
                    break;
                case GET_VERCODE_SUC:
                    UIUtil.showToast(RegisterActivity.this,"获取验证码成功！");
                    break;
                case GET_VERCODE_FAILED:
                    UIUtil.showToast(RegisterActivity.this,"获取验证码失败！");
                    stopLoading();
                    break;
            }
        }
    };
    private MaterialEditText mEditPhone;


    /**
     * 检测完毕，开始注册
     */
    private void startRegister() {
        stopLoading();
        Intent intent = new Intent(RegisterActivity.this,RegisterActivity2.class);
        intent.putExtra("phone",mEditUsername.getText().toString().trim());
        startActivity(intent);
//        UIUtil.showToast(RegisterActivity.this,"注册成功！");
        RegisterActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 短信验证需要先注册相关信息   注意必须在合适的位置取消注册 否则会造成内存泄漏
        SMSSDK.registerEventHandler(eh);

        bindView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);

        handler.removeCallbacksAndMessages(null);//销毁的时候移除所有的handler线程操作 参数为null时候会移除all
        timeCount = null;
        Log.e(TAG,"Activity成功销毁");
    }

    private void bindView() {
        mTitleBar = (TitleView) findViewById(R.id.register_titleBar);
        mEditUsername = (EditText) findViewById(R.id.register_edt_username);
        mEditPwd1 = (EditText) findViewById(R.id.register_edt_pwd1);
        mEditPwd2 = (EditText) findViewById(R.id.register_edt_pwd2);
        mEditVercode = (EditText) findViewById(R.id.register_edt_vercode);
        mBtnVerify = (Button) findViewById(R.id.register_btn_verify);
        mBtnGetVercode = (Button) findViewById(R.id.register_btn_getVercode);

        mInputUsername = (TextInputLayout) findViewById(R.id.register_layout_username);
        mInputPwd1 = (TextInputLayout) findViewById(R.id.register_layout_pwd1);
        mInputPwd2 = (TextInputLayout) findViewById(R.id.register_layout_pwd2);
        mInputCode = (TextInputLayout) findViewById(R.id.register_layout_code);




        mTitleBar.setLeftButtonAsFinish(this);
        mTitleBar.setTitle("手机验证");

        mBtnVerify.setOnClickListener(this);
        mBtnGetVercode.setOnClickListener(this);



        mEditUsername.addTextChangedListener(new MyTextWatcher(this,TAG_USERNAME));
        mEditPwd1.addTextChangedListener(new MyTextWatcher(this,TAG_PWD1));
        mEditPwd2.addTextChangedListener(new MyTextWatcher(this,TAG_PWD2));
        mEditVercode.addTextChangedListener(new MyTextWatcher(this,TAG_VERCODE));


        mEditPhone = (MaterialEditText) findViewById(R.id.register_edt_username1);
        mEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtil.isMobile(mEditPhone.getText().toString().trim())){
                    mEditPhone.setHelperTextAlwaysShown(true);
                    mEditPhone.setHelperText("手机号格式不正确");
                }else {
                    mEditPhone.setHelperTextAlwaysShown(false);
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn_verify:
                Log.e(TAG,"你点击了注册按钮!");
                getCode = true;
                register();
                hideKeyboard();//关闭键盘
//                finish();
                break;
            case R.id.register_btn_getVercode:
                Log.e(TAG,"你点击了获取验证码按钮!");
                getVerCode();// 获取验证码

                break;
        }
    }

    /**
     * 点击验证后执行的方法
     */
    private void register() {
        phone = mEditUsername.getText().toString().trim();
        pwd1 = mEditPwd1.getText().toString().trim();
        pwd2 = mEditPwd2.getText().toString().trim();
        verCode = mEditVercode.getText().toString().trim();

        Log.e(TAG,"phone:"+phone+"pwd1:"+pwd1+"pwd2:"+pwd2+"verCode:"+verCode);

//        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd1)
//                || TextUtils.isEmpty(pwd2) || TextUtils.isEmpty(verCode)){
//            UIUtil.showToast(this,"请输入必要信息！");
//            return;
//        }

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(verCode)){
            UIUtil.showToast(this,"请输入必要信息！");
            return;
        }


        if (!TextUtil.isMobile(phone)){
            UIUtil.showToast(this,"手机号格式不正确!");
            return;
        }
//        if (pwd1.length() < 6){
//            UIUtil.showToast(this,"密码长度不能小于6！");
//            return;
//        }
//        if (!pwd1.equals(pwd2)){
//            UIUtil.showToast(this,"两次的密码不一致，请检查！");
//            return;
//        }
        
        showLoading(this);

        AppService.getInstance().isUsableMobileAsync(phone, new JsonCallback<LslResponse<User>>() {
            @Override
            public void onSuccess(LslResponse<User> userLslResponse, Call call, Response response) {
                if (userLslResponse.code == LslResponse.RESPONSE_OK){
                    SMSSDK.submitVerificationCode(COUNTRY_CODE,phone,verCode);
                    Log.e(TAG,userLslResponse.msg);
                }else {
                    UIUtil.showToast("错误："+userLslResponse.msg);
                    Log.e(TAG,"错误："+userLslResponse.msg);
                    stopLoading();
                }
            }
        });


    }

    /**
     * 关闭键盘
     */
    private void hideKeyboard(){
        View view = getCurrentFocus();
        if (view != null){
            ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 点击获取验证码后执行的方法
     */
    private void getVerCode() {
        phone = mEditUsername.getText().toString().trim();
        if (!TextUtil.isMobile(phone)){
            UIUtil.showToast(this,"手机号格式不正确，请检查！");
//            mInputUsername.setError("账号必须为11位手机号！");
            return;
        }
//        mInputUsername.setErrorEnabled(false);// 隐藏
        SMSSDK.getVerificationCode(COUNTRY_CODE,phone);// 请求获取验证码
//                // 设置为灰色
//                mBtnGetVercode.setBackgroundResource(R.drawable.button_border_radius_no);
//                timer = null;
//                timer = new Timer();
//                timer.schedule(timerTask,0,1000);// 计时器任务开始
        timeCount = null;
        timeCount = new TimeCount(120 * 1000,1000,this);
        timeCount.start();
    }

    private OnSendMessageHandler listener;

    /**
     * 倒计时120秒的倒计时任务
     */
    private TimerTask timerTask = new TimerTask() {
        int cnt = 120;
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mBtnGetVercode.setText(--cnt+"秒后可再次获取");
                }
            });
        }
    };


    /**
     * 专用于倒计时处理的类
     */
    private static class TimeCount extends CountDownTimer{
        private RegisterActivity activity;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval,RegisterActivity activity) {
            super(millisInFuture, countDownInterval);
            WeakReference<RegisterActivity> weakReference = new WeakReference<RegisterActivity>(activity);
            this.activity = weakReference.get();
        }

        /**
         * 计时过程显示
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            // 设置为灰色
            activity.mBtnGetVercode.setBackgroundResource(R.drawable.button_border_radius_no);
            activity.mBtnGetVercode.setClickable(false);
            activity.mBtnGetVercode.setText(millisUntilFinished/1000 + "秒后可再次获取");
        }

        /**
         * 计时结束调用
         */
        @Override
        public void onFinish() {
            activity.mBtnGetVercode.setClickable(true);
            activity.mBtnGetVercode.setBackgroundResource(R.drawable.button_border_radius);
            activity.mBtnGetVercode.setText("点击获取验证码");
        }
    }

    private static class MyTextWatcher implements TextWatcher {

        private int tag;
        private RegisterActivity activity;
        private WeakReference<RegisterActivity> mWeakReference;

        MyTextWatcher(RegisterActivity activity, int tag){
            mWeakReference = new WeakReference<RegisterActivity>(activity);
            RegisterActivity activity1 = mWeakReference.get();
            if (activity1 != null){
                this.activity = activity1;
            }else{
                this.activity = activity;
            }
            this.tag = tag;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void afterTextChanged(Editable s) {
            switch (tag){
                case TAG_USERNAME:
                    String phone = activity.mEditUsername.getText().toString().trim();
                    if (!TextUtil.isMobile(phone)){
//                        activity.mInputUsername.setErrorEnabled(true);
//                        activity.mInputUsername.setError("手机号格式不正确！");
                        activity.mInputUsername.getEditText().setError("手机号格式不正确！");
                    }else {
                        activity.mInputUsername.getEditText().setError(null);
//                        activity.mInputUsername.setErrorEnabled(false);

                    }
                    break;
                case TAG_PWD1:
                    if (activity.mEditPwd1.getText().toString().length() < 6){
                        activity.mInputPwd1.getEditText().setError("密码不能小于6位");
                    }else {
                        activity.mInputPwd1.getEditText().setError(null);
                    }
                    break;
                case TAG_PWD2:
                    String pwd1 = activity.mEditPwd1.getText().toString().trim();
                    String pwd2 = activity.mEditPwd2.getText().toString().trim();
                    if (pwd1.equals(pwd2)){
                        activity.mInputPwd2.getEditText().setError(null);
                    }else {
                        activity.mInputPwd2.getEditText().setError("两次的密码输入不一致!");
                    }
                    break;
                case TAG_VERCODE:
                    if (TextUtils.isEmpty(activity.mEditVercode.getText().toString())){
                        activity.mInputCode.getEditText().setError("验证码不能为空！");
                    }else {
                        activity.mInputCode.getEditText().setError(null);
                    }
                    break;
            }
        }
    }
}

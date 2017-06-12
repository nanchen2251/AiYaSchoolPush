package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.nanchen.aiyaschoolpush.App;
import com.example.nanchen.aiyaschoolpush.AppService;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.config.Consts;
import com.example.nanchen.aiyaschoolpush.db.DemoDBManager;
import com.example.nanchen.aiyaschoolpush.helper.DemoHelper;
import com.example.nanchen.aiyaschoolpush.helper.QiYuCloudServerHelper;
import com.example.nanchen.aiyaschoolpush.model.User;
import com.example.nanchen.aiyaschoolpush.net.okgo.JsonCallback;
import com.example.nanchen.aiyaschoolpush.net.okgo.LslResponse;
import com.example.nanchen.aiyaschoolpush.ui.view.IcomoonTextView;
import com.example.nanchen.aiyaschoolpush.utils.IntentUtil;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends ActivityBase implements OnClickListener {

    private EditText mEditUserName;
    private EditText mEditPwd;
    private Button mBtnLogin;
    private LinearLayout mLinearRegister;
    private IcomoonTextView mTextFindPwd;
    private boolean autoLogin = false;
    private static final String TAG = "LoginActivity";

    private SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        List<String> stringList = MiPushClient.getAllAlias(App.getAppContext());
        Log.e("pushtest", "run: size:"+stringList.size() );
        for (int i = 0; i < stringList.size(); i++) {
            Log.e("pushtest", "stringList,"+i +"=> "+stringList.get(i) );
        }

        sp = getSharedPreferences("user.temp",MODE_PRIVATE);
        if (DemoHelper.getInstance().isLoggedIn()) {
            autoLogin = true;

            String username =  sp.getString("username","");
            String password = sp.getString("password","");

            AppService.getInstance().loginAsync(username, password, new JsonCallback<LslResponse<User>>() {
                @Override
                public void onSuccess(LslResponse<User> userLslResponse, Call call, Response response) {
                    if (userLslResponse.code == LslResponse.RESPONSE_OK){
                        setUserInfo(userLslResponse.data);
                        IntentUtil.newIntent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.finish();
                        return;
                    }
                    UIUtil.showToast("当前用户登录信息已过期，请重新登录！");
                }
            });

        }
        setContentView(R.layout.activity_login);

        bindView();
        setListener();
    }



    private void setListener() {
        mBtnLogin.setOnClickListener(this);
        mLinearRegister.setOnClickListener(this);
        mTextFindPwd.setOnClickListener(this);
    }

    private void bindView() {
        mEditUserName = (EditText) findViewById(R.id.login_edt_username);
        mEditPwd = (EditText) findViewById(R.id.login_edt_pwd);
        mBtnLogin = (Button) findViewById(R.id.login_btn_login);
        mLinearRegister = (LinearLayout) findViewById(R.id.linear_layout_btn_register);

        mTextFindPwd = (IcomoonTextView) findViewById(R.id.login_find_pwd);

        //  if user changed, clear the password
        mEditUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mEditPwd.setText(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (DemoHelper.getInstance().getCurrentUserName() != null) {
            mEditUserName.setText(DemoHelper.getInstance().getCurrentUserName());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:
                login();
                break;
            case R.id.linear_layout_btn_register:
                IntentUtil.newIntent(this, RegisterActivity.class);
//                String phone1 = mEditUserName.getText().toString().trim();
//                Intent intent1 = new Intent(this,RegisterActivity2.class);
//                intent1.putExtra("phone",phone1);
//                startActivity(intent1);
                break;
            case R.id.login_find_pwd:
//                UIUtil.showToast(this,"你点击了找回密码！");

                String phone = mEditUserName.getText().toString().trim();
                Intent intent = new Intent(this, ResetPwdActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
                break;
        }
    }

    private void login() {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            UIUtil.showToast("网络连接不可用，请检查！");
            return;
        }
        final String currentUsername = mEditUserName.getText().toString().trim();
        final String currentPassword = mEditPwd.getText().toString().trim();

        Log.e(TAG,"得到的用户名和密码："+currentUsername+" ---  "+currentPassword);

        if (TextUtils.isEmpty(currentUsername)) {
            UIUtil.showToast("用户名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            UIUtil.showToast("密码不能为空！");
            return;
        }
        showLoading(this);
        AppService.getInstance().loginAsync(currentUsername, currentPassword, new JsonCallback<LslResponse<User>>() {
            @Override
            public void onSuccess(LslResponse<User> userLslResponse, Call call, Response response) {
                if (userLslResponse.code == LslResponse.RESPONSE_ERROR){
                    UIUtil.showToast(userLslResponse.msg);
                    stopLoading();
                }else{
                    setUserInfo(userLslResponse.data);
                    Log.e(TAG,"登陆爱吖服务器成功！");
                    loginXin(currentUsername,currentPassword);
                }
            }
        });
    }

    /**
     * 把信息存储起来
     * @param data
     */
    @SuppressWarnings("ConstantConditions")
    private void setUserInfo(User data) {
        Log.e(TAG,"username："+data.username);
        Log.e(TAG,"password："+data.password);
        Log.e(TAG,"birthday："+data.birthday);
        Log.e(TAG,"nickname："+data.nickname);
        Log.e(TAG,"type："+data.type);
        Log.e(TAG,"classid："+data.classid);
        Log.e(TAG,"address："+data.address);
        Log.e(TAG,"childName："+data.childName);
        if (!TextUtils.isEmpty(data.icon)){
            data.icon = Consts.API_SERVICE_HOST+data.icon;
        }
        Log.e(TAG,"avatar："+ data.icon);
        if (!TextUtils.isEmpty(data.childAvatar)){
            data.childAvatar = Consts.API_SERVICE_HOST+data.childAvatar;
        }
        Log.e(TAG,"childAvatar："+data.childAvatar);

        AppService.getInstance().setCurrentUser(data);

        MiPushClient.subscribe(App.getAppContext(),data.classid+"",null);


        MiPushClient.setAlias(App.getAppContext(),data.classid+"",null);


        Editor editor = sp.edit();
        editor.putString("username",data.username);
        editor.putString("password",data.password);
        editor.putString("classid",data.classid+"");
        editor.apply();
    }


    /**
     * 登录环信即时通讯需要
     * @param currentUsername   当前输入的用户名
     * @param currentPassword   密码
     */
    private void loginXin(String currentUsername,String currentPassword){
        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(currentUsername);

        boolean flag = EMClient.getInstance().isLoggedInBefore();
        Log.e(TAG, flag + "  --------");

        if (flag) { //  如果已经有登录用户，先调用登出
            EMClient.getInstance().logout(false);
        }

        // go login 环信
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
                        App.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }

                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UIUtil.showToast("登录成功！");
                        stopLoading();
                    }
                });

                IntentUtil.newIntent(LoginActivity.this,MainActivity.class);
                finish();
            }

            @Override
            public void onError(final int code, final String message) {

                Log.d(TAG, "login: onError: " + code);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stopLoading();
                        UIUtil.showToast("登陆IM失败:"+message+",code:"+code);
                    }
                });
            }

            @Override
            public void onProgress(final int code, final String message) {
                Log.d(TAG, "login: onProgress");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 未登录状态下，清空用户信息
        QiYuCloudServerHelper.setUserInfo(false);
        if (autoLogin){
            return;
        }
    }
}

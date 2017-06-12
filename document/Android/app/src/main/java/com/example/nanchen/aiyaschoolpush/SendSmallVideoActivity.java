package com.example.nanchen.aiyaschoolpush;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.config.AddConfig;
import com.example.nanchen.aiyaschoolpush.helper.event.CommunityEvent;
import com.example.nanchen.aiyaschoolpush.helper.event.HomeworkEvent;
import com.example.nanchen.aiyaschoolpush.helper.event.NoticeEvent;
import com.example.nanchen.aiyaschoolpush.model.User;
import com.example.nanchen.aiyaschoolpush.model.info.InfoModel;
import com.example.nanchen.aiyaschoolpush.model.info.InfoType;
import com.example.nanchen.aiyaschoolpush.net.okgo.JsonCallback;
import com.example.nanchen.aiyaschoolpush.net.okgo.LslResponse;
import com.example.nanchen.aiyaschoolpush.ui.activity.ActivityBase;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;
import com.example.nanchen.aiyaschoolpush.ui.view.WavyLineView;
import com.example.nanchen.aiyaschoolpush.utils.ScreenUtil;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mabeijianxi.camera.MediaRecorderActivity;
import okhttp3.Call;
import okhttp3.Response;


public class SendSmallVideoActivity extends ActivityBase implements View.OnClickListener {

    private static final String TAG = "SendSmallVideoActivity";
    private String videoUri;
    private TextView tv_send;
    private TextView tv_cancel;
    private String videoScreenshot;
    private ImageView iv_video_screenshot;
    private EditText et_send_content;

    private AlertDialog dialog;
    private TitleView mTitleBar;
    private int infoType;
    private WavyLineView mWavyLine;
    private EditText mEditText;
    private List<File> mFiles;
    private List<String> mSmallUrls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.smallvideo_text_edit_activity);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
//        tv_cancel.setOnClickListener(this);
//        tv_send.setOnClickListener(this);
//        et_send_content.setOnClickListener(this);
        iv_video_screenshot.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFiles != null){
            mFiles.clear();
            mFiles = null;
        }
        if (mSmallUrls != null){
            mSmallUrls.clear();
            mSmallUrls = null;
        }
    }

    private void initData() {
        Intent intent = getIntent();
        videoUri = intent.getStringExtra(MediaRecorderActivity.VIDEO_URI);
        videoScreenshot = intent.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);
        Bitmap bitmap = BitmapFactory.decodeFile(videoScreenshot);
        iv_video_screenshot.setImageBitmap(bitmap);
        mFiles = new ArrayList<>();
        mSmallUrls = new ArrayList<>();
    }

    private void initView() {
        setContentView(R.layout.activity_release);


//        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
//        tv_send = (TextView) findViewById(R.id.tv_send);
//        et_send_content = (EditText) findViewById(R.id.et_send_content);
        iv_video_screenshot = (ImageView) findViewById(R.id.iv_video_screenshot);
        iv_video_screenshot.setVisibility(View.VISIBLE);

//        mTitleBar = (TitleView) findViewById(R.id.send_video_titleBar);
        mTitleBar = (TitleView) findViewById(R.id.release_title);
        mTitleBar.setLeftButtonAsFinish(this);

        SharedPreferences sp = getSharedPreferences("send.tmp",MODE_PRIVATE);

        String mFrom = sp.getString("infoType","");
        String content = sp.getString("content","");
        switch (mFrom) {
            case AddConfig.NOTICE:
                mTitleBar.setTitle("发布公告");
                infoType = InfoType.NOTICE;
                break;
            case AddConfig.HOMEWORK:
                mTitleBar.setTitle("发布作业");
                infoType = InfoType.HOMEWORK;
                break;
            default:
                mTitleBar.setTitle("发布动态");
                infoType = InfoType.COMMUNITY;
                break;
        }
        mTitleBar.changeRightButtonTextColor(getResources().getColor(R.color.white3));
        mTitleBar.setRightButtonText(getResources().getString(R.string.send_back_right));
        mTitleBar.setRightButtonTextSize(25);
        mTitleBar.setFixRightButtonPadingTop();
        mTitleBar.setRightButtonOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });



        // 波浪线设置
        mWavyLine = (WavyLineView) findViewById(R.id.release_wavyLine);
        int initStrokeWidth = 1;
        int initAmplitude = 5;
        float initPeriod = (float) (2 * Math.PI / 60);
        mWavyLine.setPeriod(initPeriod);
        mWavyLine.setAmplitude(initAmplitude);
        mWavyLine.setStrokeWidth(ScreenUtil.dp2px(initStrokeWidth));


        mEditText = (EditText) findViewById(R.id.release_edit);

        if (TextUtils.isEmpty(content) || content.equals("")){// 如果用户之前没有输入信息，则不设置输入框
            return;
        }
        mEditText.setText(content);
    }

    private void uploadFile() {

        final String content = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            UIUtil.showToast("发布内容不能为空！");
            return;
        }
        File file = new File(videoUri);
        File file1 = new File(videoScreenshot);
        mFiles.add(file);
        mFiles.add(file1);
        showLoading(this);
        AppService.getInstance().upLoadFileAsync(mFiles,new JsonCallback<LslResponse<User>>() {
            @Override
            public void onSuccess(LslResponse<User> userLslResponse, Call call, Response response) {
                if (userLslResponse.code == LslResponse.RESPONSE_OK) {
                    UIUtil.showToast("视频上传成功");
                    Log.e(TAG, "视频上传成功");
                    for (int i = 0; i < mFiles.size(); i++) {
                        Log.e(TAG, "onSuccess: "+i+":"+mFiles.get(i).getName() );
                    }
                } else {
                    UIUtil.showToast("视频上传失败");
                    Log.e(TAG, "视频上传失败");
                    if (!SendSmallVideoActivity.this.isFinishing()) {
                        stopLoading();
                        return;
                    }
                }
                sendInfo();
            }
        });
    }

    private void sendInfo() {
        final String content = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            UIUtil.showToast("发布内容不能为空！");
            stopLoading();
            return;
        }
        if(AppService.getInstance().getCurrentUser() == null){
            UIUtil.showToast("未知错误，请重新登录后操作");
            stopLoading();
            return;
        }
//        mSmallUrls.add(videoScreenshot);
//        mSmallUrls.add(videoUri);
        for (int i = 0; i < mFiles.size(); i++) {
            mSmallUrls.add(mFiles.get(i).getName());
        }
        final int classId = AppService.getInstance().getCurrentUser().classid;
        String username = AppService.getInstance().getCurrentUser().username;
        AppService.getInstance().addMainInfoAsync(classId, username, infoType, content, mSmallUrls, true,new JsonCallback<LslResponse<InfoModel>>() {

            @Override
            public void onSuccess(LslResponse<InfoModel> infoModelLslResponse, Call call, Response response) {
                if (infoModelLslResponse.code == LslResponse.RESPONSE_OK) {
                    UIUtil.showToast("发布信息成功！");

                    Log.e(TAG, "onSuccess: data："+infoModelLslResponse.data.videoUrl.get(0).toString() );
                    // 只有公告和作业才发布推送
                    if (infoType == 1 || infoType == 2){
                        sendMsgToOthers(classId,infoType);
                    }

                    Log.e(TAG, infoType + "");
                    if (infoType == InfoType.NOTICE) {
                        EventBus.getDefault().post(new NoticeEvent(infoModelLslResponse.data));
                        Log.e(TAG, "通知发起");
                    } else if (infoType == InfoType.HOMEWORK) {
                        EventBus.getDefault().post(new HomeworkEvent(infoModelLslResponse.data));
                        Log.e(TAG, "作业发起");
                    } else {
                        EventBus.getDefault().post(new CommunityEvent(infoModelLslResponse.data));
                        Log.e(TAG, "社区发起");
                    }
                    if (!SendSmallVideoActivity.this.isFinishing()) {
                        stopLoading();
                    }
                    SendSmallVideoActivity.this.finish();
                } else {
                    UIUtil.showToast("发布信息失败，请稍后再试！");
                    if (!SendSmallVideoActivity.this.isFinishing()) {
                        stopLoading();
                    }
                }
            }
        });
    }

    private void sendMsgToOthers(int classId,int infoType) {
        AppService.getInstance().sendMsgToOthersAsync(classId,infoType, new JsonCallback<LslResponse<Object>>() {
            @Override
            public void onSuccess(LslResponse<Object> objectLslResponse, Call call, Response response) {
                Log.e(TAG,objectLslResponse.msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_cancel:
//                hesitate();
//                break;
//            case R.id.tv_send:
//                break;
            case R.id.iv_video_screenshot:
                startActivity(new Intent(this, VideoPlayerActivity.class).putExtra(
                        "path", videoUri));
                break;
        }
    }


    @Override
    public void onBackPressed() {
        hesitate();
    }

    private void hesitate() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.hint)
                    .setMessage(R.string.record_camera_exit_dialog_message)
                    .setNegativeButton(
                            R.string.record_camera_cancel_dialog_yes,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();

//                                    FileUtils.deleteDir(getIntent().getStringExtra(MediaRecorderActivity.OUTPUT_DIRECTORY));

                                }

                            })
                    .setPositiveButton(R.string.record_camera_cancel_dialog_no,
                            null).setCancelable(false).show();
        } else {
            dialog.show();
        }
    }

}

package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.AppService;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.adapter.CommonAdapter;
import com.example.nanchen.aiyaschoolpush.adapter.ViewHolder;
import com.example.nanchen.aiyaschoolpush.config.Consts;
import com.example.nanchen.aiyaschoolpush.model.User;
import com.example.nanchen.aiyaschoolpush.model.info.CommentInfoModel;
import com.example.nanchen.aiyaschoolpush.model.info.InfoModel;
import com.example.nanchen.aiyaschoolpush.model.info.UserModel;
import com.example.nanchen.aiyaschoolpush.net.okgo.JsonCallback;
import com.example.nanchen.aiyaschoolpush.net.okgo.LslResponse;
import com.example.nanchen.aiyaschoolpush.ui.view.IcomoonTextView;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;
import com.example.nanchen.aiyaschoolpush.ui.view.WavyLineView;
import com.example.nanchen.aiyaschoolpush.utils.ScreenUtil;
import com.example.nanchen.aiyaschoolpush.utils.SoftInputMethodUtil;
import com.example.nanchen.aiyaschoolpush.utils.TimeUtils;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

public class LookDetailActivity extends ActivityBase {

    private static final String TAG = "LookDetailActivity";
    private TitleView mTitleBar;
    private EditText mEditText;
    private IcomoonTextView mTextSend;
    private TextView mTextView;
    private boolean isReply = false;

    private static final String EXTRA_KEY = "v";
    private ListView mListView;
    private InfoModel mInfoModel;

    private CommonAdapter<CommentInfoModel> mAdapter;
    private List<CommentInfoModel> mCommentInfoModels;

    private String commentText = "";
    private String replyId;


    public static void start(Context context, InfoModel noticeModel) {
        Intent intent = new Intent(context, LookDetailActivity.class);
        intent.putExtra(EXTRA_KEY, noticeModel);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_detail);

        if (getIntent() == null) {
            return;
        }
        mInfoModel = (InfoModel) getIntent().getSerializableExtra(EXTRA_KEY);
        initList();
        bindView();

    }

    private void initList() {
        mCommentInfoModels = new ArrayList<>();

        if (mInfoModel.commentInfo != null && mInfoModel.commentInfo.size() != 0){
            mCommentInfoModels.addAll(mInfoModel.commentInfo);
        }
    }


    private void showKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) mEditText.getContext().getSystemService(INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(mEditText, 0);
    }

    private void bindView() {

        mTitleBar = (TitleView) findViewById(R.id.detail_titleBar);
        mTitleBar.setLeftButtonAsFinish(this);
        mTitleBar.setTitle("通知详情");

        mEditText = (EditText) findViewById(R.id.detail_edit);
        mTextSend = (IcomoonTextView) findViewById(R.id.detail_send);
        mTextView = (TextView) findViewById(R.id.detail_send_text);

        mTextSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommentCheck();
            }
        });

        mEditText.addTextChangedListener(new MyTextWatcher());


        mListView = (ListView) findViewById(R.id.detail_lv);

        mAdapter = new CommonAdapter<CommentInfoModel>(this, mCommentInfoModels, R.layout.layout_detail_comment) {
            @Override
            public void convert(ViewHolder holder, CommentInfoModel item) {
                if (TextUtils.isEmpty(item.commentUser.avatar)) {
                    holder.setImageDrawable(R.id.comment_image, getResources().getDrawable(R.drawable.default_avatar));
                } else {
                    holder.setImageByUrl(R.id.comment_image, Consts.API_SERVICE_HOST+item.commentUser.avatar);
                }
                holder.setText(R.id.comment_name, item.commentUser.nickname);
                holder.setText(R.id.comment_time, TimeUtils.longToDateTime(item.time));
                if (item.replyUser == null){
                    holder.setText(R.id.comment_text, item.content);
                }else{
                    holder.setText(R.id.comment_text, "回复 "+item.replyUser.nickname+": "+item.content);
                }
            }
        };


        View topView = getHeaderView();
        mListView.addHeaderView(topView,null,false);

        View lineView = getLineView();
        mListView.addHeaderView(lineView,null,false);


        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                UIUtil.showToast(position-2+"");

                // 这里点击的时候把发送按钮设置为不可点击和灰色，这里算一个小bug
                mTextSend.setTextColor(getResources().getColor(R.color.gray19));
                mTextSend.setClickable(false);
                Log.e(TAG,mCommentInfoModels.get(position-2).toString());
                replyId = mCommentInfoModels.get(position-2).username;
                commentText = "回复 " + mCommentInfoModels.get(position-2).commentUser.nickname + ":";
                mEditText.setText(commentText);
                mEditText.requestFocus();
                mEditText.setSelection(commentText.length()); // 将光标移至文字末尾
                showKeyboard(); // 弹出软键盘
            }
        });

        SoftInputMethodUtil.HideSoftInput(mEditText.getWindowToken());
    }

    /**
     * 获取赞的人数
     */
    private void getSupportView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_comment_support, null);
        TextView textView = (TextView) view.findViewById(R.id.comment_support_text);
        TextView tv_line = (TextView) view.findViewById(R.id.comment_line2);

    }


    /**
     * 获取波浪线View
     */
    private View getLineView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_wavy_line, null);
        WavyLineView mWavyLine = (WavyLineView) view.findViewById(R.id.wavyLine);
        int initStrokeWidth = 1;
        int initAmplitude = 5;
        float initPeriod = (float) (2 * Math.PI / 60);
        mWavyLine.setPeriod(initPeriod);
        mWavyLine.setAmplitude(initAmplitude);
        mWavyLine.setStrokeWidth(ScreenUtil.dp2px(initStrokeWidth));
        return view;
    }

    /**
     * 获取信息发布者的View
     */
    private View getHeaderView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_detail_header, null);
        CircleImageView avatar = (CircleImageView) view.findViewById(R.id.notice_item_avatar);
        TextView tv_name = (TextView) view.findViewById(R.id.notice_item_name);
        TextView tv_time = (TextView) view.findViewById(R.id.notice_item_time);
        TextView tv_comment = (TextView) view.findViewById(R.id.notice_item_comment);
        TextView tv_like = (TextView) view.findViewById(R.id.notice_item_like);
        TextView tv_content = (TextView) view.findViewById(R.id.notice_item_content);

        if (TextUtils.isEmpty(mInfoModel.user.avatar)) {
            Picasso.with(this).load(R.drawable.default_avatar).into(avatar);
        } else {
            Picasso.with(this).load(Consts.API_SERVICE_HOST+mInfoModel.user.avatar).into(avatar);
        }
        tv_name.setText(mInfoModel.user.nickname);
        tv_time.setText(TimeUtils.longToDateTime(mInfoModel.time));
        tv_content.setText(mInfoModel.content);
        tv_like.setText(String.format(Locale.CHINA, "赞 %d", mInfoModel.praiseCount));
        tv_comment.setText(String.format(Locale.CHINA, "评论 %d", mInfoModel.commentCount));

        return view;
    }

    private String comment_ui = "";

    private void sendCommentCheck() {
        comment_ui = mEditText.getText().toString().trim();
        String comment = "";
        Log.e(TAG,commentText);
//        if (comment_ui.contains(commentText)){//代表是回复的
//            if (comment_ui.split(":").length == 1){
//                UIUtil.showToast("评论内容不能为空");
//                return;
//            }
//            comment = comment_ui.substring(commentText.length());
//        }else{
//            if (TextUtils.isEmpty(comment_ui)) {
//                UIUtil.showToast("评论内容不能为空");
//                return;
//            }else{
//                comment = comment_ui;
//            }
//        }

        if (comment_ui.contains(":") && comment_ui.length()<=commentText.length()){
            UIUtil.showToast("评论内容不能为空！");
            return;
        }
        if (comment_ui.contains(":")){
            comment = comment_ui.substring(commentText.length());
        }else{
            replyId = null;
            comment = comment_ui;
        }

        UIUtil.showToast("正在尝试发送....");
        insertInfo(comment);
        SoftInputMethodUtil.HideSoftInput(mEditText.getWindowToken());
        cleanEdit();
    }

    /**
     * 插入信息到信息表
     */
    private void insertInfo(final String comment) {
        commentText = "";
        if (AppService.getInstance().getCurrentUser() != null){
            showLoading(this);
            int mainId = mInfoModel.mainid;
            String username = AppService.getInstance().getCurrentUser().username;
            AppService.getInstance().insertCommentAsync(mainId,username,
                    comment, replyId, new JsonCallback<LslResponse<Object>>() {
                        @Override
                        public void onSuccess(LslResponse<Object> objectLslResponse, Call call, Response response) {
                            if (objectLslResponse.code == LslResponse.RESPONSE_OK){
                                UIUtil.showToast("评论成功！");
                                CommentInfoModel commentInfoModel = new CommentInfoModel();
                                User user = AppService.getInstance().getCurrentUser();
                                UserModel userModel = new UserModel();
                                int len = Consts.API_SERVICE_HOST.length();
                                Log.e(TAG,"API长度:"+len+"");
                                if (user.icon.length()>len){
                                    userModel.avatar = user.icon.substring(len);
                                }
                                userModel.nickname = user.nickname;
                                commentInfoModel.commentUser = userModel;
                                commentInfoModel.time = System.currentTimeMillis()/1000;
                                commentInfoModel.content = comment_ui;
                                mCommentInfoModels.add(commentInfoModel);
                                mAdapter.notifyDataSetChanged();
                            }else{
                                UIUtil.showToast("评论失败，请稍后再试!");
                            }
                            stopLoading();
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void cleanEdit() {
        mEditText.setText("");
        mEditText.setHint("写评论...");

    }

    private class MyTextWatcher implements TextWatcher {



        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mEditText.length() > 0) {
                mTextSend.setTextColor(getResources().getColor(R.color.gray_blue));
                mTextSend.setClickable(true);
            } else {
                mTextSend.setTextColor(getResources().getColor(R.color.gray19));
                mTextSend.setClickable(false);
            }
        }
    }
}

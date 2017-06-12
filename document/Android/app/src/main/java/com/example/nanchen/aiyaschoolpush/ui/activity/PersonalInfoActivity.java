package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.example.nanchen.aiyaschoolpush.CropOption;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.adapter.CommonAdapter;
import com.example.nanchen.aiyaschoolpush.adapter.ViewHolder;
import com.example.nanchen.aiyaschoolpush.AppService;
import com.example.nanchen.aiyaschoolpush.config.Consts;
import com.example.nanchen.aiyaschoolpush.helper.event.UpdateUserEvent;
import com.example.nanchen.aiyaschoolpush.helper.DemoHelper;
import com.example.nanchen.aiyaschoolpush.model.User;
import com.example.nanchen.aiyaschoolpush.net.okgo.JsonCallback;
import com.example.nanchen.aiyaschoolpush.net.okgo.LslResponse;
import com.example.nanchen.aiyaschoolpush.helper.receiver.AvatarReceiver;
import com.example.nanchen.aiyaschoolpush.utils.IntentUtil;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;
import com.example.nanchen.aiyaschoolpush.ui.view.LinearLayoutListItemView;
import com.example.nanchen.aiyaschoolpush.ui.view.OnLinearLayoutListItemClickListener;
import com.example.nanchen.aiyaschoolpush.ui.view.SelectDialog;
import com.example.nanchen.aiyaschoolpush.ui.view.SelectDialog.SelectDialogListener;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;
import com.example.nanchen.aiyaschoolpush.ui.view.UserInfoView;
import com.example.nanchen.aiyaschoolpush.ui.view.UserInfoView.OnItemClickListener;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import okhttp3.Call;
import okhttp3.Response;

public class PersonalInfoActivity extends ActivityBase {

    private TitleView mTitleBar;
    private UserInfoView mUserInfo;
    private LinearLayoutListItemView mItemName;
    private LinearLayoutListItemView mItemAddress;
    private LinearLayoutListItemView mItemPwd;
    private static final String TAG = "PersonalInfoActivity";
    private NiftyDialogBuilder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        bindView();
    }

    private Crouton mCrouton;

    private void showCrouton(String desc) {
        if (mCrouton != null){
            mCrouton.cancel();
        }
        mCrouton = Crouton.makeText(getWeakContext(), desc, Style.ALERT, R.id.personal_info_toast);
        mCrouton.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCrouton != null){
            mCrouton.cancel();
            mCrouton = null;
        }
        if (dialogBuilder != null){
            dialogBuilder.cancel();
            dialogBuilder = null;
        }
    }


    private PersonalInfoActivity getWeakContext(){
        WeakReference<PersonalInfoActivity> weakReference = new WeakReference<>(PersonalInfoActivity.this);
        return weakReference.get();
    }

    private void bindView() {
        mTitleBar = (TitleView) findViewById(R.id.personal_info_title);
        mTitleBar.setTitle("个人信息");
        mTitleBar.setLeftButtonAsFinish(this);
//        mTitleBar.setLeftButtonOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                IntentUtil.newIntent(PersonalInfoActivity.this,MainActivity.class);
//                PersonalInfoActivity.this.finish();
//            }
//        });

        mUserInfo = (UserInfoView) findViewById(R.id.personal_info_image);
        mUserInfo.setHeight(60);
        mUserInfo.setHeadImageSize(50);
        mUserInfo.setBackgroundImage(R.drawable.background_linearlayout_listitem);
        mUserInfo.setUserNameText("我的头像");
        Log.e(TAG,AppService.getInstance().getCurrentUser().toString());
        if (AppService.getInstance().getCurrentUser() != null) {
            String avatarUrl = AppService.getInstance().getCurrentUser().icon;
            Log.e(TAG, "avatarUrl:" + avatarUrl);
            if (!TextUtils.isEmpty(avatarUrl) && !avatarUrl.equals("null")) {
                mUserInfo.setHeadImage(avatarUrl);
            }
        }

        mUserInfo.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick() {
                selectPhoto();
            }
        });

        mItemName = (LinearLayoutListItemView) findViewById(R.id.personal_info_name);

        mItemName.setOnLinearLayoutListItemClickListener(new OnLinearLayoutListItemClickListener() {
            @Override
            public void onLinearLayoutListItemClick(Object object) {
                View view = LayoutInflater.from(getWeakContext()).inflate(R.layout.view_dialog_edit, null);
                final EditText mEditText = (EditText) view.findViewById(R.id.view_dialog_edit);
                dialogBuilder = NiftyDialogBuilder.getInstance(getWeakContext());
                dialogBuilder.withTitle("设置你的昵称")                                  //.withTitle(null)  no title
                        .withMessage(null)
                        .withDialogColor(getResources().getColor(R.color.main_bg_color1))                               //def  | withDialogColor(int resid)                               //def
                        .withIcon(getResources().getDrawable(R.mipmap.icon))
                        .withEffect(Effectstype.RotateLeft)                                         //def Effectstype.Slidetop
                        .withButton1Text("确定")                                      //def gone
                        .withButton2Text("取消")                                  //def gone
                        .setCustomView(view, mItemName.getContext())         //.setCustomView(View or ResId,context)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String value = mEditText.getText().toString().trim();
                                updateUserInfo("nickname",value);
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                            }
                        })
                        .show();
            }
        });

        mItemAddress = (LinearLayoutListItemView) findViewById(R.id.personal_info_address);
        mItemAddress.setOnLinearLayoutListItemClickListener(new OnLinearLayoutListItemClickListener() {
            @Override
            public void onLinearLayoutListItemClick(Object object) {
                View view = LayoutInflater.from(getWeakContext()).inflate(R.layout.view_dialog_edit, null);
                final EditText mEditText = (EditText) view.findViewById(R.id.view_dialog_edit);
                dialogBuilder = NiftyDialogBuilder.getInstance(getWeakContext());
                dialogBuilder.withTitle("设置你的地址")                                  //.withTitle(null)  no title
                        .withMessage(null)
                        .withDialogColor(getResources().getColor(R.color.main_bg_color1))                               //def  | withDialogColor(int resid)                               //def
                        .withIcon(getResources().getDrawable(R.mipmap.icon))
                        .withEffect(Effectstype.Slideright)                                         //def Effectstype.Slidetop
                        .withButton1Text("确定")                                      //def gone
                        .withButton2Text("取消")                                  //def gone
                        .setCustomView(view, mItemAddress.getContext())         //.setCustomView(View or ResId,context)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String value = mEditText.getText().toString().trim();
                                updateUserInfo("address",value);
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                            }
                        })
                        .show();
            }
        });

        mItemPwd = (LinearLayoutListItemView) findViewById(R.id.personal_info_pwd);
        mItemPwd.setOnLinearLayoutListItemClickListener(new OnLinearLayoutListItemClickListener() {
            @Override
            public void onLinearLayoutListItemClick(Object object) {
//                View view = LayoutInflater.from(PersonalInfoActivity.this).inflate(R.layout.view_dialog_pwd,null);
//                final EditText editPwd1 = (EditText) view.findViewById(R.id.dialog_pwd1);
//                final EditText editPwd2 = (EditText) view.findViewById(R.id.dialog_pwd2);
//                final EditText editPwd3 = (EditText) view.findViewById(R.id.dialog_pwd3);
//                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(PersonalInfoActivity.this);
//                dialogBuilder.withTitle("修改密码")                                  //.withTitle(null)  no title
//                        .withMessage(null)
//                        .withDialogColor(getResources().getColor(R.color.main_bg_color1))                               //def  | withDialogColor(int resid)                               //def
//                        .withIcon(getResources().getDrawable(R.mipmap.icon))
//                        .withEffect(Effectstype.Newspager)                                         //def Effectstype.Slidetop
//                        .withButton1Text("确定")                                      //def gone
//                        .withButton2Text("取消")                                  //def gone
//                        .setCustomView(view,mItemPwd.getContext())         //.setCustomView(View or ResId,context)
//                        .setButton1Click(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                String pwd1 = editPwd1.getText().toString().trim();
//                                String pwd2 = editPwd2.getText().toString().trim();
//                                String pwd3 = editPwd3.getText().toString().trim();
//                                dialogBuilder.dismiss();
//                            }
//                        })
//                        .setButton2Click(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialogBuilder.dismiss();
//                            }
//                        })
//                        .show();
                IntentUtil.newIntent(PersonalInfoActivity.this, ResetPwdActivity.class);
            }
        });

        User user = AppService.getInstance().getCurrentUser();
        if (user != null){
            mItemName.setRightText(user.nickname);
            mItemAddress.setRightText(user.address);
        }
    }

    /**
     * 更新用户信息
     * @param action        需要更新的项
     * @param value         需要更新的值
     */
    private void updateUserInfo(final String action, final String value) {
        User user = AppService.getInstance().getCurrentUser();
        if (user != null){
            showLoading(this);
            AppService.getInstance().updateUserInfoAsync(user.username, action, value, new JsonCallback<LslResponse<Object>>() {
                @Override
                public void onSuccess(LslResponse<Object> objectLslResponse, Call call, Response response) {
                    if (objectLslResponse.code == LslResponse.RESPONSE_OK){
                        switch (action){
                            case "address":
                                mItemAddress.setRightText(value);
                                AppService.getInstance().getCurrentUser().address = value;
                                break;
                            case "nickname":
                                mItemName.setRightText(value);
                                AppService.getInstance().getCurrentUser().nickname = value;
                                break;
                        }
                        EventBus.getDefault().post(new UpdateUserEvent());//发送更新事件
                    }
                    showCrouton(objectLslResponse.msg);
                    dialogBuilder.dismiss();
                    dialogBuilder.cancel();
                    dialogBuilder = null;
                    stopLoading();
                }
            });
        }
    }

    private final int PHOTO_PICKED_FROM_CAMERA = 1; // 用来标识头像来自系统拍照
    private final int PHOTO_PICKED_FROM_FILE = 2; // 用来标识从相册获取头像
    private final int CROP_FROM_CAMERA = 3;

    private void getIconFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_PICKED_FROM_FILE);
    }

    private void selectPhoto() {
        List<String> list = new ArrayList<>();
        list.add("拍照");
        list.add("相册");
        showDialog(new SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getIconFromCamera();
                        break;
                    case 1:
                        getIconFromPhoto(); // 从系统相册获取
                        break;
                    default:
                        break;
                }
            }
        }, list);

    }

    private Uri imgUri; // 由于android手机的图片基本都会很大，所以建议用Uri，而不用Bitmap

    /**
     * 调用系统相机拍照
     */
    private void getIconFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "avatar_" + String.valueOf(System.currentTimeMillis()) + ".png"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(intent, PHOTO_PICKED_FROM_CAMERA);
    }

    private SelectDialog showDialog(SelectDialogListener listener, List<String> list) {
        SelectDialog dialog = new SelectDialog(this,
                R.style.transparentFrameWindowStyle, listener, list);
        if (canUpdateUI()) {
            dialog.show();
        }
        return dialog;
    }


    /**
     * 尝试裁剪图片
     */
    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<>();
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            UIUtil.showToast(this, "当前不支持裁剪图片!");
            return;
        }
        intent.setData(imgUri);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);

        // only one
        if (size == 1) {
            Intent intent1 = new Intent(intent);
            ResolveInfo res = list.get(0);
            intent1.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(intent1, CROP_FROM_CAMERA);
        } else {
            // 很多可支持裁剪的app
            for (ResolveInfo res : list) {
                CropOption co = new CropOption();
                co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                co.appIntent = new Intent(intent);
                co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                cropOptions.add(co);
            }

            CommonAdapter<CropOption> adapter = new CommonAdapter<CropOption>(this, cropOptions, R.layout.layout_crop_selector) {
                @Override
                public void convert(ViewHolder holder, CropOption item) {
                    holder.setImageDrawable(R.id.iv_icon, item.icon);
                    holder.setText(R.id.tv_name, item.title);
                }
            };

            AlertDialog.Builder builder = new Builder(this);
            builder.setTitle("choose a app");
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(cropOptions.get(which).appIntent, CROP_FROM_CAMERA);
                }
            });
            builder.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (imgUri != null) {
                        getContentResolver().delete(imgUri, null, null);
                        imgUri = null;
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PHOTO_PICKED_FROM_CAMERA:
                doCrop();
                break;
            case PHOTO_PICKED_FROM_FILE:
                imgUri = data.getData();
                doCrop();
                break;
            case CROP_FROM_CAMERA:
                if (data != null) {
                    setCropImg(data);
                }
                break;
            default:
                break;
        }
    }

    private void setCropImg(Intent picData) {
        Bundle bundle = picData.getExtras();
        if (bundle != null) {
            Bitmap mBitmap = bundle.getParcelable("data");
            mUserInfo.setHeadImage(mBitmap);
            String fileName = Environment.getExternalStorageDirectory() + "/"
                    + DemoHelper.getInstance().getCurrentUserName() + ".png";
            saveBitmap(fileName, mBitmap);
        }
    }


    private void saveBitmap(String fileName, Bitmap bitmap) {
        File file = new File(fileName);
        FileOutputStream fout = null;
        try {
            file.createNewFile();
            fout = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 60, fout);
            fout.flush();
            uploadAvatar(file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fout != null;
                fout.close();
                UIUtil.showToast(PersonalInfoActivity.this, "保存成功！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 上传头像
     *
     * @param file
     */
    private void uploadAvatar(File file) {
        showLoading(this);
        AppService.getInstance().upLoadAvatarAsync(file, new JsonCallback<LslResponse<User>>() {
            @Override
            public void onSuccess(LslResponse<User> userLslResponse, Call call, Response response) {
                if (userLslResponse.code == LslResponse.RESPONSE_OK) {// 头像上传成功，还应该把头像的url设置到数据库中去
                    updateAvatarUrl();

                    /* 下面的会报空指针异常 可行性需要进一步研究，暂时关闭个人资料页的上传更改头像功能*/
                    /* 目前采用另外一种方式，在跳转到这个页面的时候finish掉主页面，这里出去的时候重新start主页面*/
                    // 发广播提示主页面更新头像
                    sendBroadcast(new Intent(AvatarReceiver.AVATAR_ACTION));

                    Log.e(TAG, "头像上传到本地成功！");
                } else {
                    UIUtil.showToast(userLslResponse.msg);
                    Log.e(TAG, userLslResponse.msg);
                    stopLoading();
                }
            }
        });
    }


    /**
     * 把头像的url加到数据库中去
     */
    private void updateAvatarUrl() {
        final User user = AppService.getInstance().getCurrentUser();
        if (TextUtils.isEmpty(user.icon)) { // 如果当前头像地址为null,所以数据库还未存有,则把此url插入到数据库中
            final String iconUrl = "/user/avatar/" + user.username + ".png";
            AppService.getInstance().updateAvatarUrlAsync(user.username, iconUrl, 0, new JsonCallback<LslResponse<User>>() {
                @Override
                public void onSuccess(LslResponse<User> userLslResponse, Call call, Response response) {
                    UIUtil.showToast(userLslResponse.msg);
                    user.icon = Consts.API_SERVICE_HOST+iconUrl;
                    AppService.getInstance().setCurrentUser(user);
                    stopLoading();
                }
            });
        } else {// 否则不用插入，图片已经成功替换
            UIUtil.showToast("图片替换成功！");
            stopLoading();
        }
    }
}

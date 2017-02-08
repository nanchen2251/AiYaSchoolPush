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
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.example.nanchen.aiyaschoolpush.AppService;
import com.example.nanchen.aiyaschoolpush.CropOption;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.adapter.CommonAdapter;
import com.example.nanchen.aiyaschoolpush.adapter.ViewHolder;
import com.example.nanchen.aiyaschoolpush.model.User;
import com.example.nanchen.aiyaschoolpush.net.okgo.JsonCallback;
import com.example.nanchen.aiyaschoolpush.net.okgo.LslResponse;
import com.example.nanchen.aiyaschoolpush.ui.view.SelectDialog;
import com.example.nanchen.aiyaschoolpush.ui.view.SelectDialog.SelectDialogListener;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;
import com.example.nanchen.aiyaschoolpush.utils.SoftInputMethodUtil;
import com.example.nanchen.aiyaschoolpush.utils.TextUtil;
import com.example.nanchen.aiyaschoolpush.utils.TimeUtils;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;
import com.philliphsu.bottomsheetpickers.date.BottomSheetDatePickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog.OnDateSetListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity2 extends ActivityBase implements OnClickListener, OnDateSetListener {

    private static final String TAG = "RegisterActivity2";
    private String phone;
    private CircleImageView mHeadImage;
    private TextInputLayout mLayoutPwd1;
    private TextInputLayout mLayoutPwd2;
    private EditText mEditPwd1;
    private EditText mEditPwd2;
    private Button mBtnRegister;
    private EditText mEditName;
    private TitleView mTitleBar;

    private String mStr_HeadImagePath = null;


    // 用于存储拍照产生的临时文件路径URI
    private Uri mTempUri;
    //裁剪后返回的uri
    private Uri mZoomUri;
    private EditText mEditBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");

//        phone = "15600000924";
        if (TextUtils.isEmpty(phone)) {
            finish();
        }

        bindView();
    }

    private void bindView() {
        mTitleBar = (TitleView) findViewById(R.id.register2_titleBar);
        mTitleBar.setTitle("注册");
        mTitleBar.setLeftButtonAsFinish(this);

        mHeadImage = (CircleImageView) findViewById(R.id.register2_head_image);

        mLayoutPwd1 = (TextInputLayout) findViewById(R.id.register2_layout_pwd1);
        mLayoutPwd2 = (TextInputLayout) findViewById(R.id.register2_layout_pwd2);
        mEditPwd1 = (EditText) findViewById(R.id.register2_edt_pwd1);
        mEditPwd2 = (EditText) findViewById(R.id.register2_edt_pwd2);
        mEditName = (EditText) findViewById(R.id.register2_edt_name);
        mBtnRegister = (Button) findViewById(R.id.register2_btn_register);

        mEditBirthday = (EditText) findViewById(R.id.register2_edt_birthday);

        mEditBirthday.setKeyListener(null);//禁止输入内容
        mEditBirthday.setInputType(InputType.TYPE_NULL);//禁止手机软键盘

        mBtnRegister.setOnClickListener(this);
        mHeadImage.setOnClickListener(this);
        mEditBirthday.setOnClickListener(this);


//        if (mHeadImage != null){
//            mHeadImage.setCircleWidth(20);
//            mHeadImage.setLevel(4);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register2_head_image:
                selectPhoto();
                break;
            case R.id.register2_btn_register:
                register();
                break;
            case R.id.register2_edt_birthday:
                selectBirthday();
                break;
        }
    }

    /**
     * 选择生日
     */
    private void selectBirthday() {
        SoftInputMethodUtil.HideSoftInput(mEditBirthday.getWindowToken());//禁止弹出软键盘
        Calendar calendar = Calendar.getInstance();
        BottomSheetDatePickerDialog dialog = BottomSheetDatePickerDialog.newInstance(
                RegisterActivity2.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show(getSupportFragmentManager(), TAG);
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
//                        mTempUri = PhotoUtil.camera(RegisterActivity2.this);
                        getIconFromCamera();
                        break;
                    case 1:
//                        PhotoUtil.getIconFromPhoto(RegisterActivity2.this);
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
            mHeadImage.setImageBitmap(mBitmap);
            saveBitmap(Environment.getExternalStorageDirectory() +"/" + phone + ".png", mBitmap);
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
                if (fout != null){
                    fout.close();
                }
                UIUtil.showToast(RegisterActivity2.this, "保存成功！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String avatarUrl = "";

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
                if (userLslResponse.code == LslResponse.RESPONSE_OK) {
                    UIUtil.showToast("头像设置成功");
//                    updateAvatarUrl();
                    avatarUrl = "/user/avatar/" + phone + ".png";
                    stopLoading();
                } else {
                    UIUtil.showToast("头像上传失败：" + userLslResponse.msg);
                    stopLoading();
                }
            }
        });
    }

    /**
     * 把头像的url加到数据库中去
     */
    private void updateAvatarUrl() {
//        final String iconUrl = Consts.API_SERVICE_HOST + "/user/avatar/" + phone + ".png";

        Log.e(TAG,"avatar:"+avatarUrl+"   ,phone:"+phone);
        AppService.getInstance().updateAvatarUrlAsync(phone, avatarUrl, 0, new JsonCallback<LslResponse<User>>() {
            @Override
            public void onSuccess(LslResponse<User> userLslResponse, Call call, Response response) {
//                UIUtil.showToast(userLslResponse.msg);
                if (userLslResponse.code == LslResponse.RESPONSE_OK){
                    UIUtil.showToast("注册成功！");
                }else{
                    UIUtil.showToast("注册成功！但没有设置头像，可进到app中进行设置!");
                }
                stopLoading();
                RegisterActivity2.this.finish();

            }
        });
    }

    /**
     * 注册
     */
    private void register() {
        String pwd1 = mEditPwd1.getText().toString().trim();
        String pwd2 = mEditPwd2.getText().toString().trim();
        String name = mEditName.getText().toString().trim();
        String birthday = mEditBirthday.getText().toString().trim();

        Log.e(TAG, phone + " *** " + pwd1 + " *** " + name + " *** " + birthday);
        String longDate = TimeUtils.strToLongDate(birthday);

        if (TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2) || TextUtils.isEmpty(name)) {
            UIUtil.showToast(this, "请填写必要信息！");
            return;
        }
        if (pwd1.length() < 6) {
            UIUtil.showToast(this, "密码长度不能小于6！");
            mEditPwd1.requestFocus();
            return;
        }
        if (!pwd1.equals(pwd2)) {
            UIUtil.showToast(this, "两次输入的密码不一致！");
            mEditPwd1.requestFocus();
            return;
        }
        if (!TextUtil.isChinese(name)) {
            UIUtil.showToast(this, "姓名必须为中文字符!");
            mEditName.requestFocus();
            return;
        }

        Log.e(TAG, phone + " *** " + pwd1 + " *** " + name + " *** " + longDate  + " *** " + avatarUrl);

//        UIUtil.showToast(this,"正在尝试注册！");
        // 此处开始注册
        showLoading(this);

        AppService.getInstance().registerAsync(phone, pwd1, name, longDate, avatarUrl, new JsonCallback<LslResponse<User>>() {
            @Override
            public void onSuccess(LslResponse<User> userLslResponse, Call call, Response response) {
                if (userLslResponse.code == LslResponse.RESPONSE_OK) {
                    Log.e(TAG,avatarUrl);
                    if (TextUtils.isEmpty(avatarUrl)){
                        UIUtil.showToast("注册成功，但头像设置失败！请登录后自行修改！");
                    }else{
                        UIUtil.showToast("注册成功！");
                    }
                    stopLoading();
                    RegisterActivity2.this.finish();
//                    else{
//                        updateAvatarUrl();
//                    }
                } else {
                    UIUtil.showToast("注册失败" + userLslResponse.msg);
                    stopLoading();
                }
            }
        });
    }


    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mEditBirthday.setText(DateFormat.getDateFormat(this).format(cal.getTime()));
    }
}

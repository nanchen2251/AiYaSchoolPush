package com.example.nanchen.aiyaschoolpush.ui.fragment;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanchen.aiyaschoolpush.App;
import com.example.nanchen.aiyaschoolpush.AppService;
import com.example.nanchen.aiyaschoolpush.CropOption;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.adapter.CommonAdapter;
import com.example.nanchen.aiyaschoolpush.adapter.ViewHolder;
import com.example.nanchen.aiyaschoolpush.config.Consts;
import com.example.nanchen.aiyaschoolpush.helper.DemoHelper;
import com.example.nanchen.aiyaschoolpush.helper.event.UpdateUserEvent;
import com.example.nanchen.aiyaschoolpush.helper.receiver.AvatarReceiver;
import com.example.nanchen.aiyaschoolpush.helper.receiver.AvatarReceiver.AvatarCallback;
import com.example.nanchen.aiyaschoolpush.model.User;
import com.example.nanchen.aiyaschoolpush.net.okgo.JsonCallback;
import com.example.nanchen.aiyaschoolpush.net.okgo.LslResponse;
import com.example.nanchen.aiyaschoolpush.ui.activity.AboutActivity;
import com.example.nanchen.aiyaschoolpush.ui.activity.ActivityBase;
import com.example.nanchen.aiyaschoolpush.ui.activity.ChildInfoActivity;
import com.example.nanchen.aiyaschoolpush.ui.activity.LoginActivity;
import com.example.nanchen.aiyaschoolpush.ui.activity.MainActivity;
import com.example.nanchen.aiyaschoolpush.ui.activity.PersonalInfoActivity;
import com.example.nanchen.aiyaschoolpush.ui.view.LinearLayoutListItemView;
import com.example.nanchen.aiyaschoolpush.ui.view.OnLinearLayoutListItemClickListener;
import com.example.nanchen.aiyaschoolpush.ui.view.SelectDialog;
import com.example.nanchen.aiyaschoolpush.ui.view.SelectDialog.SelectDialogListener;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;
import com.example.nanchen.aiyaschoolpush.utils.IntentUtil;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;
import com.hyphenate.EMCallBack;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.fragment
 * @date 2016/10/08  08:57
 *
 * 我的页面
 */

public class MineFragment extends FragmentBase{
    private TitleView mTitleBar;
    private CircleImageView mHeadImage;
    private LinearLayoutListItemView mMenuReviseData;
    private LinearLayoutListItemView mMenuMyRoom;
    private LinearLayoutListItemView mMenuMyBaby;
    private LinearLayoutListItemView mMenuAbout;
    private Button mBtnExit;
    private LinearLayoutListItemView mItemServer;
    private static final String TAG = "MineFragment";
    private AvatarReceiver mAvatarReceiver;
    private IntentFilter mIntentFilter;
    private TextView mUsername;
    private TextView mAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        bindView(view);
        return view;
    }

    @Override
    public void onStart() {
        Log.e(TAG,"onStart");
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    private Crouton mCrouton;

    private void showCrouton(String desc) {
        if (mCrouton != null){
            mCrouton.cancel();
        }
        mCrouton = Crouton.makeText(getActivity(), desc, Style.ALERT);
        mCrouton.show();
    }



    private void bindView(View view) {

        mUsername = (TextView) view.findViewById(R.id.mine_name);
        mAddress = (TextView) view.findViewById(R.id.mine_city);

        mTitleBar = (TitleView) view.findViewById(R.id.mine_titleBar);
        mTitleBar.setTitle("我的");

        mHeadImage = (CircleImageView) view.findViewById(R.id.mine_image);

        if (AppService.getInstance().getCurrentUser() != null){
            String iconUrl = AppService.getInstance().getCurrentUser().icon;
            Log.e(TAG,iconUrl+"  ********** ");
            if (!TextUtils.isEmpty(iconUrl) && !iconUrl.equals("null")){
                // 设置picasso不允许缓存，以免头像更新后不能动态更新
                Picasso.with(getActivity()).load(iconUrl)
                        .placeholder(R.drawable.default_avatar)
                        .error(R.drawable.default_avatar)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(mHeadImage);
            }
        }


        mHeadImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });


        mMenuReviseData = (LinearLayoutListItemView) view.findViewById(R.id.mine_revise_data);
        mMenuMyRoom = (LinearLayoutListItemView) view.findViewById(R.id.mine_my_room);
        mMenuMyBaby = (LinearLayoutListItemView) view.findViewById(R.id.mine_my_baby);
        mMenuAbout = (LinearLayoutListItemView) view.findViewById(R.id.mine_about);
        mBtnExit = (Button) view.findViewById(R.id.mine_exit_btn);
        mBtnExit.setText("退出登录("+DemoHelper.getInstance().getCurrentUserName()+")");

        mBtnExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        mMenuReviseData.setOnLinearLayoutListItemClickListener(new OnLinearLayoutListItemClickListener() {
            @Override
            public void onLinearLayoutListItemClick(Object object) {
//                Crouton.makeText(getActivity(),"你点击了个人信息", Style.ALERT).show();
                IntentUtil.newIntent(getActivity(), PersonalInfoActivity.class);

                /* 由于在个人信息页面采取广播和Service方式都会报空指针异常，可行性有待考证
                * 所以暂时采用打开个人信息页面的时候关闭主页面，返回的时候重新start主页面*/
//                getActivity().finish();
            }
        });

        mMenuMyRoom.setOnLinearLayoutListItemClickListener(new OnLinearLayoutListItemClickListener() {
            @Override
            public void onLinearLayoutListItemClick(Object object) {
                showCrouton("你点击了我的空间");
            }
        });

        mMenuMyBaby.setOnLinearLayoutListItemClickListener(new OnLinearLayoutListItemClickListener() {
            @Override
            public void onLinearLayoutListItemClick(Object object) {
//                showCrouton("你点击了我的宝贝");
                IntentUtil.newIntent(getActivity(), ChildInfoActivity.class);
            }
        });

        mMenuAbout.setOnLinearLayoutListItemClickListener(new OnLinearLayoutListItemClickListener() {
            @Override
            public void onLinearLayoutListItemClick(Object object) {
                IntentUtil.newIntent(getActivity(), AboutActivity.class);
            }
        });


        mItemServer = (LinearLayoutListItemView) view.findViewById(R.id.mine_cloud_server);
        mItemServer.setOnLinearLayoutListItemClickListener(new OnLinearLayoutListItemClickListener() {
            @Override
            public void onLinearLayoutListItemClick(Object object) {
                // 跳转到七鱼云客服
                ConsultSource source = new ConsultSource(null, null, null);
                if(!Unicorn.isServiceAvailable()){
                    UIUtil.showToast("客服接口有问题，请稍后再试");
                }
                Unicorn.openServiceActivity(getActivity(), "爱吖客服", source);
            }
        });



        // 注册广播，千万记得销毁的时候关闭广播，否则造成内存泄漏
        mAvatarReceiver = new AvatarReceiver(new AvatarCallback() {
            @Override
            public void onAvatarChanged() {
                String iconUrl = AppService.getInstance().getCurrentUser().icon;
                Log.e(TAG,iconUrl+"  ********** ");
                if (!TextUtils.isEmpty(iconUrl)){
                    // 设置picasso不允许缓存，以免头像更新后不能动态更新
                    Picasso.with(getActivity()).load(iconUrl)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .into(mHeadImage);
                }
            }
        });
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(AvatarReceiver.AVATAR_ACTION);
        getActivity().registerReceiver(mAvatarReceiver,mIntentFilter);

        updateUserInfo();
    }

    private void logout() {
//        ((MainActivity)getActivity()).showLoading(getActivity());
        DemoHelper.getInstance().logout(false,new EMCallBack() {

            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    @SuppressWarnings("ConstantConditions")
                    public void run() {
//                        stopLoading();
                        // show login screen

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user.temp",MODE_PRIVATE);
                        String classid = sharedPreferences.getString("classid","");
                        Log.e(TAG,classid);
                        MiPushClient.subscribe(App.getAppContext(),classid,null);//设置订阅标签为classid
                        MiPushClient.unsetAlias(App.getAppContext(),classid,null);//退出登录后取消接收classid



                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
//                        ((MainActivity)getActivity()).stopLoading();
                        Toast.makeText(getActivity(), "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


    private final int PHOTO_PICKED_FROM_CAMERA = 1; // 用来标识头像来自系统拍照
    private final int PHOTO_PICKED_FROM_FILE = 2; // 用来标识从相册获取头像
    private final int CROP_FROM_CAMERA = 3;

    private void getIconFromPhoto(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_PICKED_FROM_FILE);
    }

    /**
     * 弹出选择图片或者拍照
     */
    private void selectPhoto() {
        List<String> list = new ArrayList<>();
        list.add("拍照");
        list.add("相册");
        showDialog(new SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
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
        },list);

    }

    private Uri imgUri; // 由于android手机的图片基本都会很大，所以建议用Uri，而不用Bitmap

    /**
     * 调用系统相机拍照
     */
    private void getIconFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "avatar_"+String.valueOf(System.currentTimeMillis())+".png"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
        startActivityForResult(intent,PHOTO_PICKED_FROM_CAMERA);
    }

    private SelectDialog showDialog(SelectDialogListener listener, List<String> list){
        SelectDialog dialog = new SelectDialog(getActivity(),
                R.style.transparentFrameWindowStyle,listener,list);
        if (((ActivityBase)getActivity()).canUpdateUI()){
            dialog.show();
        }
        return dialog;
    }


    /**
     * 尝试裁剪图片
     */
    private void doCrop(){
        final ArrayList<CropOption> cropOptions = new ArrayList<>();
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent,0);
        int size = list.size();
        if (size == 0){
            UIUtil.showToast(getActivity(),"当前不支持裁剪图片!");
            return;
        }
        intent.setData(imgUri);
        intent.putExtra("outputX",300);
        intent.putExtra("outputY",300);
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("scale",true);
        intent.putExtra("return-data",true);

        // only one
        if (size == 1){
            Intent intent1 = new Intent(intent);
            ResolveInfo res = list.get(0);
            intent1.setComponent(new ComponentName(res.activityInfo.packageName,res.activityInfo.name));
            startActivityForResult(intent1,CROP_FROM_CAMERA);
        }else {
            // 很多可支持裁剪的app
            for (ResolveInfo res : list) {
                CropOption co = new CropOption();
                co.title = getActivity().getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                co.icon = getActivity().getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                co.appIntent = new Intent(intent);
                co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName,res.activityInfo.name));
                cropOptions.add(co);
            }

            CommonAdapter<CropOption> adapter = new CommonAdapter<CropOption>(getActivity(),cropOptions,R.layout.layout_crop_selector) {
                @Override
                public void convert(ViewHolder holder, CropOption item) {
                    holder.setImageDrawable(R.id.iv_icon,item.icon);
                    holder.setText(R.id.tv_name,item.title);
                }
            };

            AlertDialog.Builder builder = new Builder(getActivity());
            builder.setTitle("choose a app");
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(cropOptions.get(which).appIntent,CROP_FROM_CAMERA);
                }
            });
            builder.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (imgUri != null){
                        getActivity().getContentResolver().delete(imgUri,null,null);
                        imgUri = null;
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
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
                if (data != null){
                    setCropImg(data);
                }
                break;
            default:
                break;
        }
    }

    private void setCropImg(Intent picData){
        Bundle bundle = picData.getExtras();
        if (bundle != null){
            Bitmap mBitmap = bundle.getParcelable("data");
            mHeadImage.setImageBitmap(mBitmap);
            String fileName = Environment.getExternalStorageDirectory() + "/"
                    +DemoHelper.getInstance().getCurrentUserName() + ".png";
            saveBitmap(fileName,mBitmap);

        }
    }

    private void saveBitmap(String fileName,Bitmap bitmap){
        File file = new File(fileName);
        FileOutputStream fout = null;
        try {
            file.createNewFile();
            fout = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG,60,fout);
            fout.flush();
            uploadAvatar(file);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } finally {
            try {
                assert fout != null;
                fout.close();
                bitmap.recycle();
                UIUtil.showToast(getActivity(),"保存成功！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 上传头像
     * @param file
     */
    private void uploadAvatar(File file) {
        ((MainActivity)getActivity()).showLoading(getActivity());
        AppService.getInstance().upLoadAvatarAsync(file, new JsonCallback<LslResponse<User>>() {
            @Override
            public void onSuccess(LslResponse<User> userLslResponse, Call call, Response response) {
                if (userLslResponse.code == LslResponse.RESPONSE_OK){// 头像上传成功，还应该把头像的url设置到数据库中去
                    updateAvatarUrl();
                    Log.e(TAG,"头像上传到本地成功！");
                    return;
                }
                UIUtil.showToast(userLslResponse.msg);
                Log.e(TAG,userLslResponse.msg);
                ((MainActivity)getActivity()).stopLoading();
            }
        });

        // 下面的方法仅仅是为了测试

//        AppService.getInstance().upLoadAvatarAsync(file, new StringCallback() {
//            @Override
//            public void onSuccess(String s, Call call, Response response) {
//                UIUtil.showToast(s);
//                Log.e(TAG,s);
//                ((MainActivity)getActivity()).stopLoading();
//            }
//        });
    }

    /**
     * 把头像的url加到数据库中去
     */
    private void updateAvatarUrl() {
        final User user = AppService.getInstance().getCurrentUser();
        if (TextUtils.isEmpty(user.icon)){ // 如果当前头像地址为null,所以数据库还未存有,则把此url插入到数据库中
            final String iconUrl = "/user/avatar/"+user.username+".png";
            AppService.getInstance().updateAvatarUrlAsync(user.username, iconUrl, 0, new JsonCallback<LslResponse<User>>() {
                @Override
                public void onSuccess(LslResponse<User> userLslResponse, Call call, Response response) {
                    UIUtil.showToast(userLslResponse.msg);
                    user.icon = Consts.API_SERVICE_HOST+iconUrl;
                    AppService.getInstance().setCurrentUser(user);
                    ((MainActivity)getActivity()).stopLoading();
                }
            });
//            AppService.getInstance().updateAvatarUrlAsync(user.username, iconUrl, 0, new StringCallback() {
//                @Override
//                public void onSuccess(String s, Call call, Response response) {
//                    UIUtil.showToast(s);
//                    Log.e(TAG,s);
//                    ((MainActivity)getActivity()).stopLoading();
//                }
//            });
        }else {// 否则不用插入，图片已经成功替换
            UIUtil.showToast("图片替换成功！");
            ((MainActivity)getActivity()).stopLoading();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mAvatarReceiver);
        EventBus.getDefault().unregister(this);
        if (mCrouton != null){
            mCrouton.cancel();
            mCrouton = null;
        }
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo(){
       if (AppService.getInstance().getCurrentUser() != null){
           String address = AppService.getInstance().getCurrentUser().address;
           String nickname = AppService.getInstance().getCurrentUser().nickname;
           if (TextUtils.isEmpty(nickname)){
               mUsername.setText("请设置昵称");
           }else{
               mUsername.setText(nickname);
           }
           if (TextUtils.isEmpty(address)){
               mAddress.setText("请设置地址");
           }else{
               mAddress.setText(address);
           }
       }
    }


    //定义处理接收方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UpdateUserEvent event) {
        Log.e(TAG,"通知收到:");
        updateUserInfo();
    }
}

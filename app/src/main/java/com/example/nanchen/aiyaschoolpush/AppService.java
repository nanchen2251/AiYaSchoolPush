package com.example.nanchen.aiyaschoolpush;

import com.example.nanchen.aiyaschoolpush.config.Consts;
import com.example.nanchen.aiyaschoolpush.model.PraiseModel;
import com.example.nanchen.aiyaschoolpush.model.User;
import com.example.nanchen.aiyaschoolpush.model.info.InfoModel;
import com.example.nanchen.aiyaschoolpush.model.info.UserModel;
import com.example.nanchen.aiyaschoolpush.net.okgo.JsonCallback;
import com.example.nanchen.aiyaschoolpush.net.okgo.LslResponse;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.api
 * @date 2016/11/09  11:19
 */

public class AppService {
    private static final String TAG = "AppService";

    private static AppService instance;
    private User mCurrentUser;


    private AppService(){
    }

    public static AppService getInstance(){
        if (instance == null){
            instance = new AppService();
        }
        return instance;
    }

    /**
     * 退出登录时重置此类
     */
    public static void resetInstance(){
        instance = null;
    }

    /**
     * 获取当前登录的用户
     * @return  当前用户
     */
    public User getCurrentUser(){
        return mCurrentUser;
    }

    public void setCurrentUser(User user){
        this.mCurrentUser = user;
    }

    

    /***************    用户系统 Begin    ******************/


    /**
     * 用户手机号验证是否已经注册
     * @param mobile    手机号
     * @param callback  回调
     */
    public void isUsableMobileAsync(String mobile, JsonCallback<LslResponse<User>> callback){
        String url = Consts.API_SERVICE_HOST+"/user/usable_mobile.php?mobile="+mobile;
        OkGo.get(url).execute(callback);
    }


    /**
     * 用户注册
     * @param username  用户名
     * @param password  密码
     * @param nickname  昵称
     * @param birthday  生日
     * @param callback  回调
     */
    public void registerAsync(String username,String password,String nickname,String birthday,String iconUrl,JsonCallback<LslResponse<User>> callback){
        String url = Consts.API_SERVICE_HOST + "/user/register.php";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("username",username);
        postParams.put("password",password);
        postParams.put("nickname",nickname);
        postParams.put("birthday",birthday);
        postParams.put("avatar",iconUrl);
        OkGo.post(url).params(postParams).execute(callback);
    }

    /**
     * 异步用户登录
     * @param username  用户名
     * @param password  用户密码
     * @param callback  回调
     */
    public void loginAsync(String username,String password,JsonCallback<LslResponse<User>> callback){
        String url = Consts.API_SERVICE_HOST + "/user/login.php";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("username",username);
        postParams.put("password",password);
        OkGo.post(url).params(postParams).execute(callback);
    }




    /**
     * 异步用户修改密码
     * @param username  用户名
     * @param password  新密码
     * @param callback  回调
     */
    public void resetPwdAsync(String username,String password,JsonCallback<LslResponse<User>> callback){
        String url = Consts.API_SERVICE_HOST + "/user/reset_pwd.php";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("username",username);
        postParams.put("password",password);
        OkGo.post(url).params(postParams).execute(callback);
    }

    /**
     * 异步用户头像上传
     * @param file      文件
     * @param callback  回调
     */
    public void upLoadAvatarAsync(File file,JsonCallback<LslResponse<User>> callback){
        String url = Consts.API_SERVICE_HOST + "/user/avatar.php";
        OkGo.post(url).params("avatar",file,file.getName()).execute(callback);
    }

    /**
     * 异步用户头像上传
     * @param file      文件
     * @param callback  回调  返回json字符串，只是为了测试
     */
    public void upLoadAvatarAsync(File file, StringCallback callback){
        String url = Consts.API_SERVICE_HOST + "/user/avatar.php";
        OkGo.post(url).params("avatar",file).execute(callback);
    }

    /**
     * 更新用户头像信息
     *
     * @param username  用户名
     * @param iconUrl   头像地址
     * @param type      传递类型
     * @param callback  回调
     */
    public void updateAvatarUrlAsync(String username,String iconUrl,int type,JsonCallback<LslResponse<User>> callback){
        String url = Consts.API_SERVICE_HOST+"/user/update_avatar.php?username="+username
                +"&iconUrl="+iconUrl+"&type="+type;
        OkGo.get(url).execute(callback);
    }


    /**
     * 更新用户信息
     * @param username  用户名
     * @param action    更新项
     * @param value     更新值
     * @param callback  回调
     */
    public void updateUserInfoAsync(String username,String action,String value,JsonCallback<LslResponse<Object>> callback){
        String url = Consts.API_SERVICE_HOST+"/user/update_user.php?username="+username
                +"&action="+action+"&value="+value;
        OkGo.get(url).execute(callback);
    }


    /***************    用户系统 End    ******************/


    /***************    信息系統 Begin    ******************/


    /**
     * 异步获取信息
     * @param classId   班级id
     * @param username  用户名  用于返回用户是否赞了主贴
     * @param infoType  信息类型 1 公告 2 作业 3 动态
     * @param start     信息从第几行开始提取
     * @param count     信息数目   这里一次获取10条  需要更多获取联系后台
     * @param callback  回调
     */
    public void getNoticeAsync(int classId,String username, int infoType,int start, int count,int lastId, JsonCallback<LslResponse<List<InfoModel>>> callback){
        String url = Consts.API_SERVICE_HOST+"/info/info_main.php?classId="+classId+
                "&username="+username+"&infoType="+infoType+"&start="+start+"&count="+count+"&lastId="+lastId;
        OkGo.get(url).execute(callback);
    }

    /**
     * 异步获取发布信息的用户信息
     * @param username  用户名
     * @param callback  回调
     */
    public void getUserInfoAsync(String username, JsonCallback<LslResponse<UserModel>> callback){
        String url = Consts.API_SERVICE_HOST+"/info/get_user.php?username="+username;
        OkGo.get(url).execute(callback);
    }


    /**
     * 异步插入数据到评论
     * @param mainId    信息表条目id
     * @param username  发布人id
     * @param content   发布内容
     * @param reply     回复的人的id
     * @param callback  回调
     */
    public void insertCommentAsync(int mainId, String username, String content,
                                   String reply, JsonCallback<LslResponse<Object>> callback){
        String url = Consts.API_SERVICE_HOST+"/info/insert_comment.php";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("mainId",mainId+"");
        postParams.put("username",username);
        postParams.put("content",content);
        postParams.put("reply",reply);
        OkGo.post(url).params(postParams).execute(callback);
    }

    /**
     * 更新点赞的信息
     * @param mainId    主贴的id
     * @param username  用户名 - 用户只能删除自己发布的信息
     * @param callback  回调接口
     */
    public void updatePraiseAsync(int mainId, String username, JsonCallback<LslResponse<PraiseModel>> callback){
        String url = Consts.API_SERVICE_HOST+"/info/praise.php?mainId="+mainId+"&username="+username;
        OkGo.get(url).execute(callback);
    }


    /**
     * 异步发送消息给服务器
     * @param classId       班级id
     * @param username      发布人用户名
     * @param infoType      信息类型  1 公告 2 作业  3 动态
     * @param content       发布内容
     * @param picUrls       图片地址
     * @param callback      回调
     */
    public void addMainInfoAsync(int classId, String username, int infoType, String content, List<String> picUrls,boolean isVideo, JsonCallback<LslResponse<InfoModel>> callback){
        String url = Consts.API_SERVICE_HOST+"/info/add_main.php";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("classId",classId+"");
        postParams.put("username",username);
        postParams.put("infoType",infoType+"");
        postParams.put("content",content);
        postParams.put("picCount",picUrls.size()+"");
        if (isVideo){
            postParams.put("type","video");
            postParams.put("picUrl0","/info/video/"+picUrls.get(0));
            postParams.put("picUrl1","/info/pic/"+picUrls.get(1));
        }else{
            postParams.put("type","pic");
            for (int i = 0; i < picUrls.size(); i++) {
                postParams.put("picUrl"+i,"/info/pic/"+picUrls.get(i));
            }
        }
        OkGo.post(url).params(postParams).execute(callback);
    }

    /**
     * 异步提醒服务器推送相关消息给他人
     *
     *
     * @param classId   班级id
     * @param infoType  信息类型
     * @param callback  回调
     */
    public void sendMsgToOthersAsync(int classId,int infoType,JsonCallback<LslResponse<Object>> callback){
        String url = Consts.API_SERVICE_HOST+"/android_example.php?classId="+classId+"&infoType="+infoType;
        OkGo.get(url).execute(callback);
    }


    /**
     * 附件上传
     * @param files     文件集合
     * @param callback  回调
    */
    public void upLoadFileAsync(List<File> files,JsonCallback<LslResponse<User>> callback){
        String url = Consts.API_SERVICE_HOST + "/info/attachment.php";
//        OkGo.post(url).params("size",files.size()).addFileParams("files",files).execute(callback);
//        String url = Consts.API_SERVICE_HOST + "/user/avatar.php";
        PostRequest postRequest = OkGo.post(url);
        for (int i = 0; i < files.size(); i++) {
            postRequest.params("file"+i,files.get(i),files.get(i).getName());
        }
        postRequest.params("size",files.size());
        postRequest.execute(callback);
    }


    /***************    信息系統 End      ******************/

}

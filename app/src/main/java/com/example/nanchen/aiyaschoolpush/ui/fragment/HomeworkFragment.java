package com.example.nanchen.aiyaschoolpush.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nanchen.aiyaschoolpush.VideoPlayerActivity;
import com.example.nanchen.aiyaschoolpush.config.Consts;
import com.example.nanchen.aiyaschoolpush.helper.event.HomeworkEvent;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.ui.activity.LookDetailActivity;
import com.example.nanchen.aiyaschoolpush.adapter.CommonRecyclerAdapter;
import com.example.nanchen.aiyaschoolpush.adapter.CommonRecyclerHolder;
import com.example.nanchen.aiyaschoolpush.AppService;
import com.example.nanchen.aiyaschoolpush.model.PraiseModel;
import com.example.nanchen.aiyaschoolpush.model.info.InfoModel;
import com.example.nanchen.aiyaschoolpush.model.info.InfoType;
import com.example.nanchen.aiyaschoolpush.model.info.PicModel;
import com.example.nanchen.aiyaschoolpush.net.okgo.JsonCallback;
import com.example.nanchen.aiyaschoolpush.net.okgo.LslResponse;
import com.example.nanchen.aiyaschoolpush.utils.TimeUtils;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.lzy.okgo.exception.OkGoException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.fragment
 * @date 2016/10/08  08:57
 */

public class HomeworkFragment extends FragmentBase {
    private static final String TAG = "HomeworkFragment";
    private XRecyclerView mRecyclerView;
    private CommonRecyclerAdapter<InfoModel> mAdapter;
    private List<InfoModel> mInfoModels;
    private int start = 0;
    private int count = 10;//设置一次获取的条目数
    private View footerView;
    private ImageView mImageView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homework,container,false);
        initView(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        EventBus.getDefault().register(this);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        if (mInfoModels != null){
            mInfoModels.clear();
            mInfoModels = null;
        }
    }

    //定义处理接收方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HomeworkEvent event) {
        if (event.getInfoModel() != null){
            mInfoModels.add(0,event.getInfoModel());
            mAdapter.notifyDataSetChanged();
        }else{
            //否则直接刷新
            mRecyclerView.setRefreshing(true);
        }
    }

    private void initView(View view) {
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.homework_recycler);
        mImageView = (ImageView) view.findViewById(R.id.no_content);

//        mNoticeModelList = new ArrayList<>();
        mInfoModels = new ArrayList<>();

        // 获取一些假数据
//        getSomeData();


        loadData(true);


        mAdapter = new CommonRecyclerAdapter<InfoModel>(getActivity(), mInfoModels, R.layout.layout_notice_item) {
            @Override
            public void convert(final CommonRecyclerHolder holder, final InfoModel item, final int position, boolean isScrolling) {
                if (TextUtils.isEmpty(item.user.avatar)) {
                    holder.setImageResource(R.id.notice_item_avatar, R.drawable.default_avatar);
                } else {
                    Log.e(TAG, item.user.avatar);
                    holder.setImageByUrl(R.id.notice_item_avatar,Consts.API_SERVICE_HOST+item.user.avatar);
                }
                holder.setText(R.id.notice_item_name, item.user.nickname);
                holder.setText(R.id.notice_item_time, TimeUtils.longToDateTime(item.time));
                holder.setText(R.id.notice_item_content, item.content);
                holder.setText(R.id.notice_item_like, "赞 " + item.praiseCount);
                holder.setText(R.id.notice_item_comment, "评论 " + item.commentCount);
                if (item.isIPraised){
                    holder.setTextColor(R.id.notice_item_like, getResources().getColor(R.color.red));
                }else{
                    holder.setTextColor(R.id.notice_item_like, getResources().getColor(R.color.gray));
                }
                // 如果是视频
                if (item.videoUrl != null && item.videoUrl.size() != 0){
                    holder.setVisibility(R.id.videoImage,View.VISIBLE);
                    holder.setVisibility(R.id.community_nineGrid,View.GONE);
                    String imgUrl = Consts.API_SERVICE_HOST+item.picUrls.get(0).imageUrl;
                    Log.e(TAG, "convert: imgUrl:"+imgUrl );
                    holder.setImageByUrl(R.id.videoImage,imgUrl);
                    final String videoUrl = Consts.API_SERVICE_HOST+item.videoUrl.get(0).videoUrl;
                    Log.e(TAG, "convert: videoUrl:"+videoUrl );
                    holder.setOnClckListener(R.id.videoImage, new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), VideoPlayerActivity.class).putExtra(
                                    "path", videoUrl));
                        }
                    });
                }else{
                    holder.setVisibility(R.id.videoImage,View.GONE);
                    holder.setVisibility(R.id.community_nineGrid,View.VISIBLE);
                    ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
                    List<PicModel> picModels = item.picUrls;
                    if (picModels != null && picModels.size() != 0){
                        for (PicModel picModel:picModels) {
                            ImageInfo imageInfo = new ImageInfo();
                            imageInfo.setThumbnailUrl(Consts.API_SERVICE_HOST+picModel.imageUrl);
                            imageInfo.setBigImageUrl(Consts.API_SERVICE_HOST+picModel.imageUrl);
                            imageInfoList.add(imageInfo);
                        }
                    }
                    holder.setNineGridAdapter(R.id.community_nineGrid,new NineGridViewClickAdapter(getActivity(), imageInfoList));
                }
                Log.e(TAG,item.mainid+","+item.isIPraised);
                holder.setOnRecyclerItemClickListener(R.id.notice_item_like, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertPraised(item,holder);

                    }
                });

                holder.setOnRecyclerItemClickListener(R.id.notice_item_comment, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getActivity(), "你点击了评论，将进入详情页面！", Toast.LENGTH_SHORT).show();
                        LookDetailActivity.start(getActivity(), mInfoModels.get(position),InfoType.HOMEWORK);
                    }
                });
            }
        };


        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);


        mRecyclerView.setLoadingListener(new LoadingListener() {
            @Override
            public void onRefresh() {
                loadData(true);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
//                getSomeData();
                loadData(false);
                mRecyclerView.loadMoreComplete();
            }
        });

        footerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_not_more, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        mRecyclerView.addFootView(footerView);
        footerView.setVisibility(View.GONE);

        // 设置下拉图片为自己的图片
        mRecyclerView.setArrowImageView(R.mipmap.refresh_icon);

    }

    /**
     * 把赞的信息提交到服务器
     */
    private void insertPraised(final InfoModel item,final CommonRecyclerHolder holder) {

        if (AppService.getInstance().getCurrentUser() == null) {
            return;
        }
        AppService.getInstance().updatePraiseAsync(item.mainid, AppService.getInstance().getCurrentUser().username
                , new JsonCallback<LslResponse<PraiseModel>>() {
                    @Override
                    public void onSuccess(LslResponse<PraiseModel> praiseLslResponse, Call call, Response response) {
                        if (praiseLslResponse.code == LslResponse.RESPONSE_OK){
                            Log.e(TAG,"更新赞的信息成功！");
                            int praiseCount = praiseLslResponse.data.praiseCount;
                            boolean isInsert = praiseLslResponse.data.isInsert;
                            if (!isInsert) {
                                holder.setTextColor(R.id.notice_item_like, getResources().getColor(R.color.gray));
                            } else {
                                holder.setTextColor(R.id.notice_item_like, getResources().getColor(R.color.red));
                            }
                            holder.setText(R.id.notice_item_like, "赞 " + praiseCount);
                        }else{
                            Log.e(TAG,"更新赞的信息失败！");
                            UIUtil.showToast("更新赞的信息失败！");
                        }
                    }
                });
    }


    private int lastMainId;

    /**
     * 加载数据
     */
    private void loadData(final boolean isRefresh) {
        if (isRefresh) {
            start = 0;
            lastMainId = Integer.MAX_VALUE;
        } else {
            start += count;
        }
        Log.e(TAG+"1", start +"");
        if (AppService.getInstance().getCurrentUser() != null) {
            int classId = AppService.getInstance().getCurrentUser().classid;
            String username = AppService.getInstance().getCurrentUser().username;
            AppService.getInstance().getNoticeAsync(classId, username,InfoType.HOMEWORK, start,count, lastMainId,new JsonCallback<LslResponse<List<InfoModel>>>() {
                @Override
                public void onSuccess(LslResponse<List<InfoModel>> listLslResponse, Call call, Response response) {
                    if (listLslResponse.code == LslResponse.RESPONSE_OK) {
                        mRecyclerView.setLoadingMoreEnabled(true);
                        if (isRefresh) {
                            mInfoModels.clear();
                            UIUtil.showToast("刷新成功！");
                            mAdapter.notifyDataSetChanged();

                        } else {
                            UIUtil.showToast("加载成功！");
                            mAdapter.notifyDataSetChanged();
                        }
                        lastMainId = listLslResponse.data.get(0).mainid;
                        mInfoModels.addAll(listLslResponse.data);
                        footerView.setVisibility(View.GONE);
                    } else {
                        Log.e(TAG, "onSuccess: size =>"+mInfoModels.size() );
                        lastMainId = Integer.MAX_VALUE;
                        UIUtil.showToast(listLslResponse.msg);
                        footerView.setVisibility(View.VISIBLE);
                        mRecyclerView.setLoadingMoreEnabled(false);
                    }
                    if (isRefresh && mInfoModels.size() == 0){// 没有数据的话，显示图片
                        mImageView.setVisibility(View.VISIBLE);
                    } else {
                        mImageView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    if (e != null){
                        Log.e(TAG, "onError: "+e.getMessage() );
                        e.printStackTrace();
                        UIUtil.showToast(e.getMessage());
                    }
                    if (e instanceof OkGoException){
                        UIUtil.showToast("抱歉，发生了未知错误！");
                    } else if (e instanceof SocketTimeoutException){
                        UIUtil.showToast("你的手机网络太慢！");
                    } else if (e instanceof ConnectException){
                        UIUtil.showToast("对不起，你的手机没有联网！");
                    }
                }
            });
        }
    }


}

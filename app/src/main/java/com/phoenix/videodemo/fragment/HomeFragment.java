package com.phoenix.videodemo.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phoenix.videodemo.R;
import com.phoenix.videodemo.adapter.ViewPagerAdapter;
import com.phoenix.videodemo.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flashing on 2017/3/20.
 */

@TargetApi(Build.VERSION_CODES.M)
public class HomeFragment extends Fragment implements View.OnClickListener, View.OnScrollChangeListener {
    Context mContext;
    TextView video_tv;
    TextView favorite_tv;
    TextView movie_tv;
    //游标
    ImageView cursor_iv;
    ImageView menu_iv;
    ImageView search_iv;
    //ViewPager
    ViewPager vp;
    ViewPagerAdapter viewPagerAdapter;
    //ViewPager数据源
    private List<View> viewList = new ArrayList<>();
    //当前选中项
    private int currIndex = 0;
    //图片居中位移
    private int offset;
    //游标图片宽度
    private int bmpW;

    private DrawerLayout mDrawerLayout = null;
    private RelativeLayout ll_main;
    private OnScrollListener mScrollListener;
    public static final int PAGE_VIDEO = 0;
    public static final int PAGE_FAVORITE = 1;
    public static final int PAGE_MOVIE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        video_tv = (TextView) view.findViewById(R.id.tv_video);
        favorite_tv = (TextView) view.findViewById(R.id.tv_favorite);
        movie_tv = (TextView) view.findViewById(R.id.tv_movie);
        cursor_iv = (ImageView) view.findViewById(R.id.iv_cursor);
        menu_iv= (ImageView) view.findViewById(R.id.iv_menu);
        search_iv = (ImageView) view.findViewById(R.id.iv_search);
        ll_main= (RelativeLayout) view.findViewById(R.id.ll_main);
        vp = (ViewPager) view.findViewById(R.id.viewpager);

        mDrawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);

        video_tv.setOnClickListener(this);
        favorite_tv.setOnClickListener(this);
        movie_tv.setOnClickListener(this);
        menu_iv.setOnClickListener(this);
        search_iv.setOnClickListener(this);
        mDrawerLayout.setOnScrollChangeListener(this);

        //游标初始化
        initCursor();
        //页卡初始化
        initViewPager();

        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        sdfsd = (ObservableScrollView) view.findViewById(R.id.sdfsd);
//        sdfsd.setScrollListener(new ObservableScrollView.ScrollListener() {
//            @Override
//            public void scrollOritention(int oritention) {
//                if (oritention==0x01){
////                    向上滑动的回调
//                    ll_main.setVisibility(View.VISIBLE);
////                    ll_main_title.setVisibility(View.VISIBLE);
////                    img_main_icon.setVisibility(View.VISIBLE);
//                }else if(oritention==0x10) {
////                    向下滑动的回调
//                    ll_main.setVisibility(View.GONE);
////                    ll_main_title.setVisibility(View.GONE);
////                    img_main_icon.setVisibility(View.GONE);
//                }else {
//                    return;
//                }
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_video:
                vp.setCurrentItem(PAGE_VIDEO);
                break;
            case R.id.tv_favorite:
                vp.setCurrentItem(PAGE_FAVORITE);
                break;
            case R.id.tv_movie:
                vp.setCurrentItem(PAGE_MOVIE);
                break;
            case R.id.iv_menu:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.iv_search:
                break;
        }
    }

    //初始化游标
    private void initCursor(){
        //得到屏幕的宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels - DisplayUtil.dip2px(getActivity(), 120+20);

        //得到游标图片的宽度
        bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.zitixiaxian).getWidth();

        //计算图片居中需要的位移
        offset = (screenW / 3 - bmpW) / 2;
        //设置初始化位置
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor_iv.setImageMatrix(matrix);
        currIndex = PAGE_VIDEO;
    }

    //初始化ViewPager
    private void initViewPager(){
        //切换的四个界面初始化
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout video_layout = (LinearLayout) inflater.inflate(R.layout.fragment_home_video, null);
        LinearLayout favorite_layout = (LinearLayout) inflater.inflate(R.layout.fragment_home_favorite, null);
        LinearLayout movie_layout = (LinearLayout) inflater.inflate(R.layout.fragment_home_movie, null);

        //初始化视频布局
        initVideoLayout(video_layout);
        //初始化红人布局
        initFavoriteLayout(favorite_layout);
        //初始化电影布局
        initMovieLayout(movie_layout);

        //将四个界面的视图添加到ViewPager的数据源中
        viewList.add(video_layout);
        viewList.add(video_layout);
        viewList.add(video_layout);

        viewPagerAdapter = new ViewPagerAdapter(viewList);
        //ViewPager绑定适配器
        vp.setAdapter(viewPagerAdapter);
        //ViewPager初始选中第一视图
        vp.setCurrentItem(currIndex);
        //ViewPager滑动监听器
        vp.addOnPageChangeListener(new DefineOnPageChangeListener());
    }

    //初始化视频布局
    private void initVideoLayout(LinearLayout layout) {
//        TextView fragment_video_tv = (TextView) layout.findViewById(R.id.fragment_video_tv);
    }

    //初始化红人布局
    private void initFavoriteLayout(LinearLayout layout) {
        //同上，略
    }

    //初始化电影布局
    private void initMovieLayout(LinearLayout layout) {
        //同上，略
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int titleHeight = ll_main.getHeight();
        int maxAlpha = 229;
        mScrollListener.onScroll(scrollY);

        if (scrollY > 0) {
            if (scrollY <= titleHeight) {
                double d = scrollY * 1.0 / (titleHeight) * 1.0;
                int a = (int) (maxAlpha * d);
                ll_main.setTop(scrollY - titleHeight);
//                ll_main.getBackground().setAlpha(a);
            } else {
                ll_main.setTop(0);
//                ll_main.getBackground().setAlpha(maxAlpha);
            }
        } else {
            scrollY = Math.abs(scrollY);
            if (scrollY <= titleHeight) {
                double d = scrollY * 1.0 / (titleHeight) * 1.0;
                int a = (int) (maxAlpha * d);
                ll_main.setTop(-1 * scrollY);
//                ll_main.getBackground().setAlpha(a);
            } else {
                ll_main.setTop(-1 * titleHeight);
//                ll_main.getBackground().setAlpha(maxAlpha);
            }
        }
    }

    public void setScrollListener(OnScrollListener mScrollListener) {
        this.mScrollListener = mScrollListener;
    }

    /**
     * 滚动的回调接口
     */
    public interface OnScrollListener {
        /**
         * 回调方法，返回滚动的Y方向距离
         * @param scrollY
         */
        public void onScroll(int scrollY);
    }

    //ViewPager视图切换监听器
    public class DefineOnPageChangeListener implements ViewPager.OnPageChangeListener {
        //游标移动一个单位长度
        int one = offset * 2 + bmpW;
        //两个单位长度
        int two = one * 2;

        @Override
        public void onPageScrollStateChanged(int arg0) {}
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0://视频
                    //如果当前在第二页卡
                    if(currIndex == 1){
                        //初始化切换页卡动画，平移与Y轴无关，所以Y值均为0，在X轴由one位置移动到0位置
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    }
                    //如果当前在第三页卡
                    if(currIndex == 2){
                        //初始化切换页卡动画
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1://红人
                    //如果当前在第一页卡
                    if(currIndex == 0){
                        //初始化切换页卡动画
                        animation = new TranslateAnimation(0, one, 0, 0);
                    }
                    //如果当前在第三页卡
                    if(currIndex == 2){
                        //初始化切换页卡动画
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2://电影
                    //如果当前在第一页卡
                    if(currIndex == 0){
                        //初始化切换页卡动画
                        animation = new TranslateAnimation(0, two, 0, 0);
                    }
                    //如果当前在第二页卡
                    if(currIndex == 1){
                        //初始化切换页卡动画
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            //切换页卡动画
            animation.setFillAfter(true);
            animation.setDuration(300);
            cursor_iv.startAnimation(animation);
        }
    }
}

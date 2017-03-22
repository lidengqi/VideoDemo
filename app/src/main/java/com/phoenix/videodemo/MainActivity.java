package com.phoenix.videodemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.phoenix.videodemo.fragment.CartFragment;
import com.phoenix.videodemo.fragment.FujinFragment;
import com.phoenix.videodemo.fragment.HomeFragment;
import com.phoenix.videodemo.fragment.MainFragment;
import com.phoenix.videodemo.fragment.MineFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity implements View.OnClickListener, HomeFragment.OnScrollListener{

    private LinearLayout ll_tab;
    private ImageView iv_home;
    private ImageView iv_mine;
    private ImageView iv_main;
    private ImageView iv_fujin;
    private ImageView iv_cart;

//    private TabLayout mTabLayout;
    private static int TAB_NUM = 5;
    private static int TAB_HOME = 0;
    private static int TAB_MINE = 1;
    private static int TAB_MAIN = 2;
    private static int TAB_FUJIN = 3;
    private static int TAB_CART = 4;
    private int currentTab = 0;
    private int lastPosition;

    //Fragment事务
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private Fragment lastFragment;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private MainFragment mainFragment;
    private FujinFragment fujinFragment;
    private CartFragment cartFragment;

    private static String TAG_HOME = "tag_home";
    private static String TAG_MINE = "tag_mine";
    private static String TAG_MAIN = "tag_main";
    private static String TAG_FUJIN = "tag_fujin";
    private static String TAG_CART = "tag_cart";

    private ScaleAnimation scaleAnimation;

    private static Boolean isExit = false;

    int[] ims_selecter = {R.drawable.tab_home_selector, R.drawable.tab_mine_selector,
            R.mipmap.main_btn, R.drawable.tab_fujin_selector, R.drawable.tab_cart_selector};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        setContentView(R.layout.activity_main);
        scaleAnimation = new ScaleAnimation(0.7f, 1f, 0.7f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(200);

        initView();
    }

    private void initView() {
        ll_tab = (LinearLayout) findViewById(R.id.ll_tab);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_mine = (ImageView) findViewById(R.id.iv_mine);
        iv_main = (ImageView) findViewById(R.id.iv_main);
        iv_fujin = (ImageView) findViewById(R.id.iv_fujin);
        iv_cart = (ImageView) findViewById(R.id.iv_cart);

        iv_home.setOnClickListener(this);
        iv_mine.setOnClickListener(this);
        iv_main.setOnClickListener(this);
        iv_fujin.setOnClickListener(this);
        iv_cart.setOnClickListener(this);

        setTabSelected(TAB_HOME);
        tabBgChange(TAB_HOME);
        currentTab = TAB_HOME;

//        mTabLayout = (TabLayout) findViewById(R.id.tab_bottom);
//        for (int i = 0; i < TAB_NUM; i++) {
//            View view;
//            if (i == 2) {
//                view = LayoutInflater.from(this).inflate(R.layout.tab_view_main_btn, null);
//            } else {
//                view = LayoutInflater.from(this).inflate(R.layout.tab_view, null);
//            }
//            ImageView img = (ImageView) view.findViewById(R.id.tab_img);
//            img.setImageResource(ims_selecter[i]);
//            mTabLayout.addTab(mTabLayout.newTab().setCustomView(view), i);
//        }
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
//
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                currentTab = tab.getPosition();
//                setTabSelected(currentTab);
//                tab.getCustomView().findViewById(R.id.tab_img).startAnimation(scaleAnimation);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                lastPosition = tab.getPosition();
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
//        setTabSelected(currentTab);
//        mTabLayout.getTabAt(currentTab).select();
    }

    private void detachFragments(FragmentTransaction transaction) {
        if (null != lastFragment) {
            transaction.detach(lastFragment);
        }
    }

    private void setTabSelected(int index) {
        // 开启一个Fragment事务
        transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，防止有多个Fragment显示在界面上的情况
        detachFragments(transaction);
        switch (index) {
            case 0:
                if (null == homeFragment) {
                    homeFragment = new HomeFragment();
                    homeFragment.setScrollListener(this);
                    transaction.replace(R.id.container, homeFragment, TAG_HOME);
                } else {
                    transaction.attach(homeFragment);
                }
                lastFragment = homeFragment;
                break;
            case 1:
                if (null == mineFragment) {
                    mineFragment = new MineFragment();
                    transaction.replace(R.id.container, mineFragment, TAG_MINE);
                } else {
                    transaction.attach(mineFragment);
                }
                lastFragment = mineFragment;
                break;
            case 2:
                if (null == mainFragment) {
                    mainFragment = new MainFragment();
                    transaction.replace(R.id.container, mainFragment, TAG_MAIN);
                } else {
                    transaction.attach(mainFragment);
                }
                lastFragment = mainFragment;
                break;
            case 3:
                if (null == fujinFragment) {
                    fujinFragment = new FujinFragment();
                    transaction.replace(R.id.container, fujinFragment, TAG_FUJIN);
                } else {
                    transaction.attach(fujinFragment);
                }
                lastFragment = fujinFragment;
                break;
            case 4:
                if (null == cartFragment) {
                    cartFragment = new CartFragment();
                    transaction.replace(R.id.container, cartFragment, TAG_CART);
                } else {
                    transaction.attach(cartFragment);
                }
                lastFragment = cartFragment;
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            exitByDoubbleClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitByDoubbleClick() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            MainActivity.this.finish();
        }
    }

    //切换tab背景
    private void tabBgChange(int tab){
        iv_home.setSelected(false);
        iv_mine.setSelected(false);
        iv_main.setSelected(false);
        iv_fujin.setSelected(false);
        iv_cart.setSelected(false);
        switch (tab) {
            case 0:
                //给图标设置背景选择器，在这里通过setSelected改变当前选中或非选中的状态
                iv_home.setSelected(true);
                break;
            case 1:
                iv_mine.setSelected(true);
                break;
            case 2:
                iv_main.setSelected(true);
                break;
            case 3:
                iv_fujin.setSelected(true);
                break;
            case 4:
                iv_cart.setSelected(true);
                break;
        }
    }

    //只要屏幕被触摸就会被触发
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        //如果自定义的接口不为空，就将当前的触摸事件传递到接口当中
//        if(fragmentOnTouchListener != null){
//            fragmentOnTouchListener.onTouch(ev);
//        }
//        //接着执行这句代码保证原来的逻辑不会改变
//        return super.dispatchTouchEvent(ev);
//    }
//
//    public interface FragmentOnTouchListener{
//        public boolean onTouch(MotionEvent ev);
//    }
//    //注册自定义接口
//    public void registerFragmentOnTouchListener(FragmentOnTouchListener fragmentOnTouchListener){
//        this.fragmentOnTouchListener = fragmentOnTouchListener;
//    }
//    //注销自定义接口
//    public void unregisterMyOnTouchListener(FragmentOnTouchListener myOnTouchListener){
//        this.fragmentOnTouchListener = null;
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //记录Fragment索引，防止Activity被系统回收时，Fragment错乱的问题
//        outState.putBoolean("isRecycle", true);
//        outState.putInt("chooseIndex", chooseIndex);
//    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        isRecycle = savedInstanceState.getBoolean("isRecycle");
//        chooseIndex = savedInstanceState.getInt("chooseIndex");
//    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(isRecycle){
//            tabBgChange(chooseIndex);
//        }
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(scaleAnimation);
        switch (v.getId()) {
            case R.id.iv_home:
                setTabSelected(TAB_HOME);
                tabBgChange(TAB_HOME);
                currentTab = TAB_HOME;
                break;
            case R.id.iv_mine:
                setTabSelected(TAB_MINE);
                tabBgChange(TAB_MINE);
                currentTab = TAB_MINE;
                break;
            case R.id.iv_main:
                setTabSelected(TAB_MAIN);
                tabBgChange(TAB_MAIN);
                currentTab = TAB_MAIN;
                break;
            case R.id.iv_fujin:
                setTabSelected(TAB_FUJIN);
                tabBgChange(TAB_FUJIN);
                currentTab = TAB_FUJIN;
                break;
            case R.id.iv_cart:
                setTabSelected(TAB_CART);
                tabBgChange(TAB_CART);
                currentTab = TAB_CART;
                break;
        }
    }

    @Override
    public void onScroll(int scrollY) {
        int tabHeight = ll_tab.getHeight();
        int maxAlpha = 229;

        if (scrollY > 0) {
            if (scrollY <= tabHeight) {
                double d = scrollY * 1.0 / (tabHeight) * 1.0;
                int a = (int) (maxAlpha * d);
                ll_tab.setBottom(scrollY - tabHeight);
//                ll_main.getBackground().setAlpha(a);
            } else {
                ll_tab.setBottom(0);
//                ll_main.getBackground().setAlpha(maxAlpha);
            }
        } else {
            scrollY = Math.abs(scrollY);
            if (scrollY <= tabHeight) {
                double d = scrollY * 1.0 / (tabHeight) * 1.0;
                int a = (int) (maxAlpha * d);
                ll_tab.setBottom(-1 * scrollY);
//                ll_main.getBackground().setAlpha(a);
            } else {
                ll_tab.setBottom(-1 * tabHeight);
//                ll_main.getBackground().setAlpha(maxAlpha);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

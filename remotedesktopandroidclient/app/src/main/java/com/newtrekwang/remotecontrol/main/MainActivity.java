package com.newtrekwang.remotecontrol.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.newtrekwang.remotecontrol.R;

import static com.newtrekwang.remotecontrol.service.ConnectService.CONNECT_BROADCAST_ACTION;
import static com.newtrekwang.remotecontrol.service.ConnectService.CONNECT_PROGRESS_BROADCAST_ACTION;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity>>>>";

//    toolbar
    private Toolbar toolbar;
//    viewpager控件
    private ViewPager fragmentViewPager;
//    底部导航菜单控件
    private BottomNavigationView bottomNavigationView;
//    主界面的FragmentPagerAdapter
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;
//    三个碎片的标题
    private String[] titles={"设备","发现","我的"};
//    广播
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;
/**
 * 进行初始化操作
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//      抽屉菜单设置
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//      碎片适配设置
        fragmentViewPager= (ViewPager) findViewById(R.id.main_viewPager);
        mainFragmentPagerAdapter=new MainFragmentPagerAdapter(getSupportFragmentManager());
        fragmentViewPager.setAdapter(mainFragmentPagerAdapter);

        //底部菜单栏设置
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);//菜单选择监听接口

//      fragmentViewPager的左右滑动监听
        fragmentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                选中了指定位置，就设置toolbar的标题和同步底部菜单的选中状态
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                toolbar.setTitle(titles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//          底部菜单设置
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);//底部菜单选中监听

//        广播设置
        initBroadCast();
    }

    /**
     * 返回键监听
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);//如果抽屉开着，就关闭抽屉
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 设置Options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Options menu item点击监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 这里抽屉菜单和底部菜单的item点击监听都在这儿
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_touchpad) {
            showToast("暂未开发");
        } else if (id == R.id.nav_filetrans) {
            showToast("暂未开发");
        } else if (id == R.id.nav_filedownload) {
            showToast("暂未开发");
        } else if (id == R.id.nav_mediaplay) {
            showToast("暂未开发");
        } else if (id == R.id.nav_imageViewer) {
            showToast("暂未开发");
        } else if (id == R.id.nav_presentation) {
            showToast("暂未开发");
        } else if (id == R.id.nav_poweroff) {
            //showToast("暂未开发");
          //  new DeletePCByMac().show(getSupportFragmentManager(),"deletePcByMac");
        } else if (id == R.id.bottom_device) {//设备
            toolbar.setTitle("设备");
           fragmentViewPager.setCurrentItem(0);
        } else if (id == R.id.bottom_find) {//发现
            toolbar.setTitle("发现");
            fragmentViewPager.setCurrentItem(1);
        } else if (id == R.id.bottom_my) {//我的
            toolbar.setTitle("我的");
            fragmentViewPager.setCurrentItem(2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 显示Toast
     */
    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 释放引用
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(broadcastReceiver);
        toolbar = null;
        fragmentViewPager=null;
        bottomNavigationView=null;
        mainFragmentPagerAdapter=null;
    }

/**
 * 初始化广播接收器
 */
    private void initBroadCast(){
        mLocalBroadcastManager=LocalBroadcastManager.getInstance(this);
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(CONNECT_BROADCAST_ACTION)){
                    String str=intent.getStringExtra("status");
                    Log.i(TAG, "onReceive: "+str);
                    showToast(str);
                }else if (intent.getAction().equals(CONNECT_PROGRESS_BROADCAST_ACTION)){
                    int count=intent.getIntExtra("progress",0);
                    Log.i(TAG, "onReceive: "+count);
                }
            }
        };
        IntentFilter  intentFilter=new IntentFilter();
        intentFilter.addAction(CONNECT_BROADCAST_ACTION);
        intentFilter.addAction(CONNECT_PROGRESS_BROADCAST_ACTION);
        mLocalBroadcastManager.registerReceiver(broadcastReceiver,intentFilter);
    }

}

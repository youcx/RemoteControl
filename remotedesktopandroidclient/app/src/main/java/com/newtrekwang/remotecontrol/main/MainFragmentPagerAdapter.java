package com.newtrekwang.remotecontrol.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.newtrekwang.remotecontrol.devices.DevicesFragment;
import com.newtrekwang.remotecontrol.find.TouchPadFragment;
import com.newtrekwang.remotecontrol.my.MyFragment;

import java.util.ArrayList;
import java.util.List;
/**
 * 主页界面的碎片适配器
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        list=new ArrayList<>();
        list.add(DevicesFragment.newInstance());//设备碎片
        list.add(TouchPadFragment.newInstance());//
        list.add(MyFragment.newInstance());//我的 碎片
    }
/**
 * 指定item的碎片对象
 */
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }
/**
 * 碎片数量
 */
    @Override
    public int getCount() {
        return list.size();
    }
}

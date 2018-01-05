package com.newtrekwang.remotecontrol.devices;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newtrekwang.remotecontrol.R;
import com.newtrekwang.remotecontrol.bean.Device;

import java.util.ArrayList;
/**
 * 利用BaseQuickAdapter封装的列表适配器
 */
public class DevicesAdapter_1 extends BaseQuickAdapter<Device,BaseViewHolder> {
/**
 * 构造方法
 */
    public DevicesAdapter_1() {
        super(R.layout.devices_item, new ArrayList<Device>());
    }
/**
 * 填充item数据
 */
    @Override
    protected void convert(BaseViewHolder helper, Device item) {
        helper.setText(R.id.devices_item_tv_num,item.getIp());
        helper.setText(R.id.devices_item_tv_name,item.getName());
        helper.setText(R.id.devices_item_tv_mac,""+item.getPort());
    }
}

package com.newtrekwang.remotecontrol.devices;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newtrekwang.remotecontrol.R;
import com.newtrekwang.remotecontrol.bean.PCdevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 利用RecyclerView.Adapter完全自已封装的Adapter
 */
public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DeviceItemViewHolder> {
    //    item点击监听接口
    private DeviceItemClickListener deviceItemClickListener;
    private DeviceItemLongClickListener deviceItemLongClickListener;
    //    适配数据
    private List<PCdevice> datas;

    /**
     * 数据的setter,set后刷新
     */
    public void setDatas(List<PCdevice> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * 获取适配的数据
     */
    public List<PCdevice> getDatas() {
        return this.datas;
    }

    /**
     * 无参构造
     */
    DevicesAdapter() {
        datas = new ArrayList<>();
    }

    /**
     * 叠加数据，并刷新
     */
    public void addDatas(List<PCdevice> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 构造每个item的ViewHolder
     */
    @Override
    public DeviceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.devices_item, parent, false);
        DeviceItemViewHolder holder = new DeviceItemViewHolder(root);
        return holder;
    }

    /**
     * 填充每个item的数据
     */
    @Override
    public void onBindViewHolder(final DeviceItemViewHolder holder, final int position) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deviceItemClickListener != null) {
                    deviceItemClickListener.onClick(holder.itemView, datas.get(holder.getAdapterPosition()));
                    }
                }
            });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                if(deviceItemLongClickListener!=null){
                    deviceItemLongClickListener.longClick(holder.itemView,datas.get(holder.getAdapterPosition()));
                }
                return true;
            }
        });
       PCdevice device = datas.get(position);
        if (!TextUtils.isEmpty(device.getUid())) {
        holder.devicesItemTvNum.setText(device.getUid());
        }
      if(!(TextUtils.isEmpty(device.getPcname()))){
          holder.devicesItemTvName.setText(device.getPcname());
      }
        if(!(TextUtils.isEmpty(device.getMac()))){
        holder.devicesItemTvMac.setText(device.getMac());
        }
        if(device.getFlag()==1){
          holder.devicesItemTvStatus.setText("在线");
        }else {
            holder.devicesItemTvStatus.setText("离线");
        }

    }

    /**
     * 适配数据大小
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 获取DeviceItemClickListenner
     */
    public DeviceItemClickListener getDeviceItemClickListenner() {
        return deviceItemClickListener;
    }

    /**
     * 设置DeviceItemClickListenner点击
     */
    public void setDeviceItemClickListenner(DeviceItemClickListener deviceItemClickListener) {
        this.deviceItemClickListener = deviceItemClickListener;
    }

    /**
     * 设置DeviceItem长按
     * @param deviceItemLongClickListener
     */

    public void setDeviceItemLongClickListener(DeviceItemLongClickListener deviceItemLongClickListener){
        this.deviceItemLongClickListener = deviceItemLongClickListener;
    }

    /**
     * 自定义ViewHolder
     */
    public class DeviceItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.devices_item_tv_name)
        TextView devicesItemTvName;
        @BindView(R.id.devices_item_tv_num)
        TextView devicesItemTvNum;
        @BindView(R.id.devices_item_tv_mac)
        TextView devicesItemTvMac;
        @BindView(R.id.devices_item_tv_status)
        TextView devicesItemTvStatus;
        public DeviceItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 自定义item点击监听接口
     */
    public interface DeviceItemClickListener {
        void onClick(View view, PCdevice device);
    }

    /**
     * 自定义item长按监听接口
     */
    public interface DeviceItemLongClickListener{
        boolean longClick(View view,PCdevice device);
    }

}

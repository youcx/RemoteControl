package com.newtrekwang.remotecontrol.devices;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.newtrekwang.commonactivity.SharePreferenceUtil;
import com.newtrekwang.commonlib.http.ApiCallBack;
import com.newtrekwang.commonlib.http.SubscriberCallBack;
import com.newtrekwang.remotecontrol.R;
import com.newtrekwang.remotecontrol.bean.PCdevice;
import com.newtrekwang.remotecontrol.bean.Result;
import com.newtrekwang.remotecontrol.connect.ConnectActivity;
import com.newtrekwang.remotecontrol.model.ModelManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.newtrekwang.remotecontrol.util.Constants.PHONE;
import static com.newtrekwang.remotecontrol.util.Constants.SESSIONID;

/**
 * auther    : WJX
 * classname : DevicesFragment
 * desc      : 主页设备碎片界面
 * createTime: 2017/7/30 10:21
 */
public class DevicesFragment extends Fragment {
    private static final String TAG = "DevicesFragment>>>>>>>>";
    //    RecyclerView
    @BindView(R.id.devices_recyclerView)
    RecyclerView devicesRecyclerView;
    //    下拉刷新控件
    @BindView(R.id.devices_swipe_RefreshLayout)
    SwipeRefreshLayout devicesSwipeRefreshLayout;
//  浮动按钮
    @BindView(R.id.device_fab_add)
    FloatingActionButton deviceFabAdd;

    //    控件解绑助手对象
    Unbinder unbinder;
    //    列表适配器
    private DevicesAdapter devicesAdapter;
    //    数据层
    private ModelManager modelManager;

    //   午餐构造
    public DevicesFragment() {
        // Required empty public constructor
    }

    //    是否第一次请求过
    private boolean isFirstRefreshed = false;
    //    管理订阅
    private CompositeDisposable compositeDisposable;

    /**
     * 初始化
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        modelManager = new ModelManager();
        devicesAdapter = new DevicesAdapter();
        devicesAdapter.setDeviceItemClickListenner(new DevicesAdapter.DeviceItemClickListenner() {
            @Override
            public void onClick(View view, PCdevice device) {
                goConectActivity(device);
            }
        });
    }

    /**
     * 工厂方法
     */
    public static DevicesFragment newInstance() {
        DevicesFragment fragment = new DevicesFragment();
        return fragment;
    }

    /**
     * 设置View树
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devices, container, false);
        unbinder = ButterKnife.bind(this, view);//绑定控件
//        RecyclerView初始设置
        devicesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        devicesRecyclerView.setAdapter(devicesAdapter);
//        首先来一个刷新任务
        if (!isFirstRefreshed) {
            devicesSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    isFirstRefreshed = true;
                    devicesSwipeRefreshLayout.setRefreshing(true);
                    upDate();
                }
            });
        }
//        设置下拉刷新监听
        devicesSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                upDate();
            }
        });

        deviceFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new AddDeviceDialog().show(getActivity().getSupportFragmentManager(),"adddevice");
            }
        });
        return view;
    }

    /**
     * View销毁前，取消异步任务
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        devicesSwipeRefreshLayout.setRefreshing(false);
        unbinder.unbind();
        Log.i(TAG, "onDestroyView: >>>>>>>>>>>>");
    }


    /**
     * 刷新列表任务
     */
    private void upDate() {
        String phone = (String) SharePreferenceUtil.getParam(getActivity(), PHONE, "");
        if (TextUtils.isEmpty(phone)) {
            showToast("用户手机号数据为空！");
            return;
        }
        String session = (String) SharePreferenceUtil.getParam(getActivity(), SESSIONID, "");
        if (TextUtils.isEmpty(session)) {
            showToast("用户session数据为空！");
            return;
        }
        modelManager.getAllDevices(phone, session)
                .subscribe(new SubscriberCallBack<Result<List<PCdevice>>>(new ApiCallBack<Result<List<PCdevice>>>() {
                    @Override
                    public void onStart(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onFailed(int code, String msg, Throwable e) {
                        devicesSwipeRefreshLayout.setRefreshing(false);
                        showToast(msg + code);
                        Log.e(TAG, "onFailed: >>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<List<PCdevice>> listResult) {
                        devicesSwipeRefreshLayout.setRefreshing(false);
                        if (listResult != null) {
                            showToast(listResult.getMsg());
                            if (listResult.getStatus() == 1) {
                                devicesAdapter.setDatas(listResult.getResult());
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }
                }));

    }

    /**
     * 销毁前清除引用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        modelManager = null;
        devicesAdapter = null;
        Log.i(TAG, "onDestroy: >>>>>>>>>>");
    }

    /**
     * 跳转到连接界面
     */
    private void goConectActivity(PCdevice device) {
        Intent intent = new Intent(getActivity(), ConnectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("device", device);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }


}

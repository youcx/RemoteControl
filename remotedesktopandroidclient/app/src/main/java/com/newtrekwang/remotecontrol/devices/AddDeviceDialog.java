package com.newtrekwang.remotecontrol.devices;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.newtrekwang.commonactivity.SharePreferenceUtil;
import com.newtrekwang.commonlib.http.ApiCallBack;
import com.newtrekwang.commonlib.http.SubscriberCallBack;
import com.newtrekwang.remotecontrol.R;
import com.newtrekwang.remotecontrol.bean.Result;
import com.newtrekwang.remotecontrol.model.ModelManager;
import com.newtrekwang.remotecontrol.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * auther    : WJX
 * classname : AddDeviceDialog
 * desc      : 添加设备的编辑对话框碎片
 * createTime: 2017/7/30 10:14
 */
public class AddDeviceDialog extends BottomSheetDialogFragment {
    //   精度框
    @BindView(R.id.adddevice_progress)
    LinearLayout adddeviceProgress;
    //    设备名编辑框
    @BindView(R.id.adddevice_et_name)
    EditText adddeviceEtName;
    //    设备唯一码编辑框
    @BindView(R.id.adddevice_et_ip)
    EditText adddeviceEtIp;
    //    添加按钮
    @BindView(R.id.adddevice_btn_add)
    Button adddeviceBtnAdd;
    //    表单的容器，便于控制一堆控件的显示
    @BindView(R.id.devices_adddevice__form)
    LinearLayout adddevice__form;

    @BindView(R.id.adddevice_form)
    ScrollView adddeviceForm;

    Unbinder unbinder;

    private BottomSheetBehavior<View> mBehavior;

    private CompositeDisposable compositeDisposable;
    private ModelManager modelManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        modelManager = new ModelManager();
    }

    /**
     * 创建dialog  初始化里面的控件
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View rootView = View.inflate(getContext(), R.layout.device_dialog_adddevice, null);
        unbinder = ButterKnife.bind(this, rootView);
        dialog.setContentView(rootView);
        mBehavior = BottomSheetBehavior.from((View) rootView.getParent());
        adddeviceBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempAdd();//开始添加设备任务
            }
        });
        return dialog;
    }

    /**
     * 检查信息，尝试开始任务
     */
    private void attempAdd() {
        // 清除错误提示信息
        adddeviceEtIp.setError(null);
        adddeviceEtName.setError(null);

//        保存用户输入的变量
        String num = adddeviceEtIp.getText().toString();
        String name = adddeviceEtName.getText().toString();

        boolean cancel = false;
        View focusView = null;
//      检查PC名
        if (TextUtils.isEmpty(name)) {
            adddeviceEtName.setError("PC名不能为空！");
            cancel = true;
            focusView = adddeviceEtName;
        }
//        检查iP
        if (TextUtils.isEmpty(num)) {
            adddeviceEtIp.setError("唯一码不能为空！");
            cancel = true;
            focusView = adddeviceEtIp;
        }

        if (cancel) {
            //            显示错误提示
            focusView.requestFocus();
        } else {
//            登录任务启动
            showProgress(true);//显示进度条
            startAddTask(name, num);
        }
    }

    /**
     * 设置进度显示
     *
     * @param show 是否显示
     */
    private void showProgress(boolean show) {
        adddeviceProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        adddevice__form.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    /**
     * 开始填加任务
     *
     * @param name 设备名
     * @param num   电脑唯一码
     */
    private void startAddTask(String name, String num) {
        String sessionid = (String) SharePreferenceUtil.getParam(getActivity(), Constants.SESSIONID, "");
        String phone = (String) SharePreferenceUtil.getParam(getActivity(), Constants.PHONE, "");

        modelManager.addPcByNum(phone, sessionid, num,name)
                .subscribe(new SubscriberCallBack<Result>(new ApiCallBack<Result>() {
                    @Override
                    public void onStart(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onFailed(int code, String msg, Throwable e) {
                        showProgress(false);
                    }

                    @Override
                    public void onNext(Result result) {
                        showProgress(false);
                        if (result != null) {
                            if (result.getStatus() == 1) {
                                showToast(result.getMsg());
                                dismiss();
                            } else {
                                showToast(result.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }
                }));
    }

    /**
     * 碎片销毁前的操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 这里要设置一下，不然碎片无法填充屏幕宽度
     */
    @Override
    public void onStart() {
        super.onStart();
        //默认全屏展开
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}

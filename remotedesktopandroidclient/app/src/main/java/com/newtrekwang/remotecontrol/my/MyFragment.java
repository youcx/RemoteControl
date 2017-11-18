package com.newtrekwang.remotecontrol.my;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.newtrekwang.commonactivity.SharePreferenceUtil;
import com.newtrekwang.commonlib.http.ApiCallBack;
import com.newtrekwang.commonlib.http.SubscriberCallBack;
import com.newtrekwang.remotecontrol.R;
import com.newtrekwang.remotecontrol.bean.Result;
import com.newtrekwang.remotecontrol.login.LoginActivity;
import com.newtrekwang.remotecontrol.login.RegistDialog;
import com.newtrekwang.remotecontrol.model.ModelManager;
import com.newtrekwang.remotecontrol.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * auther    : WJX
 * classname : MyFragment
 * desc      : 我的 碎片界面
 * createTime: 2017/7/24 16:01
 */
public class MyFragment extends Fragment implements View.OnClickListener {

    //    绑定助手
    Unbinder unbinder;

    @BindView(R.id.personcenterSrollView)
    PullToZoomScrollViewEx  personcenterSrollView;

    //    用户名标签控件
    private TextView personUserName;

    private CompositeDisposable compositeDisposable;

    private ModelManager modelManager;


    public MyFragment() {

    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        modelManager = new ModelManager();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        loadViewForCode();
        return view;
    }

    private void loadViewForCode() {

        //        设置headerView
      View  headView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_head_view, null, false);
        personUserName = (TextView) headView.findViewById(R.id.tv_user_name);
        String userName = (String) SharePreferenceUtil.getParam(getActivity(), Constants.USERNAME,"");//获取记录的登录的用户名
        personUserName.setText(userName);//显示用户名
        headView.findViewById(R.id.tv_register).setOnClickListener(this);//注册按钮点击监听
        headView.findViewById(R.id.tv_logout).setOnClickListener(this);//退出按钮点击监听
//         设置zoomView，也就是图片背景控件
       View zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_zoom_view, null, false);
//        设置contentView
      View  contentView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_content_2, null, false);

        contentView.findViewById(R.id.person_userName).setOnClickListener(this);//修改用户名点击监听
        contentView.findViewById(R.id.person_phone).setOnClickListener(this);//修改手机号点击监听
        contentView.findViewById(R.id.person_setPwd).setOnClickListener(this);//修改密码点击监听
        contentView.findViewById(R.id.person_set).setOnClickListener(this);//设置点击监听
        contentView.findViewById(R.id.person_about).setOnClickListener(this);//关于点击监听

        personcenterSrollView.setHeaderView(headView);
        personcenterSrollView.setZoomView(zoomView);
        personcenterSrollView.setScrollContentView(contentView);
        personcenterSrollView.setZoomEnabled(true);
        personcenterSrollView.setParallax(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        modelManager = null;
        unbinder.unbind();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_register://注册，弹出注册对话框
                RegistDialog registDialog = new RegistDialog();
                registDialog.show(getChildFragmentManager(), "RegisterDialog");
                break;
            case R.id.tv_logout://退出登录
                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
                dialogBuilder
                        .withTitle("提示")                                  //.withTitle(null)  no title
                        .withTitleColor("#FFFFFF")                                  //def
                        .withDividerColor("#11000000")                              //def
                        .withMessage("确定退出登录？")                     //.withMessage(null)  no Msg
                        .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                        .withDialogColor("#009688")                               //def  | withDialogColor(int resid)
                        .withDuration(500)//def
                        .withEffect(Effectstype.Shake)
                        .withButton1Text("确定")                                      //def gone
                        .withButton2Text("取消")                                  //def gone
                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                            }
                        }).show();
                break;
            case R.id.person_userName://修改用户名
                final NiftyDialogBuilder dialogBuilder_1 = NiftyDialogBuilder.getInstance(getActivity());
                final EditText edittext = new EditText(getActivity());
                edittext.setTextColor(Color.WHITE);
                dialogBuilder_1
                        .withTitle("编辑用户名")                                  //.withTitle(null)  no title
                        .withTitleColor("#FFFFFF")                                  //def
                        .withDividerColor("#11000000")                              //def
                        .withMessage(null)                     //.withMessage(null)  no Msg
                        .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                        .withDialogColor("#009688")                               //def  | withDialogColor(int resid)
                        .withDuration(500)//def
                        .withEffect(Effectstype.Fall)
                        .withButton1Text("确定")                                      //def gone
                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                        .setCustomView(edittext, getActivity())
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String username = edittext.getText().toString();
                                if (!TextUtils.isEmpty(username)) {
                                    String phonenum=(String)SharePreferenceUtil.getParam(getActivity(),Constants.PHONE,"");//获取电话号码
                                    String sessionid=(String)SharePreferenceUtil.getParam(getActivity(),Constants.SESSIONID,"");
                                    updateuserName(phonenum,sessionid,username);
                                }
                                dialogBuilder_1.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.person_phone://修改手机号
                final NiftyDialogBuilder dialogBuilder_2 = NiftyDialogBuilder.getInstance(getActivity());
                final EditText edittext_2 = new EditText(getActivity());
                edittext_2.setTextColor(Color.WHITE);
                dialogBuilder_2
                        .withTitle("编辑手机号")                                  //.withTitle(null)  no title
                        .withTitleColor("#FFFFFF")                                  //def
                        .withDividerColor("#11000000")                              //def
                        .withMessage(null)                     //.withMessage(null)  no Msg
                        .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                        .withDialogColor("#009688")                               //def  | withDialogColor(int resid)
                        .withDuration(500)//def
                        .withEffect(Effectstype.Fall)
                        .withButton1Text("确定")                                      //def gone
                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                        .setCustomView(edittext_2, getActivity())
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String str = edittext_2.getText().toString();
                                if (!TextUtils.isEmpty(str)) {
                                    updatePhone(str);
                                }
                                dialogBuilder_2.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.person_setPwd://修改密码
                final NiftyDialogBuilder dialogBuilder_3 = NiftyDialogBuilder.getInstance(getActivity());
                final EditText edittext_3 = new EditText(getActivity());
                edittext_3.setTextColor(Color.WHITE);
                dialogBuilder_3
                        .withTitle("编辑密码")                                  //.withTitle(null)  no title
                        .withTitleColor("#FFFFFF")                                  //def
                        .withDividerColor("#11000000")                              //def
                        .withMessage(null)                     //.withMessage(null)  no Msg
                        .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                        .withDialogColor("#009688")                               //def  | withDialogColor(int resid)
                        .withDuration(500)//def
                        .withEffect(Effectstype.Fall)
                        .withButton1Text("确定")                                      //def gone
                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                        .setCustomView(edittext_3, getActivity())
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String str = edittext_3.getText().toString();
                                if (!TextUtils.isEmpty(str)) {
                                    updatePwd(str);
                                }
                                dialogBuilder_3.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.person_set:
                showToast("set");
                break;
            case R.id.person_about:
                showToast("about");
                break;
            default:
                break;
        }
    }



    /**
     * 更新密码
     * @param str 新密码
     */
    private void updatePwd(String str) {

    }

    /**
     * 更新手机号
     * @param str 新手机号
     */
    private void updatePhone(String str) {

    }

    /**
     * 跟新用户名
     * @param username 新用户名
     */
    private void updateuserName(String phonenum, String sessionid, String username) {
        modelManager=new ModelManager();
        modelManager.Updateusername(phonenum,sessionid,username)
                .subscribe(new SubscriberCallBack<Result>(new ApiCallBack<Result>() {
                    @Override
                    public void onStart(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onFailed(int code, String msg, Throwable e) {
                        showToast(msg+" "+code);
                        Log.e("Failed",e.getMessage());
                    }

                    @Override
                    public void onNext(Result result) {
                        if (result!=null){
                            if (result.getStatus()==1){
                                showToast(result.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        Log.e("TAG","success");
                        Intent intent=new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                    }
                }));
    }

    /**
     * 显示Toast消息
     */
    private void showToast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

}

package com.newtrekwang.commonactivity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseRegistDialog extends BottomSheetDialogFragment {

    private static final String TAG = "BaseRegistDialog>>>>>>>>>>>>`";
    EditText registUsername;
    AutoCompleteTextView registPhone;
    EditText registPassword;
    Button registeBtn;
    LinearLayout phoneRegistForm;
    ScrollView loginForm;
    View progressBar;

    private BottomSheetBehavior<View> mBehavior;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.fragment_regist, null);
        dialog.setContentView(view);
         mBehavior = BottomSheetBehavior.from((View) view.getParent());
        registUsername= (EditText) view.findViewById(R.id.regist_username);
        registPhone= (AutoCompleteTextView) view.findViewById(R.id.regist_phone);
        registPassword= (EditText) view.findViewById(R.id.regist_password);
        registeBtn= (Button) view.findViewById(R.id.registe_btn);
        phoneRegistForm= (LinearLayout) view.findViewById(R.id.phone_regist_form);
        loginForm= (ScrollView) view.findViewById(R.id.regist_form);
        progressBar=view.findViewById(R.id.regist_progress);
        init();
        return dialog;
    }
    private void init(){
        //软键盘回车监听
        registPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.regist|| id == EditorInfo.IME_NULL) {
                    attemptRegist();
                    return true;
                }
                return false;
            }
        });

        registeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegist();
            }
        });
    }

    private void attemptRegist() {
        // 清除错误提示信息
        registUsername.setError(null);
        registPhone.setError(null);
        registPassword.setError(null);

        // 暂时用三个变量保存用户输入的信息
        String username=registUsername.getText().toString();
        String phone =registPhone.getText().toString();
        String password = registPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 检查密码
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            registPassword.setError(getString(R.string.error_invalid_password));
            focusView =registPassword;
            cancel = true;
        }


        // 检查手机号有效性
        if (TextUtils.isEmpty(phone)) {
            registPhone.setError(getString(R.string.error_field_required));
            focusView = registPhone;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            registPhone.setError(getString(R.string.error_invalid_phone));
            focusView =registPhone;
            cancel = true;
        }

        //检查用户名
        if (TextUtils.isEmpty(username)){
            registUsername.setError(getString(R.string.error_field_required));
            focusView=registUsername;
            cancel=true;
        }

        if (cancel) {
//            显示错误提示
            focusView.requestFocus();
        } else {
//            登录任务启动
            showProgress(true);//显示进度条
            startRegistTask(phone,password,username);
        }

    }

    protected abstract void startRegistTask(String phone,String password,String username) ;

    /**
     * Shows the progress UI and hides the login form.显示登录时的进度条，隐藏登录表单
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
   protected void showProgress(final boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        phoneRegistForm.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    protected void showToast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    private boolean isNameValid(String username) {
        return username.length()>1;
    }

    /**
     * 匹配手机号，以检查手机号有效性
     */
    private boolean isPhoneValid(String phone) {
        return TelNumMatch.isValidPhoneNumber(phone);
    }


    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        //默认全屏展开
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

}

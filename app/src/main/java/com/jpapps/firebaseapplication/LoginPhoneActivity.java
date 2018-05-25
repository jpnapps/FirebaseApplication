package com.jpapps.firebaseapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;

import com.jpapps.firebaseapplication.network.PrefManager;
import com.jpndev.utilitylibrary.base.BaseAppCompactActivity;

import org.json.JSONObject;



public class LoginPhoneActivity extends BaseAppCompactActivity {

    public Bundle bundle;
    public String phoneNo;//phoneFromTextview;

   // public CustomEditText phone_tv;
    //public SharedPreferences sharedpreferences;
    //public RelativeLayout google_rlay;
    public String main_label="error",label="error";
    JSONObject userObject;

    private String jsonStrng;
    private Context context;

    private PrefManager prefM;
    //private CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_login_phone);

        setContentView(R.layout.activity_sign_in_code_verify);
        context = LoginPhoneActivity.this;
        prefM = PrefManager.getInstance(context);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        bundle=getIntent().getExtras();
        phoneNo=bundle.getString("phone");
       // phone_tv=(CustomEditText) findViewById(R.id.phone_no);
        //hideKeyboard();

        /* */


        //countryCodePicker = (CountryCodePicker) findViewById(R.id.login_phone_ccp);
        ////phoneFromTextview=countryCodePicker.getSelectedCountryCode()+phone_tv.getText();
        //google_rlay=(RelativeLayout) findViewById(R.id.google_rlay);
        loginUsingPhone();


    }

//    @Override
//    protected View getSnackbarAnchorView() {
//        return findViewById(com.quickblox.sample.groupchatwebrtc.R.id.scrollView);
//    }

    public void loginUsingPhone() {
        try {
            setLoading();
            showProgress();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 300);
            try {

                callLogin();


            } catch (Exception e) {
                hideProgress();
                e.printStackTrace();
                // LogUtils.LOGD("sign","lA validRegister exception "+e.getMessage());
            }
        }
        catch(Exception ex)
        {   hideProgress();
            ex.printStackTrace();}
    }

    public void callLogin()
    {
        try {
            startActivity(new Intent(context, RetailerActivity.class));
            finishAffinity();


        }
        catch(Exception ex)
        {
            /*ex.printStackTrace();
            hideProgress();
            setStatus("Login failed : \n,"+modelPhoneLogin.getMessage(), R.color.endcall);
            showStatus();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // hideProgress();
                    hideStatus();
                    finish();
                    Intent intent=new Intent(LoginPhoneActivity.this,SignActivity.class);
                    startActivity(intent);
                }
            }, 900);*/
        }
    }






}

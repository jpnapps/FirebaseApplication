package com.jpapps.firebaseapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

public class AcitivityLoginNumberActivity extends Activity implements View.OnClickListener {

    private ProgressBar loginProgress;
    private ScrollView loginForm;
    private LinearLayout emailLoginForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login_number);

        loginProgress = (ProgressBar) findViewById(R.id.login_progress);
        loginForm = (ScrollView) findViewById(R.id.login_form);
        emailLoginForm = (LinearLayout) findViewById(R.id.email_login_form);
        findViewById(R.id.signin_btn).setOnClickListener(this);
    }

    private EditText getPhonenumberEdt(){
        return (EditText) findViewById(R.id.phonenumber_edt);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin_btn:
                String phoneNumber=getPhonenumberEdt().getText()+"";
              /*  if(phoneNumber!=null) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            this,               // Activity (for callback binding)
                            mCallbacks);
                }*/
                break;
        }
    }
}

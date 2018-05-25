package com.jpapps.firebaseapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.jpapps.firebaseapplication.model.ItemData;
import com.jpapps.firebaseapplication.network.NetworkService;
import com.jpapps.firebaseapplication.network.PrefManager;
import com.jpndev.utilitylibrary.CustomEditText;
import com.jpndev.utilitylibrary.CustomFontTextView;
import com.jpndev.utilitylibrary.DeviceFitImageView;
import com.jpndev.utilitylibrary.base.BaseAppCompactActivity;

import java.util.ArrayList;



public class LoginActivity extends BaseAppCompactActivity {





   // CallbackManager callbackManager;
   // public static CallbackManager callbackmanager2;
  //  public PostRegisterModel postRegisterModel;
    public String FEmail,FName;
    public String label="error",main_label="error";
    private Spinner spinner;

    private PrefManager prefM;
    private String companyId;

    //CountryCodePicker countryCodePicker;

    public static String alreadyRegisteresPhone;

    RelativeLayout loginRlay;
    public static ItemData countryData;

    private CustomEditText phoneCedit;
    //private AccessTokenTracker accessTokenTracker;
   // private LoginManager loginManager;
    private String jsonStrng;
    private String qbID;
    private Context context;
    //private CustomFontTextView enter_phone_ctxv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pacifyer);
        try {

            boolean islogged=PrefManager.getInstance(this).getSharedBoolean("islogged",false);
            if(islogged)
            {
                startActivity(new Intent(context, RetailerActivity.class));
                finishAffinity();
            }

            else {
                setLoading();
                hideProgress();

                onSetActionBar();
                prefM = prefM = PrefManager.getInstance(LoginActivity.this);
                context = LoginActivity.this;
                //hideKeyboard();

                //edt_email=(EditText) findViewById(R.id.lgn_email);
                //edt_password=(EditText) findViewById(R.id.lgn_password);
                spinner = (Spinner) findViewById(R.id.spinner);
                phoneCedit = (CustomEditText) findViewById(R.id.phone_cedit);





           /* enter_phone_ctxv = (CustomFontTextView) findViewById(R.id.enter_phone_ctxv);
            Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Raleway-Bold.ttf");
            enter_phone_ctxv.setTypeface(type);*/

                if (isValid(alreadyRegisteresPhone)) {
                    phoneCedit.setText(alreadyRegisteresPhone.substring(2));
                }
                ArrayList<ItemData> list = new ArrayList<>();
                list.add(new ItemData("+1", "US", R.drawable.usa2));
                list.add(new ItemData("+1", "Canada", R.drawable.canada2));
                list.add(new ItemData("+91", "India", R.drawable.india2));
                list.add(new ItemData("+971", "UAE", R.drawable.uae2));
            /*list.add(new ItemData("+971","Kuwait", R.drawable.kuwait2));*/

                CountrySpinnerAdapter adapter = new CountrySpinnerAdapter(this,
                        R.layout.spinner_item, R.id.txt, list);
                spinner.setAdapter(adapter);

            /*loginButton.setReadPermissions("user_friends");
            loginButton.setReadPermissions("public_profile");
            loginButton.setReadPermissions("email");
            loginButton.setReadPermissions("user_birthday");*/


                loginRlay = (RelativeLayout) findViewById(R.id.login_rlay);
                loginRlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  validRegister();
                        phoneVerifyWeb();

                    }
                });
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG);

        }

    }



    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void phoneVerifyWeb() {
        try {
            setLoading();
            showProgress();
            if (!isValidMobile(phoneCedit, "Please enter your mobile number"))
            {
                hideProgress();
                return;
            }

            countryData = (ItemData) spinner.getSelectedItem();
            String code = countryData.getCode();
            LogInCodeVerifyActivity. phonenumber =  code + phoneCedit.getText()+"";
            LogInCodeVerifyActivity.phoneVerifyTXT = code + phoneCedit.getText();

            prefM.saveCountry(countryData.getCountry());
            if(NetworkService.isRelease) {
                Intent intent = new Intent(this, LogInCodeVerifyActivity.class);
                intent.putExtra("from", "login");
                startActivity(intent);
                //OnVerificationStateChangedCallbacks
            }
            else {
                Intent intent = new Intent(this, LoginPhoneActivity.class);
                intent.putExtra("phone", LogInCodeVerifyActivity. phonenumber);
                startActivity(intent);
                finish();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG);
            //  LogUtils.LOGD("sign","lA onCreate exception "+e.getMessage());
        }
    }

    public void backpress(View view) {
        finish();
    }

   /* public void loginProcess(View view) {
        try {
            setLoading();
            showProgress();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 300);
            try {
                if (!isValidEmail(edt_email, "Enter valid Email")){
                    hideProgress();
                    return;}
                if (!isValid(edt_password, "Enter password")){
                    hideProgress();
                    return;}
                callLogin(1);


            } catch (Exception e) {
                hideProgress();
                // LogUtils.LOGD("sign","lA validRegister exception "+e.getMessage());
            }
        }
        catch(Exception ex)
        { hideProgress();ex.printStackTrace();}
    }*/




  /*  private void saveCustomerData(Context context, String role) {
        if(isValid(companyId))
            prefM.saveCompanyID(companyId);
        prefM.saveCustomerRole(role);
        ConnectToPacifyrActivity.start(context);
        finishAffinity();
    }*/

   /* private void saveToPrefM() {
        prefM.saveJsonKey(jsonStrng);
        prefM.saveAccessToken(loginModel.getAccessToken());
        prefM.saveUserID(loginModel.getUser().getId());
        prefM.saveUserFirstName(userModel.getFirstName());
        prefM.saveUserUserLastName(userModel.getLastName());
        prefM.saveMail(userModel.getEmail());
        prefM.saveCountry(userModel.getCountry());
        prefM.saveCountry(userModel.getCountry());
        prefM.saveCompanyID(userModel.getCompanyId());

        prefM.saveQBID(qbID);

        if(isValid(FName)){
            prefM.savefbName(FName);
        }else{
            prefM.savefbName(userModel.getFirstName()+" "+userModel.getLastName());
        }
    }*/














    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //callbackmanager2.onActivityResult(requestCode, resultCode, data);
      //  callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private LinearLayout actionBarTopLlay;
    private RelativeLayout bottomPanel;
    private LinearLayout leftLlay;
    private DeviceFitImageView backImv;
    private CustomFontTextView leftTxv;
    private CustomFontTextView titleTxv;
    private LinearLayout rightLlay;
    private CustomFontTextView rightTxv;
    private DeviceFitImageView rightImv;

    public void onSetActionBar() {
        actionBarTopLlay = (LinearLayout) findViewById(R.id.action_bar_top_llay);
        bottomPanel = (RelativeLayout) findViewById(R.id.bottom_panel);
        leftLlay = (LinearLayout) findViewById(R.id.left_llay);
        backImv = (DeviceFitImageView) findViewById(R.id.back_imv);
        leftTxv = (CustomFontTextView) findViewById(R.id.left_txv);
        titleTxv = (CustomFontTextView) findViewById(R.id.title_txv);
        rightLlay = (LinearLayout) findViewById(R.id.right_llay);
        rightTxv = (CustomFontTextView) findViewById(R.id.right_txv);
        rightImv = (DeviceFitImageView) findViewById(R.id.right_imv);
        rightImv.setVisibility(View.INVISIBLE);
        rightTxv.setVisibility(View.INVISIBLE);
        rightLlay.setVisibility(View.INVISIBLE);
        leftTxv.setVisibility(View.INVISIBLE);
        backImv.setVisibility(View.VISIBLE);
        titleTxv.setVisibility(View.VISIBLE);
        leftLlay.setVisibility(View.VISIBLE);
        defSetText(titleTxv,"");
        //backImv.setBackgroundResource(R.drawable.leftmen);
        leftLlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}

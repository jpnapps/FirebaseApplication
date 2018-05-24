package com.jpapps.firebaseapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ceino.utilitylibrary.CustomEditText;
import com.ceino.utilitylibrary.model.pacifyr.MRoleModel;
import com.ceino.utilitylibrary.model.pacifyr.MUser;
import com.ceino.utilitylibrary.model.pacifyr.ModelPhoneLogin;
import com.ceino.utilitylibrary.utils.PrefManager;
import com.pacifyr.pacifyrapp.R;
import com.pacifyr.pacifyrapp.base.PacifyerActivity;
import com.pacifyr.pacifyrapp.home.ConnectToPacifyrActivity;
import com.pacifyr.pacifyrapp.home.PacifyrDashboardActivity;
import com.pacifyr.pacifyrapp.network.GsonUtils;
import com.pacifyr.pacifyrapp.network.NetworkService;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.sample.core.utils.Toaster;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.json.JSONObject;

import static com.ceino.utilitylibrary.network.UtilityNetworkService.ACCOUNT_KEY;
import static com.ceino.utilitylibrary.network.UtilityNetworkService.APP_ID;
import static com.ceino.utilitylibrary.network.UtilityNetworkService.AUTH_KEY;
import static com.ceino.utilitylibrary.network.UtilityNetworkService.AUTH_SECRET;
import static com.ceino.utilitylibrary.network.UtilityNetworkService.GLOBAL_URL;
import static com.pacifyr.pacifyrapp.network.NetworkService.editProfileObject;

public class LoginPhoneActivity extends PacifyerActivity {

    public Bundle bundle;
    public String phoneNo;//phoneFromTextview;
    public ModelPhoneLogin modelPhoneLogin;
    public CustomEditText phone_tv;
    //public SharedPreferences sharedpreferences;
    //public RelativeLayout google_rlay;
    public String main_label="error",label="error";
    JSONObject userObject;
    private MUser userModel;
    private MRoleModel roleModel;
    private String jsonStrng;
    private Context context;

    private PrefManager prefM;
    //private CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fbLogout();
        //setContentView(R.layout.activity_login_phone);

        setContentView(R.layout.activity_sign_in_code_verify);
        context = LoginPhoneActivity.this;
        prefM = PrefManager.getInstance(context);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        bundle=getIntent().getExtras();
        phoneNo=bundle.getString("phone");
        phone_tv=(CustomEditText) findViewById(R.id.phone_no);
        //hideKeyboard();

        /* */


        //countryCodePicker = (CountryCodePicker) findViewById(R.id.login_phone_ccp);
        ////phoneFromTextview=countryCodePicker.getSelectedCountryCode()+phone_tv.getText();
        //google_rlay=(RelativeLayout) findViewById(R.id.google_rlay);
        loginUsingPhone();


    }

    @Override
    protected View getSnackbarAnchorView() {
        return findViewById(com.quickblox.sample.groupchatwebrtc.R.id.scrollView);
    }

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
            String url = GLOBAL_URL + "login/phonenumber";
            AjaxCallback cb = new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, final JSONObject json, final AjaxStatus status) {
                    try {
                        modelPhoneLogin = GsonUtils.getInstance().gsonToModelPhoneLogin(json);
                        userModel = modelPhoneLogin.getUser();
                        jsonStrng = json.toString();
                        String logstat = modelPhoneLogin.getStatus().toString();
                        if (isValid(logstat)) {
                            if (logstat.equals("success")) {
                                // hideProgress();
                                //qbNewLogin();
                                signInCreatedUser();
                                //String sts = status.getMessage().toString();


                            } else {
                                //setStatus(modelPhoneLogin.getMessage(), R.color.md_red_100);
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
                                }, 900);
                            }
                        }

                        //results=memberModel.getResults();
                        //modelMember.toString();
                        //String js=json.toString();
                        //Toast.makeText(LoginPhoneActivity.this, js, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        hideProgress();
                        e.printStackTrace();
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
                        }, 900);
                    }
                }
            };
            AQuery aq = new AQuery(LoginPhoneActivity.this);
            cb.header("Content-Type", "application/json");
            JSONObject postDataParams = new JSONObject();
            postDataParams.putOpt("phoneNumber",phoneNo);
           // postDataParams.putOpt("phoneNumber","+"+phoneNo);
            aq.post(url, postDataParams, JSONObject.class, cb);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
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
            }, 900);
        }
    }



    private void qbNewLogin() {

        hideStatus();
        showProgress();

        try {
            QBSettings.getInstance().init(LoginPhoneActivity.this, APP_ID, AUTH_KEY, AUTH_SECRET);
            QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);

            QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {
                @Override
                public void onSuccess(QBSession qbSession, Bundle bundle) {


                    String userID = modelPhoneLogin.getUser().getId();
                    String mail = modelPhoneLogin.getUser().getEmail();
                    final QBUser qbUser = new QBUser(userID, userID);
                    qbUser.setEmail(mail);


                    QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                        @Override
                        public void onSuccess(QBUser qbUser, Bundle bundle) {

                            try {
                                /*String basetoken= BaseService.getBaseService().getToken();
                                prefM.saveBaseToken(basetoken);*/
                                saveUserData(qbUser);
                                startLoginService(qbUser);

                                saveValuesToPrefM(LoginPhoneActivity.this,modelPhoneLogin);
                                loginTheUser(qbUser);

                                /*NetworkService.editProfileObject=new JSONObject();
                                NetworkService.editProfileObject.putOpt("qbid", Long.valueOf(qbID));
                                //NetworkService.editProfileObject.putOpt("online", true);
                                hideProgress();*/

                                NetworkService.startActionEditProfile(LoginPhoneActivity.this);

                            } catch (Exception e) {
                                Toast.makeText(LoginPhoneActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                                getPrefManager(LoginPhoneActivity.this).clearSharedAll();
                                //LoginManager.getInstance().logOut();
                               fbLogout();
                                Intent intent = new Intent(LoginPhoneActivity.this,SignActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onError(QBResponseException responseException) {
                            Toast.makeText(LoginPhoneActivity.this, "User signed up failed!" + responseException.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            responseException.printStackTrace();
                            getPrefManager(LoginPhoneActivity.this).clearSharedAll();
                            //LoginManager.getInstance().logOut();
                           fbLogout();
                            Intent intent = new Intent(LoginPhoneActivity.this,SignActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

                @Override
                public void onError(QBResponseException e) {

                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }



    }


    private void signInCreatedUser( ) {
        //final QBUser user,
        hideStatus();
        setLoading("Connecting");
        showProgress();
       final String userID = userModel.getId();
        String mail = modelPhoneLogin.getUser().getEmail();
        final QBUser user = new QBUser(userID, userID);
        user.setEmail(mail);
        QBSettings.getInstance().init(LoginPhoneActivity.this, APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                requestExecutor.signInUser(user, new com.pacifyr.pacifyrapp.quickblox.utils.QBEntityCallbackImpl<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle params) {
                        qbUser.setPassword(userID);
                        Log.d("jp", "LPA signInCreatedUser getPassword "+qbUser.getPassword());
                        try {
                                saveUserData(qbUser);
                                startLoginService(qbUser);
                                saveValuesToPrefM(LoginPhoneActivity.this,modelPhoneLogin);
                                loginTheUser(qbUser);
                           }
                           catch (Exception e) {
                                hideProgress();
                                hideStatus();
                                Toast.makeText(LoginPhoneActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                                prefM.clearSharedAll();
                                fbLogout();
                                startActivity(new Intent(LoginPhoneActivity.this,SignActivity.class));
                                finish();
                        }
                    }

                    @Override
                    public void onError(QBResponseException responseException) {
                        hideProgress();
                        Toaster.longToast("User signInUser failed!"+ responseException.getMessage());
                        Toast.makeText(LoginPhoneActivity.this, "User signInUser failed!" + responseException.getMessage(),
                                Toast.LENGTH_LONG).show();
                        prefM.clearSharedAll();
                        fbLogout();
                        startActivity(new Intent(LoginPhoneActivity.this,SignActivity.class));
                        finish();
                    }
                });
            }

            @Override
            public void onError(QBResponseException e) {
                hideProgress();
                Toast.makeText(LoginPhoneActivity.this, "QB sessron create failure !" + e.getMessage(), Toast.LENGTH_LONG).show();
                prefM.clearSharedAll();
                fbLogout();
                startActivity(new Intent(LoginPhoneActivity.this,SignActivity.class));
                finish();
            }
        });

    }

    private void loginTheUser(QBUser qbUser) {
        try{
            roleModel = userModel.getRoleModel();
            main_label = roleModel.getMainLabel();
            label = roleModel.getLabel();
            String qbID = qbUser.getId().toString();
            prefM.saveQBID(qbID);
            editProfileObject=new JSONObject();
            editProfileObject.putOpt("qbid", Long.valueOf(qbID));
            //NetworkService.editProfileObject.putOpt("online", true);
            hideProgress();
            setStatus("Login Success ", R.color.md_green_400);
            showStatus();
            //NetworkService.mCurrentUser.setImage(userModel.getImage());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                    //ToastHandler.newInstance(LoginPhoneActivity.this).showToast("main_label :"+main_label+" label"+label);
                    if (main_label.equals("pacifyr")){

                        Boolean flag = userModel.isTermsAccepted();
                        if (!flag){
                            hideStatus();
                            hideKeyboard();
                            Intent i = new Intent(LoginPhoneActivity.this, PacifyrTermsPolicyActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            getPrefManager(LoginPhoneActivity.this).saveIsOnline("true");
                            startActivity(i);
                            finishAffinity();

                        }else{
                            hideStatus();
                            hideKeyboard();
                            prefM.saveIsOnline("true");
                            editProfileObject =new JSONObject();
                            editProfileObject.put("isOnline",true);
                            NetworkService.webListEmotions(LoginPhoneActivity.this);
                            startActivity(new Intent(context, PacifyrDashboardActivity.class));
                            finishAffinity();
                        }


                    }else if(main_label.equals("customer")){
                        if (label.equalsIgnoreCase("employee")) {
                            prefM.saveCompanyID(userModel.getCompanyId());
                            saveUserRoleGO(context,"employee");
                        } else if (label.equalsIgnoreCase("customer")) {
                            saveUserRoleGO(context,"customer");
                        }
                        else if (label.equalsIgnoreCase("paidMember")) {
                            saveUserRoleGO(context,"paidMember");
                        }
                    }
                    NetworkService.startActionEditProfile(LoginPhoneActivity.this);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    hideProgress();
                    Toast.makeText(LoginPhoneActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    prefM.clearSharedAll();
                    fbLogout();
                    startActivity(new Intent(LoginPhoneActivity.this,SignActivity.class));
                    finish();
                }

            }
        },500);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private void saveUserRoleGO(Context context, String role) {
        try {
            prefM.saveCustomerRole(role);
            hideStatus();
            hideKeyboard();
            ConnectToPacifyrActivity.start(LoginPhoneActivity.this);
            finishAffinity();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void saveValuesToPrefM(Context context, ModelPhoneLogin modelPhone) {
        try {
            MUser userModel = modelPhone.getUser();

            prefM.saveJsonKey(jsonStrng);
            prefM.saveAccessToken(modelPhone.getAccessToken());
            prefM.saveUserID(userModel.getId());
            prefM.saveUserFirstName(userModel.getFirstName());
            prefM.saveUserUserLastName(userModel.getLastName());
            prefM.saveMail(userModel.getEmail());
            prefM.savefbName(userModel.getName());
            prefM.saveCountry(userModel.getCountry());
            if (isValid(userModel.getImage())) {
                prefM.saveProfileImageURL(userModel.getImage());
                NetworkService.mCurrentUser.setImage(userModel.getImage());
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

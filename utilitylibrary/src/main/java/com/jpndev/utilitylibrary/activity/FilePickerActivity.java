package com.jpndev.utilitylibrary.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import com.google.gson.Gson;

import com.jpndev.utilitylibrary.LogUtilsutility;
import com.jpndev.utilitylibrary.R;
import com.jpndev.utilitylibrary.ToastHandler;
import com.jpndev.utilitylibrary.base.BaseAppCompactActivity;
import com.jpndev.utilitylibrary.network.GsonUtils;
import com.jpndev.utilitylibrary.utils.PrefManager;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

/**
 * Created by Mani-Ceino on 9/8/2017.
 */

public class FilePickerActivity extends BaseAppCompactActivity {

    private LinearLayout container;

    public static Fragment fragment=null;
    public static ArrayList<String> docPaths = new ArrayList<>();
    public static void openActivity(Activity activity, int requestCode
                            ) {
        docPaths= new ArrayList<>();
        Intent intent = new Intent(activity, FilePickerActivity.class);

        activity.startActivityForResult(intent, requestCode);
        // activity.startActivityForResult(cameraIntent, ACTIVITY_REQUEST_CODE);
    }
/*    public static void openCustomFraActivity(Activity activity, Fragment fragment) {
        Intent intent = new Intent(activity, FilePickerActivity.class);
        FilePickerActivity.fragment=fragment;
        activity.startActivity(intent);

    }
    public static void openCustomFraActivity(Activity activity,Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        Intent intent = new Intent(activity, FilePickerActivity.class);
        FilePickerActivity.fragment=fragment;
        activity.startActivity(intent);

    }
    public static void openCustomFraActivity(Context activity, Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        Intent intent = new Intent(activity, FilePickerActivity.class);
        FilePickerActivity.fragment=fragment;
        activity.startActivity(intent);

    }*/
    public void hideKeyboard() {
        // Check if no view has focus:
        View view = FilePickerActivity.this.getCurrentFocus();
        if(view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_file_picker2);
        try {
            container = (LinearLayout) findViewById(R.id.container);
            int permissionCheck = ContextCompat.checkSelfPermission(FilePickerActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FilePickerActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                pickDoc();
            }

           /* if(isValid(fragment))
            {
                showFragment(fragment);
            }
            else
                finish();*/
        }catch (Exception e)
        {
            finish();
        }
    }



    public void pickDoc()
    {
        FilePickerBuilder.getInstance().setMaxCount(1)
                .setSelectedFiles(docPaths)
                .setActivityTheme(R.style.StartThemeWithoutBg)
                .pickFile(FilePickerActivity.this);
    }

    public static final int PERMISSION_REQUEST_CODE = 511;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        //   super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode != PERMISSION_REQUEST_CODE) return;
        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            ToastHandler.newInstance(FilePickerActivity.this).showToast("Storage permission denied");
                Log.d("","Storage permission denied");
            return;
        }

        pickDoc();
        /*Activity activity = mActivity.get();
        if (activity == null) return;
        startNDocumentntent(activity);*/
    }
    Bundle bundle;
    private boolean mReturningWithResult = false;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        try {
            LogUtilsutility.LOGD("jithish", "QDA onPostResume ");
            if (mReturningWithResult) {
                 /*  if (isValid(bundle)) {

                 PostNDoc postNDoc = new PostNDoc();
                    postNDoc.setUserid(PrefManager.getInstance(getApplicationContext()).getProfileID());
                   // postNDoc.setFilename("Sample" + (int) Math.random());

                    String path=docPaths.get(0);
                    if(!isValid(path,"File mot found"))
                        return;

                    String filename=path.substring(path.lastIndexOf("/")+1);
                    if(isValid(filename))
                    postNDoc.setFilename(""+filename);
                    else
                        postNDoc.setFilename("file");
                    File file = new File(docPaths.get(0));
                    nDocumentMultipartWeb2(postNDoc, file);
                }
                else
                {
                    if(isUploading)
                    showProgress();
                }*/
                returnBackData();
                mReturningWithResult = false;
                bundle = null;
            }
        }catch (Exception e)
        {
            ToastHandler.newInstance(getApplicationContext()).mustShowToast("Error : Please Try Again");
            LogUtilsutility.LOGD("jithish", "QDA onPostResume "+e.getMessage());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        LogUtilsutility.LOGD("layerutils", " image on Qs detail activity");
        try {

            switch (requestCode) {
                case FilePickerConst.REQUEST_CODE_DOC:
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        docPaths = new ArrayList<>();
                        docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                        mReturningWithResult = true;
                        bundle = new Bundle();
                        bundle.putString("fragment", "ggggg");
                    }
                    else if (resultCode == Activity.RESULT_CANCELED ) {
                        if(!isUploading) {
                            docPaths = null;
                            finish();
                        }
                        else
                        {
                            mReturningWithResult = true;
                            bundle =null;

                        }
                    }
                    break;
            }

          /*  List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if(fragments != null) {
                for (Fragment fragment : fragments) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }*/

        } catch (Exception e){
          //  Crashlytics.logException(e);
            ToastHandler.newInstance(this).mustShowToast("Please Try Again");
        }

    }

    public boolean isUploading=false;


  private void returnBackData() {
        LogUtilsutility.LOGD("jithish"," mediapicker return data ");
        Intent data = new Intent();

        setResult(Activity.RESULT_OK, data);
        finish();
    }

    public void showFragment(android.support.v4.app.Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("" + fragment).commit();
// fragmentShowActionBar(fragment);

    }
    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        if(!isUploading) {
            finish();
        }
        else
        {
            showProgress();
        }
    }



}

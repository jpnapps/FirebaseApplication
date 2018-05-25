package com.jpndev.utilitylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jpndev.utilitylibrary.LogUtilsutility;

import com.jpndev.utilitylibrary.model.MLoginRoot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PrefManager {


    public static final String GCM_TOKEN = "gcm_pref_token";
 //   public static final String PREFERENCES = "Gcm_Config";
    //   public static final String PREFERENCES = "Login";

    public static final String UDID = "udid";
    public static final String DEVICETYPE = "android";




    public static final String USERID = "Userid";
    public static final String UserFirstName = "UserFirstName";
    public static final String UserLastName = "UserLastName";
    public static final String EMAIL = "EMAIL";
    public static final String PROFILE_IMAGE_URL = "profile_image_url";

    public static final String TO = "to:";
    public static final String FROM = "from:";
    public static final String SUBJECT = "subject:";
    public static final String COVER_LETTER = "cover_letter:";
    public static final String ATTACH_FILE = "attach_file:";


    public static final String LOGIN_PREFERENCES = "jp_Login";

    public static final String TYPE = "Type";
    public static final String WEBURL = "webUrl";
    public static final String MLOGINROOT = "MLOGINROOT";
    public static final String JSON_KEY = "JSON_KEY";





    Context context;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    private static PrefManager instance = null;
    PrefManager()
    {

    }

   // http://graph.facebook.com/1156564127777842/picture?type=larg
    public static PrefManager getInstance(Context context) {
        if(instance == null) {
            instance = new PrefManager(context);
        }
        return instance;
    }
    public PrefManager(Context context) {
        super();
        this.context = context;
        sharedpreferences=context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        editor=sharedpreferences.edit();
    }

    public String getTo()
    {
        return  getSharedString(TO, "");

    }
    public String getSubject()
    {
        return  getSharedString(SUBJECT, "");

    }
    public String getCoverLetter()
    {
        return  getSharedString(COVER_LETTER, "");

    }
    public String getAttachFile()
    {
        return  getSharedString(ATTACH_FILE, "");

    }
    public String getUserFirstName()
    {
        return getSharedString(UserFirstName,"");
    }
    public String getUserLastName()
    {
        return getSharedString(UserLastName,"");
    }


    public String getUserName()
    {
        String fName = getSharedString(UserFirstName, "");
        String lName = getSharedString(UserLastName, "");
        return fName+" "+lName;

    }

    public String getEmail()
    {
        return  getSharedString(EMAIL, null);

    }

    public String getUserID()
    {
        return getSharedString(USERID,"");

    }
    public String getJsonKey()
    {
        return getSharedString(JSON_KEY,"");
    }
    public MLoginRoot getLOGINRoot()
    {
        MLoginRoot loginRoot=null;
        try {


           String jsonString= getSharedString(MLOGINROOT,"");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            loginRoot = gson.fromJson(jsonString, MLoginRoot.class);
        }catch (Exception e)
        {

        }
        return loginRoot;
    }





    public void saveCoverLetter(String toemailID,String subject,String coverletter,String attachfile)
    {
        putSharedString(TO,toemailID);
        putSharedString(SUBJECT,subject);
        putSharedString(COVER_LETTER,coverletter);
        putSharedString(ATTACH_FILE,attachfile);

    }
    public void saveCoverLetter(String toemailID,String subject,String coverletter)
    {
        putSharedString(TO,toemailID);
        putSharedString(SUBJECT,subject);
        putSharedString(COVER_LETTER,coverletter);
        putSharedString(ATTACH_FILE,"");

    }

    public void saveEmail(String emailID)
    {
        putSharedString(EMAIL,emailID);

    }


    public void saveUserFirstName(String accesstoken)
    {
        putSharedString(UserFirstName,accesstoken);
    }
    public void saveUserUserLastName(String accesstoken)
    {
        putSharedString(UserLastName,accesstoken);
    }

    public void saveUserID(String  classid)
    {
        putSharedString(USERID,classid);

    }
    public void saveJsonKey(String jsonString)
    {
        putSharedString(JSON_KEY,jsonString);

    }

    public void saveLOGINRoot(MLoginRoot root)
    {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(root);

            putSharedString(MLOGINROOT, json);
        }catch (Exception e)
        {

        }
    }



    public void clear()
    {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void clearSharedAll() {
        if(!isValid(editor))
        {
            if(!isValid(sharedpreferences))
                sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
        }
        editor.clear().commit();
    }


    public String getTYPE()
    {
        return getSharedString(TYPE,"");

    }








    public String getWEBURL()
    {
        return getSharedString(WEBURL,"");

    }
    public void saveWEBURL(String  classid)
    {
        putSharedString(WEBURL,classid);

    }



    public String getRoleTYpe()
    {
        return getSharedString("Type","");

    }







    private String getDayString(String createdAt, SimpleDateFormat sdf) {

        String date="";
        try{
            date= sdf.format(sdf.parse(createdAt));
        }catch (Exception e)
        {
            LogUtilsutility.LOGD("exception", "getDayString exception" + e.getMessage());
        }
        return date;
    }
    private String getDayString(String pass_date) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date="";
        try{
            date= sdf.format(sdf.parse(pass_date));
        }catch (Exception e)
        {
            LogUtilsutility.LOGD("exception", "getDayString exception" + e.getMessage());
        }
        return date;
    }

    private Date getDate(int i,  Date now_date) {
        Date newDate =now_date;
        try{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now_date);
        calendar.add(Calendar.DAY_OF_YEAR, -i);
       newDate = calendar.getTime();
        }catch (Exception e)
        {
            LogUtilsutility.LOGD("exception", "getDate exception" + e.getMessage());
        }
        return newDate;
    }





    public String getSharedString(String KEY, String defValue) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        return sharedpreferences.getString(KEY, defValue);
    }

    public void putSharedString(String KEY, String value) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        editor.putString(KEY, value).commit();

    }

    public boolean getSharedBoolean(String KEY, boolean defValue) {
        if(!isValid(sharedpreferences))
            sharedpreferences = context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        return sharedpreferences.getBoolean(KEY, defValue);
    }

    public void putSharedBoolean(String KEY, boolean value) {
        if(!isValid(sharedpreferences))
            sharedpreferences = context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        editor.putBoolean(KEY, value).commit();
    }

    private Long getSharedLong(String KEY) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        return sharedpreferences.getLong(KEY,0L);
    }

    private void putSharedLong(String KEY, long value) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        editor.putLong(KEY, value).commit();
    }








    public String getString(String KEY, String defValue) {
        return sharedpreferences.getString(KEY, defValue);
    }

    public void putString(String KEY, String value) {

        editor.putString(KEY, value).commit();
    }


    public Long getLong(String KEY) {
        return sharedpreferences.getLong(KEY,0L);
    }

    public void putLong(String KEY, long value) {

        editor.putLong(KEY, value).commit();
    }




    public String getUpperCaseFirstLetter(String fname) {
        if(isValid(fname)) {
            StringBuilder rackingSystemSb = new StringBuilder(fname.toLowerCase());
            rackingSystemSb.setCharAt(0, Character.toUpperCase(rackingSystemSb.charAt(0)));
            fname = rackingSystemSb.toString();
        }
        return fname;
    }




    public Boolean isValid(Object text)
    {
        if(text!=null)

            return  true;
        return  false;

    }

    public Boolean isValid(int count)
    {
        if(count>0)
            return  true;
        return  false;

    }
    public Boolean isValid(String text)
    {
        if(text!=null)
            if(!text.trim().equalsIgnoreCase(""))
                return  true;
        return  false;

    }
    public Boolean isValid(List list)
    {
        if(list!=null)
            if(list.size()>0)
                return  true;
        return  false;

    }
}

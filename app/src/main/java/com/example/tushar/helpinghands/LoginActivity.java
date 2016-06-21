package com.example.tushar.helpinghands;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import User.UserDetails;

import static com.android.volley.Request.Method;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener,CompoundButton.OnCheckedChangeListener {


    private View mProgressView;
    private View mLoginFormView;
    private CallbackManager callbackManager;
    private UserDetails userDetails;
    private TextView adminLogin;
    private TextView adminVerifyBtn;
    private TextView adminCancelBtn;
    private AlertDialog adminDialog;
    private int tapCount;
    private EditText adminPassword;
    private Button mEmailSignInButton;
    private TextView skipRegistration;
    private ArrayList<String> adminList;
    private EditText mobileTextBox;
    private EditText passwordBox;
    private JSONObject abc;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private UserDetails user;
    private String url;
    private Dialog dialog;
    private CheckBox autoSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        if(Preferences.getInstance(LoginActivity.this).isAutoLogin()){
            initiateLogin(null);
        }
       // showOpeningAppDialog();

        mobileTextBox = (EditText) findViewById(R.id.mobile_text_box);
        passwordBox=(EditText)findViewById(R.id.password_edit);
        autoSignIn=(CheckBox)findViewById(R.id.stay_signed_in);
        autoSignIn.setOnCheckedChangeListener(this);
        //userDetails = new UserDetails();
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doOtpRequestCall();
            }
        });
        url=Constants.Url;
        //url=((HelpingHandsApplication)getApplicationContext()).getUrl();
      //  mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        skipRegistration = (TextView)findViewById(R.id.skip_registration);
        skipRegistration.setOnClickListener(this);
        adminLogin = (TextView)findViewById(R.id.admin_login);
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(++tapCount >= 1) {
                    tapCount=0;
                    showAdminLoginDialog();
                }
            }
        });
        //populateAutoComplete();
        userDetails=new UserDetails();
        //mPasswordView = (EditText) findViewById(R.id.password);
     /*   mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });*/
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        adminList = new ArrayList<String>();
        adminList.add("tushar gupta");

      /*  fbProfilePic = (ImageView) findViewById(R.id.fb_pro_pic);
        LoginButton button = (LoginButton) findViewById(R.id.login_button);
        button.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        callbackManager = CallbackManager.Factory.create();
        String email = null;
        String birthday = null;
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());
                                        // Application code
                                        try {
                                            userDetails.setName(object.getString("name"));
                                            userDetails.seteMail(object.getString("email"));
                                            userDetails.setDob(object.getString("birthday"));
                                            userDetails.setFbUserId(object.getString("id"));
                                            setFacebookProfilePicture(object.getString("id"));
                                            //      Log.v("LoginActivity","email is "+email+" birthday is "+birthday );
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // 01/31/1980 format
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.v("LoginActivity", "cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });
*/
      //  launchFormPage();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.admin_verify) {
            if (adminPassword.getText().toString().equals("t")) {
                Preferences.getInstance(this).setLoggeInAsAdmin(true);
                Intent intent = new Intent(LoginActivity.this, InformationTabsActivity.class);
                startActivity(intent);
            }
            adminDialog.dismiss();
        } else if (id == R.id.admin_cancel) {
            if (adminDialog != null) {
                adminDialog.dismiss();
            }
        }
        else if(id==R.id.skip_registration){
            Preferences.getInstance(this).setLoggeInAsAdmin(false);
            Intent intent = new Intent(LoginActivity.this, InformationTabsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void doOtpRequestCall() {

        otpThread abc=new otpThread();
        abc.start();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            Preferences.getInstance(LoginActivity.this).setAutoLogin(true);
        }
        else {
            Preferences.getInstance(LoginActivity.this).setAutoLogin(true);
        }
    }

    public class otpThread extends Thread {

        @Override
        public void run() {

            final Map<String,String> mHeader= new ArrayMap<String, String>();
            String uri=url+"/login";
            mHeader.put("Content-Type", "application/json; charset=utf-8");
            final Map<String,String> params=new HashMap<String, String>();
            params.put("mobile",mobileTextBox.getText().toString());
            params.put("password",passwordBox.getText().toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, uri, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getString("status").equalsIgnoreCase("verified") && response.getString("isvalid").equalsIgnoreCase("true")){
                            initiateLogin(response);
                        }
                        else if(response.getString("status").equalsIgnoreCase("verified") && response.getString("isvalid").equalsIgnoreCase("false")) {
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 Toast.makeText(LoginActivity.this, R.string.enter_correct_password,Toast.LENGTH_SHORT).show();
                                 passwordBox.setText("");
                             }
                         });
                        }
                        else{
                        registerUser(response);
                            showOtpBox();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, R.string.verify_internet,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return mHeader;
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(getBaseContext()).add(jsonObjectRequest);
        }

        /*@Override
        public JSONObject call() throws Exception {
            final String url = "http://192.168.1.2:3000/login";
            final Map<String, String> mHeader = new ArrayMap<String, String>();
            //JSONObject a=new JSONObject();
            mHeader.put("Content-Type", "application/json; charset=utf-8");
            final Map<String, String> params = new ArrayMap<>();
            params.put("mobile", mobileTextBox.getText().toString());
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, url, new JSONObject(params), future, future) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return mHeader;
                }
            };
            //  jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(getBaseContext()).add(jsonObjectRequest);

            try {
                JSONObject response = future.get();
                Log.d("otp response", response.toString());
                return response;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }*/
    }

    private void initiateLogin(JSONObject user) {
        if(user!=null){   //Not overwriting on auto login
        addUserDetailsToPreferences(user);}
        Intent intent = new Intent(LoginActivity.this, InformationTabsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void addUserDetailsToPreferences(JSONObject user) {
       Preferences pref= Preferences.getInstance(LoginActivity.this);
        try {
            pref.setUserUid(user.getString("uuid"));
            pref.setUserToken(user.getString("token"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void registerUser(JSONObject response)  {
      //abc=response;
        Log.d("otp abc",response.toString());
        user=UserDetails.getInstance();
        try {
            user.setUuid(response.getString("uuid"));
            user.setStatus(response.getString("status"));
            user.setToken(response.getString("token"));
            user.setMobile(response.getString("mobile"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void updateUser(JSONObject response)  {
        //abc=response;
        Log.d("otp abc",response.toString());
        user=UserDetails.getInstance();
        try {
            user.setUuid(response.getString("uuid"));
            user.setStatus(response.getString("status"));
            user.setToken(response.getString("token"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showOtpBox() {
        dialog = new Dialog(this);
        final String TAG = "helping hand otp";
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Enter OTP ");
        final String url = Constants.Url;
        Button btnOk = (Button) dialog.findViewById(R.id.otp_confirm_button);
        btnOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtpForVerification();
            }
        });
        dialog.show();

    }

    private void sendOtpForVerification() {
        if(user==null){
            return;
        }
        callApiForVerification();
    }

    private void callApiForVerification() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map<String,String> mHeader= new ArrayMap<String, String>();
                String uri=url+"/verify";
                mHeader.put("Content-Type", "application/json; charset=utf-8");
                final Map<String,String> params=new HashMap<String, String>();
                final EditText otpText = (EditText) dialog.findViewById(R.id.otp_box);
                params.put("otp",otpText.getText().toString());
                params.put("uuid",user.getUuid());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, uri, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    updateUser(response);
                        dialog.dismiss();
                        initiateLogin(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                          Toast.makeText(LoginActivity.this, R.string.re_enter_otp,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return mHeader;
                    }
                };
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(getBaseContext()).add(jsonObjectRequest);
            }
        }).start();
    }


    private void showOpeningAppDialog(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.opening_dialog, null);
        dialog.setView(view);
        final AlertDialog alert = dialog.create();
        alert.show();

        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                alert.dismiss();
            }
        }.start();
    }

    private void showAdminLoginDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.admin_login_dialog, null);
        adminPassword = (EditText)view.findViewById(R.id.admin_password);
        adminVerifyBtn = (TextView)view.findViewById(R.id.admin_verify);
        adminCancelBtn = (TextView)view.findViewById(R.id.admin_cancel);
        adminVerifyBtn.setOnClickListener(this);
        adminCancelBtn.setOnClickListener(this);
        dialog.setView(view);
        adminDialog = dialog.create();
        adminDialog.show();
    }



    private void launchNextActivity() {
        if (adminList.contains(userDetails.getName().toLowerCase())) {
            launchFormPage();
        }
    }

    private void launchFormPage() {
        Intent formPageLaunch = new Intent(LoginActivity.this, FormFillActivity.class);
        startActivity(formPageLaunch);
    }

  /*  public void setFacebookProfilePicture(String userId) {
        //Bitmap bitmap = null;
        Runnable r = new MyThread(userId);
        new Thread(r).start();
    }

    public class MyThread implements Runnable {
        String userId;
        Bitmap bitmap;

        public MyThread(String userid) {
            // store parameter for later user
            userId = userid;
        }

        public void run() {
            try {
                URL imageUrl = new URL("https://graph.facebook.com/" + userId + "/picture?type=large");
                bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                userDetails.setFbProfilePic(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fbProfilePic.setImageBitmap(bitmap);
                }
            });
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    //    callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}


package com.example.tushar.helpinghands;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import User.UserDetails;

import static com.android.volley.Request.Method;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private ImageView fbProfilePic;
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
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
    private JSONObject abc;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private UserDetails user;
    private String url;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        showOpeningAppDialog();
        // Set up the login form.
        mobileTextBox = (EditText) findViewById(R.id.mobile_text_box);
        userDetails = new UserDetails();
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doOtpRequestCall();
                showOtpBox();
            }
        });
        url="http://192.168.1.5:3000/";
        //url=((HelpingHandsApplication)getApplicationContext()).getUrl();
      //  mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        skipRegistration = (TextView)findViewById(R.id.skip_registration);
        skipRegistration.setOnClickListener(this);
        adminLogin = (TextView)findViewById(R.id.admin_login);
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(++tapCount >= 7) {
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

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        adminList = new ArrayList<String>();
        adminList.add("tushar gupta");

        fbProfilePic = (ImageView) findViewById(R.id.fb_pro_pic);
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

      //  launchFormPage();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.admin_verify) {
            if (adminPassword.getText().toString().equals("tushar")) {
                Intent intent = new Intent(LoginActivity.this, FormFillActivity.class);
                startActivity(intent);
            }
            adminDialog.dismiss();
        } else if (id == R.id.admin_cancel) {
            if (adminDialog != null) {
                adminDialog.dismiss();
            }
        }else if(id==R.id.email_sign_in_button){
           // attemptLogin();
        }else if(id==R.id.skip_registration){
            Intent intent = new Intent(LoginActivity.this, InformationTabsActivity.class);
            startActivity(intent);
        }
    }

    private void doOtpRequestCall() {

       /* AsyncTask a=new AsyncTask(this);
        try {
            JSONObject json=a.execute().get(200,TimeUnit.SECONDS);
            Log.d("final otp json",json.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (TimeoutException e){
            e.printStackTrace();
        }*/
        otpThread abc=new otpThread();
        abc.start();
        /*final ExecutorService service;
        final Future<JSONObject> task;

        service = Executors.newFixedThreadPool(10);
        task = service.submit(new otpThread());
        JSONObject str=new JSONObject();
        try {

            str = task.get();
            Log.d("otp",str.toString());
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        } catch (final ExecutionException ex) {
            ex.printStackTrace();
        }
        service.shutdown();*/
       /* final ExecutorService service;
        final Future<JSONObject> task;

        service = Executors.newFixedThreadPool(1);
        task = service.submit(new otpThread());
        final JSONObject str;
        try {

            str = task.get(10, TimeUnit.SECONDS);
            // waits the 10 seconds for the Callable.call to finish.
                 // this raises ExecutionException if thread dies
            Log.d("otp",str.toString());
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        } catch (final ExecutionException ex) {
            ex.printStackTrace();
        }
        catch (TimeoutException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("attempt to shutdown executor");
            service.shutdown();
            service.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            if (!service.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            service.shutdownNow();
            System.out.println("shutdown finished");
        }*/
        //}
        /*final String url = "http://192.168.1.2:3000/login";
        final Map<String, String> mHeader = new ArrayMap<>();
        JSONObject a = new JSONObject();
        mHeader.put("Content-Type", "application/json; charset=utf-8");
    *//*new Thread(new Runnable() {
            @Override
            public void run() {*//*
        final Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobileTextBox.getText().toString());
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, url, new JSONObject(params), future, future) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return mHeader;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getBaseContext()).add(jsonObjectRequest);

        try {
            JSONObject response = future.get(40, TimeUnit.SECONDS);
            // a=response;
            Log.d("otp response", response.toString());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }

/*
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.tushar.helpinghands/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }
*/

 /*   @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.tushar.helpinghands/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    //  }).start();
    //}*/

    public class AsyncTask extends android.os.AsyncTask<Void , Void , JSONObject> {
        private Context cntx;

        AsyncTask(Context context){
            cntx=context;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            final String url = "http://192.168.1.2:3000/login";
            final Map<String, String> mHeader = new ArrayMap<>();
            JSONObject a = new JSONObject();
            mHeader.put("Content-Type", "application/json; charset=utf-8");
    /*new Thread(new Runnable() {
            @Override
            public void run() {*/
            Map<String, String> mparams = new HashMap<String, String>();
            mparams.put("mobile", "123456");
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, url, new JSONObject(mparams), future, future) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return mHeader;
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(100000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(cntx).add(jsonObjectRequest);

            try {
                JSONObject response = future.get(200, TimeUnit.SECONDS);
                // a=response;
                Log.d("otp response", response.toString());
                return response;
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
         return null;
        }

    }

    public class otpThread extends Thread {

        @Override
        public void run() {

            final Map<String,String> mHeader= new ArrayMap<String, String>();
            //JSONObject a=new JSONObject();
            String uri=url+"login";
            mHeader.put("Content-Type", "application/json; charset=utf-8");
            final Map<String,String> params=new HashMap<String, String>();
            params.put("mobile",mobileTextBox.getText().toString());
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, uri,new JSONObject(params),future,future){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return mHeader;
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(getBaseContext()).add(jsonObjectRequest);

            try {
                JSONObject response = future.get(40, TimeUnit.SECONDS);
                Log.d("otp response",response.toString());
                tryFunction(response);
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
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

    private void tryFunction(JSONObject response)  {
      abc=response;
        Log.d("otp abc",abc.toString());
        user=new UserDetails();
        try {
            user.setUuid(response.getString("UUID"));
            user.setStatus(response.getString("status"));
            user.setToken(response.getString("token"));
            user.setMobile(response.getString("mobile"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showOtpBox() {
        dialog = new Dialog(this);
        final String TAG = "helping hand otp";
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Enter OTP ");
        final String url = "http://192.168.1.6:3000/login";
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
                //JSONObject a=new JSONObject();
                String uri=url+"verify";
                mHeader.put("Content-Type", "application/json; charset=utf-8");
                final Map<String,String> params=new HashMap<String, String>();
                final EditText otpText = (EditText) dialog.findViewById(R.id.otp_box);
                params.put("otp",otpText.getText().toString());
                params.put("uuid",user.getUuid());
                RequestFuture<JSONObject> future = RequestFuture.newFuture();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, uri,new JSONObject(params),future,future){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return mHeader;
                    }
                };
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(getBaseContext()).add(jsonObjectRequest);

                try {
                    JSONObject response = future.get(40, TimeUnit.SECONDS);
                    Log.d("otp response",response.toString());
                    tryFunction(response);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
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

    public void setFacebookProfilePicture(String userId) {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}


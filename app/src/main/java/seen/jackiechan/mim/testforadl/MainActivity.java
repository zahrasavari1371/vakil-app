package seen.jackiechan.mim.testforadl;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    private static final float END_SCALE = 0.7f;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View contentView;
    private ViewFlipper start_view_flipper;
    private ProgressDialog progressDialog;
    private EditText num1;
    private EditText num2;
    private EditText num3;
    private EditText num4;
    private String password;
    private String phone;
    private HashMap<String,String> userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_view_flipper = findViewById(R.id.start_view_flipper);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);

//        First if is for check internet connection
        if (checkInternet()) {
//            check that user is exist in app or not. If yes goes to main page, If no goes to register page
            MrSQl check = new MrSQl(getApplicationContext());
            if (check.exists(Config.class, "name", "token")) {
                start_view_flipper.setDisplayedChild(4);
                navMenuAnim();
//                Here if user did not in app should register first by his/her mobile
            } else {
                Log.e("This is token ", check.get(Config.class,"name","token").getValue());
                start_view_flipper.setDisplayedChild(0);
//                saveUserInformation();
                generatePassword();
                navMenuAnim();
                Listeners();
            }
//            this else work when internet connection is off
        } else {
            start_view_flipper.setDisplayedChild(5);
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setCancelable(true);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_dialog);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    public void saveUserInformation() {

        TextInputEditText name = findViewById(R.id.name);
        TextInputEditText family = findViewById(R.id.family);
        TextInputEditText email = findViewById(R.id.email);
        TextInputEditText address = findViewById(R.id.address);
        Spinner state = findViewById(R.id.state);
        Spinner city = findViewById(R.id.city);
        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);

        String State = state.getSelectedItem().toString();
        String City = city.getSelectedItem().toString();

        userInfo=new HashMap<>();
        userInfo.put("name", Objects.requireNonNull(name.getText()).toString());
        userInfo.put("family", Objects.requireNonNull(family.getText()).toString());
        userInfo.put("email", Objects.requireNonNull(email.getText()).toString());
        userInfo.put("address", Objects.requireNonNull(address.getText()).toString());
        userInfo.put("state", Objects.requireNonNull(State));
        userInfo.put("city", Objects.requireNonNull(City));
        if (male.isChecked()){
            userInfo.put("gender","male");
        }
        if (female.isChecked()){
            userInfo.put("gender","female");
        }

        Log.e("User Information ",userInfo.toString());

//        userInfo = new ArrayList<>();
//        userInfo.add(new BasicNameValuePair("name", Objects.requireNonNull(name.getText()).toString()));
//        userInfo.add(new BasicNameValuePair("family", Objects.requireNonNull(family.getText()).toString()));
//        userInfo.add(new BasicNameValuePair("email", Objects.requireNonNull(email.getText()).toString()));
//        userInfo.add(new BasicNameValuePair("address", Objects.requireNonNull(address.getText()).toString()));
//        userInfo.add(new BasicNameValuePair("state", Objects.requireNonNull(State)));
//        userInfo.add(new BasicNameValuePair("city", Objects.requireNonNull(City)));
//        if (male.isChecked()) {
//            userInfo.add(new BasicNameValuePair("gender", "male"));
//        }
//        if (female.isChecked()) {
//            userInfo.add(new BasicNameValuePair("gender", "female"));
//        }
    }

    //    This method is for generating verity sms
    public void generatePassword() {
        num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (num1.length() == 1) {
                    password = num1.getText().toString();
                    num1.clearFocus();
                    num2.requestFocus();
                    num2.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (num2.length() == 1) {
                    password = password + num2.getText().toString();
                    num2.clearFocus();
                    num3.requestFocus();
                    num3.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (num3.length() == 1) {
                    password = password + num3.getText().toString();
                    num3.clearFocus();
                    num4.requestFocus();
                    num4.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                if (num4.length() == 1) {
                    password = password + num4.getText().toString();
                    login();
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage(""); // Setting Message
                    progressDialog.setTitle("لطفا صبر کنید..."); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                }
            }
        });
    }

    //    This method is for navigation drawer animation and its listeners
    public void navMenuAnim() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        contentView = findViewById(R.id.contentView);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(new DrawerArrowDrawable(this));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     if (drawerLayout.isDrawerOpen(navigationView)) {
                                                         drawerLayout.closeDrawer(navigationView);
                                                     } else {
                                                         drawerLayout.openDrawer(navigationView);
                                                     }
                                                 }
                                             }
        );

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                                           @Override
                                           public void onDrawerSlide(View drawerView, float slideOffset) {

                                               // Scale the View based on current slide offset
                                               final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                                               final float offsetScale = 1 - diffScaledOffset;
                                               contentView.setScaleX(offsetScale);
                                               contentView.setScaleY(offsetScale);

                                               // Translate the View, accounting for the scaled width
                                               final float xOffset = drawerView.getWidth() * slideOffset;
                                               final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                                               final float xTranslation = -xOffset + xOffsetDiff;
                                               contentView.setTranslationX(xTranslation);
                                           }

                                           @Override
                                           public void onDrawerClosed(View drawerView) {

                                           }
                                       }
        );

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wallet:
//                        Toast.makeText(MainActivity.this, "wallet clicked", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.userInfo:
                        Intent profile = new Intent(MainActivity.this, UserProfileActivity.class);
                        startActivity(profile);
                        break;

                    case R.id.turnover:
                        break;

                    case R.id.history:
                        break;

                    case R.id.favoriteLawyer:
                        break;

                    case R.id.inBux:
                        break;

                    case R.id.support:
                        Intent support = new Intent(MainActivity.this, SupportActivity.class);
                        startActivity(support);
                        break;

                    case R.id.setting:
                        break;

                    case R.id.about:
                        Intent about = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(about);
                        break;

                    case R.id.exitAccount:
                        break;

                }
                return true;
            }
        });
    }

    //    These are for Register listeners
    public void Listeners() {

        final Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_register.setBackgroundColor(getResources().getColor(R.color.white));
                start_view_flipper.setDisplayedChild(1);
            }
        });

        final Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MrSQl checkLogin = new MrSQl(getApplicationContext());
                if (checkLogin.exists(Config.class, "value", "token")) {

                }
                start_view_flipper.setDisplayedChild(1);
            }
        });

        final EditText mobile = findViewById(R.id.mobile);
        Button btn_submit_mobile = findViewById(R.id.btn_submit_mobile);
        btn_submit_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = mobile.getText().toString();
                getVerifyCode();
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage(""); // Setting Message
                progressDialog.setTitle("لطفا صبر کنید..."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
            }
        });

        TextView chose_photo = findViewById(R.id.chose_photo);
        chose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_photo();
            }
        });

        Button save_user_info = findViewById(R.id.save_user_info);
        save_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserInfo();
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage(""); // Setting Message
                progressDialog.setTitle("لطفا صبر کنید..."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
            }
        });
    }

    //    This method is for chose an image for user profile
    public void select_photo() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
//        int PICK_IMAGE_REQUEST=1;
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    //    This method return internet connection status
    public boolean checkInternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connected = Objects.requireNonNull(connectivityManager).getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        return connected;
    }

    //    Request to server for getting verify SMS
    public void getVerifyCode() {
        EditText mobile = findViewById(R.id.mobile);
        HashMap<String, String> value = new HashMap<>();
        value.put("phone", mobile.getText().toString());
        MrAPi register = new MrAPi("api/user/register", "post", value, getApplicationContext());
        register.getJsonObject(new MrAPi.MrOnTaskExecuteJsonObject() {
            @Override
            public void onTaskSuccess(JSONObject jsonObject) throws JSONException {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, jsonObject.getString("password"), Toast.LENGTH_SHORT).show();
                start_view_flipper.setDisplayedChild(2);
                Log.e("Register Successful", jsonObject.toString());
            }

            @Override
            public void onTaskFailure(Integer status, String response, Exception exception) {
                progressDialog.dismiss();
                Log.e("Register Error ", exception.toString());
            }
        });
    }

    //    Request to server for getting token for authorization
    public void login() {
        List<NameValuePair> values = new ArrayList<>();
        values.add(new BasicNameValuePair("phone", phone));
        values.add(new BasicNameValuePair("password", password));
        Log.e("values ", values.toString());
        final MrSQl sQl = new MrSQl(getApplicationContext());
        MrAPi login = new MrAPi("api/user/login", "post", values);
        login.getJsonObject(new MrAPi.MrOnTaskExecuteJsonObject() {
            @Override
            public void onTaskSuccess(JSONObject jsonObject) throws JSONException {
                Config phone = new Config("phone", jsonObject.getString("phone"));
                Config token = new Config("token", jsonObject.getString("token"));
                sQl.insert(phone);
                sQl.insert(token);
                Log.e("This is token ",sQl.get(Config.class,"name","token").getValue());
                progressDialog.dismiss();
                start_view_flipper.setDisplayedChild(3);
            }

            @Override
            public void onTaskFailure(Integer status, String response, Exception exception) {

            }
        });
    }

    //    This method send User information like first name, last name and etc to server
    public void sendUserInfo() {
        saveUserInformation();
        Log.e("User info ",userInfo.toString());
        MrAPi sendInfo = new MrAPi("api/user/update", "post", userInfo, getApplicationContext());
        sendInfo.getJsonObject(new MrAPi.MrOnTaskExecuteJsonObject() {
            @Override
            public void onTaskSuccess(JSONObject jsonObject) throws JSONException {
                progressDialog.dismiss();
                start_view_flipper.setDisplayedChild(4);
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
                Log.e("User Information are ",jsonObject.toString());
            }

            @Override
            public void onTaskFailure(Integer status, String response, Exception exception) {
                progressDialog.dismiss();
                Log.e("User Information Error ", exception.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri UriImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), UriImage);
                ImageView profile_photo = findViewById(R.id.profile_photo);
                profile_photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

//MrAPi mrAPi = new MrAPi("api/application/countries", "get");
//        mrAPi.getArrayList(new MrAPi.MrOnTaskExecuteList<Country>() {
//            @Override
//            public void onTaskSuccess(List<Country> objects) {
//                mrSQl.updateOrCreate(objects, "iCountryId");
//            }
//
//            @Override
//            public void onTaskFailure(Integer status, String response, Exception exception) {
//                Toast.makeText(getApplicationContext(), "failed to updated countries information", Toast.LENGTH_LONG).show();
//            }
//        });

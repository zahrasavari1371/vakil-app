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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
    private LinearLayout header_layout;
    private ViewFlipper start_view_flipper;
    private ProgressDialog progressDialog;
    private List<NameValuePair> values;
    private EditText num1;
    private EditText num2;
    private EditText num3;
    private EditText num4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_view_flipper = findViewById(R.id.start_view_flipper);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);

        if (checkInternet()) {
            start_view_flipper.setDisplayedChild(0);
            num1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (num1.length() == 1) {
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

//                        progressDialog = new ProgressDialog(MainActivity.this);
//                        progressDialog.setMessage(""); // Setting Message
//                        progressDialog.setTitle("لطفا صبر کنید..."); // Setting Title
//                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
//                        progressDialog.show(); // Display Progress Dialog
//                        progressDialog.setCancelable(false);
                }


                @Override
                public void afterTextChanged(Editable editable) {
                    if (num4.length() == 1) {
                        login();
                    }
                }
            });
            navMenuAnim();
            Listeners();
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

    public void navMenuAnim() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        contentView = findViewById(R.id.contentView);
        header_layout = findViewById(R.id.header_layout);

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

    public void Listeners() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    header_layout.setBackgroundColor(getResources().getColor(R.color.blue));
                }
            });
        }

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
                start_view_flipper.setDisplayedChild(1);
            }
        });

        Button btn_submit_mobile = findViewById(R.id.btn_submit_mobile);
        btn_submit_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                start_view_flipper.setDisplayedChild(4);
            }
        });
    }

    public void select_photo() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
//        int PICK_IMAGE_REQUEST=1;
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
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

    public boolean checkInternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }

    public void getVerifyCode() {
        EditText mobile = findViewById(R.id.mobile);
        HashMap<String, String> value = new HashMap<>();
        value.put("phone", mobile.getText().toString());
       MrAPi register = new MrAPi("api/user/register", "post", value, getApplicationContext());
        register.getString(new MrAPi.MrOnTaskExecute() {
            @Override
            public void onTaskSuccess(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                start_view_flipper.setDisplayedChild(2);
                Log.e("Successfull ", response);
            }

            @Override
            public void onTaskFailure(Integer status, String response, Exception exception) {
                Log.e("Register Error : ", exception.toString());
            }
        });
    }

    public void login() {
        EditText mobile = findViewById(R.id.mobile);
        values = new ArrayList<>();
        values.add(new BasicNameValuePair("phone", mobile.getText().toString()));
        values.add(new BasicNameValuePair("password", String.valueOf(num1.getText()) + num2.getText() + num3.getText() + num4.getText()));
        final MrSQl sQl = new MrSQl(getApplicationContext());
        MrAPi login = new MrAPi("api/user/login", "post", values, getApplicationContext());

        login.getArrayList(Config.class, new MrAPi.MrOnTaskExecuteList() {
            @Override
            public void onTaskSuccess(List<Object> objects) {
                Log.e("Login Successful ",objects.toString());
                Toast.makeText(MainActivity.this, "login was successfully", Toast.LENGTH_SHORT).show();
//                for (int i = 0; i < objects.size(); i++) {
//                    Config config = (Config) objects.get(i);
//                    sQl.insert(config);
//                }
//                progressDialog.dismiss();
                start_view_flipper.setDisplayedChild(3);
            }

            @Override
            public void onTaskFailure(Integer status, String response, Exception exception) {
                Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Login Error ", exception.toString());
            }
        });

    }

}
// final MrSQl mrSQl = new MrSQl(context);
//            final MrAPi mrAPi = new MrAPi("api/customers", "get", context);
//            mrAPi.getArrayList(Customer.class, new MrAPi.MrOnTaskExecuteList() {
//                @Override
//                public void onTaskSuccess(List<Object> objects) {
//                    for (int i = 0; i < objects.size(); i++) {
//                        Customer customer = (Customer) objects.get(i);
//                        mrSQl.updateOrCreate(customer, "customer_id");
//                    }
//                    onSyncComplete.onSyncSuccess(objects.size());
//                }
//
//                @Override
//                public void onTaskFailure(Integer status, String response, Exception exception) {
//                    onSyncComplete.onSyncFailure(status, response, exception);
//                }
//            });
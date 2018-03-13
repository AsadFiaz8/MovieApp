package com.example.asadfiaz.movieapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class SignUp extends AppCompatActivity {

    CircleImageView profileImage;
    EditText txtUserName, txtUserEmail, txtUserPassword, txtConformPassword;
    Button btnRedister;
    String url = "https://universityfyp2017.000webhostapp.com/userRegister.php";
    String imageUrl = "https://universityfyp2017.000webhostapp.com/imageuploads.php";
    TextView txtLogin;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtUserEmail = (EditText) findViewById(R.id.txtUserEmail);
        txtUserPassword = (EditText) findViewById(R.id.txtPassword);
        txtConformPassword = (EditText) findViewById(R.id.txtConformPassword);
        profileImage = (CircleImageView) findViewById(R.id.profile_image);
        btnRedister = (Button) findViewById(R.id.btnSignUp);
        txtLogin = (TextView) findViewById(R.id.txtLogin);


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });


        btnRedister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCredentials();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data == null) {
                Toasty.error(SignUp.this, "Failed to select Picture", Toast.LENGTH_SHORT).show();
            } else {
                Uri selectedImage = data.getData();
                profileImage.setImageURI(selectedImage);

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        uploadImage();
                    }
                };
                Thread thread = new Thread(r);
                thread.start();

            }
        }
    }


    public void signUp() {

        closeKeyboard();

        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Signing Up");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(SignUp.this);
        StringRequest request = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Toasty.success(SignUp.this, "Registered \n Login Now", Toast.LENGTH_SHORT).show();
                txtUserName.setText("");
                txtUserEmail.setText("");
                txtUserPassword.setText("");
                txtConformPassword.setText("");
                profileImage.setImageResource(R.drawable.man_user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                ConnectivityManager manager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=manager.getActiveNetworkInfo();
                if (networkInfo==null){
                    Toasty.error(SignUp.this,"Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toasty.error(SignUp.this,"Server Error",Toast.LENGTH_SHORT).show();
                }

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Convert String into Base64
//                final Bitmap image = ((BitmapDrawable) profileImage.getDrawable()).getBitmap();
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                Map<String, String> params = new HashMap<String, String>();
                params.put("userFullName", txtUserName.getText().toString());
                params.put("userEmail", txtUserEmail.getText().toString());
                params.put("userPassword", txtUserPassword.getText().toString());
                //params.put("image",encodedImage);

                return params;


            }
        };
        queue.add(request);

    }

    public void validateCredentials() {

        if (txtUserName.getText().toString().equals("")) {
            txtUserName.setError("User Name is Required");

        } else if (txtUserEmail.getText().toString().equals("")) {
            txtUserEmail.setError("Email is required!");

        } else if (txtUserPassword.getText().toString().equals("")) {
            txtUserPassword.setError("Password is required!");

        } else if (!txtUserPassword.getText().toString().equals(txtConformPassword.getText().toString())) {
            txtUserPassword.setText(null);
            txtConformPassword.setText(null);
            Toasty.error(SignUp.this, "passwords do not match", Toast.LENGTH_SHORT).show();

        } else if (!isValidEmail(txtUserEmail.getText().toString())) {
            txtUserEmail.setError("Invalid Email!");
            txtUserEmail.setText(null);

        } else {
            signUp();
        }


    }

    public final static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void uploadImage() {

        StringRequest request = new StringRequest(
                Request.Method.POST, imageUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                //Encode Image into Base64 String
                final Bitmap image = ((BitmapDrawable) profileImage.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                Map<String, String> params = new HashMap<String, String>();
                params.put("image", encodedImage);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(SignUp.this);
        queue.add(request);

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}








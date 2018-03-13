package com.example.asadfiaz.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class Login extends AppCompatActivity {

    public static final String SERVER_URL = "https://universityfyp2017.000webhostapp.com/";
    public static final String API_URL = "http://www.omdbapi.com/?t=";
    EditText txtUserEmail, txtUserPassword;
    Button login, signUpNow;
    TextView txtLogin;
    public static String userEmail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        signUpNow = (Button) findViewById(R.id.btnSignUpNow);
        login = (Button) findViewById(R.id.btnLogin);
        txtUserEmail = (EditText) findViewById(R.id.txtUserEmail);
        txtUserPassword = (EditText) findViewById(R.id.txtPassword);
        txtLogin = (TextView) findViewById(R.id.txtLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login(v);
                closeKeyboard();
            }
        });

        signUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);

                //Close Keyboard
                closeKeyboard();
            }
        });

    }


    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void login(View view) {


        userEmail = txtUserEmail.getText().toString();
        String loginScript = "https://universityfyp2017.000webhostapp.com/login.php";

        if (txtUserEmail.getText().toString().equals("") || txtUserPassword.getText().toString().equals("")) {
            Toasty.error(Login.this, "Enter All Fields", Toast.LENGTH_LONG).show();
        }
        else  {
            StringRequest request = new StringRequest(
                    Request.Method.POST, loginScript, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equalsIgnoreCase("Data Matched")) {
                        Toasty.success(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);
                    } else {
                        Toasty.error(Login.this, "Please Enter Valid Fields", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    ConnectivityManager manager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo=manager.getActiveNetworkInfo();
                    if (networkInfo==null){
                        Toasty.error(Login.this,"Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toasty.error(Login.this,"Server Error",Toast.LENGTH_SHORT).show();
                    }

                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userEmail", txtUserEmail.getText().toString());
                    params.put("userPassword", txtUserPassword.getText().toString());

                    return params;


                }
            };
            RequestQueue queue = Volley.newRequestQueue(Login.this);
            queue.add(request);
        }

    }


}

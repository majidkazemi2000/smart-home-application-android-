package com.example;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.relay.R;

public class loginActivity extends AppCompatActivity {


    RelativeLayout root;
    EditText address;
    EditText password;
    Button login;
    String add,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);




        root = findViewById(R.id.root);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);


        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        root.startAnimation(anim);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add = address.getText().toString();
                pass = password.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(loginActivity.this);
                String url = "http://"+add+"/?ip="+add+"&password="+pass;
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("ok")){
                            Intent intent = new Intent(loginActivity.this,MainActivity.class);
                            intent.putExtra("ip",add);
                            ActivityOptions options =
                                    ActivityOptions.makeCustomAnimation(loginActivity.this, R.anim.fade_in, R.anim.fade_out);
                            startActivity(intent, options.toBundle());
                        }else if(response.equals("error")){
                            Toast.makeText(loginActivity.this,"PASSWORD IS NOT CORRECT .",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(loginActivity.this,"PLEASE RETRY 1.",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(loginActivity.this,"PLEASE RETRY 2.",Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(request);

            }
        });
    }


}

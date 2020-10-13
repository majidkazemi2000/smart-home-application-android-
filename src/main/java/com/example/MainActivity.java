package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.relay.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout lamp1;
    LinearLayout lamp2;
    ImageButton voice;
    RequestQueue queue;
    StringRequest request;
    String url;
    String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        queue = Volley.newRequestQueue(this);

        lamp1 = findViewById(R.id.lamp1);
        lamp2 = findViewById(R.id.lamp2);
        voice = findViewById(R.id.voice);


        lamp1.setOnClickListener(this);
        lamp2.setOnClickListener(this);
        voice.setOnClickListener(this);

        ip  = getIntent().getStringExtra("ip");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lamp1:
                url = "http://"+ip+"/lamp?number=1";
                request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(request);
                break;
            case R.id.lamp2:
                url = "http://"+ip+"/lamp?number=2";
                request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(request);
                break;
            case R.id.voice:
                voice();
                break;
            default:
                break;
        }

    }

    private void voice() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "صحبت کنید");
        startActivityForResult(i, 1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            ArrayList< String > result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (result.equals("1")){
                url = "http://"+ip+"/voice?command=1";

            }else if(result.equals("2")){
                url = "http://"+ip+"/voice?number=2";

            }else if(result.equals("sleep")){
                url = "http://"+ip+"/voice?command=sleep";

            }else{
                Toast.makeText(getApplicationContext(),"WRONG COMMAND .", Toast.LENGTH_LONG).show();
            }

            request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();
                }
            });
            queue.add(request);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

package com.payvand.jahandideh.payvand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.Map;

public class NewPayam extends AppCompatActivity {
private EditText mapayam;
private EditText mopayam;
private EditText recive;
    private Button buttonRegister;
    public static final String KEY_Mopayam = "mopayam";
    public static final String KEY_Mapayam = "mapayam";
    public static final String KEY_recive = "recive";
    public static final String KEY_ersal = "ersal";
    private GoogleApiClient client;
    String SetData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_payam);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mapayam=(EditText)findViewById(R.id.mapaym);
        mopayam=(EditText)findViewById(R.id.mopaym);
        recive=(EditText)findViewById(R.id.payamrecive);
        buttonRegister=(Button)findViewById(R.id.btn_register_newpayam);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerChat();
                mapayam.setText("");
                mopayam.setText("");
                recive.setText("");
            }
        });
    }

    private void registerChat() {
        final String nn = mapayam.getText().toString();
        final String mn = mopayam.getText().toString();
        final String reciv = recive.getText().toString();
        Bundle b = getIntent().getExtras();
        SetData = b.getString("username");



        StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.PAYAM_NEW_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent viewActivity = new Intent(NewPayam.this, SentPayam.class);
                        Bundle b = getIntent().getExtras();
                        SetData = b.getString("username");
                        Bundle bd = new Bundle();
                        bd.putString("username", SetData);
                        viewActivity.putExtras(bd);

                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewPayam.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_Mapayam, nn);
                params.put(KEY_Mopayam, mn);
                params.put(KEY_recive, reciv);

                params.put(KEY_ersal, SetData);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}

package com.payvand.jahandideh.payvand;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.payvand.jahandideh.payvand.Config.TAG_Lname;
import static com.payvand.jahandideh.payvand.Config.TAG_MANAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_MNAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_NAME;
import static com.payvand.jahandideh.payvand.Config.TAG_ersal;


public class Nameh extends AppCompatActivity {

    private TextView mnameh;
    private TextView manameh;
    private TextView ersal;
    private GoogleApiClient client;
    String SetData;
    String recive;
    String name,lname,nlname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nameh);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mnameh=(TextView)findViewById(R.id.textmnameh);
        manameh=(TextView)findViewById(R.id.textmanameh);
        ersal=(TextView)findViewById(R.id.textersal);
        getData();


    }



    private void getData() {
        final ProgressDialog loading = ProgressDialog.show(this, "در حال دریافت اطلاعات...", "لطفا منتظر بمانید", false, false);
        final StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.NAMEH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONArray jsonArray = jo.getJSONArray(Config.TAG_NAMEHS);
                            loading.dismiss();
                            parseData(jsonArray);
                        } catch (JSONException e) {
                            Log.i("matis", "error in nameh jsonobject()-->" + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(Nameh.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Bundle b = getIntent().getExtras();
                SetData = b.getString("id");
                params.put("user", SetData);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonobjectRequest);
    }

    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {

            JSONObject json = null;

            try {
                json = array.getJSONObject(i);
                mnameh.setText(json.getString(TAG_MNAMEH));
                manameh.setText(json.getString(TAG_MANAMEH));
                recive=json.getString(TAG_ersal);
                final StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.USERI_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jo = new JSONObject(response);
                                    JSONArray array = jo.getJSONArray(Config.TAG_User);
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject json = null;
                                        try {
                                            json = array.getJSONObject(i);
                                            name=json.getString(TAG_NAME);
                                            lname=json.getString(TAG_Lname);

                                            nlname=""+name+" "+lname;
                                            ersal.setText(nlname);
                                        } catch (JSONException e) {
                                            Log.i("matis", "error in nameh parseuser()-->" + e.toString());
                                        }


                                    }
                                } catch (JSONException e) {
                                    Log.i("matis", "error in getuser jsonobject()-->" + e.toString());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Nameh.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", recive);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(jsonobjectRequest);



            } catch (JSONException e) {
                Log.i("matis", "error in nameh paredata parsedata()-->"+recive+ e.toString());
            }


        }

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//دستور رفتن از صفحه حاضر به صفحه دلخواه

            this.overridePendingTransition(R.anim.slide_l,
                    R.anim.slide_r);
            finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
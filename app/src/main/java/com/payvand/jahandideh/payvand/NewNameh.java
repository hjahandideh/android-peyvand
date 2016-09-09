package com.payvand.jahandideh.payvand;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.payvand.jahandideh.payvand.Config.NAMEH_NEW_URL;
import static com.payvand.jahandideh.payvand.Config.TAG_ID;

public class NewNameh extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_NNAMEH = "nnameh";
    public static final String KEY_MNAMEH = "mnameh";
    public static final String KEY_MANAMEH = "manameh";
    public static final String KEY_ERJAH = "jahat";
    public static final String KEY_ERJA = "erjah";
    public static final String KEY_mte = "tmeghhdam";
    public static final String KEY_ERSAL = "ersal";
    public static final String KEY_EGHDAM = "eghdam";
    public static final String KEY_TERSAL = "tersal";
    public static final String KEY_ST = "st";
    public static final String KEY_TAYEED = "tayeed";
    String SetData;
    String date;
    private EditText nnameh;
    private EditText mnameh;
    private EditText manameh;
    public static TextView tv;
    private  String count;
    public static String usern;
    private List<ErjahList> listerjah;
    private Button buttonRegister;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnameh);
        tv = (TextView) findViewById(R.id.textView18);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_user);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        nnameh = (EditText) findViewById(R.id.nnameh);
        mnameh = (EditText) findViewById(R.id.mnameh);
        manameh = (EditText) findViewById(R.id.manameh);
        listerjah = new ArrayList<>();
        buttonRegister = (Button) findViewById(R.id.btn_sabt);
        buttonRegister.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getData();
        Bundle b = getIntent().getExtras();
        SetData = b.getString("username");
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        getcount();
    }
    private void getcount() {
        final StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.COUNT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONArray jsonArray = jo.getJSONArray(Config.TAG_NAMEHS);
                            parsecount(jsonArray);
                        } catch (JSONException e) {
                            Log.i("matis", "error in newnameh jsonobject()-->" + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewNameh.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonobjectRequest);
    }

    private void parsecount(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                count=json.getString(TAG_ID);



            } catch (JSONException e) {
                Log.i("matis", "error in Recive_payam parsedata()-->" + e.toString());
            }

        }
    }
    private void getData() {

        final StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.ERJAH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONArray jsonArray = jo.getJSONArray(Config.TAG_NAMEHS);
                            parseData(jsonArray);
                        } catch (JSONException e) {

                            Log.i("matis", "error in newnameh jsonobject()-->" + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewNameh.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonobjectRequest);
    }

    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            ErjahList erjahList = new ErjahList();
            JSONObject json = null;
            ArrayList<ErjahList> powers = new ArrayList<ErjahList>();
            try {
                json = array.getJSONObject(i);
                erjahList.seterjah(json.getString(KEY_ERJA));

                powers.add(erjahList);


            } catch (JSONException e) {
                Log.i("matis", "error in Recive_payam parsedata()-->" + e.toString());
            }
            erjahList.setErjahs(powers);
            listerjah.add(erjahList);
            adapter = new ErjahAdapter(listerjah);
            recyclerView.setAdapter(adapter);
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void registerUser() {
        final String nn = nnameh.getText().toString();
        final String mn = mnameh.getText().toString();
        final String man = manameh.getText().toString();
        final String r = tv.getText().toString();
        final String e = SetData;
        final String t = date;
        final String ter = "00-00-0000";
        final String s = "0";
        final String ta="";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NAMEH_NEW_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewNameh.this, "خطا در ثبت اطلاعات", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_NNAMEH, nn);
                params.put(KEY_MNAMEH, mn);
                params.put(KEY_MANAMEH, man);
                params.put(KEY_ERJAH, r);
                params.put(KEY_ERSAL, e);
                params.put(KEY_TERSAL, t);
                params.put(KEY_mte, ter);
                params.put(KEY_ST, s);
                params.put(KEY_TAYEED, ta);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
                if (v == buttonRegister) {
                    registerUser();
                    nnameh.setText("");
                    mnameh.setText("");
                    manameh.setText("");
                    tv.setText("");
                    usern="";
                    Intent viewActivity = new Intent(this, Reciver.class);
                    Bundle bd = new Bundle();
                    bd.putString("username",count);
                    viewActivity.putExtras(bd);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(this, R.anim.ani1, R.anim.anim2).toBundle();
                    startActivity(viewActivity, bndlanimation);
                    finish();
            }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
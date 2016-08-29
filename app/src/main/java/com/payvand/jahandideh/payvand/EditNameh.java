package com.payvand.jahandideh.payvand;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.payvand.jahandideh.payvand.Config.Edit_Nameh_URL;
import static com.payvand.jahandideh.payvand.Config.TAG_Lname;
import static com.payvand.jahandideh.payvand.Config.TAG_MANAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_MNAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_NAME;
import static com.payvand.jahandideh.payvand.Config.TAG_NNAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_TERSAL;
import static com.payvand.jahandideh.payvand.Config.TAG_Username;
import static com.payvand.jahandideh.payvand.Config.TAG_ersal;

public class EditNameh extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_NNAMEH = "nnameh";
    public static final String KEY_MNAMEH = "mnameh";
    public static final String KEY_MANAMEH = "manameh";
    public static final String KEY_RECIVE = "recive";
    public static final String KEY_ERSAL = "ersal";
    public static final String KEY_TERSAL = "tersal";
    public static final String KEY_ID = "id";

    String SetData;
    private EditText nnameh;
    private EditText mnameh;
    private EditText manameh;
    public static TextView tv;
    public static String usere;
    String userer;
    private EditText tersal;
    private List<Userlist> listuser;
    private Button buttonRegister;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nameh);
        tv=(TextView)findViewById(R.id.textgetrec);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_user_edit);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        nnameh = (EditText) findViewById(R.id.nn);
        mnameh = (EditText) findViewById(R.id.mn);
        manameh = (EditText) findViewById(R.id.man);
        listuser=new ArrayList<>();
        tersal = (EditText) findViewById(R.id.tersali);
        buttonRegister = (Button) findViewById(R.id.btn_sabti);
        buttonRegister.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        getData();
        setData();
        Bundle db = getIntent().getExtras();
        SetData = db.getString("id");
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
                            setparseData(jsonArray);
                        } catch (JSONException e) {
                            Log.i("matis", "error in nameh jsonobject()-->" + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(EditNameh.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user", SetData);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonobjectRequest);
    }

    private void setparseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                mnameh.setText(json.getString(TAG_MNAMEH));
                nnameh.setText(json.getString(TAG_NNAMEH));
                manameh.setText(json.getString(TAG_MANAMEH));
                tersal.setText(json.getString(TAG_TERSAL));
                userer=json.getString(TAG_ersal);
            } catch (JSONException e) {
                Log.i("matis", "error in nameh paredata parsedata()-->"+ e.toString());
            }
        }
    }

    private void setData() {
        final StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONArray jsonArray = jo.getJSONArray(Config.TAG_User);
                            parseData(jsonArray);
                        } catch (JSONException e) {
                            Log.i("matis", "error in newnameh jsonobject()-->" + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditNameh.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonobjectRequest);
    }

    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            Userlist userlist = new Userlist();
            JSONObject json = null;
            ArrayList<Userlist> powers = new ArrayList<Userlist>();
            try {
                json = array.getJSONObject(i);
                userlist.setUsername(json.getString(TAG_Username));
                userlist.setname(json.getString(TAG_NAME));
                userlist.setLname(json.getString(TAG_Lname));
                powers.add(userlist);
            } catch (JSONException e) {
                Log.i("matis", "error in Recive_payam parsedata()-->" + e.toString());
            }
            userlist.setuser(powers);
            listuser.add(userlist);
            adapter = new UsereAdapter(listuser);
            recyclerView.setAdapter(adapter);
        }
    }
    private void registerUser() {
        final String id=SetData;
        final String nn = nnameh.getText().toString();
        final String mn = mnameh.getText().toString();
        final String man = manameh.getText().toString();
        final String r = usere;
        final String e = userer;
        final String t = tersal.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Edit_Nameh_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(EditNameh.this, "نامه ویرایش    شد", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditNameh.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ID, id);
                params.put(KEY_NNAMEH, nn);
                params.put(KEY_MNAMEH, mn);
                params.put(KEY_MANAMEH, man);
                params.put(KEY_RECIVE, r);
                params.put(KEY_ERSAL, e);
                params.put(KEY_TERSAL, t);
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
            tersal.setText("");

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
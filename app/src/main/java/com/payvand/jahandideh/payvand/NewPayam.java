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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.payvand.jahandideh.payvand.Config.TAG_Lname;
import static com.payvand.jahandideh.payvand.Config.TAG_NAME;
import static com.payvand.jahandideh.payvand.Config.TAG_Username;

public class NewPayam extends AppCompatActivity {
    private List<Userlist> listuser;
    private EditText mapayam;
    private EditText mopayam;
    public static TextView recive;
    public static String recie;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Button buttonRegister;
    public static final String KEY_Mopayam = "mopayam";
    public static final String KEY_Mapayam = "mapayam";
    public static final String KEY_recive = "recive";
    public static final String KEY_ersal = "ersal";
    String SetData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_payam);
        Bundle b = getIntent().getExtras();
        SetData = b.getString("username");
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        listuser=new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_useri);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mapayam=(EditText)findViewById(R.id.mapayam);
        mopayam=(EditText)findViewById(R.id.mopayam);
        recive=(TextView)findViewById(R.id.textView18);
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
        getData();
    }
    private void getData() {
        final ProgressDialog loading = ProgressDialog.show(this, "در حال دریافت اطلاعات...", "لطفا منتظر بمانید", false, false);
        final StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONArray jsonArray = jo.getJSONArray(Config.TAG_User);
                            loading.dismiss();
                            parseData(jsonArray);
                        } catch (JSONException e) {
                            Log.i("matis", "error in newnameh jsonobject()-->" + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(NewPayam.this, error.toString(), Toast.LENGTH_LONG).show();
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
            adapter = new UseriAdapter(listuser);
            recyclerView.setAdapter(adapter);
        }
    }
    private void registerChat() {
        final String nn = mapayam.getText().toString();
        final String mn = mopayam.getText().toString();
        final String reciv = recie;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.PAYAM_NEW_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     Toast.makeText(NewPayam.this, "پیام فرستاده شد.", Toast.LENGTH_LONG).show();
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

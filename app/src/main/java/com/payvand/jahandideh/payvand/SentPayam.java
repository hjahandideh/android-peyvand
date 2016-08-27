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

import static com.payvand.jahandideh.payvand.Config.TAG_Lname;
import static com.payvand.jahandideh.payvand.Config.TAG_NAME;
import static com.payvand.jahandideh.payvand.Config.TAG_RECIVE;
import static com.payvand.jahandideh.payvand.Config.TAG_mopayam;



public class SentPayam extends AppCompatActivity {

    private List<SuperHeroes> listSuperHeroes;
    private RecyclerView recyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private GoogleApiClient client;
    String SetData;
    String name,lname,nlname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentpayam);
        recyclerview = (RecyclerView) findViewById(R.id.my_recycler_view_sentpayam);
        assert recyclerview != null;
        recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        listSuperHeroes = new ArrayList<>();
        getData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    private void getData() {
        final ProgressDialog loading = ProgressDialog.show(this, "در حال دریافت اطلاعات...", "لطفا منتظر بمانید", false, false);
        final StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.PAYAM_ERSAL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONArray jsonArray = jo.getJSONArray(Config.TAG_Payam);
                            loading.dismiss();
                            parseData(jsonArray);
                        } catch (JSONException e) {
                            Log.i("matis", "error in Sent_payam jsonobject()-->" + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(SentPayam.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Bundle b = getIntent().getExtras();
                SetData = b.getString("username");
                params.put("user", SetData);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonobjectRequest);
    }

    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            final SuperHeroes superHero = new SuperHeroes();
            JSONObject json = null;
            ArrayList<SuperHeroes> powers = new ArrayList<SuperHeroes>();
            try {
                json = array.getJSONObject(i);
                superHero.setMopayam(json.getString(TAG_mopayam));

                final String recive=(json.getString(TAG_RECIVE));
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
                                            superHero.setErsal(nlname);
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
                                Toast.makeText(SentPayam.this, error.toString(), Toast.LENGTH_LONG).show();
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
                powers.add(superHero);


            } catch (JSONException e) {
                Log.i("matis", "error in Sent_payam parsedata()-->" + e.toString());
            }
            superHero.setPayams(powers);
            listSuperHeroes.add(superHero);


            //Finally initializing our adapter
            adapter = new CardAdapter(listSuperHeroes);

            //Adding adapter to recyclerview
            recyclerview.setAdapter(adapter);

        }

    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            this.overridePendingTransition(R.anim.slide_l,
                    R.anim.slide_r);
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
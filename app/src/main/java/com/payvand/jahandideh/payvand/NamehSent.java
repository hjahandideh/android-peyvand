package com.payvand.jahandideh.payvand;

import android.app.ProgressDialog;
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

import static com.payvand.jahandideh.payvand.Config.TAG_MANAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_MNAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_RECIVE;

public class NamehSent extends AppCompatActivity {
    private GoogleApiClient client;
    private List<NamehParse> listNamehRecive;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    String SetData;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ersal);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_ersalname);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listNamehRecive = new ArrayList<>();
        getData();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    private void getData() {

        final ProgressDialog loading = ProgressDialog.show(this, "در حال دریافت اطلاعات...", "لطفا منتظر بمانید", false, false);


        StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.NAMEH_ERSAL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo=new JSONObject(response);
                            JSONArray jsonArray = jo.getJSONArray(Config.TAG_NAMEHS);
                            loading.dismiss();
                            parseData(jsonArray);
                        } catch (JSONException e) {
                            Log.i("matis", "error in Sent jsonobject()-->"+response + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(NamehSent.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
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
            NamehParse namehParse = new NamehParse();
            JSONObject json = null;
            ArrayList<NamehParse> powers = new ArrayList<NamehParse>();
            try {
                json = array.getJSONObject(i);
                namehParse.setNnameh(json.getString(TAG_MANAMEH));
                namehParse.setMnameh(json.getString(TAG_MNAMEH));
                namehParse.setRecive(json.getString(TAG_RECIVE));
                powers.add(namehParse);



            } catch (JSONException e) {
                Log.i("matis", "error in Sent parsedata()-->" + e.toString());
            }
            namehParse.setNamehs(powers);
            listNamehRecive.add(namehParse);


            //Finally initializing our adapter
            adapter = new NamehAdapter(listNamehRecive,this);

            //Adding adapter to recyclerview
            recyclerView.setAdapter(adapter);

        }

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


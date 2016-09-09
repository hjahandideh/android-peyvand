package com.payvand.jahandideh.payvand;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import static com.payvand.jahandideh.payvand.Config.TAG_ID;
import static com.payvand.jahandideh.payvand.Config.TAG_Lname;
import static com.payvand.jahandideh.payvand.Config.TAG_MANAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_MNAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_NAME;
import static com.payvand.jahandideh.payvand.Config.TAG_ST;
import static com.payvand.jahandideh.payvand.Config.TAG_TERSAL;
import static com.payvand.jahandideh.payvand.Config.TAG_ersal;

public class Baygani extends AppCompatActivity {
    private List<NamehParse> listNamehRecive;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    String name, lname;
    String SetData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baygani);

        Bundle b = getIntent().getExtras();
        SetData = b.getString("username");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_baygani);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listNamehRecive = new ArrayList<>();
        getData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void getData() {

        final ProgressDialog loading = ProgressDialog.show(this, "در حال دریافت اطلاعات", "لطفا منتظر بمانید...", false, false);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.NAMEH_BAYEGANI_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            loading.dismiss();
                            JSONObject jo=new JSONObject(response);
                            JSONArray jsonArray = jo.getJSONArray(Config.TAG_NAMEHS);
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
                        Toast.makeText(Baygani.this, "خطا در دریافت اطلاعات", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", SetData);
                return params;
            }
        };
        requestQueue.add(jsonobjectRequest);
    }

    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            final NamehParse namehParse = new NamehParse();
            JSONObject json = null;
            ArrayList<NamehParse> powers = new ArrayList<NamehParse>();
            try {
                json = array.getJSONObject(i);
                namehParse.setNnameh(json.getString(TAG_MNAMEH));
                namehParse.setname(json.getString(TAG_NAME));
                namehParse.setlname(json.getString(TAG_Lname));
                namehParse.setId(json.getString(TAG_ID));
                String de=json.getString(TAG_TERSAL);
                String[] dated=de.split("-");
                Roozh roozh =new Roozh();
                int year=Integer.parseInt(dated[0]);
                int month=Integer.parseInt(dated[1]);
                int day=Integer.parseInt(dated[2]);
                roozh.GregorianToPersian(year,month,day);
                namehParse.setMnameh(roozh.toString());
                namehParse.setImageUrl("http://bemq.ir/image/"+json.getString(TAG_ST)+".png");
                namehParse.setersal(json.getString(TAG_ersal));
                powers.add(namehParse);
            } catch (JSONException e) {
                Log.i("matis", "error in Sent parsedata()-->" + e.toString());
            }
            namehParse.setNamehs(powers);
            listNamehRecive.add(namehParse);
            adapter = new NamehAdapter(listNamehRecive,this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}


package com.payvand.jahandideh.payvand;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
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
import static com.payvand.jahandideh.payvand.Config.TAG_ersal;

public class Peyvand extends AppCompatActivity {
    private List<NamehParse> listNamehRecive;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private CustomView imageeView;
    private ImageLoader imageLoader;
    String name, lname;
    String SetData;
    DatabaseHelper mydb;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peyvand);
        mydb = new DatabaseHelper(this);
        Cursor res = mydb.showdata();
        StringBuffer data = new StringBuffer();
        while (res.moveToNext()) {
            data.append(res.getString(1) + "");
        }
        SetData = data.toString();
        imageeView = (CustomView) findViewById(R.id.g);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listNamehRecive = new ArrayList<>();
        getData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Navigation_drawer my_nav = (Navigation_drawer)
                getSupportFragmentManager().findFragmentById(R.id.fnd);
        my_nav.setup((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        loadImage();
        expListView = (ExpandableListView) findViewById(R.id.exp_list);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataHeader.add("نامه");
        listDataHeader.add("پیام");
        listDataHeader.add("گفتگو کاربران");
        listDataHeader.add("خروج");

        List<String> nameh = new ArrayList<String>();
        nameh.add("جدید");
        nameh.add("ارسال شده ها");
        nameh.add("پیش نویس");
        List<String> payam = new ArrayList<String>();
        payam.add("پیام جدید");
        payam.add("دریافت");
        payam.add("ارسال شده");

        listDataChild.put(listDataHeader.get(0), nameh); // Header, Child data
        listDataChild.put(listDataHeader.get(1), payam);
    }

    private void loadImage() {
        try {
            String url = "http://bemq.ir/image/" + SetData + ".jpg";
            imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(imageeView, R.drawable.user, android.R.drawable.ic_dialog_alert));
            imageeView.setImageUrl(url, imageLoader);
        } catch (Exception e) {
            Log.i("matis", "error in Peyvand loadimage()-->" + e.toString());
        }
    }

    private void getData() {

        final ProgressDialog loading = ProgressDialog.show(this, "در حال دریافت اطلاعات", "لطفا منتظر بمانید...", false, false);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.NAMEH_RECIVE_URL,
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
                        Toast.makeText(Peyvand.this, "خطا در دریافت اطلاعات", Toast.LENGTH_LONG).show();
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
                namehParse.setNnameh(json.getString(TAG_MANAMEH));
                namehParse.setMnameh(json.getString(TAG_MNAMEH));
                namehParse.setname(json.getString(TAG_NAME));
                namehParse.setlname(json.getString(TAG_Lname));
                namehParse.setId(json.getString(TAG_ID));
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


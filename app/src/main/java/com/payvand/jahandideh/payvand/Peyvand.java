package com.payvand.jahandideh.payvand;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
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

import static com.payvand.jahandideh.payvand.Config.TAG_ID;
import static com.payvand.jahandideh.payvand.Config.TAG_Lname;
import static com.payvand.jahandideh.payvand.Config.TAG_MANAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_MNAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_NAME;
import static com.payvand.jahandideh.payvand.Config.TAG_ersal;

public class Peyvand extends AppCompatActivity {
    private GoogleApiClient client;
    private List<NamehParse> listNamehRecive;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private CustomView imageeView;
    private ImageLoader imageLoader;
    private Button btnsentnameh,slideButton,b1,b2,b3,b4;
    private Button slidebutton,b11,b12,b13,b14;
    private SlidingDrawer slidingDrawer;
    private SlidingDrawer slidingDrawere;
    String SetDat, name, lname, nlname;
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
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    /*
     * Preparing the list data
     */
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

            imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(imageeView,
                    R.drawable.user, android.R.drawable.ic_dialog_alert));
            imageeView.setImageUrl(url, imageLoader);
        } catch (Exception e) {
            Log.i("matis", "error in Peyvand loadimage()-->" + e.toString());
        }
    }

    private void getData() {


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.NAMEH_RECIVE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
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
                        Toast.makeText(Peyvand.this, error.toString(), Toast.LENGTH_LONG).show();
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
                final String recive=(json.getString(TAG_ersal));
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
                                            namehParse.setRecive(nlname);
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
                                Toast.makeText(Peyvand.this, error.toString(), Toast.LENGTH_LONG).show();
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
                namehParse.setId(json.getString(TAG_ID));
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
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Peyvand Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.payvand.jahandideh.payvand/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Peyvand Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.payvand.jahandideh.payvand/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}


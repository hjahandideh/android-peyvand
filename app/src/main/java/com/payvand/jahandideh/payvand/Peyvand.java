package com.payvand.jahandideh.payvand;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import static com.payvand.jahandideh.payvand.Config.TAG_MANAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_MNAMEH;
import static com.payvand.jahandideh.payvand.Config.TAG_ersal;

public class Peyvand extends AppCompatActivity {
    private GoogleApiClient client;
    private List<NamehParse> listNamehRecive;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private CustomView imageeView;
    private ImageLoader imageLoader;
    String SetData, SetDat, name, lname, nlname;
    DatabaseHelper mydb;

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

        Button btnexit = (Button) findViewById(R.id.btnexit);
        assert btnexit != null;
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean f = mydb.deletdata(SetData);
                if (f == true) {
                    Intent viewActivity = new Intent(Peyvand.this, MainActivity.class);
                    startActivity(viewActivity);
                    finish();
                } else
                    Toast.makeText(Peyvand.this, "no", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnnew = (Button) findViewById(R.id.button3);
        assert btnnew != null;
        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent viewActivity = new Intent(Peyvand.this, NewNameh.class);

                Bundle bd = new Bundle();
                bd.putString("newnameh", SetData);
                viewActivity.putExtras(bd);
                startActivity(viewActivity);

            }
        });

        Button btnnewpayam = (Button) findViewById(R.id.btnnewpayam);
        assert btnnewpayam != null;
        btnnewpayam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewActivity = new Intent(Peyvand.this, NewPayam.class);
                Bundle bd = new Bundle();
                bd.putString("username", SetData);
                viewActivity.putExtras(bd);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_l, R.anim.slide_r).toBundle();
                startActivity(viewActivity, bndlanimation);
            }
        });

        Button btnrpayam = (Button) findViewById(R.id.button4);
        assert btnrpayam != null;
        btnrpayam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewActivity = new Intent(Peyvand.this, Recive_Payam.class);
                Bundle bd = new Bundle();
                bd.putString("recive", SetData);
                viewActivity.putExtras(bd);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_l, R.anim.slide_r).toBundle();
                startActivity(viewActivity, bndlanimation);
            }
        });


        Button btnsentnameh = (Button) findViewById(R.id.btnsent);
        assert btnsentnameh != null;
        btnsentnameh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewActivity = new Intent(Peyvand.this, NamehSent.class);
                Bundle bd = new Bundle();
                bd.putString("username", SetData);
                viewActivity.putExtras(bd);
                startActivity(viewActivity);
            }
        });

        Button btnsent = (Button) findViewById(R.id.btnsentpayam);
        assert btnsent != null;
        btnsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewActivity = new Intent(Peyvand.this, SentPayam.class);
                Bundle bd = new Bundle();
                bd.putString("username", SetData);
                viewActivity.putExtras(bd);
                startActivity(viewActivity);
            }
        });

        Button btnpishnevis = (Button) findViewById(R.id.btnpishnevis);
        assert btnpishnevis != null;
        btnpishnevis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewActivity = new Intent(Peyvand.this, Pishnevis.class);
                startActivity(viewActivity);
            }
        });

        Button btnchat = (Button) findViewById(R.id.btnchat);
        assert btnchat != null;
        btnchat.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent viewActivity = new Intent(Peyvand.this, Chat.class);
                Bundle bd = new Bundle();
                bd.putString("chat", SetData);
                viewActivity.putExtras(bd);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ani1, R.anim.anim2).toBundle();
                startActivity(viewActivity, bndlanimation);

            }
        });

        Navigation_drawer my_nav = (Navigation_drawer)
                getSupportFragmentManager().findFragmentById(R.id.fnd);
        my_nav.setup((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        loadImage();
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

        StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.NAMEH_ERSAL_URL,
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
            NamehParse namehParse = new NamehParse();
            JSONObject json = null;
            ArrayList<NamehParse> powers = new ArrayList<NamehParse>();
            try {
                json = array.getJSONObject(i);
                namehParse.setNnameh(json.getString(TAG_MANAMEH));
                namehParse.setMnameh(json.getString(TAG_MNAMEH));
                namehParse.setRecive(json.getString(TAG_ersal));
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


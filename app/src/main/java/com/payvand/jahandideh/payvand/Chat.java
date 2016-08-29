package com.payvand.jahandideh.payvand;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
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

import static com.payvand.jahandideh.payvand.Config.TAG_MAPayam;

public class Chat extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_Name = "name";
    public static final String KEY_MAPAYAM = "mapayam";
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private NetworkImageView imageView;
    private ImageLoader imageLoader;
    private List<Chatlist> listchat;
    private RecyclerView recyclerView;
    String SetData;
    private EditText mapayam;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listchat = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mapayam = (EditText) findViewById(R.id.txtchat);
        buttonRegister = (Button) findViewById(R.id.button6);
        buttonRegister.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void registerChat() {
        final String nn = mapayam.getText().toString();
        Bundle b = getIntent().getExtras();
        SetData = b.getString("chat");
        final String e = "http://bemq.ir/image/" + SetData + ".jpg";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.INSERT_CHAT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Chat.this, "", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Chat.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_MAPAYAM, nn);
                params.put(KEY_Name, e);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerChat();
            mapayam.setText("");
            getData();
        }
    }

    private void getData() {
        StringRequest jsonobjectRequest = new StringRequest(Request.Method.POST, Config.Chat_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONArray jsonArray = jo.getJSONArray(Config.TAG_Paygham);
                            parseData(jsonArray);
                        } catch (JSONException e) {
                            Log.i("matis", "error in chat jsonobject()-->" + response + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Chat.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonobjectRequest);
    }

    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            Chatlist chatlist = new Chatlist();
            JSONObject json = null;
            ArrayList<Chatlist> powers = new ArrayList<Chatlist>();
            try {
                json = array.getJSONObject(i);
                chatlist.setMapayam(json.getString(TAG_MAPayam));
                chatlist.setImageUrl(json.getString(Config.TAG_NAME));
                powers.add(chatlist);
            } catch (JSONException e) {
                Log.i("matis", "error in chat parsedata()-->" + e.toString());
            }
            chatlist.setchats(powers);
            listchat.add(chatlist);
            adapter = new ChatAdapter(listchat);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(listchat.size());
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
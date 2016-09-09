package com.payvand.jahandideh.payvand;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.List;

import static com.payvand.jahandideh.payvand.Config.TAG_Lname;
import static com.payvand.jahandideh.payvand.Config.TAG_NAME;
import static com.payvand.jahandideh.payvand.Config.TAG_SEMAT;
import static com.payvand.jahandideh.payvand.Config.TAG_Username;

public class Reciver extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<Userlist> listuser;

    public static TextView selectuser;
    public static String usrnn;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciver);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_userselect);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Button btns = (Button) findViewById(R.id.btnanajam);
        assert btns != null;
        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectuser.setText("");
                Intent viewActivity = new Intent(Reciver.this, NamehSent.class);
                Bundle bd = new Bundle();
                viewActivity.putExtras(bd);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Reciver.this, R.anim.ani1, R.anim.anim2).toBundle();
                startActivity(viewActivity, bndlanimation);
                finish();
            }
        });
        listuser = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        usrnn = b.getString("username");
        selectuser = (TextView) findViewById(R.id.userselect);
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
                        Toast.makeText(Reciver.this, error.toString(), Toast.LENGTH_LONG).show();
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
                userlist.setsemat(json.getString(TAG_SEMAT));
                powers.add(userlist);


            } catch (JSONException e) {
                Log.i("matis", "error in Recive_payam parsedata()-->" + e.toString());
            }
            userlist.setuser(powers);
            listuser.add(userlist);
            adapter = new UserAdapter(listuser,this);
            recyclerView.setAdapter(adapter);
        }
    }


}

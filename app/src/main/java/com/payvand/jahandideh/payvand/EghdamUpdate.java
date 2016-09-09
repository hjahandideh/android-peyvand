package com.payvand.jahandideh.payvand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EghdamUpdate extends AppCompatActivity {
    String SetData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eghdam_update);
        Bundle b = getIntent().getExtras();
        SetData = b.getString("id");
        Button btntayeed=(Button)findViewById(R.id.eghdamupdate);
        btntayeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                Intent viewActivity = new Intent(EghdamUpdate.this, NamehSentView.class);
                Bundle bd=new Bundle();
                bd.putString("id",SetData);
                viewActivity.putExtras(bd);
                startActivity(viewActivity);
                finish();
            }
        });
    }
    private void registerUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.NAMEH_EDIT_EGHDAM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", SetData);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

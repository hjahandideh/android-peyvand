package com.payvand.jahandideh.payvand;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NamehAdapter extends RecyclerView.Adapter<NamehAdapter.ViewHolder>{

    private Context context;
    private ImageLoader imageLoader;
    String id;
    public List<NamehParse> namehParses;
    private NamehParse namehParse;
    public NamehAdapter(List<NamehParse> namehParses,Context context){
        super();
        this.namehParses = namehParses;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nameh_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        namehParse =  namehParses.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(namehParse.getImageUrl(), ImageLoader.getImageListener(holder.imgread, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        holder.imgread.setImageUrl(namehParse.getImageUrl(), imageLoader);
        holder.nnameh.setText(namehParse.getNnameh());
        holder.mnameh.setText(namehParse.getMnameh());

        holder.name.setText(namehParse.getname());
        holder.lname.setText(namehParse.getlname());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewActivity = new Intent(context.getApplicationContext(), Nameh.class);
                Bundle bd=new Bundle();

                namehParse =  namehParses.get(position);
                id=namehParse.getid();
                bd.putString("id",id);
                registerUser();
                viewActivity.putExtras(bd);
                context.startActivity(viewActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return namehParses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nnameh;
        public TextView mnameh;
        public TextView name;
        public  NetworkImageView imgread;
        public TextView lname;
        public CardView card_view;
        public ViewHolder(View itemView) {
            super(itemView);
            nnameh = (TextView) itemView.findViewById(R.id.textView3);
            mnameh = (TextView) itemView.findViewById(R.id.textView2);
            Typeface face = Typeface.createFromAsset(context.getAssets(),"fonts/droidnaskh_regular.ttf");
            mnameh.setTypeface(face);
            name = (TextView) itemView.findViewById(R.id.textView4);
            lname = (TextView) itemView.findViewById(R.id.textView7);
            imgread= (NetworkImageView) itemView.findViewById(R.id.imgread);
            card_view=(CardView)itemView.findViewById(R.id.card_view);
        }
    }
    private void registerUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.Edit_st_URL,
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
                params.put("id", id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
    }
}

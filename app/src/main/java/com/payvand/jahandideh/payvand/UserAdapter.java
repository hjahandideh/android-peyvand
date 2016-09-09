package com.payvand.jahandideh.payvand;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    String usernn;
    List<Userlist> userlists;
    public UserAdapter(List<Userlist> userlists,Context context){
        super();
        this.userlists = userlists;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Userlist userlist =  userlists.get(position);
        holder.name.setText(userlist.getname());
        holder.lname.setText(userlist.getLname());
        holder.semat.setText(userlist.getsemat());
        holder.crvuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Userlist userlist =  userlists.get(position);
                Reciver.selectuser.setText(Reciver.selectuser.getText()+" "+userlist.getname()+"  "+userlist.getLname());
                usernn=userlist.getUsername();
                registerUser();

            }
        });
    }
    public  void registerUser() {
        final String nn = Reciver.usrnn;
        final String mn = usernn;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.NEW_recive_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            public static final String KEY_namehid = "nameh_id";
            public static final String KEY_reciver = "reciver";

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_namehid, nn);
                params.put(KEY_reciver, mn);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return userlists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView lname;
        public TextView semat;

        public CardView crvuser;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView17);
            lname = (TextView) itemView.findViewById(R.id.textuser);
            semat = (TextView) itemView.findViewById(R.id.semat);
            crvuser = (CardView) itemView.findViewById(R.id.card_view_userlist);
        }
    }
}

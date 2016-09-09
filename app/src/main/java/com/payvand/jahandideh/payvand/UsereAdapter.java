package com.payvand.jahandideh.payvand;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UsereAdapter extends RecyclerView.Adapter<UsereAdapter.ViewHolder> {

    List<Userlist> userlists;
    public UsereAdapter(List<Userlist> userlists){
        super();
        this.userlists = userlists;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_user, parent, false);
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
                EditNameh.tv.setText(userlist.getname()+" "+userlist.getLname());
                EditNameh.usere=userlist.getUsername();
            }
        });
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

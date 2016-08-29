package com.payvand.jahandideh.payvand;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UseriAdapter extends RecyclerView.Adapter<UseriAdapter.ViewHolder> {

    List<Userlist> userlists;
    public UseriAdapter(List<Userlist> userlists){
        super();
        this.userlists = userlists;

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
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Userlist userlist =  userlists.get(position);
                NewPayam.recive.setText(userlist.getname()+" "+userlist.getLname());
                NewPayam.recie=userlist.getUsername();
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
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView17);
            lname = (TextView) itemView.findViewById(R.id.textuser);

        }
    }
}

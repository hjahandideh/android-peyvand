package com.payvand.jahandideh.payvand;

/**
 * Created by Jahandideh on 19/07/2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Belal on 11/9/2015.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {



    //List of superHeroes
    List<Userlist> userlists;

    public UserAdapter(List<Userlist> userlists){
        super();
        //Getting all the superheroes
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
        holder.username.setText(userlist.getUsername());
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Userlist userlist =  userlists.get(position);
                NewNameh.tv.setText(userlist.getUsername());
            }
        });
    }

    @Override
    public int getItemCount() {
        return userlists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;


        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.textuser);

        }
    }
}

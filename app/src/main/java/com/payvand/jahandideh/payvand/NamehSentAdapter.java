package com.payvand.jahandideh.payvand;

/**
 * Created by Jahandideh on 19/07/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class NamehSentAdapter extends RecyclerView.Adapter<NamehSentAdapter.ViewHolder>{

    private Context context;
    private String iid;
    //List of superHeroes
    public List<NamehParse> namehParses;
    private NamehParse namehParse;
    public NamehSentAdapter(List<NamehParse> namehParses,Context context){
        super();
        //Getting all the superheroes
        this.namehParses = namehParses;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_nameh_ersal, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        namehParse =  namehParses.get(position);
        holder.nnameh.setText(namehParse.getNnameh());
        holder.mnameh.setText(namehParse.getMnameh());
        holder.recive.setText(namehParse.getRecive());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewActivity = new Intent(context.getApplicationContext(), Nameh.class);
                Bundle bd=new Bundle();
                namehParse =  namehParses.get(position);
                bd.putString("id",namehParse.getid());
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
        public TextView recive;
        public CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);
            nnameh = (TextView) itemView.findViewById(R.id.textView3);
            mnameh = (TextView) itemView.findViewById(R.id.textView2);
            recive = (TextView) itemView.findViewById(R.id.textView4);
            card_view=(CardView)itemView.findViewById(R.id.card_view);

        }


    }
}

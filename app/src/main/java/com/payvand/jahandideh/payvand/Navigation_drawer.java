package com.payvand.jahandideh.payvand;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Navigation_drawer extends Fragment {
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout my_dl;
    public Navigation_drawer() {
    }
    public void setup(DrawerLayout dl, Toolbar toolbar){
        my_dl=dl;
        drawerToggle =new ActionBarDrawerToggle(getActivity(),dl,toolbar,
                R.string.open_drawer,R.string.close_drawer)
        {
            @Override
            public void onDrawerOpened(View draw0erView) {
            super.onDrawerOpened(draw0erView);
        }
            @Override
            public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }
        };
        my_dl.setDrawerListener(drawerToggle);
        my_dl.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }
}

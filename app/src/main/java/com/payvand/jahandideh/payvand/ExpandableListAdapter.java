package com.payvand.jahandideh.payvand;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	DatabaseHelper mydb;
	private Context _context;
	private String SetData;
	private List<String> _listDataHeader;
	private HashMap<String, List<String>> _listDataChild;

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
								 HashMap<String, List<String>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		mydb = new DatabaseHelper(_context);
		Cursor res = mydb.showdata();
		StringBuffer data = new StringBuffer();
		while (res.moveToNext()) {
			data.append(res.getString(1) + "");
		}
		SetData=data.toString();
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {
		final String childText = (String) getChild(groupPosition, childPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.child, null);
		}
		final TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
		txtListChild.setText(childText);
		txtListChild.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txtListChild.getText()=="جدید") {
					Intent viewActivity = new Intent(_context.getApplicationContext(), NewNameh.class);
					Bundle bd = new Bundle();
					bd.putString("username",SetData);
					viewActivity.putExtras(bd);
					Bundle bndlanimation = ActivityOptions.makeCustomAnimation(_context.getApplicationContext(), R.anim.ani1, R.anim.anim2).toBundle();
					_context.startActivity(viewActivity, bndlanimation);
				}
				if(txtListChild.getText()=="ارسال شده ها") {
					Intent viewActivity = new Intent(_context.getApplicationContext(), NamehSent.class);
					Bundle bd = new Bundle();
					bd.putString("username",SetData);
					viewActivity.putExtras(bd);
					Bundle bndlanimation = ActivityOptions.makeCustomAnimation(_context.getApplicationContext(), R.anim.ani1, R.anim.anim2).toBundle();
					_context.startActivity(viewActivity, bndlanimation);
				}
				if(txtListChild.getText()=="پیش نویس") {
					Intent viewActivity = new Intent(_context.getApplicationContext(), Pishnevis.class);
					Bundle bd = new Bundle();
					bd.putString("username",SetData);
					viewActivity.putExtras(bd);
					Bundle bndlanimation = ActivityOptions.makeCustomAnimation(_context.getApplicationContext(), R.anim.ani1, R.anim.anim2).toBundle();
					_context.startActivity(viewActivity, bndlanimation);
				}
				if(txtListChild.getText()=="در دست اقدام") {
					txtListChild.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent viewActivity = new Intent(_context.getApplicationContext(),Eghdam.class);
							Bundle bd = new Bundle();
							bd.putString("username",SetData);
							viewActivity.putExtras(bd);
							Bundle bndlanimation = ActivityOptions.makeCustomAnimation(_context.getApplicationContext(), R.anim.ani1, R.anim.anim2).toBundle();
							_context.startActivity(viewActivity, bndlanimation);
						}
					});
				}
				if(txtListChild.getText()=="بایگانی") {
					txtListChild.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent viewActivity = new Intent(_context.getApplicationContext(), Baygani.class);
							Bundle bd = new Bundle();
							bd.putString("username",SetData);
							viewActivity.putExtras(bd);
							Bundle bndlanimation = ActivityOptions.makeCustomAnimation(_context.getApplicationContext(), R.anim.ani1, R.anim.anim2).toBundle();
							_context.startActivity(viewActivity, bndlanimation);
						}
					});
				}
				if(txtListChild.getText()=="پیام جدید") {
					Intent viewActivity = new Intent(_context.getApplicationContext(), NewPayam.class);
					Bundle bd = new Bundle();
					bd.putString("username",SetData);
					viewActivity.putExtras(bd);
					Bundle bndlanimation = ActivityOptions.makeCustomAnimation(_context.getApplicationContext(), R.anim.ani1, R.anim.anim2).toBundle();
					_context.startActivity(viewActivity, bndlanimation);
				}
				if(txtListChild.getText()=="ارسال شده") {
					Intent viewActivity = new Intent(_context.getApplicationContext(), SentPayam.class);
					Bundle bd = new Bundle();
					bd.putString("username",SetData);
					viewActivity.putExtras(bd);
					Bundle bndlanimation = ActivityOptions.makeCustomAnimation(_context.getApplicationContext(), R.anim.ani1, R.anim.anim2).toBundle();
					_context.startActivity(viewActivity, bndlanimation);
				}
				if(txtListChild.getText()=="دریافت") {
					Intent viewActivity = new Intent(_context.getApplicationContext(), Recive_Payam.class);
					Bundle bd = new Bundle();
					bd.putString("username",SetData);
					viewActivity.putExtras(bd);
					Bundle bndlanimation = ActivityOptions.makeCustomAnimation(_context.getApplicationContext(), R.anim.ani1, R.anim.anim2).toBundle();
					_context.startActivity(viewActivity, bndlanimation);
				}
				if(txtListChild.getText()=="خروج از حساب کاربری") {
					txtListChild.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							boolean f = mydb.deletdata(SetData);
							if (f == true) {
								Intent viewActivity1 = new Intent(_context.getApplicationContext(), MainActivity.class);
								((Activity)_context).finish();
								_context.startActivity(viewActivity1);
							} else
								Toast.makeText(_context.getApplicationContext(), "no", Toast.LENGTH_SHORT).show();
						}
					});
				}
				if(txtListChild.getText()=="خروج از برنامه") {
					txtListChild.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					});
				}
				if(txtListChild.getText()=="گفتگو کاربران") {
					txtListChild.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent viewActivity = new Intent(_context.getApplicationContext(), Chat.class);
							Bundle bd = new Bundle();
							bd.putString("chat",SetData);
							viewActivity.putExtras(bd);
							Bundle bndlanimation = ActivityOptions.makeCustomAnimation(_context.getApplicationContext(), R.anim.ani1, R.anim.anim2).toBundle();
							_context.startActivity(viewActivity, bndlanimation);
						}
					});
				}
			}
		});
		return convertView;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
							 View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.group, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);


			return convertView;

	}
	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
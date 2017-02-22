package com.example.everyday;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	
	private Context context;
	private DataItem[] data;
	private int layoutID;
	private String year, month;
	
	public MyAdapter() {
		
	}
	
	public MyAdapter(Context context, DataItem[] data, int layoutID) {
		this.context = context;
		this.data = data;
		this.layoutID = layoutID;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(context).inflate(layoutID, null);
		ImageView item = (ImageView) convertView.findViewById(R.id.item);
		ImageView item2 = (ImageView) convertView.findViewById(R.id.item2);
		ImageView item3 = (ImageView) convertView.findViewById(R.id.item3);
		TextView tv1 = (TextView) convertView.findViewById(R.id.week);
		TextView tv2 = (TextView) convertView.findViewById(R.id.date);
		TextView tv3 = (TextView) convertView.findViewById(R.id.content);
		DataItem d = null;
		for(int i = 0; i < data.length; i++) {
			if(data[i] == null) {
				d = null;
				break;
			}
			if(data[i].getDate() == position+1) {
				d = data[i];
				break;
			} else {
				d = null;
			}
		}
		if(d != null) {
			tv1.setText(d.getWeek());
			tv2.setText(d.getDate()+"");
			tv3.setText(d.getContent());
		} else {
			item2.setVisibility(View.INVISIBLE);
			item3.setVisibility(View.INVISIBLE);
		}
		if(getWeek(month,year,position+1+"").equals("Sun")) {
			item.setVisibility(View.INVISIBLE);
			item2.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	
	private String getWeek(String b,String d,String c) {
		// TODO Auto-generated method stub
		int m = 0;
		if(b.equals("JAN")) {
			m = 1;
		} else if(b.equals("FEB")){
			m = 2;
		} else if(b.equals("MAR")){
			m = 3;
		} else if(b.equals("APR")){
			m = 4;
		} else if(b.equals("MAY")){
			m = 5;
		} else if(b.equals("JUN")){
			m = 6;
		} else if(b.equals("JUL")){
			m = 7;
		} else if(b.equals("AUG")){
			m = 8;
		} else if(b.equals("SEP")){
			m = 9;
		} else if(b.equals("OCT")){
			m = 10;
		} else if(b.equals("NOV")){
			m = 11;
		} else if(b.equals("DEC")){
			m = 12;
		}
		Calendar ca = Calendar.getInstance();
		ca.set(Integer.parseInt(d), m, Integer.parseInt(c));
		String[] t = ca.getTime().toString().split(" ");
		return t[0];
	}

}

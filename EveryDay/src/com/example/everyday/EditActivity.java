package com.example.everyday;


import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
	
	private TextView time;
	private EditText content;
	private ImageView getTime, ok;
	private DatabaseSqlite helper;
	private SQLiteDatabase db;
	private String time_time;
	private int flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_edit);
		
		init();
		
		setListener();
		
		Intent in = getIntent();
		
		String b = in.getStringExtra("month");
		String c = in.getStringExtra("date");
		String d = in.getStringExtra("year");
		String a = getWeek(b,d,c);
		
		time_time = a+"/"+b+" "+c+"/"+d;
		
		time.setText(time_time);
		
		Cursor cur = db.query("daygram", null, "year=? and month=? and date=?", new String[]{d,b,c}, null, null, null);
		flag = cur.getCount();
		if(flag != 0) {
			cur.moveToNext();
			content.setText(cur.getString(cur.getColumnIndex("content")));
		}
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

	private void setListener() {
		// TODO Auto-generated method stub
		
		getTime.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				content.append(new Date(System.currentTimeMillis()).toString());
			}
		});
		
		ok.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues c = new ContentValues();
				String t = time.getText().toString();
				String[] str = t.split("[ /]");
				c.put("week", str[0]);
			    c.put("year", Integer.parseInt(str[3]));
			    c.put("month", str[1]);
			    c.put("date", Integer.parseInt(str[2]));
			    c.put("content", content.getText().toString());
			    if(flag == 0)
			    	db.insert("daygram", null, c);
			    else
			    	db.update("daygram", c, "year=? and month=? and date=?", new String[]{str[3],str[1],str[2]});
			    Intent in = new Intent(EditActivity.this,MainActivity.class);
			    startActivity(in);
			    finish();
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		time = (TextView) findViewById(R.id.time);
		content = (EditText) findViewById(R.id.content);
		getTime = (ImageView) findViewById(R.id.getTime);
		ok = (ImageView) findViewById(R.id.ok);
		helper = new DatabaseSqlite(EditActivity.this);
		db = helper.getWritableDatabase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*
	 * ¼àÌý·µ»Ø°´Å¥
	 * */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
		Intent in = new Intent(EditActivity.this,MainActivity.class);
	    startActivity(in);
	    finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}

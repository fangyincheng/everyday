package com.example.everyday;

import java.util.Calendar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	
	private TextView month, year, add, show;
	private ImageView toMonth;
	private LinearLayout LL;
	private HorizontalScrollView HSV, HSV_2;
	private TextView[] monthes, years;
	private ImageView[] m;
	private ListView listView;
	private MyAdapter myAdapter;
	private DataItem[] data;
	private DatabaseSqlite helper;
	private SQLiteDatabase db;
	private int flag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		init();//初始化控件实例
		
		//给textview添加监听
		month.setOnClickListener(new Click());
		year.setOnClickListener(new Click());
		add.setOnClickListener(new Click());
		toMonth.setOnClickListener(new Click());
		for(int i = 0; i < monthes.length; i++) {
			monthes[i].setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Calendar ca = Calendar.getInstance();
					
					if(year.getText().toString().equals(ca.get(Calendar.YEAR)+"") &&
							 isDo(((TextView) v).getText().toString())-1>ca.get(Calendar.MONTH)) {
						return;
					}
					
					month.setText(((TextView) v).getText().toString());
					myAdapter.setMonth(((TextView) v).getText().toString());
					LL.setVisibility(View.VISIBLE);
					HSV.setVisibility(View.INVISIBLE);
					
					getData();
					
					if(flag == 1) {
						show.setText("");
						for(int i = 0; i < data.length; i++) {
							if(data[i] == null)
								break;
							show.append("  "+data[i].getDate()+" "+data[i].getWeek()+" / "+data[i].getContent()+"\n\n");
						}
					}
					
					for(int i = 0; i < monthes.length; i++) {
						if(monthes[i].getText().toString().equals(month.getText().toString())) {
							m[i].setImageResource(R.drawable.bg3_g);
						} else {
							m[i].setImageResource(R.drawable.bg3);
						}
					}
				}
			});
		}
		for(int i = 0; i < years.length; i++) {
			years[i].setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Calendar ca = Calendar.getInstance();
					
					if(((TextView) v).getText().equals(ca.get(Calendar.YEAR)+"") &&
							 isDo(month.getText().toString())-1>ca.get(Calendar.MONTH)) {
						return;
					}
					
					year.setText(((TextView) v).getText());
					myAdapter.setYear(((TextView) v).getText().toString());
					LL.setVisibility(View.VISIBLE);
					HSV_2.setVisibility(View.INVISIBLE);
					
					getData();
					
					if(flag == 1) {
						show.setText("");
						for(int i = 0; i < data.length; i++) {
							if(data[i] == null)
								break;
							show.append("  "+data[i].getDate()+" "+data[i].getWeek()+" / "+data[i].getContent()+"\n\n");
						}
					}
					
					for(int i = 0; i < years.length; i++) {
						if(years[i].getText().toString().equals(year.getText().toString())) {
							years[i].setTextColor(Color.BLACK);
						} else {
							years[i].setTextColor(Color.WHITE);
						}
					}
				}
			});
		}
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent in = new Intent(MainActivity.this,EditActivity.class);
				in.putExtra("month", month.getText().toString());
				in.putExtra("year", year.getText().toString());
				in.putExtra("date", position+1+"");
				startActivity(in);
				finish();
			}
		});
	}
	
	private int isDo(String b) {
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
		
		return m;
	}
	
	private void getData() {
		Cursor cur = db.query("daygram", null, "year=? and month=?", new String[]{year.getText().toString(),month.getText().toString()}, null, null, null);
		for(int i = 0; i < data.length; i++) {
			myAdapter.notifyDataSetChanged();
			data[i] = null;
		}
		for(int i = 0; i < cur.getCount(); i++) {
			cur.moveToNext();
			myAdapter.notifyDataSetChanged();
			data[i] = new DataItem();
			data[i].setYear(cur.getInt(cur.getColumnIndex("year")));
			data[i].setWeek(cur.getString(cur.getColumnIndex("week")));
			data[i].setDate(cur.getInt(cur.getColumnIndex("date")));
			data[i].setMonth(cur.getString(cur.getColumnIndex("month")));
			data[i].setContent(cur.getString(cur.getColumnIndex("content")));
		}
	}
	
	private void init() {
		month = (TextView) findViewById(R.id.month);
		year = (TextView) findViewById(R.id.year);
		show = (TextView) findViewById(R.id.show);
		toMonth = (ImageView) findViewById(R.id.toMonth);
		
		Calendar ca = Calendar.getInstance();
		String[] t = ca.getTime().toString().split(" ");
		month.setText(t[1].toUpperCase());
		year.setText(t[5].toString());
		
		add = (TextView) findViewById(R.id.add);
		LL = (LinearLayout) findViewById(R.id.LL);
		HSV = (HorizontalScrollView) findViewById(R.id.HSV);
		HSV_2 = (HorizontalScrollView) findViewById(R.id.HSV_2);
		monthes = new TextView[12];
		m = new ImageView[12];
		years = new TextView[7];
		for(int i = 0; i < monthes.length; i++) {
			try {
				monthes[i] = (TextView) findViewById(R.id.class.getField("month_"+(i+1)).getInt(new R.id()));
				m[i] = (ImageView) findViewById(R.id.class.getField("m_"+(i+1)).getInt(new R.id()));
				if(monthes[i].getText().toString().equals(month.getText().toString())) {
					m[i].setImageResource(R.drawable.bg3_g);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int i = 0; i < years.length; i++) {
			try {
				years[i] = (TextView) findViewById(R.id.class.getField("year_"+(i+1)).getInt(new R.id()));
				years[i].setText(Integer.parseInt(year.getText().toString())-years.length+1+i+"");
				if(years[i].getText().toString().equals(year.getText().toString())) {
					years[i].setTextColor(Color.BLACK);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		listView = (ListView) findViewById(R.id.list);
		helper = new DatabaseSqlite(MainActivity.this);
		db = helper.getWritableDatabase();
		data = new DataItem[31];
		myAdapter = new MyAdapter(this, data, R.layout.item);
		listView.setAdapter(myAdapter);
		
		myAdapter.setMonth(t[1].toUpperCase());
		myAdapter.setYear(t[5].toString());
		
		//数据库查询
		Cursor c = db.query("daygram", null, null, null, null, null, null);
		for(int i = 0; i < c.getCount(); i++) {
			c.moveToNext();
			myAdapter.notifyDataSetChanged();
			data[i] = new DataItem();
			data[i].setYear(c.getInt(c.getColumnIndex("year")));
			data[i].setWeek(c.getString(c.getColumnIndex("week")));
			data[i].setDate(c.getInt(c.getColumnIndex("date")));
			data[i].setMonth(c.getString(c.getColumnIndex("month")));
			data[i].setContent(c.getString(c.getColumnIndex("content")));
		}
	}
	
	private class Click implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.month:
				LL.setVisibility(View.INVISIBLE);
				HSV.setVisibility(View.VISIBLE);
				break;
			case R.id.year:
				LL.setVisibility(View.INVISIBLE);
				HSV_2.setVisibility(View.VISIBLE);
				break;
			case R.id.add:
				
				Calendar c = Calendar.getInstance();
				String[] t = c.getTime().toString().split(" ");

				Intent in = new Intent(MainActivity.this,EditActivity.class);
				in.putExtra("month", t[1]);
				in.putExtra("year", t[5]);
				in.putExtra("date", t[2]);
				startActivity(in);
				finish();
				break;
			case R.id.toMonth:
				if(flag == 0) {
					show.setVisibility(View.VISIBLE);
					listView.setVisibility(View.INVISIBLE);
					for(int i = 0; i < data.length; i++) {
						if(data[i] == null)
							break;
						show.append("  "+data[i].getDate()+" "+data[i].getWeek()+" / "+data[i].getContent()+"\n\n");
					}
					flag = 1;
				} else {
					show.setVisibility(View.INVISIBLE);
					listView.setVisibility(View.VISIBLE);
					show.setText("");
					flag = 0;
				}
				break;
				
			default:
				break;
			}
		}
		
	}
	
	/*
	 * 监听返回按钮
	 * */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
		if(HSV.getVisibility() == View.VISIBLE || HSV_2.getVisibility() == View.VISIBLE) {
			LL.setVisibility(View.VISIBLE);
			HSV.setVisibility(View.INVISIBLE);
			HSV_2.setVisibility(View.INVISIBLE);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

package com.tectonics.taximeter;

import com.tectonics.taximeter.db.SqliteDb;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener 
{
	Button btnLogin;
	EditText eUserId,ePassword;
	String userId,password;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		@SuppressWarnings({ "unused", "deprecation" })
		int sdkversion=Integer.valueOf(android.os.Build.VERSION.SDK);	
        setContentView(R.layout.login_activity);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		
		context=getApplicationContext();
		
		eUserId=(EditText)findViewById(R.id.editTextUserId);
		ePassword=(EditText)findViewById(R.id.editTextPassword);
		btnLogin=(Button)findViewById(R.id.buttonLogin);
		btnLogin.setOnClickListener(this);		
		
	}
	@Override
	public void onClick(View v)
	{
		userId=eUserId.getText().toString();
		password=ePassword.getText().toString();
		if((userId.equalsIgnoreCase("123"))&&(password.equals("demo")))
		{		
			SqliteDb sqlDb=new SqliteDb(context);
			SQLiteDatabase db=sqlDb.getWritableDatabase();
			Cursor cursor1=db.query("jobs", new String[]{"pickup_address"}, null, null, null, null, null);
			Cursor cursor2=db.query("taxicharges", new String[]{"regular_fare"}, null, null, null, null, null);
			if(cursor1.getCount()>0){
				db.delete("jobs", null, null);
			}
			if(cursor2.getCount()>0){
				db.delete("taxicharges", null, null);
			}
			cursor1.close();
			cursor2.close();
			
			for(int i=0;i<5;i++){
				ContentValues insertValues=new ContentValues();
				if(i==0){
					insertValues.put("pickup_time", "9:30 AM");
					insertValues.put("pickup_address", "Azadpur, New Delhi");
					insertValues.put("drop_address", "New ashok nagar, new delhi");
					insertValues.put("cust_mobile", "9935008985");
					insertValues.put("fare_type", "Prepaid");
					insertValues.put("cust_name", "Ram Ji");
				}else if(i==1){
					insertValues.put("pickup_time", "9:30 AM");
					insertValues.put("pickup_address", "Jahageer puri, New Delhi");
					insertValues.put("drop_address", "New ashok nagar, new delhi");
					insertValues.put("cust_mobile", "9935008985");
					insertValues.put("fare_type", "Prepaid");
					insertValues.put("cust_name", "Joology Devi");
				}else if(i==2){
					insertValues.put("pickup_time", "10:00 AM");
					insertValues.put("pickup_address", "Model town, New Delhi");
					insertValues.put("drop_address", "Ashok vihar, new delhi");
					insertValues.put("cust_mobile", "9935008985");
					insertValues.put("fare_type", "Fixed rate");
					insertValues.put("cust_name", "Botany singh");
				}else if(i==3){
					insertValues.put("pickup_time", "10:30 AM");
					insertValues.put("pickup_address", "Ashok vihar, New Delhi");
					insertValues.put("drop_address", "New ashok nagar, new delhi");
					insertValues.put("cust_mobile", "9935008985");
					insertValues.put("fare_type", "Regular");
					insertValues.put("cust_name", "Delhi Kumar");
				}else if(i==4){
					insertValues.put("pickup_time", "11:00 AM");
					insertValues.put("pickup_address", "Ashok vihar, New Delhi");
					insertValues.put("drop_address", "New ashok nagar, new delhi");
					insertValues.put("cust_mobile", "9935008985");
					insertValues.put("fare_type", "Street job");
					insertValues.put("cust_name", "Noida Prasad");
				}else{
					insertValues.put("pickup_time", "11:30 AM");
					insertValues.put("pickup_address", "Ashok vihar, New Delhi");
					insertValues.put("drop_address", "New ashok nagar, new delhi");
					insertValues.put("cust_mobile", "9935008985");
					insertValues.put("fare_type", "Regular");
					insertValues.put("cust_name", "Krisna Ji");
				}
				db.insert("jobs", null, insertValues);
			}			
			
			ContentValues insertValues1=new ContentValues();
			
			insertValues1.put("regular_fare", "20");
			insertValues1.put("fixed_rate_fare", "200");
			insertValues1.put("street_job_fare", "30");
			insertValues1.put("price_per_km", "10");
			insertValues1.put("initial_charges", "20");
			insertValues1.put("charges_upto_km", "5");
			insertValues1.put("night_charges", "50");
			insertValues1.put("available_time", "9:30 AM to 9:30 PM");
			insertValues1.put("payment_method", "Cash");
			insertValues1.put("waiting_price", "5");
			insertValues1.put("waiting_time_upto", "1");
			
			db.insert("taxicharges", null, insertValues1);
			db.close();
			sqlDb.close();
			
			Intent intent=new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Invalid User ID", Toast.LENGTH_LONG).show();
			eUserId.setText("");
			ePassword.setText("");
			eUserId.requestFocus();
		}
	}

}

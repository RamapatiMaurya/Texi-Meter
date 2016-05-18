package com.tectonics.taximeter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDb extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "taximeter.db";
	public static final String USER_DETAILS = "create table user ( "
			+ "user_id varchar(30), " + "user_pwd varchar(30), "
			+ " user_name varchar(30), " + " cab_id varchar(30)" + ")";
	public static final String TAXI_CHARGES = "create table taxicharges ("
			+ "regular_fare varchar(30), " + "fixed_rate_fare varchar(30), "
			+ "street_job_fare varchar(30), " + "price_per_km varchar(10), "
			+ "initial_charges varchar(10), "+"payment_method varchar(30), "
			+ "charges_upto_km varchar(10), " + "night_charges varchar(10), "
			+ "available_time varchar, " + "waiting_price varchar(30),"+ "waiting_time_upto varchar(30)" + ")";
	public static final String JOBS = "create table jobs ("
			+ " pickup_time varchar, " + "pickup_address varchar, "
			+ "drop_address varchar, " + "cust_mobile varchar(15),"
			+ "fare_type varchar(30)," + "cust_name varchar(30)" + ")";

	public SqliteDb(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public SqliteDb(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlDb) {
		// TODO Auto-generated method stub
		sqlDb.execSQL(USER_DETAILS);
		sqlDb.execSQL(TAXI_CHARGES);
		sqlDb.execSQL(JOBS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}

package com.tectonics.taximeter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tectonics.taximeter.db.SqliteDb;
import com.tectonics.taximeter.model.FareModel;
import com.tectonics.taximeter.model.JobListAdapter;
import com.tectonics.taximeter.model.JobReceivedModel;
import com.tectonics.taximeter.model.JobsModel;

public class MainActivity extends Activity implements OnClickListener {
	public static final String TAG="Main Activity";
	TextView txtcharges, txtTime, txtFair, txtDistance, txtWaiting,
			txtInitialCharges, txtPrice, txtPickUp, txtTechtonics, txtMobile;
	TextView textAvailbleJobs, textWaitingTime;
	Button btnOnOffDuty, btnExitExtraCharges, btnMail, btnReport,
			btnAcceptLocation, btnGetDirection, btnPrint;
	LinearLayout jobContainer;
	ListView jobList;

	ArrayList<JobsModel> jobData = new ArrayList<JobsModel>();

	public static long n = 0;
	TimerTask waitingTimer=null;

	boolean onDutyFlag = true;
	boolean offDutyFlag = false;
	boolean atCustLocationFlag = false;
	boolean boardedFlag = false;
	boolean destinationFlag = false;
	boolean extraChargesFlag = false;
	boolean exitFlag = true;
	boolean clearFlag = false;
	boolean waitingFlag = false;
	boolean reportFlag = false;
	boolean waitingOverFlag = false;
	boolean streetJobFlag = false;
	DisplayMetrics displayMatrices;
	Context context;
	ApplicationDrawable applicationDrawable;
	LinearLayout linearLayoutRecord;
	
	Handler handler;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		linearLayoutRecord = (LinearLayout) findViewById(R.id.linearlayoutRecord);
		Typeface typeface = Typeface.createFromAsset(getAssets(),
				"textstyle/digital-7.ttf");
		txtcharges = (TextView) findViewById(R.id.textViewcharges);
		txtcharges.setTypeface(typeface);
		textWaitingTime = (TextView) findViewById(R.id.textViewWaitingTime);

		txtTime = (TextView) findViewById(R.id.textViewTime);
		txtFair = (TextView) findViewById(R.id.textViewFair);
		txtDistance = (TextView) findViewById(R.id.textViewDistance);
		txtWaiting = (TextView) findViewById(R.id.textViewWaitig);
		txtInitialCharges = (TextView) findViewById(R.id.textViewInitialCharges);
		txtPrice = (TextView) findViewById(R.id.textViewPrice);
		txtPickUp = (TextView) findViewById(R.id.textViewPickUp);
		txtMobile = (TextView) findViewById(R.id.textViewMobile);
		txtTechtonics = (TextView) findViewById(R.id.textViewTechtonics);

		textAvailbleJobs = (TextView) findViewById(R.id.textviewAvailablejobs);

		btnOnOffDuty = (Button) findViewById(R.id.buttonOnOffDuty);
		btnExitExtraCharges = (Button) findViewById(R.id.buttonExitExtracharges);
		btnMail = (Button) findViewById(R.id.buttonMail);
		btnReport = (Button) findViewById(R.id.buttonReport);
		btnAcceptLocation = (Button) findViewById(R.id.buttonAcceptDuty);
		btnGetDirection = (Button) findViewById(R.id.buttonGetDirection);
		btnPrint = (Button) findViewById(R.id.buttonPrintBill);

		jobContainer = (LinearLayout) findViewById(R.id.jobContainer);
		jobList = (ListView) findViewById(R.id.jobList);

		btnOnOffDuty.setOnClickListener(this);
		btnExitExtraCharges.setOnClickListener(this);
		btnMail.setOnClickListener(this);
		btnReport.setOnClickListener(this);
		btnAcceptLocation.setOnClickListener(this);
		btnGetDirection.setOnClickListener(this);
		btnPrint.setOnClickListener(this);

		// ===================================
		displayMatrices = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMatrices);
		context = getApplicationContext();
		applicationDrawable = new ApplicationDrawable(context,
				displayMatrices.widthPixels, displayMatrices.heightPixels);

		txtcharges.setTextColor(Color.WHITE);
		txtcharges.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				(displayMatrices.widthPixels / 5));

		if ((displayMatrices.widthPixels <= 480)
				|| (displayMatrices.heightPixels <= 320)) {
			linearLayoutRecord.setLayoutParams(new LinearLayout.LayoutParams(
					(int) (displayMatrices.widthPixels / 1.5),
					(int) ((displayMatrices.heightPixels) / 2.25)));
			btnOnOffDuty.setBackgroundDrawable(applicationDrawable
					.Button(R.drawable.onduty));
			btnExitExtraCharges.setBackgroundDrawable(applicationDrawable
					.Button(R.drawable.exit));
			btnMail.setBackgroundDrawable(applicationDrawable
					.Button(R.drawable.streetjob));
			btnReport.setBackgroundDrawable(applicationDrawable
					.Button(R.drawable.report));

			btnAcceptLocation.setBackgroundDrawable(applicationDrawable
					.ActionButton(R.drawable.location));
			btnGetDirection.setBackgroundDrawable(applicationDrawable
					.ActionButton(R.drawable.get_direction));
			btnPrint.setBackgroundDrawable(applicationDrawable
					.ActionButton(R.drawable.print));

			int x = (displayMatrices.widthPixels + displayMatrices.heightPixels) / 43;
			txtTime.setTextSize(x);
			txtFair.setTextSize(x);
			txtDistance.setTextSize(x);
			txtWaiting.setTextSize(x);
			txtInitialCharges.setTextSize(x);
			txtPrice.setTextSize(x);
			txtPickUp.setTextSize(x);
			txtTechtonics.setTextSize(x);
			// &#9990 for contact use
		} else {
			linearLayoutRecord.setLayoutParams(new LinearLayout.LayoutParams(
					(int) (displayMatrices.widthPixels / 1.5),
					(int) ((displayMatrices.heightPixels) / 2.40)));
			btnOnOffDuty.setBackgroundDrawable(applicationDrawable
					.Button(R.drawable.onduty));
			btnExitExtraCharges.setBackgroundDrawable(applicationDrawable
					.Button(R.drawable.exit));
			btnMail.setBackgroundDrawable(applicationDrawable
					.Button(R.drawable.streetjob));
			btnReport.setBackgroundDrawable(applicationDrawable
					.Button(R.drawable.report));

			btnAcceptLocation.setBackgroundDrawable(applicationDrawable
					.ActionButton(R.drawable.location1));
			btnGetDirection.setBackgroundDrawable(applicationDrawable
					.ActionButton(R.drawable.get_direction1));
			btnPrint.setBackgroundDrawable(applicationDrawable
					.ActionButton(R.drawable.print1));
		}
		// ===================================
		show();
		
		//update UI from handler
		handler=new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	public void show() {

		SqliteDb sqlDb = new SqliteDb(context);
		SQLiteDatabase db = sqlDb.getWritableDatabase();
		Cursor cursor = db.query("jobs", new String[] { "pickup_time",
				"pickup_address", "drop_address", "cust_mobile", "fare_type", "cust_name" },
				null, null, null, null, null);
		int totalJobs = cursor.getCount();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			jobData.clear();
			JobsModel rowInfoData = new JobsModel();
			rowInfoData.setPickupAddress("Pickup Address");
			rowInfoData.setPickupTime("Pickup Time");
			rowInfoData.setDropAddress("Drop Address");
			rowInfoData.setFareType("Fare Type");
			rowInfoData.setCustMobile("Mobile No.");
			rowInfoData.setCustName("Name");
			jobData.add(rowInfoData);
			do {
				JobsModel jobInfoData = new JobsModel();
				jobInfoData.setPickupTime(cursor.getString(cursor
						.getColumnIndex("pickup_time")));
				jobInfoData.setPickupAddress(cursor.getString(cursor
						.getColumnIndex("pickup_address")));
				jobInfoData.setDropAddress(cursor.getString(cursor
						.getColumnIndex("drop_address")));
				jobInfoData.setFareType(cursor.getString(cursor
						.getColumnIndex("fare_type")));
				jobInfoData.setCustMobile(cursor.getString(cursor
						.getColumnIndex("cust_mobile")));
				jobInfoData.setCustName(cursor.getString(cursor
						.getColumnIndex("cust_name")));
				jobData.add(jobInfoData);
			} while (cursor.moveToNext());
			cursor.close();
			db.close();
			sqlDb.close();
			JobListAdapter listAdapter = new JobListAdapter(context, jobData);
			jobList.setAdapter(listAdapter);
		}
		textAvailbleJobs.setText("Available jobs (" + totalJobs + ")");

		jobList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Dialog dialog = new Dialog(MainActivity.this);
				dialog.setContentView(R.layout.dialog_job_info);
				dialog.setTitle("Journey Details");
				TextView textPickAddress = (TextView) dialog
						.findViewById(R.id.textViewPickupAddress);
				TextView textDropAddress = (TextView) dialog
						.findViewById(R.id.textViewDropAddress);
				TextView textPickupTime = (TextView) dialog
						.findViewById(R.id.textViewPickupTime);
				TextView textFareRs = (TextView) dialog
						.findViewById(R.id.textViewFareType);
				TextView textCustMobile = (TextView) dialog
						.findViewById(R.id.textViewMobile);
				TextView textCustName=(TextView) dialog.findViewById(R.id.textViewName);
				Button buttonAccept = (Button) dialog
						.findViewById(R.id.buttonAcceptJob);
				Button buttonCancel = (Button) dialog
						.findViewById(R.id.buttonCancelJob);

				final JobReceivedModel jobReceived = new JobReceivedModel();
				jobReceived.setPickupAddress(jobData.get(position)
						.getPickupAddress());
				jobReceived.setDropAddress(jobData.get(position)
						.getDropAddress());
				jobReceived
						.setPickupTime(jobData.get(position).getPickupTime());
				jobReceived.setFareType(jobData.get(position).getFareType());
				jobReceived
						.setCustMobile(jobData.get(position).getCustMobile());
				jobReceived.setCustName(jobData.get(position).getCustName());
				
				textPickAddress.setText("Pickup Location: "
						+ jobReceived.getPickupAddress());
				textDropAddress.setText("Drop Location: "
						+ jobReceived.getDropAddress());
				textPickupTime.setText("Pickup Time: "
						+ jobReceived.getPickupTime());
				textFareRs.setText("Fare Type: " + jobReceived.getFareType());
				textCustMobile.setText("Mobile No.: "
						+ jobReceived.getCustMobile());
				textCustName.setText("Name: "+jobReceived.getCustName());

				buttonAccept.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						jobContainer.setVisibility(View.GONE);
						txtcharges.setVisibility(View.VISIBLE);
						textWaitingTime.setVisibility(View.VISIBLE);
						SqliteDb sqlDb = new SqliteDb(context);
						SQLiteDatabase db = sqlDb.getWritableDatabase();
						Cursor cursor3 = db.query("taxicharges", new String[] {
								"regular_fare","fixed_rate_fare","street_job_fare", "price_per_km", "initial_charges",
								"charges_upto_km", "night_charges", "available_time","waiting_price","waiting_time_upto" }, null, null,
								null, null, null);
//						Log.e("count", "rows: "+cursor3.getCount());
						if (cursor3.getCount() > 0) {
							cursor3.moveToFirst();
							do {
								jobReceived.setRegularFare(Float.parseFloat(cursor3.getString(cursor3
										.getColumnIndex("regular_fare"))));
								jobReceived.setFixedFare(Float.parseFloat(cursor3.getString(cursor3
										.getColumnIndex("fixed_rate_fare"))));
								jobReceived.setStreetFare(Float.parseFloat(cursor3.getString(cursor3
										.getColumnIndex("street_job_fare"))));
								jobReceived.setPrice(Float.parseFloat(cursor3.getString(cursor3
										.getColumnIndex("price_per_km"))));
								jobReceived.setInitialCharges(Float.parseFloat(cursor3.getString(cursor3
										.getColumnIndex("initial_charges"))));
								jobReceived.setUpToKm(Integer.parseInt(cursor3
										.getString(cursor3
												.getColumnIndex("charges_upto_km"))));
								jobReceived.setWaiting(Float.parseFloat(cursor3.getString(cursor3
										.getColumnIndex("waiting_price"))));
								jobReceived.setDistance(0.0f);
								jobReceived.setPickAt("00:00");
								jobReceived.setWaitingUpTo(Integer.parseInt(cursor3.getString(cursor3
										.getColumnIndex("waiting_time_upto"))));
							} while (cursor3.moveToNext());
							cursor3.close();
							db.close();
							sqlDb.close();

						}
						
						txtcharges.setText(String.valueOf(jobReceived.getInitialCharges()));
						
						txtFair.setText("Fare = " + jobReceived.getFareType());
						txtDistance.setText("Dist. = "
								+ jobReceived.getDistance() + "Km");
						txtWaiting.setText("Waiting = \u20B9 "
								+ jobReceived.getWaiting() + "/"
								+ jobReceived.getWaitingUpTo() + " Min");
						txtInitialCharges.setText("Initial Charges = \u20B9 "
								+ jobReceived.getInitialCharges() + " (Up to "
								+ jobReceived.getUpToKm() + " Km)");
						txtTime.setText("Pick at = "
								+ jobReceived.getPickupTime());
						txtMobile.setText("Mob. " + jobReceived.getCustMobile());
						txtPickUp.setText("Pick up = "
								+ jobReceived.getPickupAddress());
						
						if(jobReceived.getFareType().equalsIgnoreCase("Prepaid") || jobReceived.getFareType().equalsIgnoreCase("Carporatre to be paid later")){
							txtPrice.setText("Price = \u20B9 "
									+ jobReceived.getRegularFare() + "/Km");
							FareModel.setFarePricePerKm(jobReceived.getRegularFare());
						}else if(jobReceived.getFareType().equalsIgnoreCase("Fixed rate")){
							txtPrice.setText("Price = \u20B9 "
									+ jobReceived.getFixedFare());
							FareModel.setFarePricePerKm(jobReceived.getFixedFare());
						}else if(jobReceived.getFareType().equalsIgnoreCase("Regular")){
							txtPrice.setText("Price = \u20B9 "
									+ jobReceived.getRegularFare() + "/Km");
							FareModel.setFarePricePerKm(jobReceived.getRegularFare());
						}else if(jobReceived.getFareType().equalsIgnoreCase("Street job")){
							txtPrice.setText("Price = \u20B9 "
									+ jobReceived.getStreetFare() + "/Km");
							FareModel.setFarePricePerKm(jobReceived.getStreetFare());
						}else{
							txtPrice.setText("Price = \u20B9 "
									+ jobReceived.getRegularFare() + "/Km");
							FareModel.setFarePricePerKm(jobReceived.getRegularFare());
						}
						
						FareModel.setFareType(jobReceived.getFareType());
						FareModel.setInitialCharges(jobReceived
								.getInitialCharges());
						FareModel.setInitialChargesUpToKm(jobReceived
								.getUpToKm());
						FareModel.setWaitingPrice(jobReceived.getWaiting());
						FareModel.setWaitingUpTo(jobReceived.getWaitingUpTo());
						FareModel.setChargesPerKm(jobReceived.getPrice());
						dialog.cancel();

						offDutyFlag = false;
						atCustLocationFlag = true;
						boardedFlag = false;
						destinationFlag = false;
						extraChargesFlag = false;
						exitFlag = false;
						clearFlag = false;
						waitingFlag = false;
						reportFlag = false;
						waitingOverFlag = false;
						streetJobFlag = false;
					}
				});
				buttonCancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				dialog.show();
			}
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		if (v == btnOnOffDuty) {
			if (offDutyFlag == true) {
				if ((displayMatrices.widthPixels <= 480)
						|| (displayMatrices.heightPixels <= 320)) {
					btnOnOffDuty.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.onduty));
					btnMail.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.streetjob));
					btnReport.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.report));
				} else {
					btnOnOffDuty.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.onduty));
					btnMail.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.streetjob));
					btnReport.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.report));
				}
				streetJobFlag = true;
				reportFlag = true;
				offDutyFlag = false;
				onDutyFlag = true;
			} else if (onDutyFlag == true) {
				if ((displayMatrices.widthPixels <= 480)
						|| (displayMatrices.heightPixels <= 320)) {
					btnOnOffDuty.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.offduty));
					btnExitExtraCharges
							.setBackgroundDrawable(applicationDrawable
									.Button(R.drawable.exit));
				} else {
					btnOnOffDuty.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.offduty));
					btnExitExtraCharges
							.setBackgroundDrawable(applicationDrawable
									.Button(R.drawable.exit));
				}
				offDutyFlag = true;
				reportFlag = true;
				streetJobFlag = true;

				atCustLocationFlag = false;
				boardedFlag = false;
				destinationFlag = false;
				extraChargesFlag = false;
				exitFlag = false;
				clearFlag = false;
				waitingFlag = false;
				waitingOverFlag = false;
			}
		} else if (v == btnExitExtraCharges) {
			if (extraChargesFlag == true) {
				Intent intent = new Intent(getApplicationContext(),
						ExtrachargeActivity.class);
				startActivity(intent);
			} else {
				if (exitFlag == true)
					finish();
			}
		} else if (v == btnMail) {
			if (streetJobFlag == true) {
				if ((displayMatrices.widthPixels <= 480)
						|| (displayMatrices.heightPixels <= 320)) {
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.boarded));
					btnReport.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.waiting));
				} else {
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.boarded1));
					btnReport.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.waiting));
				}
				
				waitingFlag = true;
				boardedFlag = true;
				reportFlag = false;
				atCustLocationFlag = false;
				onDutyFlag = false;
				exitFlag = false;
				offDutyFlag = false;
				
				jobContainer.setVisibility(View.GONE);
				txtcharges.setVisibility(View.VISIBLE);
				textWaitingTime.setVisibility(View.VISIBLE);
				
				// Get and Set street job fare details
				FareModel.setInitialCharges(10.0f);
				FareModel.setInitialChargesUpToKm(2);
				FareModel.setChargesPerKm(10.0f);
				FareModel.setFareType("street job");
				FareModel.setFarePricePerKm(10.0f);
				FareModel.setWaitingPrice(5);
				FareModel.setWaitingUpTo(1);
				
				txtcharges.setText(String.valueOf(FareModel.getInitialCharges()));
				txtFair.setText("Fare = " + FareModel.getFareType());
				txtDistance.setText("Dist. = "
						+ 0.0 + "Km");
				txtWaiting.setText("Waiting = \u20B9 "
						+ FareModel.getWaitingPrice() + "/"
						+ FareModel.getWaitingUpTo() + " Min");
				txtInitialCharges.setText("Initial Charges = \u20B9 "
						+ FareModel.getInitialCharges() + " (Up to "
						+ FareModel.getInitialChargesUpToKm() + " Km)");
				
				Calendar cal1=Calendar.getInstance();
				int hour=cal1.get(Calendar.HOUR);
				int minute=cal1.get(Calendar.MINUTE);
				int am_pm=cal1.get(Calendar.AM_PM);
				if(am_pm == Calendar.PM){
					txtTime.setText("Pick at = "
							+ hour+":"+minute+ " "+"PM");
				}else{
					txtTime.setText("Pick at = "
							+ hour+":"+minute+ " "+"AM");
				}
				txtMobile.setText("Mob. " + "N/A");
				txtPickUp.setText("Pick up = "
						+ "N/A");
				
			} else {

			}
		} else if (v == btnReport) {
			if (waitingFlag == true) {
				btnReport.setBackgroundDrawable(applicationDrawable
						.Button(R.drawable.waitingover));
				waitingFlag = false;
				waitingOverFlag = true;
				startWaitingTimer();
			} else if (waitingOverFlag == true) {

				if ((displayMatrices.widthPixels <= 480)
						|| (displayMatrices.heightPixels <= 320)) {
					btnReport.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.waiting));
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.destination));
				} else {
					btnReport.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.waiting));
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.destination1));
				}
				stopWaitingTimer();
				waitingFlag = true;
				waitingOverFlag = false;
				destinationFlag = true;
				boardedFlag = false;
				
				long waitingUpTo=FareModel.getWaitingUpTo();
				long totalWaiting=FareModel.getWaitingTime()/60;
				float priceWaiting=FareModel.getWaitingPrice();
				
				if(totalWaiting >= waitingUpTo && waitingUpTo !=0){
					priceWaiting = priceWaiting + (totalWaiting-waitingUpTo)*(priceWaiting/waitingUpTo);
				}else{
					priceWaiting=0.0f;
				}
				
				txtcharges.setText(String.valueOf(FareModel.getInitialCharges() + priceWaiting));
				
			} else if (reportFlag == true) {
				
			}
		} else if (v == btnAcceptLocation) {
			if (atCustLocationFlag == true) {
				if ((displayMatrices.widthPixels <= 480)
						|| (displayMatrices.heightPixels <= 320)) {
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.boarded));
					btnReport.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.waiting));
					btnMail.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.email));
					btnExitExtraCharges
					.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.extracharges));
				} else {
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.boarded1));
					btnReport.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.waiting));
					btnMail.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.email));
					btnExitExtraCharges
					.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.extracharges));
				}
				extraChargesFlag = true;
				boardedFlag = true;
				waitingFlag = true;
				atCustLocationFlag = false;
				reportFlag = false;
				streetJobFlag = false;
			} else if (boardedFlag == true) {
				if ((displayMatrices.widthPixels <= 480)
						|| (displayMatrices.heightPixels <= 320)) {
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.destination));
					btnMail.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.email));
					btnReport.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.waiting));
				} else {
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.destination1));
					btnMail.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.email));
				}

				waitingFlag = true;
				streetJobFlag = false;
				destinationFlag = true;
				boardedFlag = false;
				stopWaitingTimer();
				
				long waitingUpTo=FareModel.getWaitingUpTo();
				long totalWaiting=FareModel.getWaitingTime()/60;
				float priceWaiting=FareModel.getWaitingPrice();
				
				if(totalWaiting >= waitingUpTo && waitingUpTo !=0){
					priceWaiting = priceWaiting + (totalWaiting-waitingUpTo)*(priceWaiting/waitingUpTo);
				}else{
					priceWaiting=0.0f;
				}
				
				txtcharges.setText(String.valueOf(FareModel.getInitialCharges() + priceWaiting));
				
			} else if (destinationFlag == true) {
				if ((displayMatrices.widthPixels <= 480)
						|| (displayMatrices.heightPixels <= 320)) {
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.clear));
					btnExitExtraCharges
							.setBackgroundDrawable(applicationDrawable
									.Button(R.drawable.extracharges));
				} else {
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.clear1));
					
				}
				
				clearFlag = true;
				destinationFlag = false;
				
				Intent i = new Intent(MainActivity.this, FareCalculation.class);
				startActivity(i);
				finish();
			} else if (clearFlag == true) {
				if ((displayMatrices.widthPixels <= 480)
						|| (displayMatrices.heightPixels <= 320)) {
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.location));
					btnReport.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.report));
					btnMail.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.streetjob));
				} else {
					btnAcceptLocation.setBackgroundDrawable(applicationDrawable
							.ActionButton(R.drawable.location1));
					btnReport.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.report));
					btnMail.setBackgroundDrawable(applicationDrawable
							.Button(R.drawable.streetjob));
					btnExitExtraCharges
							.setBackgroundDrawable(applicationDrawable
									.Button(R.drawable.exit));
				}
				clearFlag = false;
				waitingFlag = false;
				reportFlag = true;
				streetJobFlag = true;
				onDutyFlag = false;
				extraChargesFlag = true;
				exitFlag = true;
				offDutyFlag = true;

			}
		} else if (v == btnGetDirection) {

		} else if (v == btnPrint) {

		}
	}
	
	@SuppressLint({ "DefaultLocale", "NewApi" })
	public void startWaitingTimer() {
		final Handler handler = new Handler();
		Timer ourtimer = new Timer();
		waitingTimer = new TimerTask() {
			public void run() {
				// update waiting time in every 1 second
				handler.post(new Runnable() {
					public void run() {
						n = FareModel.getWaitingTime();
						n++;
						FareModel.setWaitingTime(n);
						long s = n % 60;
						long m = (n / 60) % 60;
						long h = (n / (60 * 60)) % 24;
						String hms = String.format("%d:%02d:%02d", h, m, s);
						textWaitingTime.setText("Waiting Time = " + hms);
					}
				});
			}
		};

		ourtimer.schedule(waitingTimer, 0, 1000);
	}

	public void stopWaitingTimer() {
		if(waitingTimer!=null){
			waitingTimer.cancel();
			waitingTimer = null;
			n = 0;
		}
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
	
}

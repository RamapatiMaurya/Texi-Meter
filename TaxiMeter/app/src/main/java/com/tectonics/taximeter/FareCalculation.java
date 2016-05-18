package com.tectonics.taximeter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tectonics.taximeter.model.FareModel;

@SuppressLint("DefaultLocale")
public class FareCalculation extends Activity {
	TableLayout fareTable;
	TableRow rowWaitingTime, rowWaitingPrice, rowTollPrice, rowParkingPrice,
			rowChallanPrice, rowTipPrice, rowMislPrice, rowPaymentMethod;
	TextView textBaseFare, textWaitingTime, textWaitingPrice, textTollPrice,
			textParkingPrice, textChallanPrice, textTipPrice, textMislPrice,
			textTotalPrice;
	Button buttonOk;

	double totalPrice = 0.0f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_total_fare);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		fareTable = (TableLayout) findViewById(R.id.tableFare);
		rowWaitingTime = (TableRow) findViewById(R.id.rowWaitingTime);
		rowWaitingPrice = (TableRow) findViewById(R.id.rowWaitingPrice);
		rowTollPrice = (TableRow) findViewById(R.id.rowTollPrice);
		rowParkingPrice = (TableRow) findViewById(R.id.rowParkingPrice);
		rowTipPrice = (TableRow) findViewById(R.id.rowTipPrice);
		rowChallanPrice = (TableRow) findViewById(R.id.rowChallanPrice);
		rowMislPrice = (TableRow) findViewById(R.id.rowMislPrice);
		rowPaymentMethod = (TableRow) findViewById(R.id.rowPaymentMode);

		textBaseFare = (TextView) findViewById(R.id.textViewBaseFare);
		textWaitingTime = (TextView) findViewById(R.id.textViewTotalWaiting);
		textWaitingPrice = (TextView) findViewById(R.id.textViewWaitingPrice);
		textTollPrice = (TextView) findViewById(R.id.textViewTollPrice);
		textParkingPrice = (TextView) findViewById(R.id.textViewParkingPrice);
		textChallanPrice = (TextView) findViewById(R.id.textViewChallanPrice);
		textTipPrice = (TextView) findViewById(R.id.textViewTipPrice);
		textMislPrice = (TextView) findViewById(R.id.textViewMislPrice);
		textTotalPrice = (TextView) findViewById(R.id.textViewTotalPrice);

		buttonOk = (Button) findViewById(R.id.buttonPaymentDone);

		// JobReceivedModel modelJob=new JobReceivedModel();
		float initialPrice = FareModel.getInitialCharges();
		float pricePerKm = FareModel.getChargesPerKm();
		int kmUpTo = FareModel.getInitialChargesUpToKm();
		float distance = 0.0f;
		float priceWaiting = FareModel.getWaitingPrice();
		long waitingUpTo = FareModel.getWaitingUpTo();
		long totalWaiting = FareModel.getWaitingTime() / 60;
		float tollPrice = FareModel.getFareTollTax();
		float parkingPrice = FareModel.getFareParking();
		float challanPrice = FareModel.getFareChallan();
		float tipPrice = FareModel.getFareTip();
		float mislPrice = FareModel.getFareMislleneous();

		if (distance > kmUpTo
				&& !FareModel.getFareType().equalsIgnoreCase("Fixed rate")) {
			initialPrice = initialPrice + (distance - kmUpTo) * pricePerKm;
		} else if (FareModel.getFareType().equalsIgnoreCase("Fixed rate")) {
			initialPrice = FareModel.getFarePricePerKm();
		} 

		if (totalWaiting >= waitingUpTo && waitingUpTo!=0) {
			priceWaiting = priceWaiting + (totalWaiting - waitingUpTo)
					* (priceWaiting / waitingUpTo);
		} else {
			priceWaiting = 0.0f;
		}

		textBaseFare.setText(String.valueOf(initialPrice));

		long s = FareModel.getWaitingTime() % 60;
		long m = (FareModel.getWaitingTime() / 60) % 60;
		long h = (FareModel.getWaitingTime() / (60 * 60)) % 24;
		String hms = String.format("%d:%02d:%02d", h, m, s);

		if (totalWaiting < waitingUpTo) {
			fareTable.removeView(rowWaitingTime);
			fareTable.removeView(rowWaitingPrice);
		} else {
			textWaitingTime.setText(hms);
			textWaitingPrice.setText(String.valueOf(priceWaiting));
		}

		if (tollPrice <= 0.0) {
			fareTable.removeView(rowTollPrice);
		} else {
			textTollPrice.setText(String.valueOf(tollPrice));
		}

		if (parkingPrice <= 0.0) {
			fareTable.removeView(rowParkingPrice);
		} else {
			textParkingPrice.setText(String.valueOf(parkingPrice));
		}

		if (challanPrice <= 0.0) {
			fareTable.removeView(rowChallanPrice);
		} else {
			textChallanPrice.setText(String.valueOf(challanPrice));
		}

		if (tipPrice <= 0.0) {
			fareTable.removeView(rowTipPrice);
		} else {
			textTipPrice.setText(String.valueOf(tipPrice));
		}

		if (mislPrice <= 0.0) {
			fareTable.removeView(rowMislPrice);
		} else {
			textMislPrice.setText(String.valueOf(mislPrice));
		}
		if (FareModel.getFareType().equalsIgnoreCase("Prepaid")) {
			fareTable.removeView(rowPaymentMethod);
		}

		totalPrice = initialPrice + priceWaiting + tollPrice + parkingPrice
				+ challanPrice + tipPrice + mislPrice;
		textTotalPrice.setText(String.valueOf(totalPrice));
		buttonOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Payment Success",
						Toast.LENGTH_LONG).show();
				FareModel.setChargesPerKm(0.0f);
				FareModel.setFareChallan(0.0f);
				FareModel.setFareCurrent(0.0f);
				FareModel.setFareMislleneous(0.0f);
				FareModel.setFareParking(0.0f);
				FareModel.setFarePricePerKm(0.0f);
				FareModel.setFareTip(0.0f);
				FareModel.setFareTollTax(0.0f);
				FareModel.setFareTotal(0.0f);
				FareModel.setWaitingTime(0);
				FareModel.setWaitingPrice(0.0f);
				Intent i = new Intent(FareCalculation.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

}

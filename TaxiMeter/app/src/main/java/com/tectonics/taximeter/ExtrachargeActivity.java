package com.tectonics.taximeter;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tectonics.taximeter.model.FareModel;

public class ExtrachargeActivity extends Activity implements OnClickListener
{
	Button btnSave,btnCancle;
	EditText eTollTax,eParking,echallan,eTip,eMislaneous;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
		
		@SuppressWarnings("deprecation")
		int sdkversion=Integer.valueOf(android.os.Build.VERSION.SDK);
		if(sdkversion>10)
		{
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
        setContentView(R.layout.extracharges_activity);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		eTollTax=(EditText)findViewById(R.id.editTextTollTax);
		eParking=(EditText)findViewById(R.id.editTextParking);
		echallan=(EditText)findViewById(R.id.editTextChallan);
		eTip=(EditText)findViewById(R.id.editTextTip);
		eMislaneous=(EditText)findViewById(R.id.editTextMislaneous);
		
		btnCancle=(Button)findViewById(R.id.buttonCancle);
		btnSave=(Button)findViewById(R.id.buttonSave);
		
		btnSave.setOnClickListener(this);		
		btnCancle.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) 
	{
		if(v==btnSave)
		{
//			FareModel fareModel=new FareModel();
			if(eTollTax.getText().toString().equals(null) || eTollTax.getText().toString().equals("")){
				FareModel.setFareTollTax(0.0f);
			}else{
				FareModel.setFareTollTax(Float.parseFloat(eTollTax.getText().toString()));
			}
			
			if(eParking.getText().toString().equals(null) || eParking.getText().toString().equals("")){
				FareModel.setFareParking(0.0f);
			}else{
				FareModel.setFareParking(Float.parseFloat(eParking.getText().toString()));
			}
			
			if(echallan.getText().toString().equals(null) || echallan.getText().toString().equals("")){
				FareModel.setFareChallan(0.0f);
			}else{
				FareModel.setFareChallan(Float.parseFloat(echallan.getText().toString()));
			}
			
			if(eTip.getText().toString().equals(null) || eTip.getText().toString().equals("")){
				FareModel.setFareTip(0.0f);
			}else{
				FareModel.setFareTip(Float.parseFloat(eTip.getText().toString()));
			}
			
			if(eMislaneous.getText().toString().equals(null) || eMislaneous.getText().toString().equals("")){
				FareModel.setFareMislleneous(0.0f);
			}else{
				FareModel.setFareMislleneous(Float.parseFloat(eMislaneous.getText().toString()));
			}
			
			Toast.makeText(getApplicationContext(), "Extra charges saved", Toast.LENGTH_LONG).show();
			finish();
		}
		else
		{
			finish();
		}
	}
}
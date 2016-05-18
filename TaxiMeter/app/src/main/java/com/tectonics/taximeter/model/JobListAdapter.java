package com.tectonics.taximeter.model;

import java.util.ArrayList;

import com.tectonics.taximeter.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JobListAdapter extends BaseAdapter {
	ArrayList<JobsModel> jobListData=new ArrayList<JobsModel>();
	Context context;
	LayoutInflater inflater;
	
	public JobListAdapter(Context context, ArrayList<JobsModel> jobListData){
		this.context=context;
		this.jobListData=jobListData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jobListData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return jobListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("static-access")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
			convertView=inflater.inflate(R.layout.list_job_row, null);
			viewHolder=new ViewHolder();
			viewHolder.textPickupAddress=(TextView) convertView.findViewById(R.id.jobPickupLocation);
			viewHolder.textPickupTime=(TextView) convertView.findViewById(R.id.jobPickupTime);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		JobsModel jobData=jobListData.get(position);
		viewHolder.textPickupAddress.setText(jobData.getPickupAddress());
		viewHolder.textPickupTime.setText(jobData.getPickupTime());
		
		return convertView;
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		if(position==0){
			return false;
		}else{
			return true;
		}
	}
	
	class ViewHolder{
		TextView textPickupAddress;
		TextView textPickupTime;
	}

}

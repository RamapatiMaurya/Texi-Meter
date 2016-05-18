package com.tectonics.taximeter.model;

public class JobReceivedModel {
	public float price;
	public float distance;
	public float waiting;
	public float initialCharges;
	public float regularFare;
	public float fixedFare;
	public float streetFare;
	
	public String pickAt;
	public String pickupTime;
	public String pickupAddress;
	public String dropAddress;
	public String custMobile;
	public String fareType;
	public String custName;
	
	public int waitingUpTo;
	public int upToKm;
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public float getWaiting() {
		return waiting;
	}
	public void setWaiting(float waiting) {
		this.waiting = waiting;
	}
	public float getInitialCharges() {
		return initialCharges;
	}
	public void setInitialCharges(float initialCharges) {
		this.initialCharges = initialCharges;
	}
	public String getPickAt() {
		return pickAt;
	}
	public void setPickAt(String pickAt) {
		this.pickAt = pickAt;
	}
	public String getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}
	public String getPickupAddress() {
		return pickupAddress;
	}
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}
	public String getDropAddress() {
		return dropAddress;
	}
	public void setDropAddress(String dropAddress) {
		this.dropAddress = dropAddress;
	}
	public String getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	public String getFareType() {
		return fareType;
	}
	public void setFareType(String fareType) {
		this.fareType = fareType;
	}
	public int getUpToKm() {
		return upToKm;
	}
	public void setUpToKm(int upToKm) {
		this.upToKm = upToKm;
	}
	public int getWaitingUpTo() {
		return waitingUpTo;
	}
	public void setWaitingUpTo(int waitingUpTo) {
		this.waitingUpTo = waitingUpTo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public float getRegularFare() {
		return regularFare;
	}
	public void setRegularFare(float regularFare) {
		this.regularFare = regularFare;
	}
	public float getFixedFare() {
		return fixedFare;
	}
	public void setFixedFare(float fixedFare) {
		this.fixedFare = fixedFare;
	}
	public float getStreetFare() {
		return streetFare;
	}
	public void setStreetFare(float streetFare) {
		this.streetFare = streetFare;
	}
	

}

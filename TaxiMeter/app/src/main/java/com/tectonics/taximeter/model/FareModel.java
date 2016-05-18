package com.tectonics.taximeter.model;

public class FareModel {
	public static float fareTollTax=0.0f;
	public static float fareParking=0.0f;
	public static float fareChallan=0.0f;
	public static float fareTip=0.0f;
	public static float fareMislleneous=0.0f;
	public static float fareWaiting=0.0f;
	public static float fareCurrent=0.0f;
	public static float fareTotal=0.0f;
	public static float farePricePerKm=0.0f;
	public static long waitingTime=0;
	
	public static float initialCharges=0.0f;
	public static float chargesPerKm=0.0f;
	public static int initialChargesUpToKm=0;
	public static float waitingPrice=0.0f;
	public static int waitingUpTo=0;
	
	public static String fareType="";
	
	public static float getFareTollTax() {
		return fareTollTax;
	}
	public static void setFareTollTax(float fareTollTax) {
		FareModel.fareTollTax = fareTollTax;
	}
	public static float getFareParking() {
		return fareParking;
	}
	public static void setFareParking(float fareParking) {
		FareModel.fareParking = fareParking;
	}
	public static float getFareChallan() {
		return fareChallan;
	}
	public static void setFareChallan(float fareChallan) {
		FareModel.fareChallan = fareChallan;
	}
	public static float getFareTip() {
		return fareTip;
	}
	public static void setFareTip(float fareTip) {
		FareModel.fareTip = fareTip;
	}
	public static float getFareMislleneous() {
		return fareMislleneous;
	}
	public static void setFareMislleneous(float fareMislleneous) {
		FareModel.fareMislleneous = fareMislleneous;
	}
	public static float getFareWaiting() {
		return fareWaiting;
	}
	public static void setFareWaiting(float fareWaiting) {
		FareModel.fareWaiting = fareWaiting;
	}
	public static float getFareCurrent() {
		return fareCurrent;
	}
	public static void setFareCurrent(float fareCurrent) {
		FareModel.fareCurrent = fareCurrent;
	}
	public static float getFareTotal() {
		return fareTotal;
	}
	public static void setFareTotal(float fareTotal) {
		FareModel.fareTotal = fareTotal;
	}
	public static long getWaitingTime() {
		return waitingTime;
	}
	public static void setWaitingTime(long waitingTime) {
		FareModel.waitingTime = waitingTime;
	}
	public static float getInitialCharges() {
		return initialCharges;
	}
	public static void setInitialCharges(float initialCharges) {
		FareModel.initialCharges = initialCharges;
	}
	public static float getChargesPerKm() {
		return chargesPerKm;
	}
	public static void setChargesPerKm(float chargesPerKm) {
		FareModel.chargesPerKm = chargesPerKm;
	}
	public static int getInitialChargesUpToKm() {
		return initialChargesUpToKm;
	}
	public static void setInitialChargesUpToKm(int initialChargesUpToKm) {
		FareModel.initialChargesUpToKm = initialChargesUpToKm;
	}
	public static float getWaitingPrice() {
		return waitingPrice;
	}
	public static void setWaitingPrice(float waitingPrice) {
		FareModel.waitingPrice = waitingPrice;
	}
	public static int getWaitingUpTo() {
		return waitingUpTo;
	}
	public static void setWaitingUpTo(int waitingUpTo) {
		FareModel.waitingUpTo = waitingUpTo;
	}
	public static String getFareType() {
		return fareType;
	}
	public static void setFareType(String fareType) {
		FareModel.fareType = fareType;
	}
	public static float getFarePricePerKm() {
		return farePricePerKm;
	}
	public static void setFarePricePerKm(float farePricePerKm) {
		FareModel.farePricePerKm = farePricePerKm;
	}
	

}

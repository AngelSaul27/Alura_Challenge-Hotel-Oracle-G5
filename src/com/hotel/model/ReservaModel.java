package com.hotel.model;

public class ReservaModel {
	
	private String CheckIn, CheckOut, TypePay;
	private Integer Amount;
	
	public ReservaModel(String CheckInt, String CheckOut, Integer Amount, String TypePay) {
		this.CheckIn = CheckInt;
		this.CheckOut = CheckOut;
		this.Amount = Amount;
		this.TypePay = TypePay;
	}

	public String getCheckIn() {
		return CheckIn;
	}

	public void setCheckIn(String checkIn) {
		CheckIn = checkIn;
	}

	public String getCheckOut() {
		return CheckOut;
	}

	public void setCheckOut(String checkOut) {
		CheckOut = checkOut;
	}

	public String getTypePay() {
		return TypePay;
	}

	public void setTypePay(String typePay) {
		TypePay = typePay;
	}

	public int getAmount() {
		return Amount;
	}

	public void setAmount(Integer amount) {
		Amount = amount;
	}
}

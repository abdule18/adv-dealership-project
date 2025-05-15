package com.pluralsight;

public class SalesContract extends Contract{
    private double salesTax;
    private double recordingFee;
    private double processingFee;
    private boolean finance;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double salesTax, double recordingFee, double processingFee, boolean finance) {
        super(date, customerName, customerEmail, vehicleSold);
        this.salesTax = salesTax;
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
        this.finance = finance;
    }

    @Override
    public double getTotalprice(){
        double basePrice = getVehicleSold().getPrice();
        double taxAmount  = basePrice * salesTax;
        double totalPrice = basePrice + taxAmount + recordingFee +  processingFee;
        return totalPrice;
    }

    @Override
    public double getMonthlyPayment(){
        if (!finance) {
            return 0.0;
        }

        double totalPrice = getTotalPrice();
        int termInMonths = 60;
        double interestRate = 0.05;

        double monthlyPayment = (totalPrice * (interestRate / 12)) / (1 - Math.pow(1  + (interestRate /12), -termInMonths));
        return monthlyPayment;
    }
}



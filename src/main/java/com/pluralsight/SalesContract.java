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

    public double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public boolean isFinance() {
        return finance;
    }

    public void setFinance(boolean finance) {
        this.finance = finance;
    }

    @Override
    public double getTotalPrice(){
        double basePrice = getVehicleSold().getPrice();
        double taxAmount  = basePrice * salesTax;
        return basePrice + taxAmount + recordingFee +  processingFee;

//        double totalPrice = basePrice + taxAmount + recordingFee +  processingFee;
//        return totalPrice;
    }

    @Override
    public double getMonthlyPayment(){
        if (!finance) {
            return 0.0;
        }

        double totalPrice = getTotalPrice();
        int termInMonths = 60;
        double interestRate = 0.05;

        return (totalPrice * (interestRate / 12)) / (1 - Math.pow(1  + (interestRate /12), -termInMonths));
//        double monthlyPayment = (totalPrice * (interestRate / 12)) / (1 - Math.pow(1  + (interestRate /12), -termInMonths));
//        return monthlyPayment;
    }

    public String encodeSalesContract() {
        return "SALE|" +
                getDate() + "|" +
                getCustomerName() + "|" +
                getCustomerEmail() + "|" +
                getVehicleSold().getVin() + "|" +
                getVehicleSold().getYear() + "|" +
                getVehicleSold().getMake() + "|" +
                getVehicleSold().getModel() + "|" +
                getVehicleSold().getVehicleType() + "|" +
                getVehicleSold().getColor() + "|" +
                getVehicleSold().getOdometer() + "|" +
                getVehicleSold().getPrice() + "|" +
                getTotalPrice() + "|" +
                getRecordingFee() + "|" +
                getProcessingFee() + "|" +
                isFinance();
    }
}



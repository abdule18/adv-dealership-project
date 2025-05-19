package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private static Console console = new Console();
    private String fileName;
    private DealershipFileManager manager;

    public void display() {
        init();
        displayHelper();
    }

    private void init(){
        fileName = "inventory.csv";
        this.manager = new DealershipFileManager(fileName);
        this.dealership = manager.getDealership();
    }

    private void displayVehicles(ArrayList<Vehicle> vehicles){
        System.out.println(this.dealership.getFormattedDealershipText());
        for (Vehicle v: vehicles){
            System.out.println(v.getFormattedVehicleText());
        }
    }



    public void displayHelper(){
        String promptUser =
                "1. Get by Price\n" +
                        "2. Get by Make and Model\n" +
                        "3. Get by Year\n" +
                        "4. Get by Color\n" +
                        "5. Get by Mileage\n" +
                        "6. Get by Vehicle Type\n" +
                        "7. Get All Vehicles\n" +
                        "8. Add Vehicle\n" +
                        "9. Remove Vehicle\n" +
                        "10. Sale Vehicle\n" +
                        "11. Lease Vehicle\n" +
                        "0. Exit\n";
        int option;
        do {
            option = console.promptForInt(promptUser);

            switch (option){
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVehicleTypeRequest();
                    break;
                case 7:
                    processGetAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 10:
                    processSellingVehicleRequest();
                    break;
                case 11:
                    processLeasingVehicleRequest();
                    break;
                case 0:
                    System.out.println("Exiting!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }

        } while (option != 0);
    }

    public void processNewContract(){

        //figured out is this a lease or a sale?
        // ask for date, name, email, to select vehicle by vin.
            //if sale
                //figure out if should be financed y/n

    }

    public void processGetByPriceRequest() {
        double min = console.promptForDouble("What's the min price to search for? ");
        double max = console.promptForDouble("What's the max price to search for? ");
        displayVehicles(dealership.getVehiclesByPrice(min,max));

    }
    public void processGetByMakeModelRequest() {
        String make = console.promptForString("What's make to search for? ");
        String model = console.promptForString("What's make to search for? ");
        displayVehicles(dealership.getVehicleByMakeModel(make, model));

    }
    public void processGetByYearRequest() {
        int minYear = console.promptForInt("What's the min year to search for? ");
        int maxYear = console.promptForInt("What's the max year to search for? ");
        displayVehicles(dealership.getVehicleByYear(minYear, maxYear));

    }
    public void processGetByColorRequest() {
        String color = console.promptForString("What's the color to search for? ");
        displayVehicles(dealership.getVehicleByColor(color));
    }
    public void processGetByMileageRequest() {
        int min = console.promptForInt("What's the min mileage to search for? ");
        int max = console.promptForInt("What's the max mileage to search for? ");

        displayVehicles(dealership.getVehicleByMileage(min, max));
    }
    public void processGetByVehicleTypeRequest() {
        String vehicleType = console.promptForString("What's the vehicle type to search for? ");
        displayVehicles(dealership.getVehicleByType(vehicleType));
    }
    public void processGetAllVehiclesRequest() {
        ArrayList<Vehicle> result = dealership.getAllVehicles();
        displayVehicles(result);
    }
    public void processAddVehicleRequest() {
        int vin = console.promptForInt("Enter the VIN: ");
        int year = console.promptForInt("Enter the Year: ");
        String make = console.promptForString("Enter the Make: ");
        String model = console.promptForString("Enter the Model: ");
        String vehicleType = console.promptForString("Enter the Vehicle Type (SUV, Truck, Sedan, etc...): ");
        String color = console.promptForString("Enter the Color: ");
        int odometer = console.promptForInt("Enter the Odometer Reading: ");
        double price = console.promptForDouble("Enter the Price: ");

        // Creating the new Vehicle object
        Vehicle newVehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

        // Adding the new vehicle  to the dealership
        dealership.addVehicle(newVehicle);
        //DealershipFileManager manager = new DealershipFileManager(fileName);
        manager.saveDealership(dealership);
        System.out.println("Vehicle added successfully!");
    }
    public void processRemoveVehicleRequest() {
        int vin = console.promptForInt("Enter the VIN of the vehicle to remove: ");
        Vehicle vehicle = dealership.findVehicleByVin(vin);

        if (vehicle != null) {
            dealership.removeVehicle(vehicle);
            manager.saveDealership(dealership);
            System.out.println("Vehicle removed successfully!");
        } else {
            System.out.println("Vehicle with VIN " + vin + " not found.");
        }
    }

    public void processSellingVehicleRequest(){
        System.out.println("========== SELLING A VEHICLE ==========");
        String date = console.promptForString("Enter Date: ");
        String customerName = console.promptForString("Enter Name: ");
        String customerEmail = console.promptForString("Enter Email: ");
        int vehicleVin = console.promptForInt("Enter Vin: ");

        Vehicle v = dealership.findVehicleByVin(vehicleVin);

        if (v ==  null){
            System.out.println("Vehicle not found!");
            return;
        }

        // 2️⃣ Prompt for additional sales information
        double salesTax = console.promptForDouble("Enter Sales Tax: ");
        double recordingFee = console.promptForDouble("Enter Recording Fee: ");
        double processingFee = console.promptForDouble("Enter Processing Fee: ");
        boolean finance = console.promptForString("Is the vehicle financed? (yes/no): ").equalsIgnoreCase("yes");

        SalesContract salesContract = new SalesContract(
                date,
                customerName,
                customerEmail,
                v,
                salesTax,
                recordingFee,
                processingFee,
                finance
        );

        ContractDataManager manager1 = new ContractDataManager();
        manager1.saveContract(salesContract);

        dealership.removeVehicle(v);

        manager.saveDealership(dealership);

        System.out.println("Vehicle sold and contract saved successfully!");



    }

    public void processLeasingVehicleRequest(){
        System.out.println("========== SELLING A VEHICLE ==========");
        String date = console.promptForString("Enter Date: ");
        String customerName = console.promptForString("Enter Name: ");
        String customerEmail = console.promptForString("Enter Email: ");
        int vehicleVin = console.promptForInt("Enter Vin: ");

        Vehicle v = dealership.findVehicleByVin(vehicleVin);

        if (v ==  null){
            System.out.println("Vehicle not found!");
            return;
        }

        LeaseContract leaseContract = new LeaseContract(
                date,
                customerName,
                customerEmail,
                v
        );

        ContractDataManager manager1 = new ContractDataManager();
        manager1.saveContract(leaseContract);

        dealership.removeVehicle(v);
        manager.saveDealership(dealership);

        System.out.println("Vehicle sold and contract saved successfully!");
    }
}

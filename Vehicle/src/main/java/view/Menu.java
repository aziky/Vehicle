package view;

import controller.Status;
import controller.Validation;
import controller.VehicleManage;

public class Menu {

    Validation valid = new Validation();
    VehicleManage vehicleManage = new VehicleManage();

    public static final String line = "-----------------------";

    public Menu() {
    }

    
    public void execute() {
        int userChoice;
        do {
            System.out.println(line);
            System.out.println("1. Add new vehicle.");
            System.out.println("2. Check exits Vehicle.");
            System.out.println("3. Update vehicle.");
            System.out.println("4. Delete vehicle.");
            System.out.println("5. Search vehicle.");
            System.out.println("6. Display all vehicles.");
            System.out.println("7. Save all vehicles to file.");
            System.out.println("8. Print all vehicles from the file.");
            System.out.println("9. Quit.");
            userChoice = valid.checkInt("Enter your choice: ", 1, 9 , Status.NONE);

            switch (userChoice) {
                case 1:
                    System.out.println(line);
                    vehicleManage.addVehicle();
                    break;

                case 2:
                    System.out.println(line);
                    vehicleManage.checkExistVehicle();
                    break;

                case 3:
                    System.out.println(line);
                    vehicleManage.updateVehicle();
                    break;
                case 4:
                    System.out.println(line);
                    vehicleManage.deleteVehicle();
                    break;
                case 5:
                    System.out.println(line);

                    System.out.println("1. Search vehicle by ID");
                    System.out.println("2. Search vehicle by name");
                    System.out.println("3. Back to main menu");
                    int inputSearch = valid.checkInt("Enter your choice: ", 1, 3 ,Status.NONE);

                    if (inputSearch == 1) {
                        System.out.println(line);
                        vehicleManage.searchListById();
                    } else if (inputSearch == 2) {
                        System.out.println(line);
                        vehicleManage.searchListByName();
                    }

                    break;
                case 6:
                    System.out.println(line);
                    System.out.println("1. Display all vehicles");
                    System.out.println("2. Display all vehicles by price");
                    System.out.println("3. Back to main menu");
                    int inputDisplay = valid.checkInt("Enter your choice: ", 1, 3 , Status.NONE);

                    if (inputDisplay == 1) {
                        System.out.println(line);
                        vehicleManage.displayAll();
                    } else if (inputDisplay == 2) {
                        System.out.println(line);
                        vehicleManage.displayAllByPrice();
                    }
                    break;
                case 7:
                    System.out.println(line);
                    vehicleManage.saveData();
                    break;
                case 8:
                    System.out.println(line);
                    System.out.println("1. Print all vehicles");
                    System.out.println("2. Print all vehicles by price");
                    System.out.println("3. Back to main menu");
                    int inputPrint = valid.checkInt("Enter your choice: ", 1, 3 , Status.NONE);

                    if (inputPrint == 1) {
                        System.out.println(line);
                        vehicleManage.printAllData();
                    } else if (inputPrint == 2) {
                        System.out.println(line);
                        vehicleManage.printDataByPrice();
                    }

                    break;
                case 9:
                    System.out.println("Bye bye");
                    break;
            }

        } while (userChoice != 9);
    }
}

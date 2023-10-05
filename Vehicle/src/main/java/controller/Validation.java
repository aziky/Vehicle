package controller;

// check input 
import java.util.List;
import java.util.Scanner;


import vehicle.Vehicle;

public class Validation {

    private static final Scanner sc = new Scanner(System.in);
    private static String RESET = "\u001B[0m";
    private static String RED = "\u001B[31m";

    public String checkString(String msg, Status status) {
        while (true) {
            System.out.print(msg);
            String str = sc.nextLine().trim().toLowerCase();


            if (status.equals(Status.UPDATE) && str.isBlank()) {
                return str;
            }

            if (str == null || str.isBlank()) {
                System.err.println("\nMust input a string not empty !!!");
                System.out.println("Please enter again!\n");
                continue;
            }

            return str;
        }
    }

    public int checkInt(String msg, int min, int max, Status status) {
        while (true) {
            String inputNum = checkString(msg, status);
            if (inputNum.isBlank() && status.equals(Status.UPDATE)) {
                return -1;
            } else {
                try {
                    int num = Integer.parseInt(inputNum);
                    if (num < min || num > max) {
                        System.err.println("The number must be between from  " + min + " to " + max + "\n");
                        continue;
                    }
                    return num;
                } catch (NumberFormatException e) {
                    System.err.println(RED + "It must be a number!\n" + RESET );
                }
            }

        }
    }


    public double checkDouble(String msg, double min, double max, Status status) {
        while (true) {
            String inputNum = checkString(msg, status);

            if (inputNum.isBlank() && status.equals(Status.UPDATE)) {
                return -1;
            }

            try {
                double num = Double.parseDouble(inputNum);
                if (num < min || num > max) {
                    System.err.println("The number must be between from  " + min + " to " + max);
                    continue;
                }
                return num;
            } catch (Exception e) {
                System.out.println("It must be a number!");

            }

        }
    }

    public int checkYear(String msg, Status status) {

        while (true) {
            String year = checkString(msg, status);
            String pattern = "\\d{4}";
            if (year.isBlank() && status.equals(Status.UPDATE)) {
                return -1;
            }
            if (year.matches(pattern)) {
                return Integer.parseInt(year);
            }
            System.err.println("The input must be XXXX with X is a digit");
        }
    }

    public boolean checkYesOrNo(String msg, Status status) {
        while (true) {
            String check = checkString(msg, status);
            if (check.equalsIgnoreCase("Y")) {
                return true;
            } else if (check.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.err.println("Must input Y or N to select option");
                continue;
            }
        }
    }
    public String checkID(String msg, Status status, List<Vehicle> list, List<Vehicle> listFromFile){
        String ID;
        while (true) {
             ID = checkString(msg, status);
            String pattern = "\\d{4}";
            if(!ID.matches(pattern)){
                System.out.println("Incorrect format! ID must have (XXXX) format with X is a digits");
                continue;
            }
            boolean isUnique = true;
            for (Vehicle v : list){
                if (ID.equals(v.getId())){
                    isUnique = false;
                }
            }

            for(Vehicle v : listFromFile){
                if (ID.equals(v.getId())){
                    isUnique = false;
                }
            }

            if (!isUnique){
                System.out.println("ID already exists!");
            }else{
                break;
            }
        }
        return ID;
    }

    public boolean checkDuplicateId(String id, List<Vehicle> list) {
        if (list.isEmpty()) return false;
        while (true) {
            for (Vehicle vehicle : list) {
                if (id.equals(vehicle.getId())) {
                    return true;
                }
            }
            return false;
        }
    }


}

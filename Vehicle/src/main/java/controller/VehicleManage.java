package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import vehicle.Vehicle;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleManage {

    public List<Vehicle> listVehicle;
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    // List<Vehicle> list = new ArrayList<>(); tao danh sach

    Validation valid = new Validation();

    public VehicleManage() {
        listVehicle = new ArrayList<>();
    }

    public Vehicle inputVehicle(Status status) {
        while (true) {
            String ID = valid.checkID("Enter vehicle ID: ", status, listVehicle, loadData());
            String name = valid.checkString("Enter vehicle name: ", status);
            String color = valid.checkString("Enter vehicle color: ", status);
            double price = valid.checkDouble("Enter vehicle price: ", 0, Double.MAX_VALUE, status);
            String brand = valid.checkString("Enter vehicle brand: ", status);
            String type = valid.checkString("Enter vehicle type: ", status);
            int productYear = valid.checkYear("Enter vehicle product year: ", status); // 2022
            Vehicle newVehicle = new Vehicle(ID, name, color, price, brand, type, productYear);
            System.out.println(GREEN + "Successful added!" + RESET);
            return newVehicle;
        }
    }

    public void addVehicle() {
        while (true) {
            Vehicle vehicle = inputVehicle(Status.ADD);
            if (vehicle == null) {
                System.out.println(RED + "Failed to add. Id already exists!" + RESET);
                return;
            }
            listVehicle.add(vehicle);
            boolean checkYesNo = valid.checkYesOrNo("Do you want to continue adding new vehicle (Y/N): ", Status.NONE);
            if (checkYesNo == false) {
                return;
            }
            System.out.println();
        }
    }

    public Vehicle search(String id) {
        for (Vehicle vehicle : listVehicle) {
            if (id.equals(vehicle.getId())) {
                return vehicle;
            }
        }
        return null;
    }

    public void checkExistVehicle() {
        if (listVehicle.isEmpty()) {
            System.out.println("Don't have any vehicles in showroom!");
            return;
        }
        String id = valid.checkString("Enter vehicle id you want to check: ", Status.NONE); // goi thi moi nhap id
        if (search(id) == null) {
            System.out.println(RED + "No Vehicle found!" + RESET);
        } else {
            System.out.println(GREEN + "Exist Vehicle" + RESET);
        }
    }

    public Vehicle updateVehicle(Vehicle oldVehicle, Vehicle newVehicle) {
        if (newVehicle.getId().isBlank()) {
            newVehicle.setId(oldVehicle.getId());
        }

        if (newVehicle.getName().isBlank()) {
            newVehicle.setName(oldVehicle.getName());
        }
        if (newVehicle.getColor().isBlank()) {
            newVehicle.setColor(oldVehicle.getColor());
        }

        if (newVehicle.getPrice() == -1) {
            newVehicle.setPrice(oldVehicle.getPrice());
        }
        if (newVehicle.getBrand().isBlank()) {
            newVehicle.setBrand(oldVehicle.getBrand());
        }
        if (newVehicle.getType().isBlank()) {
            newVehicle.setType(oldVehicle.getType());
        }
        if (newVehicle.getProductYear() == -1) {
            newVehicle.setProductYear(oldVehicle.getProductYear());
        }
        oldVehicle = newVehicle;
        return oldVehicle;
    }

    public void updateVehicle() {
        if (listVehicle.isEmpty()) {
            System.out.println("Can't delete. Don't have any vehicles in showroom!");
            return;
        }

        String id = valid.checkString("Enter vehicle id to update: ", Status.NONE); // nhap id trong
        if (search(id) == null) {
            System.out.println(RED + "No vehicle found" + RESET);
            return;
        }
        Vehicle oldVehicle = search(id);
        System.out.println("Leave it blank to not change old information");
        Vehicle newVehicle = inputVehicle(Status.UPDATE);
        Vehicle vehicle = updateVehicle(oldVehicle, newVehicle);

        listVehicle.remove(oldVehicle);
        listVehicle.add(vehicle);
        System.out.println("New information of old product");
        System.out.println("================VEHICLE LIST==============");
        System.out.printf("%-20s%-20s%-20s%-15s%-15s%-15s%-25s\n", "ID", "NAME", "COLOR", "PRICE", "BRAND", "TYPE",
                "PRODUCT YEAR");
        printVehicle(vehicle);

    }

    public void deleteVehicle() {
        if (listVehicle.isEmpty()) {
            System.out.println(RED + "Can't delete. Don't have any vehicles in showroom!" + RESET);
            return;
        }
        String id = valid.checkString("Enter vehicle id to delete: ", Status.NONE); // nhap id trong
        if (search(id) == null) {
            System.out.println(RED + "Failed to delete. No vehicle found!" + RESET);
            return;
        }
        boolean checkYesNo = valid.checkYesOrNo("Do you want to delete vehicle with " + id + " (Y/N): ", Status.NONE);
        if (checkYesNo) {
            listVehicle.remove(search(id));
            System.out.println(GREEN + "Successful deleted" + RESET);
        } else {
            System.out.println("Didn't delete anything");
        }
    }

    public void searchListByName() {
        if (listVehicle.isEmpty()) {
            System.out.println(RED + "Don't have any vehicles in showroom!" + RESET);
            return;
        }
        String name = valid.checkString("Enter vehicle name to search: ", Status.NONE);
        List<Vehicle> listName = new ArrayList<>();
        for (Vehicle v : listVehicle) {
            if (v.getName().toLowerCase().contains(name.toLowerCase())) {
                listName.add(v);
            }
        }
        Collections.sort(listName, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return -o1.getName().compareTo(o2.getName());
            }
        });

        if (listName.isEmpty()) {
            System.out.println("Nothing!");
        }

        showAll(listName);
    }

    public void searchListById() {
        if (listVehicle.isEmpty()) {
            System.out.println(RED + "Don't have any vehicles in showroom!" + RESET);
            return;
        }
        String id = valid.checkString("Enter vehicle id to check: ", Status.NONE);
        List<Vehicle> listID = new ArrayList<>();
        for (Vehicle v : listVehicle) {
            if (v.getId().toLowerCase().contains(id.toLowerCase())) {
                listID.add(v);
            }
        }
        if (listID.isEmpty()) {
            System.out.println("Nothing!");
            return;
        }

        showAll(listID);
    }

    public void displayAll() {
        if (listVehicle.isEmpty()) {
            System.out.println(RED + "Don't have any vehicles in showroom!" + RESET);
            return;
        }
        System.out.println("================VEHICLE LIST==============");
        System.out.printf("%-15s%-15s%-20s%-15s%-15s%-20s%-25s\n", "ID", "NAME", "COLOR", "PRICE", "BRAND", "TYPE",
                "PRODUCT YEAR");
        for (Vehicle a : listVehicle) {
            printVehicle(a);
        }
    }

    public void displayAllByPrice() {
        if (listVehicle.isEmpty()) {
            System.out.println(RED + "Don't have any vehicles to show in showroom!" + RESET);
            return;
        }
        Collections.sort(listVehicle, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return -Double.compare(o1.getPrice(), o2.getPrice());
            }
        });

        showAll(listVehicle);
    }

    public void saveData() {
        if (listVehicle.isEmpty()) {
            System.out.println("Nothing to save");
            return;
        }

        try {
            File fileName = new File("vehicle.dat");

            if (!fileName.exists()) {
                fileName.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            StringBuilder fileContent = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }

            bufferedReader.close();

            for (Vehicle v : listVehicle) {
                fileContent.append(v).append("\n");
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write(fileContent.toString());
            bufferedWriter.close();
            System.out.println(GREEN + "Success" + RESET);

        } catch (IOException ex) {
            System.err.println(RED + "Can't save" + RESET);
        }

    }

    public List<Vehicle> loadData() {
        List<Vehicle> listFromFile = new ArrayList<>();
        File fileName = new File("vehicle.dat");
        
        if (!fileName.exists()){
            return null;
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(line, ",");
                String id = stk.nextToken().trim();
                String name = stk.nextToken().trim();
                String color = stk.nextToken().trim();
                double price = Double.parseDouble(stk.nextToken().trim());
                String brand = stk.nextToken().trim();
                String type = stk.nextToken().trim();
                int productYear = Integer.parseInt(stk.nextToken().trim());
                Vehicle vehicle = new Vehicle(id, name, color, price, brand, type, productYear);
                listFromFile.add(vehicle);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VehicleManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VehicleManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listFromFile;
    }

    public void printAllData() {
        List<Vehicle> list = new ArrayList<>(loadData());
        if (list.isEmpty()) {
            System.out.println("Nothing to print out from file");
            return;
        }
        showAll(list);
    }

    public void printDataByPrice() {
        List<Vehicle> list = new ArrayList<>(loadData());

        Collections.sort(list, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return -Double.compare(o1.getPrice(), o2.getPrice());
            }
        });

        if (list.isEmpty()) {
            System.out.println("Nothing to print out from file");
            return;
        }

        showAll(list);
    }

    public void printVehicle(Vehicle a) {
        System.out.printf("%-15s%-15s%-20s%-15s%-15s%-20s%-25s\n", a.getId(), a.getName(), a.getColor(), a.getPrice(),
                a.getBrand(), a.getType(), a.getProductYear());
    }

    public void showAll(List<Vehicle> list) {
        System.out.println("================VEHICLE LIST==============");
        System.out.printf("%-15s%-15s%-20s%-15s%-15s%-20s%-25s\n", "ID", "NAME", "COLOR", "PRICE", "BRAND", "TYPE",
                "PRODUCT YEAR");
        for (Vehicle a : list) {
            printVehicle(a);
        }
    }

}

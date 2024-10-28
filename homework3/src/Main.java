//Emily Dutton, Homework#3
//This program is a menu-driven payroll program that calculates pay for three types of employees during a pay period.
//The program displays payroll information for each employee type, including employee ID, name, salary/rate, hours/projects, paid status, and pay.
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

abstract class Employee {
    private int _id;
    private String _name;
    public Employee(){}
    public Employee(int x, String s1){
        _id = x;
        _name = s1;
    }
    public void setId(int id){_id = id;}
    public void setName(String n){_name = n;}
    public int getId(){return _id;}
    public String getName(){return _name;}
    public abstract double calculatePay();
    public abstract boolean equals(Employee e);
    public String toString(){
        return "EmployeeID:" +_id + " " +  "Name:" + _name + " ";
    }
}
class FullTimeEmp extends Employee {
    private double _salary;
    private boolean _paid;
    public FullTimeEmp(){
        _paid = false;
    }
    public FullTimeEmp(int x, String s1, double s){
        super(x,s1);
        _salary = s;
        _paid = false;
    }
    public void setSalary(double x) {_salary = x;}
    public void setPaid(boolean b){_paid = b;}
    public double getSalary(){return _salary;}
    public boolean getPaid(){return _paid;}
    @Override
    public double calculatePay() {
        double biMonthPay = _salary / 24;
        setPaid(true);
        return biMonthPay;
    }
    @Override
    public boolean equals(Employee e) {
        FullTimeEmp emp = (FullTimeEmp) e;
        if(getId() == emp.getId()
                && getName().equals(emp.getName())
                && getSalary() == emp.getSalary()){
            return true;
        } else {
            return false;
        }
    }
    public String toString(){
        String s = super.toString() + "Salary:" + _salary + " " + "Paid:" + _paid + " ";
        return s;
    }
}
class PartTimeEmp extends Employee {
    private double _hoursWorked;
    private double _hourlyPay;
    private boolean _paid;
    public PartTimeEmp(){
        _paid = false;
    }
    public PartTimeEmp(int x, String s1, double hrW, double hrP){
        super(x,s1);
        _hoursWorked = hrW;
        _hourlyPay = hrP;
        _paid = false;
    }
    public void setHourlyPay(double hrP) {_hourlyPay = hrP;}
    public void setHoursWorked(double hrW) {_hoursWorked = hrW;}
    public void setPaid(boolean b){_paid = b;}
    public double getHourlyPay(){return _hourlyPay;}
    public double getHoursWorked(){return _hoursWorked;}
    public boolean getPaid(){return _paid;}
    public double calculatePay() {
        double weeklyPay = _hoursWorked * _hourlyPay;
        setPaid(true);
        return weeklyPay;
    }
    @Override
    public boolean equals(Employee e) {
        PartTimeEmp emp = (PartTimeEmp) e;
        if(getId() == emp.getId()
                && getName().equals(emp.getName())
                && getHourlyPay() == emp.getHourlyPay()
            && getHoursWorked() == emp.getHoursWorked()){
            return true;
        } else {
            return false;
        }
    }
    public String toString(){
        String s = super.toString() + "HourlyRate:" + _hourlyPay
                + " " + "HoursWorked:" + _hoursWorked + " " + "Paid:" + _paid + " ";;
        return s;
    }
}
class Contractor extends Employee {
    private int _numProjects;
    private double _payRate;
    private boolean _paid;
    public Contractor(){
        _paid = false;
    }
    public Contractor(int x, String s1, int p, double r){
        super(x,s1);
        _numProjects = p;
        _payRate = r;
        _paid = false;
    }
    public void setNumProjects(int p) {_numProjects = p;}
    public void setPayRate(double r) {_payRate = r;}
    public void setPaid(boolean b) {_paid = b;}
    public int getNumProjects() {return _numProjects;}
    public double getPayRate() {return _payRate;}
    public boolean getPaid() {return _paid;}
    public double calculatePay() {
        double totalPay = _numProjects * _payRate;
        setPaid(true);
        return totalPay;
    }
    @Override
    public boolean equals(Employee e) {
        Contractor emp = (Contractor) e;
        if(getId() == emp.getId()
                && getName().equals(emp.getName())
                && getNumProjects() == emp.getNumProjects()
                && getPayRate() == emp.getPayRate())
                {
            return true;
        } else {
            return false;
        }
    }
    public String toString(){
        String s = super.toString() + "NumProjects:" + _numProjects
                + " " + "PayRate:" + _payRate + " " + "Paid:" + _paid;
        return s;
    }
}

public class Main {
    private static ArrayList<Employee> employees = new ArrayList<>();
    private static void populateEmp(String file) {
        boolean paid = false;
        try{
            Scanner scan = new Scanner(new File(file));
            while (scan.hasNextLine()){
                String[] line = scan.nextLine().split(",");
                int empType = Integer.parseInt(line[0]);
                if ( empType == 1){
                    int id = Integer.parseInt(line[1]);
                    String name = line[2];
                    double salary = Double.parseDouble(line[3]);
                    employees.add(new FullTimeEmp(id, name, salary));
                } else if (empType == 2) {
                    int id = Integer.parseInt(line[1]);
                    String name = line[2];
                    double hoursWorked = Double.parseDouble(line[3]);
                    double hourlyPay = Double.parseDouble(line[4]);
                    employees.add(new PartTimeEmp(id, name, hoursWorked, hourlyPay));
                } else if (empType == 3) {
                    int id = Integer.parseInt(line[1]);
                    String name = line[2];
                    int projects = Integer.parseInt(line[3]);
                    double payRate = Double.parseDouble(line[4]);
                    employees.add(new Contractor(id, name, projects, payRate));
                }
            }
        } catch (Exception e){
            System.out.println("Problem reading in file");
            System.exit(-1);
        }
    }
    private static void payrollMenu() {
        Scanner scan = new Scanner(System.in);
        int userChoice = 0;

        System.out.println("Employee Payroll Menu:");
        System.out.println("1. Calculate pay for full time employees");
        System.out.println("2. Calculate pay for part time employees");
        System.out.println("3. Calculate pay for contractors");
        System.out.println("Enter a number or -1 to exit program:");
        userChoice = scan.nextInt();
        while (userChoice != -1){
            if (userChoice == 1) {
                System.out.println("Full time employees:");
                calcFTPay();
            } else if (userChoice == 2) {
                System.out.println("Part time employees:");
                calcPTPay();
            } else if (userChoice == 3) {
                System.out.println("Contractors:");
                calcCTPay();
            }
            System.out.println("Enter a number or -1 to exit program:");
            userChoice = scan.nextInt();
        }
    }
    private static void calcFTPay() {
        for (int i = 0; i < employees.size(); i++){
            Employee e = employees.get(i);
            if (employees.get(i) instanceof FullTimeEmp){
                double pay = e.calculatePay();
                System.out.println(e + " " + "Pay:" + String.format("%.2f",pay));
            }
        }
    }
    private static void calcPTPay() {
        for (int i = 0; i < employees.size(); i++){
            Employee e = employees.get(i);
            if (employees.get(i) instanceof PartTimeEmp){
                double pay = e.calculatePay();
                System.out.println(e + " " + "Pay:" + String.format("%.2f", pay));
            }
        }
    }
    private static void calcCTPay() {
        for (int i = 0; i < employees.size(); i++){
            Employee e = employees.get(i);
            if (employees.get(i) instanceof Contractor){
                double pay = e.calculatePay();
                System.out.println(e + " " + "Pay:" + String.format("%.2f",pay));
            }
        }
    }
    public static void main(String[] args) throws IOException {
        populateEmp("employees.txt");
        payrollMenu();
    }
}


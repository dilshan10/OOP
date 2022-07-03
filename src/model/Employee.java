package model;

import java.sql.Date;

public class Employee {
    private String Emp_ID;
    private String User_ID;
    private String Emp_Name;
    private String Emp_Address;
    private String DOB;
    private double Salery;

    public Employee(String string, String resultString, String emp_name, String emp_address, Date date, double aDouble) {
    }

    public Employee(String emp_ID, String user_ID, String emp_Name, String emp_Address, String DOB, double salery) {
        Emp_ID = emp_ID;
        User_ID = user_ID;
        Emp_Name = emp_Name;
        Emp_Address = emp_Address;
        this.DOB = DOB;
        Salery = salery;
    }

    public String getEmp_ID() {
        return Emp_ID;
    }

    public void setEmp_ID(String emp_ID) {
        Emp_ID = emp_ID;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getEmp_Name() {
        return Emp_Name;
    }

    public void setEmp_Name(String emp_Name) {
        Emp_Name = emp_Name;
    }

    public String getEmp_Address() {
        return Emp_Address;
    }

    public void setEmp_Address(String emp_Address) {
        Emp_Address = emp_Address;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public double getSalery() {
        return Salery;
    }

    public void setSalery(double salery) {
        Salery = salery;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Emp_ID='" + Emp_ID + '\'' +
                ", User_ID='" + User_ID + '\'' +
                ", Emp_Name='" + Emp_Name + '\'' +
                ", Emp_Address='" + Emp_Address + '\'' +
                ", DOB='" + DOB + '\'' +
                ", Salery=" + Salery +
                '}';
    }
}

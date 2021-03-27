package com.example.demo1;

public class Model_Teacher {

    String Name,DOB,Gender,Phone_No,Email_Id,
            Blood_Group,Mother_Name,Father_Name,Parent_Phone_No,
            Cast,Address,Local_Address,Teacher_Id_No,Department,
            Married,Wife_or_Husband_Name,Joining_Date;

    public Model_Teacher() {
    }

    public Model_Teacher(String name, String DOB, String gender, String phone_No, String email_Id, String blood_Group, String mother_Name, String father_Name, String parent_Phone_No, String cast, String address, String local_Address, String teacher_Id_No, String department, String married, String wife_or_Husband_Name, String joining_Date) {
        Name = name;
        this.DOB = DOB;
        Gender = gender;
        Phone_No = phone_No;
        Email_Id = email_Id;
        Blood_Group = blood_Group;
        Mother_Name = mother_Name;
        Father_Name = father_Name;
        Parent_Phone_No = parent_Phone_No;
        Cast = cast;
        Address = address;
        Local_Address = local_Address;
        Teacher_Id_No = teacher_Id_No;
        Department = department;
        Married = married;
        Wife_or_Husband_Name = wife_or_Husband_Name;
        Joining_Date = joining_Date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String phone_No) {
        Phone_No = phone_No;
    }

    public String getEmail_Id() {
        return Email_Id;
    }

    public void setEmail_Id(String email_Id) {
        Email_Id = email_Id;
    }

    public String getBlood_Group() {
        return Blood_Group;
    }

    public void setBlood_Group(String blood_Group) {
        Blood_Group = blood_Group;
    }

    public String getMother_Name() {
        return Mother_Name;
    }

    public void setMother_Name(String mother_Name) {
        Mother_Name = mother_Name;
    }

    public String getFather_Name() {
        return Father_Name;
    }

    public void setFather_Name(String father_Name) {
        Father_Name = father_Name;
    }

    public String getParent_Phone_No() {
        return Parent_Phone_No;
    }

    public void setParent_Phone_No(String parent_Phone_No) {
        Parent_Phone_No = parent_Phone_No;
    }

    public String getCast() {
        return Cast;
    }

    public void setCast(String cast) {
        Cast = cast;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLocal_Address() {
        return Local_Address;
    }

    public void setLocal_Address(String local_Address) {
        Local_Address = local_Address;
    }

    public String getTeacher_Id_No() {
        return Teacher_Id_No;
    }

    public void setTeacher_Id_No(String teacher_Id_No) {
        Teacher_Id_No = teacher_Id_No;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getMarried() {
        return Married;
    }

    public void setMarried(String married) {
        Married = married;
    }

    public String getWife_or_Husband_Name() {
        return Wife_or_Husband_Name;
    }

    public void setWife_or_Husband_Name(String wife_or_Husband_Name) {
        Wife_or_Husband_Name = wife_or_Husband_Name;
    }

    public String getJoining_Date() {
        return Joining_Date;
    }

    public void setJoining_Date(String joining_Date) {
        Joining_Date = joining_Date;
    }
}

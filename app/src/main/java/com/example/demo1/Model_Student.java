package com.example.demo1;

public class Model_Student {

    String Name,DOB,Gender,Course,Branch,Section,Admission_Year,
            College_RollNo,University_RollNo,Phone_No,Email_Id,
            Blood_Group,Mother_Name,Father_Name,Parent_Phone_No,
            Cast,Address,Local_Address;

    public Model_Student() {
    }

    public Model_Student(String name, String DOB, String gender, String course, String branch, String section, String admission_Year, String college_RollNo, String university_RollNo, String phone_No, String email_Id, String blood_Group, String mother_Name, String father_Name, String parent_Phone_No, String cast, String address, String local_Address) {
        this.Name = name;
        this.DOB = DOB;
        this.Gender = gender;
        this.Course = course;
        this.Branch = branch;
        this.Section = section;
        this.Admission_Year = admission_Year;
        this.College_RollNo = college_RollNo;
        this.University_RollNo = university_RollNo;
        this.Phone_No = phone_No;
        this.Email_Id = email_Id;
        this.Blood_Group = blood_Group;
        this.Mother_Name = mother_Name;
        this.Father_Name = father_Name;
        this.Parent_Phone_No = parent_Phone_No;
        this.Cast = cast;
        this.Address = address;
        this.Local_Address = local_Address;
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

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getAdmission_Year() {
        return Admission_Year;
    }

    public void setAdmission_Year(String admission_Year) {
        Admission_Year = admission_Year;
    }

    public String getCollege_RollNo() {
        return College_RollNo;
    }

    public void setCollege_RollNo(String college_RollNo) {
        College_RollNo = college_RollNo;
    }

    public String getUniversity_RollNo() {
        return University_RollNo;
    }

    public void setUniversity_RollNo(String university_RollNo) {
        University_RollNo = university_RollNo;
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
}

package com.example.demo1;

public class Model_teacher_class {

String Admission_Year,Branch,Course,Number_of_lectures,Section,Subject_Name;

    public Model_teacher_class() {
    }

    public Model_teacher_class(String admission_Year, String branch, String course, String number_of_lectures, String section, String subject_Name) {
        Admission_Year = admission_Year;
        Branch = branch;
        Course = course;
        Number_of_lectures = number_of_lectures;
        Section = section;
        Subject_Name = subject_Name;
    }


    public String getAdmission_Year() {
        return Admission_Year;
    }

    public void setAdmission_Year(String admission_Year) {
        Admission_Year = admission_Year;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getNumber_of_lectures() {
        return Number_of_lectures;
    }

    public void setNumber_of_lectures(String number_of_lectures) {
        Number_of_lectures = number_of_lectures;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getSubject_Name() {
        return Subject_Name;
    }

    public void setSubject_Name(String subject_Name) {
        Subject_Name = subject_Name;
    }
}

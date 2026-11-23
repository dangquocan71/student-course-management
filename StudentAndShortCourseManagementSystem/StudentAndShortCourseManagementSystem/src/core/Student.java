/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.util.Objects;

/**
 *
 * @author MINH THU
 */
public class Student {
    String studentID;
    String studentName;
    String major;
    double gpa;

    public Student(String studentID, String studentName, String major, double gpa) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.major = major;
        this.gpa = gpa;
    }

    // 1-arg constructor used by indexOf() search helper
    public Student(String studentID) {
        this.studentID = studentID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.studentID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Student other = (Student) obj;
        return Objects.equals(this.studentID, other.studentID);
    }

    public String getStudentID() { return studentID; }
    public void setStudentID(String studentID) { this.studentID = studentID; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    @Override
    public String toString() {
        return studentID + ", " + studentName + ", " + major + ", " + gpa;
    }
} // class Student

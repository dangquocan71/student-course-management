/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author MINH THU
 */
public class Course {
    String courseID;
    String studentID;
    String courseName;
    int durationWeeks;
    Date startDate;

    public Course(String courseID, String studentID, String courseName, int durationWeeks, Date startDate) {
        this.courseID = courseID;
        this.studentID = studentID;
        this.courseName = courseName;
        this.durationWeeks = durationWeeks;
        this.startDate = startDate;
    }

    // 1-arg constructor used by indexOf() search helper
    public Course(String courseID) {
        this.courseID = courseID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.courseID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Course other = (Course) obj;
        return Objects.equals(this.courseID, other.courseID);
    }

    public String getCourseID() { return courseID; }
    public void setCourseID(String courseID) { this.courseID = courseID; }

    public String getStudentID() { return studentID; }
    public void setStudentID(String studentID) { this.studentID = studentID; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getDurationWeeks() { return durationWeeks; }
    public void setDurationWeeks(int durationWeeks) { this.durationWeeks = durationWeeks; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    @Override
    public String toString() {
        return courseID + ", " + studentID + ", " + courseName + ", " + durationWeeks + ", " + startDate;
    }
} // class Course

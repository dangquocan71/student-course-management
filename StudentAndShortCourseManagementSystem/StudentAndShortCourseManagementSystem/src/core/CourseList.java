/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import tool.ConsoleInputter;

/**
 *
 * @author MINH THU
 */
public class CourseList extends ArrayList<Course> {
    static String ID_PAT    = "^[A-Z]{2,6}[0-9]{3}$";
    static String NOT_EMPTY = "^.+$";
    static String DATE_PAT  = "dd/MM/yyyy";

    // Helper: Find position by course ID (uses Course.equals on courseID)
    public int indexOf(String courseID) {
        return super.indexOf(new Course(courseID));
    }

    // Helper: Check whether any course record is assigned to a given student ID
    /*
    loop through Course using for;if getstudentID equal studentID, return true, flase outside for loop
    */
    public boolean containsStudentID(String studentID) {
        for (Course c : this) {
            if (c.getStudentID().equalsIgnoreCase(studentID)) {
                return true;
            }
        }
        return false;
    }

    // Helper: Get all courses for a given student ID
    /*
    Call ArrayList of Class Course , you wanna get result so call the name result = new ArrayList<>();
    loop through Course c using for(), check if studentId.equalIgnoreCase(studentID), then add to result;
    out of for loop then return result;
    */
    public ArrayList<Course> getByStudentID(String studentID) {
        ArrayList<Course> result = new ArrayList<>();
        for (Course c : this) {
            if (c.getStudentID().equalsIgnoreCase(studentID)) {
                result.add(c);
            }
        }
        return result;
    }

    /* HUONG DAN / REWRITE HINT:
 * Function: Add one or more courses; return true if at least one was added.
 * Steps: if studentList.isEmpty() print message, return false
 * -> boolean addedAny = false -> boolean cont -> do {
 * 1) Course ID: String courseID -> int pos -> do { courseID = getStr("Course ID", NOT_EMPTY, msg).toUpperCase()
 *    -> pos = indexOf(courseID) -> if (pos >= 0) print duplicate } while (pos >= 0)
 * 2) Student menu: sout "Select a student" -> String line = "-----" -> sout line
 *    -> printf header "%-5s %-10s %-30s %-25s %-5s%n" (No., ID, Name, Major, GPA) -> sout line
 *    -> for (int i = 0; i < studentList.size(); i++) { Student s = studentList.get(i)
 *       -> printf row "%-5d %-10s %-30s %-25s %-5.1f%n" (i + 1, id, name, major, gpa) } -> sout line
 * 3) Pick student: int studentChoice = getInt("Choose student number", 1, studentList.size())
 *    -> Student selectedStudent = studentList.get(studentChoice - 1)
 *    -> String studentID = selectedStudent.getStudentID()
 * 4) Other fields: String courseName = getStr("Course name", NOT_EMPTY, msg)
 *    -> int durationWeeks = getInt("Duration", 1, Integer.MAX_VALUE)
 *    -> Date startDate -> do { startDate = getDate("Start date", DATE_PAT)
 *       -> if (!startDate.after(new Date())) print "must be future" } while (!startDate.after(new Date()))
 * 5) Save: this.add(new Course(courseID, studentID, courseName, durationWeeks, startDate))
 *    -> addedAny = true -> print success -> cont = getBoolean("Do you want to continue adding a new course")
 * } while (cont) -> return addedAny.
 * IMPORTANT: Student ID is chosen from the menu (index = choice - 1), so it is always valid.
 * Two loops: inner = unique course ID / future date, outer = "add another?".
 */
    public boolean addCourse(StudentList studentList) {
        if (studentList.isEmpty()) {
            System.out.println("No students available. Please add a student first!");
            return false;
        }

        boolean addedAny = false;
        boolean cont;
        do {
            // Enter and validate Course ID (must be unique)
            String courseID;
            int pos;
            do {
                courseID = ConsoleInputter.getStr("Course ID (e.g., PRO192)", NOT_EMPTY,
                        "Course ID cannot be empty!").toUpperCase();
                pos = this.indexOf(courseID);
                if (pos >= 0)
                    System.out.println("Course ID already exists! Please enter a different ID.");
            } while (pos >= 0);

            // Student ID must be selected from a menu of existing students
            System.out.println("\nSelect a student from the list below:");
            String line = "-------------------------------------------------------------------";
            System.out.println(line);
            System.out.printf("%-5s %-10s %-30s %-25s %-5s%n", "No.", "ID", "Name", "Major", "GPA");
            System.out.println(line);
            for (int i = 0; i < studentList.size(); i++) {
                Student s = studentList.get(i);
                System.out.printf("%-5d %-10s %-30s %-25s %-5.1f%n",
                        (i + 1), s.getStudentID(), s.getStudentName(), s.getMajor(), s.getGpa());
            }
            System.out.println(line);
            int studentChoice = ConsoleInputter.getInt("Choose student number", 1, studentList.size());
            Student selectedStudent = studentList.get(studentChoice - 1);
            String studentID = selectedStudent.getStudentID();

            String courseName = ConsoleInputter.getStr("Course name", NOT_EMPTY, "Course name cannot be empty!");

            int durationWeeks = ConsoleInputter.getInt("Duration (weeks, minimum 1)", 1, Integer.MAX_VALUE);

            Date startDate;
            do {
                startDate = ConsoleInputter.getDate("Start date (" + DATE_PAT + ")", DATE_PAT);
                if (!startDate.after(new Date()))
                    System.out.println("Start date must be a future date!");
            } while (!startDate.after(new Date()));

            this.add(new Course(courseID, studentID, courseName, durationWeeks, startDate));
            addedAny = true;
            System.out.println("New course was added successfully.");

            cont = ConsoleInputter.getBoolean("Do you want to continue adding a new course");
        } while (cont);

        return addedAny;
    }

    // Function 7: List all courses by student (grouped)
    /* * HUONG DAN / REWRITE HINT:
 * Function: Display courses grouped by student.
 * Steps: if (studentList.isEmpty()) print message and return -> String bigLine = "-----"
 * -> for (Student s : studentList) { ArrayList<Course> courses = getByStudentID(s.getStudentID())
 * -> print student ID + name -> if (courses.isEmpty()) print "(No courses assigned)"
 * -> else print course header -> for (Course c : courses) printf c.getCourseID(), c.getCourseName(),
 *    c.getDurationWeeks(), ConsoleInputter.dateStr(c.getStartDate(), DATE_PAT) } -> print line.
 */
    public void listCoursesByStudent(StudentList studentList) {
        if (studentList.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }

        String bigLine = "-------------------------------------------------------------------";
        System.out.println("\nList of Courses by Student (Grouped)");

        for (Student s : studentList) {
            ArrayList<Course> courses = getByStudentID(s.getStudentID());
            System.out.println(bigLine);
            System.out.println("Student: " + s.getStudentID() + " - " + s.getStudentName());
            System.out.println(bigLine);
            if (courses.isEmpty()) {
                System.out.println("  (No courses assigned)");
            } else {
                System.out.printf("  %-12s %-25s %-12s %-15s%n",
                        "Course ID", "Course Name", "Duration(w)", "Start Date");
                System.out.println("  " + "-------------------------------------------------------------");
                for (Course c : courses) {
                    System.out.printf("  %-12s %-25s %-12d %-15s%n",
                            c.getCourseID(),
                            c.getCourseName(),
                            c.getDurationWeeks(),
                            ConsoleInputter.dateStr(c.getStartDate(), DATE_PAT));
                }
            }
        }
        System.out.println(bigLine);
    }

    // Function 8: Calculate total study duration by student ID
    public void totalDurationByStudentID() {
        String studentID = ConsoleInputter.getStr("Enter Student ID").toUpperCase();
        ArrayList<Course> courses = getByStudentID(studentID);
        if (courses.isEmpty()) {
            System.out.println("No courses found for Student ID: " + studentID);
            return;
        }
        int total = 0;
        for (Course c : courses) {
            total += c.getDurationWeeks();
        }
        System.out.println("Total study duration for Student " + studentID + ": " + total + " week(s).");
    }

    // Load course data from text file
    /*
   ***
    */
    public void readFile(String fName) {  
        File f = new File(fName);
        if (!f.exists()) {
            System.out.println("The file " + fName + " does not exist. Ignored.");
            return;
        }
        this.clear();
        java.text.DateFormat formatter = new SimpleDateFormat(DATE_PAT);
        formatter.setLenient(false);
        try(BufferedReader br= new BufferedReader(new FileReader(f))){
           String line;
           while((line=br.readLine())!=null){
               line=line.trim();
               if(line.isEmpty()){
                   continue;
               }
               String parts[]=line.split(",");
               if(parts.length<5){
                   continue;
               }
               String courseid=parts[0].trim();
               String studentid=parts[1].trim();
               String coursename=parts[2].trim();
               int duration = 0;
               Date startDate = null;
               try{
                   duration=Integer.parseInt(parts[3].trim());
                   startDate=formatter.parse(parts[4].trim());
               }catch(Exception e){
                   System.out.println("All data in field readed");
               }
                this.add(new Course(courseid, studentid, coursename, duration, startDate));
           }
       }catch(IOException e){
           System.out.println(e.getMessage());
       }
    }

    // Save course data to text file
    /*
    try(call BufferedWriter = new BufferedWriter(new FileWriter(fName))){
        loop through Course c this using for;
        bw.write(getCourseID() + ","+ getStudentID() +", "+getCourseName()+", "+getDurationWeeks()+","+formatter.format(getStartDate()));
        bw.newLine();
        out of for loop, sout Student data saved to +fName;
    }catch(IOException)
        sout err
    */
    public void writeFile(String fName) {
        java.text.DateFormat formatter = new SimpleDateFormat(DATE_PAT);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fName))) {
            for (Course c : this) {
                bw.write(c.getCourseID() + ", " + c.getStudentID() + ", "
                        + c.getCourseName() + ", " + c.getDurationWeeks() + ", "
                        + formatter.format(c.getStartDate()));
                bw.newLine();
            }
            System.out.println("Course data saved to " + fName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
} // class CourseList

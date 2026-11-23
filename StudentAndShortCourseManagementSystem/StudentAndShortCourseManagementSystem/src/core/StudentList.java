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
import java.util.Comparator;
import tool.ConsoleInputter;

/**
 *
 * @author MINH THU
 */
public class StudentList extends ArrayList<Student> {
    static String ID_PAT    = "^STU[0-9]{4}$";
    static String NAME_PAT  = "^([a-zA-ZÀ-ỹ]+ ){1,}[a-zA-ZÀ-ỹ]+$";
    static String NOT_EMPTY = "^.+$";
    static String DATE_PAT  = "dd/MM/yyyy";

    // Helper: Find position by student ID (uses Student.equals on studentID)
    public int indexOf(String studentID) {
        return super.indexOf(new Student(studentID));
    }

    // Function 1: List all students in a formatted table
    /*
    check if empty, sout "No students in the system", retrun;
    String line="-----------------";
    UI: "\nList of All Students"; line;"%-10s %-30s %-25s %-5s%n", "ID", "Name", "Major", "GPA";line;
    loop through Student s using for loop{ sout "%-10s %-30s %-25s %-5.1f%n", getStudentID,studentName,getmajor,getgpa};line
    */
    public void listAllStudents() {
        if (this.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }
        String line = "-------------------------------------------------------------------";
        System.out.println("\nList of All Students");
        System.out.println(line);
        System.out.printf("%-10s %-30s %-25s %-5s%n", "ID", "Name", "Major", "GPA");
        System.out.println(line);
        for (Student s : this) {
            System.out.printf("%-10s %-30s %-25s %-5.1f%n",
                    s.getStudentID(), s.getStudentName(), s.getMajor(), s.getGpa());
        }
        System.out.println(line);
    }

    // Function 2: Add a new student
    /*
    Call boolean addedAny and cont;
    do{
        call variable studentID and pos;
        do{
            input studentID and pos;
            check if pos>=0, then sout "Student ID already exists! Please enter a different ID.";
        while(pos>=0);
        call variable studentName, major,gpa; studentName cannot be empty and must contain at least 2 chars; major cannot be empty; gpa must be 0.0 and 4.0;
        call new Student s= new Student(); and add to the list this.add; before changing addedAny condition
        sout "New student was added successfully.", before cont input asking them if "Do you want to continue adding a new student";
    while(cont);
    return addedAny;    
    */
    public boolean addStudent() {
        boolean addedAny = false;
        boolean cont;
        do {
            String studentID;
            int pos;
            do {
                studentID = ConsoleInputter.getStr("Student ID (format: STU0000)", ID_PAT,
                        "Student ID must follow the format STU0000!").toUpperCase();
                pos = this.indexOf(studentID);
                if (pos >= 0)
                    System.out.println("Student ID already exists! Please enter a different ID.");
            } while (pos >= 0);

            String studentName = ConsoleInputter.getStr("Full name (at least 2 words)", NAME_PAT,
                    "Full name cannot be empty and must contain at least two words!");
            String major = ConsoleInputter.getStr("Major", NOT_EMPTY, "Major cannot be empty!");
            double gpa = ConsoleInputter.getDouble("GPA", 0.0, 4.0);

            this.add(new Student(studentID, studentName, major, gpa));
            addedAny = true;
            System.out.println("New student was added successfully.");

            cont = ConsoleInputter.getBoolean("Do you want to continue adding a new student");
        } while (cont);

        return addedAny;
    }

    // Function 3: Search for a student by ID
    /*
    call variable studentID,pos, "Enter student ID to search",check if pos<0 then sout "Student ID does not exist!";
    else, list through Student s=this.get(pos);
    UI:
    String line="-----------------------------"
    line;"%-10s %-30s %-25s %-5s%n", "ID", "Name", "Major", "GPA";line;"%-10s %-30s %-25s %-5.1f%n";line
    */
    public void searchByID() {
        String studentID = ConsoleInputter.getStr("Enter Student ID to search").toUpperCase();
        int pos = this.indexOf(studentID);
        if (pos < 0) {
            System.out.println("Student ID does not exist!");
        } else {
            Student s = this.get(pos);
            String line = "-------------------------------------------------------------------";
            System.out.println(line);
            System.out.printf("%-10s %-30s %-25s %-5s%n", "ID", "Name", "Major", "GPA");
            System.out.println(line);
            System.out.printf("%-10s %-30s %-25s %-5.1f%n",
                    s.getStudentID(), s.getStudentName(), s.getMajor(), s.getGpa());
            System.out.println(line);
        }
    }

    // Function 4: Update a student's GPA by ID
    /*
    Call studentID,pos, asking them to Enter student ID to udate GPA;
    if(pos<0), then sout "Student ID does not exist! return false;
    list throught Student s=this.get(pos); Enter new GPA and setgpa; then sout "Student GPA updated successfully.";return true'
    */
    public boolean updateGPA() {
        String studentID = ConsoleInputter.getStr("Enter Student ID to update GPA").toUpperCase();
        int pos = this.indexOf(studentID);
        if (pos < 0) {
            System.out.println("Student ID does not exist!");
            return false;
        }
        Student s = this.get(pos);
        double newGpa = ConsoleInputter.getDouble("Enter new GPA", 0.0, 4.0);
        s.setGpa(newGpa);
        System.out.println("Student GPA updated successfully.");
        return true;
    }

    // Function 5: List all students by major
    /*
    call variable major, console input, cannot be empty;
    call ArrayList class Student call result = new ArrayList;
    loop throught Student s using for, check if major.equalignorecase then adding to result list;
    out of for loop, check if result is empty, sout No students found for major: +major,return;
    String line="-------------------------";
    UI:
    "\nStudents in major: " + major;line;"%-10s %-30s %-25s %-5s%n", "ID", "Name", "Major", "GPA";line;
    loop through Student s of result list then sout "%-10s %-30s %-25s %-5.1f%n";out of for loop;line;
    */
    public void listByMajor() {
        String major = ConsoleInputter.getStr("Enter major to search", NOT_EMPTY, "Major cannot be empty!");
        ArrayList<Student> result = new ArrayList<>();
        for (Student s : this) {
            if (s.getMajor().equalsIgnoreCase(major)) {
                result.add(s);
            }
        }

        if (result.isEmpty()) {
            System.out.println("No students found for major: " + major);
            return;
        }
        String line = "-------------------------------------------------------------------";
        System.out.println("\nStudents in major: " + major);
        System.out.println(line);
        System.out.printf("%-10s %-30s %-25s %-5s%n", "ID", "Name", "Major", "GPA");
        System.out.println(line);
        for (Student s : result) {
            System.out.printf("%-10s %-30s %-25s %-5.1f%n",
                    s.getStudentID(), s.getStudentName(), s.getMajor(), s.getGpa());
        }
        System.out.println(line);
    }

    // Function 9: Remove a student by ID (cannot delete if assigned to courses)
    /*
    call variable studentID, pos, asking them to Enter student ID to remove;
    check if pos<0, then sout Student ID does not exist, return false;
    else if(course list contains student id then sout Cannot delte: student is assigned to courses, return false
    else boolean confirm , asking them are you sure you want to remove this student;
    if confirm then remove, sout student removed successfully, return true else sout removal cancelled then opposite of true;
    */
    public boolean removeStudent(CourseList courseList) {
        String studentID = ConsoleInputter.getStr("Enter Student ID to remove").toUpperCase();
        int pos = this.indexOf(studentID);
        if (pos < 0) {
            System.out.println("Student ID does not exist!");
            return false;
        } else if (courseList.containsStudentID(studentID)) {
            System.out.println("Cannot delete: Student is assigned to Courses.");
            return false;
        } else {
            boolean confirm = ConsoleInputter.getBoolean("Are you sure you want to remove this student?");
            if (confirm) {
                this.remove(pos);
                System.out.println("Student removed successfully.");
                return true;
            } else {
                System.out.println("Removal cancelled.");
                return false;
            }
        }
    }

    // Function 10: Sort students by GPA in ascending order
    /*
    check if empty then sout No students in the system, return;
    call ArrayList class Student name sorted because you wanna sort new arraylist this;
    sort(comparator.comparingDouble(Student::getGpa));
    */
    public void sortByGPA() {
        if (this.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }
        ArrayList<Student> sorted = new ArrayList<>(this);
        sorted.sort(Comparator.comparingDouble(Student::getGpa));

        String line = "-------------------------------------------------------------------";
        System.out.println("\nStudents sorted by GPA (Ascending)");
        System.out.println(line);
        System.out.printf("%-10s %-30s %-25s %-5s%n", "ID", "Name", "Major", "GPA");
        System.out.println(line);
        for (Student s : sorted) {
            System.out.printf("%-10s %-30s %-25s %-5.1f%n",
                    s.getStudentID(), s.getStudentName(), s.getMajor(), s.getGpa());
        }
        System.out.println(line);
    }

    // Load student data from text file
    /*
   ception was  gpa double.parsedouble(parts[].trim())
    }catch(IOException)
    */
    public void readFile(String fName) {
       File f= new File(fName);
       if(!f.exists()){
           System.out.println("The file "+fName+" does not exist");
           return;
       }
       this.clear();
       try(BufferedReader br= new BufferedReader(new FileReader(f))){
           String line;
           while((line=br.readLine())!=null){
               line=line.trim();
               if(line.isEmpty()){
                   continue;
               }
               String parts[]=line.split(",");
               if(parts.length<4){
                   continue;
               }
               String id=parts[0].trim();
               String name=parts[1].trim();
               String major=parts[2].trim();
               double gpa = 0;
               try{
                   gpa=Double.parseDouble(parts[3].trim());
               }catch(Exception e){
                   System.out.println("All field was read");
               }
               this.add(new Student(id, name, major, gpa));
           }
       }catch(IOException e){
           System.out.println(e.getMessage());
       }
    }

    // Save student data to text file
    /*
    try(call BufferedWriter = new BufferedWriter(new FileWriter(fName))){
        loop through Student s this using for;
        bw.write(getStudentID() + ","+ getStudentName() +", "+getMajor()+", "+getGpa());
        bw.newLine();
        out of for loop, sout Student data saved to +fName;
    }catch(IOException)
        sout err
    */
    public void writeFile(String fName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fName))) {
            for (Student s : this) {
                bw.write(s.getStudentID() + ", " + s.getStudentName() + ", "
                        + s.getMajor() + ", " + s.getGpa());
                bw.newLine();
            }
            System.out.println("Student data saved to " + fName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
} // class StudentList

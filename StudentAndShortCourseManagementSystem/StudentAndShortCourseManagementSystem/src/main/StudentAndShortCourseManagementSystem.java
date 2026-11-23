/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import core.CourseList;
import core.StudentList;
import tool.ConsoleInputter;
/*
   call studentFile and courseFile which is "Students.txt";"Courses.txt" repspectively;
   call new StudentList name it studentList; your new studentList readfile , read the studentFile, same along woth courseList
   Object[] mnuOptions={}; inside /*num*/ /*function*/;
/*
    int choice, call studentChanged and courseChanged as boolean and set it as false;
    String title \nSTUDENT AND SHORT COURSE MANAGEMENT SYSTEM\n---------------------------------------------------
do{
    sout title, make choice able to input, intMenu(mnuOptions);
    switch case 1 to 12 no default case with conditions of studentchanged and coursechanged changed to opposite with things like add or update;
    on case 12 check if studentChanged or courseChanged; boolean savenow asked do you wan to save the change before exist (y/n);
    if(savenow) then if studentChanged studentList.writeFile(studentFile), if courseChanged thhen applied the same;sout goodbye! then break;
}while(choice not equal 12);
*/
public class StudentAndShortCourseManagementSystem {

    public static void main(String[] args) {
        // File names used by the program
        String studentFile = "Students.txt";
        String courseFile  = "Courses.txt";

        // Load both lists from file at startup
        StudentList studentList = new StudentList();
        studentList.readFile(studentFile);

        CourseList courseList = new CourseList();
        courseList.readFile(courseFile);

        // Menu options
        Object[] mnuOptions = { /*1*/  "List all students",
                                /*2*/  "Add a new student",
                                /*3*/  "Search for a student by ID",
                                /*4*/  "Update a student by ID",
                                /*5*/  "List all students by major",
                                /*6*/  "Add a new course",
                                /*7*/  "List all courses by student (grouped)",
                                /*8*/  "Calculate total study duration by student ID",
                                /*9*/  "Remove a student by ID",
                                /*10*/ "Sort students by GPA",
                                /*11*/ "Save data to files",
                                /*12*/ "Quit" };

        int choice;
        // Track unsaved changes
        boolean studentChanged = false;
        boolean courseChanged  = false;
        String title = "\nSTUDENT AND SHORT COURSE MANAGEMENT SYSTEM\n---------------------------------------------------";

        // Main program loop
        do {
            System.out.println(title);
            choice = ConsoleInputter.intMenu(mnuOptions);
            switch (choice) {
                case 1:
                    studentList.listAllStudents();
                    break;
                case 2:
                    if (studentList.addStudent()) {
                        studentChanged = true;
                    }
                    break;
                case 3:
                    studentList.searchByID();
                    break;
                case 4:
                    if (studentList.updateGPA()) {
                        studentChanged = true;
                    }
                    break;
                case 5:
                    studentList.listByMajor();
                    break;
                case 6:
                    if (courseList.addCourse(studentList)) {
                        courseChanged = true;
                    }
                    break;
                case 7:
                    courseList.listCoursesByStudent(studentList);
                    break;
                case 8:
                    courseList.totalDurationByStudentID();
                    break;
                case 9:
                    if (studentList.removeStudent(courseList)) {
                        studentChanged = true;
                    }
                    break;
                case 10:
                    studentList.sortByGPA();
                    break;
                case 11:
                    studentList.writeFile(studentFile);
                    courseList.writeFile(courseFile);
                    studentChanged = false;
                    courseChanged  = false;
                    break;
                case 12:
                    if (studentChanged || courseChanged) {
                        boolean saveNow = ConsoleInputter.getBoolean(
                                "Do you want to save the changes before exiting? (Y/N)");
                        if (saveNow) {
                            if (studentChanged) studentList.writeFile(studentFile);
                            if (courseChanged)  courseList.writeFile(courseFile);
                        }
                    }
                    System.out.println("Good bye!");
                    break;
            }
        } while (choice != 12);
    } // main
} // class StudentAndShortCourseManagementSystem

// Write your code here
package com.example.school.repository;

import com.example.school.model.Student;
import java.util.*;

public interface StudentRepository{
    ArrayList<Student> allStudents();
    Student addStudent(Student student);
    String addStudents(ArrayList<Student> student);
    Student getStudent(int studentId);
    Student updateStudent(int studentId, Student student);
    void deleteStudent(int studentId);
    
    
}
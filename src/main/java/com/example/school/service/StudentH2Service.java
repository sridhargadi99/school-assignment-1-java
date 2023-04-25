/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 *
 */

// Write your code here
package com.example.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.school.model.Student;
import com.example.school.model.StudentRowMapper;
import com.example.school.repository.StudentRepository;

@Service
public class StudentH2Service implements StudentRepository{
    @Autowired
    private JdbcTemplate db;

    @Override 
    public ArrayList<Student> allStudents(){
        List<Student> studentCollection = db.query("SELECT * FROM STUDENT", new StudentRowMapper());
        ArrayList<Student> students = new ArrayList<>(studentCollection);
        return students;
    }

    @Override 
    public Student addStudent(Student student){
        db.update("INSERT INTO STUDENT(studentName,gender,standard) VALUES(?,?,?)", student.getStudentName(), student.getGender(), student.getStandard());
        Student studentDetails = db.queryForObject("SELECT * FROM STUDENT WHERE studentName = ? AND gender = ? AND standard = ? ", new StudentRowMapper(), student.getStudentName(), student.getGender(), student.getStandard());
        return studentDetails;
    }

    @Override 
    public String addStudents(ArrayList<Student> student){
        for(Student eachStudent : student){
            db.update("INSERT INTO STUDENT(studentName,gender,standard) VALUES(?,?,?)", eachStudent.getStudentName(), eachStudent.getGender(), eachStudent.getStandard());
        }
        int noOfStudents = student.size();
        return String.format("Successfully added %d students",noOfStudents);
    }

    @Override 
    public Student getStudent(int studentId){
        try{
            Student studenDetails = db.queryForObject("SELECT * FROM STUDENT WHERE studentId = ?", new StudentRowMapper(), studentId);
            return studenDetails;
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override 
    public Student updateStudent(int studentId, Student student){
        if(student.getStudentName() != null){
            db.update("UPDATE STUDENT SET studentName = ? WHERE studentId = ?", student.getStudentName(), studentId);
        }
        if(student.getGender() != null){
            db.update("UPDATE STUDENT SET gender = ? WHERE studentId = ?", student.getGender(), studentId);
        }
        if(student.getStandard() != 0){
            db.update("UPDATE STUDENT SET standard = ? WHERE studentId = ?", student.getStandard(), studentId);
        }
        return getStudent(studentId);
    }

    @Override 
    public void deleteStudent(int studentId){
        db.update("DELETE FROM STUDENT WHERE studentId = ?", studentId);
    }




}



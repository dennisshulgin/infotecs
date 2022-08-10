package com.shulgin.services;

import com.shulgin.entity.Student;

import java.util.List;

public interface StudentService {
    void addStudent(Student student);

    void addStudents(List<Student> student);

    List<Student> findStudentByName(String name);

    Student findStudentById(int id);

    void deleteStudentById(int id);

    List<Student> findAllStudents();
}

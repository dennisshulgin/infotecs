package com.shulgin.services;

import com.shulgin.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {

    private final List<Student> students = new ArrayList<>();

    @Override
    public void addStudent(Student student) {
        int id = getNextId();
        student.setId(id);
        this.students.add(student);
    }

    @Override
    public void addStudents(List<Student> students) {
        this.students.addAll(students);
    }

    @Override
    public List<Student> findStudentByName(String name) {
        return this.students.stream()
                .filter(student -> student.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public Student findStudentById(int id) {
        return this.students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteStudentById(int id) {
        Student delStudent = this.students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
        students.remove(delStudent);
    }

    @Override
    public List<Student> findAllStudents() {
        return this.students;
    }

    private int getNextId() {
        return this.students.stream()
                .mapToInt(Student::getId)
                .max().
                orElse(0) + 1;
    }
}

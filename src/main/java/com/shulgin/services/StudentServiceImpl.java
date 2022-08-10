package com.shulgin.services;

import com.shulgin.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {

    private final List<Student> students;

    public StudentServiceImpl(List<Student> students) {
        this.students = students;
    }

    @Override
    public synchronized void addStudent(Student student) {
        int id = getNextId();
        student.setId(id);
        this.students.add(student);
    }

    @Override
    public synchronized void addStudents(List<Student> students) {
        this.students.addAll(students);
    }

    @Override
    public synchronized List<Student> findStudentByName(String name) {
        return this.students.stream()
                .filter(student -> student.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized Student findStudentById(int id) {
        return this.students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized void deleteStudentById(int id) {
        this.students.remove(id);
    }

    @Override
    public synchronized List<Student> findAllStudents() {
        return this.students;
    }

    private int getNextId() {
        return this.students.stream()
                .mapToInt(Student::getId)
                .max().
                orElse(0) + 1;
    }
}

package com.shulgin.services;

import com.shulgin.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс реализует набор методов для работы с данными студентов.
 */
public class StudentServiceImpl implements StudentService {

    private final List<Student> students = new ArrayList<>();

    /**
     * Метод добавления студентов.
     * @param student новый экземпляр студента, содеражщий имя.
     */
    @Override
    public void addStudent(Student student) {
        // генерируем id для новой записи
        int id = getNextId();
        student.setId(id);
        this.students.add(student);
    }

    /**
     * Метод добавления нескольких студентов.
     * @param students добавление нескольких студентов.
     */
    @Override
    public void addStudents(List<Student> students) {
        this.students.addAll(students);
    }

    /**
     * Поиск студента по имени.
     * @param name имя студента.
     * @return список студентов.
     */
    @Override
    public List<Student> findStudentByName(String name) {
        return this.students.stream()
                .filter(student -> student.getName().equals(name))
                .collect(Collectors.toList());
    }

    /**
     * Поиск студента по ИД.
     * @param id идентификатор студента.
     * @return студент.
     */
    @Override
    public Student findStudentById(int id) {
        return this.students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Удаление студента по ид.
     * @param id идентификатор студента.
     */
    @Override
    public void deleteStudentById(int id) {
        Student delStudent = this.students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
        students.remove(delStudent);
    }

    /**
     * Найти всех студентов.
     * @return студенты.
     */
    @Override
    public List<Student> findAllStudents() {
        return this.students;
    }

    /**
     * Метод генрации нового ИД.
     * Ищет максимальный номер в листе и генерирует следующий.
     * @return номер.
     */
    private int getNextId() {
        return this.students.stream()
                .mapToInt(Student::getId)
                .max().
                orElse(0) + 1;
    }
}

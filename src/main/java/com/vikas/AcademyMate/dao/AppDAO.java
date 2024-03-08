package com.vikas.AcademyMate.dao;

import com.vikas.AcademyMate.entity.Course;
import com.vikas.AcademyMate.entity.Instructor;
import com.vikas.AcademyMate.entity.InstructorDetail;
import com.vikas.AcademyMate.entity.Student;

import java.util.List;

public interface AppDAO {
    void save(Instructor instructor);

    Instructor findInstructorById(int id);

    void deleteInstructorById(int id);

    InstructorDetail findInstructorDetailById(int id);

    void deleteInstructorDetailById(int id);

    List<Course> findCourseByInstructorId(int id);

    Instructor findInstructorByIdJoinFetch(int id);

    void update(Instructor newInstructor);

    void update(Course newCourse);

    Course findCourseById(int id);

    void deleteCourseById(int id);

    void save(Course course);

    Course findCourseAndReviewsByCourseId(int id);

    Course findCourseAndStudentsByCourseId(int id);

    Student findStudentAndCoursesByStudentId(int id);

    void update(Student student);

    void deleteStudentById(int id);
}

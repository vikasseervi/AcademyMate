package com.vikas.AcademyMate.dao;

import com.vikas.AcademyMate.entity.Course;
import com.vikas.AcademyMate.entity.Instructor;
import com.vikas.AcademyMate.entity.InstructorDetail;
import com.vikas.AcademyMate.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{

    // define field for entity manager
    private EntityManager entityManager;

    // inject entity manager using constructor injection
    @Autowired
    public AppDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int id) {
        Instructor instructor = entityManager.find(Instructor.class, id);
        List<Course> courses = instructor.getCourses();
        // break associations of all courses for instructor
        for(Course course : courses){
            course.setInstructor(null);
        }
        entityManager.remove(instructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        return entityManager.find(InstructorDetail.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int id) {
        InstructorDetail instructorDetail = findInstructorDetailById(id);
        // remove the associated object reference
        // break bi-direction link
        instructorDetail.getInstructor().setInstructorDetail(null);
        entityManager.remove(instructorDetail);
    }

    @Override
    public List<Course> findCourseByInstructorId(int id) {
        // create query
        TypedQuery<Course> query = entityManager.createQuery(
                "from Course where instructor.id = :data", Course.class);
        query.setParameter("data", id);
        // execute query
        List<Course> courses = query.getResultList();
        return courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int id) {
        // create query
        TypedQuery<Instructor> query = entityManager.createQuery(
                "select inst from Instructor inst " +
                        "JOIN FETCH inst.courses " +
                        "JOIN FETCH inst.instructorDetail " +
                        "where inst.id = :data", Instructor.class);
        query.setParameter("data", id);
        // execute query

        Instructor instructor = query.getSingleResult();
        return instructor;
    }

    @Override
    @Transactional
    public void update(Instructor newInstructor) {
        entityManager.merge(newInstructor);
    }

    @Override
    @Transactional
    public void update(Course newCourse) {
        entityManager.merge(newCourse);
    }

    @Override
    public Course findCourseById(int id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        Course course = entityManager.find(Course.class, id);
        entityManager.remove(course);
    }

    @Override
    @Transactional
    public void save(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c " +
                        "JOIN FETCH c.reviews "+
                        "where c.id = :data", Course.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c " +
                        "JOIN FETCH c.students "+
                        "where c.id = :data", Course.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Override
    public Student findStudentAndCoursesByStudentId(int id) {
        TypedQuery<Student> query = entityManager.createQuery(
                "select s from Student s " +
                        "JOIN FETCH s.courses "+
                        "where s.id = :data", Student.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Override
    @Transactional
    public void deleteStudentById(int id) {
        Student student = entityManager.find(Student.class, id);
        entityManager.remove(student);
    }

}

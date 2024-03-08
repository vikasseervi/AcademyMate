package com.vikas.AcademyMate;

import com.vikas.AcademyMate.dao.AppDAO;
import com.vikas.AcademyMate.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;

@SpringBootApplication
public class AcademyMateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademyMateApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {
			// createCourseAndStudent(appDAO);
			// findCourseAndStudents(appDAO);
			// findStudentAndCourses(appDAO);
			// addMoreCoursesForStudent(appDAO);
			// deleteCourse(appDAO);
			// deleteStudent(appDAO);
		};
	}

	private void deleteStudent(AppDAO appDAO) {
		int id = 1;
		System.out.println("Deleting student id: " + id);
		appDAO.deleteStudentById(id);
		System.out.println("DONE!");
	}

	private void addMoreCoursesForStudent(AppDAO appDAO) {
		int id = 2;
		Student student = appDAO.findStudentAndCoursesByStudentId(id);
		Course course1 = new Course("Rubik's Cube - How to speed cube");
		Course course2 = new Course("Atari 2600 - Game Development");
		student.addCourse(course1);
		student.addCourse(course2);
		System.out.println("Updated student: " + student);
		System.out.println("Courses: " + student.getCourses());
		appDAO.update(student);
		System.out.println("DONE!");
	}

	private void findStudentAndCourses(AppDAO appDAO) {
		int id = 1;
		Student student = appDAO.findStudentAndCoursesByStudentId(id);
		System.out.println("Student: " + student);
		System.out.println("Courses: " + student.getCourses());
		System.out.println("DONE!");

	}

	private void findCourseAndStudents(AppDAO appDAO) {
		int id = 10;
		Course course = appDAO.findCourseAndStudentsByCourseId(id);
		System.out.println("Course: " +course);
		System.out.println("Student: " +course.getStudents());
		System.out.println("DONE!");
	}

	private void createCourseAndStudent(AppDAO appDAO) {
		// create a course
		Course course = new Course("Dance like MJ");

		// create the students
		Student student1 = new Student("Vikas", "Seervi", "vikas@seervi");
		Student student2 = new Student("Abdul", "Ahad", "abdul@ahad");

		// add students to the course
		course.addStudent(student1);
		course.addStudent(student2);

		// save the course and associated students
		System.out.println("Saving the course: "+course);
		System.out.println("Associated students: "+course.getStudents());
		appDAO.save(course);
		System.out.println("DONE!");
	}

	private void deleteCourseAndReviews(AppDAO appDAO) {
		int id = 11;
		System.out.println("Deleting course with id: " + id);
		appDAO.deleteCourseById(id);
		System.out.println("DONE!");
	}

	private void retrieveCourseAndReviews(AppDAO appDAO) {
		int id = 11;
		Course course = appDAO.findCourseAndReviewsByCourseId(id);
		System.out.println("Course: "+ course);
		System.out.println("Reviews: "+ course.getReviews());
	}

	private void createCourseAndReviews(AppDAO appDAO) {
		// create a course
		Course course = new Course("Dance like MJ");
		// add some reviews
		course.addReview(new Review("Great course ... loved it!"));
		course.addReview(new Review("Cool course!"));
		course.addReview(new Review("What a dumb course!"));

		// save the course ... and leverage the cascade all
		System.out.println("Saving the course: ");
		System.out.println("Course: "+ course);
		System.out.println("Reviews: "+ course.getReviews());
		appDAO.save(course);
		System.out.println("DONE!");
	}

	private void deleteCourse(AppDAO appDAO) {
		int id = 10;
		System.out.println("Deleting course id: " + id);
		appDAO.deleteCourseById(id);
		System.out.println("DONE!");
	}

	private void updateCourse(AppDAO appDAO) {
		int id = 1;
		Course course = appDAO.findCourseById(10);
		course.setTitle("Dance like MJ");
		appDAO.update(course);
		System.out.println("DONE");
	}

	private void updateInstructor(AppDAO appDAO) {
		int id = 1;
		Instructor instructor = appDAO.findInstructorById(id);
		instructor.setLastName("TESTER");
		appDAO.update(instructor);
		System.out.println("DONE!");
	}

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {
		int id = 1;
		System.out.println("Finding Instructor id: "+id);
		Instructor instructor = appDAO.findInstructorByIdJoinFetch(id);
		System.out.println("Instructor: "+instructor);
		System.out.println("Courses: " + instructor.getCourses());
	}

	private void findCoursesForInstructors(AppDAO appDAO) {
		int id = 1;
		System.out.println("Finding Instructor id: "+id);
		Instructor instructor = appDAO.findInstructorById(id);
		System.out.println("Instructor: "+instructor);
		// find courses for instructor
		System.out.println("Finding Courses for Instructor Id: "+id);
		List<Course> courses = appDAO.findCourseByInstructorId(id);
		// associate the objects
		instructor.setCourses(courses);
		System.out.println("Courses: " + instructor.getCourses());
	}

	private void findInstructorWithCourses(AppDAO appDAO) {
		int id = 1;
		System.out.println("Finding Instructor id: "+id);
		Instructor instructor = appDAO.findInstructorById(id);
		System.out.println("Instructor: "+instructor);
		System.out.println("Courses: "+instructor.getCourses());
	}

	private void createInstructorWithCourses(AppDAO appDAO) {
		Instructor instructor = new
				Instructor("Yash", "Prakash", "yash@prakash");
		InstructorDetail instructorDetail = new
				InstructorDetail("Hacking Adda", "Hack the box");

		// associate the objects
		instructor.setInstructorDetail(instructorDetail);

		// create some courses
		// Course course1 = new Course("Air Guitar - The Ultimate Guide");
		Course course2 = new Course("Java Coding Master Classes");

		// add courses to the instructor
		// instructor.add(course1);
		instructor.add(course2);

		// save the instructor
		// this will also save "courses" object bcuz of this -->     @OneToOne(cascade = CascadeType.PERSIST)
		System.out.println("Saving...");
		appDAO.save(instructor);
		System.out.println("Done saving the object: "+instructor.toString()+
				"Courses are: "+instructor.getCourses());
	}

	private void deleteInstructorDetail(AppDAO appDAO) {
		int id = 6;
		try {
			appDAO.deleteInstructorDetailById(id);
			System.out.println("Deleting...");
			System.out.println("Done");
		}
		catch (InvalidDataAccessApiUsageException ex){
			System.out.println("There is no InstructorDetail with id: "+id);
		}
	}

	private void findInstructorDetail(AppDAO appDAO) {
		int id = 6;
		InstructorDetail instructorDetail = appDAO.findInstructorDetailById(id);
		System.out.println(instructorDetail);
		System.out.println(instructorDetail.getInstructor());
	}

	private void deleteInstructor(AppDAO appDAO) {
		int id = 1;
		try {
			System.out.println("Deleting...");
			appDAO.deleteInstructorById(id);
			System.out.println("Done");
		}
		catch (InvalidDataAccessApiUsageException ex){
			System.out.println("There is no Instructor with id: "+id);
		}
	}

	private void findInstructor(AppDAO appDAO) {
		int id = 6;
		System.out.println("Finding instructor of id: " + id);
		Instructor tempInstructor = appDAO.findInstructorById(id);
		System.out.println(tempInstructor);
	}

	private void createInstructor(AppDAO appDAO) {
		Instructor instructor = new
				Instructor("Yash", "Prakash", "yash@prakash");
		InstructorDetail instructorDetail = new
				InstructorDetail("Hacking Adda", "Hack the Box");

		// associate the objects
		instructor.setInstructorDetail(instructorDetail);

		// save the instructor
		// this will also save "instructorDetail" object bcuz of this -->     @OneToOne(cascade = CascadeType.ALL)
		System.out.println("Saving...");
		appDAO.save(instructor);
		System.out.println("Done saving the object: "+instructor.toString());
	}
}

package com.javalover.service;

import com.javalover.dto.Course;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {
private List<Course> courseDatabase = new ArrayList<Course>();

//create and save course object in db
public Course onboardNewCourse(Course course) {
    course.setCourseId(new Random().nextInt(800008));
    courseDatabase.add(course);
    return course;
}
// view all the courses from the database
public List<Course> viewAllCourses() {
    return courseDatabase;
}

//filter course by courseId
public Course findByCourseId(Integer courseId) {
    return courseDatabase.stream()
            .filter(course -> course.getCourseId()
                    .equals(courseId))
            .findFirst().orElse(null);
}

//delete the course
    public void deleteCourse(Integer courseId) {
    Course course = findByCourseId(courseId);
    courseDatabase.remove(course);
    }

    //update the course
    public Course updateCourse(Integer courseId, Course course) {
       Course existingCourse = findByCourseId(courseId);
       courseDatabase.set(courseDatabase.indexOf(existingCourse), course);
       return course;
    }

    public List<Map<String, Object>> getCourseTypeCounts() {
        // Group courses by courseType and calculate the count for each type
        Map<String, Long> courseTypeCounts = courseDatabase.stream()
                .collect(Collectors.groupingBy(Course::getCourseType, Collectors.counting()));

        // Convert the Map to a List of Maps with "courseType" and "count" fields
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Long> entry : courseTypeCounts.entrySet()) {
            Map<String, Object> courseTypeData = new HashMap<>();
            courseTypeData.put("courseType", entry.getKey());
            courseTypeData.put("count", entry.getValue());
            result.add(courseTypeData);
        }

        return result;
    }

}

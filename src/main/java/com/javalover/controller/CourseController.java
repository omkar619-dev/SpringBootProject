package com.javalover.controller;

import com.javalover.dto.Course;
import com.javalover.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
       Course newCourse = courseService.onboardNewCourse(course);
       return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAllCourses() {
        return new ResponseEntity<>(courseService.viewAllCourses(),HttpStatus.OK);
    }

    @GetMapping("/search/request")
    public ResponseEntity<?> findCourseById(@RequestParam(required = false) Integer courseId ) {
        return new ResponseEntity<>(courseService.findByCourseId(courseId),HttpStatus.OK);
    }
    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer courseId){
    courseService.deleteCourse(courseId);
    return new ResponseEntity<>("",HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable int courseId,@RequestBody Course course) {
     return  new ResponseEntity<>(courseService.updateCourse(courseId,course),HttpStatus.OK);
    }

    // New Endpoint: Get count of courses by type
    @GetMapping("/typeCounts")
    public ResponseEntity<?> getCourseTypeCounts() {
        List<Map<String, Object>> courseTypeCounts = courseService.getCourseTypeCounts();
        return new ResponseEntity<>(courseTypeCounts, HttpStatus.OK);
    }
}

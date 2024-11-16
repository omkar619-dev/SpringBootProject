package com.javalover.controller;

import com.javalover.dto.CourseRequestDTO;
import com.javalover.dto.CourseResponseDTO;
import com.javalover.dto.ServiceResponse;
import com.javalover.service.CourseService;
import jakarta.validation.Valid;
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
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody @Valid CourseRequestDTO courseRequestDTO) {
    //validate request
        ServiceResponse<CourseResponseDTO> serviceResponse = new ServiceResponse<>();
        CourseResponseDTO newCourse = courseService.onboardNewCourse(courseRequestDTO);
        serviceResponse.setStatus(HttpStatus.CREATED);
        serviceResponse.setResponse(newCourse);
        return serviceResponse;
    }

    @GetMapping
    public ServiceResponse<List<CourseResponseDTO>> findAllCourses() {
        List<CourseResponseDTO> courseResponseDTOS = courseService.viewAllCourses();
        return new ServiceResponse<>(HttpStatus.OK, courseResponseDTOS);
    }

    @GetMapping("/search/course/{courseId}")
    public ServiceResponse<CourseResponseDTO> findCourseById(@PathVariable Integer courseId) {
        CourseResponseDTO responseDTO = courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK, responseDTO);
    }

//    @GetMapping("/search/request")
//    public ServiceResponse<CourseResponseDTO> findCourseById(@RequestParam(required = false) Integer courseId ) {
//        CourseResponseDTO responseDTO = courseService.findByCourseId(courseId);
//        return new ServiceResponse<>(HttpStatus.OK, responseDTO);
//    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer courseId){
    courseService.deleteCourse(courseId);
    return new ResponseEntity<>("",HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId,@RequestBody CourseRequestDTO course) {
       CourseResponseDTO courseResponseDTO = courseService.updateCourse(courseId, course);
     return  new ServiceResponse<>(HttpStatus.OK, courseResponseDTO);
    }

    // New Endpoint: Get count of courses by type
    @GetMapping("/typeCounts")
    public ResponseEntity<?> getCourseTypeCounts() {
        List<Map<String, Object>> courseTypeCounts = courseService.getCourseTypeCounts();
        return new ResponseEntity<>(courseTypeCounts, HttpStatus.OK);
    }
}

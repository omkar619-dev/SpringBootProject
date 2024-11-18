package com.javalover.controller;

import com.javalover.dto.CourseRequestDTO;
import com.javalover.dto.CourseResponseDTO;
import com.javalover.dto.ServiceResponse;
import com.javalover.service.CourseService;
import com.javalover.util.AppUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    Logger log = LoggerFactory.getLogger(CourseController.class);
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "add the new course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "course added",content = {
                    @Content(mediaType = "application/json",schema = @Schema(implementation = CourseResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400",description = "validation error")
    })
    @PostMapping
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody @Valid CourseRequestDTO courseRequestDTO) {
    //validate request
        log.info("CourseController:addCourse Request Payload :{}", AppUtils.convertObjectToJson(courseRequestDTO));
        ServiceResponse<CourseResponseDTO> serviceResponse = new ServiceResponse<>();
        CourseResponseDTO newCourse = courseService.onboardNewCourse(courseRequestDTO);
        serviceResponse.setStatus(HttpStatus.CREATED);
        serviceResponse.setResponse(newCourse);
        log.info("CourseController:addCourse Response   :{}", AppUtils.convertObjectToJson(serviceResponse));

        return serviceResponse;
    }

    @GetMapping
    @Operation(summary = "fetch all the courses object")
    public ServiceResponse<List<CourseResponseDTO>> findAllCourses() {
        List<CourseResponseDTO> courseResponseDTOS = courseService.viewAllCourses();
        return new ServiceResponse<>(HttpStatus.OK, courseResponseDTOS);
    }

    @Operation(summary = "Find the course by specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "course found",content = {
                    @Content(mediaType = "application/json",schema = @Schema(implementation = CourseResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400",description = "course not found with given id")
    })
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
        log.info("CourseController;deleteCOurse dceleing a course with id {}", courseId);
    courseService.deleteCourse(courseId);
    return new ResponseEntity<>("",HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId,@RequestBody CourseRequestDTO course) {
        log.info("CourseController:updateCourse Request Payload :{} and {}", AppUtils.convertObjectToJson(course),courseId);
       CourseResponseDTO courseResponseDTO = courseService.updateCourse(courseId, course);
     return  new ServiceResponse<>(HttpStatus.OK, courseResponseDTO);
    }

    // New Endpoint: Get count of courses by type
    @GetMapping("/typeCounts")
    public ResponseEntity<?> getCourseTypeCounts() {
        List<Map<String, Object>> courseTypeCounts = courseService.getCourseTypeCounts();
        return new ResponseEntity<>(courseTypeCounts, HttpStatus.OK);
    }

    @GetMapping("/log")
    public String loggingLevel(){

        log.trace("trace message");
        log.debug("debug message");
        log.info("info message");
        log.warn("warn message");
        log.error("error message");
        return "log printed in console";

    }
}

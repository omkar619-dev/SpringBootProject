package com.javalover.service;

import com.javalover.dao.CourseDao;
import com.javalover.dto.CourseRequestDTO;
import com.javalover.dto.CourseResponseDTO;
import com.javalover.entity.CourseEntity;
import com.javalover.exception.CourseServiceBusinessException;
import com.javalover.util.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseService {
    @Autowired
    private CourseDao courseDao;

    Logger log = LoggerFactory.getLogger(CourseService.class);
//private List<Course> courseDatabase = new ArrayList<Course>();

//create and save course object in db
public CourseResponseDTO onboardNewCourse(CourseRequestDTO courseRequestDTO) {
    //convert DTO to Entity
    CourseEntity courseEntity = AppUtils.mapDTOToEntity(courseRequestDTO);
    CourseEntity entity = null;
    log.info("CourseService :: onboardNewCourse method execution started");
    try{
        entity = courseDao.save(courseEntity);
        log.debug("course Entity response from Database {} ",AppUtils.convertObjectToJson(entity));
        CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(entity);
        courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
        log.debug("CourseService::onboardNewCourse method response {} ",AppUtils.convertObjectToJson(courseResponseDTO));
        log.info("CourseService :: onboardNewCourse method execution ended");
        return courseResponseDTO;
    }
    catch (Exception exception){
        log.error("CourseService :: onboardNewCourse method exception occurs while persisting the course object to DB ");
        throw new CourseServiceBusinessException("Onboard new course service method failed");
    }
    // convert Entity to Response DTO
}


// view all the courses from the database
public List<CourseResponseDTO> viewAllCourses() {
    Iterable<CourseEntity> courseEntities = null;
    try {
        courseEntities = courseDao.findAll();
        return StreamSupport.stream(courseEntities.spliterator(),false)
                .map(AppUtils::mapEntityToDTO)
                .collect(Collectors.toList());
    }
catch (Exception exception){
    throw new CourseServiceBusinessException("viewAllCourses service method failed");
}


}

//filter course by courseId
public CourseResponseDTO findByCourseId(Integer courseId) {
    // Select * from table where id =?
    CourseEntity courseEntity = courseDao.findById(courseId)
            .orElseThrow(()-> new CourseServiceBusinessException(courseId + " Does not exist in system!"));
    return AppUtils.mapEntityToDTO(courseEntity);
}

//delete the course
    public void deleteCourse(Integer courseId) {
log.info("CourseService :: deleteCourse method execution started");
    try {
        log.debug("CourseService :: deleteCourse method input {}",courseId);
        courseDao.deleteById(courseId);
    }
catch (Exception ex){
        log.error("CourseService:: deleteCourse exception occurs while deleting the course object {}",ex.getMessage());
        throw new CourseServiceBusinessException("deleteCourse service method failed "+ex.getMessage());
}
    log.info("CourseService :: deleteCourse method execution ended");
    }

    //update the course
    public CourseResponseDTO updateCourse(Integer courseId, CourseRequestDTO courseRequestDTO) {
        log.info("CourseService :: updateCourse method execution started");
        try {
            //get the existing obejct and modify that object an save it in db
            log.debug("CourseService::updateCourse request payload {} & {}", AppUtils.convertObjectToJson(courseRequestDTO), courseId);
            CourseEntity existingCourseEntity = courseDao.findById(courseId).orElseThrow(()->new RuntimeException("corse object not present in db with id "+courseId));
            log.debug("CourseService::updateCourse getting existing course object as {}", AppUtils.convertObjectToJson(existingCourseEntity));
            existingCourseEntity.setName(courseRequestDTO.getName());
            existingCourseEntity.setDuration(courseRequestDTO.getDuration());
            existingCourseEntity.setTrainerName(courseRequestDTO.getTrainerName());
            existingCourseEntity.setCourseType(courseRequestDTO.getCourseType());
            existingCourseEntity.setCount(courseRequestDTO.getCount());
            existingCourseEntity.setFees(courseRequestDTO.getFees());
            existingCourseEntity.setStartDate(courseRequestDTO.getStartDate());
            existingCourseEntity.setIsCertificateAvailable(courseRequestDTO.getIsCertificateAvailable());
            existingCourseEntity.setDescription(courseRequestDTO.getDescription());
            existingCourseEntity.setEmailId(courseRequestDTO.getEmailId());

            CourseEntity updatedCourseEntity = courseDao.save(existingCourseEntity);
            CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(updatedCourseEntity);

            log.debug("CourseService::updateCourse getting updated course object as {}", AppUtils.convertObjectToJson(existingCourseEntity));
            log.info("CourseService :: updateCourse method execution ended");
            return courseResponseDTO;
        }
        catch (Exception ex){
            log.error("courseService :: updateCourse exception occurs while updating the course object {}",ex.getMessage());
            throw new CourseServiceBusinessException("updateCourse service method failed " + ex.getMessage());
        }
    }

    public List<Map<String, Object>> getCourseTypeCounts() {
        // Group courses by courseType and calculate the count for each type
        Iterable<CourseEntity> courseEntities = courseDao.findAll();
        // Map the courses to DTOs and group by courseType to calculate the count
        Map<String, Long> courseTypeCounts = StreamSupport.stream(courseEntities.spliterator(), false)
                .map(AppUtils::mapEntityToDTO)
                .collect(Collectors.groupingBy(CourseResponseDTO::getCourseType, Collectors.counting()));

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

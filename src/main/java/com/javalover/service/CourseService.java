package com.javalover.service;

import com.javalover.dao.CourseDao;
import com.javalover.dto.CourseRequestDTO;
import com.javalover.dto.CourseResponseDTO;
import com.javalover.entity.CourseEntity;
import com.javalover.exception.CourseServiceBusinessException;
import com.javalover.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseService {
    @Autowired
    private CourseDao courseDao;
//private List<Course> courseDatabase = new ArrayList<Course>();

//create and save course object in db
public CourseResponseDTO onboardNewCourse(CourseRequestDTO courseRequestDTO) {
    //convert DTO to Entity
    CourseEntity courseEntity = AppUtils.mapDTOToEntity(courseRequestDTO);
    CourseEntity entity = null;
    try{
        entity = courseDao.save(courseEntity);
        CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(entity);
        courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
        return courseResponseDTO;
    }
    catch (Exception exception){
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
    courseDao.deleteById(courseId);

    }

    //update the course
    public CourseResponseDTO updateCourse(Integer courseId, CourseRequestDTO courseRequestDTO) {
    //get the existing obejct and modify that object an save it in db
       CourseEntity existingCourseEntity = courseDao.findById(courseId).orElse(null);
        existingCourseEntity.setName(courseRequestDTO.getName());
        existingCourseEntity.setDuration(courseRequestDTO.getDuration());
        existingCourseEntity.setTrainerName(courseRequestDTO.getTrainerName());
        existingCourseEntity.setCourseType(courseRequestDTO.getCourseType());
        existingCourseEntity.setCount(courseRequestDTO.getCount());
        existingCourseEntity.setFees(courseRequestDTO.getFees());
        existingCourseEntity.setStartDate(courseRequestDTO.getStartDate());
        existingCourseEntity.setIsCertificateAvailable(courseRequestDTO.getIsCertificateAvailable());
        existingCourseEntity.setDescription(courseRequestDTO.getDescription());

        CourseEntity updatedCourseEntity =  courseDao.save(existingCourseEntity);

       return AppUtils.mapEntityToDTO(updatedCourseEntity);
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

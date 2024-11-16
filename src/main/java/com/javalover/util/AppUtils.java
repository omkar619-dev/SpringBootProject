package com.javalover.util;

import com.javalover.dto.CourseRequestDTO;
import com.javalover.dto.CourseResponseDTO;
import com.javalover.entity.CourseEntity;

public class AppUtils {

    //DTO -> Entity Object
    public static CourseEntity mapDTOToEntity(CourseRequestDTO courseRequestDTO){
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(courseRequestDTO.getName());
        courseEntity.setDuration(courseRequestDTO.getDuration());
        courseEntity.setTrainerName(courseRequestDTO.getTrainerName());
        courseEntity.setCourseType(courseRequestDTO.getCourseType());
        courseEntity.setCount(courseRequestDTO.getCount());
        courseEntity.setFees(courseRequestDTO.getFees());
        courseEntity.setStartDate(courseRequestDTO.getStartDate());
        courseEntity.setIsCertificateAvailable(courseRequestDTO.getIsCertificateAvailable());
        courseEntity.setDescription(courseRequestDTO.getDescription());
        courseEntity.setEmailId(courseRequestDTO.getEmailId());
        courseEntity.setContact(courseRequestDTO.getContact());
        return courseEntity;


    }

    public static CourseResponseDTO mapEntityToDTO(CourseEntity courseEntity ){
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setCourseId(courseEntity.getCourseId());
        courseResponseDTO.setName(courseEntity.getName());
        courseResponseDTO.setDuration(courseEntity.getDuration());
        courseResponseDTO.setTrainerName(courseEntity.getTrainerName());
        courseResponseDTO.setCourseType(courseEntity.getCourseType());
        courseResponseDTO.setCount(courseEntity.getCount());
        courseResponseDTO.setFees(courseEntity.getFees());
        courseResponseDTO.setStartDate(courseEntity.getStartDate());
        courseResponseDTO.setIsCertificateAvailable(courseEntity.getIsCertificateAvailable());
        courseResponseDTO.setDescription(courseEntity.getDescription());
        courseResponseDTO.setEmailId(courseEntity.getEmailId());
        courseResponseDTO.setContact(courseEntity.getContact());
        return courseResponseDTO;

    }
}

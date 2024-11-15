package com.javalover.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer courseId;
    private String name;
    private String duration;
    private String trainerName;
    private String courseType;
    private Long count;
    private Double fees;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date startDate;
    private Boolean isCertificateAvailable;
    private String Description;

}

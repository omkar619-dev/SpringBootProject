package com.javalover.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javalover.annotation.CourseTypeValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDTO {
    @NotBlank(message = "Course name should not be null or empty")
    private String name;
    @NotNull(message = "Duration must be specified")
    @NotEmpty(message = "Duration cannot be empty cmon man!")
    private String duration;
    @NotEmpty(message = "Trainer name should be defined")
    private String trainerName;
    @CourseTypeValidation
    private String courseType;
    private Long count;
    @Min(value = 1500,message = "course price cannot be less than 1500")
    @Max(value = 10000,message = "course price should not be more than 10k")
    private Double fees;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    @Past(message = "start date cannot be before than the current data")
    private Date startDate;
    private Boolean isCertificateAvailable;
    @NotEmpty(message = "description must be present")
    @Length(min = 5,max = 10)
    private String Description;
    @Email(message = "invalid email id")
    private String emailId;
    @Pattern(regexp = "^[0-9]{10}$")
    private String contact;

}

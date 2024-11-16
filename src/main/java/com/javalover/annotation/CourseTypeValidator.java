package com.javalover.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CourseTypeValidator implements ConstraintValidator<CourseTypeValidation,String> {
    @Override
    public boolean isValid(String courseType, ConstraintValidatorContext constraintValidatorContext) {
        List list = Arrays.asList("LIVE","RECORDING");
        return list.contains(courseType);
    }
}

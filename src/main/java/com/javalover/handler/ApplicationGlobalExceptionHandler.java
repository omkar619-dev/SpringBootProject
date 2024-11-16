package com.javalover.handler;

import com.javalover.dto.ErrorDTO;
import com.javalover.dto.ServiceResponse;
import com.javalover.exception.CourseServiceBusinessException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApplicationGlobalExceptionHandler {
 //MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception){
    ServiceResponse<?> serviceResponse = new ServiceResponse<>();
    List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
    exception.getBindingResult().getAllErrors().forEach(error -> {
        ErrorDTO errorDTO = new ErrorDTO(error.getDefaultMessage());
        errorDTOList.add(errorDTO);
    });
    serviceResponse.setStatus(HttpStatus.BAD_REQUEST);
    serviceResponse.setError(errorDTOList);
    return serviceResponse;
    }

    @ExceptionHandler(CourseServiceBusinessException.class)
    public ServiceResponse<?> handleServiceException(CourseServiceBusinessException exception){
        ServiceResponse<?> serviceResponse = new ServiceResponse<>();
        List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
        errorDTOList.add(new ErrorDTO(exception.getMessage()));
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setError(errorDTOList);
        return serviceResponse;
    }
}

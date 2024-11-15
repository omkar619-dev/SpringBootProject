package com.javalover.dao;

import com.javalover.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;

public interface CourseDao extends CrudRepository<CourseEntity,Integer> {

}

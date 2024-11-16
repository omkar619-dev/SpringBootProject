package com.javalover.dao;

import com.javalover.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends CrudRepository<CourseEntity,Integer> {

}

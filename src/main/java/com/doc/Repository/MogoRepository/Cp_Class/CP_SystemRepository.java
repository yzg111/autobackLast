package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Entity.MogoEntity.CP_Class.CP_System;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2019/4/24 由Administrator 创建 .
 */
public interface CP_SystemRepository extends MongoRepository<CP_System,ObjectId> {
    //根据id查询出数据
    public CP_System findById(String id);

    //根据id查询出数据
    public List<CP_System> findByIdIn(List<String> id);
}

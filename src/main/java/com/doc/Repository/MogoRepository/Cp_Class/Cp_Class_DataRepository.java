package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2018/2/26 由Administrator 创建 .
 */
public interface Cp_Class_DataRepository extends MongoRepository<CP_Class_Data,ObjectId> {
    //根据父级id查询出数据
    public List<CP_Class_Data> findByCpid(String cpid);
    //根据id查询出数据
    public CP_Class_Data findById(String id);
    //根据id数组查询数据
    public List<CP_Class_Data> findByIdIn(List<String> ids);
}

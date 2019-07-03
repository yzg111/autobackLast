package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Entity.MogoEntity.CP_Class.CP_Roles;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2019/4/23 由Administrator 创建 .
 */
public interface CP_RolesRepository extends MongoRepository<CP_Roles,ObjectId> {
    //根据id查询出数据
    public CP_Roles findById(String id);

    public List<CP_Roles> findByIdIn(List<String> ids);
}

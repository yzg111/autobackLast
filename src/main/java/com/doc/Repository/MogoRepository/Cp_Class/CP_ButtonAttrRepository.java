package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Entity.MogoEntity.CP_Class.CP_ButtonAttr;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2019/4/15 由Administrator 创建 .
 */
public interface CP_ButtonAttrRepository extends MongoRepository<CP_ButtonAttr,ObjectId> {
    public CP_ButtonAttr findById(String id);
}

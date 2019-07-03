package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Entity.MogoEntity.CP_Class.CP_Form;
import com.doc.Entity.MogoEntity.CP_Class.CP_Table;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2018/2/27 由Administrator 创建 .
 */
public interface Cp_FormRepository extends MongoRepository<CP_Form,ObjectId> {
    //根据父级id查询出数据
    public List<CP_Form> findByCpid(String cpid);
    //根据id查询出数据
    public CP_Form findById(String id);
}

package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2018/2/23 由Administrator 创建 .
 */
public interface Cp_ClassRepository extends MongoRepository<CP_Class,ObjectId> {
    //根据id进行排序
    public List<CP_Class> findAllByOrderByIdDesc();

    public List<CP_Class>findByParentidIsNull();

    public List<CP_Class>findByParentidIsNotNull();

    public List<CP_Class>findByParentid(String parentid);
    public CP_Class findById(String id);
    public List<CP_Class> findByIdIn(List<String> ids);

    public CP_Class findByCpname(String cpname);
}

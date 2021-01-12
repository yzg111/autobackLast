package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Dao.MogoDao.ComDao.ComDao;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2018/2/23 由Administrator 创建 .
 */
@Component("Cp_ClassRepository")
@Qualifier("Cp_ClassRepository")
public interface Cp_ClassRepository
        extends MongoRepository<CP_Class,ObjectId> {
    //根据id进行排序
    public  List<CP_Class> findAllByOrderByIdDesc();

    public List<CP_Class>findByParentidIsNull();

    public List<CP_Class>findByParentidIsNotNull();

    public List<CP_Class>findByParentid(String parentid);
    public CP_Class findById(String id);
    public List<CP_Class> findByIdIn(List<String> ids);

    public CP_Class findByCpname(String cpname);

    //分页查询数据
    public List<CP_Class>findByParentid(String parentid,Pageable pageable);
}

package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Menu;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2018/2/27 由Administrator 创建 .
 */
public interface Cp_MenuRepository extends MongoRepository<CP_Menu,ObjectId> {
    //根据id进行排序
    public List<CP_Menu> findAllByOrderByIdDesc();

    public List<CP_Menu>findByParentidIsNull();

    public List<CP_Menu>findByParentid(String parentid);
    public CP_Menu findById(String id);

    public List<CP_Menu>findByIdIn(List<String> ids);

    public List<CP_Menu>findByParentidIn(List<String> parentids);

    public List<CP_Menu>findByParentidAndIdIn(String parentid,List<String> ids);
}

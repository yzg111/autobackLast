package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScriptTree;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2019/3/7 由Administrator 创建 .
 */
public interface Cp_GroovyScriptTreeRepository extends MongoRepository<CP_GroovyScriptTree,ObjectId> {
    public CP_GroovyScriptTree findByScripttreename(String scripttreename);

    public List<CP_GroovyScriptTree> findByParentid(String parentid);

    public CP_GroovyScriptTree findById(String id);

    public List<CP_GroovyScriptTree> findByParentidIsNull();
}

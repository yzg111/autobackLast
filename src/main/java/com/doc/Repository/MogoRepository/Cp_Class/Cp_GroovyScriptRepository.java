package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScript;
import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScriptTree;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2019/3/7 由Administrator 创建 .
 */
public interface Cp_GroovyScriptRepository extends MongoRepository<CP_GroovyScript,ObjectId> {
    public List<CP_GroovyScript> findByScripttreeid(String scripttreeid);

    public CP_GroovyScript findByScriptname(String scriptname);

    public CP_GroovyScript findById(String id);
    public CP_GroovyScript findByScriptcode(String scriptcode);
}

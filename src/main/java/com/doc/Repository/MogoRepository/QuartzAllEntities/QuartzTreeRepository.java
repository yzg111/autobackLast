package com.doc.Repository.MogoRepository.QuartzAllEntities;

import com.doc.Entity.MogoEntity.QuartzAllEntities.QuartzTree;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.QuartzAllEntities 于2020/9/24 由Administrator 创建 .
 */
public interface QuartzTreeRepository extends MongoRepository<QuartzTree,ObjectId> {
    public QuartzTree findById(String id);
    public List<QuartzTree> findByParentid(String parentid);
    public List<QuartzTree> findByParentidIsNull();
}

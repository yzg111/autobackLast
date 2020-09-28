package com.doc.Repository.MogoRepository.QuartzAllEntities;

import com.doc.Entity.MogoEntity.QuartzAllEntities.Quartz;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.QuartzAllEntities 于2020/9/24 由Administrator 创建 .
 */
public interface QuartzRepository extends MongoRepository<Quartz,ObjectId> {
    public Quartz findById(String id);
    public List<Quartz> findByQuartztreeid(String quartztreeid);
    public List<Quartz> findByQuartztreeidAndIsuse(String quartztreeid,Boolean isuse);
    public List<Quartz> findByIsuse(Boolean isuse);

}

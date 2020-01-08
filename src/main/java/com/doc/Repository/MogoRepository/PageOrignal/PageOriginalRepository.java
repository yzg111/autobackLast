package com.doc.Repository.MogoRepository.PageOrignal;

import com.doc.Entity.MogoEntity.PageOrignal.PageOrignal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2019/4/15 由Administrator 创建 .
 */
public interface PageOriginalRepository extends MongoRepository<PageOrignal,ObjectId> {
    public PageOrignal findById(String id);
}

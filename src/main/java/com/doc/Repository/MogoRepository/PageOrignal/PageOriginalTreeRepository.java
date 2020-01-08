package com.doc.Repository.MogoRepository.PageOrignal;

import com.doc.Entity.MogoEntity.PageOrignal.PageOrignalTree;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2019/4/15 由Administrator 创建 .
 */
public interface PageOriginalTreeRepository extends MongoRepository<PageOrignalTree,ObjectId> {
    public PageOrignalTree findById(String id);

    public List<PageOrignalTree> findByParentid(String parentid);
}

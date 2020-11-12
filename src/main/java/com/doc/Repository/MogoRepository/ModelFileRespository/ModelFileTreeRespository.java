package com.doc.Repository.MogoRepository.ModelFileRespository;

import com.doc.Entity.MogoEntity.ModelFileEntity.ModelFileTree;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.ModelFileRespository 于2020/10/23 由Administrator 创建 .
 */
public interface ModelFileTreeRespository extends MongoRepository<ModelFileTree,ObjectId> {
    public ModelFileTree findById(String id);
    public List<ModelFileTree> findByParentid(String parentid);

    public List<ModelFileTree> findByParentidIsNull();
}

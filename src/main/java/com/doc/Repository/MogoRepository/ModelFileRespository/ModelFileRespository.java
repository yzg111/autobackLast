package com.doc.Repository.MogoRepository.ModelFileRespository;

import com.doc.Entity.MogoEntity.ModelFileEntity.ModelFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * com.doc.Repository.MogoRepository.ModelFileRespository 于2020/10/23 由Administrator 创建 .
 */
public interface ModelFileRespository extends MongoRepository<ModelFile,ObjectId> {
    public ModelFile findById(String id);
}

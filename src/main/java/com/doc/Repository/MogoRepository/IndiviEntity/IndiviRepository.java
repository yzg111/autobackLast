package com.doc.Repository.MogoRepository.IndiviEntity;

import com.doc.Entity.MogoEntity.IndiviEntity.IndiviEntity;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.IndiviEntity 于2020/9/1 由Administrator 创建 .
 */
public interface IndiviRepository extends MongoRepository<IndiviEntity,ObjectId> {

    public IndiviEntity findById(String id);

    public List<IndiviEntity> findByIndiviengname(String indiviengname);

}

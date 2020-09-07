package com.doc.Repository.MogoRepository.Echart;

import com.doc.Entity.MogoEntity.EchartEntity.EchartEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2019/4/15 由Administrator 创建 .
 */
public interface EchartRepository extends MongoRepository<EchartEntity,ObjectId> {
    public EchartEntity findById(String id);
    public List<EchartEntity> findByCpid(String id);

    public List<EchartEntity> findByIdIn(List<String> ids);
}

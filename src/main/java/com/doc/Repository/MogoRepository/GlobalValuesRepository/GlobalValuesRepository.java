package com.doc.Repository.MogoRepository.GlobalValuesRepository;

import com.doc.Entity.MogoEntity.GlobalValuesEntity.GlobalValuesEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * com.doc.Repository.MogoRepository.GlobalValuesRepository 于2021/2/2 由Administrator 创建 .
 */
@Component("GlobalValuesRepository")
@Qualifier("GlobalValuesRepository")
public interface GlobalValuesRepository extends MongoRepository<GlobalValuesEntity,ObjectId> {
    //根据id查询出数据
    public GlobalValuesEntity findById(String id);

    public Page<GlobalValuesEntity> findByGlobalnameLike(String globalname, Pageable pageable);
}

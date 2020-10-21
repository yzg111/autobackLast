package com.doc.Repository.MogoRepository.DataSourceSyncRepository;

import com.doc.Entity.MogoEntity.DataSourceSync.DataSetInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * com.doc.Repository.MogoRepository.DataSourceSyncRepository 于2020/10/12 由Administrator 创建 .
 */
@Component("DataSetInfoRepository1")
public interface DataSetInfoRepository extends MongoRepository<DataSetInfo,ObjectId> {
    public DataSetInfo  findById(String id);
}

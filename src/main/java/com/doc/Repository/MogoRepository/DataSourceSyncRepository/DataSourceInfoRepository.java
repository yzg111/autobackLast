package com.doc.Repository.MogoRepository.DataSourceSyncRepository;

import com.doc.Entity.MogoEntity.DataSourceSync.DataSourceInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * com.doc.Repository.MogoRepository.DataSourceSyncRepository 于2020/10/12 由Administrator 创建 .
 */
@Component("DataSourceInfoRepository1")
public interface DataSourceInfoRepository extends MongoRepository<DataSourceInfo,ObjectId> {
            public DataSourceInfo  findById(String id);
}

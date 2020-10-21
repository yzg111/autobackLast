package com.doc.Repository.MogoRepository.DataSourceSyncRepository;

import com.doc.Entity.MogoEntity.DataSourceSync.DataSetIntoMogo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * com.doc.Repository.MogoRepository.DataSourceSyncRepository 于2020/10/12 由Administrator 创建 .
 */
@Component("DataSetIntoMogoRepository1")
public interface DataSetIntoMogoRepository extends MongoRepository<DataSetIntoMogo,ObjectId> {
    public DataSetIntoMogo  findById(String id);
}

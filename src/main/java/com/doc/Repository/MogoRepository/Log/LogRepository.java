package com.doc.Repository.MogoRepository.Log;

import com.doc.Entity.MogoEntity.Log.log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * com.doc.Repository.MogoRepository.Log 于2017/8/25 由Administrator 创建 .
 */
public interface LogRepository extends MongoRepository<log, ObjectId> {
    public log findByUrl(String url);
    public log findByText(String text);
    public log findByType(String type);
}

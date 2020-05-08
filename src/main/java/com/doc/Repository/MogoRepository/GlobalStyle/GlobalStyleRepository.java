package com.doc.Repository.MogoRepository.GlobalStyle;

import com.doc.Entity.MogoEntity.GlobalStyleEntity.GlobalStyle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * com.doc.Repository.MogoRepository.GlobalStyle 于2020/5/8 由Administrator 创建 .
 */
public interface GlobalStyleRepository extends MongoRepository<GlobalStyle,ObjectId> {
    //根据id查询出扩展样式
    public GlobalStyle findById(String id);
}

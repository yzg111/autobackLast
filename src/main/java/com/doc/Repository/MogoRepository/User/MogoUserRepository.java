package com.doc.Repository.MogoRepository.User;

import com.doc.Entity.MogoEntity.User.MogoUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.User 于2017/8/25 由Administrator 创建 .
 */
public interface MogoUserRepository extends MongoRepository<MogoUser, ObjectId> {
    public MogoUser findByUsernameAndPassword(String username, String password);

    public MogoUser findByUsername(String username);

    public List<MogoUser> findAllByOrderByIdDesc();

//    @Query("update user u set u.password=?1 where u.username=?2")
//    public int update(String password,String username);
}

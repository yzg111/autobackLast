package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Dao.MogoDao.ComDao.ComDao;
import com.doc.Entity.MogoEntity.CP_Class.CP_File;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2019/11/22 由Administrator 创建 .
 */
public interface CP_FileRepository  extends MongoRepository<CP_File,ObjectId>  {
    //根据id查询出数据
    public CP_File findById(String id);

    //根据数据的id查询出附件信息
    public List<CP_File> findByDataid(String dataid,Pageable pageable);
    //根据数据的id查询出附件信息
    public List<CP_File> findByDataid(String dataid);
}

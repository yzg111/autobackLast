package com.doc.Repository.MogoRepository.Cp_Class.impl;

import com.doc.Dao.MogoDao.ComDao.ComDao;
import com.doc.Entity.MogoEntity.CP_Class.CP_File;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class.impl 于2020/9/10 由Administrator 创建 .
 */
@Repository("Cp_FileComRespository")
public class Cp_FileComRespository extends ComDao<CP_File> {

    @Override
    protected Class<CP_File> getEntityClass() {
        return CP_File.class;
    }

    public List<CP_File> getFilesByDataidPage(int pageno, int pagesize, String dataid){
        Criteria criteria=new Criteria();
        criteria.where("dataid").is(dataid);
        //按照时间远近排序
        Sort sort =new Sort(Sort.Direction.DESC,"createtime");
        Pageable pageable=new PageRequest(pageno,pagesize,sort);
        Query query=new Query(criteria);
        query.with(pageable);
        return  find(query);
    }
}

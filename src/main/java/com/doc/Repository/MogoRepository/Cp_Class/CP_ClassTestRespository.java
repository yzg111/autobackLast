package com.doc.Repository.MogoRepository.Cp_Class;

import com.doc.Dao.MogoDao.ComDao.ComDao;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.doc.Repository.MogoRepository.Cp_Class 于2020/9/10 由Administrator 创建 .
 */
@Component("CP_ClassTestRespository")
public  class CP_ClassTestRespository extends ComDao<CP_Class> {
    @Override
    protected Class<CP_Class> getEntityClass() {
        return CP_Class.class;
    }

    public List<CP_Class> getResBypageNoAndQuery(int pageno,int pagesize){
        //查询条件
        Criteria criteria=Criteria.where("_id").is("5a912d0d56e6f03780d0b7b1");
        //增加条件，两种方式
//        criteria.andOperator(Criteria.where("cpname").is("了科三简单和"));
//        criteria.and("cpname").is("了科三简单和");
        //排序
        Sort sort = new Sort(Sort.Direction.DESC,"cpname");
//        criteria.where("_id").is("5a912d0d56e6f03780d0b7b1");
//        Pageable pageable=new PageRequest(pageno,pagesize,sort);
        Pageable pageable=new PageRequest(pageno,pagesize);
        //根据条件查询
        Query query=new Query(criteria);
        //查询所有
//        Query query=new Query();
        query.with(pageable);
        return  find(query);
    }

}

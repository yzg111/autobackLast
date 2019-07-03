package com.doc.Dao.MogoDao.ComDao;


import com.doc.Entity.MogoEntity.ComEntity.ComEnt;
import com.doc.Entity.MogoEntity.ComEntity.Pagination;
import com.doc.Entity.MogoEntity.ComEntity.QuerySortOrder;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Doc.Dao.Common 于2017/8/22 由Administrator 创建 .
 */
@Transactional
public abstract class ComDao<T extends ComEnt> {
    @Autowired
    private MongoTemplate mongoTemplate;

    //保存一个对象到mongodb
    public T save(T bean) {
        mongoTemplate.save(bean);
        return bean;
    }

    /**
     * 保存一组数据到mongodb中。
     *
     * @param beans
     */
    public void insert(Collection<T> beans) {
        mongoTemplate.insert(beans, this.getEntityClass());
    }

    // 根据id删除对象
    public T deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        return this.mongoTemplate.findAndRemove(query, this.getEntityClass());
    }

    // 根据对象的属性删除
    public List<T> deleteByCondition(Query query) {
        return mongoTemplate.findAllAndRemove(query, getEntityClass());

    }

    /**
     * 删除一组指定ID的文档。
     *
     * @param ids
     */
    public List<T> deleteByIds(Collection<String> ids) {
        if (ids == null || ids.isEmpty())
            return new ArrayList<T>(0);

        Query query = new Query(Criteria.where("_id").in(ids));

        return this.mongoTemplate.findAllAndRemove(query, this.getEntityClass());
    }

    public void updateBatch(Collection<T> beans) {
        for (T bean : beans) {
            this.mongoTemplate.save(bean);
        }
    }

    // 通过条件更新符合条件的数据
    public int updateMulti(Query query, Update update) {
        WriteResult wr = mongoTemplate.updateMulti(query, update, getEntityClass());
        return wr.getN();
    }

    // 通过条件更新符合条件的第一条数据
    public int update(Query query, Update update) {
        WriteResult wr = mongoTemplate.updateFirst(query, update, getEntityClass());
        return wr.getN();
    }

    /**
     * 根据ID更新相关字段的值。
     *
     * @param id 文档ID
     * @param update 要更新键值对。
     */
    public int updateByID(String id, Update update) {
        Query query = new Query(Criteria.where("_id").is(id));
        WriteResult wr = this.mongoTemplate.updateFirst(query, update, getEntityClass());
        return wr.getN();
    }

    // 根据id进行更新
    public void update(T t) {
        this.mongoTemplate.save(t);
    }

    // 通过条件查询实体(集合)
    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 根据条件分页查询。
     *
     * @param query
     * @param skip
     * @param limit
     * @return
     */
    public List<T> findByCondition(Query query, int skip, int limit) {
        query.skip(skip);
        query.limit(limit);
        return mongoTemplate.find(query, getEntityClass());
    }

    // 通过ID获取记录,并且指定了集合名(表的意思)
    public T findByID(String id) {

        return mongoTemplate.findById(id, getEntityClass());
    }

    /**
     * 查询一组指定ID的文档信息列表。
     *
     * @param ids ID列表。
     * @return
     */
    public List<T> findByIDs(List<String> ids) {
        if (ids == null || ids.isEmpty())
            return new ArrayList<T>(0);

        Query query = new Query(Criteria.where("_id").in(ids));
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 通过条件查询,查询分页结果
     *
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @param query 查询条件。
     * @return
     * @throws Exception
     * @throws Exception
     */
    public Pagination<T> getPage(int pageNo, int pageSize, Query query) throws Exception {
        long totalCount = this.mongoTemplate.count(query, this.getEntityClass());
        Pagination<T> page = new Pagination<T>(pageNo, pageSize, totalCount);
        //如果当前页大于总页数则抛出异常。
        if (totalCount <= 0) {
            return page;
        }
        if (page.getTotalPage() > 0 && pageNo > page.getTotalPage()) {
//            throw new ServiceException(
//                String.format("当前页(%s)大于总页数(%s)。", pageNo, page.getTotalPage()));
            page = new Pagination<T>(page.getTotalPage(), pageSize, totalCount);//如果所查询的页数大于当前页数，则跳到最后一页
        }
        query.skip(page.getFirstResult());// skip相当于从那条记录开始
        query.limit(page.getCount());// 从skip开始,取多少条记录
        List<T> datas = this.find(query);
        page.build(datas);
        return page;
    }

    /**
     * 查询指定条件的文档数量
     *
     * @param query
     * @return
     */
    public long count(Query query) {
        return this.mongoTemplate.count(query, this.getEntityClass());
    }

    /**
     * 获取需要操作的实体类class
     *
     * @return
     */
    @SuppressWarnings("all")
    protected Class<T> getEntityClass() {
        Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        return clazz;
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    protected List<Sort.Order> getSorts(List<QuerySortOrder> sortFields) {
        if (sortFields != null && sortFields.size() > 0) {
            List<Sort.Order> sorts = new LinkedList<Sort.Order>();
            for (QuerySortOrder field : sortFields) {
                if (field.getDirection() >= 0) {
                    Sort.Order order = new Sort.Order(Sort.Direction.DESC,
                            field.getAttribute());
                    sorts.add(order);
                } else {
                    Sort.Order order = new Sort.Order(Sort.Direction.ASC,
                            field.getAttribute());
                    sorts.add(order);
                }
            }
            return sorts;
        } else {
            return new ArrayList<Sort.Order>();
        }
    }

    /**
     * 将数据查询权限附加到当前查询条件中。
//     * @param where
     */
//    public void attachDataPermQueryFilter(Criteria where) {
//        CurrentInvokingWithPermission invoking = LoginnedAccountManager.getInstance()
//                .getCurrentInvoking();
//        if (invoking == null || invoking.getAccountNo().equalsIgnoreCase("admin")
//                || !invoking.isCheckDataPermission()) { //不需要添加数据权限的检查
//            return;
//        }
//        //如果是本人创建的数据和当前访问用户在具有相应的查询权限的组中也可以查询到。
//        where.orOperator(Criteria.where("createUser").is(invoking.getAccountNo()),
//                Criteria.where(invoking.getFieldName()).in(invoking.getUserGroupIDs()));
//    }

    public List<T> groupByFind(String inputCollectionName){
        GroupBy groupBy = GroupBy.key("ciClassID").initialDocument("{count:0}").reduceFunction("function(doc, out){out.count++}")
                .finalizeFunction("function(out){return out;}");
        GroupByResults<T> res = mongoTemplate.group("test", groupBy, this.getEntityClass());
        System.out.println(res.toString());
        return null;
    }


}

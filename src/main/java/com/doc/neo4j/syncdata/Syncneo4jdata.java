package com.doc.neo4j.syncdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doc.Manager.Service.ComService;
import com.doc.StartMain;
import com.doc.UtilsTools.UtilsTools;
import com.doc.neo4j.mode.CpClass;
import com.doc.neo4j.syncdata.servermoudel.Neo4jmoudel;
import com.doc.neo4j.syncdata.service.Neo4jservice;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.cypher.internal.javacompat.MapRow;
import org.neo4j.graphdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.rmi.CORBA.Util;
import java.io.File;
import java.util.*;

/**
 * com.doc.neo4j.syncdata 于2019/1/7 由Administrator 创建 .
 */
@Component("Syncneo4jdata")
public class Syncneo4jdata implements ComService {
    private static final Logger logger = LoggerFactory.getLogger(Syncneo4jdata.class);

    @Autowired
    private Neo4jmoudel neo4jmoudel;
    @Autowired
    private Neo4jservice neo4jservice;

    public void start() {
        initFloder(this.neo4jservice.getdataPath() + File.separator + "data");
        neo4jmoudel.start();
    }

    @Override
    public void stop() {
        neo4jmoudel.stop();
    }

    public void saveOrUpdateCps(List<CpClass> cps) {
        logger.info("neo4j connect start!");
        GraphDatabaseService graphDb = this.neo4jservice.getdatabase();
        Transaction tx = graphDb.beginTx();
        try { // Perform DB operations
            for (CpClass cpClass : cps) {
                CreateOrUpdateNode(cpClass);
                tx.success();
            }
        } catch (Exception e) {

        } finally {
            tx.close();
        }
        logger.info("neo4j connect success");
    }


    public void saveOrUpdateCp(CpClass cpClass) {
        logger.info("neo4j connect start!");
        GraphDatabaseService graphDb = this.neo4jservice.getdatabase();
        Transaction tx = graphDb.beginTx();
        try { // Perform DB operations
            CreateOrUpdateNode(cpClass);
            tx.success();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.close();
        }
        logger.info("neo4j connect success!");
    }

    public void DeleteCp(CpClass cpClass) {
        logger.info("neo4j connect start!");
        GraphDatabaseService graphDb = this.neo4jservice.getdatabase();
        Transaction tx = graphDb.beginTx();
        try { // Perform DB operations
            DeleteNode(cpClass);
            tx.success();
        } catch (Exception e) {

        } finally {
            tx.close();
        }
        logger.info("neo4j connect success!");
    }

    //初始化文件
    public void initFloder(String path) {
        UtilsTools.delFolder(path);
    }

    //新建节点
    private Node CreateNode(CpClass cpClass, String cpid) {
        Node node = this.neo4jservice.getdatabase().createNode(createLabel(cpClass));
        this.neo4jservice.PutMapid(cpid, node.getId());
        node.setProperty("id", cpid);
        node.setProperty("cpid", cpClass.getCpid());
        //在这里循环cp类里面的数据，将数据插入到neo4j里面
        for (Map.Entry<String, Object> kv : cpClass.getDataMap().entrySet()) {
            if (kv.getValue() == null) {
                node.setProperty(kv.getKey(), "");
            } else {
                if (kv.getValue() instanceof ArrayList) {
                    logger.info(JSON.toJSONString(kv.getValue()));
                    node.setProperty(kv.getKey(), JSON.toJSONString(kv.getValue()));
                } else {
                    node.setProperty(kv.getKey(), kv.getValue());
                }
            }
        }
        return node;
    }

    /**
     * Method  通过查询语句查询数据，返回数据字段需要制定
     * 示例  String query = "match (n:`123`)  return n.部门名称 as 部门名称,n.部门责任 as 部门责任";
     * * @param null
     *
     * @Date 2019/1/8
     * @description:a
     */
    public List<Map<String, Object>> excuteList(String cysql) {
        List<Map<String, Object>> list = new ArrayList<>();
        //查询数据库
        Map<String, Object> parameters = new HashMap<String, Object>();
        GraphDatabaseService graphDb = this.neo4jservice.getdatabase();
        Map<String, Object> row;
        try (Result result = graphDb.execute(cysql, parameters)) {
            while (result.hasNext()) {
                row = result.next();
                list.add(row);
            }
        }
        return list;
    }


    /**
     * @Method 根据查询语句查询数据，返回所有字段以及数据
     * 示例  String query = "match (n:`123`)  return n,n.部门名称 as 名称,n.部门责任 as 职责";
     * * @param null
     * @Date 2019/1/8
     * * @description:a
     */
    public List<Map<String, Object>> excuteListByAll(String cysql) {
        List<Map<String, Object>> list = new ArrayList<>();
        //查询数据库
        Map<String, Object> parameters = new HashMap<String, Object>();
        GraphDatabaseService graphDb = this.neo4jservice.getdatabase();
        Transaction tx = graphDb.beginTx();

        try (Result result = graphDb.execute(cysql, parameters)) {
            List<String> columns = result.columns();
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                MapRow mapRow = new MapRow(row);
                Map<String, Object> clmrow = new HashedMap();
                //拷贝map对象
                clmrow.putAll(row);
                for (String clm : columns) {
                    if (UtilsTools.isNode(mapRow, clm)) {
                        Node node = mapRow.getNode(clm);
                        clmrow.remove(clm);
                        Iterator<String> it = node.getPropertyKeys().iterator();
                        List<String> pks = new LinkedList<String>();
                        while (it.hasNext()) {
                            pks.add(it.next());
                        }
                        for (String pk : pks) {
                            clmrow.put(pk, node.getProperty(pk));
                        }
                    }
                }
                list.add(clmrow);
            }
        }
        tx.close();
        return list;
    }

    /**
     * 功能描述:
     *
     * @param null 1
     * @return : 获取总条数
     */
    public int getTotalCount(String cpname,String where){
        int total=0;
        //拼接查询语句
        String start="match (n:`";
        String beforew="`)";
        String countEnd=" RETURN count(n) as total";
        //首先要查询出总的条数，然后再查询分页的条数
        String totalsql=start+cpname+beforew+where+countEnd;
        List<Map<String, Object>> totaldatas=this.excuteListByAll(totalsql);
        for (Map<String,Object> totalmap:totaldatas){
            if(totalmap.containsKey("total")){
                total=Integer.valueOf(totalmap.get("total").toString());
            }
        }
        return total;
    }

    /**
     * Method 根据查询语句查询数据，只能查询一个表中所有数据根据字段显示
     * 示例  String query = "match (n:`123`)  return n";
     * * @param null
     *
     * @Date 2019/1/8
     * @description:a
     */
    public List<Map<String, Object>> excuteListByOneAll(String cysql) {
        List<Map<String, Object>> list = new ArrayList<>();
        //查询数据库
        Map<String, Object> parameters = new HashMap<String, Object>();
        GraphDatabaseService graphDb = this.neo4jservice.getdatabase();
        Transaction tx = graphDb.beginTx();
        Map<String, Object> row;
        try (Result result = graphDb.execute(cysql, parameters)) {
            List<String> columns = result.columns();
            for (String clm : columns) {
                //获取指定列的结果集
                Iterator<Node> n_column = result.columnAs(clm);
                List<Node> nodes = IteratorUtils.toList(n_column);
                for (Node node : nodes) {
                    row = new HashedMap();
                    Iterator<String> it = node.getPropertyKeys().iterator();
                    List<String> pks = new LinkedList<String>();
                    while (it.hasNext()) {
                        pks.add(it.next());
                    }
                    for (String pk : pks) {
                        System.out.println(node.getProperty(pk));
                        row.put(pk, node.getProperty(pk));
                    }
                    list.add(row);
                }
            }

        }
        tx.close();
        return list;
    }

    private void CreateOrUpdateNode(CpClass cpClass) {
        String cpid = cpClass.getId();
        Long neo4jId = this.neo4jservice.getNeo4jId(cpid);
        Node node = null;
        //开启事务
        if (neo4jId != null) {
            //已经存在了则更新数据
            //直接覆盖数据就行
            node = this.neo4jservice.getdatabase().getNodeById(neo4jId);
            //先删除已有的属性值，然后再重新添加。
//            Iterator<String> it = node.getPropertyKeys().iterator();
//            List<String> pks = new LinkedList<String>();//获取里面的属性值
//            while (it.hasNext()) {
//                pks.add(it.next());
//            }
//            for (String pk : pks) {
//                node.removeProperty(pk);
//            }
            node.setProperty("id", cpid);
            node.setProperty("cpid", cpClass.getCpid());
            for (Map.Entry<String, Object> kv : cpClass.getDataMap().entrySet()) {
                if (kv.getValue() == null){
                    node.setProperty(kv.getKey(), "");
                }
                else {
                    if (kv.getValue() instanceof ArrayList) {
                        logger.info(JSON.toJSONString(kv.getValue()));
                        node.setProperty(kv.getKey(), JSON.toJSONString(kv.getValue()));
                    } else {
                        node.setProperty(kv.getKey(), kv.getValue());
                    }
                }
            }
        } else {
            //数据不存在则添加数据
            node = CreateNode(cpClass, cpid);
        }
    }

    private void DeleteNode(CpClass cpClass) {
        String cpid = cpClass.getId();
        Long neo4jId = this.neo4jservice.getNeo4jId(cpid);
        Node node = null;
        //开启事务
        if (neo4jId != null) {
            //删除neo4j里面的数据
            node = this.neo4jservice.getdatabase().getNodeById(neo4jId);
            node.delete();
        }
    }

    private Label createLabel(final CpClass cls) {
        return new Label() {
            @Override
            public String name() {
                return cls.getName();
            }
        };
    }

    @Override
    public String getServiceName() {
        return "Syncneo4jdata";
    }
}

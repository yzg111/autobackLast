package com.doc.neo4j.syncdata.service;

import com.doc.Manager.Service.ComService;
import com.doc.UtilsTools.UtilsTools;
import com.doc.neo4j.mode.CpClass;
import com.doc.neo4j.syncdata.server.Neo4jserver;
import org.bouncycastle.operator.MacCalculatorProvider;
import org.neo4j.graphdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * com.doc.neo4j.syncdata.server 于2019/1/7 由Administrator 创建 .
 */
@Component("neo4jservice")
public class Neo4jservice implements ComService {
    @Autowired
    private Neo4jserver neo4jserver;

    //利用mongodb数据库里面的id和neo4j里面的id相对应
    private Map<String, Long> idmap;

    public void start() {
        neo4jserver.start();
        idmap = Collections.synchronizedMap(new HashMap<String, Long>());
    }

    public void stop() {
        neo4jserver.stop();
        //清除内存中的数据
        idmap.clear();
    }

    public GraphDatabaseService getdatabase() {
        return this.neo4jserver.getGraphDbService();
    }

    public String getdataPath(){
        return this.neo4jserver.getDbPath();
    }

    public void PutMapid(String CpId, Long neo4jId) {
        idmap.put(CpId, neo4jId);
    }

    public Long getNeo4jId(String Cpid) {
        Long neo4jId=idmap.get(Cpid);
        return neo4jId;
    }

    @Override
    public String getServiceName() {
        return "neo4jservice";
    }
}

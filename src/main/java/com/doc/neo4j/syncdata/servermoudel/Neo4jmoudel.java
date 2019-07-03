package com.doc.neo4j.syncdata.servermoudel;

import com.doc.Manager.Service.ComService;
import com.doc.neo4j.syncdata.service.Neo4jservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 模块启动类
 * com.doc.neo4j.syncdata.servermoudel 于2019/1/7 由Administrator 创建 .
 */
@Component("Neo4jmoudel")
public class Neo4jmoudel implements ComService {
    @Autowired
    private Neo4jservice neo4jservice;

    public void start(){
        neo4jservice.start();
    }
    public void stop(){
        neo4jservice.stop();
    }

    @Override
    public String getServiceName() {
        return "Neo4jmoudel";
    }
}

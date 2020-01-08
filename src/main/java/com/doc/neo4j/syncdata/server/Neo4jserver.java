package com.doc.neo4j.syncdata.server;

import com.doc.Manager.Service.ComService;
import com.doc.controller.CP_Class.CP_FormController;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.doc.neo4j.syncdata.service 于2019/1/7 由Administrator 创建 .
 */
@Component("neo4jserver")
public class Neo4jserver implements ComService{
    private static final Logger logger = LoggerFactory.getLogger(Neo4jserver.class);
    @Value(value = "${db.path}")
    private String               dbPath;
    //数据库实例
    private GraphDatabaseService graphDbService;

    public GraphDatabaseService getGraphDbService() {
        return graphDbService;
    }
    public String getDbPath() {
        return dbPath;
    }
    public void start(){
        logger.info("neo4j start create0!");
         graphDbService = new GraphDatabaseFactory()
                .newEmbeddedDatabase(new File(dbPath + File.separator + "data"));
        logger.info("neo4j start create1!");
        //配置数据库的一些数据
//        gb.setConfig("neostore.nodestore.db.mapped_memory", "100m");
//        gb.setConfig("neostore.propertystore.db.arrays.mapped_memory", "260m");
//        gb.setConfig("neostore.propertystore.db.index.keys.mapped_memory", "8m");
//        gb.setConfig("neostore.propertystore.db.index.mapped_memory", "8m");
//        gb.setConfig("neostore.propertystore.db.mapped_memory", "180M");
//        gb.setConfig("neostore.propertystore.db.strings.mapped_memory", "260m");
//        gb.setConfig("neostore.relationshipstore.db.mapped_memory", "200m");
//        gb.setConfig("node_cache_size", "400m");
//
//        gb.setConfig("node_cache_array_fraction", "6.18");
//        gb.setConfig("relationship_cache_array_fraction", "6.18");
//        gb.setConfig("relationship_cache_size", "800m");
//        gb.setConfig("use_memory_mapped_buffers", "false");

//        graphDbService = gb.newGraphDatabase();

    }
    public void stop(){
        registerShutdownHook(graphDbService);
    }



    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    @Override
    public String getServiceName() {
        return "neo4jserver";
    }
}

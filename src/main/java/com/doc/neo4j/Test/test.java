package com.doc.neo4j.Test;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.cypher.internal.ExecutionEngine;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.internal.GraphDatabaseAPI;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * com.doc.neo4j.Test 于2019/1/7 由Administrator 创建 .
 */
public class test {
    @Before
    public void init() {
        delFolder("./neo4j/data/");
    }

    @Test
    public void testneo4j() {
        GraphDatabaseService graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabase(new File("./neo4j/data/"));
        System.out.println("开始生成数据库!");
        //开启事务
        try (Transaction tx = graphDb.beginTx()) { // Perform DB operations
            CIClass ciClass = new CIClass("123", "234");
            Node steve = graphDb.createNode(createLabel(ciClass));
            steve.setProperty("name", "Steve");
            steve.setProperty("age", "12");
//            ciClass = new CIClass("234", "345");
            Node linda = graphDb.createNode(createLabel(ciClass));
            linda.setProperty("name", "Linda");
            linda.setProperty("age", "13");
//            steve.createRelationshipTo(linda, RelationshipTypes.IS_FRIEND_OF);
            System.out.println("created node name is" + steve.getProperty("name"));
            tx.success();
        }
        //查询数据库
        String query = "match (n:`234`) return n.name as name,n.age as age";
        Map<String, Object> parameters = new HashMap<String, Object>();
        try (Result result = graphDb.execute(query, parameters)) {
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                for (String key : result.columns()) {
                    System.out.printf("%s = %s%n", key, row.get(key));
                }
            }
        }


//        registerShutdownHook(graphDb);

    }

    @Test
    public void deletefile() {
        delFolder("./neo4j/Data/Test");
    }

    public class CIClass {
        private String domain;
        private String name;

        public CIClass() {
        }

        public CIClass(String domain, String name) {
            this.domain = domain;
            this.name = name;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private Label createLabel(final CIClass cls) {
        return new Label() {
            @Override
            public String name() {
                return cls.getName();
            }
        };
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
    //param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }


}

//package com.doc.scala;
//
//import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
//import scala.Array;
//import scala.collection.JavaConverters;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * com.doc.scala 于2020/12/2 由Administrator 创建 .
// */
//public class TestJavadyScala {
//
//
//    public static void  main(String[] args){
//        com.doc.scala.Test test=new com.doc.scala.Test();
//        List<CP_Class_Data> list =new ArrayList<>();
//        CP_Class_Data cpdata=new CP_Class_Data();
//        HashMap<String,Object> map=new HashMap<>();
//        map.put("haha","123");
//        map.put("中文测试11","2");
//        map.put("数组测试","[\"哈哈\",\"哈哈1\"]");
//        cpdata.setId("122333");
//        cpdata.setCpid("134566777");
//        cpdata.setDatamap(map);
//        list.add(cpdata);
//        HashMap<String,Object> map1=new HashMap<>();
//        cpdata=new CP_Class_Data();
//        map1.put("中文测试","恩恩");
//        map1.put("中文测试11","1");
//        map1.put("数组测试","[\"部门\",\"啦啦\",\"哈哈2\"]");
//        cpdata.setId("12wrsghhs33");
//        cpdata.setCpid("1345hjshs");
//        cpdata.setDatamap(map1);
//        list.add(cpdata);
//        //把java里面的list数组转换成scala里面的数组
//        scala.collection.immutable.List<CP_Class_Data> scalist=
//                JavaConverters.asScalaBufferConverter(list).asScala().toList();
//        List<CP_Class_Data> relist=test.dotest(scalist,"表的名字",
//                "select * from user");
//        System.out.println(12333);
//    }
//}

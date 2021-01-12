//package com.doc.scala
//
//import java.util
//
//import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data
//import com.doc.scala.common.LocalSparkSession
//import org.apache.spark.rdd.RDD
//import org.apache.spark.sql.types._
//import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
//import org.apache.spark.storage.StorageLevel
//import org.apache.spark.{SparkConf, SparkContext}
//import org.json4s.DefaultFormats
//import org.json4s.jackson.Json
//
//import scala.collection.JavaConverters._
//import scala.collection.{JavaConverters, mutable}
//import scala.util.parsing.json.{JSONArray, JSONObject}
//
///**
//  * com.doc.scala 于2020/12/2 由Administrator 创建 .
//  */
//class Test {
//
//
//
//  def dotest(num:List[CP_Class_Data])(name:String)(sql:String):util.List[CP_Class_Data]={
//    //将数组转成java数组的引入
////    import  scala.collection.JavaConverters._
//    val sparconf=new SparkConf().setMaster("local[*]").setAppName("TestCount");
//
////    var sc=new SparkContext(sparconf);
//
//    val sparksql=  SparkSession.builder().config(sparconf).getOrCreate()
//    import sparksql.implicits._
//
//    LocalSparkSession.set(sparksql);
//
//
//    val rdd: RDD[CP_Class_Data] = sparksql.sparkContext.makeRDD(num)
//
//    val listmap: RDD[mutable.Map[String, AnyRef]] = rdd.map(cpdata => {
//      var datamap = cpdata.getDatamap
//      datamap.put("id", cpdata.getId)
//      datamap.put("cpid", cpdata.getCpid)
//      var scamap=datamap.asScala
//      scamap
//    })
//    val mapString: RDD[String] = listmap.map(cpmap => {
//      Json(DefaultFormats).write(cpmap)
////      cpmap.mkString("{", ",", "}")
//    })
//    mapString.persist(StorageLevel.MEMORY_AND_DISK)
//    val reduceString: String = mapString.reduce((str1, str2) => {
//      str1 + ",\n"+str2
//    })
////    println(Seq(reduceString))
//    println(mapString)
//
////    val map: RDD[DataFrame] = mapString.map(rdd => {
////      val json: DataFrame = sparksql.read.json(sparksql.createDataset(Seq(rdd)))
////      json.show()
////      json
////    })
//
//    val ds1: DataFrame = sparksql.read.json(sparksql.createDataset(mapString))
////    ds1.write.mode("overwrite").format("json").saveAsTable("user1")
////    ds1.write.mode("overwrite").text("output/123.txt")
//    ds1.write.mode("overwrite").format("json").save("spark/中文测试")
//    ds1.write.mode("overwrite").format("json").save("spark/中文测试1")
//    //获取单个的数据表信息
////    val testjson: DataFrame = sparksql.read.json("output/中文测试/*.json")
////    testjson.show()
//    //注册自定义的函数进行sql查询
//    sparksql.udf.register("arrayin" ,(array:String,indata:String)=>{
//      val split= array.replace("[","").replace("]","").replace("\"","").split(",")
//      println("-----------"+split.toString,indata,array,array.replace("[","").replace("]",""))
//      var flag=false;
//      split.foreach((item)=>{
//        println("---------item----------",item)
//        if (item==indata){
//          flag=true
//        }
//      })
//      flag
//    })
//    //获取全部表的数据信息
//    val testalljson: DataFrame = sparksql.read.json("spark/**/*.json")
//    testalljson.show()
//    testalljson.createOrReplaceTempView("alltable")
////    sparksql.sql("select * from `alltable` where `数组测试` like '%哈哈%'").show()
//    sparksql.sql("select * from `alltable` where arrayin(`数组测试`, '啦啦')").show()
//
//
////    listmap.collect().foreach(println)
//
////    val ds: Dataset[mutable.Map[String, AnyRef]] = listmap.toDS()
//    ds1.createOrReplaceGlobalTempView("`中文测试表`")
//    ds1.createOrReplaceGlobalTempView("`user`")
////    sparksql.sql("select * from global_temp.`user` where `中文测试` = 1").show()
////    sparksql.sql("select * from `user1` where `中文测试` = 1").show()
//    sparksql.sql("select * from global_temp.`中文测试表` where `中文测试` = '恩恩'").show()
//    sparksql.sql("select * from global_temp.`中文测试表` where `中文测试11` = '2'").show()
//    sparksql.sql("select * from global_temp.`中文测试表` where `中文测试11` < '2'").show()
//    sparksql.sql("select * from global_temp.`中文测试表` where `haha` is not null").show()
//    val rdd1: RDD[Row] = sparksql.sql("select * from global_temp.`中文测试表` where `haha` is not null").rdd
//
////    map.collect().map(cpdata=>{
////      println(cpdata.getId())
////      println(cpdata.getCpid)
////      println(cpdata.getDatamap)
////      if (cpdata.getDatamap().get("haha") != null) {
////        println(cpdata.getDatamap().get("haha"))
////      }
////    })
//    listmap.collect().map(datamap=>{
//      println(datamap)
//    })
//    println(num)
//
//    sparksql.stop()
//    //将数组转成java数组
//    num.asJava
//  }
//
//}

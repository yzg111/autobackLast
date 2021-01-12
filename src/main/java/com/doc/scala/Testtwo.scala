//package com.doc.scala
//
//import org.apache.spark.SparkConf
//import org.apache.spark.sql.SparkSession
//
///**
//  * com.doc.scala 于2020/12/4 由Administrator 创建 .
//  */
//object Testtwo {
//
//
//  def main(args: Array[String]): Unit = {
//    val sparconf=new SparkConf().setMaster("local[*]").setAppName("TestCount");
//
//    //    var sc=new SparkContext(sparconf);
//
//    val sparksql=  SparkSession.builder().config(sparconf)
//      .config("spark.sql.warehouse.dir","..\\park-warehouse")
//      .enableHiveSupport().getOrCreate()
//    import sparksql.implicits._
//
//    sparksql.sql("select * from `user1` where `中文测试` = 1").show()
//
//    sparksql.stop()
//  }
//}

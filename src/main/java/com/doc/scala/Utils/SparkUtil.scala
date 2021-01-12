package com.doc.scala.Utils

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * com.doc.scala.Utils 于2020/12/3 由Administrator 创建 .
  */
class SparkUtil {
  //    var sc=new SparkContext(sparconf);

  var sparksql:SparkSession=null
  var sparconf:SparkConf=null

  def InitSpark(master:String="local[*]",appname:String="Application")={
    this.sparconf=new SparkConf().setMaster(master).setAppName(appname);
    this.sparksql=  SparkSession.builder().config(this.sparconf).getOrCreate()
  }

  def getSpark():SparkSession={
    sparksql match {
      case spk:SparkSession=>{
        spk
      }
      case _=> null
    }
  }

  def stopSpark():Unit={
    this.sparksql.stop()
  }

}

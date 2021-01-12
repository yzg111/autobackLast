//package com.doc.scala.common
//
//import org.apache.spark.sql.SparkSession
//
///**
//  * com.doc.scala.common 于2020/12/3 由Administrator 创建 .
//  */
//object LocalSparkSession {
//
//  private val thlocal:ThreadLocal[SparkSession]=new ThreadLocal[SparkSession]()
//
//
//  def set(sparkSession: SparkSession):Unit={
//    this.thlocal.set(sparkSession)
//  }
//
//  def get():SparkSession={
//    this.thlocal.get()
//  }
//
//  def  clear():Unit={
//    this.thlocal.remove()
//  }
//
//}

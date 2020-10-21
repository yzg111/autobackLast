package com.doc.Manager.Quartz.Job;


import com.alibaba.fastjson.JSON;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScript;
import com.doc.Entity.MogoEntity.DataSourceSync.DataSetInfo;
import com.doc.Entity.MogoEntity.DataSourceSync.DataSetIntoMogo;
import com.doc.Entity.MogoEntity.DataSourceSync.DataSourceInfo;
import com.doc.Entity.MogoEntity.QuartzAllEntities.Quartz;
import com.doc.Manager.JDBCManager.JdbcComServiceImpl;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_Class_DataRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_GroovyScriptRepository;
import com.doc.Repository.MogoRepository.DataSourceSyncRepository.DataSetInfoRepository;
import com.doc.Repository.MogoRepository.DataSourceSyncRepository.DataSetIntoMogoRepository;
import com.doc.Repository.MogoRepository.DataSourceSyncRepository.DataSourceInfoRepository;
import com.doc.Repository.MogoRepository.QuartzAllEntities.QuartzRepository;
import com.doc.UtilsTools.GroovyTools;
import com.doc.config.Until.SpringContextUtil;
import com.doc.neo4j.mode.CpClass;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import jline.internal.Log;
import org.apache.commons.collections.map.HashedMap;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * com.doc.Manager.Quartz.Job 于2020/9/28 由Administrator 创建 .
 */
public class JdbcJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(JdbcJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("链接数据库的任务");
        JobExecutionContext c = context;
        //获取映射id，通过映射id查询映射信息，从而同步相应的数据
        try {
            QuartzRepository quartzRepository =
                    (QuartzRepository) SpringContextUtil.getBean("QuartzRepository1");
            DataSetIntoMogoRepository dataSetIntoMogoRepository1 =
                    (DataSetIntoMogoRepository) SpringContextUtil.getBean("DataSetIntoMogoRepository1");
            DataSetInfoRepository dataSetInfoRepository1 =
                    (DataSetInfoRepository) SpringContextUtil.getBean("DataSetInfoRepository1");
            DataSourceInfoRepository dataSourceInfoRepository1 =
                    (DataSourceInfoRepository) SpringContextUtil.getBean("DataSourceInfoRepository1");
            JdbcComServiceImpl jdbcComServiceImpl =
                    (JdbcComServiceImpl) SpringContextUtil.getBean("JdbcComServiceImpl");
            Syncneo4jdata syncneo4jdata= (Syncneo4jdata) SpringContextUtil.getBean("Syncneo4jdata");
            Cp_ClassRepository cp_classRepository=
                    (Cp_ClassRepository) SpringContextUtil.getBean("Cp_ClassRepository1");
            Cp_Class_DataRepository cpClassDataRepository=
                    (Cp_Class_DataRepository) SpringContextUtil.getBean("Cp_Class_DataRepository1");
            String id = c.getTrigger().getKey().getName();
            Quartz quartz=quartzRepository.findById(id);
            String ysid=quartz.getScriptid();



            DataSetIntoMogo dataSetIntoMogo = dataSetIntoMogoRepository1.findById(ysid);
            DataSetInfo dataSetInfo = dataSetInfoRepository1.findById(dataSetIntoMogo.getDatasetid());
            DataSourceInfo dataSourceInfo = dataSourceInfoRepository1.findById(dataSetInfo.getDatasourceid());
            //根据数据源信息查询出数据
            jdbcComServiceImpl.SetConnection(dataSourceInfo.getDriver(), dataSourceInfo.getUrl(),
                    dataSourceInfo.getUsername(), dataSourceInfo.getPassword());
            ResultSet resultSet=jdbcComServiceImpl.selectDataBysql(dataSetInfo.getSqlinfo());
            //获取相互映射的信息
            List<Map<String,Object>> ysdatamap=dataSetIntoMogo.getColumsdatamapcp();
            while(resultSet.next()){
                //将数据进行对应
                CP_Class_Data cpClassData=new CP_Class_Data();
                cpClassData.setCpid(dataSetIntoMogo.getCpid());
                Map<String,Object>datamap=new HashedMap();
                for (int i=0;i<ysdatamap.size();i++){
                    Map<String,Object> map=ysdatamap.get(i);
                    if(map.get("tocpclm")!=null&&map.get("sqlcolunm")!=null){
                        datamap.put(map.get("tocpclm").toString(),resultSet.getString(map.get("sqlcolunm").toString()));
                    }

                }
                cpClassData.setDatamap(datamap);
                CP_Class_Data data = cpClassDataRepository.save(cpClassData);
                if(data.getId()!=null){
                    String cpid=data.getCpid();
                    CP_Class cp_class=cp_classRepository.findById(cpid);
                    CpClass cpClass=new CpClass(cp_class.getCpname(),data.getId(),data.getDatamap(),cpid);
                    System.out.println(JSON.toJSONString(data));
                    logger.info("更新数据："+JSON.toJSONString(data));
                    //在这里更新neo4j数据库中的数据
                    syncneo4jdata.saveOrUpdateCp(cpClass);
                }

            }
            //执行配置好的脚本信息
            if(quartz.getIsusescript()){
                //如果需要执行脚本，则调用脚本即可
                Cp_GroovyScriptRepository cp_groovyScriptRepository= (Cp_GroovyScriptRepository)
                        SpringContextUtil.getBean("Cp_GroovyScriptRepository1");
                CP_GroovyScript cp_groovyScript=cp_groovyScriptRepository.findById(quartz.getSyncscriptid());
                GroovyTools groovyTools=new GroovyTools();
                Object res=groovyTools.AssemAndRunScript(cp_groovyScript,syncneo4jdata,cp_classRepository,cpClassDataRepository);
                logger.info("执行结果："+res);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}

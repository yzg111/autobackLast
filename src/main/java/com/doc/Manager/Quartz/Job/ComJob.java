package com.doc.Manager.Quartz.Job;

import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScript;
import com.doc.Entity.MogoEntity.QuartzAllEntities.Quartz;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_Class_DataRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_GroovyScriptRepository;
import com.doc.Repository.MogoRepository.QuartzAllEntities.QuartzRepository;
import com.doc.UtilsTools.GroovyTools;
import com.doc.config.Until.SpringContextUtil;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过实现job的方式来运行相应的任务调度
 * Doc.config.QuartzConfig.job 于2017/8/30 由Administrator 创建 .
 */
public class ComJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(ComJob.class);


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
            JobExecutionContext c=context;

        QuartzRepository quartzRepository =
                (QuartzRepository) SpringContextUtil.getBean("QuartzRepository1");
            //根据脚本编码获取脚本信息，并执行脚本信息
        Cp_GroovyScriptRepository cp_groovyScriptRepository= (Cp_GroovyScriptRepository)
                SpringContextUtil.getBean("Cp_GroovyScriptRepository");
        Syncneo4jdata syncneo4jdata= (Syncneo4jdata) SpringContextUtil.getBean("Syncneo4jdata");
        Cp_ClassRepository cp_classRepository=
                (Cp_ClassRepository) SpringContextUtil.getBean("Cp_ClassRepository");
        Cp_Class_DataRepository cpClassDataRepository=
                (Cp_Class_DataRepository) SpringContextUtil.getBean("Cp_Class_DataRepository");

        String id=c.getTrigger().getKey().getName();

        Quartz quartz=quartzRepository.findById(id);
        String scriptid=quartz.getScriptid();//获取脚本编码
        CP_GroovyScript cp_groovyScript=cp_groovyScriptRepository.findById(scriptid);
        logger.info("开始执行脚本："+cp_groovyScript.getScriptname());
        //执行脚本
        GroovyTools groovyTools=new GroovyTools();
        Object res=groovyTools.AssemAndRunScript(cp_groovyScript,syncneo4jdata,cp_classRepository,cpClassDataRepository);
//        String groovyscript=cp_groovyScript.getScriptcontent();
//        StringBuilder sb=new StringBuilder();
//        sb.append("import java.text.SimpleDateFormat;");
//        sb.append("import com.doc.UtilsTools.CpTools;");
//        sb.append("import com.doc.neo4j.syncdata.*;");
//        sb.append("def cpTools=new CpTools();");
//        sb.append("cpTools.setSyncneo4jdata(syncneo4jdata);");
//        sb.append("cpTools.setCpClassRepository(cp_classRepository);");
//        sb.append("cpTools.setCpClassDataRepository(cpClassDataRepository);");
//
//
//        sb.append(groovyscript);
//        Map<String,Object> pm=new HashMap<>();
//
//        pm.put("syncneo4jdata",syncneo4jdata);
//        pm.put("cp_classRepository",cp_classRepository);
//        pm.put("cpClassDataRepository",cpClassDataRepository);
//
//        Object res=groovyTools.runGroovyScript(sb.toString(),pm,cp_groovyScript.getScriptname());
        logger.info("执行结果："+res);

    }
}

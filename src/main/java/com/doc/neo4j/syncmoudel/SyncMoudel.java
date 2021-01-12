package com.doc.neo4j.syncmoudel;

import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Manager.Service.ComService;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_Class_DataRepository;
import com.doc.neo4j.mode.CpClass;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * com.doc.neo4j.syncmoudel 于2019/1/8 由Administrator 创建 .
 */
@Component("SyncMoudel")
public class SyncMoudel implements ComService {

    @Autowired
    private Syncneo4jdata syncneo4jdata;

    @Autowired
    @Qualifier("Cp_Class_DataRepository")
    private Cp_Class_DataRepository cpClassDataRepository;
    @Autowired
    @Qualifier("Cp_ClassRepository")
    private Cp_ClassRepository cpClassRepository;

    @Override
    public String getServiceName() {
        return "SyncMoudel";
    }

    @Override
    public void start() {
        syncneo4jdata.start();
        //mogodb里面数据同步
        List<CP_Class_Data> datas=cpClassDataRepository.findAll();
        List<CpClass> cps=new LinkedList<>();
        for (CP_Class_Data data:datas){
            String cpid=data.getCpid();
            CP_Class cp_class=cpClassRepository.findById(cpid);
            CpClass cpClass=new CpClass(cp_class.getCpname(),data.getId(),data.getDatamap(),cpid);
            cps.add(cpClass);
            //在这里将数据全部同步进spark的临时表里面

        }
        syncneo4jdata.saveOrUpdateCps(cps);

    }

    @Override
    public void stop() {
        syncneo4jdata.stop();
    }
}

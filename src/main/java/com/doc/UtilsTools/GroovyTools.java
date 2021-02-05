package com.doc.UtilsTools;

import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScript;
import com.doc.Entity.MogoEntity.GlobalValuesEntity.GlobalValuesEntity;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_Class_DataRepository;
import com.doc.Repository.MogoRepository.GlobalValuesRepository.GlobalValuesRepository;
import com.doc.config.LogConfig.MyLog4JAppender.Log4j2OutPrintStream;
import com.doc.config.LogConfig.MyLog4JAppender.Log4jOutPrintWriter;
import com.doc.controller.CP_Class.CP_FormController;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.*;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.doc.UtilsTools 于2019/2/19 由Administrator 创建 .
 */
@Component("GroovyTools")
public class GroovyTools {
    final ClassLoader cl = GroovyShell.class.getClassLoader();
    final ScriptEngineManager factory = new ScriptEngineManager(cl);
    final ScriptEngine engine = factory.getEngineByName("groovy");
    final Compilable compilable = (Compilable) engine;
    @Autowired
    private GlobalValuesRepository globalValuesRepository;
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(GroovyTools.class);

    public Object AssemAndRunScript(CP_GroovyScript cp_groovyScript,
                                  Syncneo4jdata syncneo4jdata,
                                  Cp_ClassRepository cp_classRepository,
                                  Cp_Class_DataRepository cpClassDataRepository){
        StringBuilder sb=new StringBuilder();
        sb.append("import java.text.SimpleDateFormat;");
        sb.append("import com.aspose.cells.Workbook;");
        sb.append("import com.aspose.cells.WorkbookDesigner;");
        sb.append("import com.aspose.words.Document;");
        sb.append("import com.doc.UtilsTools.CpTools;");
        sb.append("import com.doc.UtilsTools.ApTools;");
        sb.append("import com.doc.UtilsTools.ExportTools;");
        sb.append("import com.doc.neo4j.syncdata.*;");
        sb.append("def cpTools=new CpTools();");
        sb.append("def aposeTools=new ApTools();");
        sb.append("def exportTools=new ExportTools();");
        sb.append("cpTools.setSyncneo4jdata(syncneo4jdata);");
        sb.append("cpTools.setCpClassRepository(cp_classRepository);");
        sb.append("cpTools.setCpClassDataRepository(cpClassDataRepository);");


        sb.append(cp_groovyScript.getScriptcontent());
        Map<String,Object> pm=new HashMap<>();
        pm.put("syncneo4jdata",syncneo4jdata);
        pm.put("cp_classRepository",cp_classRepository);
        pm.put("cpClassDataRepository",cpClassDataRepository);
        Object res=runGroovyScript(sb.toString(),pm,cp_groovyScript.getScriptname());
//        Object res=runGroovyShellScript(sb.toString(),pm);
        return res;
    }

    //获取执行脚本基本的引入信息
    public StringBuilder getBasicSb(){
        StringBuilder sb=new StringBuilder();
        sb.append("import java.text.SimpleDateFormat;");
        sb.append("import com.aspose.cells.Workbook;");
        sb.append("import com.aspose.cells.WorkbookDesigner;");
        sb.append("import com.aspose.words.Document;");
        sb.append("import com.doc.UtilsTools.CpTools;");
        sb.append("import com.doc.UtilsTools.ApTools;");
        sb.append("import com.doc.UtilsTools.ExportTools;");
        sb.append("import com.doc.neo4j.syncdata.*;");
        sb.append("def cpTools=new CpTools();");
        sb.append("def aposeTools=new ApTools();");
        sb.append("def exportTools=new ExportTools();");
        sb.append("cpTools.setSyncneo4jdata(syncneo4jdata);");
        sb.append("cpTools.setCpClassRepository(cp_classRepository);");
        sb.append("cpTools.setCpClassDataRepository(cpClassDataRepository);");
        sb.append("aposeTools.setModelFileRespository(modelFileRespository);");
        sb.append("aposeTools.setGlobalValue(globalValue);");
        sb.append("exportTools.setGlobalValue(globalValue);");
        return sb;
    }

    /**
     * 功能描述:获取全局变量信息
     *
     */
    public Map<String,String> getGlobalValues(){
        Map<String,String> globalvalues=new HashedMap();
        List<GlobalValuesEntity> globalValuesEntityList=globalValuesRepository.findAll();
        for(GlobalValuesEntity glb:globalValuesEntityList){
            globalvalues.put(glb.getGlobalname(),glb.getGlobalval());
        }

        return globalvalues;
    }

    /**
     * @author yzg
     * @创建时间 2017年3月2日 下午9:43:08
     * @desc 执行groovy脚本(不指定方法)
     * @param script
     *            要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param params
     *            执行grovvy需要传入的参数
     * @return 脚本执行结果
     */
    public Object runGroovyScript(String script, Map<String, Object> params,String scriptname) {
        if (script == null || "".equals(script)){
            logger.error("方法runGroovyScript无法执行，传入的脚本为空！");
            throw new RuntimeException("方法runGroovyScript无法执行，传入的脚本为空");
        }


        try {
            Bindings bindings = engine.createBindings();
            bindings.putAll(params);
//            bindings.put("out",new PrintStream(System.out ));
            //重定向groovy脚本打印信息，然后让日志获取到打印信息
            Log4jOutPrintWriter writer=new Log4jOutPrintWriter(System.out);
//            PrintWriter writer = new PrintWriter(System.out,true);
//            writer.println("测试测试");
//            PrintWriter writer = new PrintWriter("log.txt");
            engine.getContext().setWriter(writer);
            engine.getContext().setErrorWriter(writer);

            return engine.eval(script, bindings);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage());
            logger.error(scriptname+"脚本执行失败！");
            return null;
        }
    }
    /**
     * @author yzg
     * @创建时间 2017年3月2日 下午9:43:08
     * @desc 执行groovy脚本(不指定方法)
     * @param script
     *            要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param params
     *            执行grovvy需要传入的参数
     * @return 脚本执行结果
     */
    public Object runGroovyComScript(String script, Map<String, Object> params) {
        if (script == null || "".equals(script))
            throw new RuntimeException("方法runGroovyScript无法执行，传入的脚本为空");

        try {
            Bindings bindings = engine.createBindings();
            bindings.putAll(params);
            CompiledScript compiledScript=compile(script);
            return compiledScript.eval(bindings);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author yzg
     * @创建时间 2017年3月2日 下午9:43:08
     * @desc 执行groovy脚本(不指定方法)
     * @param script
     *            要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param params
     *            执行grovvy需要传入的参数
     * @return 脚本执行结果
     */
    public Object runGroovyShellScript(String script, Map<String, Object> params) {
        if (script == null || "".equals(script))
            throw new RuntimeException("方法runGroovyScript无法执行，传入的脚本为空");

        try {
            Binding binding = new Binding();
            for(String key: params.keySet()){
                binding.setVariable(key,params.get(key));
            }
//            binding.setProperty("out",new PrintStream(System.out));
            GroovyShell shell = new GroovyShell(binding);
            Object value = shell.parse(script);
            return value;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    // 此方法的作用是将传入的脚本字符串编译成脚本对象
    public final CompiledScript compile(String script) throws Exception {
        if(StringUtils.isEmpty(script))
            return null;
        try {
            return compilable.compile(script);
        } catch (Exception e) {
            throw e;
        }
    }
}

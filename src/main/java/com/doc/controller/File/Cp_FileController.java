package com.doc.controller.File;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Entity.MogoEntity.CP_Class.CP_File;
import com.doc.Entity.MogoEntity.CP_Class.CP_Table;
import com.doc.Entity.MogoEntity.IndiviEntity.IndiviEntity;
import com.doc.Entity.MogoEntity.ModelFileEntity.ModelFile;
import com.doc.Manager.SelfAnno.DelDataLog;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.CP_FileRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_Class_DataRepository;
import com.doc.Repository.MogoRepository.Cp_Class.impl.Cp_FileComRespository;
import com.doc.Repository.MogoRepository.IndiviEntity.IndiviRepository;
import com.doc.Repository.MogoRepository.ModelFileRespository.ModelFileRespository;
import com.doc.UtilsTools.AposeUtils;
import com.doc.UtilsTools.DocToHtml;
import com.doc.UtilsTools.FastDFSClientUtils;
import com.doc.UtilsTools.UtilsTools;
import com.doc.config.GlobalValue;
import com.doc.controller.CP_Class.CP_TableController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * com.doc.controller.File 于2019/11/22 由Administrator 创建 .
 */
@RestController
@Api(description = "操作附件信息", tags = "操作附件信息")
@RequestMapping("/cp")
public class Cp_FileController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CP_TableController.class);

    @Autowired
    private CP_FileRepository cp_fileRepository;
    @Autowired
    @Qualifier("Cp_ClassRepository")
    private Cp_ClassRepository cpClassRepository;

    @Autowired
    @Qualifier("Cp_Class_DataRepository")
    private Cp_Class_DataRepository cp_class_dataRepository;

    @Autowired
    private Cp_FileComRespository cp_fileComRespository;

    @Autowired
    private ModelFileRespository modelFileRespository;
    @Autowired
    private IndiviRepository indiviRepository;

    @Autowired
    private GlobalValue globalValue;

    @RequestMapping(value = "/incpfile", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个附件数据！")
    @ApiOperation(value = "插入一个附件数据！", notes = "插入一个附件数据！")
    //@RequestBody CP_Class cp
    public Back incpfile(@RequestBody CP_File cp) {
        logger.info("插入一个附件数据！");

        System.out.println(cp.toString());
        CP_File i = cp_fileRepository.save(cp);

        Back<CP_File> back = new Back<>();
        back.setData(i);
        back.setCmd("附件信息上传成功！");
        back.setState(1);

        return back;
    }


    @RequestMapping(value = "/viewfile", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "预览文件！")
    @ApiOperation(value = "预览文件！", notes = "预览文件！")
    public void viewfile(@RequestParam String fileaddress, @RequestParam String filename,
                         HttpServletResponse response, HttpServletRequest request) throws Exception {
        logger.info("预览附件信息！");
        System.out.println(fileaddress.toString());
        InputStream inputStream;

        byte[] data = null;
        response.reset();
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/pdf");
//        response.setContentType(UtilsTools.getContentType(filename));
        //文件名后缀
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        File file1 = new File(globalValue.getFilepath() + fileaddress);
        if (".doc".equalsIgnoreCase(fileExtension) || ".docx".equalsIgnoreCase(fileExtension)) {
            response.setContentType("application/pdf");
            if (file1.exists()) {
                inputStream = new FileInputStream(file1);
//            inputStream = FastDFSClientUtils.downloadFile(fileaddress);
                data = AposeUtils.doc2pdf(inputStream);
            }

        } else if (".xls".equalsIgnoreCase(fileExtension) || ".xlsx".equalsIgnoreCase(fileExtension)) {
            response.setContentType("application/pdf");
            if (file1.exists()) {
                inputStream = new FileInputStream(file1);
//            inputStream = FastDFSClientUtils.downloadFile(fileaddress);
                data = AposeUtils.excel2pdf(inputStream);
            }
        } else if (".ppt".equalsIgnoreCase(fileExtension) || ".pptx".equalsIgnoreCase(fileExtension)) {
            response.setContentType("application/pdf");
            if (file1.exists()) {
                inputStream = new FileInputStream(file1);
//            inputStream = FastDFSClientUtils.downloadFile(fileaddress);
                data = AposeUtils.ppt2pdf(inputStream);
            }
        } else if (".jpg".equalsIgnoreCase(fileExtension)
                || ".png".equalsIgnoreCase(fileExtension) ||
                ".jpeg".equalsIgnoreCase(fileExtension)
                || ".jpe".equalsIgnoreCase(fileExtension)
                ) {
            response.setContentType("image/jpeg");
            if (file1.exists()) {
                inputStream = new FileInputStream(file1);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                byte[] b = new byte[1024];
//                int len = -1;
//                while ((len = inputStream.read(b)) != -1) {
//                    bos.write(b, 0, len);
//                }
//                inputStream.close();
//                bos.close();
//                data = bos.toByteArray();
                data = UtilsTools.fileToArrayByte(inputStream);
            }
//            data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        } else if (".pdf".equalsIgnoreCase(fileExtension)) {
            response.setContentType("application/pdf");
            if (file1.exists()) {
                inputStream = new FileInputStream(file1);
                data = UtilsTools.fileToArrayByte(inputStream);
            }
//            data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        } else if (".txt".equalsIgnoreCase(fileExtension)
                || ".conf".equalsIgnoreCase(fileExtension)
                || ".def".equalsIgnoreCase(fileExtension)
                || ".in".equalsIgnoreCase(fileExtension)) {
            if (file1.exists()) {
                inputStream = new FileInputStream(file1);
                data = UtilsTools.fileToArrayByte(inputStream);
//            data = FastDFSClientUtils.downloadFilebytes(fileaddress);
            }

        } else {
            response.setContentType("application/octet-stream");
            if (file1.exists()) {
                inputStream = new FileInputStream(file1);
                data = UtilsTools.fileToArrayByte(inputStream);
//            data = FastDFSClientUtils.downloadFilebytes(fileaddress);
            }
        }
        ServletOutputStream outputStream = null;
        try {
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-disposition", "inline;filename=" +
                    new String(filename.getBytes("gb2312"), "ISO8859-1"));
            // 写出


            outputStream = response.getOutputStream();
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                System.out.println("viewfile success!");
//                outputStream.flush();
//                outputStream.close();
                System.out.println("viewfile success complete!");
//                outputStream = null;
            }

        }
    }

    @RequestMapping(value = "/viewdfsfile", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "预览dfs文件！")
    @ApiOperation(value = "预览dfs文件！", notes = "预览dfs文件！")
    public void viewdfsfile(@RequestParam String fileaddress, @RequestParam String filename,
                            HttpServletResponse response, HttpServletRequest request) throws Exception {
        logger.info("预览附件信息！");
        System.out.println(fileaddress.toString());
        InputStream inputStream;

        byte[] data;
        response.reset();
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/pdf");
//        response.setContentType(UtilsTools.getContentType(filename));
        //文件名后缀
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        if (".doc".equalsIgnoreCase(fileExtension) || ".docx".equalsIgnoreCase(fileExtension)) {
            response.setContentType("application/pdf");
            inputStream = FastDFSClientUtils.downloadFile(fileaddress);
            data = AposeUtils.doc2pdf(inputStream);
        } else if (".xls".equalsIgnoreCase(fileExtension) || ".xlsx".equalsIgnoreCase(fileExtension)) {
            response.setContentType("application/pdf");
            inputStream = FastDFSClientUtils.downloadFile(fileaddress);
            data = AposeUtils.excel2pdf(inputStream);
        } else if (".ppt".equalsIgnoreCase(fileExtension) || ".pptx".equalsIgnoreCase(fileExtension)) {
            response.setContentType("application/pdf");
            inputStream = FastDFSClientUtils.downloadFile(fileaddress);
            data = AposeUtils.ppt2pdf(inputStream);
        } else if (".jpg".equalsIgnoreCase(fileExtension)
                || ".png".equalsIgnoreCase(fileExtension) ||
                ".jpeg".equalsIgnoreCase(fileExtension)
                || ".jpe".equalsIgnoreCase(fileExtension)
                ) {
            response.setContentType("image/jpeg");
            data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        } else if (".pdf".equalsIgnoreCase(fileExtension)) {
            response.setContentType("application/pdf");
            data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        } else if (".txt".equalsIgnoreCase(fileExtension)
                || ".conf".equalsIgnoreCase(fileExtension)
                || ".def".equalsIgnoreCase(fileExtension)
                || ".in".equalsIgnoreCase(fileExtension)) {
            data = FastDFSClientUtils.downloadFilebytes(fileaddress);

        } else {
            response.setContentType("application/octet-stream");
            data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        }
        ServletOutputStream outputStream = null;
        try {
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-disposition", "inline;filename=" +
                    new String(filename.getBytes("UTF-8"), "UTF-8"));
            // 写出


            outputStream = response.getOutputStream();
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                System.out.println("viewfile success!");
//                outputStream.flush();
//                outputStream.close();
                System.out.println("viewfile success complete!");
//                outputStream = null;
            }

        }
        ;
//        return outputStream;
//        return inputStream;
    }

    @RequestMapping(value = "/downfile", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "下载文件！")
    @ApiOperation(value = "下载文件！", notes = "下载文件！")
    public void downfile(@RequestParam String fileaddress, @RequestParam String filename, HttpServletResponse response) throws IOException {
        logger.info("预览附件信息！");
        System.out.println(fileaddress.toString());
        File file1 = new File(globalValue.getFilepath() + fileaddress);
        byte[] data = null;
        if (file1.exists()) {
            FileInputStream fis = new FileInputStream(file1);
            data = UtilsTools.fileToArrayByte(fis);
        }

        response.reset();
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/xml");
        response.setContentType("application/octet-stream");
        ServletOutputStream outputStream = null;
        try {
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-disposition", "attachment;filename=" +
                    new String(filename.getBytes("gb2312"), "ISO8859-1"));
            // 写出
            outputStream = response.getOutputStream();
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                System.out.println("viewfile success!");
//                outputStream.flush();
//                outputStream.close();
                System.out.println("viewfile success complete!");
//                outputStream = null;
            }
        }
    }

    @RequestMapping(value = "/downdfsfile", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "下载dfs文件！")
    @ApiOperation(value = "下载dfs文件！", notes = "下载dfs文件！")
    public void downdfsfile(@RequestParam String fileaddress, @RequestParam String filename, HttpServletResponse response) throws IOException {
        logger.info("预览附件信息！");
        System.out.println(fileaddress.toString());
//        InputStream inputStream = FastDFSClientUtils.downloadFile(fileaddress);

        byte[] data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        response.reset();
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/xml");
        response.setContentType("application/octet-stream");
        ServletOutputStream outputStream = null;
        try {
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-disposition", "attachment;filename=" +
                    new String(filename.getBytes("gb2312"), "ISO8859-1"));
            // 写出
            outputStream = response.getOutputStream();
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                System.out.println("viewfile success!");
//                outputStream.flush();
//                outputStream.close();
                System.out.println("viewfile success complete!");
//                outputStream = null;
            }

        }

    }

    @RequestMapping(value = "/downlocalfile", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "下载文件！（本地文件）")
    @ApiOperation(value = "下载文件！（本地文件）", notes = "下载文件！（本地文件）")
    public void downlocalfile(@RequestParam String fileaddress, @RequestParam String filename, HttpServletResponse response) throws IOException {
        logger.info("预览附件信息！");
        System.out.println(fileaddress.toString());
        File file1 = new File(globalValue.getFilepath() + fileaddress);
//        InputStream inputStream = FastDFSClientUtils.downloadFile(fileaddress);

//        byte[] data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        byte[] data = null;
        if (file1.exists()) {
            FileInputStream fis = new FileInputStream(file1);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            data = bos.toByteArray();
            fis.close();
            bos.close();
        }

        response.reset();
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/xml");
        response.setContentType("application/octet-stream");
        ServletOutputStream outputStream = null;
        try {
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-disposition", "attachment;filename=" +
                    new String(filename.getBytes("gb2312"), "ISO8859-1"));
            // 写出
            outputStream = response.getOutputStream();
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                System.out.println("viewfile success!");
//                outputStream.flush();
//                outputStream.close();
                System.out.println("viewfile success complete!");
//                outputStream = null;
            }

        }
        ;
//        return outputStream;
//        return inputStream;
    }

    //查询父级相关数据的接口
    @RequestMapping(value = "/delfile", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除附件！")
    @ApiOperation(value = "删除附件！", notes = "删除附件！")
    public Back delCpData(@RequestParam String id, @RequestParam String fieldid) {
        CP_File listcpdatas = cp_fileRepository.findById(id);
        cp_fileRepository.delete(listcpdatas);

//        FastDFSClientUtils.deleteFile(fieldid);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("删除附件成功！");
        back.setState(1);

        return back;
    }

    //查询父级相关数据的接口
    @RequestMapping(value = "/delfilebyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除附件！")
    @ApiOperation(value = "删除附件！", notes = "删除附件！")
    public Back delfilebyid(@RequestParam String id) {
        CP_File listcpdatas = cp_fileRepository.findById(id);

//        FastDFSClientUtils.deleteFile(fieldid);
        //先把本地的文件删除
        String filepath = globalValue.getFilepath() + listcpdatas.getFileaddress();
        File file = new File(filepath);
        if (file.exists()) {
            file.delete();
        }
        cp_fileRepository.delete(listcpdatas);
        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("删除附件成功！");
        back.setState(1);

        return back;
    }


    //查询父级相关数据的接口
    @RequestMapping(value = "/getfilebydataid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据数据id查询出相应附件信息！")
    @ApiOperation(value = "根据数据id查询出相应附件信息！", notes = "根据数据id查询出相应附件信息！")
    public Back getfilebydataid(@RequestParam String dataid, @RequestParam int pageno, @RequestParam int pagesize) {
        //分页查询
//        List<CP_File> listcpdatas = cp_fileComRespository.getFilesByDataidPage(pageno,pagesize,dataid);
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        Pageable pageable = new PageRequest(pageno, pagesize, sort);
        //分页查询
        Page<CP_File> listcpdatas = cp_fileRepository.findByDataid(dataid, pageable);
//        List<CP_File> files=cp_fileRepository.findByDataid(dataid);

        Back<List<CP_File>> back = new Back<>();
        back.setTotalcount((int) listcpdatas.getTotalElements());
        back.setData(listcpdatas.getContent());
        back.setCmd("根据数据id查询出相应附件成功！");
        back.setState(1);

        return back;
    }

    //查询父级相关数据的接口
    @RequestMapping(value = "/clearunusefile", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "清楚无用文件接口！")
    @ApiOperation(value = "清楚无用文件接口！", notes = "清楚无用文件接口！")
    public Back clearunusefile() {
        Back<Integer> back = new Back<>();
        //首先需要加载cpclass里面的数据判断出是附件或者图片的字段，根据字段去拿到所有的文件地址信息，
        //然后根据地址去查询出附件表不在这些里面的数据，将数据和文件一并删除
        List<CP_Class> cp_classes = cpClassRepository.findAll();
        List<Map<String, Object>> maps = new LinkedList<>();
        Set<String> cpids = new HashSet<>();

        ListIterator<CP_Class> cp_classListIterator = cp_classes.listIterator();
        while (cp_classListIterator.hasNext()) {
            CP_Class cp_class = cp_classListIterator.next();
            Map<String, Object> atrrs = cp_class.getAtrrs();
            JSONObject jsonObject = new JSONObject(atrrs);
            Map<String, Object> map = new ConcurrentHashMap<>();
            List<String> zdnames = new LinkedList<>();
            boolean flag = false;
            map.put("cpid", cp_class.getId());
            for (String s : jsonObject.keySet()) {
                if (jsonObject.get(s) != null) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject(s);
                    if (jsonObject1.get("type") != null && ("2".equals(jsonObject1.getString("type"))
                            || "8".equals(jsonObject1.getString("type")))) {
                        //如果它的类型是图片或者附件的话，就需要拿到相应的数据进行附件数据检测
                        flag = true;
                        cpids.add(cp_class.getId());
                        zdnames.add(s + "," + jsonObject1.getString("type"));
                        map.put("value", zdnames);
                    }
                }
            }
            if (flag) {
                maps.add(map);
            }
        }

        //查询书cpclassdata表里面的数据
        List<String> cpidlist = new ArrayList<>(cpids);
        List<CP_Class_Data> cp_class_datas = cp_class_dataRepository.findByCpidIn(cpidlist);
        ListIterator<CP_Class_Data> cp_class_dataListIterator = cp_class_datas.listIterator();
        List<String> fileaddress = new LinkedList<>();
        while (cp_class_dataListIterator.hasNext()) {
            CP_Class_Data cpClassData = cp_class_dataListIterator.next();
            List<Map<String, Object>> fhtjsj = maps.stream().filter((item) ->
                    item.get("cpid").toString().equals(cpClassData.getCpid()))
                    .collect(Collectors.toList());
            if (fhtjsj.size() > 0) {
                Map<String, Object> fhmap = fhtjsj.get(0);
                Object value = fhmap.get("value");
                if (value != null) {
                    List<String> vars = (List<String>) value;
                    JSONObject cpdatamapjson = new JSONObject(cpClassData.getDatamap());
                    for (String var : vars) {
                        String[] split = var.split(",");
                        if ("2".equals(split[1])) {
                            if (cpdatamapjson.get(split[0]) != null) {
                                JSONArray jsonArray = cpdatamapjson.getJSONArray(split[0]);
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if (jsonObject.containsKey("fileaddress")) {
                                        fileaddress.add(jsonObject.getString("fileaddress"));
                                    }
                                }
                            }
                        } else {
                            if (cpdatamapjson.get(split[0]) != null) {
                                String string = cpdatamapjson.getString(split[0]);
                                fileaddress.add(string);
                            }
                        }
                    }
                }
            }
        }
        //从模板文件表里面取出相应的数据
        List<ModelFile> modelfileall = modelFileRespository.findAll();
        for (ModelFile modelFile : modelfileall) {
            if (modelFile.getFileaddress() != null) {
                fileaddress.add(modelFile.getFileaddress());
            }
        }
        List<IndiviEntity> allindi = indiviRepository.findAll();
        for (IndiviEntity indiviEntity : allindi) {
            if (indiviEntity.getFilepath() != null) {
                fileaddress.add(indiviEntity.getFilepath());
            }
        }
        //根据fileaddress查询出不在这个集合里面的数据
        List<CP_File> byFileaddressNotIn = cp_fileRepository.findByFileaddressNotIn(fileaddress);
        for (CP_File listcpdatas : byFileaddressNotIn) {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 1000,
                    5000, TimeUnit.SECONDS, new LinkedBlockingDeque<>(300));
            //先把本地的文件删除
            String filepath = globalValue.getFilepath() + listcpdatas.getFileaddress();
            File file = new File(filepath);
            if (file.exists()) {
//                threadPoolExecutor.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        file.delete();
//                    }
//                });
                threadPoolExecutor.execute(() -> {
                    file.delete();
                });
            }
            cp_fileRepository.delete(listcpdatas);
        }
        back.setData(1);
        back.setState(1);
        back.setCmd("清除无用数据以及文件结束");
        return back;
    }

}

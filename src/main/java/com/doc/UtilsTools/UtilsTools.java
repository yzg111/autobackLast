package com.doc.UtilsTools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import org.neo4j.cypher.internal.javacompat.MapRow;
import org.neo4j.graphdb.Node;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;


/**
 * com.doc.UtilsTools 于2019/1/7 由Administrator 创建 .
 */
public class UtilsTools {

//    ServletContext s1=this.getServletContext();
//    String temp=s1.getRealPath("/"); (关键)
//    结果形如：D:\工具\Tomcat-6.0\webapps\002_ext\ (002_ext为项目名字)
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
    //param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public static  Boolean isNode(MapRow row,String key){
        try{
            Node node=row.getNode(key);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    /**
     * @Method 查询出来的neo4j数据转成CP_Class_Data数据
    * * @param null
     * @Date 2019/2/20
     * * @description:a
    */
    public static List<CP_Class_Data> changeCPData(List<Map<String, Object>> datas){
        List<CP_Class_Data> listcpdata=new ArrayList<>();
        //转换数据
        for (Map<String,Object>data:datas){
            CP_Class_Data cpClassData=new CP_Class_Data();
            cpClassData.setId(data.get("id").toString());
            cpClassData.setCpid(data.get("cpid").toString());
            data.remove("id");
            data.remove("cpid");
            cpClassData.setDatamap(data);
            listcpdata.add(cpClassData);
        }
        return listcpdata;
    }

    /**
     * 求交集
     *
     * @param m
     * @param n
     * @return
     */
    public static <T> List<T> getJH(List<T> m, List<T> n)
    {
        List<T> rs = new ArrayList<>();

        // 将较长的数组转换为set
        List<T> set = m.size() > n.size() ? m : n;

        // 遍历较短的数组，实现最少循环
        for (T i : m.size() > n.size() ? n : m)
        {
            if (set.contains(i))
            {
                rs.add(i);
            }
        }
        return rs;
    }


    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static  String getContentType(String fileName){
        //文件名后缀
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if(".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if(".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ){
            return "image/jpeg";
        }
        if(".html".equalsIgnoreCase(fileExtension)){
            return "text/html";
        }
        if(".txt".equalsIgnoreCase(fileExtension)){
            return "text/plain";
        }
        if(".vsd".equalsIgnoreCase(fileExtension)){
            return "application/vnd.visio";
        }
        if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if(".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        if(".pdf".equalsIgnoreCase(fileExtension)) {
            return "application/pdf";
        }
        return "application/octet-stream";
    }

    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if(!targetFile.getParentFile().exists()){
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[BUFFER_SIZE];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("解压完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if(zipFile != null){
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getdatefilename(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        //获取当前天数
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //获取当前小时
        int time = cal.get(Calendar.HOUR_OF_DAY);
        //获取当前分钟
        int min = cal.get(Calendar.MINUTE);
        //获取当前秒
        int sec = cal.get(Calendar.SECOND);
        String monthstr="";
        if(month<10)
        {
            monthstr="0"+month;
        }else {
            monthstr=String .valueOf(month);
        }
        return year+""+monthstr+day+time+min+sec;
    }

    //组装查询条件
    public static String GetqueryString(JSONArray maps, boolean flag){
        String where="";
        for (int i = 0; i < maps.size(); i++) {
            JSONObject map = maps.getJSONObject(i);
            String condition = "=";//1是=，2是>，3是<，4是!=，5是模糊查询
            if ("1".equals(map.getString("condition"))) {
                condition = "=" + "'" + map.get("value") + "' ";
            } else if ("2".equals(map.getString("condition"))) {
                condition = ">" + "'" + map.get("value") + "' ";
            } else if ("3".equals(map.getString("condition"))) {
                condition = "<" + "'" + map.get("value") + "' ";
            } else if ("4".equals(map.getString("condition"))) {
                condition = "<>" + "'" + map.get("value") + "' ";
            } else if ("5".equals(map.getString("condition"))) {
                condition = " =~ '.*" + map.get("value") + ".*' ";
            }
            if (i == 0&&flag) {
                where = where + " n." + map.get("name") + condition;
            } else {
                where = where + " and n." + map.get("name") + condition;
            }
        }

        return where;
    }

}

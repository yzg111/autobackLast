package com.doc.UtilsTools;

import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import org.neo4j.cypher.internal.javacompat.MapRow;
import org.neo4j.graphdb.Node;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

/**
 * com.doc.UtilsTools 于2019/1/7 由Administrator 创建 .
 */
public class UtilsTools {
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
}

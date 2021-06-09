package com.doc.UtilsTools.Service;

import com.doc.Manager.MethodDec.Methoddec;
import com.doc.Manager.Param.Paramdec;

import java.util.List;
import java.util.Map;

/**
 * com.doc.UtilsTools.Service 于2021/6/4 由Administrator 创建 .
 */
public abstract class CpToolsSerivce<T> {

    /**
     * @Method a
     * * * @param cp类名称
     * @Date 2019/2/20
     * * @description:a
     */
    @Methoddec("根据cp类的名称得到cp类的数据")
    public abstract List<T> getCpData(@Paramdec("cp类名称") String cpname);

    /**
     * @Method a
     * * * @param cp类名称
     * @Date 2019/2/20
     * * @description:根据条件查询出相应cp的数据
     */
    @Methoddec("根据条件查询出相应cp的数据")
    public abstract List<T> getCpData(@Paramdec("cp类名称") String cpname, @Paramdec("查询条件") List<Map<String, Object>> options);


    /**
     * @Method * @param null
     * @Date 2019/2/21
     * * @description:创建新的cp类
     */
    @Methoddec("创建新的cp类对象")
    public abstract T newCp(@Paramdec("Cp类名称") String cpname);

    /**
     * @Method * @param null
     * @Date 2019/2/21
     * * @description:创建新的cp类
     */
    @Methoddec("根据数据id查询数据")
    public abstract T getCpDataById(@Paramdec("数据id") String id);

    /**
     * @Method * @param null
     * @Date 2019/2/21
     * * @description:创建新的cp类
     */
    @Methoddec("根据cp类名称创建出新的cp对象的字符串")
    public abstract String newCpStr(@Paramdec("cp类名称") String cpname);

    /**
     * @Method
     * @Date 2019/2/20
     * * @description:保存或者更新cp数据的函数
     */
    @Methoddec("保存或者更新cp数据的函数，并返回相应的对象")
    public abstract T SaveCp(@Paramdec("cp类对象的数据") T cpdata);

    /**
     * @Method
     * @Date 2019/2/20
     * * @description:保存或者更新cp数据的函数
     */
    @Methoddec("保存或者更新cp数据")
    public abstract void SaveCpVoid(@Paramdec("cp类对象的数据")T cpdata);

    /**
     * 功能描述:打印groovy日志
     *
     * @return :
     */
    @Methoddec("打印日志信息")
    public abstract void printLog(@Paramdec("日志信息") Object message);
}

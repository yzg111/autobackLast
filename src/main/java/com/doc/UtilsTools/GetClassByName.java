package com.doc.UtilsTools;

/**
 * Doc.config.Until 于2017/8/30 由Administrator 创建 .
 */
public  class GetClassByName {

    //通过包名和类名得到相应的类，跟类.class获取到的信息一样
    public static Class GetClass(String ClassName)
    {
        Class T=null;
        try {
            T= Class.forName(ClassName);
            System.out.println(T);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return T;
    }
}

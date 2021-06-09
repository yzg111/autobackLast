package com.doc.UtilsTools;

import com.doc.Manager.MethodDec.Methoddec;
import com.doc.Manager.Param.Paramdec;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * com.doc.UtilsTools 于2021/6/3 由Administrator 创建 .
 * 根据路径获取类信息和方法信息
 */
public class PackageUtil {

    private static ThreadLocal<ClassLoader> classLoaderThreadLocal = new ThreadLocal<>();

    /**
     * 获取某包下（包括该包的所有子包）所有类
     *
     * @param packageName 包名
     * @return 类的完整名称
     */
    public static List<String> getClassName(String packageName, boolean isjarpath) {
        return getClassName(packageName, true, isjarpath);
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName  包名
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    public static List<String> getClassName(String packageName, boolean childPackage, boolean isjarpath) {
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath;
        if (isjarpath) {
            packagePath = packageName;
        } else {
            packagePath = packageName.replace(".", "/");
        }
        URL url = loader.getResource(packagePath);
        classLoaderThreadLocal.set(loader);
        if (url != null) {
            String type = url.getProtocol();
            if (isjarpath) {
                fileNames = getClassNameByJar(url.getPath(), childPackage);
            } else {
                if (type.equals("file")) {
                    fileNames = getClassNameByFile(url.getPath(), null, childPackage);
                } else if (type.equals("jar")) {
                    fileNames = getClassNameByJar(url.getPath(), childPackage);
                }
            }

        } else {
            fileNames = getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage);
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath     文件路径
     * @param className    类名集合
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath, List<String> className, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                if (childPackage) {
                    myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage));
                }
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    if (childFilePath.contains("main")){
                        childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 14, childFilePath.lastIndexOf("."));
                    }else {
                        childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                    }
//                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
//                    childFilePath = childFilePath.substring(0, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }

        return myClassName;
    }

    /**
     * 根据路径获取下面全部的jar包信息
     */
    public static List<String> getJarnameByfilepath(String filePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        filePath=filePath.replace("\\","/");
        URL url = loader.getResource(filePath);
        File file = new File(url.getPath());
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                if (childPackage) {
                    myClassName.addAll(getJarnameByfilepath(
                            childFile.getPath().substring(childFile.getPath().indexOf("\\"+filePath) + 1),
                            childPackage));
                }
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".jar")) {
                    myClassName.add(childFilePath);
                }
            }
        }
        return myClassName;
    }


    /**
     * 从jar获取某包下所有类
     *
     * @param jarPath      jar文件路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(String jarPath, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        String[] jarInfo = jarPath.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1].substring(1);
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    if (childPackage) {
                        if (entryName.startsWith(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    } else {
                        int index = entryName.lastIndexOf("/");
                        String myPackagePath;
                        if (index != -1) {
                            myPackagePath = entryName.substring(0, index);
                        } else {
                            myPackagePath = entryName;
                        }
                        if (myPackagePath.equals(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myClassName;
    }


    /**
     * 根据jar包的路径获取jar包里面的方法和参数信息
     * */
    public static List<ConcurrentHashMap<String, String>> GetAllMthodsinfoByJarpath(List<String> jarpaths) {
        List<ConcurrentHashMap<String, String>> allmethodsinfo = new ArrayList<>();
        Set<String> sts=new HashSet<>();
        jarpaths.forEach(jarpath -> {
            try {

                //通过将给定路径名字符串转换为抽象路径名来创建一个新File实例
                File f = new File(jarpath);
                URL url1 = f.toURI().toURL();
                URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url1}, Thread.currentThread().getContextClassLoader());

                //通过jarFile和JarEntry得到所有的类
                JarFile jar = new JarFile(jarpath);
                //返回zip文件条目的枚举
                Enumeration<JarEntry> enumFiles = jar.entries();
                JarEntry entry;

                //测试此枚举是否包含更多的元素
                while (enumFiles.hasMoreElements()) {
                    entry = (JarEntry) enumFiles.nextElement();
                    if (entry.getName().indexOf("META-INF") < 0) {
                        String classFullName = entry.getName();
                        if (!classFullName.endsWith(".class")) {
                            classFullName = classFullName.substring(0, classFullName.length() - 1);
                        } else {
                            //去掉后缀.class
                            String className = classFullName.substring(0, classFullName.length() - 6).replace("/", ".");
                            Class<?> myclass = myClassLoader.loadClass(className);
                            //打印类名
//                            System.out.println("*****************************");
//                            System.out.println("全类名:" + className);

                            //得到类中包含的属性
                            Method[] methods = myclass.getMethods();
                            for (Method method : methods) {
                                ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                                String methodName = method.getName();
                                map.put("package",className);
                                map.put("methodname", methodName);
                                map.put("returnsimpletype", method.getReturnType().getSimpleName());
                                map.put("returntype", method.getReturnType().getName());
//                                System.out.println("方法名称:" + methodName);
//                                Class<?>[] parameterTypes = method.getParameterTypes();
//                                for (Class<?> clas : parameterTypes) {
//                                    // String parameterName = clas.getName();
//                                    String parameterName = clas.getSimpleName();
//                                    System.out.println("参数类型:" + parameterName);
//                                }
//                                System.out.println("==========================");

                                Parameter[] parameters = method.getParameters();
                                List<String> params = new ArrayList<>();
                                for (Parameter clas : parameters) {
                                    String parameterName = clas.getName();
                                    String parametertypeName = clas.getType().getSimpleName();
                                    params.add(parametertypeName + ":" + parameterName);
//                                    System.out.println("参数类型:" + parametertypeName+ ":" + parameterName);
                                }
                                map.put("paramsdec", "(" + StringUtils.join(params, ",") + ")");
                                if (!sts.contains(map.get("returntype")+map.get("methodname")+map.get("paramsdec"))){
                                    sts.add(map.get("returntype")+map.get("methodname")+map.get("paramsdec"));
                                    allmethodsinfo.add(map);
                                }

                            }

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


        return allmethodsinfo;
    }

    /**
     * 根据方法的名称获取里面的函数信息
     */
    public static List<String> GetMethods(List<String> classNames) {
        List<String> resmethods = new ArrayList<>();
        classNames.forEach((className) -> {
            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                String packagePath = className.replace(".", "/");
//                URL url = loader.getResource(packagePath);
//                URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
                Class<?> myclass = loader.loadClass(packagePath);
                //得到类中包含的属性
                Method[] methods = myclass.getMethods();
                for (Method method : methods) {
                    String methodName = method.getName();
                    resmethods.add(methodName);
                    System.out.println("方法名称:" + methodName);

                    System.out.println("==========================");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return resmethods;
    }

    /**
     * 根据方法的名称获取里面的所有函数信息以及参数信息
     */
    public static List<ConcurrentHashMap<String, String>> GetMethodsAllInfo(List<String> classNames) {
        List<ConcurrentHashMap<String, String>> allmethodsinfo = new ArrayList<>();
        Set<String> sts=new HashSet<>();
        classNames.forEach((className) -> {
            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
//                ClassLoader loader =classLoaderThreadLocal.get();
                String packagePath = className.replace(".", "/");
//                URL url = loader.getResource(packagePath);
//                URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
                Class<?> myclass = loader.loadClass(className);
                //得到类中包含的属性
                Method[] methods = myclass.getMethods();
                for (Method method : methods) {
                    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                    String methodName = method.getName();
                    Annotation[] methodAnnotations = method.getAnnotations();
                    String methoddec = "";
                    if (methodAnnotations.length > 0) {
                        Annotation meannotation = methodAnnotations[0];
                        if (meannotation instanceof Methoddec) {
                            Methoddec param = (Methoddec) meannotation;
                            methoddec = param.value();
                        }
                    }
//                    map.put("package",className);
                    map.put("methoddec", methoddec);
                    map.put("methodname", methodName);
                    map.put("returnsimpletype", method.getReturnType().getSimpleName());
                    map.put("returntype", method.getReturnType().getName());
//                    System.out.println("方法名称:" + methodName);
                    Parameter[] parameters = method.getParameters();
                    List<String> params = new ArrayList<>();
                    for (Parameter clas : parameters) {
                        String parameterName = clas.getName();
                        Annotation[] annotations = clas.getAnnotations();
                        String decrions = "";
                        if (annotations.length > 0) {
                            Annotation annotation = annotations[0];
                            if (annotation instanceof Paramdec) {
                                Paramdec param = (Paramdec) annotation;
                                decrions = param.value();
                            }
                        }
                        String parametertypeName = clas.getType().getSimpleName();
                        decrions = (!"".equals(decrions) && decrions != null) ? "(" + decrions + ")" : "";
                        params.add(parametertypeName + ":" + parameterName + decrions);
//                        System.out.println("参数类型:" + parametertypeName);
                    }
                    map.put("paramsdec", "(" + StringUtils.join(params, ",") + ")");
                    if (!sts.contains(map.get("returntype")+map.get("methodname")+map.get("paramsdec"))){
                        sts.add(map.get("returntype")+map.get("methodname")+map.get("paramsdec"));
                        allmethodsinfo.add(map);
                    }
//                    System.out.println("==========================");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return allmethodsinfo;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls         URL集合
     * @param packagePath  包路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                URL url = urls[i];
                String urlPath = url.getPath();
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                myClassName.addAll(getClassNameByJar(jarPath, childPackage));
            }
        }
        return myClassName;
    }


}

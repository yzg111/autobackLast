package com.doc.config.LogConfig.MyLog4JAppender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * com.doc.config.LogConfig.MyLog4JAppender 于2020/10/21 由Administrator 创建 .
 * * 将系统打印的日志信息转成log4j打印输出,可以捕获传输到前端
 */
public class Log4j2OutPrintStream extends PrintStream {
    private Logger log = LoggerFactory.getLogger("SystemOut");
    private static PrintStream instance = new Log4j2OutPrintStream(System.out);


    public Log4j2OutPrintStream(OutputStream out) {
        super(out);
    }


    public static void redirectSystemOut() {
        System.setOut(instance);
    }

    public void print(boolean b) {
        println(b);
    }

    public void print(char c) {
        println(c);
    }

    public void print(char[] s) {
        println(s);
    }

    public void print(double d) {
        println(d);
    }

    public void print(float f) {
        println(f);
    }

    public void print(int i) {
        println(i);
    }

    public void print(long l) {
        println(l);
    }

    public void print(Object obj) {
        println(obj);
    }

    public void print(String s) {
        println(s);
    }

    public void println(boolean x) {
        log.info(String.valueOf(x));
    }

    public void println(char x) {
        log.info(String.valueOf(x));
    }

    public void println(char[] x) {
        log.info(x == null ? null : new String(x));
    }

    public void println(double x) {
        log.info(String.valueOf(x));
    }

    public void println(float x) {
        log.info(String.valueOf(x));
    }

    public void println(int x) {
        log.info(String.valueOf(x));
    }

    public void println(long x) {
        log.info(String.valueOf(x));
    }

    public void println(Object x) {
        log.info(String.valueOf(x));
    }

    public void println(String x) {
//        log.info("out进来没有");
        log.info(x);
    }
}

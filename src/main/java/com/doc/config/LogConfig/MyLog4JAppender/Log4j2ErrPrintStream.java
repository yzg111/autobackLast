package com.doc.config.LogConfig.MyLog4JAppender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.*;

/**
 * com.doc.config.LogConfig.MyLog4JAppender 于2020/10/21 由Administrator 创建 .
 * 将系统打印的错误信息转成log4j打印输出,可以捕获传输到前端
 */
public class Log4j2ErrPrintStream extends PrintStream {
    private Logger log = LoggerFactory.getLogger("SystemErr");
    private static PrintStream instance = new Log4j2ErrPrintStream(System.err);

    public Log4j2ErrPrintStream(OutputStream out) {
        super(out);
    }
    public static void redirectSystemErr() {
        System.setErr(instance);
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
        log.error(String.valueOf(x));
    }

    public void println(char x) {
        log.error(String.valueOf(x));
    }

    public void println(char[] x) {
        log.error(x == null ? null : new String(x));
    }

    public void println(double x) {
        log.error(String.valueOf(x));
    }

    public void println(float x) {
        log.error(String.valueOf(x));
    }

    public void println(int x) {
        log.error(String.valueOf(x));
    }

    public void println(long x) {
        log.error(String.valueOf(x));
    }

    public void println(Object x) {
        log.error(String.valueOf(x));
    }

    public void println(String x) {
//        log.error("进来没有");
        log.error(x);
    }

}

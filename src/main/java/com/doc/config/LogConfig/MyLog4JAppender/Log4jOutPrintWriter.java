package com.doc.config.LogConfig.MyLog4JAppender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * com.doc.config.LogConfig.MyLog4JAppender 于2020/10/22 由Administrator 创建 .
 */
public class Log4jOutPrintWriter extends PrintWriter {
    private Logger log = LoggerFactory.getLogger("Printwriter");
    private static PrintStream instance = new Log4j2OutPrintStream(System.out);
    private PrintStream psOut = null;
    public Log4jOutPrintWriter(OutputStream out) {
        super(out);
    }


    public static void redirectPrintOut() {
        System.setOut(instance);
    }

    @Override
    public void write(int c) {
        print(c);
    }

    @Override
    public void write(char[] buf, int off, int len) {
        print(buf);
    }

    @Override
    public void write(char[] buf) {
        print(buf);
    }

    @Override
    public void write(String s, int off, int len) {
        print(s);
    }

    @Override
    public void write(String s) {
        print(s);
    }

    @Override
    public void println() {
        println("");
    }

    @Override
    public void print(boolean b) {
        println(b);
    }

    @Override
    public void print(char c) {
        println(c);
    }

    @Override
    public void print(int i) {
        println(i);
    }

    @Override
    public void print(long l) {
        println(l);
    }

    @Override
    public void print(float f) {
        println(f);
    }

    @Override
    public void print(double d) {
        println(d);
    }

    @Override
    public void print(char[] s) {
        println(s);
    }

    @Override
    public void print(String s) {
        println(s);
    }

    @Override
    public void print(Object obj) {
        println(obj);
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
        log.info(x);
    }
}

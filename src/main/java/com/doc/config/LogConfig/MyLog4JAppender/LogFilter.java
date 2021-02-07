package com.doc.config.LogConfig.MyLog4JAppender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.doc.Entity.ConsoleLogInfoEntity.ConsoleLogInfo;
import com.doc.WebSocket.WebSocketController;
import com.doc.config.JobListener.JobListener;
import com.doc.config.Until.SpringContextUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.doc.config.LogConfig.MyLog4JAppender 于2020/5/8 由Administrator 创建 .
 */
//@Component
public class LogFilter extends Filter<ILoggingEvent> {
    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void start() {
        logger.info("日志过滤启动");
        super.start();
    }
//    @Autowired
//    private WebSocketController webSocketController;

    @Override
    public FilterReply decide(ILoggingEvent event) {
//        logger.info("拦截到日志信息");
        IThrowableProxy iThrowableProxy1 = event.getThrowableProxy();
        WebSocketController webSocketController= (WebSocketController) SpringContextUtil.getBean("WebSocketController");
//        System.out.println(webSocketController);
//        event.getLevel().levelStr
//        logger.info("log info");
        ConsoleLogInfo consoleLogInfo=new ConsoleLogInfo();
        consoleLogInfo.setLevel(event.getLevel().levelStr);
        consoleLogInfo.setMessage(event.getMessage());
//        logger.info("log info:"+event.getLevel().levelStr+"|"+event.getMessage());
//        webSocketController.sendMessage(consoleLogInfo.toString());
//        webSocketController.sendMessage(event.getLevel().levelStr);
        webSocketController.sendMessage(event.getLevel().levelStr+"|"+event.getMessage());
        return FilterReply.ACCEPT;
    }
}

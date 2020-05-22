package com.doc.config.LogConfig.MyLog4JAppender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.doc.Entity.ConsoleLogInfoEntity.ConsoleLogInfo;
import com.doc.WebSocket.WebSocketController;
import com.doc.config.Until.SpringContextUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.doc.config.LogConfig.MyLog4JAppender 于2020/5/8 由Administrator 创建 .
 */
public class LogFilter extends Filter<ILoggingEvent> {

//    @Autowired
//    private WebSocketController webSocketController;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        IThrowableProxy iThrowableProxy1 = event.getThrowableProxy();
        WebSocketController webSocketController= (WebSocketController) SpringContextUtil.getBean("WebSocketController");
//        System.out.println(webSocketController);
//        event.getLevel().levelStr
        ConsoleLogInfo consoleLogInfo=new ConsoleLogInfo();
        consoleLogInfo.setLevel(event.getLevel().levelStr);
        consoleLogInfo.setMessage(event.getMessage());

//        webSocketController.sendMessage(consoleLogInfo.toString());
//        webSocketController.sendMessage(event.getLevel().levelStr);
        webSocketController.sendMessage(event.getLevel().levelStr+"@"+event.getMessage());
        return FilterReply.ACCEPT;
    }
}

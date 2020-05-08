package com.doc.config.LogConfig.MyLog4JAppender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.rolling.RollingFileAppender;
import com.doc.WebSocket.WebSocketController;
import com.doc.config.Until.SpringContextUtil;
import com.doc.controller.PageOrignal.PageorignalController;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import jline.internal.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * com.doc.config.LogConfig.MyLog4JAppender 于2020/5/7 由Administrator 创建 .
 */
public class MyLog4JAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private static final Logger logger = LoggerFactory.getLogger(MyLog4JAppender.class);


    public Layout<ILoggingEvent> getLayout() {
        return layout;
    }

    public void setLayout(Layout<ILoggingEvent> layout) {
        this.layout = layout;
    }

    // 输出格式，用系统的，不然你输出的东西 不会解析
    Layout<ILoggingEvent> layout;

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (eventObject != null && eventObject.getFormattedMessage() != null) {
            String message = layout.doLayout(eventObject);
            try {
                // 自定义的socket发送消息
//                logger.info("发送消息:"+message);
                WebSocketController webSocketController= (WebSocketController) SpringContextUtil.getBean("WebSocketController");
                webSocketController.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

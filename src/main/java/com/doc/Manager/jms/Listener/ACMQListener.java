package com.doc.Manager.jms.Listener;


import com.doc.Manager.jms.ACMQReceiveMsgHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Doc.manager.Jms.Listener 于2017/8/23 由Administrator 创建 .
 */
public class ACMQListener implements MessageListener {
    //日志信息
    private Logger logger = LoggerFactory.getLogger(ACMQListener.class);

    private ACMQReceiveMsgHandler msgHandler;

    public ACMQListener(ACMQReceiveMsgHandler MsgHandler)
    {
        this.msgHandler=MsgHandler;
    }
    public void onMessage(Message cmdMsg) {
        try {
            //函数自己处理的操作
//            aCMQReceiveMsgHandler.processMsg(((TextMessage)cmdMsg).getText());
            if (cmdMsg instanceof TextMessage) {
                TextMessage tm = (TextMessage) cmdMsg;
                logger.info("收到的消息：" +
                        ((TextMessage)cmdMsg).getText());
                try {
                    String msgStr = tm.getText();
                    logger.debug("Receive a message: {}", msgStr);
                    // 交由具体的处理器来处理。
                    msgHandler.processMsg(msgStr);
                } catch (Exception e) {
                    logger.warn("Ignored, process message failed ", e);
                }
                return;
            } else if (cmdMsg instanceof BytesMessage) {
                BytesMessage tm = (BytesMessage) cmdMsg;
                try {
                    long length =  tm.getBodyLength();
                    //做保护，如果超过1m则抛弃。
                    if(length > 1024*1024){
                        logger.warn("Message length ({}) exceed max size 1024KB.",length);
                        return;
                    }
                    byte[] content = new byte[(int)length];
                    int rv  = tm.readBytes(content);
                    if(rv == -1){
                        logger.warn("Message not correct read.");
                        return ;
                    }
                    String msgStr = new String(content,"utf-8");

                    logger.debug("Receive a message: {}", msgStr );
                    // 交由具体的处理器来处理。
                    msgHandler.processMsg(msgStr);
                } catch (Exception e) {
                    logger.warn("Ignored, process message failed ", e);
                }
                return;
            } else {
                logger.warn("Invalid message,Ignored, the message is: {}" + cmdMsg);
            }

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }
}

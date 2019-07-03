package com.doc.Manager.jms;

/**
 * Doc.manager.Jms 于2017/8/23 由Administrator 创建 .
 */
public interface ACMQReceiveMsgHandler {
    /**
     * 处理接收到的消息。
     */
    public void processMsg(String message) throws Exception;
}

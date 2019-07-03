package com.doc.Manager.jms;

import com.doc.Manager.jms.Listener.ACMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.jms.*;

/**
 * 接收者接收信息类
 * Doc.manager.Jms 于2017/8/23 由Administrator 创建 .
 */
public class ACMQTopicReceiver {
    //日志信息
    private Logger logger = LoggerFactory.getLogger(ACMQTopicReceiver.class);

    /**
     * 队列名。
     */
    private String            queueName;

    //加载连接工厂
    @Qualifier("ACMQConnectionFactory")
    private ConnectionFactory queueConnFactory;

    private Connection queueConn;

    private Session session;

    private Destination queue;
    //消息接收器
    private MessageConsumer receiver;

    private ACMQReceiveMsgHandler    msgHandler;
    //链接其他消息服务器的时候初始化
    public ACMQTopicReceiver(QueueConnectionFactory queueConnFactory, String queueName) {
        this.queueConnFactory = queueConnFactory;
        this.queueName = queueName;
    }
    //链接默认服务器的时候初始化
    public ACMQTopicReceiver(String queueName) {
        this.queueName = queueName;
    }

    public void init(ACMQReceiveMsgHandler msgHandler) throws Exception {
        this.msgHandler = msgHandler;
        this.connect2MQ();
    }

    private void connect2MQ() throws Exception {

        if (queueName == null) {
            throw new Exception("No queue name is defined.");
        }
        logger.debug("Connect to queue: {}", queueName);

        queueConn = queueConnFactory.createConnection();//通过连接工厂获取连接
        queueConn.start();//启动连接
        session = queueConn.createSession(false, Session.AUTO_ACKNOWLEDGE);//创建session回话
        queue = session.createQueue(queueName);//创建消息队列
        try {
            receiver = session.createConsumer(queue);//创建接收者
            receiver.setMessageListener(new ACMQListener(msgHandler));//监听接收消息
        } catch (Exception e) {
            logger.warn("Create queue receiver failed: " + e.getMessage());
            throw e;
        }
        // Listening on port
        logger.info("a receiver ready for receiving message from {} ...", this.queueName);
    }

    public void destory() {
        try {
            this.receiver.close();//关闭发送者
        } catch (Exception ex) {
            logger.info("closed consumer exception :" + ex.getMessage(), ex);
        }
        try {
            this.session.close();//关闭会话
        } catch (Exception ex) {
            logger.info("closed session exception :" + ex.getMessage(), ex);
        }
        try {
            this.queueConn.close();//关闭链接
        } catch (Exception ex) {
            logger.info("closed connection exception :" + ex.getMessage(), ex);
        }
    }

    public String toString() {
        return this.queueName;
    }

}

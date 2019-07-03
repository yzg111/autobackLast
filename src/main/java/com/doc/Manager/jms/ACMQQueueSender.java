package com.doc.Manager.jms;

import com.doc.Entity.Notification.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.jms.*;
import java.io.IOException;

/**
 * 发送消息者
 * Doc.manager.Jms 于2017/8/23 由Administrator 创建 .
 */
public class ACMQQueueSender {
    //日志信息
    private Logger logger = LoggerFactory.getLogger(ACMQQueueSender.class);

    /**
     * 队列名。
     */
    private String            queueName;

    //加载连接工厂
    @Qualifier("ACMQConnectionFactory")
    private ConnectionFactory queueConnFactory;//创建工厂

    private Connection queueConn;//连接

    private Session queueSession;//会话 接收或者发送的消息的线程

    private Queue queue;//消息的目的地

    /**
     * 消息发送器。
     */
    private MessageProducer producer;//消息的发送者

    private ObjectMapper om = new ObjectMapper();

    //初始化链接外部服务器的消息发送队列
    public ACMQQueueSender(ConnectionFactory ConnFactory, String queueName) {
        this.queueConnFactory=ConnFactory;
        this.queueName = queueName;
    }

    //初始化链接默认的消息服务器队列
    public ACMQQueueSender(String queueName) {
        this.queueName = queueName;
    }

    public void init() throws Exception {
        this.connect2MQ();
    }

    private void connect2MQ() throws Exception {

        if (queueName == null) {
            throw new Exception("No queue name is defined.");
        }

        queueConn = queueConnFactory.createConnection();//通过连接工厂获取连接
        queueConn.start();//启动连接
        // 5.create queue session object
        queueSession = queueConn.createSession(true, Session.AUTO_ACKNOWLEDGE);//创建session回话
        queue = queueSession.createQueue(queueName);//创建消息队列
        // 6.create a queue receiver from queue session
        try {
            producer = queueSession.createProducer(queue);//创建消息发送者
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);//消息持久化，根据实际情况设定
        } catch (Exception e) {
            logger.warn("Create queue receiver failed: ", e.getMessage());
            throw e;
        }
        // Listening on port
        logger.info("Connect to queue: {} completely.", this.queueName);
    }

    /**
     * 发送结果到指定队列。
     *
     * @param data 消息体。
     * @return 发送成功返回true;否则返回false.
     */
    public boolean send(Notification data) {
        logger.debug("Send to queue: {}", queueName);
        Message msg = null;
        try {
            String msgStr =  om.writeValueAsString(data);
            logger.info("Message: {}",msgStr);
            msg = this.queueSession.createTextMessage(msgStr);
            producer.send(msg);//发送消息
            queueSession.commit();//提交会话
            return true;
        } catch (JMSException e) {
            logger.error(String.format("Send to JMS queue: %s failed.", queueName), e);
            return false;
        } catch (IOException ex) {
            logger.error(String.format("Cann't convert data: %s", data), ex);
            return false;
        }
    }

    /**
     * 发送字符串消息到指定队列上。
     *
     * @param message 消息字符串。
     * @return 发送成功返回true，否则返回false.
     */
    public boolean send(String message){
        logger.debug("Send to queue: {}", queueName);
        Message msg = null;
        try {
            msg = this.queueSession.createTextMessage(message);
            producer.send(msg);//发送消息
            queueSession.commit();//提交会话
            return true;
        } catch (JMSException e) {
            logger.error(String.format("Send to JMS queue: %s failed.", queueName), e);
            return false;
        }
    }

    public void destory() {
        try {
            this.producer.close();//关闭发送者
        } catch (Exception ex) {
            logger.warn("closed producer exception :" + ex.getMessage(), ex);
        }
        try {

            this.queueSession.close();//关闭会话
        } catch (Exception ex) {
            logger.warn("closed session exception :" + ex.getMessage(), ex);
        }
        try {
            this.queueConn.close();//关闭链接
        } catch (Exception ex) {
            logger.warn("closed connection exception :" + ex.getMessage(), ex);
        }

    }

    public String toString() {
        return this.queueName;
    }
}

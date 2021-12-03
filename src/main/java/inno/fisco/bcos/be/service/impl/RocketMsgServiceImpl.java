package inno.fisco.bcos.be.service.impl;

import inno.fisco.bcos.be.service.RocketMsgService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: RocketMQ 发送消息类
 * @Author zack
 * @Date 2021/9/16
 */
@Service(value = "rocketMsgService")
public class RocketMsgServiceImpl implements RocketMsgService {

    private static Logger log = LoggerFactory.getLogger(RocketMsgServiceImpl.class);

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Override
    public void simpleSendMsg(String topic, String tag, String msg) {
        try {
            Message message = new Message(topic, tag, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 调用客户端发送消息
            SendResult sendResult  = defaultMQProducer.send(message);
            log.info("sendResult: {}.",sendResult);
        } catch (Exception e) {
            log.error("send msg error: {}", e.getMessage());
        }
    }
}

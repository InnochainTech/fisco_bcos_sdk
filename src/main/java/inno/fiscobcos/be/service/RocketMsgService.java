package inno.fiscobcos.be.service;

/**
 * @Description: RocketMQ 消息接口
 * @Author zack
 * @Date 2021/9/16
 */
public interface RocketMsgService {

    void simpleSendMsg(String topic, String tag, String msg);
}

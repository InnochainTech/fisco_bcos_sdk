package inno.fiscobcos.be.configuration;

import inno.fiscobcos.be.constant.Config;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: RocketMQ 生产者配置类
 * @Author zack
 * @Date 2021/9/16
 */
@Configuration
public class MQProducerConfiguration {

    public static final Logger log = LoggerFactory.getLogger(MQProducerConfiguration.class);

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize;

    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer sendMsgTimeout;

    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryTimesWhenSendFailed;

    @Autowired
    private Config config;

    @Bean
    @ConditionalOnMissingBean
    public DefaultMQProducer defaultMQProducer() throws RuntimeException {
        DefaultMQProducer producer = new DefaultMQProducer(this.groupName);
        if(config.rocketmqWork) {
            producer.setNamesrvAddr(this.namesrvAddr);
            producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
            //如果需要同一个 jvm 中不同的 producer 往不同的 mq 集群发送消息，需要设置不同的 instanceName
            //producer.setInstanceName(instanceName);
            //如果发送消息的最大限制
            producer.setMaxMessageSize(this.maxMessageSize);
            //如果发送消息超时时间
            producer.setSendMsgTimeout(this.sendMsgTimeout);
            //如果发送消息失败，设置重试次数，默认为 2 次
            producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);
            try {
                producer.start();
                log.info("producer is started. groupName:{}, namesrvAddr: {}", groupName, namesrvAddr);
            } catch (MQClientException e) {
                log.error("failed to start producer.", e);
                throw new RuntimeException(e);
            }
        }
        return producer;
    }
}

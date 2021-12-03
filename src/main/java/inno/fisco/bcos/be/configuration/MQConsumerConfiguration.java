package inno.fisco.bcos.be.configuration;

import inno.fisco.bcos.be.constant.Config;
import inno.fisco.bcos.be.constant.MQConstant;
import inno.fisco.bcos.be.listener.*;
import io.netty.channel.DefaultChannelId;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: RocketMQ 消费者配置类
 * @Author zack
 * @Date 2021/9/16
 */
@Configuration
public class MQConsumerConfiguration {

    public static final Logger log = LoggerFactory.getLogger(MQConsumerConfiguration.class);

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;

    @Autowired
    private Config config;

    // nft
    @Autowired
    private ReqDeployListener reqDeployListener;

    @Autowired
    private ReqMintListener reqMintListener;

    @Autowired
    private ReqSellListener reqSellListener;

    @Autowired
    private ReqRenewListener reqRenewListener;

    @Autowired
    private ReqWriteoffListener reqWriteoffListener;

    @Autowired
    private ReqTransferListener reqTransferListener;

    @Autowired
    private ReqBurnListener reqBurnListener;


    @Bean
    public DefaultMQPushConsumer deployRequestConsumer() throws RuntimeException {
        return initDefaultMQPushConsumer(reqDeployListener, MQConstant.BCOS_REQ_NFT_DEPLOY_TAG);
    }

    @Bean
    public DefaultMQPushConsumer mintRequestConsumer() throws RuntimeException {
        return initDefaultMQPushConsumer(reqMintListener, MQConstant.BCOS_REQ_NFT_MINT_TAG);
    }

    @Bean
    public DefaultMQPushConsumer sellRequestConsumer() throws RuntimeException {
        return initDefaultMQPushConsumer(reqSellListener, MQConstant.BCOS_REQ_NFT_SELL_TAG);
    }

    @Bean
    public DefaultMQPushConsumer renewRequestConsumer() throws RuntimeException {
        return initDefaultMQPushConsumer(reqRenewListener, MQConstant.BCOS_REQ_NFT_RENEW_TAG);
    }

    @Bean
    public DefaultMQPushConsumer writeoffRequestConsumer() throws RuntimeException {
        return initDefaultMQPushConsumer(reqWriteoffListener, MQConstant.BCOS_REQ_NFT_WRITEOFF_TAG);
    }

    @Bean
    public DefaultMQPushConsumer transferRequestConsumer() throws RuntimeException {
        return initDefaultMQPushConsumer(reqTransferListener, MQConstant.BCOS_REQ_NFT_TRANSFER_TAG);
    }

    @Bean
    public DefaultMQPushConsumer burnRequestConsumer() throws RuntimeException {
        return initDefaultMQPushConsumer(reqBurnListener, MQConstant.BCOS_REQ_NFT_BURN_TAG);
    }




    private DefaultMQPushConsumer initDefaultMQPushConsumer(MessageListenerConcurrently listener, String tag) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(tag);
        if(config.rocketmqWork){
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setConsumeThreadMin(consumeThreadMin);
            consumer.setConsumeThreadMax(consumeThreadMax);

            // 设置 consumer 第一次启动是从队列头部开始消费还是队列尾部开始消费
            // 如果非第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            // 设置消费模型，集群还是广播，默认为集群
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.setConsumeTimeout(30000);
            // 设置一次消费消息的条数，默认为 1 条
            consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
            consumer.registerMessageListener(listener);
            try {
                // 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，使用*；
                consumer.subscribe(MQConstant.CHAIN_REQUEST_TOPIC, tag);
                DefaultChannelId.newInstance();
                // 启动消费
                consumer.start();
                log.info("consumer is started. groupName:{}, topics:{}, namesrvAddr:{}",tag,MQConstant.CHAIN_REQUEST_TOPIC,namesrvAddr);

            } catch (Exception e) {
                log.error("failed to start consumer . groupName:{}, topics:{}, namesrvAddr:{}",tag,MQConstant.CHAIN_REQUEST_TOPIC,namesrvAddr,e);
                throw new RuntimeException(e);
            }
        }

        return consumer;
    }
}

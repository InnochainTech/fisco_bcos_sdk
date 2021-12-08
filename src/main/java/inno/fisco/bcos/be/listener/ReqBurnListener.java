package inno.fisco.bcos.be.listener;

import com.alibaba.fastjson.JSONObject;
import inno.fisco.bcos.be.constant.Constant;
import inno.fisco.bcos.be.constant.MQConstant;
import inno.fisco.bcos.be.entity.response.BatchBurnVo;
import inno.fisco.bcos.be.entity.usesign.ReqVo;
import inno.fisco.bcos.be.entity.usesign.request.BatchBurnReq;
import inno.fisco.bcos.be.service.MQNFTService;
import inno.fisco.bcos.be.service.RocketMsgService;
import inno.fisco.bcos.be.util.result.Result;
import inno.fisco.bcos.be.util.validate.ErrorInfos;
import inno.fisco.bcos.be.util.validate.ValidateUtils;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.transaction.model.exception.NoSuchTransactionFileException;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/30 10:40 上午
 */
@Component
public class ReqBurnListener implements MessageListenerConcurrently {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReqBurnListener.class);

    @Autowired
    private RocketMsgService rocketMsgService;

    @Autowired
    private MQNFTService mqnftService;

    @SneakyThrows
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (CollectionUtils.isEmpty(list)) {
            LOGGER.info("receive blank msgs...");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        String msg = new String(messageExt.getBody());
        if (messageExt.getTopic().equals(MQConstant.CHAIN_REQUEST_TOPIC)) {
            // mock 消费逻辑
            mockConsume(msg);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private void mockConsume(String msg) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        LOGGER.info("ReqBurnListener receive msg: {}.", msg);
        Result<BatchBurnVo> result = new Result();

        ReqVo reqVo = JSONObject.parseObject(msg, ReqVo.class);
        BatchBurnReq batchBurnReq = JSONObject.parseObject(reqVo.getFuncParam().toString(),BatchBurnReq.class);
        reqVo.setFuncParam(batchBurnReq);
        ErrorInfos errorInfos = ValidateUtils.validate(reqVo);
        if (errorInfos.getErrors().size() != 0) {
            result = new Result<>().error(Constant.ERROR_CODE, "参数校验失败:", errorInfos);
        } else {
            result = mqnftService.batchBurn(reqVo);
        }
        LOGGER.info("ReqBurnListener mockConsume success result:{}.", result.toString());
        String resultStr = JSONObject.toJSONString(result);
        rocketMsgService.simpleSendMsg(MQConstant.CHAIN_RESPONSE_TOPIC, MQConstant.BCOS_RES_NFT_BURN_TAG, resultStr);
    }
}

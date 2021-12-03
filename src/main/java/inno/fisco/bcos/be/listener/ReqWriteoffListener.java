package inno.fisco.bcos.be.listener;

import inno.fisco.bcos.be.constant.Config;
import inno.fisco.bcos.be.constant.MQConstant;
import inno.fisco.bcos.be.service.RocketMsgService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/30 10:40 上午
 */
@Component
public class ReqWriteoffListener implements MessageListenerConcurrently {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReqWriteoffListener.class);

    @Autowired
    private RocketMsgService rocketMsgService;

    @Autowired
    private Config config;

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

    private void mockConsume(String msg){
        LOGGER.info("ReqDeployListener receive msg: {}.", msg);
//        Result<ResponseDto> result = new Result<>();
//        try{
//            String dncodeMsg = EncrypeUtils.AESDncode(config.key,msg);
//            LOGGER.info("ERC20BatchTransferRequestListener receive dncodeMsg: {}.", dncodeMsg);
//            BatchTransferVo batchTransferVo = JSONObject.parseObject(dncodeMsg,BatchTransferVo.class);
//            ErrorInfos errorInfos = ValidateUtils.validate(batchTransferVo);
//            if(errorInfos.getErrors().size() != 0){
//                result = ResultUtils.error(0,"参数校验失败",batchTransferVo.getOrderId(),errorInfos);
//            }else{
//                result = erc20Service.batchTransfer(batchTransferVo.getOrderId(), batchTransferVo.getPrivateKey(),  batchTransferVo.getAccountList(), batchTransferVo.getQuantity()+"");
//            }
//            LOGGER.info("ERC20BatchTransfer mockConsume success result:{}.",result.toString());
//        }catch (JSONException jsonException){
//            result = ResultUtils.error(0,"json数据转换失败，原数据为:"+msg);
//            LOGGER.error("ERC20BatchTransfer mockConsume error msg:{},result:{}.",jsonException.getMessage(),result.toString());
//        } catch (Exception exception) {
//            result = ResultUtils.error(0,"消息解密失败，原数据为:"+msg);
//            LOGGER.error("ERC20BatchTransfer mockConsume error msg:{},result:{}.",exception.getMessage(),result.toString());
//        }
        String resultStr = null;
//        try{
//            resultStr = EncrypeUtils.AESEncode(config.key,JSONObject.toJSONString(result));
//        }catch (Exception exception){
//            LOGGER.error("ERC20BatchTransfer AESEncode error msg:{},result:{}.",exception.getMessage(),result.toString());
//        }
        rocketMsgService.simpleSendMsg(MQConstant.CHAIN_RESPONSE_TOPIC, MQConstant.BCOS_RES_NFT_WRITEOFF_TAG, resultStr);
    }
}

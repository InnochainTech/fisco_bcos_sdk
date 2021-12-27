package inno.fisco.bcos.be.controller;

import com.alibaba.fastjson.JSONObject;
import inno.fisco.bcos.be.constant.MQConstant;
import inno.fisco.bcos.be.entity.usesign.ReqVo;
import inno.fisco.bcos.be.entity.usesign.request.*;
import inno.fisco.bcos.be.service.RocketMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


/**
 * @author peifeng
 * @date 2021/11/10 17:23
 */
@Api(tags = "测试NFT合约(MQ)")
@Controller
@Validated
@RequestMapping(value = "/mq-nft-test")
public class MQTestNFTController {

    @Autowired
    private RocketMsgService rocketMsgService;

    @ApiOperation(value = "部署合约")
    @ResponseBody
    @PostMapping("/deploy")
    public void deploy(@Validated @RequestBody ReqVo<NFTDeploy> reqNFTDeploy){
        rocketMsgService.simpleSendMsg(MQConstant.CHAIN_REQUEST_TOPIC, MQConstant.BCOS_REQ_NFT_DEPLOY_TAG, JSONObject.toJSONString(reqNFTDeploy));
    }


    @ApiOperation(value = "批量铸造")
    @ResponseBody
    @PostMapping("/batchMint")
    public void batchMint(@Validated @RequestBody ReqVo<BatchMintReq> reqBatchMint){
        rocketMsgService.simpleSendMsg(MQConstant.CHAIN_REQUEST_TOPIC, MQConstant.BCOS_REQ_NFT_MINT_TAG, JSONObject.toJSONString(reqBatchMint));
    }


    @ApiOperation(value = "批量出售")
    @ResponseBody
    @PostMapping("/batchSell")
    public void batchSell(@Validated @RequestBody ReqVo<BatchSellReq> batchSellReq){
        rocketMsgService.simpleSendMsg(MQConstant.CHAIN_REQUEST_TOPIC, MQConstant.BCOS_REQ_NFT_SELL_TAG, JSONObject.toJSONString(batchSellReq));
    }

    @ApiOperation(value = "批量转账")
    @ResponseBody
    @PostMapping("/batchTransfer")
    public void batchTransfer(@Validated @RequestBody ReqVo<BatchTransferReq> batchTransferReq){
        rocketMsgService.simpleSendMsg(MQConstant.CHAIN_REQUEST_TOPIC, MQConstant.BCOS_REQ_NFT_TRANSFER_TAG, JSONObject.toJSONString(batchTransferReq));
    }

    @ApiOperation(value = "续费")
    @ResponseBody
    @PostMapping("/renew")
    public void renew(@Validated @RequestBody ReqVo<RenewReq> renewReq){
        rocketMsgService.simpleSendMsg(MQConstant.CHAIN_REQUEST_TOPIC, MQConstant.BCOS_REQ_NFT_RENEW_TAG, JSONObject.toJSONString(renewReq));
    }

    @ApiOperation(value = "核销")
    @ResponseBody
    @PostMapping("/writeOff")
    public void writeOff(@RequestBody @Valid ReqVo<WriteOffReq> writeOffReq){
        rocketMsgService.simpleSendMsg(MQConstant.CHAIN_REQUEST_TOPIC, MQConstant.BCOS_REQ_NFT_WRITEOFF_TAG, JSONObject.toJSONString(writeOffReq));
    }


    @ApiOperation(value = "批量销毁")
    @ResponseBody
    @PostMapping("/batchBurn")
    public void batchBurn(@Validated @RequestBody ReqVo<BatchBurnReq> batchBurnReq) {
        rocketMsgService.simpleSendMsg(MQConstant.CHAIN_REQUEST_TOPIC, MQConstant.BCOS_REQ_NFT_BURN_TAG, JSONObject.toJSONString(batchBurnReq));
    }


}

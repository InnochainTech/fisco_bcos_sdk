package inno.fisco.bcos.be.controller;

import inno.fisco.bcos.be.entity.response.*;
import inno.fisco.bcos.be.entity.usesign.ReqVo;
import inno.fisco.bcos.be.entity.usesign.request.*;
import inno.fisco.bcos.be.service.MQNFTService;
import inno.fisco.bcos.be.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.transaction.model.exception.NoSuchTransactionFileException;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;


/**
 * @author peifeng
 * @date 2021/11/10 17:23
 */
@Api(tags = "NFT合约(MQ)")
@Controller
@Validated
@RequestMapping(value = "/mq-nft")
public class MQNFTController {



    @Autowired
    private MQNFTService mqnftService;

    @ApiOperation(value = "部署合约")
    @ResponseBody
    @PostMapping("/deploy")
    public Result<NFTDeployVo> deploy(@Validated @RequestBody ReqVo<NFTDeploy> reqNFTDeploy) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        return mqnftService.deploy(reqNFTDeploy);
    }


    @ApiOperation(value = "批量铸造")
    @ResponseBody
    @PostMapping("/batchMint")
    public Result<BatchMintVo> batchMint(@Validated @RequestBody ReqVo<BatchMintReq> reqBatchMint) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        return mqnftService.batchMint(reqBatchMint);
    }


    @ApiOperation(value = "批量出售")
    @ResponseBody
    @PostMapping("/batchSell")
    public Result<BatchSellVo> batchSell(@Validated @RequestBody ReqVo<BatchSellReq> batchSellReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        return mqnftService.batchSell(batchSellReq);
    }

    @ApiOperation(value = "批量转账")
    @ResponseBody
    @PostMapping("/batchTransfer")
    public Result<BatchTransferVo> batchTransfer(@Validated @RequestBody ReqVo<BatchTransferReq> batchTransferReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        return mqnftService.batchTransfer(batchTransferReq);
    }

    @ApiOperation(value = "续费")
    @ResponseBody
    @PostMapping("/renew")
    public Result<RenewVo> renew(@Validated @RequestBody ReqVo<RenewReq> renewReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        return mqnftService.renew(renewReq);
    }

    @ApiOperation(value = "核销")
    @ResponseBody
    @PostMapping("/writeOff")
    public Result<WriteOffVo> writeOff(@RequestBody @Valid ReqVo<WriteOffReq> writeOffReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        return mqnftService.writeOff(writeOffReq);
    }


    @ApiOperation(value = "批量销毁")
    @ResponseBody
    @PostMapping("/batchBurn")
    public Result<BatchBurnVo> batchBurn(@Validated @RequestBody ReqVo<BatchBurnReq> batchBurnReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        return mqnftService.batchBurn(batchBurnReq);
    }


}

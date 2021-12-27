package inno.fisco.bcos.be.controller;

import inno.fisco.bcos.be.constant.Config;
import inno.fisco.bcos.be.constant.Constant;
import inno.fisco.bcos.be.entity.request.*;
import inno.fisco.bcos.be.entity.response.*;
import inno.fisco.bcos.be.util.CommonUtils;
import inno.fisco.bcos.be.util.EncrypeUtils;
import inno.fisco.bcos.be.util.chain.NFTBcosClientWrapper;
import inno.fisco.bcos.be.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * @author peifeng
 * @date 2021/11/10 17:23
 */
@Api(tags = "NFT合约")
@Controller
@Validated
@RequestMapping(value = "/nft")
public class NFTController {

    Logger logger = LoggerFactory.getLogger(NFTController.class);

    @Autowired
    private NFTBcosClientWrapper nftBcosClientWrapper;

    @Autowired
    private Config config;

    @ApiOperation(value = "部署合约")
    @ResponseBody
    @PostMapping("/deploy")
    public Result<NFTDeployVo> deploy(@Validated @RequestBody NFTDeployDo nftDeploy) throws Exception {
        String orderId = System.currentTimeMillis() + "";
        String realPrivateKey;
        try {
            realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey, nftDeploy.getPrivateKey());
        } catch (Exception exception) {
            logger.error("[deploy]:orderId:{},status:param error,msg: privateKey error", orderId);
            return new Result().error(Constant.ERROR_CODE, Constant.AESDNCODE_ERROR);
        }
        if (nftDeploy.getCanWriteOff()) {
            if (CollectionUtils.isEmpty(nftDeploy.getInitWriteOff())) {
                logger.error("[deploy]:orderId:{},status:param error,msg: 未设置初始核销参数", orderId);
                return new Result().error(Constant.ERROR_CODE, "请设置初始核销参数！！！");
            }
        }
        if (StringUtils.isEmpty(nftDeploy.getEquityLink())) {
            nftDeploy.setEquityLink("");
        }
        TransactionResponse response = nftBcosClientWrapper.deploy(realPrivateKey, nftDeploy.getName(), nftDeploy.getSymbol(), nftDeploy.getTotalSupply(), nftDeploy.getEquityLink(), nftDeploy.getCanRenew(), nftDeploy.getCanWriteOff(), nftDeploy.getInitialDeadline());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[deploy]:orderId:{},status:fail,msg: {}", orderId, response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        NFTDeployVo nftDeployVo = new NFTDeployVo();
        String contractAddress = response.getContractAddress();
        nftDeployVo.setContractAddress(contractAddress);
        nftDeployVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        nftDeployVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        if (nftDeploy.getCanWriteOff()) {
            List<BigInteger> typeList = new ArrayList<>();
            List<BigInteger> supplyList = new ArrayList<>();
            for (InitWriteOffDo initWriteOffDo : nftDeploy.getInitWriteOff()) {
                typeList.add(initWriteOffDo.getType());
                supplyList.add(initWriteOffDo.getSupply());
            }
            TransactionResponse InitWriteOffResponse = nftBcosClientWrapper.setWriteOff(contractAddress, realPrivateKey, typeList, supplyList);
            if (!InitWriteOffResponse.getReceiptMessages().equals(Constant.SUCCESS)) {
                logger.error("[deploy-setWirteOff]:orderId:{},status:fail,msg: {}", orderId, InitWriteOffResponse.getReceiptMessages());
                return new Result().error(Constant.ERROR_CODE, InitWriteOffResponse.getReceiptMessages());
            }
            boolean result = InitWriteOffResponse.getValues().equals(Constant.TRUE);
            nftDeployVo.setSetWriteOffSuccess(result);
        }
        logger.info("[deploy]:orderId:{},status:success,result: {}", orderId, nftDeployVo.toString());
        return new Result().success(nftDeployVo);
    }

    @ApiOperation(value = "批量铸造")
    @ResponseBody
    @PostMapping("/batchMint")
    public Result<BatchMintVo> batchMint(@Validated @RequestBody BatchMintDo batchMintDo) throws Exception {
        String orderId = System.currentTimeMillis() + "";
        String realPrivateKey;
        try {
            realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey, batchMintDo.getPrivateKey());
        } catch (Exception exception) {
            logger.error("[batchMint]:orderId:{},status:param error,msg: privateKey error", orderId);
            return new Result().error(Constant.ERROR_CODE, Constant.AESDNCODE_ERROR);
        }
        TransactionResponse response = nftBcosClientWrapper.batchMint(batchMintDo.getContractAddress(), realPrivateKey, batchMintDo.getSupply(), batchMintDo.getTokenURI());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[batchMint]:orderId:{},status:fail,msg:{}", orderId, response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }

        BatchMintVo batchMintVo = new BatchMintVo();
        batchMintVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        batchMintVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        List<Object> returnABIObjects = response.getReturnObject();
        List<BigInteger> tokenIds = (List<BigInteger>) returnABIObjects.get(0);
        tokenIds.removeIf(bigInteger -> bigInteger.intValue() == 0);
        batchMintVo.setTokenIds(tokenIds);
        logger.info("[batchMint]:orderId:{},status:success,result:{}", orderId, batchMintVo.toString());
        return new Result().success(batchMintVo);
    }

    @ApiOperation(value = "批量出售")
    @ResponseBody
    @PostMapping("/batchSell")
    public Result<BatchSellVo> batchSell(@Validated @RequestBody BatchSellDo batchSellDo) throws Exception {
        String orderId = System.currentTimeMillis() + "";
        String realPrivateKey;
        try {
            realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey, orderId);
        } catch (Exception exception) {
            logger.error("[batchSell]:orderId:{},status:param error,msg: privateKey error", orderId);
            return new Result().error(Constant.ERROR_CODE, Constant.AESDNCODE_ERROR);
        }
        if (StringUtils.isEmpty(batchSellDo.getExpirationTime())) {
            batchSellDo.setExpirationTime(new BigInteger("0"));
        }
        TransactionResponse response = nftBcosClientWrapper.batchSell(batchSellDo.getContractAddress(), realPrivateKey, batchSellDo.getTokenIds(), batchSellDo.getTo(), batchSellDo.getExpirationTime());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[batchSell]:orderId:{},status:fail,msg:{}", orderId, response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        BatchSellVo batchSellVo = new BatchSellVo();
        batchSellVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        batchSellVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        Boolean result = response.getValues().equals(Constant.TRUE);
        batchSellVo.setSellSuccess(result);
        logger.info("[batchSell]:orderId:{},status:success,result:{}", orderId, batchSellVo.toString());
        return new Result().success(batchSellVo);
    }

    @ApiOperation(value = "批量转账")
    @ResponseBody
    @PostMapping("/batchTransfer")
    public Result<BatchTransferVo> batchTransfer(@Validated @RequestBody BatchTransferDo batchTransferDo) throws Exception {
        String orderId = System.currentTimeMillis() + "";
        String realPrivateKey;
        try {
            realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey, batchTransferDo.getPrivateKey());
        } catch (Exception exception) {
            logger.error("[batchTransfer]:orderId:{},status:param error,msg: privateKey error", orderId);
            return new Result().error(Constant.ERROR_CODE, Constant.AESDNCODE_ERROR);
        }
        TransactionResponse response = nftBcosClientWrapper.batchTransfer(batchTransferDo.getContractAddress(), realPrivateKey, batchTransferDo.getTokenIds(), batchTransferDo.getTo());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[batchTransfer]:orderId:{},status:fail,msg:{}", orderId, response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        BatchTransferVo batchTransferVo = new BatchTransferVo();
        batchTransferVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        batchTransferVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        Boolean result = response.getValues().equals(Constant.TRUE);
        batchTransferVo.setTransferSuccess(result);
        logger.info("[batchTransfer]:orderId:{},status:success,result:{}", orderId, batchTransferVo.toString());
        return new Result().success(batchTransferVo);
    }

    @ApiOperation(value = "续费")
    @ResponseBody
    @PostMapping("/renew")
    public Result<RenewVo> renew(@Validated @RequestBody RenewDo renewDo) throws Exception {
        String orderId = System.currentTimeMillis() + "";
        String realPrivateKey;
        try {
            realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey, orderId);
        } catch (Exception exception) {
            logger.error("[renew]:orderId:{},status:param error,msg: privateKey error", orderId);
            return new Result().error(Constant.ERROR_CODE, Constant.AESDNCODE_ERROR);
        }
        TransactionResponse response = nftBcosClientWrapper.renew(renewDo.getContractAddress(), realPrivateKey, renewDo.getTokenId(), renewDo.getRenewTime());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[renew]:orderId:{},status:fail,msg:{}", orderId, response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        RenewVo renewVo = new RenewVo();
        renewVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        renewVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        Boolean result = response.getValues().equals(Constant.TRUE);
        renewVo.setRenewSuccess(result);
        logger.info("[renew]:orderId:{},status:success,result:{}", orderId, renewVo.toString());
        return new Result().success(renewVo);
    }

	/*@ApiOperation(value = "增发")
	@ResponseBody
	@PostMapping("/addIssua")
	public Result<ResponseVo<AddIssuaVo>> addIssua(@RequestBody AddIssuaDo addIssuaDo) {
		return nftService.addIssua(addIssuaDo.getContractAddress(),addIssuaDo.getPrivateKey(),addIssuaDo.getAddIssuaSupply());
	}*/

    @ApiOperation(value = "核销")
    @ResponseBody
    @PostMapping("/writeOff")
    public Result<WriteOffVo> writeOff(@RequestBody @Valid WriteOffDo writeOffDo) throws Exception {
        String orderId = System.currentTimeMillis() + "";
        String realPrivateKey;
        try {
            realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey, writeOffDo.getPrivateKey());
        } catch (Exception exception) {
            logger.error("[deploy]:orderId:{},status:param error,msg: privateKey error", orderId);
            return new Result().error(Constant.ERROR_CODE, Constant.AESDNCODE_ERROR);
        }
        TransactionResponse response = nftBcosClientWrapper.writeOff(writeOffDo.getContractAddress(), realPrivateKey, writeOffDo.getType(), writeOffDo.getTokenId(), writeOffDo.getSupply());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[writeOff]:orderId:{},status:fail,msg:{}", orderId, response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        WriteOffVo writeOffVo = new WriteOffVo();
        writeOffVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        writeOffVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        Boolean result = response.getValues().equals(Constant.TRUE);
        writeOffVo.setWriteOffSuccess(result);
        logger.info("[writeOff]:orderId:{},status:success,result:{}", orderId, writeOffVo.toString());
        return new Result().success(writeOffVo);
    }

    @ApiOperation(value = "批量销毁")
    @ResponseBody
    @PostMapping("/batchBurn")
    public Result<BatchBurnVo> batchBurn(@Validated @RequestBody BatchBurnDo batchBurnDo) throws Exception {
        String orderId = System.currentTimeMillis() + "";
        String realPrivateKey;
        try {
            realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey, batchBurnDo.getPrivateKey());
        } catch (Exception exception) {
            logger.error("[batchBurn]:orderId:{},status:param error,msg: privateKey error", orderId);
            return new Result().error(Constant.ERROR_CODE, Constant.AESDNCODE_ERROR);
        }
        TransactionResponse response = nftBcosClientWrapper.batchBurn(batchBurnDo.getContractAddress(), realPrivateKey, batchBurnDo.getTokenIds());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[batchBurn]:orderId:{},status:fail,msg:{}", orderId, response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        BatchBurnVo batchBurnVo = new BatchBurnVo();
        batchBurnVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        batchBurnVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        Boolean result = response.getValues().equals(Constant.TRUE);
        batchBurnVo.setBurnSuccess(result);
        logger.info("[batchBurn]:orderId:{},status:success,result:{}", orderId, batchBurnVo.toString());
        return new Result().success(batchBurnVo);
    }


}

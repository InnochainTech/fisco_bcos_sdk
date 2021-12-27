package inno.fisco.bcos.be.service.impl;

import inno.fisco.bcos.be.constant.Constant;
import inno.fisco.bcos.be.entity.response.*;
import inno.fisco.bcos.be.entity.usesign.ReqVo;
import inno.fisco.bcos.be.entity.usesign.request.*;
import inno.fisco.bcos.be.service.MQNFTService;
import inno.fisco.bcos.be.util.CommonUtils;
import inno.fisco.bcos.be.util.chain.MQNFTBcosClientWrapper;
import inno.fisco.bcos.be.util.result.Result;
import org.apache.commons.collections.CollectionUtils;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.NoSuchTransactionFileException;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/12/8 1:59 下午
 */
@Service("MQNFTService")
public class MQNFTServiceImpl implements MQNFTService {

    Logger logger = LoggerFactory.getLogger(MQNFTServiceImpl.class);

    @Autowired
    private MQNFTBcosClientWrapper mqnftBcosClientWrapper;

    @Override
    public Result<NFTDeployVo> deploy(ReqVo<NFTDeploy> reqNFTDeploy) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        if (reqNFTDeploy.getFuncParam().getCanWriteOff()) {
            if (CollectionUtils.isEmpty(reqNFTDeploy.getFuncParam().getInitWriteOff())) {
                logger.error("[deploy]:orderId:{},status:param error,msg: 未设置初始核销参数", reqNFTDeploy.getOrderId());
                return new Result().error(Constant.ERROR_CODE, "请设置初始核销参数！！！");
            }
        }
        TransactionResponse response = mqnftBcosClientWrapper.deploy(reqNFTDeploy.getSignUserId(),
                reqNFTDeploy.getFuncParam().getName(),
                reqNFTDeploy.getFuncParam().getSymbol(),
                reqNFTDeploy.getFuncParam().getTotalSupply(),
                reqNFTDeploy.getFuncParam().getEquityLink(),
                reqNFTDeploy.getFuncParam().getCanRenew(),
                reqNFTDeploy.getFuncParam().getCanWriteOff(),
                reqNFTDeploy.getFuncParam().getInitialDeadline());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[deploy]:orderId:{},status:fail,msg:{}", reqNFTDeploy.getOrderId(), response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        NFTDeployVo nftDeployVo = new NFTDeployVo();
        nftDeployVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        nftDeployVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        String contractAddress = response.getContractAddress();
        if (reqNFTDeploy.getFuncParam().getCanWriteOff()) {
            List<InitWriteOffReq> initWriteOffReqList = reqNFTDeploy.getFuncParam().getInitWriteOff();
            List<BigInteger> typeList = new ArrayList<>();
            List<BigInteger> supplyList = new ArrayList<>();
            initWriteOffReqList.forEach(initWriteOffReq -> {
                typeList.add(initWriteOffReq.getType());
                supplyList.add(initWriteOffReq.getSupply());
            });
            TransactionResponse InitWriteOffResponse = mqnftBcosClientWrapper.setWriteOff(reqNFTDeploy.getSignUserId(), contractAddress, typeList, supplyList);
            if (!InitWriteOffResponse.getReceiptMessages().equals(Constant.SUCCESS)) {
                logger.error("[deploy:setWriteOff]:orderId:{},status:fail,msg:{}", reqNFTDeploy.getOrderId(), InitWriteOffResponse.getReceiptMessages());
                return new Result().error(Constant.ERROR_CODE, InitWriteOffResponse.getReceiptMessages());
            }
            nftDeployVo.setSetWriteOffSuccess(Boolean.TRUE);
        }
        nftDeployVo.setContractAddress(contractAddress);
        logger.info("[deploy]:orderId:{},status:success,result:{}", reqNFTDeploy.getOrderId(), nftDeployVo.toString());
        return new Result().success(nftDeployVo);
    }

    @Override
    public Result<BatchMintVo> batchMint(ReqVo<BatchMintReq> reqBatchMint) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        TransactionResponse response = mqnftBcosClientWrapper.batchMint(reqBatchMint.getSignUserId(),
                reqBatchMint.getContractAddress(),
                reqBatchMint.getFuncParam().getSupply(),
                reqBatchMint.getFuncParam().getTokenURI());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[batchMint]:orderId:{},status:fail,msg:{}", reqBatchMint.getOrderId(), response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }

        BatchMintVo batchMintVo = new BatchMintVo();
        batchMintVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        batchMintVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        List<Object> returnABIObjects = response.getReturnObject();
        List<BigInteger> tokenIds = (List<BigInteger>) returnABIObjects.get(0);
        tokenIds.removeIf(bigInteger -> bigInteger.intValue() == 0);
        batchMintVo.setTokenIds(tokenIds);
        logger.info("[batchMint]:orderId:{},status:success,result:{}", reqBatchMint.getOrderId(), batchMintVo.toString());
        return new Result().success(batchMintVo);
    }

    @Override
    public Result<BatchSellVo> batchSell(ReqVo<BatchSellReq> batchSellReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        TransactionResponse response = mqnftBcosClientWrapper.batchSell(batchSellReq.getSignUserId(),
                batchSellReq.getContractAddress(),
                batchSellReq.getFuncParam().getTokenIds(),
                batchSellReq.getFuncParam().getTo(),
                batchSellReq.getFuncParam().getExpirationTime());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[batchSell]:orderId:{},status:fail,msg:{}", batchSellReq.getOrderId(), response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        BatchSellVo batchSellVo = new BatchSellVo();
        batchSellVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        batchSellVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        Boolean result = response.getValues().equals(Constant.TRUE);
        batchSellVo.setSellSuccess(result);
        logger.info("[batchSell]:orderId:{},status:success,result:{}", batchSellReq.getOrderId(), batchSellVo.toString());
        return new Result().success(batchSellVo);
    }

    @Override
    public Result<BatchTransferVo> batchTransfer(ReqVo<BatchTransferReq> batchTransferReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        TransactionResponse response = mqnftBcosClientWrapper.batchTransfer(batchTransferReq.getSignUserId(),
                batchTransferReq.getContractAddress(),
                batchTransferReq.getFuncParam().getTokenIds(),
                batchTransferReq.getFuncParam().getTo());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[batchTransfer]:orderId:{},status:fail,msg:{}", batchTransferReq.getOrderId(), response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        BatchTransferVo batchTransferVo = new BatchTransferVo();
        batchTransferVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        batchTransferVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        Boolean result = response.getValues().equals(Constant.TRUE);
        batchTransferVo.setTransferSuccess(result);
        logger.info("[batchTransfer]:orderId:{},status:success,result:{}", batchTransferReq.getOrderId(), batchTransferVo.toString());
        return new Result().success(batchTransferVo);
    }

    @Override
    public Result<RenewVo> renew(ReqVo<RenewReq> renewReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        TransactionResponse response = mqnftBcosClientWrapper.renew(renewReq.getSignUserId(),
                renewReq.getContractAddress(),
                renewReq.getFuncParam().getTokenId(),
                renewReq.getFuncParam().getRenewTime());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[renew]:orderId:{},status:fail,msg:{}", renewReq.getOrderId(), response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        RenewVo renewVo = new RenewVo();
        renewVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        renewVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        Boolean result = response.getValues().equals(Constant.TRUE);
        renewVo.setRenewSuccess(result);
        logger.info("[renew]:orderId:{},status:success,result:{}", renewReq.getOrderId(), renewVo.toString());
        return new Result().success(renewVo);
    }

    @Override
    public Result<WriteOffVo> writeOff(ReqVo<WriteOffReq> writeOffReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        TransactionResponse response = mqnftBcosClientWrapper.writeOff(writeOffReq.getSignUserId(),
                writeOffReq.getContractAddress(),
                writeOffReq.getFuncParam().getType(),
                writeOffReq.getFuncParam().getTokenId(),
                writeOffReq.getFuncParam().getSupply());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[writeOff]:orderId:{},status:fail,msg:{}", writeOffReq.getOrderId(), response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        WriteOffVo writeOffVo = new WriteOffVo();
        writeOffVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        writeOffVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        Boolean result = response.getValues().equals(Constant.TRUE);
        writeOffVo.setWriteOffSuccess(result);
        logger.info("[writeOff]:orderId:{},status:success,result:{}", writeOffReq.getOrderId(), writeOffVo.toString());
        return new Result().success(writeOffVo);
    }

    @Override
    public Result<BatchBurnVo> batchBurn(ReqVo<BatchBurnReq> batchBurnReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
        TransactionResponse response = mqnftBcosClientWrapper.batchBurn(batchBurnReq.getSignUserId(),
                batchBurnReq.getContractAddress(),
                batchBurnReq.getFuncParam().getTokenIds());
        if (!response.getReceiptMessages().equals(Constant.SUCCESS)) {
            logger.error("[batchBurn]:orderId:{},status:fail,msg:{}", batchBurnReq.getOrderId(), response.getReceiptMessages());
            return new Result().error(Constant.ERROR_CODE, response.getReceiptMessages());
        }
        BatchBurnVo batchBurnVo = new BatchBurnVo();
        batchBurnVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
        batchBurnVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
        Boolean result = response.getValues().equals(Constant.TRUE);
        batchBurnVo.setBurnSuccess(result);
        logger.info("[batchBurn]:orderId:{},status:success,result:{}", batchBurnReq.getOrderId(), batchBurnVo.toString());
        return new Result().success(batchBurnVo);
    }


}

package inno.fisco.bcos.be.service;

import inno.fisco.bcos.be.entity.response.*;
import inno.fisco.bcos.be.entity.usesign.ReqVo;
import inno.fisco.bcos.be.entity.usesign.request.*;
import inno.fisco.bcos.be.util.result.Result;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.transaction.model.exception.NoSuchTransactionFileException;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionException;

import java.io.IOException;

/**
 * @author peifeng
 * @date 2021/12/8 1:59 下午
 */
public interface MQNFTService {

    public Result<NFTDeployVo> deploy(ReqVo<NFTDeploy> reqNFTDeploy) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException ;

    public Result<BatchMintVo> batchMint(ReqVo<BatchMintReq> reqBatchMint) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException;

    public Result<BatchSellVo> batchSell(ReqVo<BatchSellReq> BatchSellreq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException;

    public Result<BatchTransferVo> batchTransfer(ReqVo<BatchTransferReq> batchTransferReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException;

    public Result<RenewVo> renew(ReqVo<RenewReq> renewDoReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException;

    public Result<WriteOffVo> writeOff(ReqVo<WriteOffReq> writeOffReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException;

    public Result<BatchBurnVo> batchBurn(ReqVo<BatchBurnReq> batchBurnReq) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException;

}

package inno.fiscobcos.be.service;

import inno.fiscobcos.be.entity.ResponseVo;
import inno.fiscobcos.be.entity.request.InitWriteOffDo;
import inno.fiscobcos.be.entity.response.*;
import inno.fiscobcos.be.util.result.Result;

import java.math.BigInteger;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/10 18:14
 */
public interface NFTService {

	Result<NFTDeployVo> deploy(String privateKey, String name, String symbol, BigInteger totalSupply, String equityLink, Boolean canRenew, Boolean canWriteOff, List<BigInteger> types,List<BigInteger> supply, BigInteger initialDeadline) ;


	Result<BatchMintVo>  batchMint(String contractAddress, String privateKey, BigInteger supply, String tokenURI) ;


	Result<BatchSellVo> batchSell(String contractAddress, String privateKey, List<BigInteger> tokenIds_, String to_, BigInteger expirationTime_) ;


	Result<BatchTransferVo> batchTransfer(String contractAddress, String privateKey, List<BigInteger> tokenIds_, String to_) ;


	Result<RenewVo> renew(String contractAddress, String privateKey, BigInteger tokenId_, BigInteger renewTime_) ;


	/*Result<ResponseVo<AddIssuaVo>> addIssua(String contractAddress, String privateKey, BigInteger addIssuaSupply_) ;*/

	Result<WriteOffVo> writeOff(String contractAddress, String privateKey, BigInteger index_, BigInteger tokenId_, BigInteger supply_) ;

	Result<BatchBurnVo> batchBurn(String contractAddress, String privateKey, List<BigInteger>  tokenIds_) ;

	boolean getOverTime(String contractAddress, BigInteger tokenId) throws Exception;

	BigInteger getExpirationTime(String contractAddress, BigInteger tokenId) throws Exception;

	BigInteger getTotalSupply(String contractAddress)throws Exception;

	boolean getCanRenew(String contractAddress)throws Exception;

	boolean getCanWriteOff(String contractAddress)throws Exception;

	String getEquityLink(String contractAddress)throws Exception;

	BigInteger getTokenMinted(String contractAddress)throws Exception;

	List<InitWriteOffDo> getVipSupply(String contractAddress)throws Exception;

	List<InitWriteOffDo> getTokenVipSupply(String contractAddress, BigInteger tokenId)throws Exception;

	BigInteger getNow(String contractAddress)throws Exception;

	String getName(String contractAddress)throws Exception;

	List<BigInteger> getTokens(String contractAddress, String accountAddress) throws Exception;
}

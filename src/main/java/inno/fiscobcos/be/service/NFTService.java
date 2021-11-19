package inno.fiscobcos.be.service;

import inno.fiscobcos.be.entity.ResponseVo;
import inno.fiscobcos.be.entity.response.*;
import inno.fiscobcos.be.util.result.Result;

import java.math.BigInteger;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/10 18:14
 */
public interface NFTService {

	/**
	 * 部署
	 * @param privateKey
	 * @param name
	 * @param symbol
	 * @param totalSupply
	 * @param equityLink
	 * @param canRenew
	 * @param canWriteOff
	 * @param WriteOffQuantity
	 * @param initialDeadline
	 * @return 铸造的新tokenIds
	 */
	Result<NFTDeployVo> deploy(String privateKey, String name, String symbol, BigInteger totalSupply, String equityLink, Boolean canRenew, Boolean canWriteOff, List<BigInteger> WriteOffQuantity, BigInteger initialDeadline) ;

	/**
	 * 批量铸造
	 * @param contractAddress
	 * @param privateKey
	 * @param supply
	 * @param tokenURI
	 * @return 铸造的新tokenIds
	 */
	Result<BatchMintVo>  batchMint(String contractAddress, String privateKey, BigInteger supply, String tokenURI) ;

	/**
	 * 批量出售
	 * @param contractAddress
	 * @param privateKey
	 * @param tokenIds_
	 * @param to_
	 * @param expirationTime_
	 * @return
	 */
	Result<BatchSellVo> batchSell(String contractAddress, String privateKey, List<BigInteger> tokenIds_, String to_, BigInteger expirationTime_) ;

	/**
	 * 批量转账
	 * @param contractAddress
	 * @param privateKey
	 * @param tokenIds_
	 * @param to_
	 * @return
	 */
	Result<BatchTransferVo> batchTransfer(String contractAddress, String privateKey, List<BigInteger> tokenIds_, String to_) ;

	/**
	 * 续费
	 * @param contractAddress
	 * @param privateKey
	 * @param tokenId_
	 * @param renewTime_
	 * @return
	 */
	Result<RenewVo> renew(String contractAddress, String privateKey, BigInteger tokenId_, BigInteger renewTime_) ;

	/**
	 * 增发
	 * @param contractAddress
	 * @param privateKey
	 * @param addIssuaSupply_
	 * @return
	 */
	/*Result<ResponseVo<AddIssuaVo>> addIssua(String contractAddress, String privateKey, BigInteger addIssuaSupply_) ;*/


	/**
	 * 核销
	 * @param contractAddress
	 * @param privateKey
	 * @param index_
	 * @param tokenId_
	 * @param supply_
	 * @return
	 */
	Result<WriteOffVo> writeOff(String contractAddress, String privateKey, BigInteger index_, BigInteger tokenId_, BigInteger supply_) ;

	/**
	 * 批量销毁
	 * @param contractAddress
	 * @param privateKey
	 * @param tokenIds_
	 * @return
	 */
	Result<BatchBurnVo> batchBurn(String contractAddress, String privateKey, List<BigInteger>  tokenIds_) ;

	/**
	 * 查看代币是否过期
	 * @param contractAddress
	 * @param tokenId
	 * @return
	 */
	boolean getOverTime(String contractAddress, BigInteger tokenId) throws Exception;

	BigInteger getExpirationTime(String contractAddress, BigInteger tokenId) throws Exception;

	BigInteger getTotalSupply(String contractAddress)throws Exception;

	boolean getCanRenew(String contractAddress)throws Exception;

	boolean getCanWriteOff(String contractAddress)throws Exception;

	String getEquityLink(String contractAddress)throws Exception;

	BigInteger getTokenMinted(String contractAddress)throws Exception;

	List<BigInteger> getVipSupply(String contractAddress)throws Exception;

	List<BigInteger> getTokenVipSupply(String contractAddress, BigInteger tokenId)throws Exception;

	BigInteger getNow(String contractAddress)throws Exception;

	String getName(String contractAddress)throws Exception;

	List<BigInteger> getTokens(String contractAddress, BigInteger tokenId) throws Exception;
}

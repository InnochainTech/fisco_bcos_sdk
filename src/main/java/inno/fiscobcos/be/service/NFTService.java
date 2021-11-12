package inno.fiscobcos.be.service;

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
	 * @param canIssua
	 * @param canWriteOff
	 * @param WriteOffQuantity
	 * @return 铸造的新tokenIds
	 */
	String  deploy(String privateKey, String name, String symbol, BigInteger totalSupply,String equityLink, Boolean canRenew,Boolean canIssua,Boolean canWriteOff, BigInteger[] WriteOffQuantity) throws Exception;

	/**
	 * 批量铸造
	 * @param contractAddress
	 * @param privateKey
	 * @param supply
	 * @param tokenURI
	 * @return 铸造的新tokenIds
	 */
	List<BigInteger>  batchMint(String contractAddress,String privateKey,BigInteger supply, String tokenURI) throws Exception;

	/**
	 * 批量出售
	 * @param contractAddress
	 * @param privateKey
	 * @param tokenIds_
	 * @param to_
	 * @param expirationTime_
	 * @return
	 */
	boolean batchSell(String contractAddress,String privateKey,BigInteger[] tokenIds_, String to_, BigInteger expirationTime_) throws Exception;

	/**
	 * 续费
	 * @param contractAddress
	 * @param privateKey
	 * @param tokenId_
	 * @param renewTime_
	 * @return
	 */
	boolean renew(String contractAddress,String privateKey,BigInteger tokenId_, BigInteger renewTime_) throws Exception;

	/**
	 * 增发
	 * @param contractAddress
	 * @param privateKey
	 * @param addIssuaSupply_
	 * @return
	 */
	boolean addIssua(String contractAddress,String privateKey,BigInteger addIssuaSupply_) throws Exception;


	/**
	 * 核销
	 * @param contractAddress
	 * @param privateKey
	 * @param index_
	 * @param tokenId_
	 * @param supply_
	 * @return
	 */
	boolean writeOff(String contractAddress,String privateKey,BigInteger index_, BigInteger tokenId_, BigInteger supply_) throws Exception;

	/**
	 * 批量销毁
	 * @param contractAddress
	 * @param privateKey
	 * @param tokenIds_
	 * @return
	 */
	boolean batchBurn(String contractAddress,String privateKey,BigInteger[]  tokenIds_) throws Exception;

	/**
	 * 查看代币是否过期
	 * @param contractAddress
	 * @param tokenId
	 * @return
	 */
	boolean getOverTime(String contractAddress, BigInteger tokenId) throws Exception;
}

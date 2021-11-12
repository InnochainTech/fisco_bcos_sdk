package inno.fiscobcos.be.util.chain;

import inno.fiscobcos.be.constant.Config;
import inno.fiscobcos.be.constant.Constant;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/10 16:00
 */
@Component
public class NFTClientUtils extends ClientUtils {

	Logger logger = LoggerFactory.getLogger(NFTClientUtils.class);

	@Autowired
	private Config config;

	public String deploy(String privateKey, String name, String symbol, BigInteger totalSupply,String equityLink, Boolean canRenew,Boolean canIssua,Boolean canWriteOff) throws Exception{
		TransactionResponse response;
		String contractAddress = null;
		List<Object> params = new ArrayList<>();
		params.add(name);
		params.add(symbol);
		params.add(totalSupply);
		params.add(equityLink);
		params.add(canRenew);
		params.add(canIssua);
		params.add(canWriteOff);
		try {
			transactionProcessor = getAssembleTransactionProcessor(privateKey, config.abiFilePath, config.binFilePath);
			logger.info("state:{},functionName:{},params:{}","ready","deploy",params);
			response = transactionProcessor.deployByContractLoader(Constant.CONTRACT_NAME, params);
			contractAddress = response.getContractAddress();
			logger.info("state:{},functionName:{},tx:{},contractAddress:{}","success","deploy",response.getTransactionReceipt().getTransactionHash(),contractAddress);
		} catch (Exception e) {
			logger.error("functionName:{},info:{}","deploy",e.getMessage());
			System.out.println(e.getMessage());
		}
		return contractAddress;
	}

	public List<BigInteger> batchMint(String contractAddress,String privateKey,BigInteger supply,String tokenURI) throws Exception {
		List<BigInteger> list;
		transactionProcessor = getAssembleTransactionProcessor(privateKey, config.abiFilePath, config.binFilePath);
		List<Object> params = new ArrayList<>();
		params.add(supply);
		params.add(tokenURI);
		logger.info("state:{},functionName:{},params:{}","ready","batchMint",params);
		TransactionResponse response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "batchMint", params);
		if(response.getReceiptMessages().equals(Constant.SUCCESS)){
			List<Object> returnABIObjects = response.getReturnObject();
			list = (List<BigInteger>) returnABIObjects.get(0);
			Iterator<BigInteger> it = list.iterator();
			while(it.hasNext()){
				if(it.next().intValue() == 0){
					it.remove();
				}
			}
			logger.info("state:{},functionName:{},result:{}","success","batchMint",list);
		}else{
			throw new Exception(response.getReceiptMessages());
		}
		return list;
	}

	public boolean batchSell(String contractAddress, String privateKey, BigInteger[] tokenIds, String to, BigInteger expirationTime) throws Exception{
		transactionProcessor = getAssembleTransactionProcessor(privateKey, config.abiFilePath, config.binFilePath);
		List<Object> params = new ArrayList<>();
		params.add(tokenIds);
		params.add(to);
		params.add(expirationTime);
		logger.info("state:{},functionName:{},params:{}","ready","batchSell",params);
		TransactionResponse response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "batchSell", params);
		boolean result;
		if(response.getReceiptMessages().equals(Constant.SUCCESS)){
			result = response.getValues().equals(Constant.TRUE);
			logger.info("state:{},functionName:{},result:{}","success","batchSell",result);
		}else{
			throw new Exception(response.getReceiptMessages());
		}
		return result;
	}

	public boolean renew(String contractAddress, String privateKey, BigInteger tokenId, BigInteger renewTime) throws Exception {
		transactionProcessor = getAssembleTransactionProcessor(privateKey, config.abiFilePath, config.binFilePath);
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		params.add(renewTime);
		logger.info("state:{},functionName:{},params:{}","ready","renew",params);
		TransactionResponse response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "renew", params);
		boolean result;
		if(response.getReceiptMessages().equals(Constant.SUCCESS)){
			result = response.getValues().equals(Constant.TRUE);
			logger.info("state:{},functionName:{},result:{}","success","renew",result);
		}else{
			throw new Exception(response.getReceiptMessages());
		}
		return result;
	}

	public boolean addIssua(String contractAddress, String privateKey, BigInteger addIssuaSupply) throws Exception {
		transactionProcessor = getAssembleTransactionProcessor(privateKey, config.abiFilePath, config.binFilePath);
		List<Object> params = new ArrayList<>();
		params.add(addIssuaSupply);
		logger.info("state:{},functionName:{},params:{}","ready","addIssua",params);
		TransactionResponse response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "addIssua", params);
		boolean result;
		if(response.getReceiptMessages().equals(Constant.SUCCESS)){
			result = response.getValues().equals(Constant.TRUE);
			logger.info("state:{},functionName:{},result:{}","success","addIssua",result);
		}else{
			throw new Exception(response.getReceiptMessages());
		}
		return result;
	}

	public boolean writeOff(String contractAddress, String privateKey, BigInteger index, BigInteger tokenId, BigInteger supply) throws Exception {
		transactionProcessor = getAssembleTransactionProcessor(privateKey, config.abiFilePath, config.binFilePath);
		List<Object> params = new ArrayList<>();
		params.add(index);
		params.add(tokenId);
		params.add(supply);
		logger.info("state:{},functionName:{},params:{}","ready","writeOff",params);
		TransactionResponse response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "writeOff", params);
		boolean result;
		if(response.getReceiptMessages().equals(Constant.SUCCESS)){
			result = response.getValues().equals(Constant.TRUE);
			logger.info("state:{},functionName:{},result:{}","success","writeOff",result);
		}else{
			throw new Exception(response.getReceiptMessages());
		}
		return result;
	}

	public boolean batchBurn(String contractAddress, String privateKey, BigInteger[] tokenIds) throws Exception {
		transactionProcessor = getAssembleTransactionProcessor(privateKey, config.abiFilePath, config.binFilePath);
		List<Object> params = new ArrayList<>();
		params.add(tokenIds);
		logger.info("state:{},functionName:{},params:{}","ready","batchBurn",params);
		TransactionResponse response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "batchBurn", params);
		boolean result;
		if(response.getReceiptMessages().equals(Constant.SUCCESS)){
			result = response.getValues().equals(Constant.TRUE);
			logger.info("state:{},functionName:{},result:{}","success","batchBurn",result);
		}else{
			throw new Exception(response.getReceiptMessages());
		}
		return result;
	}

	public boolean setWriteOff(String contractAddress, String privateKey, BigInteger[] vipSupply) throws Exception {
		transactionProcessor = getAssembleTransactionProcessor(privateKey, config.abiFilePath, config.binFilePath);
		List<Object> params = new ArrayList<>();
		params.add(vipSupply);
		logger.info("state:{},functionName:{},params:{}","ready","setWriteOff",params);
		TransactionResponse response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "setWriteOff", params);
		boolean result;
		if(response.getReceiptMessages().equals(Constant.SUCCESS)){
			result = response.getValues().equals(Constant.TRUE);
			logger.info("state:{},functionName:{},result:{}","success","setWriteOff",result);
		}else{
			throw new Exception(response.getReceiptMessages());
		}
		return result;
	}

	public boolean getOverTime(String contractAddress,BigInteger tokenId) throws Exception {
		boolean result = false;
		transactionProcessor = getAssembleTransactionProcessor(
				"", config.abiFilePath, config.binFilePath);
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getOverTime",
				params);

		return result;
	}
}

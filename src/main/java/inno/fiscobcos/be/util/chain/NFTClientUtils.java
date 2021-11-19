package inno.fiscobcos.be.util.chain;

import inno.fiscobcos.be.constant.Config;
import inno.fiscobcos.be.constant.Constant;
import inno.fiscobcos.be.entity.response.*;
import inno.fiscobcos.be.util.result.Result;
import inno.fiscobcos.be.util.result.ResultUtils;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.utils.StringUtils;
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

	/* ---------  交易方法 -------------*/

	public Result<NFTDeployVo> deploy(String privateKey, String name, String symbol, BigInteger totalSupply, String equityLink, Boolean canRenew, Boolean canWriteOff){
		TransactionResponse response;
		String contractAddress = null;
		NFTDeployVo nftDeployVo = new NFTDeployVo();
		List<Object> params = new ArrayList<>();
		params.add(name);
		params.add(symbol);
		params.add(totalSupply);
		params.add(equityLink);
		params.add(canRenew);
		params.add(canWriteOff);
		try {
			initAssembleTransactionProcessor(privateKey);
			logger.info("state:{},functionName:{},params:{}","ready","deploy",params);
			response = transactionProcessor.deployByContractLoader(Constant.CONTRACT_NAME, params);
			contractAddress = response.getContractAddress();
			if(StringUtils.isEmpty(contractAddress)){
				logger.info("state:{},functionName:{},tx:{}","fail","deploy",response.getTransactionReceipt().getTransactionHash());
				return ResultUtils.error(Constant.ERROR_CODE,response.getReceiptMessages());
			}else{
				logger.info("state:{},functionName:{},tx:{},contractAddress:{}","success","deploy",response.getTransactionReceipt().getTransactionHash(),contractAddress);
				nftDeployVo.setContractAddress(contractAddress);
				nftDeployVo.setBlockNumber(conver16to10(response.getTransactionReceipt().getBlockNumber()));
				nftDeployVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				return ResultUtils.success(nftDeployVo);
			}
		} catch (Exception exception) {
			logger.error("functionName:{},info:{}","deploy",exception.getMessage());
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}
	}

	public Result<BatchMintVo> batchMint(String contractAddress, String privateKey, BigInteger supply, String tokenURI) {
		TransactionResponse response;
		List<BigInteger> tokenIds = null;
		BatchMintVo BatchMintVo = new BatchMintVo();
		List<Object> params = new ArrayList<>();
		params.add(supply);
		params.add(tokenURI);
		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("state:{},functionName:{},params:{}","ready","batchMint",params);
			response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "batchMint", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				List<Object> returnABIObjects = response.getReturnObject();
				tokenIds = (List<BigInteger>) returnABIObjects.get(0);
				Iterator<BigInteger> it = tokenIds.iterator();
				while(it.hasNext()){
					if(it.next().intValue() == 0){
						it.remove();
					}
				}
				BatchMintVo.setTokenIds(tokenIds);
				BatchMintVo.setBlockNumber(conver16to10(response.getTransactionReceipt().getBlockNumber()));
				BatchMintVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("state:{},functionName:{},tx:{},result:{}","success","batchMint",response.getTransactionReceipt().getTransactionHash(),tokenIds);
				return ResultUtils.success(BatchMintVo);
			}else{
				logger.info("state:{},functionName:{},tx:{}","fail","batchMint",response.getTransactionReceipt().getTransactionHash());
				return ResultUtils.error(Constant.ERROR_CODE,response.getReceiptMessages());
			}
		}catch (Exception exception){
			logger.error("functionName:{},info:{}","batchMint",exception.getMessage());
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

	}

	public Result<BatchSellVo> batchSell(String contractAddress, String privateKey, List<BigInteger> tokenIds, String to, BigInteger expirationTime) {
		TransactionResponse response;
		boolean result;
		BatchSellVo batchSellVo = new BatchSellVo();
		List<Object> params = new ArrayList<>();
		params.add(tokenIds);
		params.add(to);
		params.add(expirationTime);

		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("state:{},functionName:{},params:{}","ready","batchSell",params);
			response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "batchSell", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				batchSellVo.setSellSuccess(result);
				batchSellVo.setBlockNumber(conver16to10(response.getTransactionReceipt().getBlockNumber()));
				batchSellVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("state:{},functionName:{},tx:{},result:{}","success","batchSell",response.getTransactionReceipt().getTransactionHash(),result);
				return ResultUtils.success(batchSellVo);
			}else{
				logger.info("state:{},functionName:{},tx:{}","fail","batchSell",response.getTransactionReceipt().getTransactionHash());
				return ResultUtils.error(Constant.ERROR_CODE,response.getReceiptMessages());
			}

		}catch(Exception exception){
			logger.error("functionName:{},info:{}","batchSell",exception.getMessage());
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

	}

	public Result<BatchTransferVo> batchTransfer(String contractAddress, String privateKey, List<BigInteger> tokenIds, String to) {
		TransactionResponse response;
		boolean result;
		BatchSellVo batchSellVo = new BatchSellVo();
		List<Object> params = new ArrayList<>();
		params.add(tokenIds);
		params.add(to);

		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("state:{},functionName:{},params:{}","ready","batchTransfer",params);
			response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "batchTransfer", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				batchSellVo.setSellSuccess(result);
				batchSellVo.setBlockNumber(conver16to10(response.getTransactionReceipt().getBlockNumber()));
				batchSellVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("state:{},functionName:{},result:{}","success","batchTransfer",result);
				return ResultUtils.success(batchSellVo);
			}else{
				logger.info("state:{},functionName:{},tx:{}","fail","batchTransfer",response.getTransactionReceipt().getTransactionHash());
				return ResultUtils.error(Constant.ERROR_CODE,response.getReceiptMessages());
			}
		}catch(Exception exception){
			logger.error("functionName:{},info:{}","batchTransfer",exception.getMessage());
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

	}

	public Result<RenewVo> renew(String contractAddress, String privateKey, BigInteger tokenId, BigInteger renewTime)  {
		TransactionResponse response;
		boolean result;
		RenewVo renewVo = new RenewVo();
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		params.add(renewTime);
		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("state:{},functionName:{},params:{}","ready","renew",params);
			response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "renew", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				renewVo.setRenewSuccess(result);
				renewVo.setBlockNumber(conver16to10(response.getTransactionReceipt().getBlockNumber()));
				renewVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("state:{},functionName:{},result:{}","success","renew",result);
				return ResultUtils.success(renewVo);
			}else{
				logger.info("state:{},functionName:{},tx:{}","fail","renew",response.getTransactionReceipt().getTransactionHash());
				return ResultUtils.error(Constant.ERROR_CODE,response.getReceiptMessages());
			}
		}catch(Exception exception){
			logger.error("functionName:{},info:{}","renew",exception.getMessage());
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}
	}

	/*public Result<ResponseVo<AddIssuaVo>> addIssua(String contractAddress, String privateKey, BigInteger addIssuaSupply)  {

		TransactionResponse response;
		boolean result;
		AddIssuaVo addIssuaVo = new AddIssuaVo();
		ResponseVo responseVo = new ResponseVo();
		List<Object> params = new ArrayList<>();
		params.add(addIssuaSupply);

		try{
			transactionProcessor = getAssembleTransactionProcessor(privateKey, config.abiFilePath, config.binFilePath);
			logger.info("state:{},functionName:{},params:{}","ready","addIssua",params);
			response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "addIssua", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				addIssuaVo.setIssuaSuccess(result);
				responseVo.setResultData(addIssuaVo);
				responseVo.setBlockNumber(response.getTransactionReceipt().getBlockNumber());
				responseVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("state:{},functionName:{},result:{}","success","addIssua",result);
				return ResultUtils.success(responseVo);
			}else{
				logger.info("state:{},functionName:{},tx:{}","fail","addIssua",response.getTransactionReceipt().getTransactionHash());
				return ResultUtils.error(Constant.ERROR_CODE,"增发失败！！！");
			}
		}catch(Exception exception){
			logger.error("functionName:{},info:{}","addIssua",exception.getMessage());
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

	}*/

	public Result<WriteOffVo> writeOff(String contractAddress, String privateKey, BigInteger index, BigInteger tokenId, BigInteger supply)  {
		TransactionResponse response;
		boolean result;
		WriteOffVo writeOffVo = new WriteOffVo();
		List<Object> params = new ArrayList<>();
		params.add(index);
		params.add(tokenId);
		params.add(supply);
		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("state:{},functionName:{},params:{}","ready","writeOff",params);
			response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "writeOff", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				writeOffVo.setWriteOffSuccess(result);
				writeOffVo.setBlockNumber(conver16to10(response.getTransactionReceipt().getBlockNumber()));
				writeOffVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("state:{},functionName:{},result:{}","success","writeOff",result);
				return ResultUtils.success(writeOffVo);
			}else{
				logger.info("state:{},functionName:{},tx:{}","fail","writeOff",response.getTransactionReceipt().getTransactionHash());
				return ResultUtils.error(Constant.ERROR_CODE,response.getReceiptMessages());
			}
		}catch(Exception exception){
			logger.error("functionName:{},info:{}","writeOff",exception.getMessage());
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}
	}

	public Result<BatchBurnVo> batchBurn(String contractAddress, String privateKey, List<BigInteger> tokenIds)  {
		TransactionResponse response;
		boolean result;
		BatchBurnVo batchBurnVo = new BatchBurnVo();
		List<Object> params = new ArrayList<>();
		params.add(tokenIds);
		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("state:{},functionName:{},params:{}","ready","batchBurn",params);
			response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "batchBurn", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				batchBurnVo.setBurnSuccess(result);
				batchBurnVo.setBlockNumber(conver16to10(response.getTransactionReceipt().getBlockNumber()));
				batchBurnVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("state:{},functionName:{},result:{}","success","batchBurn",result);
				return ResultUtils.success(batchBurnVo);
			}else{
				logger.info("state:{},functionName:{},tx:{}","fail","batchBurn",response.getTransactionReceipt().getTransactionHash());
				return ResultUtils.error(Constant.ERROR_CODE,response.getReceiptMessages());
			}
		}catch(Exception exception){
			logger.error("functionName:{},info:{}","writeOff",exception.getMessage());
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}
	}

	public boolean setWriteOff(String contractAddress, String privateKey, List<BigInteger> vipSupply)  {
		boolean result;
		TransactionResponse response;
		List<Object> params = new ArrayList<>();
		params.add(vipSupply);
		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("state:{},functionName:{},params:{}","ready","setWriteOff",params);
			response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "setWriteOff", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				logger.info("state:{},functionName:{},result:{}","success","setWriteOff",result);
				return result;
			}else{
				logger.info("state:{},functionName:{},tx:{}","fail","setWriteOff",response.getTransactionReceipt().getTransactionHash());
				return false;
			}
		}catch(Exception exception){
			logger.error("functionName:{},info:{}","setWriteOff",exception.getMessage());
			return false;
		}
	}

	public boolean setInitialDeadline(String contractAddress, String privateKey, BigInteger initialDeadline)  {
		boolean result;
		TransactionResponse response;
		List<Object> params = new ArrayList<>();
		params.add(initialDeadline);
		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("state:{},functionName:{},params:{}","ready","setInitialDeadline",params);
			response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "setInitialDeadline", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				logger.info("state:{},functionName:{},result:{}","success","setInitialDeadline",result);
				return result;
			}else{
				logger.info("state:{},functionName:{},tx:{}","fail","batchBurn",response.getTransactionReceipt().getTransactionHash());
				return false;
			}
		}catch(Exception exception){
			logger.error("functionName:{},info:{}","setInitialDeadline",exception.getMessage());
			return false;
		}

	}

	/* ---------  查询方法 -------------*/
	public boolean getOverTime(String contractAddress,BigInteger tokenId)  throws Exception{
		boolean result = false;
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getOverTime", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			if(callResponse.getValues().equals(Constant.TRUE)){
				result = true;
			}else{
				result = false;
			}
		}
		return result;
	}

	public BigInteger getExpirationTime(String contractAddress, BigInteger tokenId) throws Exception{
		BigInteger result = null;
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getExpirationTime", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result =  new BigInteger(conver(callResponse.getValues()));
		}
		return result;
	}

	public BigInteger getTotalSupply(String contractAddress) throws Exception{
		BigInteger result = null;
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getTotalSupply", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result =  new BigInteger(conver(callResponse.getValues()));
		}

		return result;
	}

	public boolean getCanRenew(String contractAddress) throws Exception{
		boolean result = false;
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getCanRenew", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			if(callResponse.getValues().equals(Constant.TRUE)){
				result = true;
			}else{
				result = false;
			}
		}

		return result;
	}

	public boolean getCanWriteOff(String contractAddress) throws Exception{
		boolean result = false;
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getCanWriteOff", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			if(callResponse.getValues().equals(Constant.TRUE)){
				result = true;
			}else{
				result = false;
			}
		}

		return result;
	}

	public String getEquityLink(String contractAddress) throws Exception{
		String result = "";
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getEquityLink", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result = conver(callResponse.getValues());
		}

		return result;
	}

	public BigInteger getTokenMinted(String contractAddress) throws Exception{
		BigInteger result = null;
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getTokenMinted", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result =  new BigInteger(conver(callResponse.getValues()));
		}
		return result;
	}

	public List<BigInteger> getVipSupply(String contractAddress) throws Exception{
		List<BigInteger> result = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getVipSupply", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			String[] arr = conver(callResponse.getValues()).split(",");
			for (int i = 0; i <arr.length; i++){
				if(!StringUtils.isEmpty(arr[i])) {
					result.add(new BigInteger(arr[i]));
				}
			}
		}
		return result;
	}

	public List<BigInteger> getTokenVipSupply(String contractAddress, BigInteger tokenId) throws Exception{
		List<BigInteger> result = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getTokenVipSupply", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			String[] arr = conver(callResponse.getValues()).split(",");
			for (int i = 0; i <arr.length; i++){
				if(!StringUtils.isEmpty(arr[i])){
					result.add(new BigInteger(arr[i]));
				}
			}
		}

		return result;
	}

	public BigInteger getNow(String contractAddress) throws Exception{
		BigInteger result = null;
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getNow", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result =  new BigInteger(conver(callResponse.getValues()));
		}
		return result;
	}

	public String getName(String contractAddress) throws Exception{
		String result = "";
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "name", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result = conver(callResponse.getValues());
		}

		return result;
	}

	public List<BigInteger> getTokens(String contractAddress, BigInteger tokenId) throws Exception {
		List<BigInteger> result = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = transactionProcessor.sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getTokens", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			String[] arr = conver(callResponse.getValues()).split(",");
			for (int i = 0; i <arr.length; i++){
				if(!StringUtils.isEmpty(arr[i])) {
					result.add(new BigInteger(arr[i]));
				}
			}
		}
		return result;
	}


	/**
	 * 初始TransactionProcessor
	 * @param privateKey
	 * @throws Exception
	 */
	private void initAssembleTransactionProcessor(String privateKey) throws Exception {
		transactionProcessor = getAssembleTransactionProcessor(privateKey, config.abiFilePath, config.binFilePath);
	}



}

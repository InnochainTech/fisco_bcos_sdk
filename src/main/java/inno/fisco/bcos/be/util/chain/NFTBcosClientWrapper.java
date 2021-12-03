package inno.fisco.bcos.be.util.chain;

import inno.fisco.bcos.be.constant.Config;
import inno.fisco.bcos.be.constant.Constant;
import inno.fisco.bcos.be.entity.request.InitWriteOffDo;
import inno.fisco.bcos.be.entity.response.*;
import inno.fisco.bcos.be.util.CommonUtils;
import inno.fisco.bcos.be.util.result.Result;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/10 16:00
 */
@Component
public class NFTBcosClientWrapper {

	Logger logger = LoggerFactory.getLogger(NFTBcosClientWrapper.class);

	@Autowired
	private BcosClientWrapper bcosClientWrapper;

	@Autowired
	private Config config;

	/* ---------  交易方法 -------------*/

	public Result<NFTDeployVo> deploy(String orderId, String privateKey, String name, String symbol, BigInteger totalSupply, String equityLink, Boolean canRenew, Boolean canWriteOff, BigInteger initialDeadline){
		TransactionResponse response;
		String receiptMessage;
		NFTDeployVo nftDeployVo = new NFTDeployVo();
		List<Object> params = new ArrayList<>();
		params.add(name);
		params.add(symbol);
		params.add(totalSupply);
		params.add(equityLink);
		params.add(canRenew);
		params.add(canWriteOff);
		params.add(initialDeadline);
		try {
			initAssembleTransactionProcessor(privateKey);
			logger.info("orderId:{},state:{},functionName:{},params:{}",orderId,"ready","deploy",params);
			response = bcosClientWrapper.getTransactionProcessor().deployByContractLoader(Constant.CONTRACT_NAME, params);
			receiptMessage = response.getReceiptMessages();
			if(receiptMessage.equals(Constant.SUCCESS)){
				logger.info("orderId:{},state:{},functionName:{},tx:{},contractAddress:{}",orderId,"success","deploy",response.getTransactionReceipt().getTransactionHash(),response.getContractAddress());
				nftDeployVo.setContractAddress(response.getContractAddress());
				nftDeployVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
				nftDeployVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				return new Result().success(nftDeployVo);
			}else{
				logger.info("orderId:{},state:{},functionName:{},tx:{}",orderId,"fail","deploy",response.getTransactionReceipt().getTransactionHash());
				return new Result().error(Constant.ERROR_CODE,response.getReceiptMessages());
			}
		} catch (Exception exception) {
			logger.error("orderId:{},functionName:{},info:{}",orderId,"deploy",exception.getMessage());
			return new Result().error(Constant.ERROR_CODE,exception.getMessage());
		}
	}

	public Result<BatchMintVo> batchMint(String orderId, String contractAddress, String privateKey, BigInteger supply, String tokenURI) {
		TransactionResponse response;
		List<BigInteger> tokenIds;
		BatchMintVo batchMintVo = new BatchMintVo();
		List<Object> params = new ArrayList<>();
		params.add(supply);
		params.add(tokenURI);
		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("orderId:{},state:{},functionName:{},params:{}",orderId,"ready","batchMint",params);
			response = bcosClientWrapper.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "batchMint", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				List<Object> returnABIObjects = response.getReturnObject();
				tokenIds = (List<BigInteger>) returnABIObjects.get(0);
				tokenIds.removeIf(bigInteger -> bigInteger.intValue() == 0);
				batchMintVo.setTokenIds(tokenIds);
				batchMintVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
				batchMintVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("orderId:{},state:{},functionName:{},tx:{},result:{}",orderId,"success","batchMint",response.getTransactionReceipt().getTransactionHash(),tokenIds);
				return new Result().success(batchMintVo);
			}else{
				logger.info("orderId:{},state:{},functionName:{},tx:{}",orderId,"fail","batchMint",response.getTransactionReceipt().getTransactionHash());
				return new Result().error(Constant.ERROR_CODE,response.getReceiptMessages());
			}
		}catch (Exception exception){
			logger.error("orderId:{},functionName:{},info:{}",orderId,"batchMint",exception.getMessage());
			return new Result().error(Constant.ERROR_CODE,exception.getMessage());
		}

	}

	public Result<BatchSellVo> batchSell(String orderId, String contractAddress, String privateKey, List<BigInteger> tokenIds, String to, BigInteger expirationTime) {
		TransactionResponse response;
		boolean result;
		BatchSellVo batchSellVo = new BatchSellVo();
		List<Object> params = new ArrayList<>();
		params.add(tokenIds);
		params.add(to);
		params.add(expirationTime);

		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("orderId:{},state:{},functionName:{},params:{}",orderId,"ready","batchSell",params);
			response = bcosClientWrapper.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "batchSell", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				batchSellVo.setSellSuccess(result);
				batchSellVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
				batchSellVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("orderId:{},state:{},functionName:{},tx:{},result:{}",orderId,"success","batchSell",response.getTransactionReceipt().getTransactionHash(),result);
				return new Result().success(batchSellVo);
			}else{
				logger.info("orderId:{},state:{},functionName:{},tx:{}",orderId,"fail","batchSell",response.getTransactionReceipt().getTransactionHash());
				return new Result().error(Constant.ERROR_CODE,response.getReceiptMessages());
			}

		}catch(Exception exception){
			logger.error("orderId:{},functionName:{},info:{}",orderId,"batchSell",exception.getMessage());
			return new Result().error(Constant.ERROR_CODE,exception.getMessage());
		}

	}

	public Result<BatchTransferVo> batchTransfer(String orderId, String contractAddress, String privateKey, List<BigInteger> tokenIds, String to) {
		TransactionResponse response;
		boolean result;
		BatchTransferVo batchTransferVo = new BatchTransferVo();
		List<Object> params = new ArrayList<>();
		params.add(tokenIds);
		params.add(to);

		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("orderId:{},state:{},functionName:{},params:{}",orderId,"ready","batchTransfer",params);
			response = bcosClientWrapper.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "batchTransfer", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				batchTransferVo.setTransferSuccess(result);
				batchTransferVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
				batchTransferVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("orderId:{},state:{},functionName:{},result:{}",orderId,"success","batchTransfer",result);
				return new Result().success(batchTransferVo);
			}else{
				logger.info("orderId:{},state:{},functionName:{},tx:{}",orderId,"fail","batchTransfer",response.getTransactionReceipt().getTransactionHash());
				return new Result().error(Constant.ERROR_CODE,response.getReceiptMessages());
			}
		}catch(Exception exception){
			logger.error("orderId:{},functionName:{},info:{}",orderId,"batchTransfer",exception.getMessage());
			return new Result().error(Constant.ERROR_CODE,exception.getMessage());
		}

	}

	public Result<RenewVo> renew(String orderId, String contractAddress, String privateKey, BigInteger tokenId, BigInteger renewTime)  {
		TransactionResponse response;
		boolean result;
		RenewVo renewVo = new RenewVo();
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		params.add(renewTime);
		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("orderId:{},state:{},functionName:{},params:{}",orderId,"ready","renew",params);
			response = bcosClientWrapper.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "renew", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				renewVo.setRenewSuccess(result);
				renewVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
				renewVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("orderId:{},state:{},functionName:{},result:{}",orderId,"success","renew",result);
				return new Result().success(renewVo);
			}else{
				logger.info("orderId:{},state:{},functionName:{},tx:{}",orderId,"fail","renew",response.getTransactionReceipt().getTransactionHash());
				return new Result().error(Constant.ERROR_CODE,response.getReceiptMessages());
			}
		}catch(Exception exception){
			logger.error("orderId:{},functionName:{},info:{}",orderId,"renew",exception.getMessage());
			return new Result().error(Constant.ERROR_CODE,exception.getMessage());
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

	public Result<WriteOffVo> writeOff(String orderId,String contractAddress, String privateKey, BigInteger type, BigInteger tokenId, BigInteger supply)  {
		TransactionResponse response;
		boolean result;
		WriteOffVo writeOffVo = new WriteOffVo();
		List<Object> params = new ArrayList<>();
		params.add(type);
		params.add(tokenId);
		params.add(supply);
		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("orderId:{},state:{},functionName:{},params:{}",orderId,"ready","writeOff",params);
			response = bcosClientWrapper.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "writeOff", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				writeOffVo.setWriteOffSuccess(result);
				writeOffVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
				writeOffVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("orderId:{},state:{},functionName:{},result:{}",orderId,"success","writeOff",result);
				return new Result().success(writeOffVo);
			}else{
				logger.info("orderId:{},state:{},functionName:{},tx:{}",orderId,"fail","writeOff",response.getTransactionReceipt().getTransactionHash());
				return new Result().error(Constant.ERROR_CODE,response.getReceiptMessages());
			}
		}catch(Exception exception){
			logger.error("orderId:{},functionName:{},info:{}",orderId,"writeOff",exception.getMessage());
			return new Result().error(Constant.ERROR_CODE,exception.getMessage());
		}
	}

	public Result<BatchBurnVo> batchBurn(String orderId,String contractAddress, String privateKey, List<BigInteger> tokenIds)  {
		TransactionResponse response;
		boolean result;
		BatchBurnVo batchBurnVo = new BatchBurnVo();
		List<Object> params = new ArrayList<>();
		params.add(tokenIds);
		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("orderId:{},state:{},functionName:{},params:{}",orderId,"ready","batchBurn",params);
			response = bcosClientWrapper.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "batchBurn", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				batchBurnVo.setBurnSuccess(result);
				batchBurnVo.setBlockNumber(CommonUtils.conver16to10(response.getTransactionReceipt().getBlockNumber()));
				batchBurnVo.setTransationHash(response.getTransactionReceipt().getTransactionHash());
				logger.info("orderId:{},state:{},functionName:{},result:{}",orderId,"success","batchBurn",result);
				return new Result().success(batchBurnVo);
			}else{
				logger.info("orderId:{},state:{},functionName:{},tx:{}",orderId,"fail","batchBurn",response.getTransactionReceipt().getTransactionHash());
				return new Result().error(Constant.ERROR_CODE,response.getReceiptMessages());
			}
		}catch(Exception exception){
			logger.error("orderId:{},functionName:{},info:{}",orderId,"batchBurn",exception.getMessage());
			return new Result().error(Constant.ERROR_CODE,exception.getMessage());
		}
	}

	public boolean setWriteOff(String orderId,String contractAddress, String privateKey, List<BigInteger> types,List<BigInteger> supply)  {
		boolean result;
		TransactionResponse response;
		List<Object> params = new ArrayList<>();
		params.add(types);
		params.add(supply);
		try{
			initAssembleTransactionProcessor(privateKey);
			logger.info("orderId:{},state:{},functionName:{},params:{}",orderId,"ready","setWriteOff",params);
			response = bcosClientWrapper.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "setWriteOff", params);
			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
				result = response.getValues().equals(Constant.TRUE);
				logger.info("orderId:{},state:{},functionName:{},result:{}",orderId,"success","setWriteOff",result);
				return result;
			}else{
				logger.info("orderId:{},state:{},functionName:{},tx:{}",orderId,"fail","setWriteOff",response.getTransactionReceipt().getTransactionHash());
				return false;
			}
		}catch(Exception exception){
			logger.error("orderId:{},functionName:{},info:{}",orderId,"setWriteOff",exception.getMessage());
			return false;
		}
	}

//	public boolean setInitialDeadline(String orderId,String contractAddress, String privateKey, BigInteger initialDeadline)  {
//		boolean result;
//		TransactionResponse response;
//		List<Object> params = new ArrayList<>();
//		params.add(initialDeadline);
//		try{
//			initAssembleTransactionProcessor(privateKey);
//			logger.info("orderId:{},state:{},functionName:{},params:{}",orderId,"ready","setInitialDeadline",params);
//			response = transactionProcessor.sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, "setInitialDeadline", params);
//			if(response.getReceiptMessages().equals(Constant.SUCCESS)){
//				result = response.getValues().equals(Constant.TRUE);
//				logger.info("orderId:{},state:{},functionName:{},result:{}",orderId,"success","setInitialDeadline",result);
//				return result;
//			}else{
//				logger.info("orderId:{},state:{},functionName:{},tx:{}",orderId,"fail","batchBurn",response.getTransactionReceipt().getTransactionHash());
//				return false;
//			}
//		}catch(Exception exception){
//			logger.error("orderId:{},functionName:{},info:{}",orderId,"setInitialDeadline",exception.getMessage());
//			return false;
//		}
//
//	}

	/* ---------  查询方法 -------------*/

	public boolean getOverTime(String contractAddress,BigInteger tokenId)  throws Exception{
		boolean result = false;
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getOverTime", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result = callResponse.getValues().equals(Constant.TRUE);
		}
		return result;
	}

	public BigInteger getExpirationTime(String contractAddress, BigInteger tokenId) throws Exception{
		BigInteger result = null;
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getExpirationTime", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result =  new BigInteger(CommonUtils.conver(callResponse.getValues()));
		}
		return result;
	}

	public BigInteger getTotalSupply(String contractAddress) throws Exception{
		BigInteger result = null;
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getTotalSupply", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result =  new BigInteger(CommonUtils.conver(callResponse.getValues()));
		}

		return result;
	}

	public boolean getCanRenew(String contractAddress) throws Exception{
		boolean result = false;
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getCanRenew", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result = callResponse.getValues().equals(Constant.TRUE);
		}

		return result;
	}

	public boolean getCanWriteOff(String contractAddress) throws Exception{
		boolean result = false;
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getCanWriteOff", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result = callResponse.getValues().equals(Constant.TRUE);
		}

		return result;
	}

	public String getEquityLink(String contractAddress) throws Exception{
		String result = "";
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getEquityLink", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result = CommonUtils.conver(callResponse.getValues());
		}

		return result;
	}

	public BigInteger getTokenMinted(String contractAddress) throws Exception{
		BigInteger result = null;
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getTokenMinted", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result =  new BigInteger(CommonUtils.conver(callResponse.getValues()));
		}
		return result;
	}

	public List<InitWriteOffDo> getVipSupply(String contractAddress) throws Exception{
		List<InitWriteOffDo> result = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getVipSupply", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			List<Object> returnObject = callResponse.getReturnObject();
			String[] typesStr = CommonUtils.conver(returnObject.get(0).toString()).split(",");
			String[] supplyStr = CommonUtils.conver(returnObject.get(1).toString()).split(",");
			for(int i = 0; i < typesStr.length; i++){
				InitWriteOffDo initWriteOffDo = new InitWriteOffDo(new BigInteger(typesStr[i].replaceAll(" ","") ),new BigInteger(supplyStr[i].replaceAll(" ","") ));
				result.add(initWriteOffDo);
			}
		}
		return result;
	}

	public List<InitWriteOffDo> getTokenVipSupply(String contractAddress, BigInteger tokenId) throws Exception{
		List<InitWriteOffDo> result = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getTokenVipSupply", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			List<Object> returnObject = callResponse.getReturnObject();
			String[] typesStr = CommonUtils.conver(returnObject.get(0).toString()).split(",");
			String[] supplyStr = CommonUtils.conver(returnObject.get(1).toString()).split(",");
			for(int i = 0; i < typesStr.length; i++){
				InitWriteOffDo initWriteOffDo = new InitWriteOffDo(new BigInteger(typesStr[i].replaceAll(" ","") ),new BigInteger(supplyStr[i].replaceAll(" ","") ));
				result.add(initWriteOffDo);
			}
		}

		return result;
	}

	public BigInteger getNow(String contractAddress) throws Exception{
		BigInteger result = null;
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getNow", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result =  new BigInteger(CommonUtils.conver(callResponse.getValues()));
		}
		return result;
	}

	public String getName(String contractAddress) throws Exception{
		String result = "";
		List<Object> params = new ArrayList<>();
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "name", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			result = CommonUtils.conver(callResponse.getValues());
		}

		return result;
	}

	public List<BigInteger> getTokens(String contractAddress, String accountAddress) throws Exception {
		List<BigInteger> result = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		params.add(accountAddress);
		initAssembleTransactionProcessor("");
		CallResponse callResponse = bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, "getTokens", params);
		if(callResponse.getReturnMessage().equals(Constant.SUCCESS)){
			String[] arr = CommonUtils.conver(callResponse.getValues()).split(",");
			for (String s : arr) {
				if (!StringUtils.isEmpty(s)) {
					result.add(new BigInteger(s));
				}
			}
		}
		return result;
	}


	/**
	 * 初始TransactionProcessor
	 */
	private void initAssembleTransactionProcessor(String privateKey) throws Exception {
		bcosClientWrapper.setTransactionProcessor(bcosClientWrapper.getAssembleTransactionProcessor(privateKey));
	}



}

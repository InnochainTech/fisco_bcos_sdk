package inno.fisco.bcos.be.util.chain;

import inno.fisco.bcos.be.constant.Config;
import inno.fisco.bcos.be.constant.Constant;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionBaseException;
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

	public TransactionResponse deploy(String privateKey, String name, String symbol, BigInteger totalSupply, String equityLink, Boolean canRenew, Boolean canWriteOff, BigInteger initialDeadline) throws ABICodecException, TransactionBaseException {
		List<Object> params = new ArrayList<>();
		params.add(name);
		params.add(symbol);
		params.add(totalSupply);
		params.add(equityLink);
		params.add(canRenew);
		params.add(canWriteOff);
		params.add(initialDeadline);
		return bcosClientWrapper.getTransactionProcessor().deployByContractLoader(Constant.CONTRACT_NAME, params);

	}

	public TransactionResponse batchMint(String contractAddress, String privateKey, BigInteger supply, String tokenURI) throws Exception {
		List<Object> params = new ArrayList<>();
		params.add(supply);
		params.add(tokenURI);
		return sendTransaction(contractAddress,privateKey, "batchMint",params);
	}

	public TransactionResponse batchSell(String contractAddress, String privateKey, List<BigInteger> tokenIds, String to, BigInteger expirationTime) throws Exception {
		List<Object> params = new ArrayList<>();
		params.add(tokenIds);
		params.add(to);
		params.add(expirationTime);
		return sendTransaction(contractAddress,privateKey, "batchSell",params);
	}

	public TransactionResponse batchTransfer(String contractAddress, String privateKey, List<BigInteger> tokenIds, String to) throws Exception {
		List<Object> params = new ArrayList<>();
		params.add(tokenIds);
		params.add(to);
		return sendTransaction(contractAddress,privateKey, "batchTransfer",params);
	}

	public TransactionResponse renew(String contractAddress, String privateKey, BigInteger tokenId, BigInteger renewTime) throws Exception {
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		params.add(renewTime);
		return sendTransaction(contractAddress,privateKey, "renew",params);
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

	public TransactionResponse writeOff(String contractAddress, String privateKey, BigInteger type, BigInteger tokenId, BigInteger supply) throws Exception {
		List<Object> params = new ArrayList<>();
		params.add(type);
		params.add(tokenId);
		params.add(supply);
		return sendTransaction(contractAddress,privateKey, "writeOff",params);
	}

	public TransactionResponse batchBurn(String contractAddress, String privateKey, List<BigInteger> tokenIds) throws Exception {
		List<Object> params = new ArrayList<>();
		params.add(tokenIds);
		return sendTransaction(contractAddress,privateKey, "batchBurn",params);
	}

	public TransactionResponse setWriteOff(String contractAddress, String privateKey, List<BigInteger> typeList,List<BigInteger> supplyList) throws Exception {
		List<Object> params = new ArrayList<>();
		params.add(typeList);
		params.add(supplyList);
		return sendTransaction(contractAddress,privateKey, "setWriteOff",params);
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

	public CallResponse getOverTime(String contractAddress,BigInteger tokenId)  throws Exception{
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		return Call(contractAddress,"getOverTime",params);
	}

	public CallResponse getExpirationTime(String contractAddress, BigInteger tokenId) throws Exception{
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		return Call(contractAddress,"getExpirationTime",params);
	}

	public CallResponse getTotalSupply(String contractAddress) throws Exception{
		List<Object> params = new ArrayList<>();
		return Call(contractAddress,"getTotalSupply",params);
	}

	public CallResponse getCanRenew(String contractAddress) throws Exception{
		List<Object> params = new ArrayList<>();
		return Call(contractAddress,"getCanRenew",params);
	}

	public CallResponse getCanWriteOff(String contractAddress) throws Exception{
		List<Object> params = new ArrayList<>();
		return Call(contractAddress,"getCanWriteOff",params);
	}

	public CallResponse getEquityLink(String contractAddress) throws Exception{
		List<Object> params = new ArrayList<>();
		return Call(contractAddress,"getEquityLink",params);
	}

	public CallResponse getTokenMinted(String contractAddress) throws Exception{
		List<Object> params = new ArrayList<>();
		return Call(contractAddress,"getTokenMinted",params);
	}

	public CallResponse getVipSupply(String contractAddress) throws Exception{
		List<Object> params = new ArrayList<>();
		return Call(contractAddress,"getVipSupply",params);
	}

	public CallResponse getTokenVipSupply(String contractAddress, BigInteger tokenId) throws Exception{
		List<Object> params = new ArrayList<>();
		params.add(tokenId);
		return Call(contractAddress,"getTokenVipSupply",params);
	}

	public CallResponse getNow(String contractAddress) throws Exception{
		List<Object> params = new ArrayList<>();
		return Call(contractAddress,"getNow",params);
	}

	public CallResponse getName(String contractAddress) throws Exception{
		List<Object> params = new ArrayList<>();
		return Call(contractAddress,"getName",params);
	}

	public CallResponse getTokens(String contractAddress, String accountAddress) throws Exception {
		List<Object> params = new ArrayList<>();
		params.add(accountAddress);
		return Call(contractAddress,"getTokenVipSupply",params);
	}


	private CallResponse Call(String contractAddress,String methodName,List<Object> params) throws Exception {
		bcosClientWrapper.setTransactionProcessor(bcosClientWrapper.getAssembleTransactionProcessor(""));
		return bcosClientWrapper.getTransactionProcessor().sendCallByContractLoader(Constant.CONTRACT_NAME, contractAddress, methodName, params);
	}

	private TransactionResponse sendTransaction(String contractAddress,String privateKey,String methodName,List<Object> params) throws Exception {
		bcosClientWrapper.setTransactionProcessor(bcosClientWrapper.getAssembleTransactionProcessor(privateKey));
		return bcosClientWrapper.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader(Constant.CONTRACT_NAME, contractAddress, methodName, params);
	}



}

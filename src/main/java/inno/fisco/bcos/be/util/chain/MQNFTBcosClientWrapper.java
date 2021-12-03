package inno.fisco.bcos.be.util.chain;

import inno.fisco.bcos.be.constant.Config;
import inno.fisco.bcos.be.constant.Constant;
import inno.fisco.bcos.be.transaction.BasicAbiTransaction;
import inno.fisco.bcos.be.transaction.ISignTransaction;
import inno.fisco.bcos.be.transaction.KeyToolSignTransaction;
import inno.fisco.bcos.be.transaction.LegoTransaction;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.NoSuchTransactionFileException;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/10 16:00
 */
@Component
public class MQNFTBcosClientWrapper {

	@Autowired
	private LegoTransaction legoTransaction;

	@Autowired
	private Config config;


	/* ---------  交易方法 -------------*/

	public TransactionResponse deploy(String signUserId, String name, String symbol, BigInteger totalSupply, String equityLink, Boolean canRenew, Boolean canWriteOff, BigInteger initialDeadline) throws NoSuchTransactionFileException, ABICodecException, TransactionException, IOException {
		BasicAbiTransaction abiTx = new BasicAbiTransaction();
		String contractName = Constant.CONTRACT_NAME;
		String abiContent = legoTransaction.bcosClientWrapper.getContractLoader().getABIByContractName(contractName);
		String contractBin = legoTransaction.bcosClientWrapper.getContractLoader().getBinaryByContractName(contractName);
		List<Object> params = new ArrayList<Object>();
		params.add(name);
		params.add(symbol);
		params.add(totalSupply);
		params.add(equityLink);
		params.add(canRenew);
		params.add(canWriteOff);
		params.add(initialDeadline);
		abiTx.setContractName(contractName)
				.setAbiContent(abiContent)
				.setParams(params)
				.setBinContent(contractBin)
				.setDeployTransaction(true)
				.setTools(legoTransaction.bcosClientWrapper.getTxCryptoSuite(), null, null);
		ISignTransaction signTxImpl =
				new KeyToolSignTransaction(legoTransaction.bcosClientWrapper.getTxCryptoSuite());
		return legoTransaction.sendTransactionAndGetResponse(config.bcosGroupId, abiTx, signTxImpl,signUserId);
	}

	public TransactionResponse batchMint(String signUserId, String contractAddress, BigInteger supply, String tokenURI) throws NoSuchTransactionFileException, ABICodecException, TransactionException, IOException {
		List<Object> params = new ArrayList<Object>();
		params.add(supply);
		params.add(tokenURI);
		return sendTransaction(signUserId,contractAddress,"batchMint",params);
	}

	public TransactionResponse batchSell(String signUserId, String contractAddress, List<BigInteger> tokenIds, String to, BigInteger expirationTime) throws NoSuchTransactionFileException, ABICodecException, TransactionException, IOException {
		List<Object> params = new ArrayList<Object>();
		params.add(tokenIds);
		params.add(to);
		params.add(expirationTime);
		return sendTransaction(signUserId,contractAddress,"batchSell",params);
	}

	public TransactionResponse batchTransfer(String signUserId, String contractAddress, List<BigInteger> tokenIds, String to) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
		List<Object> params = new ArrayList<Object>();
		params.add(tokenIds);
		params.add(to);
		return sendTransaction(signUserId,contractAddress,"batchTransfer",params);
	}

	public TransactionResponse renew(String signUserId, String contractAddress, BigInteger tokenId, BigInteger renewTime) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
		List<Object> params = new ArrayList<Object>();
		params.add(tokenId);
		params.add(renewTime);
		return sendTransaction(signUserId,contractAddress,"renew",params);
	}

	public TransactionResponse writeOff(String signUserId,String contractAddress, BigInteger type, BigInteger tokenId, BigInteger supply) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
		List<Object> params = new ArrayList<Object>();
		params.add(type);
		params.add(tokenId);
		params.add(supply);
		return sendTransaction(signUserId,contractAddress,"writeOff",params);
	}

	public TransactionResponse batchBurn(String signUserId,String contractAddress, List<BigInteger> tokenIds) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
		List<Object> params = new ArrayList<Object>();
		params.add(tokenIds);
		return sendTransaction(signUserId,contractAddress,"batchBurn",params);
	}

	public TransactionResponse setWriteOff(String signUserId,String contractAddress, List<BigInteger> types,List<BigInteger> supply) throws ABICodecException, TransactionException, NoSuchTransactionFileException, IOException {
		List<Object> params = new ArrayList<Object>();
		params.add(types);
		params.add(supply);
		return sendTransaction(signUserId,contractAddress,"setWriteOff",params);
	}

	private TransactionResponse sendTransaction(String signUserId, String contractAddress, String methodName, List<Object> params)throws NoSuchTransactionFileException, ABICodecException, TransactionException, IOException{
		BasicAbiTransaction abiTx = new BasicAbiTransaction();
		String contractName = Constant.CONTRACT_NAME;
		String abiContent = legoTransaction.bcosClientWrapper.getContractLoader().getABIByContractName(Constant.CONTRACT_NAME);

		abiTx.setContractName(contractName)
				.setAbiContent(abiContent)
				.setMethodName(methodName)
				.setParams(params)
				.setTo(contractAddress)
				.setTools(legoTransaction.bcosClientWrapper.getTxCryptoSuite(), null, null);

		// 实例化一个签名服务的实现
		ISignTransaction signTxImpl = new KeyToolSignTransaction(legoTransaction.bcosClientWrapper.getTxCryptoSuite());

		// 发送后同步得到结果
		TransactionResponse response = legoTransaction.sendTransactionAndGetResponse(config.bcosChainId, abiTx, signTxImpl,signUserId);

		return response;
	}

}

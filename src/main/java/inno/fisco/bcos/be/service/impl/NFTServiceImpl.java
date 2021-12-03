package inno.fisco.bcos.be.service.impl;

import inno.fisco.bcos.be.entity.request.InitWriteOffDo;
import inno.fisco.bcos.be.entity.response.*;
import inno.fisco.bcos.be.service.NFTService;
import inno.fisco.bcos.be.util.chain.NFTBcosClientWrapper;
import inno.fisco.bcos.be.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/10 18:14
 */
@Service("nftService")
public class NFTServiceImpl implements NFTService {

	@Autowired
    NFTBcosClientWrapper nftClientUtils;

	@Override
	public Result<NFTDeployVo> deploy(String privateKey, String name, String symbol, BigInteger totalSupply, String equityLink, Boolean canRenew, Boolean canWriteOff, List<BigInteger> types, List<BigInteger> supply, BigInteger initialDeadline) {
		String orderId = System.currentTimeMillis()+"";
		Result<NFTDeployVo> result = nftClientUtils.deploy(orderId,privateKey,name,symbol,totalSupply,equityLink,canRenew,canWriteOff,initialDeadline);
		if(result.getCode() == 200 && canWriteOff){
			//设置核销参数
			result.getData().setSetWriteOffSuccess(nftClientUtils.setWriteOff(orderId,result.getData().getContractAddress(),privateKey,types,supply));
		}
		return result;
	}

	@Override
	public Result<BatchMintVo> batchMint(String contractAddress, String privateKey, BigInteger supply, String tokenURI) {
		String orderId = System.currentTimeMillis()+"";
		return nftClientUtils.batchMint(orderId, contractAddress, privateKey, supply, tokenURI);
	}

	@Override
	public Result<BatchSellVo> batchSell(String contractAddress, String privateKey, List<BigInteger> tokenIds, String to, BigInteger expirationTime) {
		String orderId = System.currentTimeMillis()+"";
		return nftClientUtils.batchSell(orderId, contractAddress,  privateKey,  tokenIds,  to,  expirationTime);
	}

	@Override
	public Result<RenewVo> renew(String contractAddress, String privateKey, BigInteger tokenId, BigInteger renewTime) {
		String orderId = System.currentTimeMillis()+"";
		return nftClientUtils.renew(orderId, contractAddress,  privateKey,  tokenId,  renewTime);
	}

	/*@Override
	public Result<ResponseVo<AddIssuaVo>> addIssua(String contractAddress, String privateKey, BigInteger addIssuaSupply) {
		return nftClientUtils.addIssua(contractAddress,  privateKey,  addIssuaSupply);
	}*/

	@Override
	public Result<WriteOffVo> writeOff(String contractAddress, String privateKey, BigInteger index, BigInteger tokenId, BigInteger supply) {
		String orderId = System.currentTimeMillis()+"";
		return nftClientUtils.writeOff(orderId, contractAddress,  privateKey,  index,  tokenId,  supply);
	}

	@Override
	public Result<BatchBurnVo> batchBurn(String contractAddress, String privateKey, List<BigInteger> tokenIds) {
		String orderId = System.currentTimeMillis()+"";
		return nftClientUtils.batchBurn(orderId, contractAddress, privateKey, tokenIds);
	}

	@Override
	public Result<BatchTransferVo> batchTransfer(String contractAddress, String privateKey, List<BigInteger> tokenIds_, String to_)  {
		String orderId = System.currentTimeMillis()+"";
		return nftClientUtils.batchTransfer(orderId, contractAddress, privateKey, tokenIds_ , to_);
	}

	@Override
	public boolean getOverTime(String contractAddress, BigInteger tokenId) throws Exception {
		return nftClientUtils.getOverTime(contractAddress,tokenId);
	}

	@Override
	public BigInteger getExpirationTime(String contractAddress, BigInteger tokenId) throws Exception{
		return nftClientUtils.getExpirationTime(contractAddress,tokenId);
	}

	@Override
	public BigInteger getTotalSupply(String contractAddress) throws Exception {
		return nftClientUtils.getTotalSupply(contractAddress);
	}

	@Override
	public boolean getCanRenew(String contractAddress) throws Exception {
		return nftClientUtils.getCanRenew(contractAddress);
	}

	@Override
	public boolean getCanWriteOff(String contractAddress) throws Exception {
		return nftClientUtils.getCanWriteOff(contractAddress);
	}

	@Override
	public String getEquityLink(String contractAddress) throws Exception {
		return nftClientUtils.getEquityLink(contractAddress);
	}

	@Override
	public BigInteger getTokenMinted(String contractAddress) throws Exception {
		return nftClientUtils.getTokenMinted(contractAddress);
	}

	@Override
	public List<InitWriteOffDo> getVipSupply(String contractAddress) throws Exception {
		return nftClientUtils.getVipSupply(contractAddress);
	}

	@Override
	public List<InitWriteOffDo> getTokenVipSupply(String contractAddress, BigInteger tokenId) throws Exception {
		return nftClientUtils.getTokenVipSupply(contractAddress,tokenId);
	}

	@Override
	public BigInteger getNow(String contractAddress) throws Exception {
		return nftClientUtils.getNow(contractAddress);
	}

	@Override
	public String getName(String contractAddress) throws Exception {
		return nftClientUtils.getName(contractAddress);
	}

	@Override
	public List<BigInteger> getTokens(String contractAddress, String accountAddress) throws Exception {
		return nftClientUtils.getTokens(contractAddress,accountAddress);
	}


}

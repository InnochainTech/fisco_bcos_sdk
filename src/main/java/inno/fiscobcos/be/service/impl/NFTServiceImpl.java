package inno.fiscobcos.be.service.impl;

import inno.fiscobcos.be.entity.ResponseVo;
import inno.fiscobcos.be.entity.response.*;
import inno.fiscobcos.be.service.NFTService;
import inno.fiscobcos.be.util.chain.NFTClientUtils;
import inno.fiscobcos.be.util.result.Result;
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
	NFTClientUtils nftClientUtils;

	@Override
	public Result<NFTDeployVo> deploy(String privateKey, String name, String symbol, BigInteger totalSupply, String equityLink, Boolean canRenew, Boolean canWriteOff, List<BigInteger> WriteOffQuantity, BigInteger initialDeadline) {
		Result<NFTDeployVo> result = nftClientUtils.deploy(privateKey,name,symbol,totalSupply,equityLink,canRenew,canWriteOff);
		if(!canRenew){
			//设置初始有效截至时间
			result.getData().setSetInitialDeadlineSuccess(nftClientUtils.setInitialDeadline(result.getData().getContractAddress(),privateKey,initialDeadline));
		}
		if(canWriteOff){
			//设置核销参数
			result.getData().setSetWriteOffSuccess(nftClientUtils.setWriteOff(result.getData().getContractAddress(),privateKey,WriteOffQuantity));
		}
		return result;
	}

	@Override
	public Result<BatchMintVo> batchMint(String contractAddress, String privateKey, BigInteger supply, String tokenURI) {
		return nftClientUtils.batchMint(contractAddress, privateKey, supply, tokenURI);
	}

	@Override
	public Result<BatchSellVo> batchSell(String contractAddress, String privateKey, List<BigInteger> tokenIds, String to, BigInteger expirationTime) {
		return nftClientUtils.batchSell( contractAddress,  privateKey,  tokenIds,  to,  expirationTime);
	}

	@Override
	public Result<RenewVo> renew(String contractAddress, String privateKey, BigInteger tokenId, BigInteger renewTime) {
		return nftClientUtils.renew(contractAddress,  privateKey,  tokenId,  renewTime);
	}

	/*@Override
	public Result<ResponseVo<AddIssuaVo>> addIssua(String contractAddress, String privateKey, BigInteger addIssuaSupply) {
		return nftClientUtils.addIssua(contractAddress,  privateKey,  addIssuaSupply);
	}*/

	@Override
	public Result<WriteOffVo> writeOff(String contractAddress, String privateKey, BigInteger index, BigInteger tokenId, BigInteger supply) {
		return nftClientUtils.writeOff(contractAddress,  privateKey,  index,  tokenId,  supply);
	}

	@Override
	public Result<BatchBurnVo> batchBurn(String contractAddress, String privateKey, List<BigInteger> tokenIds) {
		return nftClientUtils.batchBurn(contractAddress, privateKey, tokenIds);
	}

	@Override
	public Result<BatchTransferVo> batchTransfer(String contractAddress, String privateKey, List<BigInteger> tokenIds_, String to_)  {
		return nftClientUtils.batchTransfer(contractAddress, privateKey, tokenIds_ , to_);
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
	public List<BigInteger> getVipSupply(String contractAddress) throws Exception {
		return nftClientUtils.getVipSupply(contractAddress);
	}

	@Override
	public List<BigInteger> getTokenVipSupply(String contractAddress, BigInteger tokenId) throws Exception {
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
	public List<BigInteger> getTokens(String contractAddress, BigInteger tokenId) throws Exception {
		return nftClientUtils.getTokens(contractAddress,tokenId);
	}


}

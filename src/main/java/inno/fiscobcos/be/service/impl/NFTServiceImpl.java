package inno.fiscobcos.be.service.impl;

import inno.fiscobcos.be.service.NFTService;
import inno.fiscobcos.be.util.chain.NFTClientUtils;
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
	public String deploy(String privateKey, String name, String symbol, BigInteger totalSupply, String equityLink, Boolean canRenew, Boolean canIssua, Boolean canWriteOff, BigInteger[] WriteOffQuantity) throws Exception{
		String contractAddr = nftClientUtils.deploy(privateKey,name,symbol,totalSupply,equityLink,canRenew,canIssua,canWriteOff);
		if(canWriteOff){
			//设置核销参数
			nftClientUtils.setWriteOff(contractAddr,privateKey,WriteOffQuantity);
		}
		return contractAddr;
	}

	@Override
	public List<BigInteger> batchMint(String contractAddress, String privateKey, BigInteger supply, String tokenURI) throws Exception{
		return nftClientUtils.batchMint(contractAddress, privateKey, supply, tokenURI);
	}

	@Override
	public boolean batchSell(String contractAddress, String privateKey, BigInteger[] tokenIds, String to, BigInteger expirationTime) throws Exception{
		return nftClientUtils.batchSell( contractAddress,  privateKey,  tokenIds,  to,  expirationTime);
	}

	@Override
	public boolean renew(String contractAddress, String privateKey, BigInteger tokenId, BigInteger renewTime) throws Exception{
		return nftClientUtils.renew(contractAddress,  privateKey,  tokenId,  renewTime);
	}

	@Override
	public boolean addIssua(String contractAddress, String privateKey, BigInteger addIssuaSupply) throws Exception{
		return nftClientUtils.addIssua(contractAddress,  privateKey,  addIssuaSupply);
	}

	@Override
	public boolean writeOff(String contractAddress, String privateKey, BigInteger index, BigInteger tokenId, BigInteger supply) throws Exception{
		return nftClientUtils.writeOff(contractAddress,  privateKey,  index,  tokenId,  supply);
	}

	@Override
	public boolean batchBurn(String contractAddress, String privateKey, BigInteger[] tokenIds) throws Exception{
		return nftClientUtils.batchBurn(contractAddress, privateKey, tokenIds);
	}


	@Override
	public boolean getOverTime(String contractAddress, BigInteger tokenId) throws Exception{
		return nftClientUtils.getOverTime(contractAddress,tokenId);
	}
}

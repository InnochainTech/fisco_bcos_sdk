package inno.fiscobcos.be.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import inno.fiscobcos.be.constant.Constant;
import inno.fiscobcos.be.entity.request.BatchMintDo;
import inno.fiscobcos.be.entity.request.NFTDeployDo;
import inno.fiscobcos.be.entity.response.BatchMintVo;
import inno.fiscobcos.be.entity.response.NFTDeployVo;
import inno.fiscobcos.be.service.NFTService;
import inno.fiscobcos.be.util.result.Result;
import inno.fiscobcos.be.util.result.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fisco.bcos.sdk.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/10 17:23
 */
@Api(tags = "NFT合约")
@Controller
@RequestMapping(value = "/nft")
public class NFTController {

	@Autowired
	NFTService nftService;

	@ApiOperation(value = "部署合约")
	@ResponseBody
	@PostMapping("/deploy")
	public Result<NFTDeployVo> deploy(@RequestBody NFTDeployDo nftDeploy){
		String contractAddr;
		NFTDeployVo nftDeployVo = new NFTDeployVo();
		try {
			contractAddr = nftService.deploy(nftDeploy.getPrivateKey(),nftDeploy.getName(),nftDeploy.getSymbol(),nftDeploy.getTotalSupply(),nftDeploy.getEquityLink(),nftDeploy.getCanRenew(),nftDeploy.getCanIssua(),nftDeploy.getCanWriteOff());
			if(StringUtils.isEmpty(contractAddr)){
				return ResultUtils.error(Constant.ERROR_CODE,"部署失败！！！");
			}
			nftDeployVo.setContractAddress(contractAddr);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(nftDeployVo);
	}

	@ApiOperation(value = "批量铸造")
	@ResponseBody
	@PostMapping("/batchMint")
	public Result<BatchMintVo> batchMint(@RequestBody BatchMintDo batchMintDo) {
		List<BigInteger> tokenIds = new ArrayList<>();
		BatchMintVo batchMintVo = new BatchMintVo();
		try{
			tokenIds = nftService.batchMint(batchMintDo.getContractAddress(), batchMintDo.getPrivateKey(), batchMintDo.getSupply(), batchMintDo.getTokenURI());
			if(CollectionUtils.isEmpty(tokenIds)){
				return ResultUtils.error(Constant.ERROR_CODE,"铸造失败！！！");
			}
			batchMintVo.setTokenIds(tokenIds);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(batchMintVo);
	}


	@ApiOperation(value = "批量出售")
	@ResponseBody
	@PostMapping("/batchSell")
	public Result<BatchMintVo> batchSell(@RequestBody BatchMintDo batchMintDo) {
		List<BigInteger> tokenIds = new ArrayList<>();
		BatchMintVo batchMintVo = new BatchMintVo();
		try{
			tokenIds = nftService.batchMint(batchMintDo.getContractAddress(), batchMintDo.getPrivateKey(), batchMintDo.getSupply(), batchMintDo.getTokenURI());
			if(CollectionUtils.isEmpty(tokenIds)){
				return ResultUtils.error(Constant.ERROR_CODE,"铸造失败！！！");
			}
			batchMintVo.setTokenIds(tokenIds);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(batchMintVo);
	}

	@ApiOperation(value = "续费")
	@ResponseBody
	@PostMapping("/renew")
	public Result<BatchMintVo> renew(@RequestBody BatchMintDo batchMintDo) {
		List<BigInteger> tokenIds = new ArrayList<>();
		BatchMintVo batchMintVo = new BatchMintVo();
		try{
			tokenIds = nftService.batchMint(batchMintDo.getContractAddress(), batchMintDo.getPrivateKey(), batchMintDo.getSupply(), batchMintDo.getTokenURI());
			if(CollectionUtils.isEmpty(tokenIds)){
				return ResultUtils.error(Constant.ERROR_CODE,"铸造失败！！！");
			}
			batchMintVo.setTokenIds(tokenIds);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(batchMintVo);
	}

	@ApiOperation(value = "增发")
	@ResponseBody
	@PostMapping("/addIssua")
	public Result<BatchMintVo> addIssua(@RequestBody BatchMintDo batchMintDo) {
		List<BigInteger> tokenIds = new ArrayList<>();
		BatchMintVo batchMintVo = new BatchMintVo();
		try{
			tokenIds = nftService.batchMint(batchMintDo.getContractAddress(), batchMintDo.getPrivateKey(), batchMintDo.getSupply(), batchMintDo.getTokenURI());
			if(CollectionUtils.isEmpty(tokenIds)){
				return ResultUtils.error(Constant.ERROR_CODE,"铸造失败！！！");
			}
			batchMintVo.setTokenIds(tokenIds);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(batchMintVo);
	}


	@ApiOperation(value = "核销")
	@ResponseBody
	@PostMapping("/writeOff")
	public Result<BatchMintVo> writeOff(@RequestBody BatchMintDo batchMintDo) {
		List<BigInteger> tokenIds = new ArrayList<>();
		BatchMintVo batchMintVo = new BatchMintVo();
		try{
			tokenIds = nftService.batchMint(batchMintDo.getContractAddress(), batchMintDo.getPrivateKey(), batchMintDo.getSupply(), batchMintDo.getTokenURI());
			if(CollectionUtils.isEmpty(tokenIds)){
				return ResultUtils.error(Constant.ERROR_CODE,"铸造失败！！！");
			}
			batchMintVo.setTokenIds(tokenIds);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(batchMintVo);
	}

	@ApiOperation(value = "批量销毁")
	@ResponseBody
	@PostMapping("/batchBurn")
	public Result<BatchMintVo> batchBurn(@RequestBody BatchMintDo batchMintDo) {
		List<BigInteger> tokenIds = new ArrayList<>();
		BatchMintVo batchMintVo = new BatchMintVo();
		try{
			tokenIds = nftService.batchMint(batchMintDo.getContractAddress(), batchMintDo.getPrivateKey(), batchMintDo.getSupply(), batchMintDo.getTokenURI());
			if(CollectionUtils.isEmpty(tokenIds)){
				return ResultUtils.error(Constant.ERROR_CODE,"铸造失败！！！");
			}
			batchMintVo.setTokenIds(tokenIds);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(batchMintVo);
	}
}

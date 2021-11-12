package inno.fiscobcos.be.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import inno.fiscobcos.be.constant.Constant;
import inno.fiscobcos.be.entity.request.*;
import inno.fiscobcos.be.entity.response.*;
import inno.fiscobcos.be.service.NFTService;
import inno.fiscobcos.be.util.result.Result;
import inno.fiscobcos.be.util.result.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fisco.bcos.sdk.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
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
			contractAddr = nftService.deploy(nftDeploy.getPrivateKey(),nftDeploy.getName(),nftDeploy.getSymbol(),nftDeploy.getTotalSupply(),nftDeploy.getEquityLink(),nftDeploy.getCanRenew(),nftDeploy.getCanIssua(),nftDeploy.getCanWriteOff(),nftDeploy.getWriteOffQuantity());
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
		List<BigInteger> tokenIds;
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
	public Result<BatchSellVo> batchSell(@RequestBody BatchSellDo batchSellDo) {
		BatchSellVo batchSellVo = new BatchSellVo();
		try{
			boolean sellSuccess = nftService.batchSell(batchSellDo.getContractAddress(),batchSellDo.getPrivateKey(),batchSellDo.getTokenIds(),batchSellDo.getTo(),batchSellDo.getExpirationTime());
			if(!sellSuccess){
				return ResultUtils.error(Constant.ERROR_CODE,"出售失败！！！");
			}
			batchSellVo.setSellSuccess(sellSuccess);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(batchSellVo);
	}

	@ApiOperation(value = "续费")
	@ResponseBody
	@PostMapping("/renew")
	public Result<RenewVo> renew(@RequestBody RenewDo renewDo) {
		RenewVo renewVo = new RenewVo();
		try{
			boolean renewSuccess = nftService.renew(renewDo.getContractAddress(), renewDo.getPrivateKey(), renewDo.getTokenId(),renewDo.getRenewTime() );
			if(!renewSuccess){
				return ResultUtils.error(Constant.ERROR_CODE,"续费失败！！！");
			}
			renewVo.setRenewSuccess(renewSuccess);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(renewVo);
	}

	@ApiOperation(value = "增发")
	@ResponseBody
	@PostMapping("/addIssua")
	public Result<AddIssuaVo> addIssua(@RequestBody AddIssuaDo addIssuaDo) {
		AddIssuaVo addIssuaVo = new AddIssuaVo();
		try{
			boolean addIssuaSuccess = nftService.addIssua(addIssuaDo.getContractAddress(),addIssuaDo.getPrivateKey(),addIssuaDo.getAddIssuaSupply());
			if(!addIssuaSuccess){
				return ResultUtils.error(Constant.ERROR_CODE,"增发失败！！！");
			}
			addIssuaVo.setIssuaSuccess(addIssuaSuccess);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(addIssuaVo);
	}


	@ApiOperation(value = "核销")
	@ResponseBody
	@PostMapping("/writeOff")
	public Result<WriteOffVo> writeOff(@RequestBody WriteOffDo writeOffDo) {
		WriteOffVo writeOffVo = new WriteOffVo();
		try{
			boolean writeOffSuccess = nftService.writeOff(writeOffDo.getContractAddress(), writeOffDo.getPrivateKey(), writeOffDo.getIndex(), writeOffDo.getTokenId(), writeOffDo.getSupply());
			if(!writeOffSuccess){
				return ResultUtils.error(Constant.ERROR_CODE,"核销失败！！！");
			}
			writeOffVo.setWriteOffSuccess(writeOffSuccess);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(writeOffVo);
	}

	@ApiOperation(value = "批量销毁")
	@ResponseBody
	@PostMapping("/batchBurn")
	public Result<BatchBurnVo> batchBurn(@RequestBody BatchBurnDo batchBurnDo) {
		BatchBurnVo batchBurnVo = new BatchBurnVo();
		try{
			boolean burnSuccess = nftService.batchBurn(batchBurnDo.getContractAddress(), batchBurnDo.getPrivateKey(), batchBurnDo.getTokenIds());
			if(!burnSuccess){
				return ResultUtils.error(Constant.ERROR_CODE,"销毁失败！！！");
			}
			batchBurnVo.setBurnSuccess(burnSuccess);
		}catch (Exception exception){
			return ResultUtils.error(Constant.ERROR_CODE,exception.getMessage());
		}

		return ResultUtils.success(batchBurnVo);
	}
}

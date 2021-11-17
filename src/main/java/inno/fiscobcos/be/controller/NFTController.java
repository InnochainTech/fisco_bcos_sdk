package inno.fiscobcos.be.controller;

import inno.fiscobcos.be.constant.Config;
import inno.fiscobcos.be.constant.Constant;
import inno.fiscobcos.be.entity.ResponseVo;
import inno.fiscobcos.be.entity.request.*;
import inno.fiscobcos.be.entity.response.*;
import inno.fiscobcos.be.service.NFTService;
import inno.fiscobcos.be.util.EncrypeUtils;
import inno.fiscobcos.be.util.result.Result;
import inno.fiscobcos.be.util.result.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 * @author peifeng
 * @date 2021/11/10 17:23
 */
@Api(tags = "NFT合约")
@Controller
@RequestMapping(value = "/nft")
public class NFTController {

	@Autowired
	private NFTService nftService;

	@Autowired
	private Config config;

	@ApiOperation(value = "部署合约")
	@ResponseBody
	@PostMapping("/deploy")
	public Result<NFTDeployVo> deploy(@RequestBody NFTDeployDo nftDeploy){
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,nftDeploy.getPrivateKey());
		} catch (Exception exception) {
			return ResultUtils.error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		return nftService.deploy(realPrivateKey,nftDeploy.getName(),nftDeploy.getSymbol(),nftDeploy.getTotalSupply(),nftDeploy.getEquityLink(),nftDeploy.getCanRenew(),nftDeploy.getCanWriteOff(),nftDeploy.getWriteOffQuantity(),nftDeploy.getInitialDeadline());


	}

	@ApiOperation(value = "批量铸造")
	@ResponseBody
	@PostMapping("/batchMint")
	public Result<BatchMintVo> batchMint(@RequestBody BatchMintDo batchMintDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,batchMintDo.getPrivateKey());
		} catch (Exception exception) {
			return ResultUtils.error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		return nftService.batchMint(batchMintDo.getContractAddress(), realPrivateKey, batchMintDo.getSupply(), batchMintDo.getTokenURI());
	}

	@ApiOperation(value = "批量出售")
	@ResponseBody
	@PostMapping("/batchSell")
	public Result<BatchSellVo> batchSell(@RequestBody BatchSellDo batchSellDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,batchSellDo.getPrivateKey());
		} catch (Exception exception) {
			return ResultUtils.error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		return nftService.batchSell(batchSellDo.getContractAddress(),realPrivateKey,batchSellDo.getTokenIds(),batchSellDo.getTo(),batchSellDo.getExpirationTime());
	}

	@ApiOperation(value = "批量转账")
	@ResponseBody
	@PostMapping("/batchTransfer")
	public Result<BatchTransferVo> batchSell(@RequestBody BatchTransferDo batchTransferDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,batchTransferDo.getPrivateKey());
		} catch (Exception exception) {
			return ResultUtils.error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		return nftService.batchTransfer(batchTransferDo.getContractAddress(),realPrivateKey,batchTransferDo.getTokenIds(),batchTransferDo.getTo());

	}

	@ApiOperation(value = "续费")
	@ResponseBody
	@PostMapping("/renew")
	public Result<RenewVo> renew(@RequestBody RenewDo renewDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,renewDo.getPrivateKey());
		} catch (Exception exception) {
			return ResultUtils.error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		return nftService.renew(renewDo.getContractAddress(), realPrivateKey, renewDo.getTokenId(),renewDo.getRenewTime() );
	}

	/*@ApiOperation(value = "增发")
	@ResponseBody
	@PostMapping("/addIssua")
	public Result<ResponseVo<AddIssuaVo>> addIssua(@RequestBody AddIssuaDo addIssuaDo) {
		return nftService.addIssua(addIssuaDo.getContractAddress(),addIssuaDo.getPrivateKey(),addIssuaDo.getAddIssuaSupply());
	}*/

	@ApiOperation(value = "核销")
	@ResponseBody
	@PostMapping("/writeOff")
	public Result<WriteOffVo> writeOff(@RequestBody WriteOffDo writeOffDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,writeOffDo.getPrivateKey());
		} catch (Exception exception) {
			return ResultUtils.error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		return nftService.writeOff(writeOffDo.getContractAddress(), realPrivateKey, writeOffDo.getIndex(), writeOffDo.getTokenId(), writeOffDo.getSupply());
	}

	@ApiOperation(value = "批量销毁")
	@ResponseBody
	@PostMapping("/batchBurn")
	public Result<BatchBurnVo> batchBurn(@RequestBody BatchBurnDo batchBurnDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,batchBurnDo.getPrivateKey());
		} catch (Exception exception) {
			return ResultUtils.error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		return nftService.batchBurn(batchBurnDo.getContractAddress(), realPrivateKey, batchBurnDo.getTokenIds());
	}


}

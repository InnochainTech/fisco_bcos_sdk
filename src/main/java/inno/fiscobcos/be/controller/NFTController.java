package inno.fiscobcos.be.controller;

import inno.fiscobcos.be.constant.Config;
import inno.fiscobcos.be.constant.Constant;
import inno.fiscobcos.be.entity.request.*;
import inno.fiscobcos.be.entity.response.*;
import inno.fiscobcos.be.service.NFTService;
import inno.fiscobcos.be.util.EncrypeUtils;
import inno.fiscobcos.be.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * @author peifeng
 * @date 2021/11/10 17:23
 */
@Api(tags = "NFT合约")
@Controller
@Validated
@RequestMapping(value = "/nft")
public class NFTController {

	@Autowired
	private NFTService nftService;

	@Autowired
	private Config config;

	@ApiOperation(value = "部署合约")
	@ResponseBody
	@PostMapping("/deploy")
	public Result<NFTDeployVo> deploy(@Validated @RequestBody NFTDeployDo nftDeploy){
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,nftDeploy.getPrivateKey());
		} catch (Exception exception) {
			return new Result().error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		List<BigInteger> types = new ArrayList<>();
		List<BigInteger> supply = new ArrayList<>();
		for(InitWriteOffDo initWriteOffDo : nftDeploy.getInitWriteOff()){
			types.add(initWriteOffDo.getType());
			supply.add(initWriteOffDo.getSupply());
		}
		if(StringUtils.isEmpty(nftDeploy.getEquityLink())){
			nftDeploy.setEquityLink("");
		}
		return nftService.deploy(realPrivateKey,nftDeploy.getName(),nftDeploy.getSymbol(),nftDeploy.getTotalSupply(),nftDeploy.getEquityLink(),nftDeploy.getCanRenew(),nftDeploy.getCanWriteOff(),types,supply,nftDeploy.getInitialDeadline());
	}

	@ApiOperation(value = "批量铸造")
	@ResponseBody
	@PostMapping("/batchMint")
	public Result<BatchMintVo> batchMint(@Validated @RequestBody BatchMintDo batchMintDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,batchMintDo.getPrivateKey());
		} catch (Exception exception) {
			return new Result().error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		return nftService.batchMint(batchMintDo.getContractAddress(), realPrivateKey, batchMintDo.getSupply(), batchMintDo.getTokenURI());
	}

	@ApiOperation(value = "批量出售")
	@ResponseBody
	@PostMapping("/batchSell")
	public Result<BatchSellVo> batchSell(@Validated @RequestBody BatchSellDo batchSellDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,batchSellDo.getPrivateKey());
		} catch (Exception exception) {
			return new Result().error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		if(StringUtils.isEmpty(batchSellDo.getExpirationTime())){
			batchSellDo.setExpirationTime(new BigInteger("0"));
		}
		return nftService.batchSell(batchSellDo.getContractAddress(),realPrivateKey,batchSellDo.getTokenIds(),batchSellDo.getTo(),batchSellDo.getExpirationTime());
	}

	@ApiOperation(value = "批量转账")
	@ResponseBody
	@PostMapping("/batchTransfer")
	public Result<BatchTransferVo> batchSell(@Validated @RequestBody BatchTransferDo batchTransferDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,batchTransferDo.getPrivateKey());
		} catch (Exception exception) {
			return new Result().error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		return nftService.batchTransfer(batchTransferDo.getContractAddress(),realPrivateKey,batchTransferDo.getTokenIds(),batchTransferDo.getTo());

	}

	@ApiOperation(value = "续费")
	@ResponseBody
	@PostMapping("/renew")
	public Result<RenewVo> renew(@Validated @RequestBody RenewDo renewDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,renewDo.getPrivateKey());
		} catch (Exception exception) {
			return new Result().error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
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
	public Result<WriteOffVo> writeOff(@RequestBody @Valid WriteOffDo writeOffDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,writeOffDo.getPrivateKey());
		} catch (Exception exception) {
			return new Result().error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		return nftService.writeOff(writeOffDo.getContractAddress(), realPrivateKey, writeOffDo.getType(), writeOffDo.getTokenId(), writeOffDo.getSupply());
	}

	@ApiOperation(value = "批量销毁")
	@ResponseBody
	@PostMapping("/batchBurn")
	public Result<BatchBurnVo> batchBurn(@Validated @RequestBody BatchBurnDo batchBurnDo) {
		String realPrivateKey;
		try{
			realPrivateKey = EncrypeUtils.AESDncode(config.encrypeKey,batchBurnDo.getPrivateKey());
		} catch (Exception exception) {
			return new Result().error(Constant.ERROR_CODE,Constant.AESDNCODE_ERROR);
		}
		return nftService.batchBurn(batchBurnDo.getContractAddress(), realPrivateKey, batchBurnDo.getTokenIds());
	}


}

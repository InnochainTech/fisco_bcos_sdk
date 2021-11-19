package inno.fiscobcos.be.controller;

import inno.fiscobcos.be.constant.Config;
import inno.fiscobcos.be.constant.Constant;
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

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;

/**
 * @author peifeng
 * @date 2021/11/10 17:23
 */
@Api(tags = "NFT合约(查询)")
@Controller
@RequestMapping(value = "/nft")
public class NFTGetController {

	@Autowired
	private NFTService nftService;

	@ApiOperation(value = "查看NFT是否过期")
	@ResponseBody
	@GetMapping("/getOverTime")
	public Result getOverTime(@ApiParam(name="contractAddress",value="合约地址",required = true)
							  @RequestParam(value = "contractAddress", required = true)
							  @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误")String contractAddress,
							  @ApiParam(name="tokenId",value="tokenId",required = true)
							  @RequestParam(value = "tokenId", required = true) BigInteger tokenId
	) throws Exception{
		return ResultUtils.success(nftService.getOverTime(contractAddress,tokenId));
	}

	@ApiOperation(value = "查看NFT到期时间")
	@ResponseBody
	@GetMapping("/getExpirationTime")
	public Result getExpirationTime(@ApiParam(name="contractAddress",value="合约地址",required = true)
									@RequestParam(value = "contractAddress", required = true)
									@Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress,
									@ApiParam(name="tokenId",value="tokenId",required = true)
									@RequestParam(value = "tokenId", required = true)
									@Min(1)BigInteger tokenId
	) throws Exception{
		return ResultUtils.success(nftService.getExpirationTime(contractAddress,tokenId));
	}

	@ApiOperation(value = "查看NFT允许铸造的最大数量")
	@ResponseBody
	@GetMapping("/getTotalSupply")
	public Result getTotalSupply(@ApiParam(name="contractAddress",value="合约地址",required = true)
								 @RequestParam(value = "contractAddress", required = true)
								 @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
	) throws Exception{
		return ResultUtils.success(nftService.getTotalSupply(contractAddress));
	}

	@ApiOperation(value = "查看NFT是否允许续期")
	@ResponseBody
	@GetMapping("/getCanRenew")
	public Result getCanRenew(@ApiParam(name="contractAddress",value="合约地址",required = true)
							  @RequestParam(value = "contractAddress", required = true)
							  @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
	) throws Exception{
		return ResultUtils.success(nftService.getCanRenew(contractAddress));
	}

	@ApiOperation(value = "查看NFT是否允许核销")
	@ResponseBody
	@GetMapping("/getCanWriteOff")
	public Result getCanWriteOff(@ApiParam(name="contractAddress",value="合约地址",required = true)
								 @RequestParam(value = "contractAddress", required = true)
								 @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
	) throws Exception{
		return ResultUtils.success(nftService.getCanWriteOff(contractAddress));
	}

	@ApiOperation(value = "查看NFT权益链接")
	@ResponseBody
	@GetMapping("/getEquityLink")
	public Result getEquityLink(@ApiParam(name="contractAddress",value="合约地址",required = true)
								@RequestParam(value = "contractAddress", required = true)
								@Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
	) throws Exception{
		return ResultUtils.success(nftService.getEquityLink(contractAddress));
	}

	@ApiOperation(value = "查看NFT已经铸造的数量")
	@ResponseBody
	@GetMapping("/getTokenMinted")
	public Result getTokenMinted(@ApiParam(name="contractAddress",value="合约地址",required = true)
								 @RequestParam(value = "contractAddress", required = true)
								 @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
	) throws Exception{
		return ResultUtils.success(nftService.getTokenMinted(contractAddress));
	}


	@ApiOperation(value = "查看NFT（核销功能）初始会员数量")
	@ResponseBody
	@GetMapping("/getVipSupply")
	public Result getVipSupply(@ApiParam(name="contractAddress",value="合约地址",required = true)
							   @RequestParam(value = "contractAddress", required = true)
							   @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
	) throws Exception{
		return ResultUtils.success(nftService.getVipSupply(contractAddress));
	}

	@ApiOperation(value = "查看NFT还剩余未核销的会员数量")
	@ResponseBody
	@GetMapping("/getTokenVipSupply")
	public Result getTokenVipSupply(@ApiParam(name="contractAddress",value="合约地址",required = true)
									@RequestParam(value = "contractAddress", required = true)
									@Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress,
									@ApiParam(name="tokenId",value="tokenId",required = true)
									@RequestParam(value = "tokenId", required = true)
									@Min(1)BigInteger tokenId
	) throws Exception{
		return ResultUtils.success(nftService.getTokenVipSupply(contractAddress,tokenId));
	}


	@ApiOperation(value = "查看链上当前时间戳（豪秒）")
	@ResponseBody
	@GetMapping("/getNow")
	public Result getNow(@ApiParam(name="contractAddress",value="合约地址",required = true)
						 @RequestParam(value = "contractAddress", required = true)
						 @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
	) throws Exception{
		return ResultUtils.success(nftService.getNow(contractAddress));
	}

	@ApiOperation(value = "查看合约名称")
	@ResponseBody
	@GetMapping("/getName")
	public Result getName(@ApiParam(name="contractAddress",value="合约地址",required = true)
						 @RequestParam(value = "contractAddress", required = true)
						 @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
	) throws Exception{
		return ResultUtils.success(nftService.getName(contractAddress));
	}

	@ApiOperation(value = "查看地址拥有的资产数")
	@ResponseBody
	@GetMapping("/getTokens")
	public Result getTokens(@ApiParam(name="contractAddress",value="合约地址",required = true)
									@RequestParam(value = "contractAddress", required = true)
									@Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress,
									@ApiParam(name="tokenId",value="tokenId",required = true)
									@RequestParam(value = "tokenId", required = true)
									@Min(1)BigInteger tokenId
	) throws Exception{
		return ResultUtils.success(nftService.getTokens(contractAddress,tokenId));
	}


}

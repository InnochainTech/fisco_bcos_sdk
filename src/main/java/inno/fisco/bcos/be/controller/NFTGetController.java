package inno.fisco.bcos.be.controller;

import inno.fisco.bcos.be.constant.Constant;
import inno.fisco.bcos.be.entity.request.InitWriteOffDo;
import inno.fisco.bcos.be.util.CommonUtils;
import inno.fisco.bcos.be.util.chain.NFTBcosClientWrapper;
import inno.fisco.bcos.be.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/10 17:23
 */
@Api(tags = "NFT合约(查询)")
@Controller
@RequestMapping(value = "/nft")
public class NFTGetController {

    @Autowired
    private NFTBcosClientWrapper nftBcosClientWrapper;

    @ApiOperation(value = "查看NFT是否过期")
    @ResponseBody
    @GetMapping("/getOverTime")
    public Result getOverTime(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                              @RequestParam(value = "contractAddress", required = true)
                              @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress,
                              @ApiParam(name = "tokenId", value = "tokenId", required = true)
                              @RequestParam(value = "tokenId", required = true) BigInteger tokenId
    ) throws Exception {
        CallResponse callResponse = nftBcosClientWrapper.getOverTime(contractAddress, tokenId);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        Boolean result = callResponse.getValues().equals(Constant.TRUE);
        return new Result().success(result);
    }

    @ApiOperation(value = "查看NFT到期时间")
    @ResponseBody
    @GetMapping("/getExpirationTime")
    public Result getExpirationTime(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                                    @RequestParam(value = "contractAddress", required = true)
                                    @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress,
                                    @ApiParam(name = "tokenId", value = "tokenId", required = true)
                                    @RequestParam(value = "tokenId", required = true)
                                    @Min(1) BigInteger tokenId
    ) throws Exception {

        CallResponse callResponse = nftBcosClientWrapper.getExpirationTime(contractAddress, tokenId);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        BigInteger result = new BigInteger(CommonUtils.conver(callResponse.getValues()));
        return new Result().success(result);
    }

    @ApiOperation(value = "查看NFT允许铸造的最大数量")
    @ResponseBody
    @GetMapping("/getTotalSupply")
    public Result getTotalSupply(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                                 @RequestParam(value = "contractAddress", required = true)
                                 @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
    ) throws Exception {
        CallResponse callResponse = nftBcosClientWrapper.getTotalSupply(contractAddress);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        BigInteger result = new BigInteger(CommonUtils.conver(callResponse.getValues()));
        return new Result().success(result);
    }

    @ApiOperation(value = "查看NFT是否允许续期")
    @ResponseBody
    @GetMapping("/getCanRenew")
    public Result getCanRenew(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                              @RequestParam(value = "contractAddress", required = true)
                              @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
    ) throws Exception {
        CallResponse callResponse = nftBcosClientWrapper.getCanRenew(contractAddress);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        Boolean result = callResponse.getValues().equals(Constant.TRUE);
        return new Result().success(result);
    }

    @ApiOperation(value = "查看NFT是否允许核销")
    @ResponseBody
    @GetMapping("/getCanWriteOff")
    public Result getCanWriteOff(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                                 @RequestParam(value = "contractAddress", required = true)
                                 @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
    ) throws Exception {
        CallResponse callResponse = nftBcosClientWrapper.getCanWriteOff(contractAddress);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        Boolean result = callResponse.getValues().equals(Constant.TRUE);
        return new Result().success(result);
    }

    @ApiOperation(value = "查看NFT权益链接")
    @ResponseBody
    @GetMapping("/getEquityLink")
    public Result getEquityLink(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                                @RequestParam(value = "contractAddress", required = true)
                                @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
    ) throws Exception {
        CallResponse callResponse = nftBcosClientWrapper.getEquityLink(contractAddress);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        String result = CommonUtils.conver(callResponse.getValues());
        return new Result().success(result);
    }

    @ApiOperation(value = "查看NFT已经铸造的数量")
    @ResponseBody
    @GetMapping("/getTokenMinted")
    public Result getTokenMinted(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                                 @RequestParam(value = "contractAddress", required = true)
                                 @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
    ) throws Exception {
        CallResponse callResponse = nftBcosClientWrapper.getTokenMinted(contractAddress);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        BigInteger result = new BigInteger(CommonUtils.conver(callResponse.getValues()));
        return new Result().success(result);
    }


    @ApiOperation(value = "查看NFT（核销功能）初始会员数量")
    @ResponseBody
    @GetMapping("/getVipSupply")
    public Result getVipSupply(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                               @RequestParam(value = "contractAddress", required = true)
                               @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
    ) throws Exception {
        CallResponse callResponse = nftBcosClientWrapper.getVipSupply(contractAddress);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        List<InitWriteOffDo> result = new ArrayList<>();
        List<Object> returnObject = callResponse.getReturnObject();
        String[] typesStr = CommonUtils.conver(returnObject.get(0).toString()).split(",");
        String[] supplyStr = CommonUtils.conver(returnObject.get(1).toString()).split(",");
        for (int i = 0; i < typesStr.length; i++) {
            InitWriteOffDo initWriteOffDo = new InitWriteOffDo(new BigInteger(typesStr[i].replaceAll(" ", "")), new BigInteger(supplyStr[i].replaceAll(" ", "")));
            result.add(initWriteOffDo);
        }
        return new Result().success(result);
    }

    @ApiOperation(value = "查看NFT还剩余未核销的会员数量")
    @ResponseBody
    @GetMapping("/getTokenVipSupply")
    public Result getTokenVipSupply(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                                    @RequestParam(value = "contractAddress", required = true)
                                    @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress,
                                    @ApiParam(name = "tokenId", value = "tokenId", required = true)
                                    @RequestParam(value = "tokenId", required = true)
                                    @Min(1) BigInteger tokenId
    ) throws Exception {
        CallResponse callResponse = nftBcosClientWrapper.getTokenVipSupply(contractAddress, tokenId);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        List<InitWriteOffDo> result = new ArrayList<>();
        List<Object> returnObject = callResponse.getReturnObject();
        String[] typesStr = CommonUtils.conver(returnObject.get(0).toString()).split(",");
        String[] supplyStr = CommonUtils.conver(returnObject.get(1).toString()).split(",");
        for (int i = 0; i < typesStr.length; i++) {
            InitWriteOffDo initWriteOffDo = new InitWriteOffDo(new BigInteger(typesStr[i].replaceAll(" ", "")), new BigInteger(supplyStr[i].replaceAll(" ", "")));
            result.add(initWriteOffDo);
        }
        return new Result().success(result);
    }


    @ApiOperation(value = "查看链上当前时间戳（豪秒）")
    @ResponseBody
    @GetMapping("/getNow")
    public Result getNow(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                         @RequestParam(value = "contractAddress", required = true)
                         @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
    ) throws Exception {
        CallResponse callResponse = nftBcosClientWrapper.getNow(contractAddress);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        BigInteger result = new BigInteger(CommonUtils.conver(callResponse.getValues()));
        return new Result().success(result);
    }

    @ApiOperation(value = "查看合约名称")
    @ResponseBody
    @GetMapping("/getName")
    public Result getName(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                          @RequestParam(value = "contractAddress", required = true)
                          @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress
    ) throws Exception {
        CallResponse callResponse = nftBcosClientWrapper.getName(contractAddress);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        String result = CommonUtils.conver(callResponse.getValues());
        return new Result().success(result);
    }

    @ApiOperation(value = "查看地址拥有的资产数")
    @ResponseBody
    @GetMapping("/getTokens")
    public Result getTokens(@ApiParam(name = "contractAddress", value = "合约地址", required = true)
                            @RequestParam(value = "contractAddress", required = true)
                            @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误") String contractAddress,
                            @ApiParam(name = "accountAddress", value = "查询地址", required = true)
                            @RequestParam(value = "accountAddress", required = true)
                            @Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "查询地址格式错误") String accountAddress
    ) throws Exception {
        CallResponse callResponse = nftBcosClientWrapper.getTokens(contractAddress, accountAddress);
        if (!callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            return new Result().error(Constant.ERROR_CODE, callResponse.getReturnMessage());
        }
        List<BigInteger> result = new ArrayList<>();
        if (callResponse.getReturnMessage().equals(Constant.SUCCESS)) {
            String[] arr = CommonUtils.conver(callResponse.getValues()).split(",");
            for (String s : arr) {
                if (!StringUtils.isEmpty(s)) {
                    result.add(new BigInteger(s));
                }
            }
        }
        return new Result().success(result);
    }


}

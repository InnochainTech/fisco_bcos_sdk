package inno.fiscobcos.be.controller;

import inno.fiscobcos.be.entity.Wallet;
import inno.fiscobcos.be.util.chain.ClientUtils;
import inno.fiscobcos.be.util.result.Result;
import inno.fiscobcos.be.util.result.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author peifeng
 * @Date 2021/7/19 9:41
 */
@Api(tags = "心跳API")
@Controller
@CrossOrigin
public class HeartBeatController {

    @ApiOperation(value = "监听心跳")
    @ResponseBody
    @GetMapping("/heartBeat")
    public Result heartBeat(){
        return ResultUtils.success(true);
    }



}

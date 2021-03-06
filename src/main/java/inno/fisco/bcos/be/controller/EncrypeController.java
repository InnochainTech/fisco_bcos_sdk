package inno.fisco.bcos.be.controller;

import inno.fisco.bcos.be.constant.Config;
import inno.fisco.bcos.be.util.EncrypeUtils;
import inno.fisco.bcos.be.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @Author peifeng
 * @Date 2021/7/19 9:41
 */
@Api(tags = "加密工具")
@Controller
@CrossOrigin
@RequestMapping(value = "/EncrypeUtils")
public class EncrypeController {

    @Autowired
    private Config config;

    @ApiOperation(value = "加密私钥")
    @ResponseBody
    @GetMapping("/AESEncode")
    public Result AESEncode(@ApiParam(name="privateKey",value="未加密的私钥",required = true)
                            @RequestParam(value = "privateKey", required = true)  String privateKey
    ) throws Exception {

        return new Result().success(EncrypeUtils.AESEncode(config.encrypeKey,privateKey));
    }

    @ApiOperation(value = "解密私钥")
    @ResponseBody
    @GetMapping("/AESDncode")
    public Result AESDncode(@ApiParam(name="privateKey",value="加密后的私钥",required = true)
                            @RequestParam(value = "privateKey", required = true)  String privateKey
    ) throws Exception {

        return new Result().success(EncrypeUtils.AESDncode(config.encrypeKey,privateKey));
    }

}

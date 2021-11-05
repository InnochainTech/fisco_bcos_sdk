package inno.fiscobcos.be.controller;

import inno.fiscobcos.be.util.ClientUtils;
import inno.fiscobcos.be.util.result.Result;
import inno.fiscobcos.be.util.result.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author peifeng
 * @Date 2021/7/19 9:41
 */
@Api(tags = "账户")
@Controller
@CrossOrigin
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private ClientUtils clientUtils;

    @ApiOperation(value = "创建新地址", notes = "")
    @ResponseBody
    @GetMapping("/createAccount")
    public Result createAccount(){
        Map<String,String> resultmap = new HashMap<>();
        CryptoKeyPair account = clientUtils.createAccount("");
        resultmap.put("privateKey",account.getHexPrivateKey());
        resultmap.put("publicKey",account.getHexPublicKey());
        resultmap.put("address",account.getAddress());
        return ResultUtils.success(resultmap);
    }

}

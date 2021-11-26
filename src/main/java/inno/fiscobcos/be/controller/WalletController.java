package inno.fiscobcos.be.controller;

import inno.fiscobcos.be.entity.Wallet;
import inno.fiscobcos.be.util.chain.ClientUtils;
import inno.fiscobcos.be.util.result.Result;
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
@Api(tags = "钱包")
@Controller
@CrossOrigin
@RequestMapping(value = "/wallet")
public class WalletController {

    @Autowired
    private ClientUtils clientUtils;

    @ApiOperation(value = "创建新钱包")
    @ResponseBody
    @GetMapping("/createWallet")
    public Result<Wallet> createWallet(@ApiParam(name="accountId",value="不能使用特殊符号",required = true)
                                       @RequestParam(value = "accountId", required = true) @Pattern(regexp = "^[a-z0-9A-Z]+$", message = "参数类型错误") String accountId
    ){
        // OkHttps.async()
        //         .tag()
        //         .bind()
        //         .setOnResponse(res ->{
        //             //TODO: 回调响应
        //         })
        //         .get();
        CryptoKeyPair account = clientUtils.createAccount("");
        Wallet wallet = new Wallet(account.getHexPrivateKey(),account.getHexPublicKey(),account.getAddress());
        return new Result().success(wallet);
    }



}

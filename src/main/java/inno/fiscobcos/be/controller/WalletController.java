package inno.fiscobcos.be.controller;

import inno.fiscobcos.be.entity.Wallet;
import inno.fiscobcos.be.util.chain.ClientUtils;
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
    public Result<Wallet> createWallet(){
        CryptoKeyPair account = clientUtils.createAccount("");
        Wallet wallet = new Wallet(account.getHexPrivateKey(),account.getHexPublicKey(),account.getAddress());
        return ResultUtils.success(wallet);
    }

}

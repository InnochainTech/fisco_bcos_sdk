package inno.fisco.bcos.be.controller;

import inno.fisco.bcos.be.constant.Config;
import inno.fisco.bcos.be.entity.Wallet;
import inno.fisco.bcos.be.util.chain.BcosClientWrapper;
import inno.fisco.bcos.be.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author peifeng
 * @Date 2021/7/19 9:41
 */
@Api(tags = "钱包")
@Controller
@CrossOrigin
@RequestMapping(value = "/wallet")
public class WalletController {

    @Autowired
    private BcosClientWrapper bcosClientWrapper;

    @Autowired
    private Config config;

    @ApiOperation(value = "创建新钱包")
    @ResponseBody
    @GetMapping("/createWallet")
    public Result<Wallet> createWallet(@ApiParam(name = "accountId", value = "用户Id", required = true)
                                               String accountId
    ) {
        CryptoKeyPair account = bcosClientWrapper.createAccount("");
        Wallet wallet = new Wallet(account.getHexPrivateKey(), account.getHexPublicKey(), account.getAddress());
        return new Result<>().success(wallet);
    }
}

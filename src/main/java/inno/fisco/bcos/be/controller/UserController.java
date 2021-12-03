package inno.fisco.bcos.be.controller;

import com.alibaba.fastjson.JSON;
import inno.fisco.bcos.be.constant.Config;
import inno.fisco.bcos.be.constant.Constant;
import inno.fisco.bcos.be.entity.ResVo;
import inno.fisco.bcos.be.entity.Wallet;
import inno.fisco.bcos.be.entity.usesign.request.NewUserReq;
import inno.fisco.bcos.be.entity.usesign.response.ResNewUser;
import inno.fisco.bcos.be.util.AesUtils;
import inno.fisco.bcos.be.util.OkHttpUtils;
import inno.fisco.bcos.be.util.chain.BcosClientWrapper;
import inno.fisco.bcos.be.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author peifeng
 * @Date 2021/7/19 9:41
 */
@Api(tags = "用户")
@Controller
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private BcosClientWrapper bcosClientWrapper;

    @Autowired
    private Config config;

    @ApiOperation(value = "新增用户")
    @ResponseBody
    @PostMapping("/newUser")
    public Result<ResNewUser> newUser(@Validated @RequestBody NewUserReq reqNewUser
    ){

        CryptoKeyPair account = bcosClientWrapper.createAccount("");
        Wallet wallet = new Wallet(account.getHexPrivateKey(),account.getHexPublicKey(),account.getAddress());
        String url = Constant.LOCAL_WEBASESIGN_URL + Constant.USER_NEWUSER_URL;
        final String result = OkHttpUtils.builder().url(url)
                // 有参数的话添加参数，可多个
                .addPostParam("appId", reqNewUser.getAppId())
                .addPostParam("encryptType", config.bcosEncryptType)
                .addPostParam("privateKey", AesUtils.aesEncrypt(wallet.getPrivateKey()))
                .addPostParam("signUserId", reqNewUser.getSignUserId())
                // 也可以添加多个
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .post(true)
                // 可选择是同步请求还是异步请求
                //.async();
                .sync();

        ResVo resVo = JSON.parseObject(result, ResVo.class);
        if(resVo.getMessage().equals(Constant.SUCCESS_1)){
            ResNewUser resNewUser = JSON.parseObject(JSON.toJSONString(resVo.getData()),ResNewUser.class);
            resNewUser.setAddress(wallet.getAddress());
            resNewUser.setPublicKey(wallet.getPublicKey());
            return new Result().success(resNewUser);
        }else{
            return new Result().error(resVo.getCode(),resVo.getMessage());
        }

    }



}

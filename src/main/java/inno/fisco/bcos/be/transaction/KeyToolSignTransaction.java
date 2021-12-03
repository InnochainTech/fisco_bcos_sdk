package inno.fisco.bcos.be.transaction;

import com.alibaba.fastjson.JSON;
import inno.fisco.bcos.be.constant.Constant;
import inno.fisco.bcos.be.entity.usesign.response.ResNFTDeploy;
import inno.fisco.bcos.be.util.AesUtils;
import inno.fisco.bcos.be.util.OkHttpUtils;
import inno.fisco.bcos.be.entity.ResVo;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.utils.Numeric;

public class KeyToolSignTransaction implements ISignTransaction {
    public KeyToolSignTransaction(CryptoSuite cryptoSuite) {
        setCryptoSuite(cryptoSuite);
    }

    public CryptoSuite getCryptoSuite() {
        return cryptoSuite;
    }

    public void setCryptoSuite(CryptoSuite cryptoSuite) {
        this.cryptoSuite = cryptoSuite;
    }

    CryptoSuite cryptoSuite;

    /**
     * （案例注释）纯外部实现，这里比如是个http,rpc接口什么的，这里的实现是调用了java-sdk签名接口，可以一次对接成功
     * 调用签名服务进行数据签名
     * @param dataToSign
     * @param signUserId
     * @return
     */
    public String signData(byte[] dataToSign, String signUserId) {

        String url = Constant.LOCAL_WEBASESIGN_URL + Constant.SIGN_URL;
        final String result = OkHttpUtils.builder().url(url)
                // 有参数的话添加参数，可多个
                .addPostParam("encodedDataStr", Numeric.toHexString(dataToSign))
                .addPostParam("signUserId", signUserId)
                // 也可以添加多个
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .post(true)
                // 可选择是同步请求还是异步请求
                //.async();
                .sync();

        ResVo resVo = JSON.parseObject(result, ResVo.class);

        ResNFTDeploy resNFTDeploy = new ResNFTDeploy();
        if(resVo.getMessage().equals(Constant.SUCCESS_1)){
            resNFTDeploy = JSON.parseObject(JSON.toJSONString(resVo.getData()),ResNFTDeploy.class);
        }
        return AesUtils.aesDecrypt(resNFTDeploy.getSignDataStr());

        // CryptoKeyPair cryptoKeyPair = cryptoSuite.getCryptoKeyPair();
        // System.out.println("privateKey:"+cryptoKeyPair.getHexPrivateKey());
        // // 这里cryptoSuite的实现已经自动适配国密和ECDSA，不需要用crytoType了
        // SignatureResult signatureResult = cryptoSuite.sign(dataToSign, cryptoKeyPair);
        // System.out.println(
        //         "signUserId:"
        //                 + signUserId
        //                 + ",signData -> signature:"
        //                 + signatureResult.convertToString());
        // return signatureResult.convertToString();
    }

    @Override
    public String requestForSign(byte[] rawTxHash, int cryptoType, String signUserId) {
        String signedDataString = signData(rawTxHash, signUserId);
        return signedDataString;
    }

    /**
     *  模拟异步调用，demo代码比较简单，就本地直接同步回调了，可以改成启动一个签名线程
     * @param dateToSign 待签名的交易数据
     * @param cryptoType ECDSA=0,SM=1等,也可以约定其他，可修改类型以扩展更多参数
     * @param signUserId 签名用户在签名服务中注册的唯一标识
     * @param callback 回调
     */
    @Override
    public void requestForSignAsync(byte[] dateToSign, int cryptoType, String signUserId, ISignedTransactionCallback callback) {
        String signatureStr = requestForSign(dateToSign, cryptoType, signUserId);
        if (callback != null) {
            callback.handleSignedTransaction(signatureStr);
        }
    }
}

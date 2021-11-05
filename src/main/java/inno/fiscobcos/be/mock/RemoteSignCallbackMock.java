package inno.fiscobcos.be.mock;

import org.fisco.bcos.sdk.crypto.signature.SignatureResult;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionWithRemoteSignProcessor;
import org.fisco.bcos.sdk.transaction.model.po.RawTransaction;
import org.fisco.bcos.sdk.transaction.signer.RemoteSignCallbackInterface;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author peifeng
 * @Date 2021/7/30 16:09
 */
public class RemoteSignCallbackMock implements RemoteSignCallbackInterface {

    AssembleTransactionWithRemoteSignProcessor assembleTransactionWithRemoteSignProcessor;
    RawTransaction rawTransaction;

    public RemoteSignCallbackMock(
            AssembleTransactionWithRemoteSignProcessor assembleTransactionWithRemoteSignProcessor,
            RawTransaction rawTransaction) {
        this.assembleTransactionWithRemoteSignProcessor =
                assembleTransactionWithRemoteSignProcessor;
        this.rawTransaction = rawTransaction;
    }

    /**
     * 签名结果回调的实现
     *
     * @param signature 签名服务回调返回的签名结果串
     * @return *
     */
    @Override
    public int handleSignedTransaction(SignatureResult signature) {
        System.out.println(System.currentTimeMillis() + " SignatureResult: " + signature.convertToString());
        // 完成了交易签名后，将其发送出去
        TransactionReceipt tr =
                assembleTransactionWithRemoteSignProcessor.signAndPush(
                        rawTransaction, signature.convertToString());
        return 0;
    }

}
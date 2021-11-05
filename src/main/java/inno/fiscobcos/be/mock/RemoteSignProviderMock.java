package inno.fiscobcos.be.mock;

import org.fisco.bcos.sdk.crypto.signature.SignatureResult;
import org.fisco.bcos.sdk.transaction.signer.RemoteSignCallbackInterface;
import org.fisco.bcos.sdk.transaction.signer.RemoteSignProviderInterface;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author peifeng
 * @Date 2021/7/30 16:14
 */
public class RemoteSignProviderMock implements RemoteSignProviderInterface {
    @Override
    public SignatureResult requestForSign(byte[] dataToSign, int cryptoType) {

        return null;
    }

    @Override
    public void requestForSignAsync(byte[] dataToSign, int cryptoType, RemoteSignCallbackInterface callback) {

    }
}

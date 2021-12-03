package inno.fisco.bcos.be.transaction;

public interface ISignTransaction {

    /**
     * 同步的签名接口，不需要传入回调，直接返回签名结果
     * @param rawTxHash 交易hash
     * @param cryptoType ECDSA=0,SM=1等,也可以约定其他，可修改类型以扩展更多参数
     * @param signUserId 签名用户在签名服务中注册的唯一标识
     * @return
     */
    public String requestForSign(byte[] rawTxHash, int cryptoType, String signUserId);

    /**
     * 异步的签名接口，传入回调，当远程调用签名服务时，可以采用异步回调风格，避免堵塞
     * @param dateToSign 待签名的交易数据
     * @param cryptoType ECDSA=0,SM=1等,也可以约定其他，可修改类型以扩展更多参数
     * @param signUserId 签名用户在签名服务中注册的唯一标识
     * @param callback 回调
     */
    public void requestForSignAsync(
            byte[] dateToSign, int cryptoType, String signUserId, ISignedTransactionCallback callback);
}

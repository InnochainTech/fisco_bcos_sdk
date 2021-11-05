package inno.fiscobcos.be.util;

import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.model.gas.DefaultGasProvider;
import org.fisco.bcos.sdk.transaction.model.po.RawTransaction;
import org.fisco.bcos.sdk.transaction.tools.ContractLoader;

import java.math.BigInteger;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author peifeng
 * @Date 2021/7/27 16:07
 */
public class MyAssembleTransactionProcessor extends AssembleTransactionProcessor {
    public MyAssembleTransactionProcessor(Client client, CryptoKeyPair cryptoKeyPair, Integer groupId, String chainId, ContractLoader contractLoader) {
        super(client, cryptoKeyPair, groupId, chainId, contractLoader);
    }

    public MyAssembleTransactionProcessor(Client client, CryptoKeyPair cryptoKeyPair, Integer groupId, String chainId, String contractName, String abi, String bin) {
        super(client, cryptoKeyPair, groupId, chainId, contractName, abi, bin);
    }

    @Override
    public String createSignedTransaction(String to, String data, CryptoKeyPair cryptoKeyPair) {
        RawTransaction rawTransaction =
                transactionBuilder.createTransaction(
                        DefaultGasProvider.GAS_PRICE,
                        BigInteger.valueOf(1_500_000_000),
                        to,
                        data,
                        BigInteger.ZERO,
                        new BigInteger(this.chainId),
                        BigInteger.valueOf(this.groupId),
                        "");
        return transactionEncoder.encodeAndSign(rawTransaction, cryptoKeyPair);
    }
}

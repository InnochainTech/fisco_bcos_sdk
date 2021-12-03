package inno.fisco.bcos.be.util.chain;

import inno.fisco.bcos.be.constant.Config;
import inno.fisco.bcos.be.transaction.MyAssembleTransactionProcessor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.tools.ContractLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @Description: 连接链上 工具类
 * @Author peifeng
 * @Date 2021/7/16 15:50
 */
@Component
@Data
public class BcosClientWrapper {
    static Logger logger = LoggerFactory.getLogger(BcosClientWrapper.class);

    private BcosSDK bcosSDK;

    private Client client;

    private AssembleTransactionProcessor transactionProcessor;

    private CryptoSuite txCryptoSuite;

    private ContractLoader contractLoader;

    @Autowired
    private Config config;

    @PostConstruct
    public void initialize() throws Exception {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext(config.bcosConfig);

        bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(config.bcosGroupId);

        String eccPrivateKeySample =
                "28018238ac7eec853401dfc3f31133330e78ac27a2f53481270083abb1a126f9";

        txCryptoSuite = new CryptoSuite(client.getCryptoType());
        txCryptoSuite.createKeyPair(eccPrivateKeySample);
        txCryptoSuite = client.getCryptoSuite();

        transactionProcessor = getAssembleTransactionProcessor(eccPrivateKeySample);

        contractLoader = new ContractLoader(config.abiFilePath, config.binFilePath);
    }

    public CryptoKeyPair createAccount(String privateKey){
        if(StringUtils.isEmpty(privateKey)){
            return client.getCryptoSuite().createKeyPair();
        }
        return client.getCryptoSuite().createKeyPair(privateKey);
    }


    protected AssembleTransactionProcessor getAssembleTransactionProcessor(String privateKey) throws Exception {
        Pair<String, Integer> pair = TransactionProcessorFactory.getChainIdAndGroupId(client);
        ContractLoader contractLoader = new ContractLoader(config.abiFilePath, config.binFilePath);
        return new MyAssembleTransactionProcessor(client, createAccount(privateKey), pair.getRight(), pair.getLeft(), contractLoader);
    }






}

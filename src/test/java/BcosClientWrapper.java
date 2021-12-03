import inno.fisco.bcos.be.constant.Config;
import lombok.Data;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.tools.ContractLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 *  @Author peifeng
 *  @Date 2021/11/30 17:50
 */
@Component
@Data
public class BcosClientWrapper {

    @Autowired
    Config config;

    private BcosSDK bcosSDK;

    private Client client;
    private CryptoSuite txCryptoSuite;

    public AssembleTransactionProcessor transactionProcessor;
    public ContractLoader contractLoader;

    public void init(int groupId) throws Exception {
        // 初始化BcosSDK对象
        // String realPath =BcosClientWrapper.class.getClassLoader().getResource("").getFile();
        // System.out.println("运行目录: " + realPath);
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:fisco-config-dev.xml");
        bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(Integer.valueOf(groupId));

        String eccPrivateKeySample =
                "28018238ac7eec853401dfc3f31133330e78ac27a2f53481270083abb1a126f9";

        txCryptoSuite = new CryptoSuite(client.getCryptoType());
        txCryptoSuite.createKeyPair(eccPrivateKeySample);
        txCryptoSuite = client.getCryptoSuite();

        transactionProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        client, txCryptoSuite.getCryptoKeyPair(), config.abiFilePath, config.binFilePath);

        contractLoader = new ContractLoader(config.abiFilePath, config.binFilePath);
    }

    public void finish() {
        bcosSDK.stopAll();
    }

}

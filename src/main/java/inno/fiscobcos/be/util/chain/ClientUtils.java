package inno.fiscobcos.be.util.chain;

import inno.fiscobcos.be.util.MyAssembleTransactionProcessor;
import org.apache.commons.lang3.tuple.Pair;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.client.protocol.response.BcosTransaction;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.tools.ContractLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ClientUtils {
    static Logger logger = LoggerFactory.getLogger(ClientUtils.class);

    protected BcosSDK bcosSDK;
    protected Client client;
    //protected CryptoKeyPair cryptoKeyPair;
    AssembleTransactionProcessor transactionProcessor;

    @PostConstruct
    public void initialize() throws Exception {
        @SuppressWarnings("resource")
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:fisco-config.xml");
        BcosSDK bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(1);
        //BlockNumber blockNumber = client.getBlockNumber();
        //System.out.println(blockNumber.getBlockNumber());

        //cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        //client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
        //logger.debug("create client for group1, account address is " + cryptoKeyPair.getAddress());
    }

    protected BcosTransaction getTransferByHash(String hashStr){
       return client.getTransactionByHash(hashStr);
    }

    public CryptoKeyPair createAccount(String privateKey){
        if(StringUtils.isEmpty(privateKey)){
            return client.getCryptoSuite().createKeyPair();
        }
        return client.getCryptoSuite().createKeyPair(privateKey);
    }

    protected Client getClient(){
        return client;
    }

    protected AssembleTransactionProcessor getAssembleTransactionProcessor(String privateKey,String abiFilePath ,
                                                                           String binFilePath) throws Exception {
        Pair<String, Integer> pair = TransactionProcessorFactory.getChainIdAndGroupId(client);
        ContractLoader contractLoader = new ContractLoader(abiFilePath, binFilePath);
        return new MyAssembleTransactionProcessor(client, createAccount(privateKey), pair.getRight(), pair.getLeft(), contractLoader);
    }

    private String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    protected String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }
}

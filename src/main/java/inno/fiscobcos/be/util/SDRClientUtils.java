package inno.fiscobcos.be.util;

import org.fisco.bcos.sdk.abi.wrapper.ABIObject;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: SDR 合约操作工具类
 * @Author peifeng
 * @Date 2021/7/16 15:54
 */
@Component
public class SDRClientUtils extends ClientUtils{

    public String deploy(String privateKey) {
        TransactionResponse response = null;
        try {
            transactionProcessor = getAssembleTransactionProcessor(privateKey, "src/main/resources/abi/", "src/main" +
                    "/resources/bin/");
            response = transactionProcessor.deployByContractLoader("SDR", new ArrayList<>());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(" deploy SDR contract failed, error message is  " + e.getMessage());
        }
        return response.getContractAddress();
    }


    public String balanceOf(String contractAddr,String address) {
        AtomicReference<String> result = new AtomicReference<>(new String());
        CallResponse callResponse = null;
        try {
            transactionProcessor = getAssembleTransactionProcessor(
                    "", "src/main/resources/abi/", "src/main/resources/bin/");
            List<Object> params = new ArrayList<>();
            params.add(address);
            callResponse = transactionProcessor.sendCallByContractLoader("SDR", contractAddr, "balanceOf",
                    params);
            callResponse.getReturnObject().forEach(returnObject->{
                result.set(returnObject+"");
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(" balanceOf exception, error message is {}", e.getMessage());

            System.out.printf(" balanceOf failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

    public String decimal(String contractAddr) {
        AtomicReference<String> result = new AtomicReference<>(new String());
        CallResponse callResponse = null;
        try {
            transactionProcessor = getAssembleTransactionProcessor(
                    "", "src/main/resources/abi/", "src/main/resources/bin/");
            List<Object> params = new ArrayList<>();
            //params.add(address);
            callResponse = transactionProcessor.sendCallByContractLoader("SDR", contractAddr, "decimal",
                    params);
            callResponse.getReturnObject().forEach(returnObject->{
                result.set(returnObject+"");
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(" decimal exception, error message is {}", e.getMessage());

            System.out.printf(" decimal failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

    public String balanceOfAvailable(String contractAddr,String address) {
        AtomicReference<String> result = new AtomicReference<>(new String());
        CallResponse callResponse = null;
        try {
            transactionProcessor = getAssembleTransactionProcessor(
                    "", "src/main/resources/abi/", "src/main/resources/bin/");
            List<Object> params = new ArrayList<>();
            params.add(address);
            callResponse = transactionProcessor.sendCallByContractLoader("SDR", contractAddr, "balanceOfAvailable",
                    params);
            callResponse.getReturnObject().forEach(returnObject->{
                result.set(returnObject+"");
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(" balanceOfAvailable exception, error message is {}", e.getMessage());

            System.out.printf(" balanceOfAvailable failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

    public String totalSupply(String contractAddr) {
        AtomicReference<String> result = new AtomicReference<>(new String());
        CallResponse callResponse = null;
        try {
            transactionProcessor = getAssembleTransactionProcessor(
                    "", "src/main/resources/abi/", "src/main/resources/bin/");
            List<Object> params = new ArrayList<>();
            //params.add(address);
            callResponse = transactionProcessor.sendCallByContractLoader("SDR", contractAddr, "totalSupply",
                    params);
            callResponse.getReturnObject().forEach(returnObject->{
                result.set(returnObject+"");
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(" totalSupply exception, error message is {}", e.getMessage());

            System.out.printf(" totalSupply failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

    public String balanceOfFreeze(String contractAddr,String address) {
        AtomicReference<String> result = new AtomicReference<>(new String());
        CallResponse callResponse = null;
        try {
            transactionProcessor = getAssembleTransactionProcessor(
                    "", "src/main/resources/abi/", "src/main/resources/bin/");
            List<Object> params = new ArrayList<>();
            params.add(address);
            callResponse = transactionProcessor.sendCallByContractLoader("SDR", contractAddr, "balanceOfFreeze",
                    params);
            callResponse.getReturnObject().forEach(returnObject->{
                result.set(returnObject+"");
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(" totalSupply exception, error message is {}", e.getMessage());

            System.out.printf(" totalSupply failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

    public String transferByAdmin(String contractAddr,String privateKey,String from,String to,BigInteger value) {
        TransactionResponse response = null;
        AtomicReference<String> result = new AtomicReference<>(new String());
        try {
            transactionProcessor = getAssembleTransactionProcessor(privateKey, "src/main/resources/abi/", "src/main" +
                    "/resources/bin/");
            List<Object> params = new ArrayList<>();
            params.add(from);
            params.add(to);
            params.add(value);
            response = transactionProcessor.sendTransactionAndGetResponseByContractLoader("SDR", contractAddr,
                    "transfer", params);
            List<ABIObject> returnABIObjects = response.getReturnABIObject();
            returnABIObjects.forEach(returnABIObject ->{
                result.set(encodeHexString(returnABIObject.getBytesValue().getValue()));
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(" saveData exception, error message is {}", e.getMessage());

            System.out.printf(" saveData account failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

    public String balanceOfAccountDetail(String contractAddr,String from,String to) {
        AtomicReference<String> result = new AtomicReference<>(new String());
        CallResponse callResponse = null;
        try {
            transactionProcessor = getAssembleTransactionProcessor(
                    "", "src/main/resources/abi/", "src/main/resources/bin/");
            List<Object> params = new ArrayList<>();
            params.add(from);
            params.add(to);
            callResponse = transactionProcessor.sendCallByContractLoader("SDR", contractAddr, "balanceOfAccountDetail",
                    params);
            callResponse.getReturnObject().forEach(returnObject->{
                result.set(returnObject+"");
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(" totalSupply exception, error message is {}", e.getMessage());

            System.out.printf(" totalSupply failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

    public String balanceOfFreezeDetail(String contractAddr,String from,String to) {
        AtomicReference<String> result = new AtomicReference<>(new String());
        CallResponse callResponse = null;
        try {
            transactionProcessor = getAssembleTransactionProcessor(
                    "", "src/main/resources/abi/", "src/main/resources/bin/");
            List<Object> params = new ArrayList<>();
            params.add(from);
            params.add(to);
            callResponse = transactionProcessor.sendCallByContractLoader("SDR", contractAddr, "balanceOfFreezeDetail",
                    params);
            callResponse.getReturnObject().forEach(returnObject->{
                result.set(returnObject+"");
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(" totalSupply exception, error message is {}", e.getMessage());

            System.out.printf(" totalSupply failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

    public String mintFreezeNew(String contractAddr,String privateKey,String from,String to,BigInteger value) {
        TransactionResponse response = null;
        AtomicReference<String> result = new AtomicReference<>(new String());
        try {
            transactionProcessor = getAssembleTransactionProcessor(privateKey, "src/main/resources/abi/", "src/main" +
                    "/resources/bin/");
            List<Object> params = new ArrayList<>();
            params.add(from);
            params.add(to);
            params.add(value);
            response = transactionProcessor.sendTransactionAndGetResponseByContractLoader("SDR", contractAddr,
                    "mintFreezeNew", params);
            List<ABIObject> returnABIObjects = response.getReturnABIObject();
            returnABIObjects.forEach(returnABIObject ->{
                result.set(encodeHexString(returnABIObject.getBytesValue().getValue()));
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(" saveData exception, error message is {}", e.getMessage());

            System.out.printf(" saveData account failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

    public String burntFreezeNew(String contractAddr,String privateKey,String from,String to,BigInteger value) {
        TransactionResponse response = null;
        AtomicReference<String> result = new AtomicReference<>(new String());
        try {
            transactionProcessor = getAssembleTransactionProcessor(privateKey, "src/main/resources/abi/", "src/main" +
                    "/resources/bin/");
            List<Object> params = new ArrayList<>();
            params.add(from);
            params.add(to);
            params.add(value);
            response = transactionProcessor.sendTransactionAndGetResponseByContractLoader("SDR", contractAddr,
                    "burntFreezeNew", params);
            List<ABIObject> returnABIObjects = response.getReturnABIObject();
            returnABIObjects.forEach(returnABIObject ->{
                result.set(encodeHexString(returnABIObject.getBytesValue().getValue()));
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(" saveData exception, error message is {}", e.getMessage());

            System.out.printf(" saveData account failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

    public String unfreezeNew(String contractAddr,String privateKey,String from,String to,BigInteger value) {
        TransactionResponse response = null;
        AtomicReference<String> result = new AtomicReference<>(new String());
        try {
            transactionProcessor = getAssembleTransactionProcessor(privateKey, "src/main/resources/abi/", "src/main" +
                    "/resources/bin/");
            List<Object> params = new ArrayList<>();
            params.add(from);
            params.add(to);
            params.add(value);
            response = transactionProcessor.sendTransactionAndGetResponseByContractLoader("SDR", contractAddr,
                    "unfreezeNew", params);
            List<ABIObject> returnABIObjects = response.getReturnABIObject();
            returnABIObjects.forEach(returnABIObject ->{
                result.set(encodeHexString(returnABIObject.getBytesValue().getValue()));
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(" saveData exception, error message is {}", e.getMessage());

            System.out.printf(" saveData account failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

}

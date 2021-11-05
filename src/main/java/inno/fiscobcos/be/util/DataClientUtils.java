package inno.fiscobcos.be.util;

import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: data 合约操作工具类
 * @Author peifeng
 * @Date 2021/7/16 15:50
 */
@Component
public class DataClientUtils extends ClientUtils{

    public String deploy(String privateKey) {
        TransactionResponse response = null;
        try {
            transactionProcessor = getAssembleTransactionProcessor(privateKey, "src/main/resources/abi/", "src/main" +
                    "/resources/bin/");
            response = transactionProcessor.deployByContractLoader("Data", new ArrayList<>());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(" deploy Data contract failed, error message is  " + e.getMessage());
        }
        return response.getContractAddress();
    }

    public Map<String,Object> saveData(String contractAddr, String privateKey, String key, String data) {
        TransactionResponse response = null;
        Map<String,Object> result = new HashMap<>();
        try {
            transactionProcessor = getAssembleTransactionProcessor(privateKey, "src/main/resources/abi/", "src/main" +
                    "/resources/bin/");
            List<Object> params = new ArrayList<>();
            params.add(key);
            params.add(data);
            response = transactionProcessor.sendTransactionAndGetResponseByContractLoader("Data", contractAddr,
                    "saveChildrenNodes", params);
            result.put("transferHash",response.getTransactionReceipt().getTransactionHash());
            result.put("status",response.getTransactionReceipt().getStatus());
            result.put("message",response.getReturnMessage());
            result.put("blockNumber",ExtendsStringUtils.hex2decimal(response.getTransactionReceipt().getBlockNumber().substring(2)));
            result.put("resultData",ExtendsStringUtils.getDataSaveReturn(response.getTransactionReceipt().getOutput()));

        } catch (Exception e) {
            // TODO Auto-generated catch block
             e.printStackTrace();
            logger.error(" saveData exception, error message is {}", e.getMessage());

            System.out.printf(" saveData account failed, error message is %s\n", e.getMessage());
        }
        return result;
    }


    public String getData(String contractAddr,String key) {
        AtomicReference<String> result = new AtomicReference<>(new String());
        CallResponse callResponse = null;
        try {
            transactionProcessor = getAssembleTransactionProcessor(
                    "", "src/main/resources/abi/", "src/main/resources/bin/");
            List<Object> params = new ArrayList<>();
            params.add(key);
            callResponse = transactionProcessor.sendCallByContractLoader("Data", contractAddr, "childrenNodes",
                    params);
            callResponse.getReturnABIObject().forEach(returnABIObject->{
                result.set(new String(String.valueOf(returnABIObject.getStringValue())));
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
             e.printStackTrace();
            logger.error(" getData exception, error message is {}", e.getMessage());

            System.out.printf(" getData account failed, error message is %s\n", e.getMessage());
        }
        return result.get();
    }

    //public String dailyDatas(String contractAddr,String key) {
    //    AtomicReference<String> result = new AtomicReference<>(new String());
    //    CallResponse callResponse = null;
    //    try {
    //        transactionProcessor = getAssembleTransactionProcessor(
    //                "", "src/main/resources/abi/", "src/main/resources/bin/");
    //        List<Object> params = new ArrayList<>();
    //        params.add(key);
    //        callResponse = transactionProcessor.sendCallByContractLoader("Data", contractAddr, "dailyDatas",
    //                params);
    //        callResponse.getReturnABIObject().forEach(returnABIObject->{
    //            result.set("0x"+encodeHexString(returnABIObject.getBytesValue().getValue()));
    //        });
    //    } catch (Exception e) {
    //        // TODO Auto-generated catch block
    //         e.printStackTrace();
    //        logger.error(" dailyDatas exception, error message is {}", e.getMessage());
    //
    //        System.out.printf(" dailyDatas failed, error message is %s\n", e.getMessage());
    //    }
    //    return result.get();
    //}
    //
    //public String monthlyDatas(String contractAddr,String key) {
    //    AtomicReference<String> result = new AtomicReference<>(new String());
    //    CallResponse callResponse = null;
    //    try {
    //        transactionProcessor = getAssembleTransactionProcessor(
    //                "", "src/main/resources/abi/", "src/main/resources/bin/");
    //        List<Object> params = new ArrayList<>();
    //        params.add(key);
    //        callResponse = transactionProcessor.sendCallByContractLoader("Data", contractAddr, "monthlyDatas",
    //                params);
    //        callResponse.getReturnABIObject().forEach(returnABIObject->{
    //            result.set("0x"+encodeHexString(returnABIObject.getBytesValue().getValue()));
    //        });
    //    } catch (Exception e) {
    //        // TODO Auto-generated catch block
    //         e.printStackTrace();
    //        logger.error(" monthlyDatas exception, error message is {}", e.getMessage());
    //
    //        System.out.printf(" monthlyDatas failed, error message is %s\n", e.getMessage());
    //    }
    //    return result.get();
    //}
    //
    //public String annualDatas(String contractAddr,String key) {
    //    AtomicReference<String> result = new AtomicReference<>(new String());
    //    CallResponse callResponse = null;
    //    try {
    //        transactionProcessor = getAssembleTransactionProcessor(
    //                "", "src/main/resources/abi/", "src/main/resources/bin/");
    //        List<Object> params = new ArrayList<>();
    //        params.add(key);
    //        callResponse = transactionProcessor.sendCallByContractLoader("Data", contractAddr, "annualDatas",
    //                params);
    //        callResponse.getReturnABIObject().forEach(returnABIObject->{
    //            result.set("0x"+encodeHexString(returnABIObject.getBytesValue().getValue()));
    //        });
    //    } catch (Exception e) {
    //        // TODO Auto-generated catch block
    //         e.printStackTrace();
    //        logger.error(" annualDatas exception, error message is {}", e.getMessage());
    //
    //        System.out.printf(" annualDatas failed, error message is %s\n", e.getMessage());
    //    }
    //    return result.get();
    //}
    //
    //public String rootData(String contractAddr) {
    //    AtomicReference<String> result = new AtomicReference<>(new String());
    //    CallResponse callResponse = null;
    //    try {
    //        transactionProcessor = getAssembleTransactionProcessor(
    //                "", "src/main/resources/abi/", "src/main/resources/bin/");
    //        callResponse = transactionProcessor.sendCallByContractLoader("Data", contractAddr, "rootData",
    //                new ArrayList<>());
    //        callResponse.getReturnABIObject().forEach(returnABIObject->{
    //            result.set("0x"+encodeHexString(returnABIObject.getBytesValue().getValue()));
    //        });
    //    } catch (Exception e) {
    //        // TODO Auto-generated catch block
    //         e.printStackTrace();
    //        logger.error(" rootData exception, error message is {}", e.getMessage());
    //
    //        System.out.printf(" rootData failed, error message is %s\n", e.getMessage());
    //    }
    //    return result.get();
    //}
}

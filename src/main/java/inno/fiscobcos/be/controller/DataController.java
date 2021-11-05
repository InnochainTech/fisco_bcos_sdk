package inno.fiscobcos.be.controller;


import inno.fiscobcos.be.util.AddressUtils;
import inno.fiscobcos.be.util.DataClientUtils;
import inno.fiscobcos.be.util.result.Result;
import inno.fiscobcos.be.util.result.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author peifeng
 * @Date 2021/7/16 9:49
 */
@Api(tags = "数据合约")
@Controller
@RequestMapping(value = "/data")
public class DataController {

    @Autowired
    private DataClientUtils dataClientUtils;

    @ApiOperation(value = "部署数据合约")
    @ApiImplicitParam(name="privateKey",value = "私钥",required = true,dataType = "String",paramType = "query",example = "619aa0c1b0af1daca2da28aa1dc8f96ebd76b29ed0f0c3b3495cddb1f8a5f30f")
    @ResponseBody
    @PostMapping("/deployDataContract")
    public Result deployDataContract(@RequestParam(value = "privateKey", required = true) String privateKey){

        String contractAddr = new String();
        try {
            contractAddr = dataClientUtils.deploy(privateKey);
        }catch (Exception e){

        }
        return ResultUtils.success(contractAddr);
    }
    @ApiOperation(value = "上传数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query", required =
                    true,example = "0x00049ef568a787a70fbd7f9e1af9cc5e2730a1f5"),
            @ApiImplicitParam(name = "privateKey", value = "私钥", dataType = "string", paramType = "query", required =
                    true,example = "619aa0c1b0af1daca2da28aa1dc8f96ebd76b29ed0f0c3b3495cddb1f8a5f30f"),
            @ApiImplicitParam(name = "key", value = "日期字符串", dataType = "string", paramType = "query", required =
                    true,example = "2021071901"),
            @ApiImplicitParam(name = "data", value = "数据字符串", dataType = "string", paramType = "query", required
                    = true,example = "0x4129aefcca3819035418665cbbc6bbd8dda9efc960467a4e62b06e053c570766")
    })
    @ResponseBody
    @PostMapping("/saveData")
    public Result saveData(@RequestParam(value = "contractAddr", required = true) String contractAddr,
                           @RequestParam(value = "privateKey", required = true) String privateKey,
                           @RequestParam(value = "key", required = true) String key,
                           @RequestParam(value = "data", required = true) String data
    ){

        //String regex="^[0-9]{10}$";
        //if(!key.matches(regex)){
        //    throw new AddressException(ResultEnum.FORMAT_ERROR);
        //}
        //BigInteger year = new BigInteger(key.substring(0,4));
        //if(year.intValue()>2050||year.intValue()<1990){
        //    throw new AddressException(ResultEnum.YEAR_ERROR);
        //}
        //
        //BigInteger month = new BigInteger(key.substring(4,6));
        //if(month.intValue()>12||month.intValue()<0){
        //    throw new AddressException(ResultEnum.MONTH_ERROR);
        //}
        //BigInteger day = new BigInteger(key.substring(6,8));
        //if(day.intValue()>getMonthDays(year.intValue(),month.intValue())||day.intValue()<0){
        //    throw new AddressException(ResultEnum.DAY_ERROR);
        //}
        //BigInteger hour = new BigInteger(key.substring(8,10));
        //if(hour.intValue()>24||hour.intValue()<0){
        //    throw new AddressException(ResultEnum.HOUR_ERROR);
        //}

        Map<String,Object> result = new HashMap<>();
        try{
            result = dataClientUtils.saveData(contractAddr,privateKey,key,data);

        }catch (Exception e){

        }
        return ResultUtils.success(result);
    }

    @ApiOperation(value = "查找数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
                    required = true,example = "0x00049ef568a787a70fbd7f9e1af9cc5e2730a1f5"),
            @ApiImplicitParam(name = "key", value = "日期字符串", dataType = "string", paramType = "query", required =
                    true,example = "2021071901"),
    })
    @ResponseBody
    @GetMapping("/getData")
    public Result getData(@RequestParam(value = "contractAddr", required = true) String contractAddr,
                          @RequestParam(value = "key") String key
                          ){
        String data = new String();
        try {
            data = dataClientUtils.getData(contractAddr,key);
        }catch (Exception e){

        }
        return ResultUtils.success(data);
    }


    //@ResponseBody
    //@GetMapping("/test")
    //public Result test(@RequestParam(value = "contractAddr", required = true) String contractAddr,
    //                   @RequestParam(value = "privateKey", required = true) String privateKey){
    //    Map<String,Object> result = new HashMap<>();
    //    try{
    //        for (int i =1; i < 100; i++) {
    //            String data = "0x4129aefcca3819035418665cbbc6bbd8dda9efc960467a4e62b06e053c570766";
    //            String key = i+"";
    //            result = dataClientUtils.saveData(contractAddr,privateKey,key,data);
    //        }
    //
    //
    //    }catch (Exception e){
    //        throw new Web3Exception(ResultEnum.ERROR);
    //    }
    //    return ResultUtils.success(result);
    //}


    //private int getMonthDays(int _year,int _month){
    //    boolean isLeapYear = false;
    //    int daysNum = 0;
    //    if((_year %4 == 0 && _year%100 != 0) || _year % 400 ==0){
    //        isLeapYear = true;
    //    }
    //    if(_month == 1 || _month == 3 || _month == 5 || _month == 7 || _month == 8 || _month == 10 || _month == 12){
    //        daysNum = 31;
    //    }else if(_month == 2){
    //        if(isLeapYear){
    //            daysNum = 29;
    //        }else{
    //            daysNum = 28;
    //        }
    //    }else{
    //        daysNum = 30;
    //    }
    //    return daysNum;
    //}

}

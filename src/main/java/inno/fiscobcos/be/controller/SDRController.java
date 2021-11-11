//package inno.fiscobcos.be.controller;
//
//import inno.fiscobcos.be.util.AddressUtils;
//import inno.fiscobcos.be.util.SDRClientUtils;
//import inno.fiscobcos.be.util.result.Result;
//import inno.fiscobcos.be.util.result.ResultUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigInteger;
//
///**
// * @Description: SDR 合约操作入口
// * @Author peifeng
// * @Date 2021/7/20 10:18
// */
//@Api(tags = "SDR合约")
//@Controller
//@RequestMapping(value = "/sdr")
//public class SDRController {
//
//    @Autowired
//    SDRClientUtils sdrClientUtils;
//
//    @ApiOperation(value = "部署数据合约", notes = "")
//    @ApiImplicitParam(name="privateKey",value = "私钥",required = true,dataType = "String",paramType = "query",example = "619aa0c1b0af1daca2da28aa1dc8f96ebd76b29ed0f0c3b3495cddb1f8a5f30f")
//    @ResponseBody
//    @PostMapping("/deploySDRContract")
//    public Result deploySDRContract(@RequestParam(value = "privateKey", required = true) String privateKey){
//        if(!AddressUtils.isPrivateKey(privateKey)){
//        }
//        String contractAddr = new String();
//        try {
//            contractAddr = sdrClientUtils.deploy(privateKey);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(contractAddr);
//    }
//
//    @ApiOperation(value = "查询用户token数量")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//            @ApiImplicitParam(name = "address", value = "用户地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60")
//    })
//    @ResponseBody
//    @GetMapping("/balanceOf")
//    public Result balanceOf(@RequestParam(value = "contractAddr", required = true) String contractAddr,
//                            @RequestParam(value = "address", required = true) String address){
//
//        String balance = new String();
//        try {
//            balance = sdrClientUtils.balanceOf(contractAddr,address);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(balance);
//    }
//
//    @ApiOperation(value = "获取用户token精度")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//    })
//    @ResponseBody
//    @GetMapping("/decimal")
//    public Result decimal(@RequestParam(value = "contractAddr", required = true) String contractAddr){
//        String decimalValue = new String();
//        try {
//            decimalValue = sdrClientUtils.decimal(contractAddr);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(decimalValue);
//    }
//
//    @ApiOperation(value = "查询用户余额")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//            @ApiImplicitParam(name = "address", value = "用户地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60")
//    })
//    @ResponseBody
//    @GetMapping("/balanceOfAvailable")
//    public Result balanceOfAvailable(@RequestParam(value = "contractAddr", required = true) String contractAddr,
//                                     @RequestParam(value = "address", required = true) String address){
//        String balanceOfAvailable = new String();
//        try {
//            balanceOfAvailable = sdrClientUtils.balanceOfAvailable(contractAddr,address);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(balanceOfAvailable);
//    }
//
//    @ApiOperation(value = "查询token发行总量")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//    })
//    @ResponseBody
//    @GetMapping("/totalSupply")
//    public Result totalSupply(@RequestParam(value = "contractAddr", required = true) String contractAddr){
//        String totalSupply = new String();
//        try {
//            totalSupply = sdrClientUtils.totalSupply(contractAddr);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(totalSupply);
//    }
//
//    @ApiOperation(value = "查询用户冻结账户余额")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//            @ApiImplicitParam(name = "address", value = "用户地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60")
//    })
//    @ResponseBody
//    @GetMapping("/balanceOfFreeze")
//    public Result balanceOfFreeze(@RequestParam(value = "contractAddr", required = true) String contractAddr,
//                                  @RequestParam(value = "address", required = true) String address){
//        String balanceOfFreeze = new String();
//        try {
//            balanceOfFreeze = sdrClientUtils.balanceOfFreeze(contractAddr,address);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(balanceOfFreeze);
//    }
//
//    @ApiOperation(value = "token转让")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//            @ApiImplicitParam(name = "privateKey", value = "用户私钥", dataType = "string", paramType = "query",
//                    required = true,example = "619aa0c1b0af1daca2da28aa1dc8f96ebd76b29ed0f0c3b3495cddb1f8a5f30f"),
//            @ApiImplicitParam(name = "fromAddress", value = "from地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//            @ApiImplicitParam(name = "toAddress", value = "to地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60"),
//            @ApiImplicitParam(name = "value", value = "转让额度", dataType = "string", paramType = "query", required =
//                    true,example = "10000"),
//    })
//    @ResponseBody
//    @PostMapping("/transfer")
//    public Result transfer(@RequestParam(value = "contractAddr", required = true) String contractAddr,
//                           @RequestParam(value = "privateKey", required = true) String privateKey,
//                           @RequestParam(value = "fromAddress", required = true) String fromAddress,
//                           @RequestParam(value = "toAddress", required = true) String toAddress,
//                           @RequestParam(value = "value", required = true) String value
//                           ){
//        BigInteger bigIntegerValue = new BigInteger(value);
//        String txhash = new String();
//        try {
//            txhash = sdrClientUtils.transferByAdmin(contractAddr,privateKey,fromAddress,toAddress,bigIntegerValue);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(txhash);
//    }
//
//    @ApiOperation(value = "查询合伙人在指定商户那里获得的SDR数量")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//            @ApiImplicitParam(name = "fromAddress", value = "from地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60"),
//            @ApiImplicitParam(name = "toAddress", value = "to地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60")
//    })
//    @ResponseBody
//    @GetMapping("/balanceOfAccountDetail")
//    public Result balanceOfAccountDetail(@RequestParam(value = "contractAddr", required = true) String contractAddr,
//                                         @RequestParam(value = "fromAddress", required = true) String fromAddress,
//                                         @RequestParam(value = "toAddress", required = true) String toAddress
//    ){
//        String result = new String();
//        try {
//            result = sdrClientUtils.balanceOfAccountDetail(contractAddr,fromAddress,toAddress);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(result);
//    }
//
//    @ApiOperation(value = "查询在指定账户中冻结的SDR")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//            @ApiImplicitParam(name = "fromAddress", value = "from地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60"),
//            @ApiImplicitParam(name = "toAddress", value = "to地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60")
//    })
//    @ResponseBody
//    @GetMapping("/balanceOfFreezeDetail")
//    public Result balanceOfFreezeDetail(@RequestParam(value = "contractAddr", required = true) String contractAddr,
//                                        @RequestParam(value = "fromAddress", required = true) String fromAddress,
//                                        @RequestParam(value = "toAddress", required = true) String toAddress
//    ){
//        String result = new String();
//        try {
//            result = sdrClientUtils.balanceOfFreezeDetail(contractAddr,fromAddress,toAddress);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(result);
//    }
//
//
//    @ApiOperation(value = "冻结sdr")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//            @ApiImplicitParam(name = "privateKey", value = "用户私钥", dataType = "string", paramType = "query",
//                    required = true,example = "619aa0c1b0af1daca2da28aa1dc8f96ebd76b29ed0f0c3b3495cddb1f8a5f30f"),
//            @ApiImplicitParam(name = "fromAddress", value = "from地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60"),
//            @ApiImplicitParam(name = "toAddress", value = "to地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60"),
//            @ApiImplicitParam(name = "value", value = "冻结sdr数量", dataType = "string", paramType = "query", required =
//                    true,example = "10000"),
//    })
//    @ResponseBody
//    @PostMapping("/mintFreezeNew")
//    public Result mintFreezeNew(@RequestParam(value = "contractAddr", required = true) String contractAddr,
//                                @RequestParam(value = "privateKey", required = true) String privateKey,
//                                @RequestParam(value = "fromAddress", required = true) String fromAddress,
//                                @RequestParam(value = "toAddress", required = true) String toAddress,
//                                @RequestParam(value = "value", required = true) String value
//    ){
//        BigInteger bigIntegerValue = new BigInteger(value);
//        String txhash = new String();
//        try {
//            txhash = sdrClientUtils.mintFreezeNew(contractAddr,privateKey,fromAddress,toAddress,bigIntegerValue);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(txhash);
//    }
//
//
//    @ApiOperation(value = "销毁冻结金额")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//            @ApiImplicitParam(name = "privateKey", value = "用户私钥", dataType = "string", paramType = "query",
//                    required = true,example = "619aa0c1b0af1daca2da28aa1dc8f96ebd76b29ed0f0c3b3495cddb1f8a5f30f"),
//            @ApiImplicitParam(name = "fromAddress", value = "from地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60"),
//            @ApiImplicitParam(name = "toAddress", value = "to地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60"),
//            @ApiImplicitParam(name = "value", value = "销毁冻结金额", dataType = "string", paramType = "query", required =
//                    true,example = "10000"),
//    })
//    @ResponseBody
//    @PostMapping("/burntFreezeNew")
//    public Result burntFreezeNew(@RequestParam(value = "contractAddr", required = true) String contractAddr,
//                                 @RequestParam(value = "privateKey", required = true) String privateKey,
//                                 @RequestParam(value = "fromAddress", required = true) String fromAddress,
//                                 @RequestParam(value = "toAddress", required = true) String toAddress,
//                                 @RequestParam(value = "value", required = true) String value
//    ){
//        BigInteger bigIntegerValue = new BigInteger(value);
//        String txhash = new String();
//        try {
//            txhash = sdrClientUtils.burntFreezeNew(contractAddr,privateKey,fromAddress,toAddress,bigIntegerValue);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(txhash);
//    }
//
//
//    @ApiOperation(value = "解冻冻结SDR数量")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "contractAddr", value = "合约地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xbfd498e6deba1196ef6e433176dcc5fe86a3a0e4"),
//            @ApiImplicitParam(name = "privateKey", value = "用户私钥", dataType = "string", paramType = "query",
//                    required = true,example = "619aa0c1b0af1daca2da28aa1dc8f96ebd76b29ed0f0c3b3495cddb1f8a5f30f"),
//            @ApiImplicitParam(name = "fromAddress", value = "from地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60"),
//            @ApiImplicitParam(name = "toAddress", value = "to地址", dataType = "string", paramType = "query",
//                    required = true,example = "0xc047815daa9d0cbe44fc9928c3bf5eb6df6f3a60"),
//            @ApiImplicitParam(name = "value", value = "解冻冻结SDR数量", dataType = "string", paramType = "query", required =
//                    true,example = "10000"),
//    })
//    @ResponseBody
//    @PostMapping("/unfreezeNew")
//    public Result unfreezeNew(@RequestParam(value = "contractAddr", required = true) String contractAddr,
//                              @RequestParam(value = "privateKey", required = true) String privateKey,
//                              @RequestParam(value = "fromAddress", required = true) String fromAddress,
//                              @RequestParam(value = "toAddress", required = true) String toAddress,
//                              @RequestParam(value = "value", required = true) String value
//    ){
//        BigInteger bigIntegerValue = new BigInteger(value);
//        String txhash = new String();
//        try {
//            txhash = sdrClientUtils.unfreezeNew(contractAddr,privateKey,fromAddress,toAddress,bigIntegerValue);
//        }catch (Exception e){
//        }
//        return ResultUtils.success(txhash);
//    }
//
//}

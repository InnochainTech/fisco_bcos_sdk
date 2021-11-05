package inno.fiscobcos.be.controller;

/**
 * @Description: SDR 合约操作入口
 * @Author peifeng
 * @Date 2021/7/20 10:18
 */
//@Api(tags = "ABT合约")
//@Controller
//@RequestMapping(value = "/abt")
public class ABTController {

    //@Autowired
    //ABTClientUtils ABTClientUtils;
    //
    //@ApiOperation(value = "查询合伙人下标地址")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/getAbtIndexNumber")
    //public Result getAbtIndexNumber(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "查询拥有token数量不为0的人数")
    //@ResponseBody
    //@GetMapping("/abtNotZeroCount")
    //public Result abtNotZeroCount(){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "查询拥有token人数")
    //@ResponseBody
    //@GetMapping("/partnerSize")
    //public Result partnerSize(){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "查询固定成本金额")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/costFixed")
    //public Result costFixed(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "查询账户详情")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/getAccountList")
    //public Result getAccountList(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "获取月流水汇总")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/getMouthFlow")
    //public Result getMouthFlow(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "获取还款账单数据汇总")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/getRepaymentFlow")
    //public Result getRepaymentFlow(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "获取成本数量")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/cost")
    //public Result cost(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "获取商户总的冻结成本数量")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/freezeCost")
    //public Result freezeCost(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "添加合伙人")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/addPartner")
    //public Result addPartner(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "增加特殊合伙人")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/addSpecialPartner")
    //public Result addSpecialPartner(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "冻结成本")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/mintFreezeCost")
    //public Result mintFreezeCost(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "销毁冻结成本")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/burntFreezeCost")
    //public Result burntFreezeCost(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "解冻成本")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/unfreezeCost")
    //public Result unfreezeCost(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "按成本比例清分")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/clearBycostRatio")
    //public Result clearBycostRatio(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "固定成本清分（固定月成本）")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/clearBycostFixed")
    //public Result clearBycostFixed(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "固定成本清分（固定每笔订单成本）")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/clearByFixedMoney")
    //public Result clearByFixedMoney(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "按比例还款清分")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/clearBycostRatioRepayment")
    //public Result clearBycostRatioRepayment(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "固定成本月流水退款")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/refundMouthFlow")
    //public Result refundMouthFlow(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
    //
    //@ApiOperation(value = "退款还款账单流水")
    //@ApiImplicitParam(name="address",value = "用户地址",required = true,dataType = "String",paramType = "query",example =
    //        "0xac5d60ff982f5ced91d7c52cabf26ecaaac40b12")
    //@ResponseBody
    //@GetMapping("/refundRepaymentFlow")
    //public Result refundRepaymentFlow(@RequestParam(value = "address", required = true) String address){
    //    Map<String,String> resultmap = new HashMap<>();
    //
    //    return ResultUtils.success(resultmap);
    //}
}

package inno.fisco.bcos.be.controller;

import inno.fisco.bcos.be.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @Author peifeng
 * @Date 2021/7/19 9:41
 */
@Api(tags = "心跳API")
@Controller
@CrossOrigin
public class HeartBeatController {

    @ApiOperation(value = "监听心跳")
    @ResponseBody
    @GetMapping("/heartBeat")
    public Result heartBeat(){
        return new Result().success(true);
    }



}

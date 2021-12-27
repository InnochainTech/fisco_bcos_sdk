package inno.fisco.bcos.be.configuration;

import com.alibaba.fastjson.JSONException;
import inno.fisco.bcos.be.util.result.Result;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionBaseException;
import org.fisco.bcos.sdk.utils.exceptions.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 自定义异常处理
 * @author: DT
 * @date: 2021/4/19 21:17
 * @version: v1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler  {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return new Result().error(300,"发生空指针异常！原因是:"+e.getMessage());
    }

    /**
     * 合约参数格式异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e){
        logger.error("方法参数异常！原因是:",e);
        return new Result().error(300,"输入参数异常！原因是:"+e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 合约参数格式异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, HttpMessageNotReadableException e) {
        logger.error("请求传递的参数不可读！原因是:",e);
        return new Result().error(300,"请求传递的参数不可读！:"+e.getMessage());
    }

    /**
     * 合约参数格式异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = TransactionBaseException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, TransactionBaseException e) {
        logger.error("发起交易参数错误！原因是:",e);
        return new Result().error(300,"发起交易参数错误！:"+e.getMessage());
    }

    /**
     * 合约参数格式异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = JSONException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, JSONException e){
        logger.error("MQ读取的json数据转换失败！原因是:",e);
        return new Result().error(300,"MQ读取的json数据转换失败！:"+e.getMessage());
    }

    /**
     * 合约签名失败
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = DecoderException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, DecoderException e){
        logger.error("签名失败！原因是:",e);
        return new Result().error(300,"签名失败！:"+e.getMessage());
    }

    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("未知异常！原因是:",e);
        return new Result().error(300,"未知异常！原因是:"+e.getMessage());
    }
}

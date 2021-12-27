package inno.fisco.bcos.be.util.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 请求返回结果
 * @Author peifeng
 * @Date 2021/5/24 15:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T>{
    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体的内容. */
    private T data;

    public Result success(T object) {
        return new Result(200,"成功",object);
    }

    public Result success() {
        return success(null);
    }

    public Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public Result error(Integer code, String msg, T data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}

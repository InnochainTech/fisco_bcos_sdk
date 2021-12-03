package inno.fisco.bcos.be.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peifeng
 * @date 2021/9/15 17:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResVo<T> {

	@ApiModelProperty(value = "请求状态码", required = true)
	private Integer code;
	@ApiModelProperty(value = "请求返回数据", required = true)
	private String message;
	@ApiModelProperty(value = "请求返回结构体", required = true)
	private T data;

}
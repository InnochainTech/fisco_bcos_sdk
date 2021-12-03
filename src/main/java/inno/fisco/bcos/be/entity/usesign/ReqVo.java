package inno.fisco.bcos.be.entity.usesign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * @author peifeng
 * @date 2021/11/16 9:25
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="ReqVo",description="请求数据")
public class ReqVo<T> {

	@ApiModelProperty(value = "业务流水号(仅支持数字字母下划线,最大长度64)" ,required= true)
	@Pattern(regexp = "^[0-9a-zA-Z_]{1,64}$", message = "业务流水号格式错误")
	private String orderId;

	@ApiModelProperty(value = "签名用户编号(仅支持数字字母下划线,最大长度64)" ,required= true)
	@Pattern(regexp = "^[0-9a-zA-Z_]{1,64}$", message = "签名用户编号格式错误")
	private String signUserId;

	@ApiModelProperty(value = "方法参数" ,required= true)
	private T funcParam;

	@ApiModelProperty(value = "合约地址(部署合约时忽略)" ,required= true, example = "0x8dcde53a94b83a2f8230db0f549a7c7c8254a65f")
	@Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误")
	private String contractAddress;

}

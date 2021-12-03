package inno.fisco.bcos.be.entity;

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
@ApiModel(value="requestDo",description="请求数据")
public class RequestDo {

	@ApiModelProperty(value = "合约地址" ,required= true)
	@Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "合约地址格式错误")
	private String contractAddress;

}

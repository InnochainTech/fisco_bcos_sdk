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
public class ResponseVo{

	@ApiModelProperty(value = "交易链上hash", required = true)
	protected String transationHash;
	@ApiModelProperty(value = "交易所在区块数", required = true)
	protected String blockNumber;

}
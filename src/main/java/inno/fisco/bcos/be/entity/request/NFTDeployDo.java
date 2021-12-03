package inno.fisco.bcos.be.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/10 17:27
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="NFTDeployDo",description="NFT合约部署数据")
public class NFTDeployDo {

	@ApiModelProperty(value = "合约管理员私钥（加密后的私钥）" ,required= true)
	private String privateKey;

	@ApiModelProperty(value = "合约名称" ,required= true)
	private String name;

	@ApiModelProperty(value = "合约符号" ,required= true)
	private String symbol;

	@ApiModelProperty(value = "总发行量" ,required= true)
	@Min(1)
	private BigInteger totalSupply;

	@ApiModelProperty(value = "权益链接")
	private String equityLink = "";

	@ApiModelProperty(value = "是否支持续费" ,required= true)
	private Boolean canRenew;

	/*@ApiModelProperty(value = "是否支持增发" ,required= true)
	private Boolean canIssua;*/

	@ApiModelProperty(value = "是否支持核销" ,required= true)
	private Boolean canWriteOff;

	@ApiModelProperty(value = "核销类型及数量(支持核销时必填)")
	private List<InitWriteOffDo> initWriteOff;

	@ApiModelProperty(value = "初始有效截至时间(公链秒时间戳，bcos毫秒时间戳)",required= true)
	private BigInteger initialDeadline;

}

package inno.fiscobcos.be.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * @author peifeng
 * @date 2021/11/10 17:27
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="NFTDeploy",description="NFT合约部署数据")
public class NFTDeployDo {

	@ApiModelProperty("私钥")
	private String privateKey;

	@ApiModelProperty("合约名称")
	private String name;

	@ApiModelProperty("合约符号")
	private String symbol;

	@ApiModelProperty("总发行量")
	private BigInteger totalSupply;

	@ApiModelProperty("权益链接")
	private String equityLink;

	@ApiModelProperty("是否支持续费")
	private Boolean canRenew;

	@ApiModelProperty("是否支持增发")
	private Boolean canIssua;

	@ApiModelProperty("是否支持核销")
	private Boolean canWriteOff;

}

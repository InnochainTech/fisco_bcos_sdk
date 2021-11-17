package inno.fiscobcos.be.entity.request;

import inno.fiscobcos.be.entity.RequestDo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigInteger;

/**
 * @author peifeng
 * @date 2021/11/11 11:37
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="BatchMintDo",description="NFT批量铸造数据")
public class BatchMintDo extends RequestDo {

	@ApiModelProperty(value = "合约管理员私钥（加密后的私钥）" ,required= true)
	private String privateKey;

	@ApiModelProperty(value = "铸造数量" ,required= true)
	@Min(1)
	private BigInteger supply;

	@ApiModelProperty(value = "图片显示地址" ,required= true)
	private String tokenURI;
}

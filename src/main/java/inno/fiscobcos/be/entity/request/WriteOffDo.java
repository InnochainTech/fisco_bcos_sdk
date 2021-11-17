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
 * @date 2021/11/11 17:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="WriteOffDo",description="NFT核销数据")
public class WriteOffDo extends RequestDo {

	@ApiModelProperty(value = "NFT拥有者私钥（加密后的私钥）" ,required= true)
	private String privateKey;

	@ApiModelProperty(value = "核销类型（数组下标）" ,required= true)
	@Min(0)
	private BigInteger index;

	@ApiModelProperty(value = "核销某个代币下的会员",required= true)
	@Min(1)
	private BigInteger tokenId;

	@ApiModelProperty(value = "核销数量",required= true)
	@Min(1)
	private BigInteger supply;
}

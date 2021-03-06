package inno.fisco.bcos.be.entity.request;

import inno.fisco.bcos.be.entity.RequestDo;
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
 * @date 2021/11/11 17:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="BatchBurnDo",description="NFT批量销毁数据")
public class BatchBurnDo extends RequestDo {

	@ApiModelProperty(value = "NFT拥有者私钥（加密后的私钥）" ,required= true)
	private String privateKey;

	@ApiModelProperty(value = "批量销毁代币数组(目前最大支持200个，数量越多返回速度越慢，建议传50个)" ,required= true)
	private List<@Min(1) BigInteger> tokenIds;
}

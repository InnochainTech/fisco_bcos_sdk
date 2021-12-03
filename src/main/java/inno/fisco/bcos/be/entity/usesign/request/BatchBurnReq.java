package inno.fisco.bcos.be.entity.usesign.request;

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
@ApiModel(value="BatchBurnReq",description="NFT批量销毁数据")
public class BatchBurnReq {

	@ApiModelProperty(value = "批量销毁代币数组(目前最大支持200个，数量越多返回速度越慢，建议传50个)" ,required= true)
	private List<@Min(1) BigInteger> tokenIds;
}

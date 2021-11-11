package inno.fiscobcos.be.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * @author peifeng
 * @date 2021/11/11 17:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="BatchBurnDo",description="NFT批量销毁数据")
public class BatchBurnDo {

	@ApiModelProperty("批量销毁代币数组")
	private BigInteger[]  tokenIds;
}

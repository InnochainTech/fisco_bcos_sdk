package inno.fiscobcos.be.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * @author peifeng
 * @date 2021/11/11 17:29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="AddIssuaDo",description="NFT增发数据")
public class AddIssuaVo {

	@ApiModelProperty("增发数量")
	private BigInteger addIssuaSupply;
}

package inno.fiscobcos.be.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * @author peifeng
 * @date 2021/11/11 11:37
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="BatchSellVo",description="NFT批量出售返回数据")
public class BatchSellVo {

	@ApiModelProperty("是否出售成功")
	private boolean sellSuccess;
}

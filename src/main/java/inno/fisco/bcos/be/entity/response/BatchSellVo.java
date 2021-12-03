package inno.fisco.bcos.be.entity.response;

import inno.fisco.bcos.be.entity.ResponseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peifeng
 * @date 2021/11/11 11:37
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="BatchSellVo",description="NFT批量出售返回数据")
public class BatchSellVo extends ResponseVo {

	@ApiModelProperty("是否出售成功")
	private boolean sellSuccess;
}

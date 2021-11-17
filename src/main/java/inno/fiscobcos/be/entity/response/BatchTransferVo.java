package inno.fiscobcos.be.entity.response;

import inno.fiscobcos.be.entity.ResponseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peifeng
 * @date 2021/11/15 16:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="BatchTransferVo",description="NFT批量转账返回数据")
public class BatchTransferVo extends ResponseVo {

	@ApiModelProperty("是否转账成功")
	private boolean transferSuccess;

}

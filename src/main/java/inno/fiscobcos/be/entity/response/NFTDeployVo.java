package inno.fiscobcos.be.entity.response;

import inno.fiscobcos.be.entity.ResponseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peifeng
 * @date 2021/11/10 18:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="NFTDeployVo",description="NFT合约部署返回数据")
public class NFTDeployVo extends ResponseVo {

	@ApiModelProperty("部署成功的合约的地址")
	private String contractAddress;

	@ApiModelProperty("设置初始有效截至时间是否成功(当是否可续期为否时，需要返回true,部署才算成功)")
	private boolean setInitialDeadlineSuccess;

	@ApiModelProperty("设置核销参数是否成功(当是否可核销为是是，需要返回true,部署才算成功)")
	private boolean setWriteOffSuccess;
}

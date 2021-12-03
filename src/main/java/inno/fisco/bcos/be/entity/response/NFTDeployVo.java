package inno.fisco.bcos.be.entity.response;

import inno.fisco.bcos.be.entity.ResponseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author peifeng
 * @date 2021/11/10 18:04
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="NFTDeployVo",description="NFT合约部署返回数据")
public class NFTDeployVo extends ResponseVo {

	@ApiModelProperty("部署成功的合约的地址")
	private String contractAddress;

	@ApiModelProperty("设置核销参数是否成功(当是否可核销为是是，需要返回true,部署才算成功)")
	private boolean setWriteOffSuccess;
}

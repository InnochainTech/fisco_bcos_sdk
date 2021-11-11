package inno.fiscobcos.be.entity.response;

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
public class NFTDeployVo {

	@ApiModelProperty("合约地址")
	private String ContractAddress;
}

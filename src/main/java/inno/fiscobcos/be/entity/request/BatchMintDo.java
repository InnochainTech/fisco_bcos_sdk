package inno.fiscobcos.be.entity.request;

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
@ApiModel(value="BatchMintDo",description="NFT批量铸造数据")
public class BatchMintDo {

	@ApiModelProperty("私钥")
	private String privateKey;

	@ApiModelProperty("合约地址")
	private String contractAddress;

	@ApiModelProperty("铸造数量")
	private BigInteger supply;

	@ApiModelProperty("图片显示地址")
	private String tokenURI;
}

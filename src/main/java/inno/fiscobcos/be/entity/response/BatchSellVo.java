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
@ApiModel(value="BatchSellDo",description="NFT批量出售数据")
public class BatchSellVo {

	@ApiModelProperty("私钥")
	private String privateKey;

	@ApiModelProperty("合约地址")
	private String contractAddress;

	@ApiModelProperty("批量出售代币列表")
	private BigInteger[] tokenIds;

	@ApiModelProperty("购买者地址")
	private String to;

	@ApiModelProperty("代币有效截至时间")
	private BigInteger expirationTime;
}

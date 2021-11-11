package inno.fiscobcos.be.entity.response;

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
@ApiModel(value="WriteOffDo",description="NFT核销数据")
public class WriteOffVo {

	@ApiModelProperty("核销类型")
	private BigInteger index;

	@ApiModelProperty("核销某个代币下的会员")
	private BigInteger tokenId;

	@ApiModelProperty("核销数量")
	private BigInteger supply;
}

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
@ApiModel(value="RenewDo",description="NFT续费数据")
public class RenewVo {

	@ApiModelProperty("续费代币")
	private BigInteger tokenId;

	@ApiModelProperty("有效期截至时间")
	private BigInteger renewTime;
}

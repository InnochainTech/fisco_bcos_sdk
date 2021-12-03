package inno.fisco.bcos.be.entity.request;

import inno.fisco.bcos.be.entity.RequestDo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigInteger;

/**
 * @author peifeng
 * @date 2021/11/11 17:29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="RenewDo",description="NFT续费数据")
public class RenewDo extends RequestDo {

	@ApiModelProperty(value = "合约管理员私钥（加密后的私钥）" ,required= true)
	private String privateKey;

	@ApiModelProperty(value = "续费代币" ,required= true)
	@Min(1)
	private BigInteger tokenId;

	@ApiModelProperty(value = "有效期截至时间(公链秒时间戳，bcos毫秒时间戳)" ,required= true)
	private BigInteger renewTime;
}

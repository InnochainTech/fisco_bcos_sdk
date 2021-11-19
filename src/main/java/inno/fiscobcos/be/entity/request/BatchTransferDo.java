package inno.fiscobcos.be.entity.request;

import inno.fiscobcos.be.entity.RequestDo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/15 16:52
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="BatchTransferDo",description="NFT批量转移数据")
public class BatchTransferDo extends RequestDo {

	@ApiModelProperty(value = "NFT拥有者私钥（加密后的私钥）" ,required= true)
	private String privateKey;

	@ApiModelProperty(value = "批量出售代币列表(目前最多150个，数量越多返回速度越慢，建议传50个)" ,required= true)
	private List<@Min(1)BigInteger> tokenIds;

	@ApiModelProperty(value = "转账接受者地址" ,required= true)
	@Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "转账接受者地址格式错误")
	private String to;
}

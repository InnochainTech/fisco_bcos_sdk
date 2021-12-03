package inno.fisco.bcos.be.entity.usesign.request;

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
 * @date 2021/11/11 11:37
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="BatchSellReq",description="NFT批量出售数据")
public class BatchSellReq {


	@ApiModelProperty(value = "批量出售代币列表(目前最多150个左右，数量越多返回速度越慢，建议传50个)" ,required= true)
	private List<@Min(1)BigInteger> tokenIds;

	@ApiModelProperty(value = "购买者地址",required= true)
	@Pattern(regexp = "^0[xX][0-9a-fA-F]{40}$", message = "购买者地址格式错误")
	private String to;

	@ApiModelProperty(value = "代币有效截至时间(公链秒时间戳，bcos毫秒时间戳)", example = "0")
	private BigInteger expirationTime = new BigInteger("0");
}

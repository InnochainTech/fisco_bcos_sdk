package inno.fisco.bcos.be.entity.usesign.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigInteger;

/**
 * @author peifeng
 * @date 2021/11/11 11:37
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="BatchMintReq",description="NFT批量铸造参数")
public class BatchMintReq{

	@Valid
	@ApiModelProperty(value = "铸造数量(目前最大支持150个)" ,required= true)
	@Min(1)
	private BigInteger supply;

	@ApiModelProperty(value = "图片显示地址" ,required= true)
	private String tokenURI;
}

package inno.fisco.bcos.be.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigInteger;

/**
 * @author peifeng
 * @date 2021/11/11 17:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="InitWriteOffDo",description="初始NFT核销数据")
public class InitWriteOffDo {

	@ApiModelProperty(value = "核销类型",required= true)
	@Min(1)
	private BigInteger type;

	@ApiModelProperty(value = "核销数量",required= true)
	@Min(1)
	private BigInteger supply;
}

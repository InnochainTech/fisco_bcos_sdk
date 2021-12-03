package inno.fisco.bcos.be.entity.response;

import inno.fisco.bcos.be.entity.ResponseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

/**
 * @author peifeng
 * @date 2021/11/11 11:37
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="BatchMintVo",description="NFT批量铸造返回数据")
public class BatchMintVo extends ResponseVo {

	@ApiModelProperty(value = "铸造成功返回的tokenIds")
	private List<BigInteger> tokenIds;
}

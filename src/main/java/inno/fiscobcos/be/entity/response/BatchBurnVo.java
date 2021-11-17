package inno.fiscobcos.be.entity.response;

import inno.fiscobcos.be.entity.ResponseVo;
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
@ApiModel(value="BatchBurnVo",description="NFT批量销毁返回数据")
public class BatchBurnVo extends ResponseVo {

	@ApiModelProperty(value = "是否销毁成功")
	private boolean  burnSuccess;
}

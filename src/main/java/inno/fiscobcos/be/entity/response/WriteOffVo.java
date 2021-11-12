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
@ApiModel(value="WriteOffVo",description="NFT核销返回数据")
public class WriteOffVo {

	@ApiModelProperty("是否核销成功")
	private boolean writeOffSuccess;
}

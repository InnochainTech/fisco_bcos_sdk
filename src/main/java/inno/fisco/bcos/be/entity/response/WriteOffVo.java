package inno.fisco.bcos.be.entity.response;

import inno.fisco.bcos.be.entity.ResponseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peifeng
 * @date 2021/11/11 17:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="WriteOffVo",description="NFT核销返回数据")
public class WriteOffVo extends ResponseVo {

	@ApiModelProperty("是否核销成功")
	private boolean writeOffSuccess;
}

package inno.fisco.bcos.be.entity.response;

import inno.fisco.bcos.be.entity.ResponseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peifeng
 * @date 2021/11/11 17:29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="RenewVo",description="NFT续费数据")
public class RenewVo extends ResponseVo {

	@ApiModelProperty("是否续费成功")
	private boolean renewSuccess;

}

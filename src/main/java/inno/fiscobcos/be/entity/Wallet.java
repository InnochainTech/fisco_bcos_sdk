package inno.fiscobcos.be.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peifeng
 * @date 2021/11/10 16:57
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="wallet",description="钱包信息")
public class Wallet {

	@ApiModelProperty("私钥")
	private String privateKey;

	@ApiModelProperty("公钥")
	private String publicKey;

	@ApiModelProperty("地址")
	private String address;

}

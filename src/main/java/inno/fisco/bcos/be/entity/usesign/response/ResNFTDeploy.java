package inno.fisco.bcos.be.entity.usesign.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peifeng
 * @date 2021/12/1 2:15 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="ResNFTDeploy",description="NFT合约部署返回数据")
public class ResNFTDeploy {

    @ApiModelProperty(value = "签名后数据" ,required= true)
    private String signDataStr;
}

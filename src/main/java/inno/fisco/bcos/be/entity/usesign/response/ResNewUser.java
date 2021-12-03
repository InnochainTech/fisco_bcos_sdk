package inno.fisco.bcos.be.entity.usesign.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peifeng
 * @date 2021/11/29 16:58
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="ResNewUser",description="新建用户返回数据")
public class ResNewUser {

    @ApiModelProperty(value = "用户编号" ,required = true)
    private String signUserId;

    @ApiModelProperty(value = "应用编号" ,required = true)
    private String appId;

    @ApiModelProperty(value = "账户地址" ,required = true)
    private String address;

    @ApiModelProperty(value = "公钥信息" ,required = true)
    private String publicKey;

    @ApiModelProperty(value = "私钥信息" ,required = true)
    private String privateKey;

    // @ApiModelProperty(value = "描述" ,required = true)
    // private String description;

    @ApiModelProperty(value = "加密类型" ,required = true)
    private Integer encryptType;
}

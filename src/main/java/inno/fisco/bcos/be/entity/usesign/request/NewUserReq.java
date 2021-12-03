package inno.fisco.bcos.be.entity.usesign.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * @author peifeng
 * @date 2021/11/29 16:47
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="ReqNewUser",description="新建用户请求数据")
public class NewUserReq {

    @ApiModelProperty(value = "应用编号(用于标志用户的应用编号,仅支持数字字母下划线,最大长度64)" ,required = true)
    @Pattern(regexp = "^[0-9a-zA-Z_]{1,64}$", message = "应用编号格式错误")
    private String appId;

    // @ApiModelProperty(value = "加密类型(默认为0，0: ECDSA, 1: 国密)", example = "0")
    // private Integer encryptType;

    // @ApiModelProperty(value = "是否返回私钥(默认false，true时返回aes加密的私钥)" ,example = "false")
    // private Boolean returnPrivateKey;

    @ApiModelProperty(value = "用户编号(私钥用户的唯一业务编号，仅支持数字字母下划线,最大长度64)" ,required = true)
    @Pattern(regexp = "^[0-9a-zA-Z_]{1,64}$", message = "用户编号格式错误")
    private String signUserId;
}

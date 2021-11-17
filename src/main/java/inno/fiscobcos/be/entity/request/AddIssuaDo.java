//package inno.fiscobcos.be.entity.request;
//
//import inno.fiscobcos.be.entity.RequestDo;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.validation.constraints.DecimalMin;
//import java.math.BigInteger;
//
///**
// * @author peifeng
// * @date 2021/11/11 17:29
// */
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@ApiModel(value="AddIssuaDo",description="NFT增发数据")
//public class AddIssuaDo extends RequestDo {
//
//	@ApiModelProperty(value = "合约管理员私钥" ,required= true)
//	private String privateKey;
//
//	@ApiModelProperty(value = "增发数量" ,required= true)
//	@DecimalMin("1")
//	private BigInteger addIssuaSupply;
//}

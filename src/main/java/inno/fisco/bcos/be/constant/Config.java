package inno.fisco.bcos.be.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author peifeng
 * @date 2021/11/1 13:54
 */
@Component
public class Config {

	@Value("${filePath.abi}")
	public String abiFilePath;

	@Value("${filePath.bin}")
	public String binFilePath;

	@Value("${rocketmq.work}")
	public Boolean rocketmqWork;

	@Value("${encrype.key}")
	public String encrypeKey;

	@Value("${bcos.config}")
	public String bcosConfig;

	@Value("${bcos.groupId}")
	public Integer bcosGroupId;

	@Value("${bcos.encryptType}")
	public String bcosEncryptType;

	@Value("${bcos.bin}")
	public String bcosBin;

	@Value("${bcos.abi}")
	public String bcosAbi;

	@Value("${bcos.chainId}")
	public Integer bcosChainId;

	@Value("${bcos.async}")
	public Integer bcosAsync;


}

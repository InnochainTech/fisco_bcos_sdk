package inno.fisco.bcos.be.util.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author peifeng
 * @date 2021/9/17 11:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfos {
	private String orderId;
	private List<ErrorInfo> errors;
}

package inno.fisco.bcos.be.util.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peifeng
 * @date 2021/9/17 11:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {
	private String propertyName;
	private String message;
	private String value;
}

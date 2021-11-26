package inno.fiscobcos.be.configuration;

import com.ejlchina.okhttps.Config;
import com.ejlchina.okhttps.HTTP;
import inno.fiscobcos.be.constant.Constant;

/**
 * @author peifeng
 * @date 2021/11/26 10:21
 */
public class OkHttpsConfig implements Config{

    @Override
    public void with(HTTP.Builder builder) {
        // 在这里对 HTTP.Builder 做一些自定义的配置
        builder.baseUrl(Constant.WEBASETRANSACTION_URL);
        // 如果项目中添加了 okhttps-fastjson 或 okhttps-gson 或 okhttps-jackson 依赖
        // OkHttps 会自动注入它们提供的 MsgConvertor
        // 所以这里就不需要再配置 MsgConvertor 了 (内部实现自动注入的原理也是 SPI)
        // 但如果没有添加这些依赖，那还需要自定义一个 MsgConvertor
        // builder.addMsgConvertor(new MyMsgConvertor());
    }


}

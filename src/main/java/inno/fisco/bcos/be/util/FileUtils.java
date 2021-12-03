package inno.fisco.bcos.be.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author peifeng
 * @date 2021/11/30 11:32 上午
 */
public class FileUtils {

    static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static String getFileString(String url){
        ClassPathResource resource = new ClassPathResource(url);
        InputStream inputStream;
        String result = null;
        try {
            inputStream = resource.getInputStream();
            result = IOUtils.toString(inputStream, String.valueOf(StandardCharsets.UTF_8));
        }catch (IOException e){
            logger.debug("[getContractBin] read file error,"+e);
        }
        return result;
    }

    public static void main(String[] args) {
        String fileString = FileUtils.getFileString("contract/abi/NFT.abi");
        System.out.println(fileString);
    }

}

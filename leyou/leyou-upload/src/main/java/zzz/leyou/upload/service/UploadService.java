package zzz.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
public class UploadService {
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif","image/jpeg","image/x-icon","image/png");//文件白名单
    private static final Logger LOGGER =  LoggerFactory.getLogger(UploadService.class);
    @Autowired
    private FastFileStorageClient storageClient;
    /**
     * 文件上传
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();//获取原始文件名
        //StringUtils.substringAfterLast(originalFilename,".");//最后一个点后面的字符串
        //校验文件类型
        String contentType = file.getContentType();//获取文件类型
        if (! CONTENT_TYPES.contains(contentType)){
            LOGGER.info("文件类型不合法：{}",originalFilename);
            return null;
        }
        //校验文件的内容 ImageIO工具类读取文件类型
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        if (bufferedImage == null ){
            LOGGER.info("文件内容不合法：{}",originalFilename);
            return null;
        }
        //保存到文件的服务器
        String ext = StringUtils.substringAfterLast(originalFilename, ".");
        StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
        //file.transferTo(new File("D:\\IdeaProject\\springboot\\image\\"+originalFilename));
        //返回url，进行回显
        String url = "http://image.leyou.com/";
        //return url;
        return url+storePath.getFullPath();
    }
}

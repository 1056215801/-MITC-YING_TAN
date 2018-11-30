package com.mit.community.util;

import com.mit.community.config.FastDFSConf;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件上传工具类
 *
 * @author shuyy
 * @date 2018年8月18日
 */
public class FastDFSClient {

    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient1 storageClient = null;
    /**
     * client.conf的地址
     */
    private static final String CLIENT_URL = "classpath:config/FastDFS/client.conf";

    public static FastDFSClient getInstance() {
        try {
            return new FastDFSClient(CLIENT_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FastDFSClient(String conf) throws Exception {
        String classpathHead = "classpath:";
        if (conf.contains(classpathHead)) {
            conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
        }
        ClientGlobal.init(conf);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     *
     * @param fileName 文件全路径
     * @param extName  文件扩展名，不包含（.）
     * @param metas    文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient.upload_file1(fileName, extName, metas);
        return result;
    }

    public String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null, null);
    }

    public String uploadFile(String fileName, String extName) throws Exception {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     *
     * @param fileContent 文件的内容，字节数组
     * @param extName     文件扩展名
     * @param metas       文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {

        String result = storageClient.upload_file1(fileContent, extName, metas);
        return result;
    }

    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    public String uploadFile(byte[] fileContent, String extName) throws Exception {
        return uploadFile(fileContent, extName, null);
    }

    /**
     * 上传文件方法。
     * 上传文件的时候记得保存好文件名称
     *
     * @param @param  file
     * @param @return
     * @param @throws Exception
     * @return String 返回完整的带有nginx地址的url地址。
     * @throws
     */
    public String uploadFile(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        // 取扩展名
        String extName = filename.substring(filename.lastIndexOf(".") + 1);
        String url = uploadFile(file.getBytes(), extName);
        return FastDFSConf.nginxUrl + "/" + url;
    }
}

package com.dsb.hadoop;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.dsb.hadoop.test.TestFileStreamController;
import com.dsb.hadoop.test.TestIConnectionController;
import com.dsb.hadoop.test.TestIFileController;
import com.dsb.hadoop.test.TestIStorageController;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import paas.storage.distributedFileSystem.connection.response.CreateResponse;
import paas.storage.distributedFileSystem.fileStream.response.WriteResponse;

import java.io.File;
import java.io.InputStream;

/**
 * @author luowei
 * Creation time 2021/1/28 15:59
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHadoopApplication {

    private static final String token = "UBMCZZDPMXHOFVXBKYPB|UbVExcLt0RmS0Je8TBUfUr1AiHRllkaktC6osRox";

    private static final String upload_path = "D:\\hadoop.txt";

    @Autowired
    private TestIConnectionController testIConnectionController;

    @Autowired
    private TestFileStreamController testFileStreamController;

    @Autowired
    private TestIFileController testIFileController;

    @Autowired
    private TestIStorageController testIStorageController;

    @Test
    @SneakyThrows
    public void test1() {
        //创建链接
        CreateResponse createResponse = testIConnectionController.create("1", token, null);
        //创建目录
        paas.storage.distributedFileSystem.file.response.CreateResponse iFileCreateResponse = testIFileController.create(createResponse.getConnectionId(), UUID.randomUUID().toString());

        //拼接上传文件
        File file = FileUtil.file(upload_path);
        String type = FileUtil.getType(file);
        String hdfsFilePath = iFileCreateResponse.getFilePath() + "/" + UUID.randomUUID().toString() + "." + type;

        //创建输出流
        paas.storage.distributedFileSystem.fileStream.response.CreateResponse outCreateResponse = testFileStreamController.create(createResponse.getConnectionId(),
                hdfsFilePath, 2, 2);

        //获取上传文件流
        InputStream inputStream = FileUtil.getInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        WriteResponse writeResponse = testFileStreamController.write(outCreateResponse.getStreamId(), bytes, 0, bytes.length);

        //关闭比上传文件流
        testFileStreamController.close(outCreateResponse.getStreamId());
        //查看文件夹
        testIFileController.getFileList(createResponse.getConnectionId(), iFileCreateResponse.getFilePath(), null, 1);
        //查看文件是否存在
        testIFileController.fileExist(createResponse.getConnectionId(), hdfsFilePath);
        //获取文件属性
        testIFileController.getFileInfo(createResponse.getConnectionId(), hdfsFilePath);
        //设置权限
        testIFileController.setAuthority(hdfsFilePath, "", "doushabao", "777", 1);

        //创建输入流
        paas.storage.distributedFileSystem.fileStream.response.CreateResponse inputCreateResponse =
                testFileStreamController.create(createResponse.getConnectionId(), hdfsFilePath, 1, 1);
        //读取文件
        testFileStreamController.readlines(inputCreateResponse.getStreamId(), 1);
        //关闭输入流
        testFileStreamController.close(inputCreateResponse.getStreamId());
        //删除文件
        testIFileController.delete(createResponse.getConnectionId(), hdfsFilePath, 2, 1);
        //关闭
        testIConnectionController.close(createResponse.getConnectionId());

    }
}

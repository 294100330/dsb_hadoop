package com.dsb.hadoop;

import cn.hutool.core.io.FileUtil;
import com.dsb.hadoop.test.TestFileStreamController;
import com.dsb.hadoop.test.TestIConnectionController;
import com.dsb.hadoop.test.TestIFileController;
import com.dsb.hadoop.test.TestIStorageController;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import paas.storage.distributedFileSystem.connection.response.CreateResponse;
import paas.storage.distributedFileSystem.fileStream.response.ReadlinesResponse;

import java.io.InputStream;

/**
 * @author luowei
 * Creation time 2021/1/28 15:59
 */
@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHadoopApplication {

    private static final String token = "UBMCZZDPMXHOFVXBKYPB|UbVExcLt0RmS0Je8TBUfUr1AiHRllkaktC6osRox";

    private static final String upload_path = "D:\\hadoop.txt";

    private CreateResponse createResponse;

    @Autowired
    private TestIConnectionController testIConnectionController;

    @Autowired
    private TestFileStreamController testFileStreamController;

    @Autowired
    private TestIFileController testIFileController;

    @Autowired
    private TestIStorageController testIStorageController;

    @Before
    public void setUp() {
        //创建链接
        createResponse = testIConnectionController.create("1", token, null);
    }

    @Test
    public void test3() {
        try {
            String path = "doushabao3/hadoop.txt";
            paas.storage.distributedFileSystem.fileStream.response.CreateResponse createResponse1 =
                    testFileStreamController.create(createResponse.getConnectionId(), path, 2, 2);
            InputStream inputStream = FileUtil.getInputStream("D:\\hadoop.txt");
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            testFileStreamController.write(createResponse1.getStreamId(),bytes,0,bytes.length);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}

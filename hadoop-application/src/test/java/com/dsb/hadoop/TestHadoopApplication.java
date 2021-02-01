package com.dsb.hadoop;

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
import paas.storage.distributedFileSystem.connection.response.CreateResponse;

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
            String path = "doushabao";
            testIFileController.move(createResponse.getConnectionId(), "doushabao1/hadoop1.txt", "doushabao/hadoop.txt", 1, 1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}

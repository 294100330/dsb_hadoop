package com.dsb.hadoop;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.dsb.hadoop.test.TestFileStreamController;
import com.dsb.hadoop.test.TestIConnectionController;
import com.dsb.hadoop.test.TestIFileController;
import com.dsb.hadoop.test.TestIStorageController;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import paas.storage.distributedFileSystem.connection.response.CreateResponse;
import paas.storage.distributedFileSystem.fileStream.response.ReadResponse;
import paas.storage.distributedFileSystem.fileStream.response.ReadlinesResponse;

import java.util.Date;

/**
 * @author luowei
 * Creation time 2021/1/28 15:59
 */
@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHadoopApplication {

    /**
     * token
     */
    private static final String token = "UBMCZZDPMXHOFVXBKYPB|UbVExcLt0RmS0Je8TBUfUr1AiHRllkaktC6osRox";

    /**
     * 链接
     */
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
    public void beforeUp() {
        //创建链接
        createResponse = testIConnectionController.create(IdUtil.simpleUUID(), token, null);
    }

    @After
    public void afterUp() {
        testIConnectionController.close(createResponse.getConnectionId());
    }

    /**
     * 获得上传文件bytes
     *
     * @return
     */
    private byte[] getUploadBytes() {
        return FileUtil.readBytes(FileUtil.file("hadoop.txt"));
    }

    /**
     * 创建目录
     */
    @Test
    public void createDirectory() {
        try {
            //创建链接
            String connectionId = createResponse.getConnectionId();
            String file = IdUtil.fastUUID();
            //创建目录
            paas.storage.distributedFileSystem.file.response.CreateResponse createResponse = testIFileController.create(connectionId, file);
            //查看目录
            testIFileController.getFileInfo(connectionId, file);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 重命名文件
     */
    @Test
    public void rename() {
        try {
            String connectionId = createResponse.getConnectionId();
            String srcPath = IdUtil.fastUUID();
            //创建目录
            paas.storage.distributedFileSystem.file.response.CreateResponse createResponse = testIFileController.create(connectionId, srcPath);
            //查看目录
            testIFileController.getFileInfo(connectionId, srcPath);
            String dstPath = IdUtil.fastUUID();
            //重命名文件
            testIFileController.rename(connectionId, srcPath, dstPath);
            //查看目录
            testIFileController.getFileInfo(connectionId, dstPath);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 移动文件
     */
    @Test
    public void move() {
        try {
            String connectionId = createResponse.getConnectionId();
            String srcPath = IdUtil.fastUUID() + "/" + IdUtil.fastUUID() + ".txt";
            String dstPath = IdUtil.fastUUID() + "/" + IdUtil.fastUUID() + ".txt";
            //创建文件输出流
            paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                    testFileStreamController.create(connectionId, srcPath
                            , 2, 2);
            byte[] bytes = this.getUploadBytes();
            //写入
            testFileStreamController.write(fileStreamCreateResponse.getStreamId(), bytes, 0, bytes.length);
            //关闭
            testFileStreamController.close(fileStreamCreateResponse.getStreamId());
            //移动
            testIFileController.move(connectionId, srcPath, dstPath, 1, 1);
            //查看目录
            testIFileController.getFileInfo(connectionId, dstPath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取文件列表
     */
    @Test
    public void getFileList() {
        try {
            Integer i = 1;
            String path = IdUtil.fastUUID();
            String connectionId = createResponse.getConnectionId();

            do {
                String srcPath = path + "/" + IdUtil.fastUUID() + ".txt";
                //创建文件输出流
                paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                        testFileStreamController.create(connectionId, srcPath
                                , 2, 2);
                byte[] bytes = this.getUploadBytes();
                //写入
                testFileStreamController.write(fileStreamCreateResponse.getStreamId(), bytes, 0, bytes.length);
                //关闭
                testFileStreamController.close(fileStreamCreateResponse.getStreamId());
                i += 1;
            } while (i <= 5);
            testIFileController.getFileList(connectionId, path, null, 1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 判断文件是否存在
     */
    @Test
    public void fileExist() {
        String connectionId = createResponse.getConnectionId();
        try {
            String srcPath = IdUtil.fastUUID() + "/" + IdUtil.fastUUID() + ".txt";
            //创建文件输出流
            paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                    testFileStreamController.create(connectionId, srcPath
                            , 2, 2);
            byte[] bytes = this.getUploadBytes();
            //写入
            testFileStreamController.write(fileStreamCreateResponse.getStreamId(), bytes, 0, bytes.length);
            //关闭
            testFileStreamController.close(fileStreamCreateResponse.getStreamId());
            //判断是否存在
            testIFileController.fileExist(connectionId, srcPath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取文件属性
     */
    @Test
    public void getFileInfo() {
        String connectionId = createResponse.getConnectionId();
        try {
            String srcPath = IdUtil.fastUUID() + "/" + IdUtil.fastUUID() + ".txt";
            //创建文件输出流
            paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                    testFileStreamController.create(connectionId, srcPath
                            , 2, 2);
            byte[] bytes = this.getUploadBytes();
            //写入
            testFileStreamController.write(fileStreamCreateResponse.getStreamId(), bytes, 0, bytes.length);
            //关闭
            testFileStreamController.close(fileStreamCreateResponse.getStreamId());
            //获取文件属性
            testIFileController.getFileInfo(connectionId, srcPath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 文件权限设置
     */
    @Test
    public void setAuthority() {
        String connectionId = createResponse.getConnectionId();
        try {
            String srcPath = IdUtil.fastUUID() + "/" + IdUtil.fastUUID() + ".txt";
            //创建文件输出流
            paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                    testFileStreamController.create(connectionId, srcPath
                            , 2, 2);
            byte[] bytes = this.getUploadBytes();
            //写入
            testFileStreamController.write(fileStreamCreateResponse.getStreamId(), bytes, 0, bytes.length);
            //关闭
            testFileStreamController.close(fileStreamCreateResponse.getStreamId());
            //权限
            testIFileController.setAuthority(srcPath, "doushabao", "doushabao", "777", 1);
            //获取文件属性
            testIFileController.getFileInfo(connectionId, srcPath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    @Test
    public void delete() {
        String connectionId = createResponse.getConnectionId();
        try {
            String srcPath = IdUtil.fastUUID() + "/" + IdUtil.fastUUID() + ".txt";
            //创建文件输出流
            paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                    testFileStreamController.create(connectionId, srcPath
                            , 2, 2);
            byte[] bytes = this.getUploadBytes();
            //写入
            testFileStreamController.write(fileStreamCreateResponse.getStreamId(), bytes, 0, bytes.length);
            //关闭
            testFileStreamController.close(fileStreamCreateResponse.getStreamId());
            //删除
            testIFileController.delete(connectionId, srcPath, 2, 1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * 写入文件
     */
    @Test
    public void write() {
        try {
            //创建链接
            String connectionId = createResponse.getConnectionId();
            //创建目录
            paas.storage.distributedFileSystem.file.response.CreateResponse createResponse =
                    testIFileController.create(connectionId, String.format("%s-doushabao", DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT)));
            //创建文件输出流
            paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                    testFileStreamController.create(connectionId,
                            createResponse.getFilePath() + "/" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".txt", 2, 2);
            byte[] bytes = this.getUploadBytes();
            //写入
            testFileStreamController.write(fileStreamCreateResponse.getStreamId(), bytes, 0, bytes.length);
            //关闭
            testFileStreamController.close(fileStreamCreateResponse.getStreamId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 写入字符串
     */
    @Test
    public void writeline() {
        //创建链接
        String connectionId = createResponse.getConnectionId();
        //创建目录
        paas.storage.distributedFileSystem.file.response.CreateResponse createResponse =
                testIFileController.create(connectionId, String.format("%s-doushabao", DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT)));
        //创建文件输出流
        paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                testFileStreamController.create(connectionId,
                        createResponse.getFilePath() + "/" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".txt", 2, 2);
        //逐行写入
        testFileStreamController.writeline(fileStreamCreateResponse.getStreamId(), "hello-doushabao");
        //关闭
        testFileStreamController.close(fileStreamCreateResponse.getStreamId());
    }

    /**
     * 读取字符串
     */
    @Test
    public void read() {
        //创建链接
        String connectionId = createResponse.getConnectionId();
        //写入文件
        String path = null;
        {
            //创建目录
            paas.storage.distributedFileSystem.file.response.CreateResponse createResponse =
                    testIFileController.create(connectionId, String.format("%s-doushabao", DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT)));
            path = createResponse.getFilePath() + "/" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".txt";
            //创建文件输出流
            paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                    testFileStreamController.create(connectionId, path, 2, 2);
            //逐行写入
            testFileStreamController.writeline(fileStreamCreateResponse.getStreamId(), "read-doushabao");
            //关闭
            testFileStreamController.close(fileStreamCreateResponse.getStreamId());
        }
        //读取
        {
            byte[] bytes = new byte[1024];
            //创建文件输出流
            paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                    testFileStreamController.create(connectionId,
                            path, 1, 2);
            ReadResponse readResponse = testFileStreamController.read(fileStreamCreateResponse.getStreamId(), bytes, 0, bytes.length);
            testFileStreamController.close(fileStreamCreateResponse.getStreamId());
            String s = new String(bytes);
            System.out.println(s);
        }
    }

    /**
     * 读取字符串
     */
    @Test
    public void readlines() {
        //创建链接
        String connectionId = createResponse.getConnectionId();
        //写入文件
        String path = null;
        {
            //创建目录
            paas.storage.distributedFileSystem.file.response.CreateResponse createResponse =
                    testIFileController.create(connectionId, String.format("%s-doushabao", DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT)));
            path = createResponse.getFilePath() + "/" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".txt";
            //创建文件输出流
            paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                    testFileStreamController.create(connectionId, path, 2, 2);
            //逐行写入
            testFileStreamController.writeline(fileStreamCreateResponse.getStreamId(), "readlines-doushabao");
            //关闭
            testFileStreamController.close(fileStreamCreateResponse.getStreamId());
        }
        //读取
        {
            //创建文件输出流
            paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                    testFileStreamController.create(connectionId,
                            path, 1, 2);
            ReadlinesResponse readResponse = testFileStreamController.readlines(fileStreamCreateResponse.getStreamId(), 1);
            testFileStreamController.close(fileStreamCreateResponse.getStreamId());
        }
    }

    /**
     *存储容量及使用量
     */
    @Test
    public void getStorageInfo() {
        //创建链接
        String connectionId = createResponse.getConnectionId();
        String path = IdUtil.fastUUID();
        try {
            Integer i = 1;
            do {
                String srcPath = path + "/" + IdUtil.fastUUID() + ".txt";
                //创建文件输出流
                paas.storage.distributedFileSystem.fileStream.response.CreateResponse fileStreamCreateResponse =
                        testFileStreamController.create(connectionId, srcPath
                                , 2, 2);
                byte[] bytes = this.getUploadBytes();
                //写入
                testFileStreamController.write(fileStreamCreateResponse.getStreamId(), bytes, 0, bytes.length);
                //关闭
                testFileStreamController.close(fileStreamCreateResponse.getStreamId());
                i+=1;
            }
            while (i<=5);
            testIStorageController.getStorageInfo(connectionId,path);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}

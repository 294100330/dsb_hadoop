package com.dsb.hadoop.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Configuration;
import paas.storage.connection.Response;
import paas.storage.distributedFileSystem.IFileStream;
import paas.storage.distributedFileSystem.fileStream.response.*;

import java.nio.charset.Charset;

/**
 * 文件流管理 接口层
 *
 * @author 豆沙包
 * Creation time 2021/1/25 14:17
 */
@Configuration
public class TestFileStreamController {

    @Autowired
    private IFileStream iFileStream;


    /**
     * 创建流
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填文件路径 文件的绝对路径。
     * @param streamType   必填 流类型 1：输入流；2:输出流。
     * @param mode         可选 写入模式 1表示追加，2表示覆盖。
     * @return
     */
    public CreateResponse create(String connectionId, String filePath, int streamType, int mode) {
        System.out.println("====================== 文件流管理 创建流 开始 ============================");
        CreateResponse createResponse = iFileStream.create(connectionId, filePath, streamType, mode);
        System.out.println(createResponse);
        System.out.println("====================== 文件流管理 创建流 结束 ============================");
        return createResponse;
    }

    /**
     * 文件流读取
     *
     * @param streamId 必填 流对象唯一标识
     * @return
     */
    public ReadResponse read(String streamId, byte[] byteArray, int offSet, int length) {
        System.out.println("====================== 文件流管理 文件流读取 开始 ============================");
        ReadResponse readResponse = iFileStream.read(streamId, byteArray, offSet, length);
        System.out.println(readResponse);
        System.out.println("====================== 文件流管理 文件流读取 结束 ============================");
        return readResponse;
    }

    /**
     * 逐行读取
     *
     * @param streamId   必填 流对象唯一标识
     * @param readMethod 必填 读取方法 1表示逐行读取；2 表示全部读取。
     * @return
     */
    public ReadlinesResponse readlines(String streamId, int readMethod) {
        System.out.println("====================== 文件流管理 逐行读取 开始 ============================");
        ReadlinesResponse readlinesResponse = iFileStream.readlines(streamId, Charset.defaultCharset().displayName(), readMethod);
        System.out.println(readlinesResponse);
        System.out.println("====================== 文件流管理 逐行读取 结束 ============================");
        return readlinesResponse;
    }

    /**
     * 文件流写入
     *
     * @param streamId 必填 流对象唯一标识
     * @return
     */
    public WriteResponse write(String streamId, byte[] byteArray, int offSet, int length) {
        System.out.println("====================== 文件流管理 文件流写入 开始 ============================");
        WriteResponse writeResponse = iFileStream.write(streamId, byteArray, offSet, length);
        System.out.println(writeResponse);
        System.out.println("====================== 文件流管理 文件流写入 结束 ============================");
        return writeResponse;
    }

    /**
     * 逐行写入
     *
     * @param streamId 必填 流对象唯一标识
     * @param string   必填 字符串
     * @return
     */
    public Response writeline(String streamId, String string) {
        System.out.println("====================== 文件流管理 逐行写入 开始 ============================");
        Response response = iFileStream.writeline(streamId, string);
        System.out.println(response);
        System.out.println("====================== 文件流管理 逐行写入 结束 ============================");
        return response;
    }

    /**
     * 关闭流
     *
     * @param streamId 必填 流对象唯一标识
     * @return
     */
    public CloseResponse close(String streamId) {
        System.out.println("====================== 文件流管理 关闭流 开始 ============================");
        CloseResponse closeResponse = iFileStream.close(streamId);
        System.out.println(closeResponse);
        System.out.println("====================== 文件流管理 关闭流 结束 ============================");
        return closeResponse;
    }

}

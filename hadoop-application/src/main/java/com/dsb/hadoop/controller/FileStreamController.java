package com.dsb.hadoop.controller;

import com.dsb.hadoop.dto.from.FileCreateFrom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import paas.storage.connection.Response;
import paas.storage.distributedFileSystem.IFileStream;
import paas.storage.distributedFileSystem.fileStream.response.*;

import java.nio.charset.Charset;

/**
 * 文件流管理 接口层
 *
 * @author luowei
 * Creation time 2021/1/25 14:17
 */
@Api(tags = "文件流管理 API")
@RestController
@RequestMapping("file_stream")
public class FileStreamController {

    @Autowired
    private IFileStream iFileStream;


    /**
     * 创建流
     *
     * @param fileCreateFrom 必填 文件系统连接标识
     * @return
     */
    @PostMapping("create")
    @ApiOperation("创建流")
    public CreateResponse create(FileCreateFrom fileCreateFrom) {
        return iFileStream.create(fileCreateFrom.getConnectionId(), fileCreateFrom.getFilePath(), fileCreateFrom.getStreamType().getCode(), fileCreateFrom.getMode().getCode());
    }

    /**
     * 文件流读取
     *
     * @param streamId 必填 流对象唯一标识
     * @return
     */
    @PostMapping("read")
    @ApiOperation("文件流读取")
    public ReadResponse read(String streamId) {
        byte[] bytes = new byte[0];
        return iFileStream.read(streamId, bytes, 0, bytes.length);
    }

    /**
     * 逐行读取
     *
     * @param streamId   必填 流对象唯一标识
     * @param readMethod 必填 读取方法 1表示逐行读取；2 表示全部读取。
     * @return
     */
    @PostMapping("readlines")
    @ApiOperation("逐行读取")
    public ReadlinesResponse readlines(String streamId, int readMethod) {
        return iFileStream.readlines(streamId, Charset.defaultCharset().displayName(), readMethod);
    }

    /**
     * 文件流写入
     *
     * @param streamId 必填 流对象唯一标识
     * @return
     */
    @SneakyThrows
    @PostMapping("write")
    @ApiOperation("文件流写入")
    public WriteResponse write(String streamId, MultipartFile file) {
        byte[] bytes = file.getBytes();
        return iFileStream.write(streamId, bytes, 0, bytes.length);
    }

    /**
     * 逐行写入
     *
     * @param streamId 必填 流对象唯一标识
     * @param string   必填 字符串
     * @return
     */
    @PostMapping("writeline")
    @ApiOperation("逐行写入")
    public Response writeline(String streamId, String string) {
        return iFileStream.writeline(streamId, string);

    }

    /**
     * 关闭流
     *
     * @param streamId 必填 流对象唯一标识
     * @return
     */
    @PostMapping("close")
    @ApiOperation("关闭流")
    public CloseResponse close(String streamId) {
        return iFileStream.close(streamId);
    }

}

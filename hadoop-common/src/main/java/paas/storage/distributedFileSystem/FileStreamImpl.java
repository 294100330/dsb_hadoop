package paas.storage.distributedFileSystem;

import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import paas.storage.component.IFileInputStreamService;
import paas.storage.component.IFileOutStreamService;
import paas.storage.connection.Response;
import paas.storage.distributedFileSystem.fileStream.response.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 文件流管理 实现层
 *
 * @author luowei
 * Creation time 2021/1/23 19:10
 */
@Log4j2
@Configuration
public class FileStreamImpl implements IFileStream {

    @Autowired
    private IFileInputStreamService iFileInputStreamService;

    @Autowired
    private IFileOutStreamService iFileOutStreamService;

    /**
     * 创建流
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填文件路径 文件的绝对路径。
     * @param streamType   必填 流类型 1：输入流；2:输出流。
     * @param mode         可选 写入模式 1表示追加，2表示覆盖。
     * @return
     */
    @Override
    public CreateResponse create(String connectionId, String filePath, int streamType, int mode) {
        String streamId = null;
        if (1 == streamType) {
            streamId = iFileInputStreamService.create(connectionId, filePath);
        } else if (2 == streamType) {
            streamId = iFileOutStreamService.create(connectionId, filePath);
        }
        CreateResponse createResponse = new CreateResponse();
        createResponse.setStreamId(streamId);
        return createResponse;
    }

    /**
     * 文件流读取
     *
     * @param streamId  必填 流对象唯一标识
     * @param byteArray 必填 字节数组
     * @param offSet    必填 偏移量
     * @param length    必填 长度
     * @return
     */
    @Override
    public ReadResponse read(String streamId, byte[] byteArray, int offSet, int length) {
        FSDataInputStream fsDataInputStream = iFileInputStreamService.get(streamId);
        try {
            fsDataInputStream.read(byteArray, offSet, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadResponse readResponse = new ReadResponse();
        readResponse.setLength(length);
        return readResponse;
    }

    /**
     * 逐行读取
     *
     * @param streamId   必填 流对象唯一标识
     * @param encode     可选 文件编码
     * @param readMethod 必填 读取方法 1表示逐行读取；2 表示全部读取。
     * @return
     */
    @Override
    public ReadlinesResponse readlines(String streamId, String encode, int readMethod) {
        FSDataInputStream fsDataInputStream = iFileInputStreamService.get(streamId);
        // 防止中文乱码
        BufferedReader bf = new BufferedReader(new InputStreamReader(fsDataInputStream));
        String line = null;
        while (true) {
            try {
                if (((line = bf.readLine()) != null)) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ReadlinesResponse readlinesResponse = new ReadlinesResponse();
        readlinesResponse.setStringList(new String(line.getBytes(), StandardCharsets.UTF_8));
        return readlinesResponse;
    }

    /**
     * 文件流写入
     *
     * @param streamId  必填 流对象唯一标识
     * @param byteArray 必填 字节数组
     * @param offSet    必填 偏移量
     * @param length    必填 长度
     * @return
     */
    @Override
    public WriteResponse write(String streamId, byte[] byteArray, int offSet, int length) {
        FSDataOutputStream fsDataOutputStream = iFileOutStreamService.get(streamId);
        try {
            fsDataOutputStream.write(byteArray, offSet, offSet);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        WriteResponse writeResponse = new WriteResponse();
        writeResponse.setLength(length);
        return writeResponse;
    }

    /**
     * 逐行写入
     *
     * @param streamId 必填 流对象唯一标识
     * @param string   必填 字符串
     * @return
     */
    @Override
    public Response writeline(String streamId, String string) {
        FSDataOutputStream fsDataOutputStream = iFileOutStreamService.get(streamId);

        // 以UTF-8格式写入文件，不乱码
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fsDataOutputStream, StandardCharsets.UTF_8));
        try {
            writer.write(string);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        Response response = new Response();
        return response;
    }

    /**
     * 关闭流
     *
     * @param streamId 必填 流对象唯一标识
     * @return
     */
    @Override
    public CloseResponse close(String streamId) {
        iFileOutStreamService.delete(streamId);
        iFileInputStreamService.delete(streamId);
        CloseResponse closeResponse = new CloseResponse();
        closeResponse.setStreamId(streamId);
        return closeResponse;
    }


}

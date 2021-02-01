package paas.storage.distributedFileSystem;

import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import paas.storage.component.IFileInputStreamService;
import paas.storage.component.IFileOutStreamService;
import paas.storage.connection.Response;
import paas.storage.distributedFileSystem.fileStream.response.*;
import paas.storage.utils.AssertUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 文件流管理 实现层
 *
 * @author 豆沙包
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
     * @param filePath     必填 文件路径 文件的绝对路径。
     * @param streamType   必填 流类型 1：输入流；2:输出流。
     * @param mode         可选 写入模式 1表示追加，2表示覆盖。
     * @return
     */
    @Override
    public CreateResponse create(String connectionId, String filePath, int streamType, int mode) {
        CreateResponse createResponse = new CreateResponse();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(connectionId), "connectionId:文件系统连接标识不能为空");
            AssertUtils.charLengthLe(connectionId, 1024, "connectionId:文件系统连接标识字符定长不能超过1024");

            AssertUtils.isTrue(!StringUtils.isEmpty(filePath), "filePath:文件路径不能为空");
            AssertUtils.charLengthLe(filePath, 1024, "filePath:文件路径符定长不能超过1024");

            AssertUtils.isTrue(1 == streamType || 2 == streamType, "streamType:流类型限定在1或2");

            String streamId = null;
            if (1 == streamType) {
                streamId = iFileInputStreamService.create(connectionId, filePath);
            } else if (2 == streamType) {
                streamId = iFileOutStreamService.create(connectionId, filePath, mode);
            }
            createResponse.setTaskStatus(1);
            createResponse.setStreamId(streamId);
        } catch (Exception e) {
            createResponse.setTaskStatus(0);
            createResponse.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }

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
        ReadResponse readResponse = new ReadResponse();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(streamId), "streamId:流对象唯一标识不能为空");
            AssertUtils.charLengthLe(streamId, 1024, "streamId:流对象唯一标识字符定长不能超过1024");

            AssertUtils.isTrue(byteArray != null, "byteArray:字节数组不能为空");

            FSDataInputStream fsDataInputStream = iFileInputStreamService.get(streamId);
            fsDataInputStream.read(byteArray,offSet,length);
            readResponse.setLength(byteArray.length);
        } catch (IOException e) {
            readResponse.setTaskStatus(0);
            readResponse.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
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
        ReadlinesResponse readlinesResponse = new ReadlinesResponse();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(streamId), "streamId:流对象唯一标识不能为空");
            AssertUtils.charLengthLe(streamId, 1024, "streamId:流对象唯一标识字符定长不能超过1024");

            AssertUtils.isTrue(1 == readMethod || 2 == readMethod, "readMethod:读取方法限定在1或2");

            encode = !StringUtils.isEmpty(encode) ? encode : Charset.defaultCharset().name();


            FSDataInputStream fsDataInputStream = iFileInputStreamService.get(streamId);
            //1表示逐行读取
            if (1 == readMethod) {
                StringBuilder stringBuilder = new StringBuilder();
                // 防止中文乱码
                BufferedReader bf = new BufferedReader(new InputStreamReader(fsDataInputStream));
                String line = null;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(new String(line.getBytes(), encode));
                }
                readlinesResponse.setStringList(stringBuilder.toString());
                //2 表示全部读取。
            } else if (2 == readMethod) {
                byte[] bytes = new byte[fsDataInputStream.available()];
                fsDataInputStream.read(bytes);
                readlinesResponse.setStringList(new String(bytes, encode));
            }
            readlinesResponse.setTaskStatus(1);
        } catch (Exception e) {
            readlinesResponse.setTaskStatus(0);
            readlinesResponse.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }

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
        WriteResponse writeResponse = new WriteResponse();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(streamId), "streamId:流对象唯一标识不能为空");
            AssertUtils.charLengthLe(streamId, 1024, "streamId:流对象唯一标识字符定长不能超过1024");

            AssertUtils.isTrue(byteArray != null, "byteArray:字节数组不能为空");

            IFileOutStreamService.IFileOutStreamData iFileOutStreamData = iFileOutStreamService.get(streamId);
            FSDataOutputStream fsDataOutputStream = iFileOutStreamData.getFsDataOutputStream();
            fsDataOutputStream.write(byteArray, offSet, length);
            writeResponse.setTaskStatus(1);
            writeResponse.setLength(byteArray.length);
        } catch (IOException e) {
            writeResponse.setTaskStatus(0);
            writeResponse.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
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
        Response response = new Response();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(streamId), "streamId:流对象唯一标识不能为空");
            AssertUtils.charLengthLe(streamId, 1024, "streamId:流对象唯一标识字符定长不能超过1024");

            AssertUtils.isTrue(!StringUtils.isEmpty(string), "string:字符串不能为空");
            AssertUtils.charLengthLe(string, 4000, "string:字符串字符定长不能超过4000");

            IFileOutStreamService.IFileOutStreamData iFileOutStreamData = iFileOutStreamService.get(streamId);
            FileSystem fileSystem = iFileOutStreamData.getFileSystem();
            FSDataInputStream fsDataInputStream = fileSystem.open(iFileOutStreamData.getFilePath());
            FSDataOutputStream fsDataOutputStream = iFileOutStreamData.getFsDataOutputStream();
            IOUtils.copyBytes(fsDataInputStream, fsDataOutputStream, 4096);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fsDataOutputStream.getWrappedStream(), StandardCharsets.UTF_8);
//             以UTF-8格式写入文件，不乱码
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            writer.write(string);
            response.setTaskStatus(1);
        } catch (Exception e) {
            response.setTaskStatus(0);
            response.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
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
        CloseResponse closeResponse = new CloseResponse();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(streamId), "streamId:流对象唯一标识不能为空");
            AssertUtils.charLengthLe(streamId, 1024, "streamId:流对象唯一标识字符定长不能超过1024");

            iFileOutStreamService.delete(streamId);
            iFileInputStreamService.delete(streamId);
            closeResponse.setStreamId(streamId);
            closeResponse.setTaskStatus(1);
        } catch (Exception e) {
            closeResponse.setTaskStatus(0);
            closeResponse.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return closeResponse;
    }
}

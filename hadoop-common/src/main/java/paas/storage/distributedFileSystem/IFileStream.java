package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.fileStream.response.*;
import paas.storage.connection.Response;

/**
 * 文件流管理 服务层
 *
 * @author luowei
 * Creation time 2021/1/23 19:10
 */
public interface IFileStream {

    /**
     * 创建流
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填文件路径 文件的绝对路径。
     * @param streamType   必填 流类型 1：输入流；2:输出流。
     * @param mode         可选 写入模式 1表示追加，2表示覆盖。
     * @return
     */
    CreateResponse create(String connectionId, String filePath, int streamType, int mode);

    /**
     * 文件流读取
     *
     * @param streamId  必填 流对象唯一标识
     * @param byteArray 必填 字节数组
     * @param offSet    必填 偏移量
     * @param length    必填 长度
     * @return
     */
    ReadResponse read(String streamId, byte[] byteArray, int offSet, int length);

    /**
     * 逐行读取
     *
     * @param streamId   必填 流对象唯一标识
     * @param encode     可选 文件编码
     * @param readMethod 必填 读取方法 1表示逐行读取；2 表示全部读取。
     * @return
     */
    ReadlinesResponse readlines(String streamId, String encode, int readMethod);

    /**
     * 文件流写入
     *
     * @param streamId  必填 流对象唯一标识
     * @param byteArray 必填 字节数组
     * @param offSet    必填 偏移量
     * @param length    必填 长度
     * @return
     */
    WriteResponse write(String streamId, byte[] byteArray, int offSet, int length);

    /**
     * 逐行写入
     *
     * @param streamId 必填 流对象唯一标识
     * @param string   必填 字符串
     * @return
     */
    Response writeline(String streamId, String string);

    /**
     * 关闭流
     *
     * @param streamId 必填 流对象唯一标识
     * @return
     */
    CloseResponse close(String streamId);

}

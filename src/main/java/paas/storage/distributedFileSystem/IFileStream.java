package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.fileStream.response.*;
import paas.storage.utils.Response;

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
     * @param connectionId
     * @param filePath
     * @param streamType
     * @param mode
     * @return
     */
    CreateResponse create(String connectionId, String filePath, int streamType, int mode);

    /**
     * 文件流读取
     *
     * @param streamId
     * @param byteArray
     * @param offSet
     * @param length
     * @return
     */
    ReadResponse read(String streamId, byte[] byteArray, int offSet, int length);

    /**
     * 逐行读取
     *
     * @param streamId
     * @param encode
     * @param readMethod
     * @return
     */
    ReadlinesResponse readlines(String streamId, String encode, int readMethod);

    /**
     * 文件流写入
     *
     * @param streamId
     * @param byteArray
     * @param offSet
     * @param length
     * @return
     */
    WriteResponse write(String streamId, byte[] byteArray, int offSet, int length);

    /**
     * 逐行写入
     * @param streamId
     * @param string
     * @return
     */
    Response writeline(String streamId, String string);

    /**
     *关闭流
     * @param streamId
     * @return
     */
     CloseResponse close(String streamId);

}

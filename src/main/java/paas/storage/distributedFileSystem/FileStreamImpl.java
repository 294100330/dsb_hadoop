package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.fileStream.response.*;
import paas.storage.utils.Response;

/**
 * 文件流管理 实现层
 *
 * @author luowei
 * Creation time 2021/1/23 19:10
 */
public class FileStreamImpl implements IFileStream {

    /**
     * 创建流
     *
     * @param connectionId
     * @param filePath
     * @param streamType
     * @param mode
     * @return
     */
    @Override
    public CreateResponse create(String connectionId, String filePath, int streamType, int mode) {
        return null;
    }

    /**
     * 文件流读取
     *
     * @param streamId
     * @param byteArray
     * @param offSet
     * @param length
     * @return
     */
    @Override
    public ReadResponse read(String streamId, byte[] byteArray, int offSet, int length) {
        return null;
    }

    /**
     * 逐行读取
     *
     * @param streamId
     * @param encode
     * @param readMethod
     * @return
     */
    @Override
    public ReadlinesResponse readlines(String streamId, String encode, int readMethod) {
        return null;
    }

    /**
     * 文件流写入
     *
     * @param streamId
     * @param byteArray
     * @param offSet
     * @param length
     * @return
     */
    @Override
    public WriteResponse write(String streamId, byte[] byteArray, int offSet, int length) {
        return null;
    }

    /**
     * 逐行写入
     *
     * @param streamId
     * @param string
     * @return
     */
    @Override
    public Response writeline(String streamId, String string) {
        return null;
    }

    /**
     *关闭流
     * @param streamId
     * @return
     */
    @Override
    public CloseResponse close(String streamId) {
        return null;
    }


}

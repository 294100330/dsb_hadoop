package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.fileStream.response.CreateResponse;

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
}

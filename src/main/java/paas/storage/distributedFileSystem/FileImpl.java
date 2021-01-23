package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.connection.response.CreateResponse;
import paas.storage.distributedFileSystem.file.response.FileExistResponse;
import paas.storage.distributedFileSystem.file.response.GetFileListResponse;
import paas.storage.distributedFileSystem.file.response.RenameResponse;
import paas.storage.utils.Response;

/**
 * 文件访问 实现层
 *
 * @author luowei
 * Creation time 2021/1/23 18:58
 */
public class FileImpl implements IFile {

    /**
     * 创建目录
     *
     * @param connectionId
     * @param filePath
     * @return
     */
    @Override
    public CreateResponse create(String connectionId, String filePath) {
        return null;
    }

    /**
     * 删除文件
     *
     * @param connectionId
     * @param filePath
     * @param objectType
     * @param recursive
     * @return
     */
    @Override
    public Response delete(String connectionId, String filePath, int objectType, int recursive) {
        return null;
    }

    /**
     * 重命名文件
     *
     * @param connectionId
     * @param srcPath
     * @param dstPath
     * @return
     */
    @Override
    public RenameResponse rename(String connectionId, String srcPath, String dstPath) {
        return null;
    }

    /**
     * 移动文件
     *
     * @param connectionId
     * @param srcPath
     * @param dstPath
     * @param operator
     * @param overwrite
     * @return
     */
    @Override
    public Response move(String connectionId, String srcPath, String dstPath, int operator, int overwrite) {
        return null;
    }

    /**
     * 获取文件列表
     *
     * @param connectionId
     * @param filePath
     * @param filter
     * @param recursive
     * @return
     */
    @Override
    public GetFileListResponse getFileList(String connectionId, String filePath, String filter, int recursive) {
        return null;
    }

    /**
     * 判断文件是否存在
     *
     * @param connectionId
     * @param filePath
     * @return
     */
    @Override
    public FileExistResponse fileExist(String connectionId, String filePath) {
        return null;
    }


}

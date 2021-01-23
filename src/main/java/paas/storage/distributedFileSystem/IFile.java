package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.file.response.*;
import paas.storage.utils.Response;

/**
 * 文件访问 服务层
 *
 * @author luowei
 * Creation time 2021/1/23 18:58
 */
public interface IFile {

    /**
     * 创建目录
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath 必填 目录路径
     * @return
     */
    CreateResponse create(String connectionId, String filePath);

    /**
     * 删除文件
     *
     * @param connectionId
     * @param filePath
     * @param objectType
     * @param recursive
     * @return
     */
    Response delete(String connectionId, String filePath, int objectType, int recursive);

    /**
     * 重命名文件
     *
     * @param connectionId
     * @param srcPath
     * @param dstPath
     * @return
     */
    RenameResponse rename(String connectionId, String srcPath, String dstPath);

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
    Response move(String connectionId, String srcPath, String dstPath, int operator, int overwrite);

    /**
     * 获取文件列表
     *
     * @param connectionId
     * @param filePath
     * @param filter
     * @param recursive
     * @return
     */
    GetFileListResponse getFileList(String connectionId, String filePath, String filter, int recursive);

    /**
     * 判断文件是否存在
     *
     * @param connectionId
     * @param filePath
     * @return
     */
    FileExistResponse fileExist(String connectionId, String filePath);

    /**
     * 获取文件属性
     *
     * @param connectionId
     * @param fileName
     * @return
     */
    GetFileInfoResponse getFileInfo(String connectionId, String fileName);

    /**
     * 文件权限设置
     *
     * @param fullPath
     * @param userGroup
     * @param user
     * @param authority
     * @param beInherit
     * @return
     */
    Response setAuthority(String fullPath, String userGroup, String user, String authority, int beInherit);

}

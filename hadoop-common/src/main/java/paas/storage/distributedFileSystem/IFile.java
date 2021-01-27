package paas.storage.distributedFileSystem;

import paas.storage.distributedFileSystem.file.response.*;
import paas.storage.connection.Response;

/**
 * 文件访问 服务层
 *
 * @author 豆沙包
 * Creation time 2021/1/23 18:58
 */
public interface IFile {

    /**
     * 创建目录
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 目录路径
     * @return
     */
    CreateResponse create(String connectionId, String filePath);

    /**
     * 删除文件
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 文件路径 填写完整的文件路径或目录。
     * @param objectType   必填 操作对象类型 1表示目录，2表示文件。
     * @param recursive    必填 是否递归 1表示是，2表示否。
     * @return
     */
    Response delete(String connectionId, String filePath, int objectType, int recursive);

    /**
     * 重命名文件
     *
     * @param connectionId 必填 文件系统连接标识
     * @param srcPath      必填 源文件 填写完整的文件路径或目录。
     * @param dstPath      必填 目标文件 此参数填写新文件名。
     * @return
     */
    RenameResponse rename(String connectionId, String srcPath, String dstPath);

    /**
     * 移动文件
     *
     * @param connectionId 必填 文件系统连接标识
     * @param srcPath      必填  源文件路径
     * @param dstPath      必填 目的文件路径
     * @param operator     必填  操作类型 1表示移动，2表示复制。
     * @param overwrite    必填  是否覆盖 1表示是；2表示否。若移动、复制操作出现，重名的文件或目录，选择是否需要覆盖。
     * @return
     */
    Response move(String connectionId, String srcPath, String dstPath, int operator, int overwrite);

    /**
     * 获取文件列表
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 文件路径  填写完整的文件路径。
     * @param filter       可选 过滤器  正则表达式。
     * @param recursive    是否 递归  1表示递归，0表示不递归。
     * @return
     */
    GetFileListResponse getFileList(String connectionId, String filePath, String filter, int recursive);

    /**
     * 判断文件是否存在
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 文件路径 填写完整的文件路径或目录。
     * @return
     */
    FileExistResponse fileExist(String connectionId, String filePath);

    /**
     * 获取文件属性
     *
     * @param connectionId 必填 文件系统连接标识
     * @param fileName     必填 文件名  填写文件名称或目录。
     * @return
     */
    GetFileInfoResponse getFileInfo(String connectionId, String fileName);

    /**
     * 文件权限设置
     *
     * @param fullPath  必填 文件或目录路径
     * @param userGroup 必填 用户组
     * @param user      必填 用户
     * @param authority 可选 权限  RWX形式,设置访问权限时需填写，为空表示设置文件或目录的所有者。
     * @param beInherit 可选 是否子目录继承 1表示是，2表示否；默认为否。
     * @return
     */
    Response setAuthority(String fullPath, String userGroup, String user, String authority, int beInherit);

}

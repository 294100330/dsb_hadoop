package paas.storage.distributedFileSystem;

import org.springframework.context.annotation.Configuration;
import paas.storage.distributedFileSystem.file.response.*;
import paas.storage.utils.Response;

/**
 * 文件访问 实现层
 *
 * @author luowei
 * Creation time 2021/1/23 18:58
 */
@Configuration
public class FileImpl implements IFile {

    /**
     * 创建目录
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 目录路径
     * @return
     */
    @Override
    public CreateResponse create(String connectionId, String filePath) {
        return null;
    }

    /**
     * 删除文件
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 文件路径 填写完整的文件路径或目录。
     * @param objectType   必填 操作对象类型 1表示目录，2表示文件。
     * @param recursive    必填 是否递归 1表示是，2表示否。
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
     * @param connectionId 必填 文件系统连接标识
     * @param srcPath      必填  源文件路径
     * @param dstPath      必填 目的文件路径
     * @param operator     必填  操作类型 1表示移动，2表示复制。
     * @param overwrite    必填  是否覆盖 1表示是；2表示否。若移动、复制操作出现，重名的文件或目录，选择是否需要覆盖。
     * @return
     */
    @Override
    public Response move(String connectionId, String srcPath, String dstPath, int operator, int overwrite) {
        return null;
    }

    /**
     * 获取文件列表
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 文件路径  填写完整的文件路径。
     * @param filter       可选 过滤器  正则表达式。
     * @param recursive    是否 递归  1表示递归，0表示不递归。
     * @return
     */
    @Override
    public GetFileListResponse getFileList(String connectionId, String filePath, String filter, int recursive) {
        return null;
    }

    /**
     * 判断文件是否存在
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 文件路径 填写完整的文件路径或目录。
     * @return
     */
    @Override
    public FileExistResponse fileExist(String connectionId, String filePath) {
        return null;
    }

    /**
     * 获取文件属性
     *
     * @param connectionId 必填 文件系统连接标识
     * @param fileName     必填 文件名  填写文件名称或目录。
     * @return
     */
    @Override
    public GetFileInfoResponse getFileInfo(String connectionId, String fileName) {
        return null;
    }

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
    @Override
    public Response setAuthority(String fullPath, String userGroup, String user, String authority, int beInherit) {
        return null;
    }


}

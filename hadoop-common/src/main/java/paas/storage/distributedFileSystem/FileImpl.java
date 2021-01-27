package paas.storage.distributedFileSystem;

import cn.hutool.core.util.ReUtil;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import paas.storage.component.ConnectionService;
import paas.storage.connection.Response;
import paas.storage.distributedFileSystem.file.response.*;
import paas.storage.utils.AssertUtils;
import paas.storage.utils.AuthorityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件访问 实现层
 *
 * @author 豆沙包
 * Creation time 2021/1/23 18:58
 */
@Log4j2
@Configuration
public class FileImpl implements IFile {

    @Autowired
    private ConnectionService connectionService;

    /**
     * 创建目录
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 目录路径
     * @return
     */
    @Override
    public CreateResponse create(String connectionId, String filePath) {
        CreateResponse createResponse = new CreateResponse();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(connectionId), "connectionId:connectionId不能为空");
            AssertUtils.isTrue(!StringUtils.isEmpty(filePath), "filePath:filePath不能为空");

            FileSystem fileSystem = connectionService.get(connectionId);
            fileSystem.mkdirs(new Path(filePath));
            createResponse.setTaskStatus(1);
            createResponse.setFilePath(filePath);
        } catch (Exception e) {
            createResponse.setErrorMsg(e.getMessage());
            createResponse.setTaskStatus(0);
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return createResponse;
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
        Response response = new Response();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(connectionId), "connectionId:文件系统连接标识不能为空");
            AssertUtils.isTrue(!StringUtils.isEmpty(filePath), "filePath:文件路径不能为空");
            AssertUtils.isTrue(1 == objectType || 2 == objectType, "objectType:操作对象类型限定在1或2");
            AssertUtils.isTrue(1 == recursive || 2 == recursive, "recursive:是否递归限定在1或2");

            FileSystem fileSystem = connectionService.get(connectionId);
            Path path = new Path(filePath);
            fileSystem.delete(path, 1 == objectType);
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
     * 重命名文件
     *
     * @param connectionId 必填 文件系统连接标识
     * @param srcPath      必填 源文件 填写完整的文件路径或目录。
     * @param dstPath      必填 目标文件 此参数填写新文件名。
     * @return
     */
    @Override
    public RenameResponse rename(String connectionId, String srcPath, String dstPath) {
        RenameResponse response = new RenameResponse();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(connectionId), "connectionId:文件系统连接标识不能为空");
            AssertUtils.isTrue(!StringUtils.isEmpty(srcPath), "srcPath:源文件不能为空");
            AssertUtils.isTrue(!StringUtils.isEmpty(dstPath), "dstPath:目标文件不能为空");

            FileSystem fileSystem = connectionService.get(connectionId);
            fileSystem.rename(new Path(srcPath), new Path(dstPath));
            response.setDstFile(dstPath);
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
        Response response = new Response();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(connectionId), "connectionId:文件系统连接标识不能为空");
            AssertUtils.isTrue(!StringUtils.isEmpty(srcPath), "srcPath:源文件路径不能为空");
            AssertUtils.isTrue(!StringUtils.isEmpty(dstPath), "dstPath:目的文件路径不能为空");
            AssertUtils.isTrue(1 == operator || 2 == operator, "operator:操作类型限定在1或2");
            AssertUtils.isTrue(1 == overwrite || 2 == overwrite, "overwrite:是否覆盖限定在1或2");

            FileSystem fileSystem = connectionService.get(connectionId);
            Path srcPath1 = new Path(srcPath);
            Path dstPath1 = new Path(dstPath);
            //移动
            if (1 == operator) {
                boolean result = fileSystem.rename(srcPath1, dstPath1);
            } else if (2 == operator) {
                //复制
                FileContext.getFileContext(fileSystem.getUri(), fileSystem.getConf()).util().copy(srcPath1, dstPath1, false, 2 == overwrite);
            }
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
     * 获取文件列表
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 文件路径  填写完整的文件路径。
     * @param filter       可选 过滤器  正则表达式。
     * @param recursive    必填 递归  1表示递归，0表示不递归。
     * @return
     */
    @Override
    public GetFileListResponse getFileList(String connectionId, String filePath, String filter, int recursive) {
        GetFileListResponse getFileListResponse = new GetFileListResponse();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(connectionId), "connectionId:文件系统连接标识不能为空");
            AssertUtils.isTrue(!StringUtils.isEmpty(filePath), "filePath:文件路径不能为空");
            AssertUtils.isTrue(1 == recursive || 2 == recursive, "recursive:递归限定在1或2");

            FileSystem fileSystem = connectionService.get(connectionId);
            //迭代查询
            RemoteIterator<LocatedFileStatus> remoteIterator = fileSystem.listFiles(new Path(filePath), 1 == recursive);
            List<LocatedFileStatus> locatedFileStatuses = new ArrayList<>();
            while (remoteIterator.hasNext()) {
                locatedFileStatuses.add(remoteIterator.next());
            }
            //处理内容
            List<Map<String, Object>> list = locatedFileStatuses.stream()
                    //过滤
                    .filter(locatedFileStatus -> ReUtil.isMatch(locatedFileStatus.getPath().getName(), filter))
                    //内容处理
                    .map(locatedFileStatus -> {
                        Map<String, Object> map = new HashMap<>(2);
                        map.put("filepath", locatedFileStatus.getPath().getName());
                        map.put("isDir", locatedFileStatus.isDirectory() ? 1 : 0);
                        return map;
                    }).collect(Collectors.toList());
            getFileListResponse.setTaskStatus(1);
            getFileListResponse.setFileList(JSONUtil.toJsonStr(list));
        } catch (Exception e) {
            getFileListResponse.setTaskStatus(0);
            getFileListResponse.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return getFileListResponse;
    }

    /**
     * 判断文件是否存在
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 文件路径 填写完整的文件路径或目录。
     * @return
     */
    @Override
    @SneakyThrows
    public FileExistResponse fileExist(String connectionId, String filePath) {
        FileExistResponse fileExistResponse = new FileExistResponse();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(connectionId), "connectionId:文件系统连接标识不能为空");
            AssertUtils.isTrue(!StringUtils.isEmpty(filePath), "filePath:文件路径不能为空");

            FileSystem fileSystem = connectionService.get(connectionId);
            boolean exists = fileSystem.exists(new Path(filePath));
            fileExistResponse.setTaskStatus(1);
            fileExistResponse.setResult(exists ? 1 : 0);
        } catch (Exception e) {
            fileExistResponse.setTaskStatus(0);
            fileExistResponse.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return fileExistResponse;
    }

    /**
     * 获取文件属性
     *
     * @param connectionId 必填 文件系统连接标识
     * @param fileName     必填 文件名  填写文件名称或目录。
     * @return
     */
    @Override
    @SneakyThrows
    public GetFileInfoResponse getFileInfo(String connectionId, String fileName) {
        GetFileInfoResponse getFileInfoResponse = new GetFileInfoResponse();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(connectionId), "connectionId:文件系统连接标识不能为空");
            AssertUtils.isTrue(!StringUtils.isEmpty(fileName), "fileName:文件名不能为空");

            FileSystem fileSystem = connectionService.get(connectionId);
            FileStatus fileStatus = fileSystem.getFileStatus(new Path(fileName));
            StringBuilder stringBuilder = new StringBuilder(fileStatus.getPermission().getUserAction().SYMBOL)
                    .append(fileStatus.getPermission().getGroupAction().SYMBOL)
                    .append(fileStatus.getPermission().getOtherAction().SYMBOL);
            Map<String,Object> map = new HashMap<>();
            map.put("path",fileStatus.getPath().getName());
            map.put("isdir",fileStatus.isDirectory());
            map.put("accessTime",fileStatus.getAccessTime());
            map.put("permission",stringBuilder.toString());
            map.put("owner",fileStatus.getOwner());
            map.put("group",fileStatus.getGroup());
            String jsonText = JSONUtil.toJsonStr(map);
            getFileInfoResponse.setTaskStatus(1);
            getFileInfoResponse.setFileDetails(jsonText);
        } catch (Exception e) {
            getFileInfoResponse.setTaskStatus(0);
            getFileInfoResponse.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return getFileInfoResponse;
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
        Response response = new Response();
        try {
            FileSystem fileSystem = connectionService.get("connectionId");
            FsPermission fsPermission = AuthorityUtils.fileSystemAction(authority);
            Path path = new Path(fullPath);
            fileSystem.setPermission(path, fsPermission);
            fileSystem.setOwner(path, user, userGroup);
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


}

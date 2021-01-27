package paas.storage.distributedFileSystem;

import cn.hutool.core.util.ReUtil;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import paas.storage.component.ConnectionService;
import paas.storage.connection.Response;
import paas.storage.distributedFileSystem.file.response.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件访问 实现层
 *
 * @author luowei
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
        FileSystem fileSystem = connectionService.get(connectionId);
        try {
            fileSystem.mkdirs(new Path(filePath));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        CreateResponse createResponse = new CreateResponse();
        createResponse.setFilePath(filePath);
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
        FileSystem fileSystem = connectionService.get(connectionId);
        Path path = new Path(filePath);
        try {
            fileSystem.delete(path, 1 == objectType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new Response();
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
    @SneakyThrows
    public RenameResponse rename(String connectionId, String srcPath, String dstPath) {
        FileSystem fileSystem = connectionService.get(connectionId);
        fileSystem.rename(new Path(srcPath), new Path(dstPath));
        RenameResponse response = new RenameResponse();
        response.setDstFile(dstPath);
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
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return response;
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
    @SneakyThrows
    public GetFileListResponse getFileList(String connectionId, String filePath, String filter, int recursive) {
        GetFileListResponse getFileListResponse = new GetFileListResponse();
        try {
            FileSystem fileSystem = connectionService.get(connectionId);
            RemoteIterator<LocatedFileStatus> remoteIterator = fileSystem.listFiles(new Path(filePath), 1 == recursive);
            List<LocatedFileStatus> locatedFileStatuses = new ArrayList<>();
            while (remoteIterator.hasNext()) {
                locatedFileStatuses.add(remoteIterator.next());
            }
            //处理内容
            List<Map<String, Object>> list = locatedFileStatuses.stream()
                    //过滤
                    .filter(locatedFileStatus -> ReUtil.isMatch(filter, locatedFileStatus.getPath().getName()))
                    //内容处理
                    .map(locatedFileStatus -> {
                        Map<String, Object> map = new HashMap<>(2);
                        map.put("filepath", locatedFileStatus.getPath().getName());
                        map.put("isDir", locatedFileStatus.isDirectory() ? 1 : 0);
                        return map;
                    }).collect(Collectors.toList());
            getFileListResponse.setFileList(JSONUtil.toJsonStr(list));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
        FileSystem fileSystem = connectionService.get(connectionId);
        boolean exists = fileSystem.exists(new Path(filePath));
        FileExistResponse fileExistResponse = new FileExistResponse();
        fileExistResponse.setResult(exists ? 1 : 0);
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
        FileSystem fileSystem = connectionService.get(connectionId);
        fileSystem.getStatus();
        FileStatus fileStatus = fileSystem.getFileStatus(new Path(fileName));
        GetFileInfoResponse getFileInfoResponse = new GetFileInfoResponse();
        String jsonText = JSONUtil.toJsonStr(fileStatus);
        getFileInfoResponse.setFileDetails(jsonText);
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
//        FileSystem fileSystem = connectionService.get(connectionId);
//        fileSystem.setPermission(new Path(fullPath),FsPermission);
        return null;
    }
}

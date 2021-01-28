package paas.storage.distributedFileSystem;

import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import paas.storage.component.ConnectionService;
import paas.storage.distributedFileSystem.storage.response.StorageResponse;
import paas.storage.utils.AssertUtils;

/**
 * 存储空间管理 实现层
 *
 * @author 豆沙包
 * Creation time 2021/1/23 19:22
 */
@Log4j2
@Configuration
public class IStorageImpl implements IStorage {

    @Autowired
    private ConnectionService connectionService;

    /**
     * 存储容量及使用量
     *
     * @param connectionId 必填 文件系统连接标识
     * @param directory    可选 目录
     * @return
     */
    @Override
    public StorageResponse getStorageInfo(String connectionId, String directory) {
        StorageResponse storageResponse = new StorageResponse();
        try {
            AssertUtils.isTrue(!StringUtils.isEmpty(connectionId), "connectionId:文件系统连接标识不能为空");
            AssertUtils.charLengthLe(connectionId, 1024, "connectionId:文件系统连接标识字符定长不能超过1024");

            //文档上是可选的，不传又要报错，就弄个根路径吧
            directory = directory != null ? directory : "/";

            FileSystem fileSystem = connectionService.get(connectionId);
            ContentSummary contentSummary = fileSystem.getContentSummary(new Path(directory));
            storageResponse.setTaskStatus(1);
            storageResponse.setTotalVolume(contentSummary.getSpaceConsumed());
            storageResponse.setUsedVolume(contentSummary.getLength());
        } catch (Exception e) {
            storageResponse.setTaskStatus(0);
            storageResponse.setErrorMsg(e.getMessage());
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return storageResponse;
    }
}

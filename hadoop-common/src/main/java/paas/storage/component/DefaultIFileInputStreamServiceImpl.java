package paas.storage.component;

import cn.hutool.core.util.IdUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认输入文件流管理
 *
 * @author luowei
 * Creation time 2021/1/25 10:59
 */
@Log4j2
public class DefaultIFileInputStreamServiceImpl implements IFileInputStreamService {

    private static final Map<String, IFileInputStreamData> MAP = new ConcurrentHashMap<>();

    @Autowired
    private ConnectionService connectionService;

    /**
     * 创建
     *
     * @param connectionId 分布式文件系统服务唯一标识。
     * @param filePath
     * @return
     */
    @Override
    public String create(String connectionId, String filePath) {
        String streamId = null;
        for (Map.Entry<String, IFileInputStreamData> entity : DefaultIFileInputStreamServiceImpl.MAP.entrySet()) {
            if (entity.getValue().getConnectionId().equals(connectionId)) {
                streamId = entity.getKey();
            }
        }
        if (streamId == null) {
            streamId = this.put(connectionId, filePath);
        }
        return streamId;
    }

    /**
     * 获得
     *
     * @param streamId
     * @return
     */
    @Override
    public FSDataInputStream get(String streamId) {
        IFileInputStreamData iFileInputStreamData = DefaultIFileInputStreamServiceImpl.MAP.get(streamId);
        return iFileInputStreamData.getFsDataInputStream();
    }

    /**
     * 删除
     *
     * @param streamId
     */
    @Override
    public void delete(String streamId) {
        DefaultIFileInputStreamServiceImpl.MAP.remove(streamId);
    }


    /**
     * 获得id
     *
     * @return
     */
    private String getId() {
        return IdUtil.simpleUUID();
    }

    /**
     * 保存
     *
     * @param connectionId
     * @return
     */
    private String put(String connectionId, String filePath) {
        String streamId = this.getId();
        FileSystem fileSystem = connectionService.get(connectionId);
        FSDataInputStream fsDataInputStream = null;
        try {
            fsDataInputStream = fileSystem.open(new Path(filePath));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        IFileInputStreamData fileSystemData = IFileInputStreamData.builder().connectionId(connectionId).filePath(filePath).fsDataInputStream(fsDataInputStream).build();
        DefaultIFileInputStreamServiceImpl.MAP.put(connectionId, fileSystemData);
        return streamId;
    }

    @Data
    @Builder
    private static class IFileInputStreamData {
        private String connectionId;

        private String filePath;

        private FSDataInputStream fsDataInputStream;

    }
}

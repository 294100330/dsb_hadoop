package paas.storage.component;

import cn.hutool.core.util.IdUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件流输出流管理 服务层
 *
 * @author luowei
 * Creation time 2021/1/25 10:56
 */
@Log4j2
public class DefaultIFileOutStreamServiceImpl implements IFileOutStreamService {

    /**
     * 流存放
     */
    private static final Map<String, DefaultIFileOutStreamServiceImpl.IFileOutStreamData> MAP = new ConcurrentHashMap<>();

    /**
     * 前缀
     */
    private static final String OUT_STREAM_PREFIX = "outStream:";

    @Autowired
    private ConnectionService connectionService;

    /**
     * 创建
     *
     * @param connectionId 分布式文件系统服务唯一标识。
     * @return
     */
    @Override
    public String create(String connectionId, String filePath) {
        String streamId = null;
        for (Map.Entry<String, DefaultIFileOutStreamServiceImpl.IFileOutStreamData> entity : DefaultIFileOutStreamServiceImpl.MAP.entrySet()) {
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
     * 获取
     *
     * @param streamId
     * @return
     */
    @Override
    public FSDataOutputStream get(String streamId) {
        DefaultIFileOutStreamServiceImpl.IFileOutStreamData iFileOutStreamData = DefaultIFileOutStreamServiceImpl.MAP.get(streamId);
        return iFileOutStreamData.getFsDataOutputStream();
    }

    /**
     * 删除
     *
     * @param streamId
     */
    @Override
    public void delete(String streamId) {
        if (streamId.contains(OUT_STREAM_PREFIX)) {
            DefaultIFileOutStreamServiceImpl.MAP.remove(streamId);
        }
    }

    /**
     * 获得id
     *
     * @return
     */
    private String getId() {
        return OUT_STREAM_PREFIX + IdUtil.simpleUUID();
    }

    /**
     * 保存
     *
     * @param connectionId
     * @return
     */
    @SuppressWarnings("all")
    private String put(String connectionId, String filePath) {
        String streamId = this.getId();
        FileSystem fileSystem = connectionService.get(connectionId);
        FSDataOutputStream fsDataInputStream = null;
        try {
            fsDataInputStream = fileSystem.create(new Path(filePath));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        DefaultIFileOutStreamServiceImpl.IFileOutStreamData fileOutStreamData = IFileOutStreamData
                .builder().connectionId(connectionId).filePath(filePath).fsDataOutputStream(fsDataInputStream).build();
        DefaultIFileOutStreamServiceImpl.MAP.put(streamId, fileOutStreamData);
        return streamId;
    }


    @Data
    @Builder
    private static class IFileOutStreamData {
        private String connectionId;

        private String filePath;

        private FSDataOutputStream fsDataOutputStream;

    }
}

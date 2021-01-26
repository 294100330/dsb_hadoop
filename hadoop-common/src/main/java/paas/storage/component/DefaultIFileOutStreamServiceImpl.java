package paas.storage.component;

import cn.hutool.core.util.IdUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
     * @param connectionId
     * @param filePath
     * @param mode
     * @return
     */
    @Override
    public String create(String connectionId, String filePath, int mode) {
        String streamId = null;
        for (Map.Entry<String, DefaultIFileOutStreamServiceImpl.IFileOutStreamData> entity : DefaultIFileOutStreamServiceImpl.MAP.entrySet()) {
            if (entity.getValue().getConnectionId().equals(connectionId)) {
                streamId = entity.getKey();
            }
        }
        if (streamId == null) {
            streamId = this.put(connectionId, filePath, mode);
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
     * @param filePath
     * @param mode         可选 写入模式 1表示追加，2表示覆盖。
     * @return
     */
    @SuppressWarnings("all")
    private String put(String connectionId, String filePath, int mode) {
        String streamId = null;
        FileSystem fileSystem = connectionService.get(connectionId);
        try {
            FSDataOutputStream fsDataOutputStream = null;
            streamId = this.getId();
            Path path = new Path(filePath);
            //追加
            if (1 == mode) {
                fsDataOutputStream = fileSystem.append(path);
//                InputStream in = new BufferedInputStream(new FileInputStream(filePath));
//                IOUtils.copyBytes(in, fsDataOutputStream, 4096, true);
            } else if (2 == mode) {
                //覆盖
                fsDataOutputStream = fileSystem.create(path);
            }
            DefaultIFileOutStreamServiceImpl.IFileOutStreamData fileOutStreamData = IFileOutStreamData
                    .builder().connectionId(connectionId).filePath(filePath).fsDataOutputStream(fsDataOutputStream).build();
            DefaultIFileOutStreamServiceImpl.MAP.put(streamId, fileOutStreamData);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

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

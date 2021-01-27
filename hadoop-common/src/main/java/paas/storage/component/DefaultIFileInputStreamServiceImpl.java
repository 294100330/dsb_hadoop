package paas.storage.component;

import cn.hutool.crypto.SecureUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import paas.storage.constants.Constants;
import paas.storage.exception.QCRuntimeException;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认输入文件流管理
 *
 * @author 豆沙包
 * Creation time 2021/1/25 10:59
 */
@Log4j2
public class DefaultIFileInputStreamServiceImpl implements IFileInputStreamService {

    private static final Map<String, IFileInputStreamData> MAP = new ConcurrentHashMap<>();

    /**
     * 前缀
     */
    private static final String INPUT_STREAM_PREFIX = "inputStream";


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
    public String create(String connectionId, String filePath) throws IOException {
        String streamId = this.getId(connectionId, filePath);
        FileSystem fileSystem = connectionService.get(connectionId);
        FSDataInputStream fsDataInputStream = fileSystem.open(new Path(filePath));
        IFileInputStreamData fileSystemData = IFileInputStreamData.builder().connectionId(connectionId).filePath(filePath).fsDataInputStream(fsDataInputStream).build();
        DefaultIFileInputStreamServiceImpl.MAP.put(streamId, fileSystemData);
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
        if (iFileInputStreamData == null) {
            throw new QCRuntimeException("没有服务");
        }
        return iFileInputStreamData.getFsDataInputStream();

    }

    /**
     * 删除
     *
     * @param streamId
     */
    @Override
    public void delete(String streamId) {
        DefaultIFileInputStreamServiceImpl.IFileInputStreamData iFileInputStreamData = DefaultIFileInputStreamServiceImpl.MAP.get(streamId);
        if (iFileInputStreamData != null) {
            IOUtils.closeStream(iFileInputStreamData.getFsDataInputStream());
        }
        DefaultIFileInputStreamServiceImpl.MAP.remove(streamId);
    }


    /**
     * 获得id
     *
     * @return
     */
    private String getId(String connectionId, String filePath) {
        StringBuilder stringBuilder = new StringBuilder(INPUT_STREAM_PREFIX)
                .append(Constants.SEPARATOR)
                .append(connectionId)
                .append(Constants.SEPARATOR)
                .append(filePath);
        String md5 = SecureUtil.md5(stringBuilder.toString());
        StringBuilder stringBuilder1 = new StringBuilder(INPUT_STREAM_PREFIX).append(Constants.SEPARATOR).append(md5);
        return stringBuilder1.toString();
    }


    @Data
    @Builder
    private static class IFileInputStreamData {
        private String connectionId;

        private String filePath;

        private FSDataInputStream fsDataInputStream;

    }
}

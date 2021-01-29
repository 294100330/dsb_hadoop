package paas.storage.component;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import paas.storage.constants.Constants;
import paas.storage.exception.QCRuntimeException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件流输出流管理 服务层
 *
 * @author 豆沙包
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
    private static final String OUT_STREAM_PREFIX = "outStream";

    @Autowired
    private ConnectionService connectionService;

    /**
     * 创建流
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填文件路径 文件的绝对路径。
     * @param mode         可选 写入模式 1表示追加，2表示覆盖。
     * @return
     */
    @Override
    public String create(String connectionId, String filePath, int mode) {
        //生成id
        String streamId = this.getId(connectionId, filePath, mode);
        FileSystem fileSystem = connectionService.get(connectionId);
        try {
            FSDataOutputStream fsDataOutputStream = null;
            Path path = new Path(filePath);
            //追加
            if (1 == mode) {
                fsDataOutputStream = fileSystem.append(path);
            } else {
                //默认--覆盖
                fsDataOutputStream = fileSystem.create(path);
            }
            DefaultIFileOutStreamServiceImpl.IFileOutStreamData fileOutStreamData = IFileOutStreamData
                    .builder().connectionId(connectionId)
                    .filePath(new Path(filePath))
                    .fsDataOutputStream(fsDataOutputStream)
                    .fileSystem(fileSystem)
                    .build();
            DefaultIFileOutStreamServiceImpl.MAP.put(streamId, fileOutStreamData);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
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
    public IFileOutStreamData get(String streamId) {
        DefaultIFileOutStreamServiceImpl.IFileOutStreamData iFileOutStreamData = DefaultIFileOutStreamServiceImpl.MAP.get(streamId);
        if (iFileOutStreamData == null) {
            throw new QCRuntimeException("没有服务");
        }
        return iFileOutStreamData;
    }

    /**
     * 删除
     *
     * @param streamId
     */
    @Override
    public void delete(String streamId) {
        DefaultIFileOutStreamServiceImpl.IFileOutStreamData iFileOutStreamData = DefaultIFileOutStreamServiceImpl.MAP.get(streamId);
        if (iFileOutStreamData != null) {
            IOUtils.closeStream(iFileOutStreamData.getFsDataOutputStream());
        }
        DefaultIFileOutStreamServiceImpl.MAP.remove(streamId);

    }

    /**
     * 获得id
     *
     * @return
     */
    private String getId(String connectionId, String filePath, int mode) {
        StringBuilder stringBuilder = new StringBuilder(OUT_STREAM_PREFIX)
                .append(Constants.SEPARATOR)
                .append(connectionId)
                .append(Constants.SEPARATOR)
                .append(filePath)
                .append(Constants.SEPARATOR)
                .append(mode);
        String md5 = SecureUtil.md5(stringBuilder.toString());
        StringBuilder stringBuilder1 = new StringBuilder(OUT_STREAM_PREFIX).append(Constants.SEPARATOR).append(md5);
        return stringBuilder1.toString();
    }

}

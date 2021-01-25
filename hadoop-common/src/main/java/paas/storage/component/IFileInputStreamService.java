package paas.storage.component;

import org.apache.hadoop.fs.FSDataInputStream;

/**
 * 文件流输入流管理 服务层
 *
 * @author luowei
 * Creation time 2021/1/25 10:56
 */
public interface IFileInputStreamService {

    /**
     * 创建
     *
     * @param connectionId 分布式文件系统服务唯一标识。
     * @return
     */
    String create(String connectionId, String filePath);

    /**
     * 获取
     *
     * @param streamId
     * @return
     */
    FSDataInputStream get(String streamId);

    /**
     * 删除
     *
     * @param streamId
     */
    void delete(String streamId);

}

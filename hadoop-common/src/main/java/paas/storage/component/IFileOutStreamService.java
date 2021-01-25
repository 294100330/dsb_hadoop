package paas.storage.component;

import org.apache.hadoop.fs.FSDataOutputStream;

/**
 * 文件流输出流管理 服务层
 *
 * @author luowei
 * Creation time 2021/1/25 10:56
 */
public interface IFileOutStreamService {

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
    FSDataOutputStream  get(String streamId);

    /**
     * 删除
     *
     * @param streamId
     */
    void delete(String streamId);
}
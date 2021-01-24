package paas.storage.component;

import org.apache.hadoop.fs.FileSystem;

/**
 * 文件系统连接创建 服务层
 *
 * @author luowei
 * Creation time 2021/1/24 16:37
 */
public interface ConnectionService {

    /**
     * 创建
     *
     * @param serviceId  分布式文件系统服务唯一标识。
     * @return
     */
    String create(String serviceId);

    /**
     * 获取
     *
     * @param connectionId
     * @return
     */
    FileSystem get(String connectionId);

    /**
     * 删除
     *
     * @param connectionId
     */
    void delete(String connectionId);

}

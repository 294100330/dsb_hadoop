package paas.storage.component;

import lombok.Builder;
import lombok.Data;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * 文件流输出流管理 服务层
 *
 * @author 豆沙包
 * Creation time 2021/1/25 10:56
 */
public interface IFileOutStreamService {

    /**
     * 创建
     *
     * @param connectionId
     * @param filePath
     * @param mode
     * @return
     */
    String create(String connectionId, String filePath, int mode);

    /**
     * 获取
     *
     * @param streamId
     * @return
     */
    IFileOutStreamData get(String streamId);

    /**
     * 删除
     *
     * @param streamId
     */
    void delete(String streamId);

    @Data
    @Builder
    class IFileOutStreamData {

        /**
         *
         */
        private String connectionId;

        /**
         *
         */
        private Path filePath;

        /**
         *
         */
        private FSDataOutputStream fsDataOutputStream;

        /**
         *
         */
        private FileSystem fileSystem;
    }
}

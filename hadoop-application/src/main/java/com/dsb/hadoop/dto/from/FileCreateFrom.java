package com.dsb.hadoop.dto.from;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 流类型 1：输入流；2:输出流。
 *
 * @author 豆沙包
 * Creation time 2021/1/26 15:23
 */
@Data
@ApiModel("文件流管理--创建流from")
public class FileCreateFrom {

    private String connectionId;
    private String filePath;
    private StreamType streamType;
    private Mode mode;

    @Getter
    @AllArgsConstructor
    public enum StreamType {

        Input(1, "输入流"), Oot(2, "输出流");

        private final int code;
        private final String text;
    }

    @Getter
    @AllArgsConstructor
    public enum Mode {

        Append(1, "追加"), Overwrite(2, "覆盖");
        private final int code;
        private final String text;

    }
}

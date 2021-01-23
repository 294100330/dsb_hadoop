package com.dsb.hadoop;

import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;


/**
 * @author luowei
 * Creation time 2021/1/23 0:05
 */
public class HadoopApplication {

    @SneakyThrows
    public static void main(String[] args) {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.5.2:50070"), conf,"root");
        fs.copyFromLocalFile(new Path("D:\\hadoop.txt"), new Path("/hadoop.txt"));
        fs.close();
        System.out.println("上传完毕");

    }


}

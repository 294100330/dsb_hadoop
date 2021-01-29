package com.dsb.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyHadoop {
    private FileSystem fs;
    private Configuration conf;

    public static void main(String args[]) throws IOException {
        System.setProperty("HADOOP_USER_NAME","root");
        MyHadoop myHadoop = new MyHadoop();
        myHadoop.HDFSUtil();
        myHadoop.mkdir("/data/test");
        myHadoop.put("C:\\Users\\Lenovo\\Desktop\\localfile\\mytest", "/data/test/", false, true);
        List<String> list = myHadoop.ls("/data/test/mytest","");
        for(String file: list){
            System.out.println(file);
        }
        System.out.println(myHadoop.count("/data/test/mytest"));
        myHadoop.chown("/data/test/mytest/", "admin", "supergroup");
        Path path = new Path("/data/test/mytest/");
        myHadoop.chmod(path, "744");
        myHadoop.touchz("/data/test/mytest/", "empty.txt");
        File file =new File("C:\\Users\\Lenovo\\Desktop\\localfile\\mytest\\data1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        myHadoop.appendToFile(fileInputStream, "/data/test/mytest/empty.txt");
        fileInputStream.close();
        myHadoop.cat("/data/test/mytest/empty.txt");
    }

    public void HDFSUtil() throws IOException {
        conf = new Configuration();
        fs = FileSystem.get(conf);
    }

    public boolean mkdir(String path) throws IOException {
        Path srcPath = new Path(path);
        return fs.mkdirs(srcPath);
    }

    public void put(String src, String dst, boolean delSrc, boolean overwritted) throws IOException {
        Path srcPath = new Path(src);
        Path dstPath = new Path(dst);
        fs.copyFromLocalFile(delSrc, overwritted, srcPath, dstPath);
    }

    public List<String> ls (String filePath, String ext) throws IOException {
        List<String> listDir = new ArrayList<String>();
        Path path = new Path(filePath);
        RemoteIterator<LocatedFileStatus> it = fs.listFiles(path, true);
        while(it.hasNext()) {
            String name = it.next().getPath().toString();
            if(name.endsWith(ext)) {
                listDir.add(name);
            }
        }
        return listDir;
    }

    public String count(String filePath) throws IOException {
        Path path = new Path(filePath);
        ContentSummary contentSummary = fs.getContentSummary(path);
        return contentSummary.toString();
    }

    public void chown(String filePath, String username, String groupname) throws IOException {
        Path path = new Path(filePath);
        RemoteIterator<LocatedFileStatus> it = fs.listFiles(path, true);
        while(it.hasNext()) {
            fs.setOwner(it.next().getPath(), username, groupname);
        }
    }

    public void chmod(Path src, String mode) throws IOException {
        FsPermission fp = new FsPermission(mode);
        RemoteIterator<LocatedFileStatus> it = fs.listFiles(src, true);
        while(it.hasNext()) {
            fs.setPermission(it.next().getPath(), fp);
        }
    }

    public void touchz(String filePath, String fileName) throws IOException {
        Path path = new Path(filePath, fileName);
        fs.create(path);
    }

    public boolean appendToFile (InputStream in, String filePath) throws IOException {
        conf.setBoolean("dfs.support.append", true);
        if(!check(filePath)) {
            fs.createNewFile(new Path(filePath));
        }
        OutputStream out = fs.append(new Path(filePath));
        IOUtils.copyBytes(in, out, 10, true);
        in.close();
        out.close();
        fs.close();
        return true;
    }

    private boolean check(String filePath) throws IOException {
        Path path = new Path(filePath);
        boolean isExists = fs.exists(path);
        return isExists;
    }

    public void cat(String filePath) throws IOException {
        Path path = new Path(filePath);
        if(!check(filePath)) {
            fs.createNewFile(new Path(filePath));
        }
        FSDataInputStream fsDataInputStream = fs.open(path);
        IOUtils.copyBytes(fsDataInputStream, System.out, 10, false);
    }
}

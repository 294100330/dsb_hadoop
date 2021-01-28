package com.dsb.hadoop.test;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import paas.storage.connection.Response;
import paas.storage.distributedFileSystem.IFile;
import paas.storage.distributedFileSystem.file.response.*;

/**
 * 文件访问 接口层
 *
 * @author 豆沙包
 * Creation time 2021/1/24 17:44
 */
@Configuration
public class TestIFileController {

    @Autowired
    private IFile iFile;

    /**
     * 创建目录
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 目录路径
     * @return
     */
    @PostMapping("create")
    @ApiOperation("创建目录")
    public CreateResponse create(String connectionId, String filePath) {
        System.out.println("====================== 文件访问 创建目录 开始 ============================");
        CreateResponse response = iFile.create(connectionId, filePath);
        System.out.println(response);
        System.out.println("====================== 文件访问 创建目录 结束 ============================");
        return response;
    }

    /**
     * 删除文件
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 文件路径 填写完整的文件路径或目录。
     * @param objectType   必填 操作对象类型 1表示目录，2表示文件。
     * @param recursive    必填 是否递归 1表示是，2表示否。
     * @return
     */
    @PostMapping("delete")
    @ApiOperation("删除文件")
    public Response delete(String connectionId, String filePath, int objectType, int recursive) {
        System.out.println("====================== 文件访问 删除文件 开始 ============================");
        Response response = iFile.delete(connectionId, filePath, objectType, recursive);
        System.out.println(response);
        System.out.println("====================== 文件访问 删除文件 结束 ============================");
        return response;
    }

    /**
     * 重命名文件
     *
     * @param connectionId 必填 文件系统连接标识
     * @param srcPath      必填 源文件 填写完整的文件路径或目录。
     * @param dstPath      必填 目标文件 此参数填写新文件名。
     * @return
     */
    @PostMapping("rename")
    @ApiOperation("重命名文件")
    public RenameResponse rename(String connectionId, String srcPath, String dstPath) {
        System.out.println("====================== 文件访问 重命名文件 开始 ============================");
        RenameResponse response = iFile.rename(connectionId, srcPath, dstPath);
        System.out.println(response);
        System.out.println("====================== 文件访问 重命名文件 结束 ============================");
        return response;
    }

    /**
     * 移动文件
     *
     * @param connectionId 必填 文件系统连接标识
     * @param srcPath      必填  源文件路径
     * @param dstPath      必填 目的文件路径
     * @param operator     必填  操作类型 1表示移动，2表示复制。
     * @param overwrite    必填  是否覆盖 1表示是；2表示否。若移动、复制操作出现，重名的文件或目录，选择是否需要覆盖。
     * @return
     */
    @PostMapping("move")
    @ApiOperation("移动文件")
    public Response move(String connectionId, String srcPath, String dstPath, int operator, int overwrite) {
        System.out.println("====================== 文件访问 移动文件 开始 ============================");
        Response response = iFile.move(connectionId, srcPath, dstPath, operator, overwrite);
        System.out.println(response);
        System.out.println("====================== 文件访问 移动文件 结束 ============================");
        return response;

    }

    /**
     * 获取文件列表
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 文件路径  填写完整的文件路径。
     * @param filter       可选 过滤器  正则表达式。
     * @param recursive    是否 递归  1表示递归，0表示不递归。
     * @return
     */
    @PostMapping("getFileList")
    @ApiOperation("获取文件列表")
    public GetFileListResponse getFileList(String connectionId, String filePath, String filter, int recursive) {
        System.out.println("====================== 文件访问 获取文件列表 开始 ============================");
        GetFileListResponse getFileListResponse = iFile.getFileList(connectionId, filePath, filter, recursive);
        System.out.println(getFileListResponse);
        System.out.println("====================== 文件访问 获取文件列表 结束 ============================");
        return getFileListResponse;
    }

    /**
     * 判断文件是否存在
     *
     * @param connectionId 必填 文件系统连接标识
     * @param filePath     必填 文件路径 填写完整的文件路径或目录。
     * @return
     */
    public FileExistResponse fileExist(String connectionId, String filePath) {
        System.out.println("====================== 文件访问 判断文件是否存在 开始 ============================");
        FileExistResponse fileExistResponse = iFile.fileExist(connectionId, filePath);
        System.out.println(fileExistResponse);
        System.out.println("====================== 文件访问 判断文件是否存在 结束 ============================");
        return fileExistResponse;
    }

    /**
     * 获取文件属性
     *
     * @param connectionId 必填 文件系统连接标识
     * @param fileName     必填 文件名  填写文件名称或目录。
     * @return
     */
    @PostMapping("getFileInfo")
    @ApiOperation("获取文件属性")
    public GetFileInfoResponse getFileInfo(String connectionId, String fileName) {
        System.out.println("====================== 文件访问 获取文件属性 开始 ============================");
        GetFileInfoResponse getFileInfoResponse =  iFile.getFileInfo(connectionId, fileName);
        System.out.println(getFileInfoResponse);
        System.out.println("====================== 文件访问 获取文件属性 结束 ============================");
        return getFileInfoResponse;
    }

    /**
     * 文件权限设置
     *
     * @param fullPath  必填 文件或目录路径
     * @param userGroup 必填 用户组
     * @param user      必填 用户
     * @param authority 可选 权限  RWX形式,设置访问权限时需填写，为空表示设置文件或目录的所有者。
     * @param beInherit 可选 是否子目录继承 1表示是，2表示否；默认为否。
     * @return
     */
    public Response setAuthority(String fullPath, String userGroup, String user, String authority, int beInherit) {
        System.out.println("====================== 文件访问 文件权限设置 开始 ============================");
        Response response =  iFile.setAuthority(fullPath, userGroup, user, authority, beInherit);
        System.out.println(response);
        System.out.println("====================== 文件访问 文件权限设置 结束 ============================");
        return response;

    }

}

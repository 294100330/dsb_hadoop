package paas.storage.utils;

import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;

/**
 * @author luowei
 * Creation time 2021/1/27 16:54
 */
public abstract class AuthorityUtils {

    /**
     * 为文件系统添加执行权限
     *
     * @param permission 权限
     * @return 文件/目录权限
     */
    public static FsPermission fileSystemAction(String permission) {
        FsPermission fsPermission = null;
        switch (permission) {
            // 文件相当于：----------
            // 目录相当于：d---------
            case "000":
                fsPermission = new FsPermission(FsAction.NONE, FsAction.NONE, FsAction.NONE);
                break;
            // 文件相当于：---x--x--x
            // 目录相当于：d--x--x--x
            case "111":
                fsPermission = new FsPermission(FsAction.EXECUTE, FsAction.EXECUTE, FsAction.EXECUTE);
                break;
            // 文件相当于：--w--w--w-
            // 目录相当于：d-w--w--w-
            case "222":
                fsPermission = new FsPermission(FsAction.WRITE, FsAction.WRITE, FsAction.WRITE);
                break;
            // 文件相当于：--wx-wx-wx
            // 目录相当于：d-wx-wx-wx
            case "333":
                fsPermission = new FsPermission(FsAction.WRITE_EXECUTE, FsAction.WRITE_EXECUTE, FsAction.WRITE_EXECUTE);
                break;
            // 文件相当于：-r--r--r--
            // 目录相当于：dr--r--r--
            case "444":
                fsPermission = new FsPermission(FsAction.READ, FsAction.READ, FsAction.READ);
                break;
            // 文件相当于：-r-xr-xr-x
            // 目录相当于：dr-xr-xr-x
            case "555":
                fsPermission = new FsPermission(FsAction.READ_EXECUTE, FsAction.READ_EXECUTE, FsAction.READ_EXECUTE);
                break;
            // 文件相当于：-rw-rw-rw-
            // 目录相当于：drw-rw-rw-
            case "666":
                fsPermission = new FsPermission(FsAction.READ_WRITE, FsAction.READ_WRITE, FsAction.READ_WRITE);
                break;
            // 文件相当于：-rwxrwxrwx
            // 目录相当于：drwxrwxrwx
            case "777":
                fsPermission = new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.ALL);
                break;
        }
        return fsPermission != null ? fsPermission : new FsPermission(FsAction.ALL, FsAction.READ_EXECUTE, FsAction.READ_EXECUTE);
    }
}

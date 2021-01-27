package paas.storage.exception;

/**
 * @author 豆沙包
 * Creation time 2021/1/27 15:27
 */
public class QCRuntimeException extends RuntimeException {

    public QCRuntimeException(String msg) {
        super(msg);
    }

    public QCRuntimeException(String msg, Throwable e) {
        super(msg, e);
    }
}

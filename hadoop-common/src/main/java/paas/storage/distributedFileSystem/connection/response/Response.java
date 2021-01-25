package paas.storage.distributedFileSystem.connection.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import paas.storage.enums.ResponseEnum;

/**
 * PAAS 平台返回结果基类
 *
 * @author luowei
 * Creation time 2021/1/23 18:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /**
     * 任务执行状态 必填 1表示成功，0表示失败。
     */
    private int taskStatus;

    /**
     * 错误码 可选
     */
    private int errorCode;

    /**
     * 失败原因 可选 失败时填写。
     */
    private String errorMsg;


    public static class Builder extends SuperClassBuilder<Response, Builder> {

        public Builder(ResponseEnum responseEnum) {
            super(responseEnum);
        }

        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public Response build() {
            return new Response();
        }
    }

    public abstract static class SuperClassBuilder<T extends Response, B extends SuperClassBuilder<T, B>> {

        private final B theBuilder = this.getThis();
        /**
         * 任务执行状态 必填 1表示成功，0表示失败。
         */
        private final int taskStatus;
        /**
         * 错误码 可选
         */
        private int errorCode;
        /**
         * 失败原因 可选 失败时填写。
         */
        private String errorMsg;

        public SuperClassBuilder(ResponseEnum responseEnum) {
            this.taskStatus = responseEnum.getTaskStatus();
        }

        /**
         * 错误码 可选
         */
        public B errorCode(int errorCode) {
            this.errorCode = errorCode;
            return theBuilder;
        }

        /**
         * 失败原因 可选 失败时填写。
         */
        public B errorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return theBuilder;
        }


        /**
         * 供子类获取自身builder抽象类;
         *
         * @return
         */
        protected abstract B getThis();

        /**
         * 供子类实现的抽象构建对象
         *
         * @return
         */
        public abstract T build();
    }
}

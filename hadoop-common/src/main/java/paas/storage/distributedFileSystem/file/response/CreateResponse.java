package paas.storage.distributedFileSystem.file.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import paas.storage.enums.ResponseEnum;
import paas.storage.connection.Response;

/**
 * @author luowei
 * Creation time 2021/1/23 19:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateResponse extends Response {

    private String filePath;


    public static class SuperClassBuilder extends Response.SuperClassBuilder<CreateResponse, SuperClassBuilder> {

        public SuperClassBuilder(ResponseEnum responseEnum) {
            super(responseEnum);
        }

        private String filePath;

        public SuperClassBuilder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        @Override
        protected SuperClassBuilder getThis() {
            return this;
        }

        @Override
        public CreateResponse build() {
            return new CreateResponse(this.filePath);
        }
    }
}

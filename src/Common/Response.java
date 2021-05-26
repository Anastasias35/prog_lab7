package Common;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponseType responseType;
    private String inf;

    public Response(ResponseType responseType, String inf){
        this.responseType=responseType;
        this.inf=inf;
    }

    public ResponseType getResponseType(){
        return responseType;
    }

    public String getInf(){
        return inf;
    }

}

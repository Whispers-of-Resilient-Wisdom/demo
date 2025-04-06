package org.qiyu.live.core.server.common;

import lombok.Getter;
import lombok.Setter;
import org.qiyu.live.im.constants.ImConstants;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**

 */
@Setter
@Getter
public class ImMsg implements Serializable {

    @Serial
    private static final long serialVersionUID = -6567417873780541989L;
    //魔数 用于做基本校验
    private short magic;

    //用于标识当前消息的作用，后续会交给不同的handler去处理
    private int code;

    //用于记录body的长度
    private int len;

    //存储消息体的内容，一般会按照字节数组的方式去存放
    private byte[] body;


    private String content;


    public static ImMsg build(int code,String data) {
        ImMsg imMsg = new ImMsg();
        imMsg.setMagic(ImConstants.DEFAULT_MAGIC);
        imMsg.setCode(code);
        imMsg.setBody(data.getBytes());
        imMsg.setLen(imMsg.getBody().length);
        imMsg.setContent(data);
        return imMsg;
    }

    @Override
    public String toString() {
        return "ImMsg{" +
                "magic=" + magic +
                ", len=" + len +
                ", code=" + code +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}

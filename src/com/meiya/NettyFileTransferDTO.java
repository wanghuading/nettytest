package com.meiya;

/**
 * 客户端和服务端传输对象
 */
public final class NettyFileTransferDTO {
    private byte[] fileBytes; // 文件内容编码
    private Object body;// 文件相关信息

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}

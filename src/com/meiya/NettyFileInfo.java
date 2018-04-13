package com.meiya;

/**
 * 文件相关信息
 *
 * Created by wanghd on 2017/8/14.
 */
public class NettyFileInfo {
    private String deviceType; // 设备类型
    private String deviceSerialNum; // 设备序列号
    private String deviceLocationCode; // 设备所在区域
    private int byteSize;// 文件字节数大小
    private String name; // 文件名称
    private String md5; // 文件MD5
    private boolean isFileSend; // 文件是否已发送
    private int requestType; // 0 是查询， 1 是上传

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceSerialNum() {
        return deviceSerialNum;
    }

    public void setDeviceSerialNum(String deviceSerialNum) {
        this.deviceSerialNum = deviceSerialNum;
    }

    public String getDeviceLocationCode() {
        return deviceLocationCode;
    }

    public void setDeviceLocationCode(String deviceLocationCode) {
        this.deviceLocationCode = deviceLocationCode;
    }

    public int getByteSize() {
        return byteSize;
    }

    public void setByteSize(int byteSize) {
        this.byteSize = byteSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isFileSend() {
        return isFileSend;
    }

    public void setFileSend(boolean fileSend) {
        isFileSend = fileSend;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }
}

package com.meiya.client;

import java.io.File;

/**
 * 服务层传输对象
 */
public final class NettyFileDTO {
    // 设备信息
    private String deviceType;// 设备型号
    private String deviceSerialNum;// 设备序列号
    private String deviceLocationCode; // 设备所在区域编码

    // 文件
    private File file;

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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

package com.company.core.util.ftp;

import lombok.Data;

@Data
public class FTPConfig {
	
	private String host;
    private Integer port;
    private String userName;
    private String password;
    private String path = "";
    /**
     * 重连的间隔时间
     * 默认20秒
     */
    private int reconnectTimeout = 1000 * 20;
    /**
     * 为0 不控制超时,单位毫秒
     * 默认1分钟
     */
    private int connectTimeout = 1000 * 60;
    /**
     * 为0 打开数据连接为长暂停，单位毫秒
     */
    private int dataTimeout = -1;
    /**
     * 设置的等待时间之间发送控制连接保持活动消息 当处理文件上传或下载。
     * 为0 禁用 单位毫秒
     */
    private int controlKeepAliveTimeout = 1000 * 60;
    /**
     * 设置等待多久控制保持活着的回复。
     * 单位毫秒
     */
    private int controlKeepAliveReplyTimeout = 1000 * 60;
    /**
     * 上传下载数据缓冲区
     */
    private int bufferSize = 1024 * 10;
}

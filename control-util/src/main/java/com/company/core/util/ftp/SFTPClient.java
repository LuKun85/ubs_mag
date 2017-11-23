package com.company.core.util.ftp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SFTPClient {

	private ChannelSftp getChannel(String user, String pass, String ip, int prot) throws Exception {
		JSch jsch = new JSch();
		Session session = jsch.getSession(user, ip, prot);
		if (pass != null) {
			session.setPassword(pass);
		}

		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		session.setTimeout(10_000); // 设置timeout时间
		session.connect(); // 通过Session建立链接

		Channel channel = session.openChannel("sftp"); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		return (ChannelSftp) channel;
	}

	public boolean put(FileInputStream src, String des, SFTPConfig config) {
		ChannelSftp channel = null;
		try {
			channel = getChannel(config.getFTP_USER(), config.getFTP_PASS(), config.getFTP_IP(), config.getFTP_PORT());
			channel.put(src, des);
			channel.ls(des);
			return true;
		} catch (Exception e) {
			log.error("上传文件失败", e);
			return false;
		} finally {
			if (channel != null) {
			    try {
                    channel.getSession().disconnect();
                } catch (JSchException e) {
                    log.error("关闭sftp-session异常", e);
                }
				channel.quit();
			}
		}
	}

	public byte[] get(String file, SFTPConfig config) {
		ChannelSftp channel = null;
		try {
			channel = getChannel(config.getFTP_USER(), config.getFTP_PASS(), config.getFTP_IP(), config.getFTP_PORT());
			InputStream inputStream = channel.get(file);
			return IOUtils.toByteArray(inputStream);
		} catch (Exception e) {
			log.error("获取文件失败", e);
			return null;
		} finally {
			if (channel != null) {
			    try {
                    channel.getSession().disconnect();
                } catch (JSchException e) {
                    log.error("关闭sftp-session异常", e);
                }
				channel.quit();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		SFTPConfig config = new SFTPConfig();
		config.setFTP_IP("172.16.1.42");
		config.setFTP_PASS("mysftp");
		config.setFTP_USER("mysftp");
		config.setFTP_PORT(22);
		SFTPClient client = new SFTPClient();

		System.out.println(client.get("upload/20170219/IND170219*ACOMA.Z", config));;
	}
}

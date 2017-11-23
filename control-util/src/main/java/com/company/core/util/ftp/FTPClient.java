package com.company.core.util.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import lombok.extern.slf4j.Slf4j;

/**
 * FTP工具类，封装FTPClient的文件上传、下载、删除等操作
 * 
 * @date 2015-07-20
 */
@Slf4j
public class FTPClient implements DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(FTPClient.class);
	private static String LOCAL_CHARSET = "GBK";

	// FTP协议里面，规定文件名编码为iso-8859-1
	private static String SERVER_CHARSET = "ISO-8859-1";

	private org.apache.commons.net.ftp.FTPClient ftpClient = new org.apache.commons.net.ftp.FTPClient();

	private FTPConfig ftpConfig;

	public FTPConfig getFtpConfig() {
		return ftpConfig;
	}

	public void setFtpConfig(FTPConfig ftpConfig) {
		this.ftpConfig = ftpConfig;
	}

	/**
	 * 初始化客户端并完成对服务端的连接
	 * 
	 * @throws IOException
	 * @throws SocketException
	 */
	public boolean connectServer() throws SocketException, IOException {
		if (!ftpClient.isConnected()) {
			return connectServer(ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUserName(),
					ftpConfig.getPassword(), ftpConfig.getPath());
		}
		return true;
	}

	/**
	 * 初始化客户端并完成对服务端的连接
	 * 
	 * @param host
	 *            远程服务器地址
	 * @param port
	 *            端口号
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param path
	 *            远程路径 值可以为空
	 * @throws IOException
	 * @throws SocketException
	 */
	public boolean connectServer(String host, int port, String username, String password, String path)
			throws SocketException, IOException {
		// ftpClient.setConnectTimeout(ftpConfig.getConnectTimeout());
		ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
		ftpClient.connect(host, port);
		ftpClient.setBufferSize(ftpConfig.getBufferSize());
		// ftpClient.setControlKeepAliveReplyTimeout(ftpConfig.getControlKeepAliveReplyTimeout());
		// ftpClient.setControlKeepAliveTimeout(ftpConfig.getControlKeepAliveTimeout());
		ftpClient.setDataTimeout(ftpConfig.getDataTimeout());
		if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			ftpClient.disconnect();
			logger.error("FTP服务器[" + host + "]拒绝连接。");
			return false;
		}
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			if (ftpClient.login(username, password)) {
				if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
					LOCAL_CHARSET = "UTF-8";
				}
			}
		}

		if (path.length() != 0) {
			ftpClient.changeWorkingDirectory(path);
		}
		// 设置ftpClient的默认FileType
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();
		return true;
	}

	public boolean reconnect() {
		boolean flag = true;
		if (!ftpClient.isConnected() || ftpClient == null) {
			for (int i = 0; i < 3; i++) {
				try {
					logger.info("FTP第" + (i + 1) + "次连接中...");
					Thread.sleep(ftpConfig.getReconnectTimeout());
					connectServer();
				} catch (Exception e) {
					logger.error("FTP重新连接失败。");
				}
				if (ftpClient.isConnected()) {
					flag = true;
					break;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * 关闭FTP连接
	 * 
	 * @throws IOException
	 */
	public void closeServer() throws IOException {
		if (ftpClient != null && ftpClient.isConnected()) {
			ftpClient.logout();
			ftpClient.disconnect();
		}
	}

	/**
	 * 设置FTP的文件传输类型 需要指定文件传输类型，否则默认是ASCII类型，会导致二进制文件传输损坏
	 * 
	 * @param fileType
	 *            如:FTP.BINARY_FILE_TYPE
	 * @throws IOException
	 */
	public void setFileType(int fileType) throws IOException {
		ftpClient.setFileType(fileType);
	}

	/**
	 * 改变FTP的工作目录
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean changeDirectory(String path) throws IOException {
		return ftpClient.changeWorkingDirectory(path);
	}

	/**
	 * 在服务端创建目录
	 * 
	 * @param path
	 *            可以是相对目录或绝对目录
	 * @return
	 * @throws IOException
	 */
	public boolean createDirectory(String path) throws IOException {
		return ftpClient.makeDirectory(path);
	}

	/**
	 * 在服务端创建目录，支持多级子目录
	 * 
	 * @param path
	 *            可以是相对目录或绝对目录
	 * @return
	 * @throws IOException
	 */
	public void createDirectorys(String path) throws IOException {
		StringTokenizer s = new StringTokenizer(path, "/");
		s.countTokens();
		String pathName = getWorkingDirectory();
		while (s.hasMoreElements()) {
			pathName = pathName + "/" + (String) s.nextElement();
			ftpClient.makeDirectory(pathName);
		}
	}

	public String getWorkingDirectory() throws IOException {
		return ftpClient.printWorkingDirectory().replace("\"", "");
	}

	/**
	 * 删除一个FTP服务器上的目录（目录为空）
	 * 
	 * @param path
	 *            目录路径
	 * @return
	 * @throws IOException
	 */
	public boolean removeDirectory(String path) throws IOException {
		return ftpClient.removeDirectory(path);
	}

	/**
	 * 删除一个FTP服务器上的目录
	 * 
	 * @param path
	 *            目录路径
	 * @param isAll
	 *            是否删除所有子目录和文件
	 * @return
	 * @throws IOException
	 */
	public boolean removeDirectory(String path, boolean isAll) throws IOException {
		if (!isAll) {
			return removeDirectory(path);
		}
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr == null || ftpFileArr.length == 0) {
			return removeDirectory(path);
		}
		for (int i = 0; i < ftpFileArr.length; i++) {
			FTPFile ftpFile = ftpFileArr[i];
			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				removeDirectory(path + "/" + name, true);
			} else if (ftpFile.isFile()) {
				deleteFile(path + "/" + name);
			} else if (ftpFile.isSymbolicLink()) {

			} else if (ftpFile.isUnknown()) {

			}
		}
		return removeDirectory(path);
	}

	/**
	 * 返回给定目录下的文件
	 * 
	 * @param path
	 * @return 目录下文件名的集合
	 * @throws IOException
	 */
	public List<String> getFileList(String path) throws IOException {
		ftpClient.enterLocalPassiveMode();

		FTPFile[] ftpFiles = ftpClient.listFiles(path);
		List<String> retList = new ArrayList<String>();
		if (ftpFiles == null || ftpFiles.length == 0) {
			return retList;
		}
		for (int i = 0; i < ftpFiles.length; i++) {
			FTPFile ftpFile = ftpFiles[i];
			if (ftpFile.isFile()) {
				retList.add(ftpFile.getName());
			}
		}
		return retList;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 *            包含文件目录的文件路径
	 * @return 删除结果，是否成功
	 * @throws IOException
	 */
	public boolean deleteFile(String filePath) throws IOException {
		return ftpClient.deleteFile(filePath);
	}

	/**
	 * 上传文件，并重命名
	 * 
	 * @param filePath
	 *            上传的文件，包含目录的文件名
	 * @param fileName
	 *            新的文件名
	 * @return 上传结果，是否成功
	 * @throws IOException
	 */
	public boolean uploadFile(String filePath, String fileName) throws IOException {
		if (!connectServer()) {
			return false;
		}
		boolean flag = false;
		InputStream iStream = null;
		try {
			iStream = new FileInputStream(filePath);
			flag = ftpClient.storeFile(fileName, iStream);
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	/**
	 * 上传文件
	 * 
	 * @param filePath
	 *            上传的文件，包含目录的文件名
	 * @return 上传结果，是否成功
	 * @throws IOException
	 */
	public boolean uploadFile(String filePath) throws IOException {
		File file = new File(filePath);
		return uploadFile(filePath, file.getName());
	}

	/**
	 *
	 * @Title: uploadSubDir @Description: 上传子目录 @param @param
	 * file @param @return @param @throws IOException 设定文件 @return boolean
	 * 返回类型 @throws
	 */
	private boolean uploadSubDir(File file) throws IOException {
		boolean success = false;
		if (file.isDirectory()) { // 文件夹上传
			String fileName = new String(file.getName().getBytes(LOCAL_CHARSET), SERVER_CHARSET);
			ftpClient.makeDirectory(fileName);
			ftpClient.changeWorkingDirectory(fileName);
			String[] files = file.list();
			for (int i = 0; i < files.length; i++) {
				File subFile = new File(file.getPath() + "/" + files[i]);
				if (subFile.isDirectory()) {
					uploadSubDir(subFile);
					ftpClient.changeToParentDirectory();
				} else {
					FileInputStream inStream = null;
					try {
						inStream = new FileInputStream(subFile);
						success = ftpClient.storeFile(
								new String(subFile.getName().getBytes(LOCAL_CHARSET), SERVER_CHARSET), inStream);
						if (!success) {
							return success;
						}
					} finally {
						if (inStream != null) {
							inStream.close();
						}
					}
				}
			}
		} else { // 文件上传
			FileInputStream inStream = null;
			try {
				File subFile = new File(file.getPath());
				inStream = new FileInputStream(subFile);
				success = ftpClient.storeFile(new String(subFile.getName().getBytes(LOCAL_CHARSET), SERVER_CHARSET),
						inStream);
				if (!success) {
					return success;
				}
			} finally {
				if (inStream != null) {
					inStream.close();
				}
			}
		}
		return success;
	}

	/**
	 *
	 * @Title: uploadFile @Description: 上传文件 @param @param
	 * localFile @param @param newName @param @return @param @throws IOException
	 * 设定文件 @return boolean 返回类型 @throws
	 */
	public boolean uploadFile(File localFile, String newName) throws IOException {
		boolean success = false;
		InputStream inStream = null;
		try {
			// 处理中文
			newName = new String(newName.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
			// 创建目录
			createDirectorys(FilenameUtils.getPath(newName));
			inStream = new FileInputStream(localFile);
			// 上传文件
			success = ftpClient.storeFile(newName, inStream);
		} finally {
			if (inStream != null) {
				inStream.close();
			}
		}
		return success;
	}

	/**
	 *
	 * @Title: uploadDir @Description: 上传目录 @param @param
	 * localFile @param @param newName @param @return @param @throws IOException
	 * 设定文件 @return boolean 返回类型 @throws
	 */
	public boolean uploadDir(File localFile, String newName) throws IOException {
		boolean success = false;
		String rootDirectory = getWorkingDirectory();
		// 处理中文
		newName = new String(newName.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
		// 创建根目录
		createDirectorys(newName);
		String[] files = localFile.list();
		for (int i = 0; i < files.length; i++) {
			// 切换至根目录
			if (!ftpClient.changeWorkingDirectory(rootDirectory + "/" + newName)) {
				throw new RuntimeException("change working directory failed.");
			}
			File subFile = new File(localFile.getPath() + "/" + files[i]);
			if (subFile.isDirectory()) {
				// 上传子目录
				success = uploadSubDir(subFile);
				if (!success) {
					return success;
				}
			} else {
				// 上传根目录文件
				InputStream inStream = null;
				try {
					inStream = new FileInputStream(subFile);
					// 上传文件
					success = ftpClient.storeFile(new String(subFile.getName().getBytes(LOCAL_CHARSET), SERVER_CHARSET),
							inStream);
					if (!success) {
						return success;
					}
				} finally {
					if (inStream != null) {
						inStream.close();
					}
				}
			}
		}
		return success;
	}

	/**
	 * 上传文件，从InputStream
	 * 
	 * @param iStream
	 *            文件流
	 * @param newName
	 *            新的文件名
	 * @return 上传结果，是否成功
	 * @throws IOException
	 */
	public boolean uploadFile(InputStream iStream, String newName) throws IOException {
		boolean flag = false;
		try {
			flag = ftpClient.storeFile(newName, iStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	/**
	 * 下载文件
	 * 
	 * @param remoteFileName
	 *            远程文件名
	 * @param localFileName
	 *            本地文件
	 * @return 返回操作结果
	 * @throws IOException
	 */
	public boolean download(String remoteFileName, String localFileName) throws IOException {
		ftpClient.enterLocalPassiveMode();
		boolean flag = false;
		File outfile = new File(localFileName);
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream(outfile);
			flag = ftpClient.retrieveFile(new String(remoteFileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET), oStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (oStream != null) {
				oStream.close();
			}
		}
		return flag;
	}

	/**
	 * 下载文件
	 * 
	 * @param remoteFileName
	 *            远程文件名
	 * @param out
	 *            本地文件
	 * @return 返回操作结果
	 * @throws IOException
	 */
	public boolean download(String remoteFileName, OutputStream out) throws IOException {
		boolean flag = false;
		try {
			flag = ftpClient.retrieveFile(new String(remoteFileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET), out);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return flag;
	}

	/**
	 * 下载文件，返回InputStream
	 * 
	 * @param sourceFileName
	 *            远程文件
	 * @return InputStream
	 * @throws IOException
	 */
	public byte[] downFile(String sourceFileName) throws IOException {
		try {
			InputStream inputStream = ftpClient.retrieveFileStream(sourceFileName);
			return IOUtils.toByteArray(inputStream);
		} catch (Exception e) {
			log.error("获取文件失败", e);
			return null;
		} 
	}

	@Override
	public void destroy() throws Exception {
		closeServer();
	}

	public boolean isConnected() {
		return ftpClient.isConnected();
	}

	public static void main(String[] args) throws Exception {
		FTPConfig ftpConfig =  new FTPConfig();
        ftpConfig.setUserName("yinlianftp");
        ftpConfig.setPassword("yinadminftp");
        ftpConfig.setPort(9902);
        ftpConfig.setHost("140.207.82.189");

        FTPClient ftp = new FTPClient();
        ftp.setFtpConfig(ftpConfig);
        ftp.connectServer();
        ftp.getFileList("/home/yinlianftp/48542900");
        ftp.closeServer();

	}
}
package com.company.core.util.ftp;

import lombok.Data;

@Data
public class SFTPConfig {
	private String FTP_USER;
	private String FTP_PASS;
	private String FTP_IP;
	private int FTP_PORT = 22;
}

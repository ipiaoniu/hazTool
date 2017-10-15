package com.piaoniu.biz;

import com.piaoniu.biz.config.service.ConfigManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * Created by Stephen Cai on 2017-10-09 09:52.
 */
public class FTPClientUtils {

	FTPClient ftp = null;

	public FTPClientUtils(String host,  int port, String user, String pwd) throws Exception {
		ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		int reply;
		ftp.connect(host, port);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new Exception("Exception in connecting to FTP Server");
		}
		ftp.login(user, pwd);
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
	}

	public void uploadInputStream(InputStream input, String fileName, String hostDir) throws Exception {
			this.ftp.storeFile(hostDir + fileName, input);
	}


	public void uploadFile(String localFileFullName, String fileName, String hostDir)
			throws Exception {
		try (InputStream input = new FileInputStream(new File(localFileFullName))) {
			this.ftp.storeFile(hostDir + fileName, input);
		}
	}

	public void disconnect() {
		if (this.ftp.isConnected()) {
			try {
				this.ftp.logout();
				this.ftp.disconnect();
			} catch (IOException f) {
				// do nothing as file is already saved to server
			}
		}
	}

	public static void main(String[] args) throws Exception {
//		System.out.println("Start");
//		FTPUploader ftpUploader = new FTPUploader("ftp.journaldev.com", "ftpUser", "ftpPassword");
//		//FTP server path is relative. So if FTP account HOME directory is "/home/pankaj/public_html/" and you need to upload
//		// files to "/home/pankaj/public_html/wp-content/uploads/image2/", you should pass directory parameter as "/wp-content/uploads/image2/"
//		ftpUploader.uploadFile("D:\\Pankaj\\images\\MyImage.png", "image.png", "/wp-content/uploads/image2/");
//		ftpUploader.disconnect();
//		System.out.println("Done");
		System.out.println("Start");
		FTPClientUtils ftpUploader = new FTPClientUtils(ConfigManager.get("douli.ftp.host"),
				Integer.valueOf(ConfigManager.get("douli.ftp.port")),
				ConfigManager.get("douli.ftp.user"),
				ConfigManager.get("douli.ftp.pwd")
				);
		ftpUploader.uploadFile("/Users/caifeng/Downloads/test.csv", "test.csv", "/");
		ftpUploader.disconnect();
		System.out.println("Done");

	}

}

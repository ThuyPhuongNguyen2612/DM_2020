package control;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import model.Configuration;
import service.ChilkatDownload;

public class Download {
//	String[] arg = {"guest_access@drive.ecepvn.org:/volume1/ECEP/song.nguyen/DW_2020/data/17130044_sang_nhom8.txt", "E:/Warehouse"};
	Configuration config;
	private String username;
	private String password;
	private String host;
	private String rDir;
	private String lDir;
	private int port;
	private String pattern;
	private String emails;
	
	public Download(Configuration config) {
		this.config = config;
		repareDownload();
	}

	private void repareDownload() {
		username = config.getSourceUsername();
		host = config.getSourceHost();
		rDir = "/" + config.getSourceRemoteFile() ;
		lDir = config.getDownloadPath();
		password = config.getSourcePassword();
		port = config.getSourcePort();
		pattern = config.getFileNamePattern();
		emails = config.getToEmails();
	}

	public void DownloadFile() throws AddressException, IOException, MessagingException {
		ChilkatDownload d = new ChilkatDownload();
		d.prepareAndDownload(config.getConfigID(), username, password, host, rDir, lDir, port, pattern, emails);
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return "RepairDownload [config=" + config + ", user=" + username + ", password=" + password + ", host=" + host
				+ ", rfile=" + rDir + ", lfile=" + lDir + ", port=" + port + "]";
	}

	public static void main(String[] args) throws AddressException, IOException, MessagingException {
		Configuration configuration = new Configuration("sinhvien", "root", "1234");
		Download rp = new Download(configuration);
		rp.DownloadFile();
//		rp.toString();
//		rp.writingLog();
	}
}

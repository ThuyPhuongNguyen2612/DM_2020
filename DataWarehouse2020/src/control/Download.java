package control;

import java.io.IOException;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import model.Configuration;
import service.ChilkatDownloadLocalHost;
import service.ChilkatDownloadSShHost;
import service.DBService;
import service.DBServiceImpl;
import service.FileService;
import service.FileServiceImpl;
import service.LogService;
import service.LogServiceImpl;

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
	private String local;

	public Download(Configuration config) {
		this.config = config;
//1.Load các thuộc tính tại dòng config có flag = prepare vào các biến
		prepareDownload();
	}

	private void prepareDownload() {
		username = config.getSourceUsername();
		host = config.getSourceHost();
		rDir = "/" + config.getSourceRemoteFile();
		lDir = config.getDownloadPath();
		password = config.getSourcePassword();
		port = config.getSourcePort();
		pattern = config.getFileNamePattern();
		emails = config.getToEmails();
		local = config.getLocal();
	}

	public boolean DownloadFile() throws AddressException, IOException, MessagingException {
		boolean result = false;
//3.1.1 Download from local
		if (local.equals("y")) {
			ChilkatDownloadLocalHost localDownload = new ChilkatDownloadLocalHost();
			result = localDownload.prepareAndDownload(config.getConfigID(), username, password, host, rDir, lDir, port, pattern,
					emails);
//			System.out.println(this.toString());
		}

//3.2.1 Download from ecepvn
		if (local.equals("n")) {
			ChilkatDownloadSShHost download = new ChilkatDownloadSShHost();
			result = download.prepareAndDownload(config.getConfigID(), username, password, host, rDir, lDir, port, pattern,
					emails);
//			System.out.println(this.toString());
		}
		
		return result;
	}

	@Override
	public String toString() {
		return "RepairDownload [config=" + config + ", user=" + username + ", password=" + password + ", host=" + host
				+ ", rfile=" + rDir + ", lfile=" + lDir + ", port=" + port + "]";
	}

	public static void main(String[] args) throws AddressException, IOException, MessagingException {
		Scanner sc = new Scanner(System.in);
		Configuration configuration;
		FileService fileService = new FileServiceImpl();
		DBService dbService = new DBServiceImpl("staging", "root", "1234");
		LogService logService = new LogServiceImpl();
		while (true) {
			System.out.print("ID: ");
			String command = sc.nextLine();
			System.out.println("command");
			if (command.equals("end")) {
				break;
			}
//		System.out.println(cm);

			try {
				configuration = new Configuration(Integer.parseInt(command), "root", "1234");
				Download rp = new Download(configuration);
				rp.DownloadFile();
//		ExtractToStaging ex = new ExtractToStaging(configuration, fileService, dbService, logService);
//		ex.extractToStaging();
			} catch (Exception e) {
				System.out.println("Wrong config name");
				continue;
			}

		}
	}
}

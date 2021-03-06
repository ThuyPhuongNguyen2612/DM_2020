package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;
import service.WritingError;

public class Configuration {
	private int configID;
	private String configName;
	private String sourceHost;
	private String sourceRemoteFile;
	private String sourceUsername;
	private String sourcePassword;
	private int sourcePort;
	private String fileNamePattern;
	private String fileColumnList;
	private String fileVariables;
	private String downloadPath;
	private String fileDilimiter;
	private String toEmails;
	private String local;
	
	public Configuration(int configID, String userName, String password) {
		Connection connection;
		try {
			connection = DBConnection.getConnection("control", userName, password);
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM configuration WHERE config_id=?");
			ps.setInt(1, configID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				this.configID = configID;
				this.configName = rs.getString("config_name");
				this.sourceHost = rs.getString("source_host");
				this.sourceRemoteFile = rs.getString("source_remote_path");
				this.sourceUsername = rs.getString("source_username");
				this.sourcePassword = rs.getString("source_password");
				this.sourcePort = rs.getInt("source_port");
				this.fileNamePattern = rs.getString("file_name_pattern");
				this.fileColumnList = rs.getString("file_column_list");
				this.fileVariables = rs.getString("file_variables");
				this.downloadPath = rs.getString("download_path");
				this.fileDilimiter = rs.getString("file_dilimiter");
				this.toEmails = rs.getString("error_to_emails");
				this.local = rs.getString("local");
			}
		} catch (SQLException e) {
			WritingError.sendError("Can't connect to control database. Configuration.java", "thuyphuongnguyen0170@gmail.com,creepy120499@gmail.com");;
		}
	}

	public int getConfigID() {
		return configID;
	}

	public String getConfigName() {
		return configName;
	}

	public String getSourceHost() {
		return sourceHost;
	}

	public String getSourceRemoteFile() {
		return sourceRemoteFile;
	}

	public String getSourceUsername() {
		return sourceUsername;
	}

	public String getSourcePassword() {
		return sourcePassword;
	}

	public String getFileColumnList() {
		return fileColumnList;
	}

	public String getFileVariables() {
		return fileVariables;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public int getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}

	public String getFileDilimiter() {
		return fileDilimiter;
	}

	public String getToEmails() {
		return toEmails;
	}

	public String getFileNamePattern() {
		return fileNamePattern;
	}
	
	public String getLocal() {
		return local;
	}
	@Override
	public String toString() {
		return "Configuration [configID=" + configID + ", configName=" + configName + ", sourceHost=" + sourceHost
				+ ", sourceRemoteFile=" + sourceRemoteFile + ", sourceUsername=" + sourceUsername + ", sourcePassword="
				+ sourcePassword + ", sourcePort=" + sourcePort + ", fileNamePattern=" + fileNamePattern
				+ ", fileColumnList=" + fileColumnList + ", fileVariables=" + fileVariables + ", downloadPath="
				+ downloadPath + ", fileDilimiter=" + fileDilimiter + ", toEmails=" + toEmails + " , local=" +local +"]";
	}

	public static void main(String[] args) {
		Configuration configuration = new Configuration(1,"root","1234");
		System.out.println(configuration.toString());
	}
}

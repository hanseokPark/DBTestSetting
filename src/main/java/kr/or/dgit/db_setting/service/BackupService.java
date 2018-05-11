package kr.or.dgit.db_setting.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import kr.or.dgit.application.jdbc.LoadProperties;
import kr.or.dgit.db_setting.dao.ExecuteSql;

public class BackupService implements DaoService {
	private static final BackupService instance = new BackupService();

	public static BackupService getInstance() {
		return instance;
	}

	private BackupService() {
	}

	@Override
	public void service() {
		LoadProperties loadProperties = new LoadProperties();
		Properties properteis = loadProperties.getProperties();

		ExecuteSql.getInstance().execSQL("use " + properteis.getProperty("dbname"));

		checkBackupDir();

		String[] tables = properteis.get("tables").toString().split(",");
		for (String tblName : tables) {
			String sql = String.format("select * from %s", tblName);
			exportData(sql, tblName);
		}

	}

	private void exportData(String sql, String tblName) {
		try {
			ResultSet rs = ExecuteSql.getInstance().execQuerySQL(sql);
			int columnCnt = rs.getMetaData().getColumnCount();
			StringBuilder sb = new StringBuilder();
			// System.out.println("column Cnt " + columnCnt);
			while (rs.next()) {
				for (int i = 1; i <= columnCnt; i++) {
					sb.append(rs.getObject(i) + ",");
				}
				sb.replace(sb.length() - 1, sb.length(), "");
				sb.append("\r\n");
			}
			writeBackupFile(sb.toString(), tblName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void writeBackupFile(String result, String tblName) {
		String resPath = System.getProperty("user.dir") + "\\BackupFiles\\" + tblName + ".txt";
		resPath = resPath.replace("\\", "/");

		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(resPath), "utf8");) {
			osw.write(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void checkBackupDir() {
		File backupDir = new File(System.getProperty("user.dir") + "\\BackupFiles");
		if (backupDir.exists()) {
			for (File file : backupDir.listFiles()) {
				file.delete();
				System.out.printf("%s Delete Success! %n", file.getName());
			}
		} else {
			backupDir.mkdir();
			System.out.printf("%s Create Success! %n", backupDir.getName());
		}
	}

}

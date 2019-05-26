package com.example.demo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class BasicDao {

	static{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	Connection getConnection() throws SQLException{
		String url = "jdbc:mysql://127.0.0.1:3306/feedback?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";
		String user = "root";
		String password = "123456";
		return DriverManager.getConnection(url, user, password);
	}

	String getMethodName(String fieldName,String preffix){
		return preffix + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
	}

	String getFieldName(String table_element){
		table_element = table_element.toLowerCase();
		byte[] buffers = table_element.getBytes();
		for (int i = 1; i < buffers.length - 1; i++) {
			if(buffers[i] == '_'){
				buffers[i + 1] = (byte)(buffers[i + 1] - 32);
			}
		}
		return new String(buffers).replaceAll("_","");
	}

	String getQuestionMark(int count){
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < count; i++) {
			stringBuilder.append("?,");
		}
		return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
	}
}

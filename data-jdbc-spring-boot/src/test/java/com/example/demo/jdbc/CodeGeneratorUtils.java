package com.example.demo.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CodeGeneratorUtils {
    private static final BasicDao basicDao = new BasicDao();

    /**
     * 生成rowMapper配置类的代码
     * @return 类的内容
     */
    public static String getRowMapper() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Connection connection = basicDao.getConnection();
            //如果想只针对特定前缀的表生成代码，可以用"show tables like 'PREFIX_%'"
            ResultSet resultset = connection.prepareStatement("show tables").executeQuery();
            while (resultset.next()){
                String tableName = resultset.getString(1);
                getRowMapper(tableName,connection,stringBuilder);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 生成rowMapper配置类的代码
     * @param tableName 表的名称
     * @return 类的内容
     */
    public static String getRowMapper(String tableName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Connection connection = basicDao.getConnection();
            getRowMapper(tableName,connection,stringBuilder);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static StringBuilder getRowMapper(String tableName,Connection connection,StringBuilder stringBuilder) throws SQLException{
        String fieldName = basicDao.getFieldName(tableName);
        //用表的名字作为bean的id
        stringBuilder.append("\t@Bean(\"").append(tableName).append("\")\n\tpublic RowMapper<")
                .append(basicDao.getMethodName(fieldName, "")).append("Entity> getRowMapper")
                .append(basicDao.getMethodName(basicDao.getFieldName(tableName),""))
                .append("(){\n\t\treturn (resultSet,i)->{\n");
        stringBuilder.append("\t\t\t").append(basicDao.getMethodName(fieldName, ""))
                .append("Entity ").append(fieldName)
                .append(" = new ").append(basicDao.getMethodName(fieldName, ""))
                .append("Entity();\n");
        ResultSet resultset1 = connection.prepareStatement("select * from "+tableName+" limit 1").executeQuery();
        ResultSetMetaData rsmd = resultset1.getMetaData();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            String columnName = rsmd.getColumnName(i);
            String columnType = rsmd.getColumnTypeName(i);
            String fieldName1 = basicDao.getFieldName(columnName);
            String fieldTypeName;
            switch (columnType){
                case "VARCHAR": fieldTypeName = "String";break;
                case "INT":fieldTypeName = "Int";break;
                case "TIMESTAMP":fieldTypeName = "Timestamp";break;
                default: fieldTypeName = "Object";//有更多类型的话自行补充
            }
            stringBuilder.append("\t\t\t").append(fieldName)
                    .append(basicDao.getMethodName(fieldName1,".set"))
                    .append("(resultSet.get")
                    .append(fieldTypeName).append("(\"")
                    .append(columnName).append("\"));\n");
        }
        stringBuilder.append("\t\t\treturn ").append(fieldName).append(";\n\t\t};\n\t}\n\n");
        return stringBuilder;
    }
    /**
     * 生成实体类的内容
     * @return 返回实体类的代码内容
     */
    public static String getEntity(){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Connection connection = basicDao.getConnection();
            //如果想只针对特定前缀的表生成代码，可以用"show tables like 'PREFIX_%'"
            ResultSet resultset = connection.prepareStatement("show tables").executeQuery();
            while (resultset.next()){
                String tableName = resultset.getString(1);
                getEntity(tableName,connection,stringBuilder);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 生成实体类的内容
     * @param tableName 表的名称
     * @return 返回实体类的代码内容
     */
    public static String getEntity(String tableName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Connection connection = basicDao.getConnection();
            getEntity(tableName,connection,stringBuilder);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static StringBuilder getEntity(String tableName,Connection connection,StringBuilder stringBuilder) throws SQLException{
        stringBuilder.append("-----------beginTag: ").append(tableName).append(": ")
                .append(basicDao.getMethodName(basicDao.getFieldName(tableName), ""))
                .append("Entity").append(" ------------------\n");
        ResultSet resultset1 = connection.prepareStatement("select * from "+tableName+" limit 1").executeQuery();
        ResultSetMetaData rsmd = resultset1.getMetaData();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            String columnType = rsmd.getColumnTypeName(i);
            String columnName = rsmd.getColumnName(i);
            String fieldName1 = basicDao.getFieldName(columnName);
            String fieldTypeName;
            switch (columnType){
                case "VARCHAR": fieldTypeName = "String";break;
                case "INT":fieldTypeName = "Integer";break;
                case "TIMESTAMP":fieldTypeName = "Timestamp";break;
                default: fieldTypeName = "Object";//有更多类型的话自行补充
            }
            stringBuilder.append("\tprivate ").append(fieldTypeName).append(" ")
                    .append(fieldName1).append(";\n");
        }
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            String columnType = rsmd.getColumnTypeName(i);
            String columnName = rsmd.getColumnName(i);
            String fieldName1 = basicDao.getFieldName(columnName);
            String fieldTypeName;
            switch (columnType){
                case "INT":fieldTypeName = "Integer";break;
                case "VARCHAR": fieldTypeName = "String";break;
                case "TIMESTAMP":fieldTypeName = "Timestamp";break;
                default: fieldTypeName = "Object";//有更多类型的话自行补充
            }
            stringBuilder.append("\tpublic ").append(fieldTypeName)
                    .append(basicDao.getMethodName(fieldName1," get")).append("(){\n\t\treturn ")
                    .append(fieldName1).append(";\n\t}\n");
            stringBuilder.append("\tpublic void ").append(basicDao.getMethodName(fieldName1,"set"))
                    .append("(").append(fieldTypeName).append(" ").append(fieldName1).append("){\n\t\tthis.")
                    .append(fieldName1).append(" = ").append(fieldName1).append(";\n\t}\n");
        }
        stringBuilder.append("-----------------endTag: ").append(tableName).append("--------------------\n\n");
        return stringBuilder;
    }

    /**
     * 生成增删改查的sql语句
     * @return 生成的代码
     */
    public static String getSqls(){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Connection connection = basicDao.getConnection();
            //如果想只针对特定前缀的表生成代码，可以用"show tables like 'PREFIX_%'"
            ResultSet resultset = connection.prepareStatement("show tables").executeQuery();
            while (resultset.next()){
                String tableName = resultset.getString(1);
                getSqls(tableName,connection,stringBuilder);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 生成增删改查的sql语句
     * @param tableName 表的名称
     * @return 生成的代码
     */
    public static String getSqls(String tableName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Connection connection = basicDao.getConnection();
            getSqls(tableName,connection,stringBuilder);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static StringBuilder getSqls(String tableName,Connection connection,StringBuilder stringBuilder) throws SQLException {
        stringBuilder.append("\t/*beginTag: ").append(tableName).append("*/\n");
        ResultSet resultset1 = connection.prepareStatement("select * from "+tableName+" limit 1").executeQuery();
        ResultSetMetaData rsmd = resultset1.getMetaData();
        int columnCount = rsmd.getColumnCount();
        stringBuilder.append("\tString SELECT_COUNT_SQL = \"select count(*) from ").append(tableName).append(" \";\n");

        stringBuilder.append("\tString SELECT_SQL = \"select ");
        for (int i = 1; i <= columnCount; i++) {
            String columnName = rsmd.getColumnName(i);
            stringBuilder.append(columnName).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1)
                .append(" from ").append(tableName).append(" \";\n");

        stringBuilder.append("\tString SELECT_BY_ID_SQL = \"select ");
        for (int i = 1; i <= columnCount; i++) {
            String columnName = rsmd.getColumnName(i);
            stringBuilder.append(columnName).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1)
                .append(" from ").append(tableName).append(" where id = ?\";\n");

        stringBuilder.append("\tString INSERT_SQL = \"insert into ").append(tableName).append("(");
        for (int i = 2; i <= columnCount; i++) {
            String columnName = rsmd.getColumnName(i);
            stringBuilder.append(columnName).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1)
                .append(") values (").append(basicDao.getQuestionMark(columnCount - 1)).append(")\";\n");

        stringBuilder.append("\tString UPDATE_SQL = \"update ").append(tableName).append(" set ");
        for (int i = 2; i <= columnCount; i++) {
            String columnName = rsmd.getColumnName(i);
            stringBuilder.append(columnName).append(" = ?,");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1)
                .append(" where id = ?").append("\";\n");

        stringBuilder.append("\tString DELETE_SQL = \"delete from ").append(tableName)
                .append(" where id = ?").append("\";\n");
        stringBuilder.append("\t/*endTag: ").append(tableName).append("*/\n\n");
        return stringBuilder;
    }
}

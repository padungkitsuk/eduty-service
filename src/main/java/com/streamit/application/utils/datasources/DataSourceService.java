package com.streamit.application.utils.datasources;

import com.google.gson.Gson;
import com.streamit.others.constant.SQLConstantFildeType;
import com.streamit.others.constant.SQLConstantOperType;
import com.streamit.others.constant.SQLConstantWhereType;
import com.streamit.others.jdbc.StateValues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface DataSourceService {
    Map<String,Object> insert(String TABLE_NAME, List<StateValues> stateValues) throws SQLException;
    Map<String,Object> update(String TABLE_NAME, List<StateValues> stateValues) throws SQLException;
    Map<String,Object> delete(String TABLE_NAME, List<StateValues> stateValues) throws SQLException;
}

@Slf4j
@Service
class DataSourceServiceImp implements DataSourceService {

    @Autowired
    private DataSource dataSource;


    public Map<String,Object> insert(String TABLE_NAME, List<StateValues> stateValues) throws SQLException {
        Map<String,Object> result = new HashMap<>();
        result.put("status",400);
        String sql = "";
        Connection conn = null;
        PreparedStatement statement = null;

        try{
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            StringBuilder sql1 = new StringBuilder(SQLConstantOperType.INSERT + TABLE_NAME + " ");
            StringBuilder sql2 = new StringBuilder();
            StringBuilder sql3 = new StringBuilder();

            Integer i = 0;
            for(StateValues state : stateValues){
                if(state.getValue() != null) {
                    if ((SQLConstantOperType.EQUALS).equals(state.getCondition())) {

                        String val = this.getValueByType(state.getType(), state.getValue());

                        if (i > 0) {sql2.append(","); sql3.append(",");}

                        sql2.append(state.getKey());
                        sql3.append(val);

                        i++;
                    }
                }
            }

            sql = sql1 + "("+ sql2 + ")" + SQLConstantOperType.VALUES + "("+ sql3 + ")";
            //log.info("{}",sql);

            Boolean chk1 = !sql2.toString().equals("");
            Boolean chk2 = !sql3.toString().equals("");

            if(chk1 && chk2){
                statement = conn.prepareStatement(sql);
                int checkUpdate = statement.executeUpdate();
                //log.info("execute:{}",checkUpdate);
            }

            conn.commit();
            result.put("status",200);
        }catch(SQLException e) {
            //e.printStackTrace();
            conn.rollback();
            result.put("error",e.getMessage());
            log.error(sql);

        }finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }

        return result;
    }

    public Map<String,Object> update(String TABLE_NAME, List<StateValues> stateValues) throws SQLException {
        Map<String,Object> result = new HashMap<>();
        result.put("status",400);
        String sql = "";
        Connection conn = null;
        PreparedStatement statement = null;

        try{
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            StringBuilder sql1 = new StringBuilder(SQLConstantOperType.UPDATE + TABLE_NAME + SQLConstantOperType.SET);
            StringBuilder sql2 = new StringBuilder();
            StringBuilder sql3 = new StringBuilder();

            Integer i = 0;
            Integer j = 0;
            for(StateValues state : stateValues){
                if(state.getValue() != null) {
                    if ((SQLConstantOperType.EQUALS).equals(state.getCondition())) {

                        String val = this.getValueByType(state.getType(), state.getValue());

                        if (i > 0) sql2.append(",");

                        sql2.append(state.getKey() + "=" + val + " ");

                        i++;
                    } else if ((SQLConstantOperType.WHERE).equals(state.getCondition())) {

                        String val = this.getValueByType(state.getType(), state.getValue());

                        if (j == 0) sql3.append(SQLConstantOperType.WHERE + " ");
                        if (j >  0) sql3.append(SQLConstantWhereType.AND + " ");

                        sql3.append(state.getKey() + "=" + val + " ");

                        j++;
                    }
                }
            }

            sql = "" + sql1 + sql2 + sql3;
            //log.info("sql:{}",sql);

            Boolean chk1 = !sql2.toString().equals("");
            Boolean chk2 = !sql3.toString().equals("");

            if(chk1 && chk2){
                statement = conn.prepareStatement(sql);
                int checkUpdate = statement.executeUpdate();
                //log.info("execute:{}",checkUpdate);
            }

            conn.commit();
            result.put("status",200);
        }catch(SQLException e) {
            //e.printStackTrace();
            conn.rollback();
            result.put("error",e.getMessage());
            log.error(sql);

        }finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }

        return result;
    }

    public Map<String,Object> delete(String TABLE_NAME, List<StateValues> stateValues) throws SQLException {
        Map<String,Object> result = new HashMap<>();
        result.put("status",400);
        String sql = "";
        Connection conn = null;
        PreparedStatement statement = null;

        try{
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            StringBuilder sql1 = new StringBuilder(SQLConstantOperType.DELETE + TABLE_NAME + " ");
            StringBuilder sql2 = new StringBuilder();
            //StringBuilder sql3 = new StringBuilder();

            Integer i = 0;
            for(StateValues state : stateValues){
                if(state.getValue() != null) {
                    if ((SQLConstantOperType.WHERE).equals(state.getCondition())) {

                        String val = this.getValueByType(state.getType(), state.getValue());

                        if (i == 0) sql2.append(SQLConstantOperType.WHERE + " ");
                        if (i >  0) sql2.append(SQLConstantWhereType.AND + " ");

                        sql2.append(state.getKey() + "=" + val + " ");

                        i++;
                    }
                }
            }

            sql = "" + sql1 + sql2 ;
            //log.info("sql:{}",sql);

            Boolean chk1 = !sql2.toString().equals("");

            if(chk1){
                statement = conn.prepareStatement(sql);
                int checkUpdate = statement.executeUpdate();
                //log.info("execute:{}",checkUpdate);
            }

            conn.commit();
            result.put("status",200);
        }catch(SQLException e) {
            //e.printStackTrace();
            conn.rollback();
            result.put("error",e.getMessage());
            log.error(sql);

        }finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }

        return result;
    }

    private String getValueByType(String type, Object value){
        String result = null;
        try{
            switch (type) {
                case SQLConstantFildeType.NUMBER:
                    result = "" + value;
                    break;
                default:
                    result = "'" + value + "'";
                    break;
            }
        }catch (Exception e){
            log.error("get error:{}",e.getMessage());
        }

        return result;
    }

}

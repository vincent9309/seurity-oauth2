package com.happylifeplat.security.common;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/19 12:42</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
public class SetTypeHandler extends BaseTypeHandler<Set<String>> {

    public SetTypeHandler() {
        super();
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Set<String> set, JdbcType jdbcType) throws SQLException {
        if (CollectionUtils.isNotEmpty(set)) {
            String result = "";
            for (Object field : set) {
                result += field.toString() + ",";
            }
            preparedStatement.setString(i, result);
        }
    }

    @Override
    public Set<String> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String column = resultSet.getString(columnName);
        return transf(column);
    }

    @Override
    public Set<String> getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String column = resultSet.getString(columnIndex);
        return transf(column);
    }

    @Override
    public Set<String> getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        String column = callableStatement.getString(columnIndex);
        return transf(column);
    }

    private Set<String> transf(String column){
        Set<String> result = new HashSet<String>();
        if (StringUtils.isNotBlank(column)) {
            String[] array = column.split(",");
            result.addAll(Arrays.asList(array));
        }
        return result;
    }
}

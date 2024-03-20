package com.chance.handler;

import com.chance.common.enums.SexEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 通用类型处理器
 * @author: chance
 * @date: 2022/9/3 13:47
 * @since: 1.0
 */
public class SexEnumHandler extends BaseTypeHandler<SexEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SexEnum parameter, JdbcType jdbcType) throws SQLException {
        List<String> codes = Arrays.stream(SexEnum.values()).map(SexEnum::getCode).collect(Collectors.toList());
        String value;
        if (codes.contains(parameter.getCode())) {
           value  = parameter.getCode();
        } else {
            value = SexEnum.DEFAULT.getCode();
        }
        ps.setString(i, value);
    }

    @Override
    public SexEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        //处理返回参数的枚举类型的状态码，根据状态码返回对应的枚举
        return SexEnum.getSexEnumByCode(code);
    }

    @Override
    public SexEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public SexEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}

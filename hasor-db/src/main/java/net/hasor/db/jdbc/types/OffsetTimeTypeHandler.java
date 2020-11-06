/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.db.jdbc.types;
import java.sql.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

/**
 * @version : 2020-10-31
 * @author 赵永春 (zyc@hasor.net)
 */
public class OffsetTimeTypeHandler extends AbstractTypeHandler<OffsetTime> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OffsetTime parameter, JDBCType jdbcType) throws SQLException {
        OffsetDateTime offsetDateTime = parameter.atDate(LocalDate.MIN);
        Timestamp timestamp = Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        ps.setTimestamp(i, timestamp);
    }

    @Override
    public OffsetTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return (timestamp == null) ? null : timestamp.toLocalDateTime().toLocalTime().atOffset(ZoneOffset.UTC);
    }

    @Override
    public OffsetTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        return (timestamp == null) ? null : timestamp.toLocalDateTime().toLocalTime().atOffset(ZoneOffset.UTC);
    }

    @Override
    public OffsetTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        return (timestamp == null) ? null : timestamp.toLocalDateTime().toLocalTime().atOffset(ZoneOffset.UTC);
    }
}
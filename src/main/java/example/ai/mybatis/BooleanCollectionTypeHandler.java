package example.ai.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(Collection.class)
public class BooleanCollectionTypeHandler extends BaseTypeHandler<Collection<Boolean>> {

    @Override
    public void setNonNullParameter(
    		final PreparedStatement preparedStatement,
    		final int parameterIndex,
    		final Collection<Boolean> parameters,
    		final JdbcType jdbcType
	) throws SQLException {
        final Boolean[] array = parameters.toArray(new Boolean[parameters.size()]);
        preparedStatement.setArray(parameterIndex, preparedStatement.getConnection().createArrayOf("boolean", array));
    }

    @Override
    public Collection<Boolean> getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        return convertArray(rs.getArray(columnName));
    }

    @Override
    public Collection<Boolean> getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        return convertArray(rs.getArray(columnIndex));
    }

    @Override
    public Collection<Boolean> getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        return convertArray(cs.getArray(columnIndex));
    }

    private Collection<Boolean> convertArray(final java.sql.Array sqlArray) throws SQLException {
        if (sqlArray == null) {
            return null;
        }
        return Arrays.asList((Boolean[]) sqlArray.getArray());
    }
    
}

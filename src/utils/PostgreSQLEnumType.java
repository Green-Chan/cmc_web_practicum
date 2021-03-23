package utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

@SuppressWarnings("rawtypes")
public class PostgreSQLEnumType extends org.hibernate.type.EnumType {

  private static final long serialVersionUID = 6036035899613326486L;

  public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
      throws HibernateException, SQLException {
    st.setObject(index, value != null ? ((Enum) value).name() : null, Types.OTHER);
  }
}

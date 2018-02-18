package com.liashenko.app.persistance.result_parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @param <T> Field of entity marked with annotation @Column
 * @version 1.0
 * @autor Vitalii Liashenko
 * Class contains methods to parse ResultSet according to corresponding to it entity
 */
public abstract class ResultSetParser<T> {
    private static final Logger classLogger = LogManager.getLogger(ResultSetParser.class);

    /**
     * @param rs           - ResultSet contains results of select-query to the relevant table
     * @param clazz        - entity class corresponds to the relevant table
     * @param localeSuffix - locale suffix for the column corresponds select-query
     * @param <T>          - field of entity marked with annotation @Column
     * @return Entity of type <T> contains results retrieved from the table
     * @throws SQLException
     */
    public static <T> T fillBeanWithResultData(ResultSet rs, Class<T> clazz, String localeSuffix) throws SQLException {
        T obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Column.class)) {
                        field.setAccessible(true);
                        Annotation annotation = field.getAnnotation(Column.class);
                        Column columnAnnot = (Column) annotation;
                        String columnName = columnAnnot.useLocaleSuffix()
                                ? (columnAnnot.name() + localeSuffix)
                                : columnAnnot.name();

                        Field newObjField = obj.getClass().getDeclaredField(field.getName());
                        newObjField.setAccessible(true);
                        setValue(obj, field, rs, columnName);
                    }
                }
            }
        } catch (IllegalAccessException | InstantiationException | NoSuchFieldException ex) {
            classLogger.error(ex.getMessage());
            throw new ResultSetParserException(ex.getMessage());
        }
        return obj;
    }

    //Sets to the field of Entity relevant data from the ResultSet
    private static <T> void setValue(T newInstanceObj, Field field, ResultSet rs, String columnName)
            throws IllegalAccessException, SQLException {

        switch (field.getType().getTypeName()) {
            case "java.lang.String":
                field.set(newInstanceObj, rs.getString(columnName));
                break;
            case "int":
                field.set(newInstanceObj, rs.getInt(columnName));
                break;
            case "java.lang.Integer":
                field.set(newInstanceObj, rs.getInt(columnName));
                break;
            case "long":
                field.set(newInstanceObj, rs.getLong(columnName));
                break;
            case "java.lang.Long":
                field.set(newInstanceObj, rs.getLong(columnName));
                break;
            case "float":
                field.set(newInstanceObj, rs.getFloat(columnName));
                break;
            case "java.lang.Float":
                field.set(newInstanceObj, rs.getFloat(columnName));
                break;
            case "double":
                field.set(newInstanceObj, rs.getBigDecimal(columnName).doubleValue());
                break;
            case "java.lang.Double":
                field.set(newInstanceObj, rs.getBigDecimal(columnName).doubleValue());
                break;
            case "short":
                field.set(newInstanceObj, rs.getShort(columnName));
                break;
            case "java.lang.Short":
                field.set(newInstanceObj, rs.getShort(columnName));
                break;
            case "byte":
                field.set(newInstanceObj, rs.getByte(columnName));
                break;
            case "java.lang.Byte":
                field.set(newInstanceObj, rs.getByte(columnName));
                break;
            case "boolean":
                field.set(newInstanceObj, rs.getBoolean(columnName));
                break;
            case "java.lang.Boolean":
                field.set(newInstanceObj, rs.getBoolean(columnName));
                break;
            case "byte[]":
                field.set(newInstanceObj, rs.getBytes(columnName));
                break;
            case "java.lang.Byte[]":
                field.set(newInstanceObj, rs.getBytes(columnName));
                break;
            case "java.time.LocalDateTime":
                field.set(newInstanceObj, rs.getTimestamp(columnName).toLocalDateTime());
                break;
            case "java.time.LocalDate":
                field.set(newInstanceObj, rs.getTimestamp(columnName).toLocalDateTime().toLocalDate());
                break;
            case "java.time.LocalTime":
                field.set(newInstanceObj, rs.getTimestamp(columnName).toLocalDateTime().toLocalTime());
                break;
            case "java.sql.Timestamp":
                field.set(newInstanceObj, rs.getTimestamp(columnName));
                break;
            case "java.lang.reflect.Array":
                field.set(newInstanceObj, rs.getArray(columnName));
                break;
            case "java.sql.Time":
                field.set(newInstanceObj, rs.getTime(columnName));
                break;
            case "java.math.BigDecimal":
                field.set(newInstanceObj, rs.getBigDecimal(columnName));
                break;
            case "java.sql.Blob":
                field.set(newInstanceObj, rs.getBlob(columnName));
                break;
            case "javax.sql.rowset.serial.SerialClob":
                field.set(newInstanceObj, rs.getClob(columnName));
                break;
            case "java.sql.Date":
                field.set(newInstanceObj, rs.getDate(columnName));
                break;
            case "javax.sql.rowset.serial.SerialRef":
                field.set(newInstanceObj, rs.getRef(columnName));
                break;
            default:
                field.set(newInstanceObj, rs.getObject(columnName));
                break;
        }
    }
}

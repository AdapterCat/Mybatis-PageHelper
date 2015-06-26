package com.github.orderbyhelper.sqlsource;

import com.github.orderbyhelper.OrderByHelper;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * 描述信息
 *
 * @author liuzh
 * @since 2015-06-26
 */
public class OrderByStaticSqlSource implements SqlSource, OrderBy {
    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;

    public OrderByStaticSqlSource(StaticSqlSource sqlSource) {
        MetaObject metaObject = SystemMetaObject.forObject(sqlSource);
        this.sql = (String) metaObject.getValue("sql");
        this.parameterMappings = (List<ParameterMapping>) metaObject.getValue("parameterMappings");
        this.configuration = (Configuration) metaObject.getValue("configuration");
    }

    public BoundSql getBoundSql(Object parameterObject) {
        String orderBy = OrderByHelper.getOrderBy();
        String tempSql = sql;
        if(orderBy!=null){
            tempSql += " order by " + orderBy;
        }
        return new BoundSql(configuration, tempSql, parameterMappings, parameterObject);
    }
}

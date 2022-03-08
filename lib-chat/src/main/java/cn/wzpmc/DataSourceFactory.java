package cn.wzpmc;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;

/**
 * @author wzp
 */
public class DataSourceFactory extends PooledDataSourceFactory {
    public DataSourceFactory(){
        this.dataSource = new DruidDataSource();
    }
}

package com.lifeonwalden.forestbatis.spring;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.mapper.SpringMapper;
import com.lifeonwalden.forestbatis.util.SingletonMapperFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.util.Optional;

public class MapperFactory implements InitializingBean {
    private DataSource dataSource;
    private Config config;
    private SingletonMapperFactory mapperFactory;

    public <T extends SpringMapper> T getMapper(Class<T> clazz) {
        Optional<T> _mapper = mapperFactory.get(clazz);
        if (_mapper.isPresent()) {
            return _mapper.get();
        } else {
            try {
                T mapper = clazz.newInstance();
                mapper.setConfig(this.config);
                mapper.setDataSource(this.dataSource);
                return mapperFactory.getOrCreate(clazz, mapper);
            } catch (InstantiationException e) {
                throw new RuntimeException("Failed to initial mapper.", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to initial mapper.", e);
            }
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mapperFactory = new SingletonMapperFactory();
    }
}

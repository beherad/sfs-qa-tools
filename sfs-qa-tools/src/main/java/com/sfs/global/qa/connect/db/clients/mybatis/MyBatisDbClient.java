package com.sfs.global.qa.connect.db.clients.mybatis;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

@Slf4j
public class MyBatisDbClient {

    private SqlSessionFactory sqlSessionFactory;
    @Getter
    private SqlSession sqlSession;

    public MyBatisDbClient connect(final String configXmlFilepath, final MyBatisDbConfig myBatisDbConfig) {
        try {
            this.sqlSessionFactory = new SqlSessionFactoryBuilder()
                    .build(Resources.getResourceAsReader(configXmlFilepath), myBatisDbConfig.getProperties());
        } catch (IOException e) {
            log.error("Database config XML file is NOT found [{}]", configXmlFilepath);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return this;
    }

    public MyBatisDbClient newSession() {
        this.sqlSession = sqlSessionFactory.openSession();
        return this;
    }

}

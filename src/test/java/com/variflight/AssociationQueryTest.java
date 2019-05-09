package com.variflight;

import com.variflight.entity.TUser;
import com.variflight.mapper.TUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author XieYufeng
 * @ClassName: AssociationQueryTest
 * @description:
 * @date 2019/5/9 14:28
 */
public class AssociationQueryTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {

        String resource = "mybatis-config.xml";

        InputStream inputStream = Resources.getResourceAsStream(resource);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        inputStream.close();
    }

    // 1对1两者关联方式
    @Test
    public void testOneToOne() {
        // 获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 获取对应对mapper
        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
        // 执行查询语句并返回结果
//        List<TUser> list1 = mapper.selectUserPosition1();
//        list1.forEach((obj) -> {
//            System.out.println(obj);
//        });

        List<TUser> list2 = mapper.selectUserPosition2();
        for (TUser t : list2) {
            System.out.println(t);
        }
//        list2.forEach((obj) -> {
//            System.out.println(obj);
//        });
    }

    // 1对多的两种关联方式
    @Test
    public void testOneToMany() {
        // 获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 获取对应的mapper
        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);

        List<TUser> selectUserJobs1 = mapper.selectUserJobs1();

        selectUserJobs1.forEach((obj) -> {
            System.out.println(obj);
        });

        System.out.println("-----------------");

        List<TUser> selectUserJobs2 = mapper.selectUserJobs2();

        selectUserJobs2.forEach((obj) -> {
             System.out.println(obj.getEmail());
        });
    }

    @Test
    public void testDiscriminator() {
        // 2.获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3.获取对应mapper
        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		// 4.执行查询语句并返回结果
		// ----------------------
        List<TUser> list = mapper.selectUserHealthReport();
        for (TUser tUser : list) {
            System.out.println(tUser);
        }
    }

    @Test
    public void testManyToMany() {
        // 2.获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3.获取对应mapper
        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);

        // 4.执行查询语句并返回结果
        // ----------------------
        List<TUser> list = mapper.selectUserRole();
        for (TUser tUser : list) {
            System.out.println(tUser.getRoles().size());
        }
    }
}

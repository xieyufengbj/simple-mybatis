package com.variflight;

import com.variflight.entity.TPosition;
import com.variflight.entity.TUser;
import com.variflight.mapper.TUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author XieYufeng
 * @ClassName: MybatisDemo
 * @description:
 * @date 2019/5/8 16:44
 */
public class MybatisDemo {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {

        String resource = "mybatis-config.xml";

        InputStream inputStream = Resources.getResourceAsStream(resource);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        inputStream.close();
    }

    // 验证foreach用于in查询
    @Test
    public void testForeach4In() {
        // 获取sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);

        String[] names = new String[]{"lison", "james"};

        List<TUser> users = mapper.selectForeach4In(names);

        System.out.println(users.size());
    }

    //验证foreach用于动态sql拼接
    @Test
    public void testForeach4Insert() {
        // 获取
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);

        TUser user1 = new TUser();
        user1.setUserName("king");
        user1.setRealName("李小京");
        user1.setEmail("li@qq.com");
        user1.setMobile("18754548787");
        user1.setNote("king's note");
        user1.setSex((byte)1);
        TUser user2 = new TUser();
        user2.setUserName("deer");
        user2.setRealName("陈大林");
        user2.setEmail("chen@qq.com");
        user2.setMobile("18723138787");
        user2.setNote("deer's note");
        user2.setSex((byte)1);

        int i = mapper.insertForeach4Batch(Arrays.asList(user1,user2));
        System.out.println(i);
    }

    // 批量更新
    @Test
    public void testBatchExcutor() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
        TUser user = new TUser();
        user.setUserName("mark");
        user.setRealName("毛毛");
        user.setEmail("xxoo@163.com");
        user.setMobile("18695988747");
        user.setNote("mark's note");
        user.setSex((byte) 1);
        TPosition positon1 = new TPosition();
        positon1.setId(1);
        user.setPosition(positon1);
        System.out.println(mapper.insertSelective(user));

        TUser user1 = new TUser();
//		user1.setId(3);
        user1.setUserName("cindy");
        user1.setRealName("王美丽");
        user1.setEmail("xxoo@163.com");
        user1.setMobile("18695988747");
        user1.setNote("cindy's note");
        user1.setSex((byte) 2);
        user.setPosition(positon1);
        System.out.println(mapper.updateIfAndSetOper(user1));

        sqlSession.commit();
        System.out.println("------------------");
    }
}

package com.variflight;

import com.variflight.entity.TPosition;
import com.variflight.entity.TUser;
import com.variflight.mapper.TUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.*;

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

    @Test
    public void testReflection() {

        // 反射工具类初始化
        ObjectFactory objectFactory = new DefaultObjectFactory();
        ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
        ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        TUser user = objectFactory.create(TUser.class);
        MetaObject metaObject = MetaObject.forObject(user, objectFactory, objectWrapperFactory, reflectorFactory);

        // 使用Reflector读取类元信息
        Reflector reflector = reflectorFactory.findForClass(TUser.class);
        Constructor<?> constructor = reflector.getDefaultConstructor();
        System.out.println("constructor ## " + constructor);
        String[] setablePropertyNames =  reflector.getSetablePropertyNames();
        System.out.println("setablePropertyNames ## " + Arrays.toString(setablePropertyNames));
        String[] getablePropertyNames = reflector.getGetablePropertyNames();
        System.out.println("getablePropertyNames ## " + Arrays.toString(getablePropertyNames));

        // 使用ObjectWrapper读取对象信息，并对对象属性进行赋值操作
        TUser userTemp = new TUser();
        ObjectWrapper objectWrapper = new BeanWrapper(metaObject, userTemp);
        String[] getterNames = objectWrapper.getGetterNames();
        System.out.println("getterNames ## " + Arrays.toString(getterNames));
        String[] setterNames = objectWrapper.getSetterNames();
        System.out.println("setterNames ## " + Arrays.toString(setterNames));

        PropertyTokenizer prop = new PropertyTokenizer("userName");
        objectWrapper.set(prop, "风光无限");
        System.out.println(userTemp.toString());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        /**
         * 模拟数据库行数据转化为对象
         */
        // 1、模拟从数据库读取数据
        Map<String, Object> dbResult = new HashMap<>();
        dbResult.put("real_name", "峰哥");
        dbResult.put("user_name", "风光无限");
        dbResult.put("mobile", "10086");
        TPosition position = new TPosition();
        position.setId(1);
        dbResult.put("position_id", position);
        // 2、模拟映射关系
        Map<String, Object> mapper = new HashMap<>();
        mapper.put("realName", "real_name");
        mapper.put("userName", "user_name");
        mapper.put("mobile", "mobile");
        mapper.put("position", "position_id");

        // 使用反射工具类将行数据转换为pojo
        BeanWrapper beanWrapper = (BeanWrapper) metaObject.getObjectWrapper();
        Set<Map.Entry<String, Object>> entrySet = mapper.entrySet();
        for (Map.Entry<String, Object> e : entrySet) {
            Object propValue = e.getValue();
            String propKey   = e.getKey();
            PropertyTokenizer p = new PropertyTokenizer(propKey);
            beanWrapper.set(p, dbResult.get(propValue));
        }
        System.out.println("模拟mybatis获取封装数据：" + metaObject.getOriginalObject());
    }
}

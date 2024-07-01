package com.demo.service;

import com.demo.entity.User;
import com.demo.utils.ApplicationContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Autowired
    private IUserService iUserService;

    /**
     *
     * Transactional注解参数：
     *
     * propagation事务传播行为：建议显式写上。
     * rollbackFor需要回滚的错误：建议显式写上。可以自己写上运行时异常，避开受检查异常的处理；Error异常程序一般是无法处理的。
     * isolation隔离级别：一般按照数据库默认。
     * timeout超时时间：一般用在可能存在耗时较长的操作。通常默认是无限制
     * readOnly是否只读：默认false，为true不允许写操作
     *
     * */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class,Error.class}, isolation = Isolation.DEFAULT, timeout = -1, readOnly = false)
    public void saveOne(){
        User user = new User();
        user.setAge(27);
        user.setName("cc1");
        user.setUserLevel("A1");
        iUserService.save(user);
        TransactionService transactionService = (TransactionService) ApplicationContextUtil.getBean("transactionService");
        transactionService.saveOne2();
        //int temp = 1/0;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = {RuntimeException.class,Error.class})
    public void saveOne2(){
        User user = new User();
        user.setAge(27);
        user.setName("cc2");
        user.setUserLevel("A1");
        iUserService.save(user);
        int temp = 1/0;
    }
}

package com.lb.bs.client.manager;

import com.lb.bs.client.annotation.LBItem;
import com.lb.bs.client.model.LBItemBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 19:57
 * description:
 */
@Aspect
public class LBAspect {

    @Pointcut(value = "execution(public * *(..))")

    public void anyPublicMethod() {

    }

    public LBAspect() {
        System.out.println("init aspect");
    }

    @Around(value = "anyPublicMethod() && @annotation(lbItem)")
    public Object aroundItem(ProceedingJoinPoint pjp, LBItem lbItem) {
        String key = lbItem.key();
        LBItemBean itemBean = LBStoreCenter.getInstance().getConfigItemMap().get(key);
        if (itemBean != null) {
            Object value = itemBean.getValue();
            return value;
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}

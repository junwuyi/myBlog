package ssm.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import ssm.controller.annotation.BussinessLog;
import ssm.entity.SysLog;
import ssm.entity.User;
import ssm.enums.PlatformEnum;
import ssm.service.SysLogService;
import ssm.util.AspectUtil;
import ssm.util.MyUtils;
import ssm.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author chen
 * @create 2019-08-08 11:19
 */
@Component
@Aspect
@Slf4j
public class LogAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysLogService sysLogService;

    private Date visitTime; //开始时间

    @Pointcut(value = "@annotation(ssm.controller.annotation.BussinessLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object writeLog(ProceedingJoinPoint point) throws Throwable {

        //先执行业务
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    /**
     * 前置通知
     * 主要是获取开始时间，执行的类是哪一个，执行的是哪一个方法
     *
     * @param jp
     */
    @Before("execution(* ssm.controller.admin.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        visitTime = new Date(); //当前时间就是开始时间
    }


    /**
     * 后置通知
     * 主要是获取开始时间，执行的类是哪一个，执行的是哪一个方法
     */
   /* @After("execution(* ssm.controller.admin.*.*(..))")*/
    public void handle(JoinPoint jp) throws NoSuchMethodException {

        Method currentMethod = AspectUtil.INSTANCE.getMethod(jp);
        //获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        boolean save = annotation.save();
        PlatformEnum platform = annotation.platform();
        String bussinessName = AspectUtil.INSTANCE.parseParams(jp.getArgs(), annotation.value());

        long time = new Date().getTime() - visitTime.getTime();

        //获取访问的ip
        String ip = MyUtils.getIpAddr(request);

        //获取当前操作的用户
        /*SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String userName = user.getUsername();*/

        User user = (User) request.getSession().getAttribute("user");
        String userName = user.getUserNickname();

        //将日志信息封装到SysLog对象
        SysLog sysLog = new SysLog();
        sysLog.setExecutionTime(time);//执行时长
        sysLog.setIp(ip);
        sysLog.setContent(bussinessName);
        sysLog.setUrl(RequestUtil.getRequestUrl());
        sysLog.setUserName(userName);
        sysLog.setVisitTime(visitTime);

        //调用service完成记录日志到数据库
        sysLogService.save(sysLog);
    }
}

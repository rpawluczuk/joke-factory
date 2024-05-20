package springapp.jokefactory.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfilingAspect {

    @Around("execution(* springapp.jokefactory.topic.view.TopicViewService.getTopicView(..))")
    public Object profileGetTopicView(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object output = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("getTopicView execution time: " + elapsedTime + " milliseconds.");
        return output;
    }

    @Around("execution(* springapp.jokefactory.topic.TopicFacade.getConnectedTopicsPage(..))")
    public Object profileGetConnectedTopicsPage(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object output = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("getConnectedTopicsPage execution time: " + elapsedTime + " milliseconds.");
        return output;
    }

    @Around("execution(* springapp.jokefactory.topic.view.TopicViewMapper.mapViewToDto(..))")
    public Object profileMapViewToDto(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object output = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("mapViewToDto execution time: " + elapsedTime + " milliseconds.");
        return output;
    }
}

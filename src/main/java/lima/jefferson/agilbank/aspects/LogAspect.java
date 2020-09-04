package lima.jefferson.agilbank.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* mapLine(..))")
    private void mapLinePointCut() {}

    @Pointcut("execution(* process(..))")
    private void processPointCut() {}

    @Pointcut("execution(* fromStringList(..))")
    private void fromStringList() {}

    @Before("mapLinePointCut()")
    private void beforeMapLine(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.debug(String.format("Mapping line %s: %s", args[1], args[0]));
    }

    @Before("processPointCut()")
    private void beforeProcess(JoinPoint joinPoint) {
        log.debug("Processing record: " + joinPoint.getArgs()[0].toString());
    }

    @Before("fromStringList()")
    private void beforeFromStringList(JoinPoint joinPoint) {
        log.debug("Converting record attributes: " + joinPoint.getArgs()[0].toString());
    }

    @AfterReturning(pointcut = "processPointCut()", returning = "entity")
    private void afterFromStringList(JoinPoint joinPoint, Object entity) {
        log.debug("Converted entity: " + entity.toString());
    }

    @AfterThrowing(pointcut = "execution(* lima.jefferson.agilbank.*.*.*(..))", throwing = "ex")
    public void afterException(Exception ex) {
        log.warn(ex.getMessage());
    }
}

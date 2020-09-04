package lima.jefferson.agilbank.aspects;

import lima.jefferson.agilbank.parsing.RecordLineMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(RecordLineMapper.class);

    @Pointcut("execution(* mapLine(..))")
    private void mapLinePointCut() {}

    @Pointcut("execution(* process(..))")
    private void processPointCut() {}

    @Pointcut("execution(* fromStringList(..))")
    private void fromStringList() {}

    @Before("mapLinePointCut()")
    private void beforeMapLine(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.debug(String.format("Mapping line %s: %s", args[1], args[0]));
    }

    @Before("mapLinePointCut()")
    private void afterProcess(JoinPoint joinPoint) {
        logger.debug("Processing record: " + joinPoint.getArgs()[0].toString());
    }

    @Before("processPointCut()")
    private void beforeFromStringList(JoinPoint joinPoint) {
        logger.debug("Converting record attributes: " + joinPoint.getArgs()[0].toString());
    }

    @AfterReturning(pointcut = "processPointCut()", returning = "entity")
    private void afterFromStringList(JoinPoint joinPoint, Object entity) {
        logger.debug("Converted entity: " + entity.toString());
    }
}

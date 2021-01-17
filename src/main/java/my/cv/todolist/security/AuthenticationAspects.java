package my.cv.todolist.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.stream.Stream;

@Component
@Aspect
public class AuthenticationAspects {
    private final AuthenticationUtils authenticationUtils;

    @Autowired
    public AuthenticationAspects(AuthenticationUtils authenticationUtils) {
        this.authenticationUtils = authenticationUtils;
    }

    @Pointcut("execution(@my.cv.todolist.annatations.Authenticational * *(..))")
    public void annotatedByAuthentication() {
    }

    @Around("annotatedByAuthentication()")
    public Object around(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = findRequest(joinPoint);
        if (request == null) {
            throw new RuntimeException("Method annotated by @Authenticational must have HttpServletRequest argument");
        }
        Object target = joinPoint.getTarget();
        return authenticationUtils.repformAfterAuthentication(request, (userId) -> {
            try {
                Field field = target.getClass().getDeclaredField("userId");
                field.setAccessible(true);
                field.set(target, userId);
                ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) joinPoint.proceed();
                field.set(target, null);
                field.setAccessible(false);
                return responseEntity;
            } catch (Throwable e) {
                throw new RuntimeException("Class with method annotated by @Authenticational must have userId field", e);
            }
        });
    }

    private HttpServletRequest findRequest(ProceedingJoinPoint joinPoint) {
        return (HttpServletRequest) Stream.of(joinPoint.getArgs()).filter(arg -> HttpServletRequest.class.isAssignableFrom(arg.getClass())).findFirst().orElse(null);
    }
}

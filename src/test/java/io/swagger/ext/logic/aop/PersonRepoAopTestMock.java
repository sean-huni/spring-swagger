package io.swagger.ext.logic.aop;


import io.swagger.persistence.entity.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.HashSet;
import java.util.Set;


@TestConfiguration
@Profile("dev-2")
@Aspect
public class PersonRepoAopTestMock {
    private static final Logger LOGGER = LogManager.getLogger(PersonRepoAopTestMock.class);
    private static boolean isSecondExecution = false;

    @Pointcut("target(io.swagger.persistence.repo.PersonRepo)")
    public void personRepoTarget() {
    }

    @Pointcut("execution(* *.save(..))")
    protected void saveExecution() {
    }

    @Before("personRepoTarget() && saveExecution()")
    public void before(JoinPoint joinPoint) {
        if (isSecondExecution) {
            LOGGER.info("Throwing Exception PersonRepo Save JoinPoint: {}", joinPoint);
            throw new ConstraintViolationException("Testing the Mocked PersonRepo", violationSet());
        }
        LOGGER.info("Initialising PersonRepo Save JoinPoint: {}", joinPoint);
        isSecondExecution = true;
    }

    private Set<ConstraintViolation<Person>> violationSet() {
        Set<ConstraintViolation<Person>> constraintViolations = new HashSet<>();
        ConstraintViolation<Person> violation = new ConstraintViolation<>() {
            @Override
            public String getMessage() {
                return "length must be between 1 and 255";
            }

            @Override
            public String getMessageTemplate() {
                return "{org.hibernate.validator.constraints.Length.message}";
            }

            @Override
            public Person getRootBean() {
                return new Person();
            }

            @Override
            public Class<Person> getRootBeanClass() {
                return Person.class;
            }

            @Override
            public Person getLeafBean() {
                return new Person();
            }

            @Override
            public Person[] getExecutableParameters() {
                return null;
            }

            @Override
            public Person getExecutableReturnValue() {
                return null;
            }

            @Override
            public Path getPropertyPath() {
                return PathImpl.createPathFromString("fullName");
            }

            @Override
            public Object getInvalidValue() {
                return null;
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                return null;
            }

            @Override
            public <U> U unwrap(Class<U> type) {
                return null;
            }
        };
        constraintViolations.add(violation);
        return constraintViolations;
    }
}

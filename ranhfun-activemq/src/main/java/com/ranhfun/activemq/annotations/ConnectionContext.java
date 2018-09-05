package com.ranhfun.activemq.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.apache.tapestry5.ioc.annotations.UseWith;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.*;

@Target(
        {PARAMETER, FIELD})
@Retention(RUNTIME)
@Documented
@UseWith({COMPONENT, MIXIN, PAGE, SERVICE})
public @interface ConnectionContext
{
    String value();
}

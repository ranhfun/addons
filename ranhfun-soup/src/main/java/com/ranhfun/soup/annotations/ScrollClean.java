package com.ranhfun.soup.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.COMPONENT;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.MIXIN;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.PAGE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.apache.tapestry5.ioc.annotations.UseWith;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@UseWith({COMPONENT,MIXIN,PAGE})
public @interface ScrollClean {

}

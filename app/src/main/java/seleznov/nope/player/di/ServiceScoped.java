package seleznov.nope.player.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by User on 28.05.2018.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceScoped {
}

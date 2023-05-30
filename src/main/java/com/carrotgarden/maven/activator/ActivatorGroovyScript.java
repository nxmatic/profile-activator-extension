package com.carrotgarden.maven.activator;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Activate project profile based on Groovy property expression.
 */
@Named(Activator.GROOVYSCRIPT)
@Singleton
public class ActivatorGroovyScript extends ActivatorBase {

	@Override
	public String activatorName() {
		return Activator.GROOVYSCRIPT;
	}

	/**
	 * @see org.codehaus.groovy.jsr223.GroovyScriptEngineFactory
	 */
	@Override
	public String activatorEngine() {
		return "groovy";
	}

}

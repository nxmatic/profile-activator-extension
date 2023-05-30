package com.carrotgarden.maven.activator;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Activate project profile based on JavaScript property expression.
 */

@Named(Activator.JAVASCRIPT)
@Singleton
public class ActivatorJavaScript extends ActivatorBase {

	@Override
	public String activatorName() {
		return Activator.JAVASCRIPT;
	}

	/**
	 * @see com.sun.phobos.script.javascript.RhinoScriptEngineFactory
	 */
	@Override
	public String activatorEngine() {
		return "rhino";
	}

}

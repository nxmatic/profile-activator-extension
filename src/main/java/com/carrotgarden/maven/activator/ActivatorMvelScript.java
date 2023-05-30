package com.carrotgarden.maven.activator;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Activate project profile based on MVFLEX property expression.
 */

@Named(Activator.MVELSCRIPT)
@Singleton
public class ActivatorMvelScript extends ActivatorBase {

	@Override
	public String activatorName() {
		return Activator.MVELSCRIPT;
	}

	/**
	 * @see org.mvel2.jsr223.MvelScriptEngineFactory
	 */
	@Override
	public String activatorEngine() {
		return "mvel";
	}

}

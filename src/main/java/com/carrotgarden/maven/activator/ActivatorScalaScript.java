package com.carrotgarden.maven.activator;

import javax.inject.Named;

import com.google.inject.Singleton;

/**
 * Activate project profile based on Scala property expression.
 */
@Named(Activator.SCALASCRIPT)
@Singleton
public class ActivatorScalaScript extends ActivatorBase {

	@Override
	public String activatorName() {
		return Activator.SCALASCRIPT;
	}

	/**
	 * @see scala.tools.nsc.interpreter.Scripted
	 */
	@Override
	public String activatorEngine() {
		return "scala";
	}

}

package com.carrotgarden.maven.activator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.profile.DefaultProfileSelector;
import org.apache.maven.model.profile.ProfileActivationContext;
import org.apache.maven.model.profile.activation.ProfileActivator;
import org.codehaus.plexus.logging.Logger;

/**
 * Profile selector which combines profiles activated by custom and default
 * activators. Overrides "default" provider.
 */

@Singleton
@Named("default")
public class CustomProfileSelector extends DefaultProfileSelector {

	@Inject
	protected Logger logger;

	/**
	 * Collect only custom activators. Default activators are in super.
	 */
	// Note: keep field name different from super to ensure proper injection.

	@Inject
	protected List<ProfileActivator> activatorList = new ArrayList<>();

	/**
	 * Profiles activated by both custom and default activators.
	 */
	@Override
	public List<Profile> getActiveProfiles( //
			Collection<Profile> profiles, //
			ProfileActivationContext context, //
			ModelProblemCollector problems //
	) {
		List<Profile> activeProfiles = evaluateActivations(profiles, SupportFunction.mutableContext(context), problems);
		if (logger.isDebugEnabled() && activeProfiles.size() > 0) {
			logger.info("Activator SELECT: " + Arrays.toString(activeProfiles.toArray()));
		}
		return activeProfiles;
	}

	protected List<Profile> evaluateActivations( //
			Collection<Profile> profiles, //
			ProfileActivationContext context, //
			ModelProblemCollector problems) {
		List<Profile> activeProfiles = super.getActiveProfiles(profiles, context, problems);
		profiles.removeAll(activeProfiles);
		if (!activeProfiles.isEmpty()) {
			// update context
			activeProfiles.stream().forEach(it -> {
				context.getInactiveProfileIds().remove(it.getId());
				context.getActiveProfileIds().add(it.getId());
				it.getProperties().entrySet().forEach( //
						e -> context.getUserProperties().put( //
								e.getKey().toString(), //
								e.getValue().toString()));
			});
			// should re-evaluate the others
			activeProfiles.addAll(evaluateActivations(profiles, context, problems));
		}
		return activeProfiles;
	}

	/**
	 * Applies only to custom activators.
	 * 
	 * Note: "AND" for custom activators. See super.
	 */
	protected boolean hasActive( //
			Profile profile, //
			ProfileActivationContext context, //
			ModelProblemCollector problems //
	) {
		int countActive = 0;
		int countPresent = 0;
		for (ProfileActivator activator : activatorList) {
			if (activator.presentInConfig(profile, context, problems)) {
				countPresent++;
				if (activator.isActive(profile, context, problems)) {
					countActive++;
				}
			}
		}
		return (countActive > 0) && (countActive == countPresent);
	}

}

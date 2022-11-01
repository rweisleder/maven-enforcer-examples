package de.rweisleder.example.enforcer;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.model.Repository;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

import javax.annotation.Nonnull;

public class RequireRepositoryRule implements EnforcerRule {

    public void execute(@Nonnull EnforcerRuleHelper helper) throws EnforcerRuleException {
        MavenProject project = getProject(helper);

        for (Repository repository : project.getRepositories()) {
            if (repository.getSnapshots().isEnabled()) {
                return;
            }
        }

        throw new EnforcerRuleException("No snapshot repository configured");
    }

    private MavenProject getProject(EnforcerRuleHelper helper) throws EnforcerRuleException {
        try {
            return (MavenProject) helper.evaluate("${project}");
        } catch (ExpressionEvaluationException e) {
            throw new EnforcerRuleException("Unable to read project", e);
        }
    }

    public boolean isCacheable() {
        return false;
    }

    public boolean isResultValid(@Nonnull EnforcerRule cachedRule) {
        return false;
    }

    public String getCacheId() {
        return null;
    }
}

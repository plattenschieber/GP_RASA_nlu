package de.thkoeln.archilab;

import com.atlassian.bamboo.specs.api.BambooSpec;
import com.atlassian.bamboo.specs.api.builders.BambooKey;
import com.atlassian.bamboo.specs.api.builders.BambooOid;
import com.atlassian.bamboo.specs.api.builders.permission.PermissionType;
import com.atlassian.bamboo.specs.api.builders.permission.Permissions;
import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.PlanIdentifier;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.artifact.Artifact;
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup;
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement;
import com.atlassian.bamboo.specs.api.builders.plan.configuration.ConcurrentBuilds;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.builders.task.CheckoutItem;
import com.atlassian.bamboo.specs.builders.task.DockerBuildImageTask;
import com.atlassian.bamboo.specs.builders.task.DockerPushImageTask;
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask;
import com.atlassian.bamboo.specs.builders.trigger.BitbucketServerTrigger;
import com.atlassian.bamboo.specs.util.BambooServer;

@BambooSpec
public class PlanSpec {

    public Plan plan() {
        final Plan plan = new Plan(new Project()
            .oid(new BambooOid("ky5ricqu8qv5"))
            .key(new BambooKey("CHAT"))
            .name("Chatbot"),
            "nlu",
            new BambooKey("NLU"))
            .oid(new BambooOid("kxw2ardmf01u"))
            .description("Build the NLU app")
            .pluginConfigurations(new ConcurrentBuilds()
                .useSystemWideDefault(false))
            .stages(new Stage("Default Stage")
                .jobs(new Job("Defaultttt Job",
                    new BambooKey("JOB1"))
                    .artifacts(new Artifact()
                        .name("docker-compose-file")
                        .copyPattern("docker-compose.yaml")
                        .location("./docker")
                        .shared(true)
                        .required(true))
                    .tasks(new VcsCheckoutTask()
                            .description("Checkout Default Repository")
                            .checkoutItems(new CheckoutItem().defaultRepository()),
                        new DockerBuildImageTask()
                            .description("Build the Docker image")
                            .imageName("docker.nexus.gpchatbot.archi-lab.io/chatbot/nlu:latest")
                            .useCache(true)
                            .dockerfileInWorkingDir(),
                        new ScriptTask()
                            .description("Create commit hash variable file")
                            .inlineBody("echo \"hello world\""),
                        new DockerPushImageTask()
                            .customRegistryImage("docker.nexus.gpchatbot.archi-lab.io/chatbot/nlu:latest")
                            .defaultAuthentication())))
            .linkedRepositories("chatbot-nlu (master)")

            .triggers(new BitbucketServerTrigger())
            .planBranchManagement(new PlanBranchManagement()
                .delete(new BranchCleanup())
                .notificationForCommitters());
        return plan;
    }

    public PlanPermissions planPermission() {
        final PlanPermissions planPermission = new PlanPermissions(new PlanIdentifier("CHAT", "NLU"))
            .permissions(new Permissions()
                .userPermissions("jlengelsen", PermissionType.EDIT, PermissionType.VIEW, PermissionType.ADMIN, PermissionType.CLONE, PermissionType.BUILD)
                .loggedInUserPermissions(PermissionType.VIEW)
                .anonymousUserPermissionView());
        return planPermission;
    }

    public static void main(String... argv) {
        //By default credentials are read from the '.credentials' file.
        BambooServer bambooServer = new BambooServer("https://bamboo.gpchatbot.archi-lab.io");
        final PlanSpec planSpec = new PlanSpec();

        final Plan plan = planSpec.plan();
        bambooServer.publish(plan);

        final PlanPermissions planPermission = planSpec.planPermission();
        bambooServer.publish(planPermission);
    }
}

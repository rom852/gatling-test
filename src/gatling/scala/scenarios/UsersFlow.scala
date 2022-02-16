package scenarios

import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import steps.{AdminSteps, ReviewSteps, UserSteps}

object UsersFlow extends BaseScenario {


  val addingDefectsAndCommentsToReview: ScenarioBuilder = scenario("UsersFlowSimulation")
    .exec(
        AdminSteps.getAdminLoginTicket,
        UserSteps.createUser(author),
        UserSteps.createUser(reviewer),
        ReviewSteps.createReview(review, author),
        ReviewSteps.addUserToReview(author, author),
        ReviewSteps.addUserToReview(reviewer, reviewer),
        ReviewSteps.uploadBigFile(author),
        ReviewSteps.addFileComment(reviewer),
        ReviewSteps.acceptComment(author),
        ReviewSteps.addFileDefect(reviewer),
        ReviewSteps.markDefectFixed(reviewer),
    )
}

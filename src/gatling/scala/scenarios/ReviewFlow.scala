package scenarios

import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import steps.{AdminSteps, ReviewSteps, UserSteps}

object ReviewFlow extends BaseScenario {

  val moveReviewToCompletePhase: ScenarioBuilder = scenario("ReviewFlowSimulation")
    .exec(
      AdminSteps.getAdminLoginTicket,
      UserSteps.createUser(author),
      UserSteps.createUser(reviewer),
      ReviewSteps.createReview(review, author),
      ReviewSteps.addUserToReview(author, author),
      ReviewSteps.addUserToReview(reviewer, reviewer),
      ReviewSteps.moveReviewToAnnotatePhase(review, author),
      ReviewSteps.finishReviewPhase(review, author),
      ReviewSteps.finishReviewPhase(review, reviewer)
    )


    def loginAndAddDbUserToReview(userFeeder: String, userAlias: String, role: String): ScenarioBuilder = scenario("ReviewDbFlowSimulation")
      .exec(
        AdminSteps.getAdminLoginTicket,
        UserSteps.loginDbUser(userFeeder, userAlias),
        ReviewSteps.storeReviewIdFromFeeder(),
        ReviewSteps.addUserToReviewByAdmin(userAlias, role)
      )
}

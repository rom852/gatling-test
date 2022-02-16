package scenarios

import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import steps.UserSteps

object UsersLoginFlow extends BaseScenario {



  def loginDbUser(user: String, userAlias: String): ScenarioBuilder = scenario("UsersDbFlowSimulation")
    .exec(
        UserSteps.loginDbUser(user, userAlias)
    )
}

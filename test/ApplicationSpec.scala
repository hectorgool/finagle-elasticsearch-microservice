import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beSome.which (status(_) == NOT_FOUND)
    }

    "render the index page" in new WithApplication{
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "application/json")
      contentAsJson(home) mustEqual ( Json.obj("data" -> "Your API is ready.") )

    }

    /*
    val action: EssentialAction = Action { request =>
      val value = (request.body.asJson.get \ "field").as[String]
      Ok(value)
    }

    val request = FakeRequest(POST, "/").withJsonBody(Json.parse("""{ "field": "value" }"""))

    val result = call(action, request)

    status(result) mustEqual OK
    contentAsString(result) mustEqual "value"
    */

    /*
    "render the read page" in new WithApplication{
      val home = route(FakeRequest(GET, "/api/read")).get

      status(home) must equalTo(OK)
      //contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain ("Your API is ready.")
    }
    */

  }

}

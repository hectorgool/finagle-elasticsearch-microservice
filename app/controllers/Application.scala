package controllers


import play.api.mvc._
import play.api.libs.json._


class Application extends Controller{

  	def index = Action {
    	Ok( Json.obj("data" -> "Your API is ready.") )
  	}

}

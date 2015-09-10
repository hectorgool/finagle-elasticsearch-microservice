package controllers


import com.twitter.bijection.twitter_util._
import com.twitter.io.Charsets
import lib._
import org.jboss.netty.util.CharsetUtil
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{Json,JsValue}
import play.api.libs.json.Json.{toJson}
import play.api.mvc._
import play.api.Play.current
import play.api._
import scala.concurrent.{Future}
import scala.util.{Failure, Success}


class Application extends Controller with UtilBijections {


	val elasticsearchIndex = current.configuration.getString("elasticsearch.index").get
	val indexType = current.configuration.getString("elasticsearch.indexType").get

  	def index = Action {
    	Ok(views.html.index("Your API is ready."))
  	}

	def searchDoc = Action.async(parse.json) { request =>
 
		val json = request.body
		val futureScala = twitter2ScalaFuture.apply( FinagleClient.documentSearch( elasticsearchIndex, indexType, json ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		)

	}
	//beta
	def docSave = Action.async(parse.json) { request =>
 		
		val reqData: JsValue = request.body

		val id = (reqData \ "id").as[Double]
		val cp = (reqData \ "cp").as[Double]
		val colonia = (reqData \ "colonia").as[String]
		val ciudad = (reqData \ "ciudad").as[String]
		val delegacion = (reqData \ "delegacion").as[String]
		val lat = (reqData \ "location" \ "lat").as[Double]
		val lon = (reqData \ "location" \ "lon").as[Double]


		val jsonDoc: JsValue = Json.toJson(
  			Map(
  				"id" -> toJson(id),
          		"cp" -> toJson(cp),
          		"colonia" -> toJson(colonia),
          		"ciudad" -> toJson(ciudad),
          		"delegacion" -> toJson(delegacion),
          		"location" -> toJson(
        			Map(
          				"lat" -> toJson(lat),
          				"lon" -> toJson(lon)
        			)
      			)
			)
  		)

  		//println("*********************** jsonDoc: " + jsonDoc)
		val futureScala = twitter2ScalaFuture.apply( FinagleClient.documentSave( List( elasticsearchIndex, indexType ), jsonDoc ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		)

	}


}

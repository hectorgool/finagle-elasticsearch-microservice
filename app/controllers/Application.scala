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

	def createDocument = Action.async(parse.json) { request =>
 		
		val reqData: JsValue = request.body

		val id = (reqData \ "id").as[Double]
		val cp = (reqData \ "cp").as[Double]
		val colonia = (reqData \ "colonia").as[String]
		val ciudad = (reqData \ "ciudad").as[String]
		val delegacion = (reqData \ "delegacion").as[String]
		val lat = (reqData \ "location" \ "lat").as[Double]
		val lon = (reqData \ "location" \ "lon").as[Double]

		val json: JsValue = Json.obj(
      		"cp" -> cp,
      		"colonia" -> colonia,
      		"ciudad" -> ciudad,
      		"delegacion" -> delegacion,
      		"location" -> Json.obj(
  				"lat" -> lat,
  				"lon" -> lon
  			)
		)

		val futureScala = twitter2ScalaFuture.apply( FinagleClient.documentSave( List( elasticsearchIndex, indexType, id.toString  ), json ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		)

	}

	def readDocument = Action.async(parse.json) { request =>
 
		val json = request.body
		val futureScala = twitter2ScalaFuture.apply( FinagleClient.documentSearch( elasticsearchIndex, indexType, json ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		)

	}	
	//beta
	def updateDocument = Action.async(parse.json) { request =>
 		
		val reqData: JsValue = request.body

		val id = (reqData \ "id").as[Double]
		val cp = (reqData \ "cp").as[Double]
		val colonia = (reqData \ "colonia").as[String]
		val ciudad = (reqData \ "ciudad").as[String]
		val delegacion = (reqData \ "delegacion").as[String]
		val lat = (reqData \ "location" \ "lat").as[Double]
		val lon = (reqData \ "location" \ "lon").as[Double]

		val json: JsValue = Json.obj(
			"doc" -> Json.obj(
	      		"cp" -> cp,
	      		"colonia" -> colonia,
	      		"ciudad" -> ciudad,
	      		"delegacion" -> delegacion,
	      		"location" -> Json.obj(
	  				"lat" -> lat,
	  				"lon" -> lon
	  			)
  			),
  			"doc_as_upsert"-> "true"
		)

		val futureScala = twitter2ScalaFuture.apply( FinagleClient.documentSave( List( elasticsearchIndex, indexType, id.toString, "_update" ), json ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		)

	}

	def deleteDocument( id: Long ) = Action.async {

		val futureScala = twitter2ScalaFuture.apply( FinagleClient.documentDelete( List( elasticsearchIndex, indexType, id.toString ) ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		)

	}


}

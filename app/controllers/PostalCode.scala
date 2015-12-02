package controllers


import com.twitter.bijection.twitter_util._
import com.twitter.io.Charsets
import java.util.concurrent.TimeoutException
import models._
import org.jboss.netty.util.CharsetUtil
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json.{toJson}
import play.api.libs.json.{Json,JsValue}
import play.api.mvc._
import play.api.Play.current
import play.api._
import scala.concurrent.{Future}
import scala.util.{Failure, Success}


class PostalCode extends Controller with UtilBijections {


	val elasticsearchIndex = current.configuration.getString("elasticsearch.index").get
	val indexType = current.configuration.getString("elasticsearch.indexType").get
	val size = current.configuration.getString("elasticsearch.size").get

	def createDocument = Action.async(parse.json) { request =>
 		
		val reqData: JsValue = request.body

		println("reqData: " + reqData)

		val id = (reqData \ "id").as[String]
		val cp = (reqData \ "cp").asOpt[Double]
		val colonia = (reqData \ "colonia").asOpt[String]
		val ciudad = (reqData \ "ciudad").asOpt[String]
		val delegacion = (reqData \ "delegacion").asOpt[String]
		val lat = (reqData \ "location" \ "lat").asOpt[Double]
		val lon = (reqData \ "location" \ "lon").asOpt[Double]

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

		val futureScala = twitter2ScalaFuture.apply( TwitterFinagleClient.documentSave( List( elasticsearchIndex, indexType, id.toString  ), json ) )
        
		futureScala.map{ f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		}.recover {
      		case t: TimeoutException =>
        		Logger.error("Problem found in createDocument process")
        		InternalServerError(t.getMessage)
    	}

	}

	def readDocument = Action.async(parse.json) { request =>

		val term = (request.body \ "term").asOpt[String]

		val json: JsValue = Json.obj(
			"size" -> size,
			"query" -> Json.obj(
				"match" -> Json.obj(
					"_all" -> Json.obj(
						"query" -> term,
                		"operator" -> "and"	
					)
				)
			),
			"sort" -> Json.arr(
				Json.obj(
					"colonia" -> Json.obj( "order"-> "asc", "mode" -> "avg")
				)
			)
		)

		val futureScala = twitter2ScalaFuture.apply( TwitterFinagleClient.documentSearch( elasticsearchIndex, indexType, json ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		).recover {
      		case t: TimeoutException =>
        		Logger.error("Problem found in readDocument process")
        		InternalServerError(t.getMessage)
    	}

	}	

	def updateDocument = Action.async(parse.json) { request =>
 		
		val reqData: JsValue = request.body

		val id = (reqData \ "id").as[String]
		val cp = (reqData \ "cp").asOpt[Double]
		val colonia = (reqData \ "colonia").asOpt[String]
		val ciudad = (reqData \ "ciudad").asOpt[String]
		val delegacion = (reqData \ "delegacion").asOpt[String]
		val lat = (reqData \ "location" \ "lat").asOpt[Double]
		val lon = (reqData \ "location" \ "lon").asOpt[Double]

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

		val futureScala = twitter2ScalaFuture.apply( TwitterFinagleClient.documentSave( List( elasticsearchIndex, indexType, id, "_update" ), json ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		).recover {
      		case t: TimeoutException =>
        		Logger.error("Problem found in updateDocument process")
        		InternalServerError(t.getMessage)
    	}

	}

	def deleteDocument( id: String ) = Action.async {

		val futureScala = twitter2ScalaFuture.apply( TwitterFinagleClient.documentDelete( List( elasticsearchIndex, indexType, id ) ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		).recover {
      		case t: TimeoutException =>
        		Logger.error("Problem found in deleteDocument process")
        		InternalServerError(t.getMessage)
    	}

	}
	
	def getDocument( term: String ) = Action.async {
 
		val json: JsValue = Json.obj(
			"size" -> size,
			"query" -> Json.obj(
				"match" -> Json.obj(
					"_all" -> Json.obj(
						"query" -> term,
                		"operator" -> "and"	
					)
				)
			),
			"sort" -> Json.arr(
				Json.obj(
					"colonia" -> Json.obj( "order"-> "asc", "mode" -> "avg")
				)
			)
		)
		
		val futureScala = twitter2ScalaFuture.apply( TwitterFinagleClient.documentSearch( elasticsearchIndex, indexType, json ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		).recover {
      		case t: TimeoutException =>
        		Logger.error("Problem found in getDocument process")
        		InternalServerError(t.getMessage)
    	}

	}
	

}

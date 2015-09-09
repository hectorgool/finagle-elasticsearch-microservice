package controllers


import com.twitter.bijection.twitter_util._
import com.twitter.io.Charsets
import lib._
import org.jboss.netty.util.CharsetUtil
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{Json,JsValue}
import play.api.mvc._
import play.api.Play.current
import play.api._
import scala.concurrent.{Future}
import scala.util.{Failure, Success}


class Application extends Controller with UtilBijections {


	val elasticsearchIndex = current.configuration.getString("elasticsearch.index").get
	val indexType = current.configuration.getString("elasticsearch.indexType").get

  	def index = Action {
    	Ok(views.html.index("Your new application is ready."))
  	}

	def searchTerm = Action.async(parse.json) { request =>
 
		val json = request.body
		val futureScala = twitter2ScalaFuture.apply( FinagleClient.documentSearch( elasticsearchIndex, indexType, json ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		)

	}
	//beta
	def docSave = Action.async(parse.json) { request =>
 
		val json = request.body
		println("request.body: " + json )
		val futureScala = twitter2ScalaFuture.apply( FinagleClient.documentSave( List( elasticsearchIndex, indexType ), json ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		)

	}


}

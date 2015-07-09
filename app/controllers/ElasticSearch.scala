package controllers


import play.api._
import play.api.mvc._
import lib._
import scala.concurrent.{Future}
import scala.util.{Failure, Success}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import com.twitter.io.Charsets
import org.jboss.netty.util.CharsetUtil
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import com.twitter.bijection.twitter_util._
import play.api.Play.current


class ElasticSearch extends Controller with UtilBijections {


	val index = current.configuration.getString("elasticsearch.index").get

	val indexType = current.configuration.getString("elasticsearch.indexType").get

	def term = Action.async(parse.json) { request =>
 
		val json = request.body
		val futureScala = twitter2ScalaFuture.apply( FinagleClient.documentSearch( index, indexType, json ) )
        
		futureScala.map( f => 
			Ok( Json.parse( f.getContent.toString( CharsetUtil.UTF_8 ) ) )
		)

	}
	

}
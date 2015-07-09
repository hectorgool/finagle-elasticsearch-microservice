package lib


import com.twitter.finagle.ServiceFactory
import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.{Http, Service}
import com.twitter.conversions.time._
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.util.CharsetUtil._
import org.jboss.netty.handler.codec.http.HttpHeaders.Names._
import org.jboss.netty.util.CharsetUtil
import com.twitter.util.Future
import play.api.Logger
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import play.api.Play.current


object FinagleClient{


  val hosts = current.configuration.getString("elasticsearch.hosts").get
  val client: Service[HttpRequest, HttpResponse] = Http.newService(hosts)

  def requestBuilderGet(path: List[String], json: JsValue): DefaultHttpRequest = {

    val payload = ChannelBuffers.copiedBuffer( Json.stringify(json) , UTF_8)    
    val _path = path.mkString("/","/","")
    val request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, _path)
    
    request.headers().add(USER_AGENT, "Finagle - Play")
    request.headers().add(HOST, "http://localhost")
    request.headers().add(CONTENT_TYPE, "application/json")
    request.headers().add(CONNECTION, "keep-alive")
    request.headers().add(CONTENT_LENGTH, String.valueOf(payload.readableBytes()));
    request.setContent(payload)
    
    Logger.debug("Sending request:\n%s".format(request))    
    Logger.debug("Sending body:\n%s".format(request.getContent.toString(CharsetUtil.UTF_8)))
    
    request

  }

  def requestBuilderPut(path: List[String], json: JsValue): DefaultHttpRequest = {

    val payload = ChannelBuffers.copiedBuffer( Json.stringify(json) , UTF_8)
    val _path = path.mkString("/","/","")
    val request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.PUT, _path)

    request.headers().add(USER_AGENT, "Finagle - Play")
    request.headers().add(HOST, hosts) // the host.openOr("localhost") can be replace for host.openOr("default value here")
    request.headers().add(CONTENT_TYPE, "application/json")
    request.headers().add(CONNECTION, "keep-alive")
    request.headers().add(CONTENT_LENGTH, String.valueOf(payload.readableBytes()));
    request.headers().add(ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost")//santo
    request.setContent(payload)
  
    Logger.debug("Sending request:\n%s".format(request))
    Logger.debug("Sending body:\n%s".format(request.getContent.toString(CharsetUtil.UTF_8)))
    
    request
  
  }

  def requestBuilderDelete(path: List[String]): DefaultHttpRequest = {

    val _path = path.mkString("/","/","")
    val request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.DELETE, _path)

    request.headers().add(USER_AGENT, "Finagle - Play")
    request.headers().add(HOST, hosts)
    request.headers().add(ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost")//santo
    
    Logger.debug("Sending request:\n%s".format(request))
    Logger.debug("Sending body:\n%s".format(request.getContent.toString(CharsetUtil.UTF_8)))
    
    request

  }

  def sendToElastic(request: DefaultHttpRequest): Future[HttpResponse] = {

    Logger.debug("Request to send is %s" format request)
    val httpResponse = client(request)

    httpResponse.onSuccess{
      response =>
        Logger.debug("Received response: " + response)
    }.onFailure{ err: Throwable =>
        Logger.error(err.toString)
    }

  }

  def documentSearch(index: String, indexType: String, json: JsValue): Future[HttpResponse] ={

    val req = requestBuilderGet(List( index, indexType, "_search"), json)
    sendToElastic(req)

  }

  def documentSave(path: List[String], json: JsValue) ={

    Logger.debug("json is %s" format json)
    val req = requestBuilderPut(path, json)
    sendToElastic(req)

  }


}
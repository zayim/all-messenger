package controllers

import javax.inject._

import model.{Message, MessageUploadResource}
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import services.MessageService

@Singleton
class MessageController @Inject()(val messagesApi: MessagesApi, messageService: MessageService) extends Controller with I18nSupport {

  val sourceOptions: List[(String, String)] = List[(String, String)](("fb", "Facebook"), ("skype", "Skype"), ("viber", "Viber"))

  def list = Action {

    val messages = messageService.findAll()
    val first: Message = messages.head
    Logger.debug(s"elements: $messages")
    Logger.debug(s"first: $first")

    Ok(views.html.message.list(messages))
  }

  val form: Form[MessageUploadResource] = Form(mapping("source" -> nonEmptyText)(MessageUploadResource.apply)(MessageUploadResource.unapply))

  def upload = Action {
    Ok(views.html.message.upload(sourceOptions, form))
  }

  def uploadPost = Action(parse.multipartFormData) {

    Logger.debug("received form")

    implicit request => {

      val messageUploadResource: MessageUploadResource = form.bindFromRequest().fold(errFrm => None, spec => Some(spec)).get
      Logger.debug(s"spec: $messageUploadResource")

      request.body.file("file").map { messageFile =>
        import scala.io.Source._

        val filename = messageFile.filename
        Logger.debug(s"filename: $filename")

        val lines: List[String] = fromFile(messageFile.ref.file).getLines().toList

        lines.foreach(line => Logger.debug(s"line: $line"))

        Ok("File uploaded")
      }.getOrElse {
        Redirect(routes.HomeController.index()).flashing(
          "error" -> "Missing file")
      }
    }
  }
}

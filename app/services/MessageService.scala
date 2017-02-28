package services

import javax.inject._

import model.Message
import org.mongodb.scala.{Document, MongoCollection}
import play.api.Logger
import util.DB
import util.MongoHelpers._

@Singleton
class MessageService @Inject()() {

  val collection: MongoCollection[Document] = DB.instance.getCollection("messages")

  def findAll(): List[Message] = {

    Logger.info("MessageService.findAll")
    collection.find().results().map(d => Message.fromDocument(d)).toList

  }

}

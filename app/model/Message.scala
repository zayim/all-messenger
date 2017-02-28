package model

import org.mongodb.scala.Document

case class Message (source: String, sender: String, receiver: String, text: String) {
  def toDocument: Document = Message.toDocument(this)
}

object Message {

  def fromDocument(document: Document): Message = Message(
    document.getString("source"),
    document.getString("sender"),
    document.getString("receiver"),
    document.getString("text")
  )

  def toDocument(message: Message): Document = Document(
    "source" -> message.source,
    "sender" -> message.sender,
    "receiver" -> message.receiver,
    "text" -> message.text
  )

}

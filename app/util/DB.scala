package util

import org.mongodb.scala.{MongoClient, MongoDatabase}

object DB {

  val instance: MongoDatabase = MongoClient(
    Config.instance.getString("mongo.url")
  ).getDatabase(
    Config.instance.getString("mongo.database")
  )

}

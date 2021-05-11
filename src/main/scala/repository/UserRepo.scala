package repository

import reactivemongo.api.bson.collection.BSONCollection
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros, document}
import scala.util.{Failure, Success}
import models.User



object UserRepo {

  val mongoUri = "mongodb://127.0.0.1:27017/"

  import ExecutionContext.Implicits.global

  val driver = AsyncDriver()
  val parsedUri = MongoConnection.fromString(mongoUri)

  // Database and collections: Get references
  val futureConnection = parsedUri.flatMap(driver.connect(_))
  def db1: Future[DB] = futureConnection.flatMap(_.database("mydb"))
  def studentCollection: Future[BSONCollection] = db1.map(_.collection("user"))
  implicit def studentWriter: BSONDocumentWriter[User] = Macros.writer[User]

  def create(student: User): Future[Unit] =
    studentCollection.flatMap(_.insert.one(student).map(_ => {}))

  implicit def personReader: BSONDocumentReader[User] = Macros.reader[User]

  def getAll(): Future[List[User]] = {
    studentCollection.flatMap(_.find(document()).
      cursor[User]().
      collect[List](10, Cursor.FailOnError[List[User]]()))
  }

  def search(email :String,pass:String)={
    studentCollection.flatMap(_.find(BSONDocument("email" -> email,"password"->pass), Option.empty[User]).one[User])
  }



}

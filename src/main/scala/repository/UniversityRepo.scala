package repository

import models.University
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros, document}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


object UniversityRepo {

  val mongoUri = "mongodb://127.0.0.1:27017/"

  import ExecutionContext.Implicits.global

  val driver = AsyncDriver()
  val parsedUri = MongoConnection.fromString(mongoUri)

  // Database and collections: Get references
  val futureConnection = parsedUri.flatMap(driver.connect(_))
  def db1: Future[DB] = futureConnection.flatMap(_.database("mydb"))
  def studentCollection: Future[BSONCollection] = db1.map(_.collection("university"))
  implicit def studentWriter: BSONDocumentWriter[University] = Macros.writer[University]



  def create(student: University): Future[Unit] =
    studentCollection.flatMap(_.insert.one(student).map(_ => {}))

  def update(person: University): Future[Int] = {
    val selector = document("id" -> person.id)
    studentCollection.flatMap(_.update.one(selector, person).map(_.n))
  }

  implicit def personReader: BSONDocumentReader[University] = Macros.reader[University]

  def findById(id: Int): Future[List[University]] =
    studentCollection.flatMap(_.find(document("id" -> id)).
      cursor[University]().
      collect[List](1, Cursor.FailOnError[List[University]]()))

  def findByName(name: String): Future[List[University]] =
    studentCollection.flatMap(_.find(document("name" -> name)).
      cursor[University]().
      collect[List](1, Cursor.FailOnError[List[University]]()))


  def getAll(): Future[List[University]] = {
    studentCollection.flatMap(_.find(document()).
      cursor[University]().
      collect[List](10, Cursor.FailOnError[List[University]]()))
  }

  def delete(id:Int) = {
    val selector1 = BSONDocument("id" ->id)
    val futureRemove1 = studentCollection.flatMap(_.delete.one(selector1))
    futureRemove1.onComplete {
      case Failure(e) => throw e
      case Success(writeResult) => println("successfully removed document")
    }
  }


}

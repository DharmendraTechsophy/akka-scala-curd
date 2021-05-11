package rest

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.{Authorization, OAuth2BearerToken, RawHeader}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import models.User
import repository.{StudentRepo, UserRepo}
import rest.RestService

class UserRoutesSpec extends AnyWordSpec with Matchers with ScalatestRouteTest with RestService {

  val routes = route
   val studentRepo = StudentRepo
  val user = studentRepo

  "UserRoutes" should {
    "return  users(GET /student" in {
      val request = HttpRequest(uri = "/student/getall")
      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
        entityAs[String] should ===("""{"student":[{"name":"dharmendra","email":"d@gmail.com","uId":1,"userId":1,"id":1}""")
      }
    }

    "be able to add users (POST /student)" in {
      val user = List("Dharmendra", "d@gmail.com",1, 1, 1)
      val userEntity = Marshal(user).to[MessageEntity].value.get.get
      val request = Post("/student/create")
      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
        entityAs[String] should ===("Student created Successfully !!")
      }
    }

    "be able to get user by name" in {
      val request = Get(uri = "/student/dharmendra")

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
        entityAs[String] should ===("""{"student":[{"name":"dharmendra","email":"d@gmail.com","uId":1,"userId":1,"id":1}""")
      }
    }

    "be able to remove users (DELETE /student)" in {
      val request = Delete(uri = "/users/1")

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
        entityAs[String] should ===("Student Deleted Succesfully!!.")
      }
    }

  }

}

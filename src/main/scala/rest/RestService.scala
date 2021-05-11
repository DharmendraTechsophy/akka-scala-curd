package rest

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import json.JsonUtility._
//import json.TokenAuthorization
import models._
import repository._
import rest.CORSHandler
//import akka.http.scaladsl.server.directives.RouteDirectives.complete

import scala.concurrent.Future

object RestService {
  private val cors = new CORSHandler {}
  def main(args: Array[String]): Unit = {

    /**
     * create ActorSystem
     *
     */

    implicit val system = ActorSystem(Behaviors.empty,"akka-scala")
    //implicit val mat:  ActorMaterializer = ActorMaterializer()
    implicit  val ex = system.executionContext

    /**
      * Create routes
      */
    val route =
          path("student" / "getall") {
            options{
              cors.corsHandler(complete(StatusCodes.OK))
            }~
            get{
              cors.corsHandler(
                complete {StudentRepo.getAll().map { res=>
                  println("res : "+res)
                  write(res)
                }
                }
              )
            }
          }~
          path("student" / "get") {
            options{
              cors.corsHandler(complete(StatusCodes.OK))
            }~
            get {
                parameters('id.as[Int]) {
                  id => // URL parameter
                    {

                      cors.corsHandler(
                        complete {
                          StudentRepo.findById(id).map
                          {
                            res=>write(res)
                          }
                        }
                      )
                    }

                }
              }
          }~
          path("student" / "getbyname") {
            options{
              cors.corsHandler(complete(StatusCodes.OK))
            }~
            get {
              parameters('name.as[String]) {
                name => // URL parameter
                {

                  cors.corsHandler(
                    complete {
                      StudentRepo.findByName(name).map
                      {
                        res=>write(res)
                      }
                    }
                  )
                }

              }
            }
          }~
          path("student" / "create")
          {
            options{
              cors.corsHandler(complete(StatusCodes.OK))
            }~
            post{
              entity(as[String]) { // post body parameter
                personJson =>
                  cors.corsHandler(
                    complete {
                      val student = parse(personJson).extract[Student]
                      StudentRepo.create(student).map{res=>
                        res.toString
                      }
                    }
                  )
              }
            }
        }~
        path("student" / "delete")
        {
          options{
            cors.corsHandler(complete(StatusCodes.OK))
          }~
          delete{
            parameters('id.as[Int]) {// post body parameter
              id =>
                cors.corsHandler(
                  complete {
                    StudentRepo.delete(id)
                    "Student Deleted Successfully !"
                  }
                )
            }
          }
        }~
        path("student" / "update")
        {
          options{
            cors.corsHandler(complete(StatusCodes.OK))
          }~
          post{
            entity(as[String]) { // post body parameter
              personJson =>
                cors.corsHandler(
                  complete {
                    val student = parse(personJson).extract[Student]
                    StudentRepo.update(student).map{res=>res.toString}
                    "Student Updated Successfully !"
                  }
                )
            }
          }
        }~
        path("user" / "getall") {
          options{
            cors.corsHandler(complete(StatusCodes.OK))
          }~
            get{
              cors.corsHandler(
                complete {UserRepo.getAll().map { res=>
                  println("res : "+res)
                  write(res)
                }
                }
              )
            }
        }~
        path("user" / "create")
        {
          options{
            cors.corsHandler(complete(StatusCodes.OK))
          }~
            post{
              entity(as[String]) { // post body parameter
                personJson =>
                  cors.corsHandler(
                    complete {
                      val user = parse(personJson).extract[User]
                      UserRepo.create(user).map{res=>
                        res.toString
                      }
                    }
                  )
              }
            }
        }~
        path("user" / "search")
        {
          options{
            cors.corsHandler(complete(StatusCodes.OK))
          }~
            post{
              entity(as[String]) { // post body parameter
                personJson =>

                  val user = parse(personJson).extract[User]
                  val s = UserRepo.search(user.email,user.password)
                  cors.corsHandler(complete(s.map{res=>write(res)}))
//                  if (UserRepo.search(user.email,user.password) != null) {
//                    //val token = TokenAuthorization.generateToken(user.email, user.password)
//                    //cors.corsHandler(complete((StatusCodes.OK, token)))
//                    //cors.corsHandler(complete((StatusCodes.OK)))
//
//                  }
//                  else {
//                    cors.corsHandler(complete(StatusCodes.Unauthorized))
//                  }

              }
            }
        }~
      path("university" / "getall") {
        options{
          cors.corsHandler(complete(StatusCodes.OK))
        }~
          get{
            cors.corsHandler(
              complete {UniversityRepo.getAll().map { res=>
                println("res : "+res)
                write(res)
              }
              }
            )
          }
      }~
        path("university" / "get") {
          options{
            cors.corsHandler(complete(StatusCodes.OK))
          }~
            get {
              parameters('id.as[Int]) {
                id => // URL parameter
                {

                  cors.corsHandler(
                    complete {
                      UniversityRepo.findById(id).map
                      {
                        res=>write(res)
                      }
                    }
                  )
                }

              }
            }
        }~
        path("university" / "getbyname") {
          options{
            cors.corsHandler(complete(StatusCodes.OK))
          }~
            get {
              parameters('name.as[String]) {
                name => // URL parameter
                {

                  cors.corsHandler(
                    complete {
                      UniversityRepo.findByName(name).map
                      {
                        res=>write(res)
                      }
                    }
                  )
                }

              }
            }
        }~
        path("university" / "create")
        {
          options{
            cors.corsHandler(complete(StatusCodes.OK))
          }~
            post{
              entity(as[String]) { // post body parameter
                personJson =>
                  cors.corsHandler(
                    complete {
                      val university = parse(personJson).extract[University]
                      UniversityRepo.create(university).map{res=>
                        res.toString
                      }
                    }
                  )
              }
            }
        }~
        path("university" / "delete")
        {
          options{
            cors.corsHandler(complete(StatusCodes.OK))
          }~
            delete{
              parameters('id.as[Int]) {// post body parameter
                id =>
                  cors.corsHandler(
                    complete {
                      UniversityRepo.delete(id)
                      "Student Deleted Successfully !"
                    }
                  )
              }
            }
        }~
        path("university" / "update")
        {
          options{
            cors.corsHandler(complete(StatusCodes.OK))
          }~
            post{
              entity(as[String]) { // post body parameter
                personJson =>
                  cors.corsHandler(
                    complete {
                      val university = parse(personJson).extract[University]
                      UniversityRepo.update(university).map{res=>res.toString}
                      "Student Updated Successfully !"
                    }
                  )
              }
            }
        }

    /**
      * Start Rest service on port 8000
      */
    val port = 9000
    Http().newServerAt("localhost", port).bindFlow(route)

    println(s"Rest service is running on $port")



  }

}




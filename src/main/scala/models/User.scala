package models

case class User(firstName:String, lastName:String, userName:String, email:String, password:String,
                createdDate:String,
                 id: Option[Int]=None
               )
package models

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.util._
import models.daos._
import org.mindrot.jbcrypt.BCrypt
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global

object MyPasswordInfo {
  implicit lazy val format = Json.format[PasswordInfo]
  private val salt = BCrypt.gensalt(16)
  private val pwd = "xxxx"
  private val saltPwd = BCrypt.hashpw(pwd, salt)
  private val myPwd = PasswordInfo("bcrypt", saltPwd, Some(salt))
  val myMap = Map(LoginInfo("credentials", "hk-vndr@iscs-i.com") -> myPwd)
  val pwdInfo = new PasswordInfoDAO[PasswordInfo](myMap)
  private val matched = BCrypt.checkpw(pwd, saltPwd)
}

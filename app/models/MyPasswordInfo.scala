package models

import com.google.inject.name.Named
import com.google.inject.{ AbstractModule, Provides }
import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.actions.{ SecuredErrorHandler, UnsecuredErrorHandler }
import com.mohiva.play.silhouette.api.crypto._
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.services._
import com.mohiva.play.silhouette.api.util._
import com.mohiva.play.silhouette.crypto.{ JcaCrypter, JcaCrypterSettings, JcaSigner, JcaSignerSettings }
import com.mohiva.play.silhouette.impl.authenticators._
import com.mohiva.play.silhouette.impl.providers._
import com.mohiva.play.silhouette.impl.providers.oauth1._
import com.mohiva.play.silhouette.impl.providers.oauth1.secrets.{ CookieSecretProvider, CookieSecretSettings }
import com.mohiva.play.silhouette.impl.providers.oauth1.services.PlayOAuth1Service
import com.mohiva.play.silhouette.impl.providers.oauth2._
import com.mohiva.play.silhouette.impl.providers.openid.YahooProvider
import com.mohiva.play.silhouette.impl.providers.openid.services.PlayOpenIDService
import com.mohiva.play.silhouette.impl.providers.state.{ CsrfStateItemHandler, CsrfStateSettings }
import com.mohiva.play.silhouette.impl.services._
import com.mohiva.play.silhouette.impl.util._
import com.mohiva.play.silhouette.password.BCryptPasswordHasher
import com.mohiva.play.silhouette.persistence.daos.{ DelegableAuthInfoDAO, InMemoryAuthInfoDAO }
import com.mohiva.play.silhouette.persistence.repositories.DelegableAuthInfoRepository
import com.typesafe.config.Config
import models.daos._
import models.services.{ UserService, UserServiceImpl }
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._
import net.ceedubs.ficus.readers.ValueReader
import net.codingwell.scalaguice.ScalaModule
import org.mindrot.jbcrypt.BCrypt
import play.api.Configuration
import play.api.libs.json.Json
import play.api.libs.openid.OpenIdClient
import play.api.libs.ws.WSClient
import play.api.mvc.{ Cookie, CookieHeaderEncoding }
import utils.auth.{ CustomSecuredErrorHandler, CustomUnsecuredErrorHandler, DefaultEnv }

import scala.concurrent.ExecutionContext.Implicits.global

object MyPasswordInfo {
  implicit lazy val format = Json.format[PasswordInfo]
  private val salt = BCrypt.gensalt(16)
  private val pwd = "P1xelate"
  private val saltPwd = BCrypt.hashpw(pwd, salt)
  private val myPwd = PasswordInfo("bcrypt", saltPwd, Some(salt))
  val myMap = Map(LoginInfo("credentials", "hk-vndr@iscs-i.com") -> myPwd)
  val pwdInfo = new PasswordInfoDAO[PasswordInfo](myMap)
  private val matched = BCrypt.checkpw(pwd, saltPwd)
}

package models.daos

import com.mohiva.play.silhouette.api.{ AuthInfo, LoginInfo }
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import play.api.libs.json.Format

import scala.concurrent.{ ExecutionContext, Future }
import scala.reflect.ClassTag

class PasswordInfoDAO[A <: AuthInfo: ClassTag: Format](
  secrets: Map[LoginInfo, A]
)(
  implicit
  ex: ExecutionContext
) extends DelegableAuthInfoDAO[A] {

  override def find(loginInfo: LoginInfo): Future[Option[A]] =
    Future.successful(
      secrets.get(loginInfo)
    )

  override def add(loginInfo: LoginInfo, authInfo: A): Future[A] = Future.successful(authInfo)

  override def update(loginInfo: LoginInfo, authInfo: A): Future[A] = Future.successful(authInfo)

  override def save(loginInfo: LoginInfo, authInfo: A): Future[A] = Future.successful(authInfo)

  override def remove(loginInfo: LoginInfo): Future[Unit] = Future.successful(())
}

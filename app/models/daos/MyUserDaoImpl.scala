package models.daos

import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo
import models.User

class MyUserDaoImpl extends UserDAOImpl {
  val testUser = User(
    UUID.randomUUID(),
    LoginInfo("credentials", "hk-vndr@iscs-i.com"),
    Some("Henry"),
    Some("Katz"),
    Some("Henry Katz"),
    Some("hk-vndr@iscs-i.com"),
    None,
    true
  )

  save(testUser)

}

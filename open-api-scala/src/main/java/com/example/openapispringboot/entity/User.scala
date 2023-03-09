package com.example.openapispringboot.entity
import javax.persistence.{Entity, GeneratedValue, Id, Table}
import scala.beans.BeanProperty

@Entity
@Table(name = "users")
class  User {

  @Id
  @GeneratedValue
  @BeanProperty
  var id: Integer = _

  @BeanProperty
  var firstName: String = _

  @BeanProperty
  var lastName: String = _

  @BeanProperty
  var email: String = _

  @BeanProperty
  var password: String = _

  @BeanProperty
  var address: String = _

}
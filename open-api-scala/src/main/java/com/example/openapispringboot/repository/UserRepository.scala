package com.example.openapispringboot.repository

import com.example.openapispringboot.entity.User
import org.springframework.data.jpa.repository.JpaRepository

trait UserRepository extends JpaRepository[User,Integer]{

}
package com.example.openapispringboot.service

import com.example.openapispringboot.UserApiDelegate
import com.example.openapispringboot.dto.UserDTO
import com.example.openapispringboot.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import com.example.openapispringboot.entity.User

import java.util
import java.util.Optional
/*
   Implemented CRUD operations using scala service which uses
   open api specification for controllers and dto
   ----> Model mapper library to se/deserialized to Scala object.
   ----> Repository and entity is implemented in scala.
 */
@Service
class UserServiceImpl extends UserApiDelegate
{
  @Autowired
  var userRepository:UserRepository = _;

  @Autowired
  var modelMapper:ModelMapper = _;

  override def createUser(userDTO: UserDTO): ResponseEntity[UserDTO] = {
    /* converting java dto to scala object*/
     var user:User = modelMapper.map(userDTO, classOf[User]);

    /* converting scalaObject  to java dto object*/
    var response:UserDTO = modelMapper.map(userRepository.save(user),classOf[UserDTO]);
     return ResponseEntity.ok(response);
  }

  override def getUserById(id: Integer): ResponseEntity[UserDTO] = {
    var user:Optional[User]  = userRepository.findById(id);

    /* converting scalaObject  to java dto object*/
    var response:UserDTO =  modelMapper.map(user.get(),classOf[UserDTO]);
    return ResponseEntity.ok(response);
  }


  override def updateUser(userDTO: UserDTO): ResponseEntity[UserDTO] = {
    var user:User =  userRepository.findById(userDTO.getId).get();
    user.firstName = userDTO.getFirstName;
    user.lastName = userDTO.getLastName;
    user.email = userDTO.getEmail;
    /* converting scalaObject  to java dto object*/
    return ResponseEntity.ok(modelMapper.map(userRepository.save(user),classOf[UserDTO]));
  }

  override def getAllUsers: ResponseEntity[util.List[UserDTO]] = {
    var users:util.List[User] = userRepository.findAll();
    /* converting scalaObjects  to java dto objects*/
    var usersDto:util.List[UserDTO] = modelMapper.map(users,classOf[util.List[UserDTO]]);
    return ResponseEntity.ok(usersDto);
  }

  override def deleteUserById(id: Integer): ResponseEntity[Void] = {
    userRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}
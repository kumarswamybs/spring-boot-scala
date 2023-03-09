package com.example.openapispringboot.service

import com.example.openapispringboot.EmployeeApiDelegate
import com.example.openapispringboot.dto.EmployeeDTO
import com.example.openapispringboot.entity.Employee
import com.example.openapispringboot.repository.EmployeeRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

import java.util
/*
   Implemented CRUD operations using scala service which uses
   open api specification for controllers and dto
   ----> Repository and entity is implemented in java.
 */
@Service
class EmployeeServiceImpl extends EmployeeApiDelegate {

  @Autowired
  var employeeRepository:EmployeeRepository = _

  @Autowired
  var modelMapper: ModelMapper = _;

  override def createEmployee(employeeDTO: EmployeeDTO): ResponseEntity[EmployeeDTO] = {
    var employee:Employee = modelMapper.map(employeeDTO,classOf[Employee]);
    employeeRepository.save(employee);
    return ResponseEntity.ok(employeeDTO);
  }

  override def updateEmployee(employeeDTO: EmployeeDTO): ResponseEntity[EmployeeDTO] = {
    var employee:Employee = employeeRepository.findById(employeeDTO.getId).get();

    /* Compile time errors thrown by open api spec for lombok setters and getteres */
    /* Manual getters and setters are working fine with open api spec*/
   /* employee.setFirstName(employee.getFirstName)
    employee.setEmail(employeeDTO.getEmail);
    employee.setLastName(employeeDTO.getLastName);
    employee.setAddress(employee.getAddress);*/
    var updatedEmployee:Employee =  employeeRepository.save(employee);
    var response:EmployeeDTO = modelMapper.map(updatedEmployee,classOf[EmployeeDTO]);
    return ResponseEntity.ok(response);
  }

  override def getEmployeeById(id: Integer): ResponseEntity[EmployeeDTO] = {
    var employee:Employee = employeeRepository.findById(id).get();
    var response:EmployeeDTO = modelMapper.map(employee,classOf[EmployeeDTO]);
    return ResponseEntity.ok(response);
  }

  override def getAllEmployees: ResponseEntity[util.List[EmployeeDTO]] = {
    var employees: util.List[Employee] = employeeRepository.findAll();
    var usersDto: util.List[EmployeeDTO] = modelMapper.map(employees, classOf[util.List[EmployeeDTO]]);
    return ResponseEntity.ok(usersDto);
  }

  override def deleteEmployeeById(id: Integer): ResponseEntity[Void] = {
      employeeRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}

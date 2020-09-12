package lk.ijse.dep.web.pos.repository;

import lk.ijse.dep.web.pos.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {

    Customer getFirstLastCustomerIDByOrderByIdDesc()throws Exception;

//    List<Customer> findAllCustomersByOrderByName()throws Exception;
//
//    List<Customer> findAllCustomersByAddressContains(String letter)throws Exception;
//
//    long countAllCustomersByAddressStartingWithAndNameStartingWith(String address,String name)throws Exception;
//
//    @Query(value = "SELECT * FROM Customer c WHERE c.name LIKE ?1%",nativeQuery = true)
//    List<Customer> findCustomers1(String name)throws Exception;
//
//    List<String> getCustomerViaNamedQuery()throws Exception;
//
//    List<String> getCustomerViaNamedNativeQuery()throws Exception;
}

package lk.ijse.dep.web.pos.repository;

import lk.ijse.dep.web.pos.entity.CustomEntity;
import lk.ijse.dep.web.pos.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String> {

    Item getFirstLastItemCodeByOrderByCodeDesc()throws Exception;

//    List<Item> findAllItemsByUnitPriceGreaterThan(BigDecimal price)throws Exception;
//
//    List<Item> findAllItemsByUnitPriceGreaterThanAndQtyOnHandLessThan(BigDecimal price,int qty)throws Exception;
//
//    @Query(value = "SELECT * FROM Item i WHERE i.qtyOnHand < 50",nativeQuery = true)
//    List<Item> findAllItemQtyLessThan50()throws Exception;
//
//    //@Query(value = "SELECT i.description FROM Item i WHERE i.unitPrice > ?1")
//    //@Query(value = "SELECT i.description FROM Item i WHERE i.unitPrice > :abc")
//    @Query(value = "SELECT i.description FROM Item i WHERE i.unitPrice > :#{#abc}" , nativeQuery = true)
//    List<String> findItemDescription(@Param("abc") BigDecimal price) throws Exception;
//
//    List<Item> getItemViaNamedQuery(String description,int qty)throws Exception;
//
//    List<CustomEntity> getItemViaNamedQuery2(String description, int qty)throws Exception;
//
//    List<Item> getItemViaNamedNativeQuery(@Param("description") String description,@Param("qty") int qty)throws Exception;
}

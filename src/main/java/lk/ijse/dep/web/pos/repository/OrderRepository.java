package lk.ijse.dep.web.pos.repository;

import lk.ijse.dep.web.pos.entity.CustomEntity2;
import lk.ijse.dep.web.pos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    Order getFirstLastOrderIDByOrderByIdDesc()throws Exception;

}

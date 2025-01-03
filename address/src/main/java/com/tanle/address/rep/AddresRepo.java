package com.tanle.address.rep;

import com.tanle.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddresRepo extends JpaRepository<Address, Integer> {
    @Query(nativeQuery = true
            , value = """
                SELECT a.* from employee e, address a 
                WHERE a.employee_id = e.id
                AND e.id =:employeeId
            """)
    List<Address> findByEmployeeId(@Param("employeeId") int id);
}

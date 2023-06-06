package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Contract;

import jakarta.transaction.Transactional;

public interface ContractRepository extends JpaRepository<Contract, Integer>{
    @Query(value = "select code from tbl_contracts", nativeQuery = true)
    List<String> findAllCode();

    // @Query(value = "select * from tbl_contracts where status = 0", nativeQuery = true)
    // List<Contract> findUnhandledContracts();

    @Query(value = "select * from tbl_contracts where status = 1", nativeQuery = true)
    List<Contract> findAssignedContracts();

    @Query(value = "select * from tbl_contracts where status = 2", nativeQuery = true)
    List<Contract> findConfirmedContracts();

    @Query(value = "select * from tbl_contracts where status > 2", nativeQuery = true)
    List<Contract> findInstalledContracts();

    @Query(value = "select * from tbl_contracts where status = 4", nativeQuery = true)
    List<Contract> findPaidContracts();

    
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_contracts SET manager_id = :managerId, technician_id = :technicianId, status = 1 WHERE id = :id",nativeQuery = true)
    void setHandleContract(@Param("id") Integer id, @Param("managerId") Integer managerId, @Param("technicianId") Integer technicianId);

    @Query(value = "select * from tbl_contracts where status > 0 and status < 4", nativeQuery = true)
    List<Contract> findProcessingContracts();

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_contracts SET status = 2 WHERE id = :id",nativeQuery = true)
    void setConfirmContract(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_contracts SET accountant_id = :accountantId, paid_amount = :paidAmount, pay_time = :paidTime, status = 4 WHERE id = :id",nativeQuery = true)
    void setPaidContract(@Param("id") Integer id,@Param("accountantId") Integer accountantId, @Param("paidAmount") Float paidAmount, @Param("paidTime") Timestamp payTime);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_contracts SET status = 3 WHERE id = :id",nativeQuery = true)
    void setInstallContract(@Param("id") Integer id);

    /*-------------------------------- REPORT---------------------------------- */
    @Query(value = "SELECT * FROM tbl_contracts WHERE MONTH(register_time) = :month and YEAR(register_time) = :year", nativeQuery = true)
    List<Contract> findContractsByMonthAndYear(@Param("month") int month, @Param("year") int year);

        
}

package com.haui.btl.demo.Repository;

import com.haui.btl.demo.Entity.Booking;
import com.haui.btl.demo.Entity.Car;
import com.haui.btl.demo.Repository.CustomCarRepository.CarRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<Car, Integer>, CarRepositoryCustom<Car> {
    @Query("SELECT SUBSTRING_INDEX(c.address, ',', -1) AS city, COUNT(c.idcar) AS carCount " +
            "FROM Car c " +
            "GROUP BY city " +
            "ORDER BY carCount DESC " +
            "LIMIT 5")
    List<Object[]> findTop5CitiesWithMostCars();

    List<Car> findAllByidcarowner(Long idCarOwner);

//    @Query("SELECT c FROM Car c WHERE c.status = 'Available' AND c.address LIKE %:address% " +
//            "AND c.idcar NOT IN (" +
//            "SELECT b.carIdcar FROM Booking b " +
//            "WHERE b.startdatetime < :endTime AND b.enddatetime > :startTime " +
//            "AND b.status <> 'Complete')")
//    List<Car> findAvailableCars(@Param("address") String address,
//                                @Param("startTime") LocalDateTime startTime,
//                                @Param("endTime") LocalDateTime endTime);

    @Query("SELECT c FROM Car c WHERE (c.status = 'Available' AND c.idcar NOT IN (" +
            "SELECT b.carIdcar FROM Booking b " +
            "WHERE b.startdatetime <= :endTime AND b.enddatetime >= :startTime " +
            "AND b.status <> 'Completed')) " +
            "AND c.address LIKE %:address%")
    List<Car> findAvailableCars(@Param("address") String address,
                                @Param("startTime") LocalDateTime startTime,
                                @Param("endTime") LocalDateTime endTime);

    @Query(value = "SELECT EXISTS (" +
            "SELECT 1 FROM car WHERE status = 'Available' AND idCar = :idCar AND idCar NOT IN (" +
            "SELECT Car_idCar FROM booking " +
            "WHERE startDateTime <= :endTime AND endDateTime >= :startTime " +
            "AND status NOT IN ('Completed', 'Cancelled')))",
            nativeQuery = true)

    Long checkCarAvailable(@Param("startTime") LocalDateTime startTime,
                           @Param("endTime") LocalDateTime endTime,
                           @Param("idCar") int idCar);



}

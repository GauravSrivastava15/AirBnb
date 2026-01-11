package com.project.airBnbApp.repository;

import com.project.airBnbApp.entity.Hotel;
import com.project.airBnbApp.entity.Inventory;
import com.project.airBnbApp.entity.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteByRoom(Room room);

    @Query("""
            Select distinct i.hotel
            from Inventory i
            where i.city = :city
                and i.date between :startDate and :endDate
                and i.closed = false
                and (i.totalCount - i.bookedCount - i.reservedCount) >= :roomsCount
            Group by i.hotel, i.room
            Having Count(i.date) = :dateCount
            """)
    Page<Hotel> findHotelWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
    );

    @Query("""
            Select i 
            From Inventory i
            Where i.room.id = :roomId
                and i.date between :startDate and :endDate
                and i.closed = false
                and (i.totalCount - i.bookedCount - i.reservedCount) >= :roomsCount
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Inventory> findAndLockAvailableInventory(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount
    );

    List<Inventory> findByHotelAndDateBetween(Hotel hotel, LocalDate startDate, LocalDate endDate);
}

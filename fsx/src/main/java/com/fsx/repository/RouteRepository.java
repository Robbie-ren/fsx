package com.fsx.repository;

import com.fsx.dto.TopRouteDto;
import com.fsx.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Integer> {
    @Query("""
            SELECT r
            FROM Route r
            where r.isActive = true and(
            LOWER(r.sourceCity) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(r.destinationCity) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
            """)
    Page<Route> getRoutes(@Param("keyword") String keyword, Pageable pageable);

    @Query("""
            select r from Route r where r.isActive=true""")
    Page<Route> findAllActive(Pageable pageable);

    @Query("""
            SELECT DISTINCT r.sourceCity
            FROM Route r
            WHERE LOWER(r.sourceCity) LIKE LOWER(CONCAT('%', :query, '%'))""")
    List<String> searchSourceCities(@Param("query") String query);

    @Query("""
            SELECT DISTINCT r.destinationCity
            FROM Route r
            WHERE LOWER(r.destinationCity) LIKE LOWER(CONCAT('%', :query, '%'))""")
    List<String> searchDestinationCities(@Param("query") String query);

    @Query("""
        select new com.fsx.dto.TopRouteDto(
            r.sourceCity,
            r.destinationCity,
            min(s.price),
            count(b)
        )
        from Booking b
        join b.schedule s
        join s.route r
        group by r.sourceCity, r.destinationCity
        order by count(b) desc
    """)
    List<TopRouteDto> findTopRoutes(Pageable pageable);
}

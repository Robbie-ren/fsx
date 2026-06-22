package com.fsx.repository;

import com.fsx.dto.FilterReqDto;
import com.fsx.model.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Schedule> getSchedulesByFilter(FilterReqDto dto, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Main Query
        CriteriaQuery<Schedule> cq = cb.createQuery(Schedule.class);

        Root<Schedule> root = cq.from(Schedule.class);

        List<Predicate> predicates = buildPredicates(dto, root, cb);

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Schedule> query = entityManager.createQuery(cq);

        query.setFirstResult((int) pageable.getOffset());

        query.setMaxResults(pageable.getPageSize());

        List<Schedule> schedules = query.getResultList();

        // Count Query
        Long total = getTotalCount(dto);

        return new PageImpl<>(schedules, pageable, total);
    }

    @Override
    public List<Schedule> getSchedulesByFilterV2(FilterReqDto dto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Schedule> cq = cb.createQuery(Schedule.class);

        Root<Schedule> root = cq.from(Schedule.class);

        List<Predicate> predicates = buildPredicates(dto, root, cb);

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Schedule> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    private Long getTotalCount(FilterReqDto dto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);

        Root<Schedule> root = countQuery.from(Schedule.class);

        List<Predicate> predicates = buildPredicates(dto, root, cb);

        countQuery.select(cb.count(root));

        countQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private List<Predicate> buildPredicates(FilterReqDto dto, Root<Schedule> root, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        // Mandatory Filters

        predicates.add(cb.equal(root.get("route").get("sourceCity"), dto.source()));

        predicates.add(cb.equal(root.get("route").get("destinationCity"), dto.destination()));

        predicates.add(cb.equal(root.get("departureDate"), dto.dateOfJourney()));

        // Optional Bus Type

        if (dto.busType() != null) {predicates.add(cb.equal(root.get("bus").get("busType"), dto.busType()));
        }

        // Optional Time Range

        if (dto.startTime() != null && dto.endTime() != null) {

            predicates.add(cb.between(root.get("departureTime"), dto.startTime(), dto.endTime()));
        }

        // Optional Max Price

        if (dto.maxPrice() != null) {

            predicates.add(cb.lessThanOrEqualTo(root.get("price"), dto.maxPrice()));
        }

        return predicates;
    }
}


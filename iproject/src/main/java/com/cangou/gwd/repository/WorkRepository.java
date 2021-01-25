package com.cangou.gwd.repository;

import com.cangou.gwd.domain.Work;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Work entity.
 */
@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    @Query(value = "select distinct work from Work work left join fetch work.executedOns",
        countQuery = "select count(distinct work) from Work work")
    Page<Work> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct work from Work work left join fetch work.executedOns")
    List<Work> findAllWithEagerRelationships();

    @Query("select work from Work work left join fetch work.executedOns where work.id =:id")
    Optional<Work> findOneWithEagerRelationships(@Param("id") Long id);
}

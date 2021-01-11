package com.cangou.gwd.repository;

import com.cangou.gwd.domain.Freelance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Freelance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FreelanceRepository extends JpaRepository<Freelance, Long> {
}

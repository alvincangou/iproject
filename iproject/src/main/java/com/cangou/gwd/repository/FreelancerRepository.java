package com.cangou.gwd.repository;

import com.cangou.gwd.domain.Freelancer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Freelancer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
}

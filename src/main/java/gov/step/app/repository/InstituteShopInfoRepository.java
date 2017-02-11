package gov.step.app.repository;

import gov.step.app.domain.InstituteShopInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstituteShopInfo entity.
 */
public interface InstituteShopInfoRepository extends JpaRepository<InstituteShopInfo,Long> {

}

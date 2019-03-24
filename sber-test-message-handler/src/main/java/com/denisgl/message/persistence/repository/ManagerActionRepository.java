package com.denisgl.message.persistence.repository;

import com.denisgl.message.persistence.entity.ManagerAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerActionRepository extends JpaRepository<ManagerAction, Long> {

}

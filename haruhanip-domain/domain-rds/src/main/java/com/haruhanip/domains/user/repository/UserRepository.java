package com.haruhanip.domains.user.repository;

import com.haruhanip.domains.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

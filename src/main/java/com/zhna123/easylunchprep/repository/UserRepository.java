package com.zhna123.easylunchprep.repository;

import com.zhna123.easylunchprep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

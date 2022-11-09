package com.tilted.Laba3.Repositories;

import com.tilted.Laba3.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepository extends JpaRepository<User, Integer> {
}

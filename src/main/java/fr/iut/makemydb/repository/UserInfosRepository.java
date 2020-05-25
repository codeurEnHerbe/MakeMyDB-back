package fr.iut.makemydb.repository;

import fr.iut.makemydb.domain.UserInfosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfosRepository extends JpaRepository<UserInfosEntity, String> {
    public String getEmailByUsername(String username);
    public Optional<UserInfosEntity> findByEmail(String email);
}
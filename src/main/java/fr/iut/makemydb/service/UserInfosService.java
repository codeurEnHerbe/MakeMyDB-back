package fr.iut.makemydb.service;

import fr.iut.makemydb.domain.SchemaEntity;
import fr.iut.makemydb.domain.UserInfosEntity;
import fr.iut.makemydb.dto.SchemaDTO;
import fr.iut.makemydb.dto.UserRegisterDTO;
import fr.iut.makemydb.repository.SchemaRepository;
import fr.iut.makemydb.repository.UserInfosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfosService {

    @Autowired
    private UserInfosRepository repository;

    public UserInfosService(){
    }

    public UserInfosEntity saveUserInfos(UserRegisterDTO user){
        UserInfosEntity userInfos = new UserInfosEntity(user.getUsername(), user.getEmail());
        this.repository.save(userInfos);
        return userInfos;
    }

    public Boolean checkEmail(String email){
        Optional<UserInfosEntity> result = this.repository.findByEmail(email);
        return !result.isPresent();
    }
}
package com.tilted.Laba3.Services;

import com.tilted.Laba3.Interfaces.IUsersService;
import com.tilted.Laba3.Mappers.UserMapper;
import com.tilted.Laba3.Models.UserDTO;
import com.tilted.Laba3.Repositories.IUsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService implements IUsersService {

    private final IUsersRepository usersRepository;

    public UsersService(IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDTO Create(UserDTO user) {
        return UserMapper.ToDTO(usersRepository.save(UserMapper.ToModel(user)));
    }

    @Override
    public List<UserDTO> GetAll() {
        return usersRepository.findAll().stream().map(UserMapper::ToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO GetById(int id) {
        return usersRepository.findById(id).map(UserMapper::ToDTO).orElse(null);
    }

    @Override
    public void Update(UserDTO user) {
        usersRepository.save(UserMapper.ToModel(user));
    }

    @Override
    public boolean DeleteById(int id) {
        if (!usersRepository.existsById(id)) {
            return false;
        }

        usersRepository.deleteById(id);
        return true;
    }
}

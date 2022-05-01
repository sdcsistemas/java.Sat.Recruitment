package sat.recruitment.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sat.recruitment.api.dto.UserDTO;
import sat.recruitment.api.model.User;
import sat.recruitment.api.repository.UserRepository;
import sat.recruitment.api.service.SatRecruitmentDatabaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SatRecruitmentDatabaseServiceImpl implements SatRecruitmentDatabaseService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            log.info("User with id {} not found in the database", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        log.info("User with id {} is", id, userOpt.get());
        return this.entityToDto(userOpt.orElse(null));
    }

    @Override
    public UserDTO insert(UserDTO dto) {
        Optional<User> existUserOpt = userRepository.findById(dto.getId());
        if (existUserOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED);
        }
        User user = userRepository.save(this.dtoToEntity(dto));
        log.info("user inserted in the database: {}", dto);
        return this.entityToDto(user);
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        log.info("User before update in the database: {}", dto);
        Optional<User> existUserOpt = userRepository.findById(id);
        if (!existUserOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User user = existUserOpt.orElse(null);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());
        user.setUserType(dto.getUserType());
        user.setMoney(dto.getMoney());
        log.info("User after update in the database: {}", user);
        return this.entityToDto(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            log.info("User with id {} not found in the database", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        log.info("User with id {} deleted successfully in the database", id);
    }

    private UserDTO entityToDto(User model) {
        return new UserDTO(model.getId(), model.getName(), model.getEmail(), model.getAddress(),
                model.getPhone(), model.getUserType(), model.getMoney());
    }

    private User dtoToEntity(UserDTO dto) {
        return new User(dto.getId(), dto.getName(), dto.getEmail(), dto.getAddress(),
                dto.getPhone(), dto.getUserType(), dto.getMoney());
    }
}

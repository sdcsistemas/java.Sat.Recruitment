package sat.recruitment.api.service;

import sat.recruitment.api.dto.UserDTO;

import java.util.List;

public interface SatRecruitmentDatabaseService {

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO insert(UserDTO dto);

    UserDTO update(Long id, UserDTO dto);

    void delete(Long id);

}

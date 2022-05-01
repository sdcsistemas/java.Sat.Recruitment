package sat.recruitment.api.service;

import sat.recruitment.api.dto.UserDTO;

import java.util.List;

public interface SatRecruitmentFileService {

    List<UserDTO> createUser(UserDTO userRequest);

    List<UserDTO> findAll();

    UserDTO findById(Long id);
}

package sat.recruitment.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sat.recruitment.api.dto.UserDTO;
import sat.recruitment.api.service.SatRecruitmentFileService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/api/v1")
public class SatRecruitmentFileController {

    @Autowired
    private SatRecruitmentFileService satRecruitmentFileService;

    @PostMapping(value = "/create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<List<UserDTO>> createUser(@Valid @RequestBody UserDTO messageBody) {
        return ResponseEntity.ok(satRecruitmentFileService.createUser(messageBody));
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(satRecruitmentFileService.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") @NotNull(message = "id: is required") Long id) {
        return ResponseEntity.ok(satRecruitmentFileService.findById(id));
    }


}

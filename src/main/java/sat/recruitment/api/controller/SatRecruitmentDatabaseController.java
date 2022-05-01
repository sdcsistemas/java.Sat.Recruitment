package sat.recruitment.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sat.recruitment.api.dto.UserDTO;
import sat.recruitment.api.service.SatRecruitmentDatabaseService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@Validated
@RequestMapping("/api/v1/user/database")
public class SatRecruitmentDatabaseController {

    @Autowired
    private SatRecruitmentDatabaseService satRecruitmentDatabaseService;

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(satRecruitmentDatabaseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") @NotNull(message = "id: is required") Long id) {
        return ResponseEntity.ok(satRecruitmentDatabaseService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<?> insert(@RequestBody UserDTO user) {
        return ResponseEntity.ok(satRecruitmentDatabaseService.insert(user));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") @NotNull(message = "id: is required") Long id, @RequestBody UserDTO user) {
        return ResponseEntity.ok(satRecruitmentDatabaseService.update(id, user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") @NotNull(message = "id: is required") Long id) {
        satRecruitmentDatabaseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

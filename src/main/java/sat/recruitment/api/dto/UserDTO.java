package sat.recruitment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id: is required")
    private Long id;

    @NotBlank(message = "name: is required")
    private String name;

    @NotBlank(message = "email: is required")
    private String email;

    @NotBlank(message = "address: is required")
    private String address;

    @NotBlank(message = "phone: is required")
    private String phone;

    private String userType;

    private Double money;

}

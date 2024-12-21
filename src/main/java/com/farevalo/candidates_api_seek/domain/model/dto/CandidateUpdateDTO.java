package com.farevalo.candidates_api_seek.domain.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateUpdateDTO {
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Email(message = "El email debe tener un formato válido")
    private String email;

    @Pattern(regexp = "^(?i)(MASCULINO|FEMENINO|OTRO)$", message = "El género debe ser MASCULINO, FEMENINO U OTRO")
    private String gender;

    @Min(value = 0, message = "El salario esperado no puede ser negativo")
    private Double salaryExpected;
}

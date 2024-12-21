package com.farevalo.candidates_api_seek.domain.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateCreateDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "El género no puede estar vacío")
    @Pattern(regexp = "^(?i)(MASCULINO|FEMENINO|OTRO)$", message = "El género debe ser MASCULINO, FEMENINO U OTRO")
    private String gender;

    @NotNull(message = "El salario esperado es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El salario esperado debe ser mayor que 0")
    private Double salaryExpected;
}

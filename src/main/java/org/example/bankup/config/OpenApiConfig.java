package org.example.bankup.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        security = @SecurityRequirement(name = "bearerAuth"),
        info = @Info(
                contact = @Contact(
                        name = "Fernando Cruz Cavina",
                        email = "fernando.cruz.cavina@gmail.com",
                        url = "https://github.com/FernandoCruzCavina"
                ),
                title = "BankUp_Project",
                version = "1.0",
                description = "This project is a bank service"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "After logging in successfully, use the token to authorize requests.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}

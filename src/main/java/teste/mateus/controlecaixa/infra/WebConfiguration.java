package teste.mateus.controlecaixa.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods(
        "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD", "TRACE", "CONNECT"
      );
  }

}

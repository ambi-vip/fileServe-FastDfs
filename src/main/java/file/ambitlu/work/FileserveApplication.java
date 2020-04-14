package file.ambitlu.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "file.ambitlu.work.config")
public class FileserveApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileserveApplication.class, args);
    }

}

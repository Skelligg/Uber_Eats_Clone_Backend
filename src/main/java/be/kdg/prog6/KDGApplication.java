package be.kdg.prog6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.Modulith;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.scheduling.annotation.EnableScheduling;

@Modulith
@EnableScheduling
public class KDGApplication {
    private static final Logger log = LoggerFactory.getLogger(KDGApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KDGApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    void onApplicationStarted() {
        ApplicationModules modules = ApplicationModules.of(KDGApplication.class);
        modules.forEach(module -> log.info("MODULE: \n{}", module));
    }
}

package com.ilroberts;

import com.ilroberts.modulith.Application;
import com.tngtech.archunit.junit.AnalyzeClasses;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import java.io.IOException;

@AnalyzeClasses(packages = "com.ilroberts.modulith")
public class SpringModulithTests {

    ApplicationModules modules = ApplicationModules.of(Application.class);

    @Test
    void verifyModularity() throws IOException {
        modules.verify();
    }

    @Test
    void printModuleStructure() throws IOException {
        modules.forEach(System.out::println);
    }

    @Test
    void createDocumentation() {
        new Documenter(Application.class)
                .writeDocumentation(Documenter.DiagramOptions.defaults(), Documenter.CanvasOptions.defaults());
    }
}

package com.example.quiz;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@Tag("architecture")
public class HexagonalArchitectureTest {

    @Test
    void domainMustNotDependOnAdapters() {
        noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..adapter..")
                .check(productionAndTestClasses());
    }

    @Test
    @Disabled
    public void domainMustNotDependOnApplication() {
        noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..application..")
                .check(productionAndTestClasses());
    }

    private JavaClasses productionAndTestClasses() {
        return new ClassFileImporter().importPackages("com.example.quiz");
    }
}

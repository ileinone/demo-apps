package fi.ilkka.demo.heroku;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("fi.ilkka.demo.heroku");

        noClasses()
            .that()
                .resideInAnyPackage("fi.ilkka.demo.heroku.service..")
            .or()
                .resideInAnyPackage("fi.ilkka.demo.heroku.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..fi.ilkka.demo.heroku.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}

package ee.tspace.app

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.Test

class ArchTest {

    @Test
    fun servicesAndRepositoriesShouldNotDependOnWebLayer() {

        val importedClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ee.tspace.app")

        noClasses()
            .that()
                .resideInAnyPackage("ee.tspace.app.service..")
            .or()
                .resideInAnyPackage("ee.tspace.app.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..ee.tspace.app.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses)
    }
}

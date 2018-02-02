# maven projects with external annotations

This project makes use of advanced analysis capabilities of IntelliJ: external annotations.

Remarkable elements for this project
 - [.idea/misc.xml](.idea/misc.xml) contains additional annotation classes for @Nullable and @NonNull (eclipse's)
 - [external-annotations-enabled-maven-project.iml](external-annotations-enabled-maven-project.iml) contains `<annotation-paths>` which 
   directs IntelliJ to search [../intellij-external-annotations](../intellij-external-annotations) for external annotations for 
   the project dependencies (not JDK's).
 

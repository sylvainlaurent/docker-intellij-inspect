# docker image to help running IntelliJ inspections.
[![docker hub](https://img.shields.io/badge/docker-hub-brightgreen.svg)](https://hub.docker.com/r/slaurent/intellij-inspect/)

It includes
- IntelliJ CE 2017.3
- [bentolor/idea-cli-inspector](https://github.com/bentolor/idea-cli-inspector) to launch the inspection and analyze the result
- a predefined `jdk.table.xml` that declares a unique JDK named `1.8` (that can be referenced for analysis)

# How to build the image?

```
cd src/main/docker
docker build --tag docker-intellij-inspect .
```

# How to run?
- (assuming a macOS or Linux machine, it should also work on Windows with some adaptations to the commands)
- open a shell at the root of the IntelliJ project you want to analyze
- either
  - prepare a `.ideainspect` file
  - or think about the arguments to pass to ideainspect. See https://github.com/bentolor/idea-cli-inspector for documentation
- launch a one-off docker container, mapping the following volumes:
  - the idea project to be inspected on the host to the `/home/ijinspector/idea-project` directory in the container
  - the host maven repository to the `/home/ijinspector/.m2/repository` directory in the container. Note that the maven dependencies must have previously been downloaded on the host. If some dependency is not available during the inspection, you'll see errors like `Please configure library 'Maven:....`

Example:

```
docker run -it --rm -v `pwd`:/home/ijinspector/idea-project -v ~/.m2/repository:/home/ijinspector/.m2/repository docker-intellij-inspect
```

(notice the `` `pwd` `` command which allows to have the full path of the current directory as required by docker to map a volume to a directory on the host)

The docker command returns when the inspection and result analysis is done and has the same result code as [bentolor/idea-cli-inspector](https://github.com/bentolor/idea-cli-inspector).

# How to use external annotations
IntelliJ inspections are quite powerful but can be even more so when using external annotations for third-party libraries or the JDK.
Here are the requirements:
- JDK annotations must be available in `/home/ijinspector/idea-jdk-external-annotations` inside the docker container. This path is set in [jdk.table.xml](src/main/docker/jdk.table.xml) that is built in the image.
- annotations for libraries must also be available inside the container. Usually a path relative to the IntelliJ project is used, such as `file://$PROJECT_DIR$/../intellij-external-annotations` (see this [example file](src/test/docker/external-annotations-enabled-maven-project/.idea/libraries/Maven__com_google_guava_guava_20_0.xml).
Thus, you can bind the annotations directory to `/home/ijinspector/intellij-external-annotations` inside the container.

The following `docker-compose.yml` is an example (for a maven project):
```yaml
version: '3'
services:
  inspect:
    image: intellij-inspect:latest
    volumes:
      - .:/home/ijinspector/idea-project:ro
      - ~/.m2/repository:/home/ijinspector/.m2/repository
      - ../intellij-external-annotations:/home/ijinspector/idea-jdk-external-annotations:ro
      - ../intellij-external-annotations:/home/ijinspector/intellij-external-annotations:ro
    command: 
      - --rootfile
      - /home/ijinspector/idea-project/pom.xml
      - --profile
      - CLI_inspection_profile.xml
```

# Future work
- automatically reference external annotations in modules *.xml files if not provided in the inspected repository (e.g. fresh git checkout and files in .gitignore)

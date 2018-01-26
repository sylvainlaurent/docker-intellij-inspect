# docker image to help running IntelliJ inspections.
It includes
- IntelliJ CE 2017.3
- [bentolor/idea-cli-inspector](https://github.com/bentolor/idea-cli-inspector) to launch the inspection and analyze the result
- a predefined `jdk.table.xml` that declares a unique JDK named `1.8` (that can be referenced for analysis)

# How to build the image?

```
docker build --tag docker-intellij-inspect .
```

# How to run?
- (assuming a macOS or Linux machine, it should also work on Windows with some adaptations to the commands)
- open a shell at the root of the IntelliJ project you want to analyze
- either prepare a `.ideainspect` file or think about the arguments to pass to ideainspect. See https://github.com/bentolor/idea-cli-inspector
- launch a one-off docker container, mapping 
-- the idea project on the host to the `/idea-project` directory in the container
-- the local maven repository to the `/root/.m2/repository` directory in the container. Note that the maven dependencies must have previously been downloaded on the host. If some dependency is not available during the inspection, you'll see errors like `Please configure library 'Maven:....`

Examples:

```
docker run -it --rm -v `pwd`:/idea-project -v ~/.m2/repository:/root/.m2/repository docker-intellij-inspect <any additional args to ideainspect>
```

```
docker run -it --rm -v `pwd`:/idea-project -v ~/.m2/repository:/root/.m2/repository docker-intellij-inspect --rootfile /idea-project/pom.xml
```

```
docker run -it --rm -v `pwd`:/idea-project -v ~/.m2/repository:/root/.m2/repository -v `pwd`/../intellij-external-annotations:/intellij-external-annotations -v `pwd`/../intellij-external-annotations:/idea-external-annotations --name ij  docker-intellij-inspect --rootfile /idea-project/pom.xml
```

(notice the `` `pwd` `` command which allows to have the full path of the current directory as required by docker to map a volume to a directory on the host)

The docker command returns when the inspection and result analysis is done and has the same result code as [bentolor/idea-cli-inspector](https://github.com/bentolor/idea-cli-inspector).

# Future work
- make it work with maven projects without committing the .iml and modules.xml files
- provide a way to map external annotations for both JDK and libs

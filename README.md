# docker image to help running IntelliJ inspections.
It includes
- IntelliJ CE 2017.3
- bentolor/ideainspect to launch the inspection and analyze the result
- a predefined `jdk.table.xml` that declares a unique JDK named `1.8` (that can be referenced for analysis)

# How to build the image?

```
docker build --tag docker-intellij-inspect .
```

# How to run?
- open a shell at the root of the IntelliJ project you want to analyze
- either prepare a `.ideainspect` file or think about the arguments to pass to ideainspect. See https://github.com/bentolor/idea-cli-inspector
- launch a one-off docker container, mapping the idea project on the host to the `/idea-project` directory in the container like this:

```
docker run -it --rm -v `pwd`:/idea-project docker-intellij-inspect <any additional args to ideainspect>
```

(notice the `` `pwd` `` command which allows to have the full path of the current directory as required by docker to map a volume to a directory on the host)

The docker command returns when the inspection and result analysis is done and has the same result code as bentolor/ideainspect

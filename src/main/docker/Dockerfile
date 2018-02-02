FROM openjdk:8u151-jdk

RUN curl https://download-cf.jetbrains.com/idea/ideaIC-2017.3.2-no-jdk.tar.gz > /tmp/ideaIC-jdk.tar.gz \
  && tar xzf /tmp/ideaIC-jdk.tar.gz \
  && rm /tmp/ideaIC-jdk.tar.gz \
  && mv idea-IC* idea-IC

RUN curl -L https://dl.bintray.com/groovy/maven/apache-groovy-binary-2.4.13.zip > /tmp/apache-groovy.zip \
  && unzip /tmp/apache-groovy.zip \
  && rm /tmp/apache-groovy.zip \
  && mv groovy-* groovy \
  && curl -L https://github.com/bentolor/idea-cli-inspector/archive/master.zip > /tmp/bentolor.zip \
  && unzip /tmp/bentolor.zip \
  && rm /tmp/bentolor.zip \
  && mv idea-cli-inspector-* idea-cli-inspector

ENV PATH="/groovy/bin:${PATH}"
ENV IDEA_HOME=/idea-IC

COPY jdk.table.xml /root/.IdeaIC2017.3/config/options/jdk.table.xml
COPY entrypoint.sh /
#COPY ideainspect.groovy idea-cli-inspector/

#let's pre-create empty dirs for mounts created by the entrypoint script
RUN mkdir -p /intellij-inspect/idea-project-tmprw \
    && mkdir -p /intellij-inspect/idea-project-ro \
    && mkdir -p /intellij-inspect/idea-project-overlay-workdir

# declare a VOLUME so that its filesystem is not of type overlay so that we can create an overlay in the entrypoint
VOLUME /intellij-inspect
WORKDIR /intellij-inspect/idea-project-tmprw

ENTRYPOINT ["/entrypoint.sh"]

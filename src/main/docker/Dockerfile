FROM openjdk:8u151-jdk

ENV GOSU_VERSION 1.10

RUN apt-get update \
 && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
     vim \
     wget \
 && dpkgArch="$(dpkg --print-architecture | awk -F- '{ print $NF }')" \
 && wget -O /usr/local/bin/gosu "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch" \
 && chmod +x /usr/local/bin/gosu \
 && gosu nobody true \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*
 
RUN groupadd -r ijinspector && useradd --no-log-init --gid ijinspector --home-dir /home/ijinspector --create-home ijinspector

WORKDIR /home/ijinspector

USER ijinspector:ijinspector

ARG IDEA_VERSION=ideaIC-2018.1.1

RUN curl https://download-cf.jetbrains.com/idea/${IDEA_VERSION}-no-jdk.tar.gz > /tmp/ideaIC-jdk.tar.gz \
  && mkdir idea-IC \
  && tar -x -C idea-IC --strip-components=1 -z -f /tmp/ideaIC-jdk.tar.gz \
  && rm /tmp/ideaIC-jdk.tar.gz

RUN curl -L https://dl.bintray.com/groovy/maven/apache-groovy-binary-2.4.13.zip > /tmp/apache-groovy.zip \
  && unzip /tmp/apache-groovy.zip \
  && rm /tmp/apache-groovy.zip \
  && mv groovy-* groovy \
  && curl -L https://github.com/bentolor/idea-cli-inspector/archive/master.zip > /tmp/bentolor.zip \
  && unzip /tmp/bentolor.zip \
  && rm /tmp/bentolor.zip \
  && mv idea-cli-inspector-* idea-cli-inspector

ENV PATH="/home/ijinspector/groovy/bin:${PATH}"
ENV IDEA_HOME="/home/ijinspector/idea-IC"

COPY --chown=ijinspector:ijinspector jdk.table.xml /home/ijinspector/.IdeaIC2018.1/config/options/jdk.table.xml

#let's pre-create empty dirs for mounts created by the entrypoint script
RUN mkdir -p /home/ijinspector/idea-project-tmprw \
    && mkdir -p /home/ijinspector/idea-project \
    && mkdir -p /home/ijinspector/idea-project-overlay-workdir

# declare a VOLUME so that its filesystem is not of type overlay so that we can create an overlay in the entrypoint
VOLUME /home/ijinspector
WORKDIR /home/ijinspector/idea-project-tmprw

#switch to root to launch the entrypoint. it will use gosu to drop down to ijinspector
USER root
COPY entrypoint.sh /

ENTRYPOINT ["/entrypoint.sh"]

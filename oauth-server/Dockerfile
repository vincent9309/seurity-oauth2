FROM registry.wang-guanjia.com:5000/gavin/jre:8u131
LABEL COM=HAPPYLIFEPLAT
LABEL Author=Zhaohua.Yu

#Define jar filename, same as your build target.
ENV JAR_PATH=target/oauth-server.jar

#Define pinpoint agentId and applicationName , agentId should be less than 25 characters
ENV agentId=oauth-server001 applicationName=oauth-server

#Define agent filename
ENV AGENT_NAME=pinpoint-agent-test-pre-prod

#Define some java variable here that what you need
ENV JAVA_OPTS=""
ENV OTHER_JAVA_OPTS="-javaagent:/${AGENT_NAME}/pinpoint-bootstrap-1.6.2.jar -Dpinpoint.agentId=${agentId}"

# Define TimeZone
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#Download pinpoint agent
RUN cd / && curl -o ${AGENT_NAME}.tar.gz http://happylife-prod.oss-cn-shenzhen.aliyuncs.com/tools/apm/${AGENT_NAME}.tar.gz && \
    tar -xf ${AGENT_NAME}.tar.gz && rm -rf ${AGENT_NAME}.tar.gz ;

ADD ${JAR_PATH} /${JAR_PATH}

CMD java ${JAVA_OPTS} ${OTHER_JAVA_OPTS} ${PINPOINT_APPNAME} -Djava.security.egd=file:/dev/./urandom -jar /${JAR_PATH}
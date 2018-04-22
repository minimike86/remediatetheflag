FROM ubuntu
ARG DEBIAN_FRONTEND=noninteractive

RUN	apt-get update && apt-get -y install nginx wget tomcat8 openjdk-8-jdk

COPY	fs/webapps /var/lib/tomcat8/webapps
COPY	run/init.sh /tmp/init.sh
COPY	run/nginx /tmp/nginx

RUN 	["touch","/var/lib/tomcat8/rtf-global.log"]
RUN 	["chown", "-R", "tomcat8:tomcat8", "/var/lib/tomcat8/"]
RUN 	["chmod", "+x", "/tmp/init.sh"]

EXPOSE 80
EXPOSE 443
ENTRYPOINT /tmp/init.sh
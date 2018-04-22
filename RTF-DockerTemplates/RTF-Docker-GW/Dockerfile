#  
# REMEDIATE THE FLAG
# Copyright 2018 - Andrea Scaduto 
# remediatetheflag@gmail.com
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#     http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# 
FROM ubuntu
ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get -y install \
	libcairo2-dev libpng12-dev libossp-uuid-dev libavcodec-dev libavutil-dev libswscale-dev \
	libfreerdp-dev libpango1.0-dev libssh2-1-dev libtelnet-dev libvncserver-dev libssl-dev libpulse-dev libvorbis-dev \
	libwebp-dev nginx wget

RUN wget http://launchpadlibrarian.net/241404989/libturbojpeg_1.4.2-0ubuntu3_amd64.deb && dpkg -i libturbojpeg_1.4.2-0ubuntu3_amd64.deb

RUN apt-get update && \
	apt-get -y install autoconf \
	libtool pkg-config gcc g++ make \
	&& mkdir -p /build-box/

RUN cd /build-box && \
    wget -q --span-hosts http://apache.mirrors.nublue.co.uk/guacamole/0.9.13-incubating/source/guacamole-server-0.9.13-incubating.tar.gz && \
    tar -zxf guacamole-server-0.9.13-incubating.tar.gz && \
    cd guacamole-server-0.9.13-incubating && \
    ./configure --with-init-dir=/etc/init.d && \
    make && \
    make install && \
    update-rc.d guacd defaults && \
    ldconfig && \
    rm -Rf /tmp/*

RUN	apt-get clean && apt-get -y remove && apt-get -y autoremove
RUN	apt-get update && apt-get -y install tomcat8 openjdk-8-jdk

COPY	fs/webapps /var/lib/tomcat8/webapps
COPY	fs/guacamole_home /usr/share/tomcat8/.guacamole
COPY	run/init.sh /tmp/init.sh
COPY	run/nginx /tmp/nginx

RUN 	["chown", "-R", "tomcat8:tomcat8", "/var/lib/tomcat8/"]
RUN 	["chmod", "+x", "/tmp/init.sh"]

EXPOSE 80
EXPOSE 443
ENTRYPOINT /tmp/init.sh
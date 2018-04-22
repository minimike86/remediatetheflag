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
echo "Starting RTF Gateway Service..."

if [ -z "$ELB" ]; then
	echo 'Skipping ELB Configuration...'
	mv /tmp/nginx/notls /etc/nginx/sites-available/default
	/etc/init.d/nginx start
else
	echo 'Performing ELB Configuration...'
	sed -i "s/FQDNTOREPLACE/${DOMAIN}/g" /tmp/nginx/elb
	mv /tmp/nginx/elb /etc/nginx/sites-available/default
	/etc/init.d/nginx start
fi
echo "mysql-password: $MYSQL_GUAC_PASSWORD" >> /usr/share/tomcat8/.guacamole/guacamole.properties
echo "mysql-hostname: mysqlhost" >> /usr/share/tomcat8/.guacamole/guacamole.properties
/etc/init.d/guacd start
mkdir -p /usr/share/tomcat8/logs
/etc/init.d/tomcat8 start
rm /tmp/init.sh
rm -rf /tmp/nginx
tail -F /var/lib/tomcat8/logs/catalina.out
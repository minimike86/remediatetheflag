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

echo "Starting RTF MySQL Service..."
chmod +x /tmp/mysql.sh
/tmp/mysql.sh mysqld &
sleep 50
echo "configuring databases"
if [ ! -z "`mysql -u root -p$MYSQL_ROOT_PASSWORD -qfsBe "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME='guacamole_db'" 3>&1`" ];
	then 
		echo "guacamole_db DATABASE ALREADY EXISTS" 
	else   
		echo "guacamole_db DATABASE DOES NOT EXIST"
		mysql -fu root -p$MYSQL_ROOT_PASSWORD -e "CREATE USER IF NOT EXISTS 'guacamole_user'@'%' IDENTIFIED BY '$MYSQL_GUAC_PASSWORD';"
		mysql -fu root -p$MYSQL_ROOT_PASSWORD < /tmp/sql/init_guac.sql
		mysql -fu guacamole_user guacamole_db -p$MYSQL_GUAC_PASSWORD < /tmp/sql/guac-create-schema.sql
		mysql -fu guacamole_user guacamole_db -p$MYSQL_GUAC_PASSWORD -e "INSERT INTO guacamole_user(username, password_hash, password_salt, password_date) VALUES ('rtfadmin',UNHEX(SHA2(CONCAT('$GUAC_RTFADMIN_PASSWORD','EA38DE4168AF9C99E9CDD77F7AB07E8202554894A50F1B1334CD2579BD9714EA'),256)),x'EA38DE4168AF9C99E9CDD77F7AB07E8202554894A50F1B1334CD2579BD9714EA',NOW());"
		mysql -fu guacamole_user guacamole_db -p$MYSQL_GUAC_PASSWORD < /tmp/sql/guac-create-admin-user.sql
fi
if [ ! -z "`mysql -u root -p$MYSQL_ROOT_PASSWORD -qfsBe "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME='dbRTF'" 3>&1`" ];
	then 
		echo "dbRTF DATABASE ALREADY EXISTS" 
	else   
		echo "dbRTF DATABASE DOES NOT EXIST"
		mysql -fu root -p$MYSQL_ROOT_PASSWORD -e "CREATE USER IF NOT EXISTS 'RTFGlobalDBUser'@'%' IDENTIFIED BY '$MYSQL_GLOBAL_PASSWORD';"
		mysql -fu root -p$MYSQL_ROOT_PASSWORD < /tmp/sql/init_global.sql
fi
if [ ! -z "$SIGNAL_URL" ]; then
	cfn-signal  -e 0 -r "Mysql Service Running" $SIGNAL_URL
	echo "INFO: Notified CloudFormation $SIGNAL_URL"
fi
rm /tmp/init.sh
tail -f /var/log/mysql/error.log
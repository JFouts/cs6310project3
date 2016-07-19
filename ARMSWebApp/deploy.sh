service tomcat6 stop
echo " * Deploying war..."
rm -r /var/lib/tomcat6/webapps/*
cp /home/ubuntu/Project3/cs6310project3/ARMSWebApp/target/ARMSWebApp.war /var/lib/tomcat6/webapps/
service tomcat6 start


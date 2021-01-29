FROM centos:8
#环境配置
RUN yum install -y openssh openssh-clients openssh-server which net-tools
RUN ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa && cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys && chmod 0600 ~/.ssh/authorized_keys
RUN yum install -y java-1.8.0-openjdk-devel

#安装Hadoop
COPY ./hadoop-2.9.2.tar.gz /tmp/tar/hadoop-2.9.2.tar.gz
RUN cd /tmp/tar/ && tar -zxf hadoop-2.9.2.tar.gz -C /opt && rm -f hadoop-2.9.2.tar.gz
COPY ./.bashrc /root/.bashrc
COPY ./core-site.xml /opt/hadoop-2.9.2/etc/hadoop/core-site.xml
COPY ./hdfs-site.xml /opt/hadoop-2.9.2/etc/hadoop/hdfs-site.xml
COPY ./mapred-site.xml /opt/hadoop-2.9.2/etc/hadoop/mapred-site.xml
COPY ./yarn-site.xml /opt/hadoop-2.9.2/etc/hadoop/yarn-site.xml

##安装HBase
#COPY ./hbase-2.3.1-bin.tar.gz /tmp/tar/hbase-2.3.1-bin.tar.gz
#RUN cd /tmp/tar/ && tar -zxf hbase-2.3.1-bin.tar.gz -C /opt && rm -f hbase-2.3.1-bin.tar.gz
##修改$HBASE_HOME/conf/hbase-env.sh文件，添加JAVA_HOME配置
#COPY ./hbase-env.sh /opt/hbase-2.3.1/conf/hbase-env.sh

#安装PostgreSQL
#RUN yum install -y @postgresql:12

WORKDIR /opt

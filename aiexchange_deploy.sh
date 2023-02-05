#! /bin/bash

JAVA_PARAM="-Xmx512m -Xms512m"
service_name="ai-exchange"
service_version=1.0.0

# 输出执行脚本名称
echo "start to execute script ${0}"

# 镜像名称
image_name="erip/${service_name}"


echo "service name -> ${service_name}"


# 停止正在运行的服务
echo "stop the current docker of ${service_name}"
sudo docker stop ${service_name}

# 删除服务容器
echo "del container of ${service_name}"
sudo docker rm -f ${service_name}

# 删除服务镜像
echo "del image of ${image_name}:latest"
sleep 5s
sudo docker rmi -f ${image_name}:service_version


# 创建新版本镜像
echo "up image of ${image_name}:latest"
sudo docker-compose up -d --build


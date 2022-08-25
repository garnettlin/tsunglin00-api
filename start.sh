#img_output='fuck-deng'        #生成鏡像標籤
#appname='fuck-deng'           #容器名稱
img_output='gcr.io/macro-resource-353407/tsunglin00:latest'        #生成鏡像標籤
appname='gcr.io/macro-resource-353407/tsunglin00:latest'           #容器名稱
port=28019                       #docker暴露端口


# 獲得docker容器 id和鏡像 id
r_c=`docker ps -a | grep "$appname" | awk '{print $1 }'`
c=`docker ps -a | grep "$appname" | awk '{print $1 }'`
r_img=`docker images | grep "$appname" | awk '{print $3 }'`
# 如果容器正在運行，停止它
if [ "$r_c"x != ""x ]; then
    docker stop "$r_c"
fi
# 刪除容器
if [ "$c"x != ""x ]; then
    docker rm "$c"
fi
# 刪除鏡像
if [ "$r_img"x != ""x ]; then
    docker rmi "$r_img"
fi

# 生成鏡像
docker build -t $img_output .
#docker build -t gcr.io/macro-resource-353407/tsunglin00:latest .
docker -- push $img_output

# 日誌目錄
mkdir -p $PWD/logs
chmod 777 $PWD/logs

# 啟動鏡像  8100為工程的端口
#docker run -d --name $appname -p $port:28019 $img_output
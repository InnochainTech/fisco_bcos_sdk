#!/bin/bash
function httpRequest()
{
    #curl 请求
    info=`curl -s -m 10 --connect-timeout 10 -I $1`

    #获取返回码
    code=`echo $info|grep "HTTP"|awk '{print $2}'`
    #对响应码进行判断
    if [ "$code" == "200" ];then
        echo "程序正常运行，$code"
    else
        echo "程序访问失败，正在重启"
        sh  run.sh restart
    fi
}

httpRequest "http://120.24.41.96:8800/heartbeat"
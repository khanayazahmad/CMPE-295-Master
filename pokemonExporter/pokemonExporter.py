import csv
import requests
import sys
from datetime import datetime
import time
import math
import json

starttime=time.time()


namespace_video_streamer="videostreamer"
namespace_video_uploader="videouploader"
namespace_datamanagement="datamanager"
namespace_prometheus="prometheus"
namespace_grafana="grafana"

namespace_array=[namespace_video_streamer,namespace_video_uploader,namespace_datamanagement,namespace_prometheus,namespace_grafana]

hostname='http://localhost:8080/api/v1/query'
while True:
  namespace_index=0
  for namespace in namespace_array:
    metrics_response_memory = requests.get(hostname,params={'query':'sum(container_memory_working_set_bytes{namespace="'+str(namespace)+'"})/sum(machine_memory_bytes)*100'})
    metrics_memory = metrics_response_memory.json()['data']['result']
    for metric in metrics_memory:
      memory_percentage = ("".join(metric['value'][1]))
    result_memory=memory_percentage.split(".")[0]

    metrics_response_cpu = requests.get(hostname,params={'query':'sum(rate(container_cpu_usage_seconds_total{namespace="'+str(namespace)+'"}[5m]))/sum(machine_cpu_cores)*100'})
    metrics_cpu = metrics_response_cpu.json()['data']['result']
    for metric in metrics_cpu:
      cpu_percentage = ("".join(metric['value'][1]))
    result_cpu=cpu_percentage.split(".")[0]

    metrics_response_latency = requests.get(hostname,params={'query':'rate(http_request_duration_microseconds_sum[5m])/rate(http_request_duration_microseconds_count[5m])'})
    metrics_latency = metrics_response_latency.json()['data']['result']
    for metric in metrics_latency:
      latency_ms = ("".join(metric['value'][1]))
    result_latency=(int)(latency_ms.split(".")[0])
    result_latency=(int)(math.ceil(math.ceil(result_latency)/1000))

    metrics_response_bandwidth = requests.get(hostname,params={'query':'rate(node_network_transmit_bytes_total[5m])'})
    metrics_bandwidth = metrics_response_bandwidth.json()['data']['result']
    for metric in metrics_bandwidth:
      bandwidth = ("".join(metric['value'][1]))
    result_bandwidth=(int)(bandwidth.split(".")[0])
    result_bandwidth=(int)(math.ceil(math.ceil(result_bandwidth)/1000))

    metrics_response_pod = requests.get(hostname,params={'query':'count(kube_pod_created{namespace="'+str(namespace)+'"})'})
    metrics_pod = metrics_response_pod.json()['data']['result']
    for metric in metrics_pod:
      pod_count = ("".join(metric['value'][1]))
    res_pod_count=(int)(pod_count.split(".")[0])

    metrics_response_totalrequests = requests.get(hostname,params={'query':'count(http_requests_total)'})
    metrics_total_requests = metrics_response_totalrequests.json()['data']['result']
    for metric in metrics_total_requests:
      total_request_count = ("".join(metric['value'][1]))
    res_request_count=(int)(total_request_count.split(".")[0])

    millis = int(round(time.time() * 1000))
    result_time=millis
    result_latency=result_latency*10
    url = "http://<IP>:8802/metrics"
    payload = {"body":{"timestamp": result_time,
        "microserviceId": namespace_index,
        "memoryUsage": result_memory,
        "cpuUsage": result_cpu,
        "numberOfRequests": res_request_count,
        "numberOfPods": 9,
        "latency":result_latency ,
        "bandwidth":90}}
    header = {"content-type": "application/json"}
    r = requests.post(url, data=json.dumps(payload), headers=header)

    ## load prediction

    prediction_url="http://<IP>:8803/loadprediction/"+str(namespace_index)
    prediction_response = requests.get(prediction_url,params={'startTime':0,'endTime':result_time+1000})
    prediction_response = prediction_response.json()
    
    result_prediction=(int)(prediction_response['predictionResult'])

    url="http://<IP>:8802/predictions"
    payload = {"body":{"timestamp": result_time,
        "microserviceId": namespace_index,
        "predictionResult":result_prediction}}
    header = {"content-type": "application/json"}
    r = requests.post(url, data=json.dumps(payload), headers=header)

    time.sleep(60.0 - ((time.time() - starttime) % 60.0))
    namespace_index=namespace_index+1

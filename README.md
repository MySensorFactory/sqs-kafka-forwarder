# SQS Kafka forwarder

## Description 

This module is responsible for forwarding raw sensors data from AWS SQS queues to Kafka topics. 

## Configuration

### SQS configuration:

* sqs.{sensorTypeQueue}
* Specifies the name of the Amazon SQS source queue for sensor-related data.


* sqs.isLocal
* This property determines when this module is launched on EC2 instance. If `true` then automatically configures internal AWS SDK from EC2 settings. If `false` then it's launched locally then and  requires access key and secret key. 

#### Available {sensorTypeQueue} values:
* pressureQueue
* temperatureQueue
* vibrationQueue
* humidity

### Kafka configuration:

* kafka.sensorConfig.{sensor-type}.topicName
* Specifies the Kafka topic for sensor-related data.


* kafka.sensorConfig.{sensor-type}.clientId
* Specifies the client ID for the Kafka producer/consumer handling sensor data.

#### Available {sensor-type} values:
* pressure
* temperature
* humidity
* compressorState
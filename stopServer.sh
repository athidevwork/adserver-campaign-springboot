#!/bin/bash

process=`ps | grep java | awk '{print $1}'`

CMD="kill -9 $process"
echo $CMD

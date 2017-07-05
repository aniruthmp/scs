#!/bin/bash

for i in {100..102}
do
   echo "Executing $i / 102 "
   curl "https://ani-customer-service.cfapps.io/customer?customerNumber=$i"
done

echo "Sleeping 3s..."
sleep 3s

for i in {105..200}
do
   echo "Executing $i / 200 "
   curl "https://ani-customer-service.cfapps.io/customer?customerNumber=$i"
done

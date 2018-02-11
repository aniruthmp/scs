#!/bin/bash

for i in {100..102}
do
   echo "Executing $i / 102 "
   curl "https://ap-customer-service.cfapps.pez.pivotal.io/customer/search/findByCustomerNumber?customerNumber=$i"
done

echo "Sleeping 3s..."
sleep 3s

for i in {105..200}
do
   echo "Executing $i / 200 "
   curl "https://ap-customer-service.cfapps.pez.pivotal.io/customer/search/findByCustomerNumber?customerNumber=$i"
done

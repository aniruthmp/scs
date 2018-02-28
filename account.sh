#!/bin/bash

for i in {100..200}
do
   echo "Executing $i / 200 "
   curl "https://ap-account-service.cfapps.io/accounts/search/findByCustomerNumber?customerNumber=$i"
done

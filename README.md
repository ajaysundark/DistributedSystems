# Distributed Systems - Homework assignment
## partA : TCP based client-server model
## partB : Apache Thrift based implementation of a client-server example implementation 

/* CSci5105 Spring 2017
* Assignment# 3
* name: Ajay Sundar Karuppasamy, Anas Saeed
* student id: karup002, saeed044
* x500 id: 5298653, <id2 (optional)>
* CSELABS machine: csel-kh4250-03.cselabs.umn.edu
*/

## Part - A
1. make inside partA directory
2. run server : java Server 9600
3. run clients : java BankClient localhost 9600 100 100

## Part - B
1. generate the required java files from thrift IDL
   thrift -r -out . --gen java BankService.thrift
   
2. run make
3. run server: java BankServer 7100
4. run client: java BankClient localhost 7100 100 100

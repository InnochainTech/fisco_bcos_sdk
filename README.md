# Fisco Bcos Sdk 

[![license](https://img.shields.io/github/license/:user/:repo.svg)](LICENSE)
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

English / [中文](./README_CN.md)

APIs for operations that need to send transactions such as data on the chain

## Table of Contents

- [Background](#background)
- [Directory Structure](#Directory Structure)
- [API](#api)
- [Profile introduction](#Profile introduction)
- [License](#license)

## Background

Aiming at the contract method of the NFT member system and the needs of back-end services, use this system for docking.


## Code Directory Structure

```
.
├── java
│     └── inno
│         └─── fisco
│              └── bcos
│                  └── be
│                      ├── MainApplication.java
│                      ├── configuration
│                      │     ├── CorsConfig.java
│                      │     ├── GlobalExceptionHandler.java
│                      │     ├── MQConsumerConfiguration.java
│                      │     ├── MQProducerConfiguration.java
│                      │     └── SwaggerConfig.java
│                      ├── constant
│                      │     ├── Config.java
│                      │     ├── Constant.java
│                      │     └── MQConstant.java
│                      ├── controller
│                      │     ├── EncrypeController.java
│                      │     ├── HeartBeatController.java
│                      │     ├── MQNFTController.java
│                      │     ├── MQTestNFTController.java
│                      │     ├── NFTController.java
│                      │     ├── NFTGetController.java
│                      │     ├── UserController.java
│                      │     └── WalletController.java
│                      ├── entity
│                      │     ├── RequestDo.java
│                      │     ├── ResVo.java
│                      │     ├── ResponseVo.java
│                      │     ├── Wallet.java
│                      │     ├── request
│                      │     │     ├── AddIssuaDo.java
│                      │     │     ├── BatchBurnDo.java
│                      │     │     ├── BatchMintDo.java
│                      │     │     ├── BatchSellDo.java
│                      │     │     ├── BatchTransferDo.java
│                      │     │     ├── InitWriteOffDo.java
│                      │     │     ├── NFTDeployDo.java
│                      │     │     ├── RenewDo.java
│                      │     │     └── WriteOffDo.java
│                      │     ├── response
│                      │     │     ├── AddIssuaVo.java
│                      │     │     ├── BatchBurnVo.java
│                      │     │     ├── BatchMintVo.java
│                      │     │     ├── BatchSellVo.java
│                      │     │     ├── BatchTransferVo.java
│                      │     │     ├── NFTDeployVo.java
│                      │     │     ├── RenewVo.java
│                      │     │     └── WriteOffVo.java
│                      │     └── usesign
│                      │         ├── ReqVo.java
│                      │         ├── request
│                      │         │     ├── BatchBurnReq.java
│                      │         │     ├── BatchMintReq.java
│                      │         │     ├── BatchSellReq.java
│                      │         │     ├── BatchTransferReq.java
│                      │         │     ├── InitWriteOffReq.java
│                      │         │     ├── NFTDeploy.java
│                      │         │     ├── NewUserReq.java
│                      │         │     ├── RenewReq.java
│                      │         │     └── WriteOffReq.java
│                      │         └── response
│                      │             ├── ResNFTDeploy.java
│                      │             └── ResNewUser.java
│                      ├── listener
│                      │     ├── ReqBurnListener.java
│                      │     ├── ReqDeployListener.java
│                      │     ├── ReqMintListener.java
│                      │     ├── ReqRenewListener.java
│                      │     ├── ReqSellListener.java
│                      │     ├── ReqTransferListener.java
│                      │     └── ReqWriteoffListener.java
│                      ├── service
│                      │     ├── MQNFTService.java
│                      │     ├── RocketMsgService.java
│                      │     └── impl
│                      │         ├── MQNFTServiceImpl.java
│                      │         └── RocketMsgServiceImpl.java
│                      ├── transaction
│                      │     ├── AssembleTransactionDemo.java
│                      │     ├── BasicAbiTransaction.java
│                      │     ├── ISignTransaction.java
│                      │     ├── ISignedTransactionCallback.java
│                      │     ├── KeyToolSignTransaction.java
│                      │     ├── LegoTransaction.java
│                      │     ├── LegoTransactionDemo.java
│                      │     └── MyAssembleTransactionProcessor.java
│                      └── util
│                          ├── AddressUtils.java
│                          ├── AesUtils.java
│                          ├── CommonUtils.java
│                          ├── EncrypeUtils.java
│                          ├── ExtendsStringUtils.java
│                          ├── FileUtils.java
│                          ├── OkHttpUtils.java
│                          ├── chain
│                          │     ├── BcosClientWrapper.java
│                          │     ├── MQNFTBcosClientWrapper.java
│                          │     └── NFTBcosClientWrapper.java
│                          ├── result
│                          │     └── Result.java
│                          └── validate
│                              ├── ErrorInfo.java
│                              ├── ErrorInfos.java
│                              └── ValidateUtils.java
├── resources
│     ├── META-INF
│     ├── application-dev.yml
│     ├── application-prod.yml
│     ├── application-test.yml
│     ├── application.properties
│     ├── application.yml
│     ├── conf (sdk证书)
│     │     ├── ca.crt
│     │     ├── sdk.crt
│     │     └── sdk.key
│     ├── contract
│     │     ├── abi
│     │     │     └── NFT.abi
│     │     └── bin
│     │         └── NFT.bin
│     ├── fisco-config-dev.xml
│     ├── fisco-config-test.xml
│     ├── fisco-config.xml
│     └──  logback-spring.xml
└──Tree
```

## API

### Online environment
Online API:[http://119.23.104.135:8800/doc.html#/home]("http://119.23.104.135:8800/doc.html#/home")

### Local environment
Local API:[http://localhost:8800/doc.html#/home]("http://localhost:8800/doc.html#/home")


## Profile introduction 

- encrype:
  - key: EfdsW23D23d3df43 Encryption key (this key is used in two places: 1. When the message is sent to the signature service, it needs
    One to one in the signature service; 2.v1.0 is the key to encrypt the user's private key when interacting with the back-end service)

- filePath:
  - abi: src/main/resources/contract/abi/（The directory where the ABI of the contract is stored, needs to be redefined in the deployment environment）
  - bin: src/main/resources/contract/bin/（The directory where the BIN of the contract is stored, needs to be redefined in the deployment environment）

- bcos:
  - config: classpath:fisco-config-dev.xml
  - groupId: 1
  - encryptType: 0 （Encryption type 0 default 1 country secret）
  - bin: contract/bin/NFT.bin
  - abi: contract/abi/NFT.abi
  - chainId: 1
  

## License

[MIT © Richard McRichface.](../LICENSE)

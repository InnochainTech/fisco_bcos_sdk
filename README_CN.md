# fisco_bcos_sdk 

[![license](https://img.shields.io/github/license/:user/:repo.svg)](LICENSE)
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)


[English](./README.md) / 中文

针对数据上链等需要发送交易的操作的api

## 目录

- [背景](#背景)
- [目录结构](#目录结构)
- [API](#api)
- [配置文件说明](#配置文件说明)
- [License](#license)

## 背景

Aiming at the contract method of the NFT member system and the needs of back-end services, use this system for docking.


## 代码目录结构

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

### 线上环境
Online API:[http://119.23.104.135:8800/doc.html#/home]("http://119.23.104.135:8800/doc.html#/home")

### 本地环境
Local API:[http://localhost:8800/doc.html#/home]("http://localhost:8800/doc.html#/home")


## 配置文件说明

- encrype:
  - key: EfdsW23D23d3df43 加密key(此密钥有两个地方使用1.在消息发送至签名服务时，需要
    与签名服务中的一至；2.v1.0版本中 与后端服务交互时对用户私钥加密的密钥)

- filePath:
  - abi: src/main/resources/contract/abi/（存放合约的ABI的目录，部署环境下需要重新定义）
  - bin: src/main/resources/contract/bin/（存放合约的BIN的目录，部署环境下需要重新定义）

- bcos:
  - config: classpath:fisco-config-dev.xml
  - groupId: 1
  - encryptType: 0 （加密类型 0默认 1国密）
  - bin: contract/bin/NFT.bin
  - abi: contract/abi/NFT.abi
  - chainId: 1

## License

[MIT © Richard McRichface.](../LICENSE)
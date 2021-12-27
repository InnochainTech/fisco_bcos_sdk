# fisco_bcos_sdk 

[English](./README.md) / 中文

针对数据上链等需要发送交易的操作的api

## Table of Contents

- [Security](#security)
- [Background](#background)
- [Install](#install)
- [Usage](#usage)
- [API](#api)
- [Contributing](#contributing)
- [License](#license)

## Security

### Any optional sections

## Background

### Any optional sections

## Install

This module depends upon a knowledge of [Markdown]().

```
```

### Any optional sections

## Usage

```
```

Note: The `license` badge image link at the top of this file should be updated with the correct `:user` and `:repo`.

### Any optional sections

## API

### Any optional sections

## More optional sections

## Contributing

See [the contributing file](CONTRIBUTING.md)!

PRs accepted.

Small note: If editing the Readme, please conform to the [standard-readme](https://github.com/RichardLitt/standard-readme) specification.

### Any optional sections

## License

[MIT © Richard McRichface.](../LICENSE)


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
package inno.fiscobcos.be.constant;

/**
 * @Description: rabbitMQ队列路由常量类
 * @Author zack
 * @Date 2021/9/13
 */
public class MQConstant {
    public static final String CHAIN_REQUEST_TOPIC = "chain-request-topic";
    public static final String CHAIN_RESPONSE_TOPIC = "chain-response-topic";

    // matic erc20 request
    public static final String ERC20_MINT_TAG = "matic-erc20-mint";
    public static final String ERC20_BATCH_TRANSFER_TAG = "matic-erc20-batchTransfer";
    public static final String ERC20_BURN_TAG = "matic-erc20-burn";

    // matic erc721 request
    public static final String ERC721_PREMINT_TAG = "matic-erc721-preMint";
    public static final String ERC721_BATCH_MINT_TAG = "matic-erc721-batchMint";
    public static final String ERC721_BATCH_TRANSFER_TAG = "matic-erc721-batchTransfer";
    public static final String ERC721_TRANSFER_TAG = "matic-erc721-transfer";
    public static final String ERC721_UPGRADE_NFT_BY_ID_TAG = "matic-erc721-upgradeNFTById";


    // matic erc20 response
    public static final String ERC20_MINT_RESPONSE_TAG = "matic-erc20-mint-response";
    public static final String ERC20_BATCH_TRANSFER_RESPONSE_TAG = "matic-erc20-batchTransfer-response";
    public static final String ERC20_BURN_RESPONSE_TAG = "matic-erc20-burn-response";

    // matic erc721 response
    public static final String ERC721_PREMINT_RESPONSE_TAG = "matic-erc721-preMint-response";
    public static final String ERC721_BATCH_MINT_RESPONSE_TAG = "matic-erc721-batchMint-response";
    public static final String ERC721_BATCH_TRANSFER_RESPONSE_TAG = "matic-erc721-batchTransfer-response";
    public static final String ERC721_TRANSFER_RESPONSE_TAG = "matic-erc721-transfer-response";
    public static final String ERC721_UPGRADE_NFT_BY_ID_RESPONSE_TAG = "matic-erc721-upgradeNFTById-response";
}

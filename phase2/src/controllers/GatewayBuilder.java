package controllers;
import gateways.*;

import java.io.IOException;

public class GatewayBuilder {
    public AdminAccountGateways getAdminAccountGateways(String filepath) throws IOException, ClassNotFoundException {
        return new AdminAccountGateways(filepath);
    }
    public AdminMessageGateway getAdminMessageGateways(String filepath) throws IOException, ClassNotFoundException {
        return new AdminMessageGateway(filepath);
    }
    public UserGateway getUserGateway(String filepath) throws IOException, ClassNotFoundException {
        return new UserGateway(filepath);
    }
    public UserTradesGateway getUserTradesGateway(String filepath) throws IOException, ClassNotFoundException {
        return new UserTradesGateway(filepath);
    }
    public GlobalWishlistGateway getGlobalWishlistGateway(String filepath) throws IOException, ClassNotFoundException {
        return new GlobalWishlistGateway(filepath);
    }
    public GlobalInventoryGateways getGlobalInventoryGateways(String filepath) throws IOException, ClassNotFoundException {
        return new GlobalInventoryGateways(filepath);
    }


}

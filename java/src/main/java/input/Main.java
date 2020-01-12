package input;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Main {
public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    var options = new DeploymentOptions();
    vertx.deployVerticle(new Controller(), options);
}
}

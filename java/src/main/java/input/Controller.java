package input;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class Controller extends AbstractVerticle {
@Override
public void start() {
    Router r = Router.router(vertx);
    r.route("/haha").handler(ctx -> {
        HttpServerRequest req = ctx.request();
        int one = Integer.parseInt(req.getParam("one"));
        int two = Integer.parseInt(req.getParam("two"));
        ctx.response().end(new JsonObject().put("one", one).put("two", two).put("sum", one + two).toString());
    });
    r.route("/solve").handler(ctx->{
       HttpServerRequest req=ctx.request();

    });
    vertx.createHttpServer().requestHandler(r).listen(9080, (server) -> {
        System.out.println("listening at http://locahost:8080/haha?one=1&two=2");
    });
}

public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Controller());
}
}
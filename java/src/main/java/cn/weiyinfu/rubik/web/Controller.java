package cn.weiyinfu.rubik.web;

import cn.weiyinfu.rubik.cube.OperationList;
import cn.weiyinfu.rubik.diamond.solvers.SearchSolverCube;
import cn.weiyinfu.rubik.diamond.solvers.SearchSolverDiamondSimple;
import cn.weiyinfu.rubik.three.min2phase.Min2PhaseSolver;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.impl.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

public class Controller extends AbstractVerticle {
    static Logger log = LoggerFactory.getLogger(Controller.class);
    public static int port = 9080;
    SearchSolverCube miniSolver = new SearchSolverCube(2, 7, 3);
    Min2PhaseSolver rubikSolver = new Min2PhaseSolver();
    SearchSolverDiamondSimple diamondSolver = new SearchSolverDiamondSimple(3, 7, 3);

    final String TEXT = "text/plain;charset=UTF-8";

    @Override
    public void start() {
        Router r = Router.router(vertx);
        r.get("/haha").handler(ctx -> {
            HttpServerRequest req = ctx.request();
            int one = Integer.parseInt(req.getParam("one"));
            int two = Integer.parseInt(req.getParam("two"));
            ctx.response().end(new JsonObject().put("one", one).put("two", two).put("sum", one + two).toString());
        });
        r.get("/debug").handler(ctx -> {
            String file = Utils.pathOffset("index.html", ctx);
            ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, TEXT).end(
                    "currentDirectory" + Paths.get(".").toAbsolutePath().toString()
                            + "\n"
                            + "index.html:" + file
            );
        });
        r.get("/api/solve").handler(ctx -> {
            System.out.println("solve is reached");
            HttpServerRequest req = ctx.request();
            int N = Integer.parseInt(req.getParam("n"));
            String state = req.getParam("q");
            log.info(String.format("got question:%s,%s", N, state));
            String ans = "";
            if (N == 2) {
                ans = miniSolver.solve(state);
                ans = ans.replaceAll("[1,]", "");
                ans = new OperationList(ans).toFormatString();
            } else if (N == 3) {
                ans = rubikSolver.solve(state);
                ans = new OperationList(ans).toFormatString();
            } else if (N == 103) {
                //三阶四面体魔方
                log.info("三阶四面体魔方");
                try {
                    ans = diamondSolver.solve(state);
                } catch (Exception e) {
                    ans = "求解失败";
                    log.error("求解失败", e);
                }
            } else {
                log.error("error N" + N);
                throw new RuntimeException("error N");
            }
            ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, TEXT).end(ans);
        }).failureHandler(x -> {
            x.failure().printStackTrace();
            log.error("求解失败", x.failure());
        });
        var staticHandler = StaticHandler.create();
        staticHandler.setAllowRootFileSystemAccess(true);
        staticHandler.setAlwaysAsyncFS(true);
        staticHandler.setCachingEnabled(false);
        r.route("/*").handler(staticHandler);
        vertx.createHttpServer().requestHandler(r).listen(port, "0.0.0.0", (server) -> {
            System.out.println("listening at http://localhost:" + port + "/haha?one=1&two=2");
        });
    }

    public static void main(String[] args) {
        System.out.println(Controller.class.getCanonicalName());
        Launcher.main(new String[]{
                "run",
                Controller.class.getCanonicalName(),
                "--redeploy=**/*",
                "--launcher-class=io.vertx.core.Launcher"});
    }
}
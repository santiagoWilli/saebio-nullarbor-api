import com.beust.jcommander.JCommander;
import handlers.*;
import utils.Arguments;

import static spark.Spark.*;
import static spark.Spark.stop;

public class Application {
    private static JCommander jCommander;

    public static void main(String[] args) {
        Arguments options = new Arguments();
        jCommander = JCommander.newBuilder()
                .addObject(options)
                .build();
        jCommander.parse(args);

        port(options.port);

        if (options.help) printHelp();
        if (options.saebioApi == null) abnormalExit();

        post("/trim", new TrimHandler(options.saebioApi));
        path("/analysis", () -> {
            post("", new CreateAnalysisHandler());
            patch("/:token", new AnalysisFileHandler());
            //post("/:token", new TrimHandler(options.saebioApi));
        });
    }

    private static void printHelp() {
        jCommander.usage();
        stop();
        System.exit(0);
    }

    private static void abnormalExit() {
        System.out.println("Debes proporcionar la url de saebio-genome-sequencing-api.\n" +
                " --help para obtener ayuda.");
        stop();
        System.exit(1);
    }
}

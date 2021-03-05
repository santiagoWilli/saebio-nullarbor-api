package utils;

import com.beust.jcommander.Parameter;

public class Arguments {
    @Parameter(names = {"-p", "--port"}, description = "Application port", order = 1)
    public Integer port = 4567;

    @Parameter(names = {"-sa", "--saebio-api"}, description = "Saebio genome sequencing endpoint", order = 0)
    public String saebioApi;

    @Parameter(names = "--help", help = true)
    public boolean help;
}

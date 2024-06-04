package org.scratchgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.scratchgame.config.ConfigurationReader;
import org.scratchgame.model.Config;
import org.scratchgame.model.Result;
import org.scratchgame.service.GameService;
import org.scratchgame.service.MatrixGeneratorService;
import org.scratchgame.service.RewardCalculatorService;
import org.scratchgame.service.implementation.GameServiceImpl;
import org.scratchgame.service.implementation.MatrixGeneratorServiceImpl;
import org.scratchgame.service.implementation.RewardCalculatorServiceImpl;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        try {

            if (args.length != 4) {
                printErrorAndExit("Incorrect number of arguments.");
            }

            var configFilePath = args[0];
            var bettingAmount = args[2];

            if (configFilePath.equals("--config")) {
                configFilePath = args[1];
            } else {
                printErrorAndExit("Missing or incorrect --config argument.");
            }

            if (bettingAmount.equals("--betting-amount")) {
                bettingAmount = args[3];
            } else {
                printErrorAndExit("Missing or incorrect --betting-amount argument.");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            ConfigurationReader configurationReader = new ConfigurationReader(objectMapper);
            Config gameConfig = configurationReader.readConfig(configFilePath);


            MatrixGeneratorService matrixGeneratorService = new MatrixGeneratorServiceImpl();
            RewardCalculatorService rewardCalculatorService = new RewardCalculatorServiceImpl();
            GameService gameService = new GameServiceImpl(gameConfig, matrixGeneratorService, rewardCalculatorService);
            Result result = gameService.start(Integer.parseInt(bettingAmount));
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String resultJson = ow.writeValueAsString(result);
            System.out.println(resultJson);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    private static void printErrorAndExit(String errorMessage) {
        System.err.println(errorMessage);
        System.err.println("""
                Help: java -jar  target/scratch-game-1.0.jar --config <config.json> --betting-amount <betAmount>
                """);
        System.exit(1);
    }
}
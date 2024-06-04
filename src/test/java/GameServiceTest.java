import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.scratchgame.model.Config;
import org.scratchgame.model.Result;
import org.scratchgame.service.GameService;
import org.scratchgame.service.MatrixGeneratorService;
import org.scratchgame.service.RewardCalculatorService;
import org.scratchgame.service.implementation.GameServiceImpl;
import org.scratchgame.service.implementation.RewardCalculatorServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GameServiceTest {
    @Mock
    private MatrixGeneratorService matrixGeneratorService;

    private GameService gameService;
    private RewardCalculatorService rewardCalculatorService;

    private Config gameConfig;


    @Before
    public void init() throws IOException {
        MockitoAnnotations.openMocks(this);
        ObjectMapper mapper = new ObjectMapper();

        this.gameConfig = mapper.readValue(new File("src/test/resources/test-config.json"), Config.class);
        this.rewardCalculatorService = new RewardCalculatorServiceImpl();
        this.gameService = new GameServiceImpl(gameConfig,matrixGeneratorService,rewardCalculatorService);


    }

    @Test
    public  void  MatrixWith3AsAnd10xShouldReturn50000RewardWhenBetis100(){
        String testMatrix [][] = {
                {"A", "B", "C"},
                {"D", "A", "F"},
                { "A", "B", "10x"}
        };

        Mockito.when(matrixGeneratorService.generateMatrix(Mockito.any())).thenReturn(testMatrix);
        int expectedReward = 50000;
        Map<String, List<String>> expectedAppliedCombinations = Map.of("A", List.of("same_symbol_3_times"));
        String expectedAppliedBonus = "10x";


        Result result = gameService.start(100);
        Assert.assertEquals(result.reward(), expectedReward);
        Assert.assertEquals(result.appliedWinningCombinations(), expectedAppliedCombinations);
        Assert.assertEquals(result.appliedBonus(), expectedAppliedBonus);


    }

    @Test
    public  void  MatrixWith3AsAndBsAnd10xShouldReturn75000RewardWhenBetIs100(){
        String testMatrix [][] = {
                {"A", "B", "C"},
                {"D", "A", "B"},
                { "A", "B", "10x"}
        };

        Mockito.when(matrixGeneratorService.generateMatrix(Mockito.any())).thenReturn(testMatrix);
        int expectedReward = 75000;
        Map<String, List<String>> expectedAppliedCombinations = Map.of("A", List.of("same_symbol_3_times"),
                "B", List.of("same_symbol_3_times") );
        String expectedAppliedBonus = "10x";

        Result result = gameService.start(100);
        Assert.assertEquals(expectedReward, result.reward());
        Assert.assertEquals(result.appliedWinningCombinations(), expectedAppliedCombinations);
        Assert.assertEquals(result.appliedBonus(), expectedAppliedBonus);

    }

    @Test
    public  void  MatrixWith5DsAnd3BsAndPlus1000ShouldReturn3500RewardWhenBetIs100(){
        String testMatrix [][] = {
                {"D", "B", "D"},
                {"D", "D", "B"},
                { "B", "D", "+1000"}
        };

        Mockito.when(matrixGeneratorService.generateMatrix(Mockito.any())).thenReturn(testMatrix);
        int expectedReward = 4500;
        Map<String, List<String>> expectedAppliedCombinations = Map.of("D", List.of("same_symbol_5_times"), "B", List.of("same_symbol_3_times") );
        String expectedAppliedBonus = "+1000";

        Result result = gameService.start(100);
        Assert.assertEquals(expectedReward, result.reward());
        Assert.assertEquals(result.appliedWinningCombinations(), expectedAppliedCombinations);
        Assert.assertEquals(result.appliedBonus(), expectedAppliedBonus);

    }

    @Test
    public  void  MatrixWithLessThan3sameSymbolShouldReturn0RewardWhenBetIs100(){
        String testMatrix [][] = {
                {"A", "B", "C"},
                {"D", "A", "F"},
                { "E", "D", "+1000"}
        };

        Mockito.when(matrixGeneratorService.generateMatrix(Mockito.any())).thenReturn(testMatrix);
        int expectedReward = 0;

        Result result = gameService.start(100);
        Assert.assertEquals(expectedReward, result.reward());
        Assert.assertTrue(result.appliedWinningCombinations().isEmpty());
       Assert.assertEquals(result.appliedBonus(), "");

    }

    @Test
    public  void  MatrixWith3sameSymbolAndMissShouldReturn0RewardWhenBetIs100(){
        String testMatrix [][] = {
                {"A", "B", "C"},
                {"D", "A", "F"},
                { "E", "A", "MISS"}
        };

        Mockito.when(matrixGeneratorService.generateMatrix(Mockito.any())).thenReturn(testMatrix);
        int expectedReward = 0;

        Result result = gameService.start(100);
        Assert.assertEquals(expectedReward, result.reward());
        Assert.assertTrue(result.appliedWinningCombinations().isEmpty());
        Assert.assertEquals(result.appliedBonus(), "");

    }

}


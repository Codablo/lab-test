package Classes;

import navis.injection.BuildFunction;
import navis.injection.BuildFunction1Arg;
import navis.injection.InstanceFactory;
import navis.injection.InstanceFactory1Arg;

import java.util.Random;

/**
 * Created by mikehollibaugh on 11/11/16.
 */
public class Dependencies {

    public static final InstanceFactory1Arg<Random, String> random = new InstanceFactory1Arg<>(new BuildFunction1Arg<Random, String>() {
        private Random random = null;

        @Override
        public Random build(String A) {
            if (random == null)
                if (A.isEmpty()) {
                    random = new Random();
                } else {
                    random = new Random(Integer.parseInt(A));
                }
            return random;
        }
    });

    public static final InstanceFactory<ConsoleIO> console = new InstanceFactory<>(new BuildFunction<ConsoleIO>() {
        private ConsoleIO consoleIO = null;

        @Override
        public ConsoleIO build() {
            if (consoleIO == null)
                consoleIO = new ConsoleIO();
            return consoleIO;
        }
    });

    public static final InstanceFactory<Deck> deck = new InstanceFactory<>(new BuildFunction<Deck>() {

        @Override
        public Deck build() {
            return new Deck();
        }
    });

    public static final InstanceFactory<Hand> hand = new InstanceFactory<>(new BuildFunction<Hand>() {

        @Override
        public Hand build() {
            return new Hand();
        }
    });

    public static final InstanceFactory<Prompt> prompt = new InstanceFactory<>(new BuildFunction<Prompt>() {

        @Override
        public Prompt build() {
            return new Prompt();
        }
    });

    public static final InstanceFactory<HumanPlayer> humanPlayer = new InstanceFactory<>(new BuildFunction<HumanPlayer>() {

        @Override
        public HumanPlayer build() {
            return new HumanPlayer();
        }
    });

    public static final InstanceFactory<BotPlayer> botPlayer = new InstanceFactory<>(new BuildFunction<BotPlayer>() {

        @Override
        public BotPlayer build() {
            return new BotPlayer();
        }
    });

    public static final InstanceFactory<Score> score = new InstanceFactory<>(new BuildFunction<Score>() {

        @Override
        public Score build() {
            return new Score();
        }
    });

    public static final InstanceFactory<Operations> operations = new InstanceFactory<>(new BuildFunction<Operations>() {

        @Override
        public Operations build() {
            return new Operations();
        }
    });
    public static final InstanceFactory<BlackJackGame> blackJackGame = new InstanceFactory<>(new BuildFunction<BlackJackGame>() {

        @Override
        public BlackJackGame build() {
            return new BlackJackGame();
        }
    });
}

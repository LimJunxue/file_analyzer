package com.filethings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.control.Button;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class AppTest {

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        new App().start(stage);
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void can_start(FxRobot robot) {
        Assertions.assertThat(robot.lookup(".button").queryAs(Button.class)).isNotNull();
    }
}

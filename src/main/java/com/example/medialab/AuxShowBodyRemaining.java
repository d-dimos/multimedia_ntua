package com.example.medialab;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class AuxShowBodyRemaining {
    static void ShowBodyRemaining(Circle head, Line body, Line left_arm, Line right_arm, Line left_foot, Line right_foot, int tries) {
        if (tries == 6) {
            head.setVisible(false);
            body.setVisible(false);
            left_arm.setVisible(false);
            right_arm.setVisible(false);
            left_foot.setVisible(false);
            right_foot.setVisible(false);
        }
        if (tries < 6)
            head.setVisible(true);
        if (tries < 5)
            body.setVisible(true);
        if (tries < 4)
            left_arm.setVisible(true);
        if (tries < 3)
            right_arm.setVisible(true);
        if (tries < 2)
            left_foot.setVisible(true);
        if (tries < 1)
            right_foot.setVisible(true);
        }
}


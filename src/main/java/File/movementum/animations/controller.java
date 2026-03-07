package File.movementum.animations;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.enums.PlayState;

import static com.zigythebird.playeranim.PlayerAnimLibMod.ANIMATION_LAYER_ID;

public class controller {
    public static void registerController() {
        System.out.println("[Movementum] Registering animation controller...");
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(ANIMATION_LAYER_ID, 1600,
                player -> new PlayerAnimationController(player,
                        (controller, state, animSetter) -> {
                        return PlayState.CONTINUE;
                        }
                )
        );
        System.out.println("[Movementum] Animation controller registered successfully!");
    }
}

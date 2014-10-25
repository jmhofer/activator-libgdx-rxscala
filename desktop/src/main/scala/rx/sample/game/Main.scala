package rx.sample.game

import com.badlogic.gdx.backends.lwjgl._
import rx.RxApplication

object Main extends App {
    val cfg = new LwjglApplicationConfiguration
    cfg.title = "Sample Game"
    cfg.height = 480
    cfg.width = 800
    cfg.forceExit = false
    new LwjglApplication(RxApplication.app(new SampleRxGame), cfg)
}

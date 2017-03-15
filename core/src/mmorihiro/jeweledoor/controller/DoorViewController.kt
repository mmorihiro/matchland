package mmorihiro.jeweledoor.controller

import ktx.actors.plus
import mmorihiro.jeweledoor.view.DoorView

class DoorViewController : ViewController {
    override val view = DoorView().apply {
        viewport.camera.translate(0f, -290f, 0f)
        this + backGround
    }
}

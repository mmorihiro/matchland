package mmorihiro.jeweledoor.controller

import ktx.actors.plus
import mmorihiro.jeweledoor.view.DoorView

class DoorViewController : ViewController {
    override val view = DoorView().apply {
        viewport.camera.translate(0f, -288f, 0f)
        this + backGround
        this + rightDoor
        this + leftDoor
        jewels.forEach { this + it }
    }
}

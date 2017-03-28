package mmorihiro.larger_circle.controller

import mmorihiro.larger_circle.view.DoorView

class DoorViewController : ViewController {
    override val view = DoorView().apply {
        viewport.camera.translate(0f, -288f, 0f)
    }
}

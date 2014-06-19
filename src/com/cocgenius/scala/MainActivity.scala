package com.cocgenius.scala

import org.scaloid.common._
import android.graphics.Color

class MainActivity extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      style {
        case b: SButton => b.onClick(toast("Bang!"))
        case t: STextView => t textSize 10.dip
        case v => v.backgroundColor(Color.YELLOW)
      }
      SButton(R.string.activate).onClick({
        startService[FloatWindowService]
        finish
      })
    }
  }

}

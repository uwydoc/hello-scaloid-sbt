package com.cocgenius.scala

import org.scaloid.common._

class MainActivity extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      SButton(R.string.activate).onClick({
        startService[FloatWindowService]
        finish
      })
    }
  }

}

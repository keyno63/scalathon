package jp.co.something.scalafx

import java.io.{FileNotFoundException, PrintWriter, StringWriter}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{GridPane, Priority, VBox}

import scala.language.implicitConversions

/**
  * Based on JavaFX version discussed in blog: [[http://code.makery.ch/blog/javafx-dialogs-official/ JavaFX Dialogs]]
  */
object DialogsDemo extends JFXApp {


  stage = new PrimaryStage {
    scene = new Scene {
      //icons += new Image("/scalafx/sfx.png")
      title = "Dialogs Demo"
      root = new VBox {
        children = Seq(
          button("Information", informationDialog),
          button("Without Header Text", withoutHeader),
          button("Warning", warningDialog),
          button("Error", errorDialog),
          button("Exception", exceptionDialog),
          button("Confirmation", confirmationDialog),
          button("Confirmation Dialog with Custom Actions", confirmationWithCustomActions),
          button("Text Input", textInputDialog),
          button("Choice", choiceDialog)
        )
        spacing = 6
        padding = Insets(10)
        alignment = Pos.Center
      }
    }
  }

  def button[R](text: String, action: () => R) = new Button(text) {
    onAction = handle {action()}
    alignmentInParent = Pos.Center
    hgrow = Priority.Always
    maxWidth = Double.MaxValue
    padding = Insets(7)
  }

  def informationDialog(): Unit = {
    new Alert(AlertType.Information) {
      initOwner(stage)
      title = "Information Dialog"
      headerText = "Look, an Information Dialog."
      contentText = "I have a great message for you!"
    }.showAndWait()
  }

  def withoutHeader(): Unit = {
    new Alert(AlertType.Information) {
      initOwner(stage)
      title = "Information Dialog"
      headerText = None
      contentText = "I have a great message for you!"
    }.showAndWait()
  }

  def warningDialog(): Unit = {
    new Alert(AlertType.Warning) {
      initOwner(stage)
      title = "Warning Dialog"
      headerText = "Look, an Warning Dialog."
      contentText = "Careful with the next step!"
    }.showAndWait()
  }

  def errorDialog(): Unit = {
    new Alert(AlertType.Error) {
      initOwner(stage)
      title = "Error Dialog"
      headerText = "Look, an Error Dialog."
      contentText = "Ooops, there was an error!"
    }.showAndWait()
  }

  def exceptionDialog(): Unit = {

    // Create expandable Exception.
    val exceptionText = {
      val ex = new FileNotFoundException("Could not find file blabla.txt")
      val sw = new StringWriter()
      val pw = new PrintWriter(sw)
      ex.printStackTrace(pw)
      sw.toString
    }
    val label = new Label("The exception stacktrace was:")
    val textArea = new TextArea {
      text = exceptionText
      editable = false
      wrapText = true
      maxWidth = Double.MaxValue
      maxHeight = Double.MaxValue
      vgrow = Priority.Always
      hgrow = Priority.Always
    }
    val expContent = new GridPane {
      maxWidth = Double.MaxValue
      add(label, 0, 0)
      add(textArea, 0, 1)
    }

    new Alert(AlertType.Error) {
      initOwner(stage)
      title = "Exception Dialog"
      headerText = "Look, an Exception Dialog."
      contentText = "Could not find file blabla.txt!"
      // Set expandable Exception into the dialog pane.
      dialogPane().expandableContent = expContent
    }.showAndWait()
  }

  def confirmationDialog(): Unit = {
    val alert = new Alert(AlertType.Confirmation) {
      initOwner(stage)
      title = "Confirmation Dialog"
      headerText = "Look, a Confirmation Dialog."
      contentText = "Are you ok with this?"
    }

    val result = alert.showAndWait()

    result match {
      case Some(ButtonType.OK) => println("OK")
      case _                   => println("Cancel or closed")
    }
  }

  def confirmationWithCustomActions(): Unit = {
    val ButtonTypeOne = new ButtonType("One")
    val ButtonTypeTwo = new ButtonType("Two")
    val ButtonTypeThree = new ButtonType("Three")

    val alert = new Alert(AlertType.Confirmation) {
      initOwner(stage)
      title = "Confirmation Dialog with Custom Actions"
      headerText = "Look, a Confirmation Dialog with Custom Actions."
      contentText = "Choose your option."
      // Note that we override here default dialog buttons, OK and Cancel, with new ones.
      // We could also just add to existing button using `++=`.
      buttonTypes = Seq(ButtonTypeOne, ButtonTypeTwo, ButtonTypeThree, ButtonType.Cancel)
    }

    val result = alert.showAndWait()

    result match {
      case Some(ButtonTypeOne)   => println("... user chose \"One\"")
      case Some(ButtonTypeTwo)   => println("... user chose \"Two\"")
      case Some(ButtonTypeThree) => println("... user chose \"Three\"")
      case _                     => println("... user chose CANCEL or closed the dialog")
    }
  }

  def textInputDialog(): Unit = {
    val dialog = new TextInputDialog(defaultValue = "walter") {
      initOwner(stage)
      title = "Text Input Dialog"
      headerText = "Look, a Text Input Dialog."
      contentText = "Please enter your name:"
    }

    val result = dialog.showAndWait()
    result match {
      case Some(name) => println("Your name: " + name)
      case None       => println("Dialog was canceled.")
    }
  }

  def choiceDialog(): Unit = {

    val choices = Seq("a", "b", "c")

    val dialog = new ChoiceDialog(defaultChoice = "b", choices = choices) {
      initOwner(stage)
      title = "Choice Dialog"
      headerText = "Look, a Choice Dialog."
      contentText = "Choose your letter:"
    }

    val result = dialog.showAndWait()

    result match {
      case Some(choice) => println("Your choice: " + choice)
      case None         => println("No selection")
    }
  }

}

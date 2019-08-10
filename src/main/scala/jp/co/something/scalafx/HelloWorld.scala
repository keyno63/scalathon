package jp.co.something.scalafx

import jp.co.something.libs.SampleHttpClient
import scalafx.Includes.{handle, _}
import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout.{GridPane, VBox}

import scala.language.implicitConversions

object HelloWorld extends JFXApp {

  stage = new PrimaryStage {
    scene = new Scene {
      //icons += new Image("/scalafx/sfx.png")
      title = "Dialogs Demo"

      root = new VBox {
        children = Seq(
          button("Hello World Http Dialog", requestSettingDialog),
          button("Hello World Login Dialog", onShowLoginDialog),
          button("Hello World Json Parser", onParseJson),
        )
      }
    }
  }

  def button[R](text: String, function: () => R): Button = {
    new Button(text) {
      onAction = handle{function()}
    }
  }

  def onShowLoginDialog(): Unit = {

    case class Result(username: String, password: String)

    val dialog = new Dialog[Result]() {
      initOwner(stage)
      title = "Login Dialog"
      headerText = "Look, a Custom Login Dialog"
    }

    // Set the button types.
    val loginButtonType = new ButtonType("Login", ButtonData.OKDone)
    dialog.dialogPane().buttonTypes = Seq(loginButtonType, ButtonType.Cancel)

    // Create the username and password labels and fields.
    val username = new TextField() {
      promptText = "Username"
    }
    val password = new PasswordField() {
      promptText = "Password"
    }

    val grid = new GridPane() {
      hgap = 10
      vgap = 10
      padding = Insets(20, 100, 10, 10)

      add(new Label("Username:"), 0, 0)
      add(username, 1, 0)
      add(new Label("Password:"), 0, 1)
      add(password, 1, 1)
    }

    // Enable/Disable login button depending on whether a username was
    // entered.
    val loginButton = dialog.dialogPane().lookupButton(loginButtonType)
    loginButton.disable = true

    // Do some validation (disable when username is empty).
    username.text.onChange { (_, _, newValue) =>
      loginButton.disable = newValue.trim().isEmpty
    }

    dialog.dialogPane().content = grid

    // Request focus on the username field by default.
    Platform.runLater(username.requestFocus())

    // Convert the result to a username-password-pair when the login button is clicked.
    dialog.resultConverter = dialogButton =>
      if (dialogButton == loginButtonType) Result(username.text(), password.text())
      else null

    val result = dialog.showAndWait()

    result match {
      case Some(Result(u, p)) => println("Username=" + u + ", Password=" + p)
      case None               => println("Dialog returned: None")
    }
  }

  def requestSettingDialog(): Unit = {
    case class HttpGUIResult(url: String, params: Map[String, String],
                             header: Map[String, String]) // not impl header yet.

    val dialog = new Dialog[HttpGUIResult]() {
      initOwner(stage)
      title = "HttpReqest Dialog"
      headerText = "Look, a Custom HttpRequest Dialog"
    }

    // Set the button types.
    val loginButtonType = new ButtonType("Request", ButtonData.OKDone)
    dialog.dialogPane().buttonTypes = Seq(loginButtonType, ButtonType.Cancel)

    val url = new TextField(new javafx.scene.control.TextField("http://example.com")) {
      promptText = "URL"
    }
    // Create the username and password labels and fields.
    val username = new TextField() {
      promptText = "Username"
    }
    val password = new PasswordField() {
      promptText = "Password"
    }

    val grid = new GridPane() {
      hgap = 10
      vgap = 10
      padding = Insets(20, 100, 10, 10)

      add(new Label("URL:"), 0, 0)
      add(url, 1, 0)
      add(new Label("Username:"), 0, 1)
      add(username, 1, 1)
      add(new Label("Password:"), 0, 2)
      add(password, 1, 2)
    }

    // Enable/Disable login button depending on whether a username was
    // entered.
    val loginButton = dialog.dialogPane().lookupButton(loginButtonType)
    loginButton.disable = true

    // Do some validation (disable when username is empty).
    username.text.onChange { (_, _, newValue) =>
      loginButton.disable = newValue.trim().isEmpty
    }

    dialog.dialogPane().content = grid

    // Request focus on the username field by default.
    Platform.runLater(username.requestFocus())

    var res: skinny.http.Response = skinny.http.Response(500)
    // Convert the result to a username-password-pair when the login button is clicked.
    dialog.resultConverter = dialogButton =>
      if (dialogButton == loginButtonType) {
        val param = Map[String, String](username.text() -> password.text())
        res = SampleHttpClient.get(url.text(), param)
        HttpGUIResult(url.text(), param, Map.empty[String, String])
      } else null

    val result = dialog.showAndWait()

    result match {
      case Some(HttpGUIResult(u, p, h)) =>
        println(s"URL=${u}, param=${p}, header=${h}")
        println(res.status, res.textBody)
      case None => println("Dialog returned: None")
    }
  }

  def onParseJson(): Unit = {
  }

}

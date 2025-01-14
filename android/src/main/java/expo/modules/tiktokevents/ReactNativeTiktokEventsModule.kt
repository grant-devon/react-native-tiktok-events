package expo.modules.tiktokevents

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import java.net.URL
import com.tiktok.TikTokBusinessSdk;
import com.tiktok.TikTokBusinessSdk.TTConfig


class ReactNativeTiktokEventsModule : Module() {
  // Each module class must implement the definition function. The definition consists of components
  // that describes the module's functionality and behavior.
  // See https://docs.expo.dev/modules/module-api for more details about available components.
  override fun definition() = ModuleDefinition {
    // Sets the name of the module that JavaScript code will use to refer to the module. Takes a string as an argument.
    // Can be inferred from module's class name, but it's recommended to set it explicitly for clarity.
    // The module will be accessible from `requireNativeModule('ReactNativeTiktokEvents')` in JavaScript.
    Name("ReactNativeTiktokEvents")

    // Sets constant properties on the module. Can take a dictionary or a closure that returns a dictionary.
    Constants(
      "SDK_VERSION" to "1.3.7"
    )

    AsyncFunction("initSDK") {appId: String, ttAppID: String ->
      try {
        val ttConfig = TTConfig(appContext).setAppId(appId).setTTAppId(ttAppID)..openDebugMode().setLogLevel(TikTokBusinessSdk.LogLevel.DEBUG)

        TikTokBusinessSdk.initializeSdk(ttConfig, object: TikTokBusinessSdk.TTInitCallback {
          override fun success() {
            sendEvent("onInitializeSuccess", mapOf(
                    "message" to "TikTok SDK Initialized Successfully"
            ))
          }

          override fun fail(code: Int, msg: String) {
            sendEvent("onInitializeError", mapOf(
                    "code" to code,
                    "message" to msg
            ))
          }
        })

        TikTokBusinessSdk.startTrack()
        "SDK initialization started"
      } catch (e: Exception)
      throw RuntimeException("Error initializing TikTok SDK: ${e.message}")
    }

    AsyncFunction("trackAppLaunch") {
      try {
        TikTokBusinessSdk.trackEvent("Launch")
        "App launch tracked successfully"
      } catch (e: Exception) {
        throw RuntimeException("Error tracking app launch: ${e.message}")
      }
    }

    AsyncFunction("trackCustomEvent") {eventName: String, properties: Map<String, Any> ->
      try {
        if (properties != null) {
          TikTokBusinessSdk.trackEvent(eventName, properties)
        } else {
          TikTokBusinessSdk.trackEvent(eventName)
        }
      } catch (e: Exception) {
        throw RuntimeException("Error tracking event: ${e.message}")
      }
    }

    // Defines event names that the module can send to JavaScript.
    Events("onInitializeSuccess", "onInitializeError")
  }
}

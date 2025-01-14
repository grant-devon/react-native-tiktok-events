import ExpoModulesCore
import TikTokBusinessSDK

public class ReactNativeTiktokEventsModule: Module {
    public func definition() -> ModuleDefinition {
        Name("ReactNativeTiktokEvents")

        Constants([
            "SDK_VERSION": "1.3.0"
        ])

        AsyncFunction("initializeSDK") { (appId: String, ttAppId: String, promise: Promise) in
            let config = TikTokConfig()
            config.appId = appId
            config.tiktokAppId = ttAppId
            config.logLevel = .debug
            
            TikTokBusiness.initializeSdk(config) { [weak self] success, error in
                if success {
                    self?.sendEvent("onInitializeSuccess", [
                        "message": "TikTok SDK Initialized Successfully"
                    ])
                    promise.resolve("SDK initialization successful")
                } else if let error = error {
                    self?.sendEvent("onInitializeError", [
                        "code": error.code,
                        "message": error.localizedDescription
                    ])
                    promise.reject("InitError", error.localizedDescription)
                }
            }
            
            TikTokBusiness.startTrack()
        }

        AsyncFunction("trackEvent") { (eventName: String, properties: [String: Any]?, promise: Promise) in
            if let properties = properties {
                TikTokBusiness.trackEvent(eventName, withProperties: properties)
            } else {
                TikTokBusiness.trackEvent(eventName)
            }
            promise.resolve("Event \(eventName) tracked successfully")
        }

        Events(
            "onInitializeSuccess",
            "onInitializeError"
        )
    }
}
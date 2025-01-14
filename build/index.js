import { EventEmitter } from "expo-modules-core";
import ReactNativeTiktokEventsModule from "./ReactNativeTiktokEventsModule";
class TikTokEvents {
    emitter;
    constructor() {
        this.emitter = new EventEmitter(ReactNativeTiktokEventsModule);
    }
    async initialize(appId, ttAppId) {
        return await ReactNativeTiktokEventsModule.initializeSDK(appId, ttAppId);
    }
    async trackEvent(eventName, properties) {
        return await ReactNativeTiktokEventsModule.trackEvent(eventName, properties);
    }
    addInitializeSuccessListener(listener) {
        return this.emitter.addListener("onInitializeSuccess", listener);
    }
    addInitializeErrorListener(listener) {
        return this.emitter.addListener("onInitializeError", listener);
    }
}
export default new TikTokEvents();
//# sourceMappingURL=index.js.map
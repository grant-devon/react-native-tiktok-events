import { EventEmitter, EventSubscription } from "expo-modules-core"


import ReactNativeTiktokEventsModule from "./ReactNativeTiktokEventsModule"

export interface TikTokEventProps {
  eventName: string
  properties?: Record<string, any>
}

class TikTokEvents {
  private emitter: InstanceType<typeof EventEmitter>

  constructor() {
    this.emitter = new EventEmitter(ReactNativeTiktokEventsModule)
  }

  async initialize(appId: string, ttAppId: string): Promise<string> {
    return await ReactNativeTiktokEventsModule.initializeSDK(appId, ttAppId)
  }

  async trackEvent(eventName: string, properties?: Record<string, any>): Promise<string> {
    return await ReactNativeTiktokEventsModule.trackEvent(eventName, properties)
  }

  addInitializeSuccessListener(listener: (event: { message: string }) => void): EventSubscription {
    return this.emitter.addListener("onInitializeSuccess", listener)
  }

  addInitializeErrorListener(listener: (event: { code: number; message: string }) => void): EventSubscription {
    return this.emitter.addListener("onInitializeError", listener)
  }
}

export default new TikTokEvents()
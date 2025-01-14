import { EventSubscription } from "expo-modules-core";
export interface TikTokEventProps {
    eventName: string;
    properties?: Record<string, any>;
}
declare class TikTokEvents {
    private emitter;
    constructor();
    initialize(appId: string, ttAppId: string): Promise<string>;
    trackEvent(eventName: string, properties?: Record<string, any>): Promise<string>;
    addInitializeSuccessListener(listener: (event: {
        message: string;
    }) => void): EventSubscription;
    addInitializeErrorListener(listener: (event: {
        code: number;
        message: string;
    }) => void): EventSubscription;
}
declare const _default: TikTokEvents;
export default _default;
//# sourceMappingURL=index.d.ts.map
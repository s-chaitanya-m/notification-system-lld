package chaitanya.shinde.notificationSystem.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class User {
    private final String id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final Set<NotificationTypeEnum> optedOutTypes;
    private boolean globalOptOut;
    private final Map<NotificationTypeEnum, Set<ChannelType>> preferences;

    public void setGlobalOptOut(boolean optOut) {
        this.globalOptOut = optOut;
        if (optOut) {
            System.out.println(name + " opted out form all notifications");
        } else {
            System.out.println(name + " opted into notifications");
        }
    }

    public Set<ChannelType> getPreferredChannels(NotificationTypeEnum nType) {
        if(globalOptOut || optedOutTypes.contains(nType)) {
            return Collections.emptySet();
        }
        return preferences.getOrDefault(nType, Collections.emptySet());
    }

    public boolean isSubscribedTo(NotificationTypeEnum nType) {
        return !globalOptOut && !optedOutTypes.contains(nType) && preferences.containsKey(nType);
    }

    public void subscribeToNotification(NotificationTypeEnum nType, ChannelType cType) {
        if(globalOptOut) {
            System.out.println(name + " is globally opted out");
            return;
        }
        preferences.computeIfAbsent(nType, k -> new HashSet<>()).add(cType);
        optedOutTypes.remove(nType);
        System.out.println(name + " subscribed to " + nType.name() + " via " + cType.name());
    }

    public void optOutFromNotificationType(NotificationTypeEnum nType) {
        optedOutTypes.add(nType);
        preferences.remove(nType);
        System.out.println(name + " opted out from all " + nType.name() + "notifications");
    }

}

package infinitetides.phantomtactics.util;

import com.google.common.collect.ImmutableList;
import java.util.HashSet;
import java.util.Set;

/** Like Observable, but type-safe */
public class Event<T> {

    /** Like Observer, but type-safe */
    public interface EventWatcher<T>{
        void onEvent(Event<T> event, T arg);
    }

    private final Set<EventWatcher<T>> Watchers = new HashSet<>(10);

    /** Adds a watcher that will be receive events. */
    public void addWatcher (EventWatcher<T> watcher) {
        if (null != watcher) {
            synchronized(Watchers) {
                Watchers.add(watcher);
            }
        }
    }

    /** Removes a watcher, so that it no longer receives events. */
    public void removeWatcher (EventWatcher<T> watcher) {
        if (null != watcher) {
            synchronized(Watchers) {
                Watchers.remove(watcher);
            }
        }
    }

    /** Removes all watchers attached to this instance. */
    public void clearWatchers () {
        synchronized(Watchers) {
            Watchers.clear();
        }
    }

    /** Notifies all of the watchers for this object, passing them 'arg'. */
    public void fire(T arg) {
        if (null == arg) {
            return;
        }

        // Freeze the list of watchers to be notified
        ImmutableList<EventWatcher<T>> copy_of_watchers;
        synchronized(Watchers) {
            copy_of_watchers = ImmutableList.copyOf(Watchers);
        }

        // Release the monitor before heading off to execute arbitrary code.
        for(EventWatcher<T> watcher : copy_of_watchers) {
            watcher.onEvent(this, arg);
        }
    }
}

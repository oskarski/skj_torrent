package HostTracker;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HostTrackerState {
    public final static Set<String> hosts = Collections.synchronizedSet(new HashSet<>());
}

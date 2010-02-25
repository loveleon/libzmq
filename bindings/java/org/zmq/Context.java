/*
    Copyright (c) 2007-2010 iMatix Corporation

    This file is part of 0MQ.

    0MQ is free software; you can redistribute it and/or modify it under
    the terms of the Lesser GNU General Public License as published by
    the Free Software Foundation; either version 3 of the License, or
    (at your option) any later version.

    0MQ is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    Lesser GNU General Public License for more details.

    You should have received a copy of the Lesser GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.zmq;

public class Context {
    static {
        System.loadLibrary("jzmq");
    }

    public static final int POLL = 1;

    public static final int POLLIN = 1;
    public static final int POLLOUT = 2;
    public static final int POLLERR = 4;

    /**
     * Class constructor.
     *
     * @param appThreads maximum number of application threads.
     * @param ioThreads size of the threads pool to handle I/O operations.
     */
    public Context (int appThreads, int ioThreads, int flags) {
        construct (appThreads, ioThreads, flags);
    }

    /**
     * Issue a poll call on the specified 0MQ sockets.
     * This function is experimental and may change in the future.
     *
     * @param socket an array of 0MQ Socket objects to poll.
     * @param event an array of short values specifying what to poll for.
     * @param revent an array of short values with the results.
     * @param timeout the maximum timeout in microseconds.
     */
    public native long poll (Socket[] socket,
			     short[] event,
			     short[] revent,
			     long timeout);

    /** Initialize the JNI interface */
    protected native void construct (int appThreads, int ioThreads, int flags);

    /** Free all resources used by JNI interface. */
    protected native void finalize ();

    /**
     * Get the underlying context handle.
     * This is private because it is only accessed from JNI, where
     * Java access controls are ignored.
     *
     * @return the internal 0MQ context handle.
     */
    private long getContextHandle () {
        return contextHandle;
    }

    /** Opaque data used by JNI driver. */
    private long contextHandle;
}

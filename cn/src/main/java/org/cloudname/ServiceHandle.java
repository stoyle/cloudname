package org.cloudname;

import java.util.List;

/**
 * The service handle -- the interface through which services
 * communicate their state to the outside world and where services can
 * register listeners to handle configuration updates.
 *
 * @author borud
 */
public interface ServiceHandle {

    /**
     * Wait for any previous operation to be complete and coordinate to be claimed state (initial claim, put endpoints etc)
     * @return
     */
 //   public boolean waitForSynchronized();
//     public boolean waitForConnectionToStorageMillis(long millis) throws CloudnameException;

    /**
     * Set the status of this service.
     *
     *
     * @param status the new status.
     */
    public StorageFuture setStatus(ServiceStatus status);

    /**
     * Publish a named endpoint.  If the endpoint already exists an
     * exception will be thrown.  If you want to republish a named
     * endpoint you first have to remove it.
     *
     * @param endpoint the endpoint data.
     */
    public StorageFuture putEndpoint(Endpoint endpoint);

    /**
     * Same as putEndpoints, but takes a list.
     *
     * @param endpoints the endpoints data.
     */
    public StorageFuture putEndpoints(List<Endpoint> endpoints);

    /**
     * Remove a published endpoint.
     *
     * @param name the name of the endpoint we wish to remove.
     */
    public StorageFuture removeEndpoint(String name);

    /**
     * Same as removeEndpoint() but takes a list of names.
     *
     * @param names
     */
    public StorageFuture removeEndpoints(List<String> names);


    /**
     * Register a ConfigListener which will be called whenever there
     * is a configuration change.
     *
     * There may have been configuration pushed to the backing storage
     * already by the time a ConfigListener is registered.  In that
     * case the ConfigListener will see these configuration items as
     * being created.
     */
    public void registerConfigListener(ConfigListener listener);

    /**
     * After registering a new listener, a new event is triggered which include current state, even without change
     * of state.
     * Don't call the cloudname library, do any heavy lifting, or do any IO operation from this callback thread.
     * That might deadlock as there is no guarantee what kind of thread that runs the callback.
     *
     * @param listener
     * @throws CloudnameException if problems talking with storage.
     */
    public void registerCoordinateListener(CoordinateListener listener)
            throws CloudnameException;

    /**
     * Close the service handle and free up the coordinate so it can
     * be claimed by others.  After close() has been called all
     * operations on this instance of the service handle will result
     * in an exception being thrown. All endpoints are deleted.
     * @throws CloudnameException if problems talking with storage.
     */
    public void close()
            throws CloudnameException;
}


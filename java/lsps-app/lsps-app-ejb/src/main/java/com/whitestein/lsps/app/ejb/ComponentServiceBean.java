package com.whitestein.lsps.app.ejb;

import javax.annotation.security.PermitAll;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.whitestein.lsps.common.ComponentService;
import com.whitestein.lsps.common.ComponentServiceBase;
import com.whitestein.lsps.common.ComponentServiceLocal;
import com.whitestein.lsps.common.ComponentServiceRegistry;
import com.whitestein.lsps.common.JavaReflectionCache;

/**
 * The central class for registering custom EJB components (custom LSPS tasks and functions).
 * The components have to be registered inside {@link ComponentServiceBean#registerCustomComponents()} method.
 * See a commented snippet in the source code for an example. 
 * POJO implementations of tasks and functions do not have to be registered.
 * 
 * ComponentService is accessed quite frequently so its retrieval and/or instantiation should be fast.
 * In case it's implemented as @Stateless bean, ensure the application container pools are properly configured.    
 * As long as the custom components are static (which is usual the case), 
 * making the ComponentServiceBean a @Singleton bean (with ConcurrentHashMap caches) yields the best performance.
 * In such case, the {@link #getCache()} method could/should expose the underlying registry {@link #getRegistry()} to consumers) 
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@PermitAll
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ComponentServiceBean extends ComponentServiceBase implements ComponentServiceLocal {

	/**
	 * Constructor
	 */
	public ComponentServiceBean() {
		super(new ComponentServiceRegistry());
		JavaReflectionCache.INSTANCE.setAppClassLoader(getClass().getClassLoader());
		JavaReflectionCache.INSTANCE.setAppProtectionDomain(getClass().getProtectionDomain());
	}

	@Override
	public ComponentService getCache() {
		return getRegistry();
	}

	/*
	 * To register a custom EJB task (e.g. com.whitestein.lsps.custom.MyCustomTask)
	 * the target EJB has to be injected using its local interface ExecutableTask
	 * and specifying beanName attribute (which is by default non-qualified class name of its implementation). 
	 * The instance of the given EJB task must be then registered under its implementation class.
	 * The task is then referenced in the model by its fully qualified implementation task name.
	 * 
	 * To register custom EJB functions (e.g. com.whitestein.lsps.custom.MyCustomFunctions)
	 * the target EJB has to be injected using its local interface (the local interface must declare
	 * all the functions that will be used in the model).
	 * The instance of the given EJB must be then registered under its local interface class.
	 * The target function is then referenced in the model using its fully qualified local interface name and method name.
	 * 
	
	
	
	
	@EJB(beanName = "MyCustomTask")
	private ExecutableTask myCustomTask;
	
	@EJB
	private MyFunctions myFunctions;
	
	
	@Override
	protected void registerCustomComponents() {
		register(myCustomTask, MyCustomTask.class);
		register(myFunctions, MyFunctions.class);
	}
	*/

}
